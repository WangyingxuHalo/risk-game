package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.*;
import edu.duke.ece651.team4.server.entity.Season;
import edu.duke.ece651.team4.server.entity.Unit;
import edu.duke.ece651.team4.server.model.*;
import edu.duke.ece651.team4.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The GameService class to create and manage games
 */
@Service
public class GameService {

    /**
     * Season Service.
     */
    @Autowired
    SeasonService seasonService;
    /**
     * Action service.
     */
    @Autowired
    private ActionService actionService;

    /**
     * The dice to resolve bonuses.
     */
    private TwentySidedDice dice = new TwentySidedDice();

    /**
     * The player repository to add players to.
     */
    @Autowired
    private PlayerRepository playerRepository;

    /**
     * The game repository to add the game to.
     */
    @Autowired
    private GameRepository gameRepository;

    /**
     * The Territory repositories to add territories to.
     */
    @Autowired
    private TerritoryRepository territoryRepository;

    /**
     * The territory neighbor repository to add territories to.
     */
    @Autowired
    private NeighborRepository neighborRepository;

    /**
     * The unit repository to interact with Unit entity
     */
    @Autowired
    private UnitRepository unitRepository;

    /**
     * Repository containing resolved actions in last round.
     */
    @Autowired
    private ResolveRepository resolveRepository;

    /**
     * Repository containing details of resolved actions.
     */
    @Autowired
    private ResolveDetailRepository resolveDetailRepository;

    /**
     * Repository containing details of the season.
     */
    @Autowired
    private SeasonRepository seasonRepository;

    /**
     * Repository containing details of the spy.
     */
    @Autowired
    private SpyRepository spyRepository;

    /**
     * Repository containing details of the territory view.
     */
    @Autowired
    private TerritoryViewRepository territoryViewRepository;

    /**
     * Repository containing details of the unit view.
     */
    @Autowired
    private UnitViewRepository unitViewRepository;

    /**
     * Method to create a game. This adds all necessary pieces of the game to the repositories and
     * assigns the player that requested the game creation to "Red".
     *
     * @param userID     the user id
     * @param numPlayers is the number of players in the game.
     * @return int which is the gameId
     */
    public ResGame createGame(int userID, int numPlayers) {
        int gameID = gameRepository.save(new Game(numPlayers)).getId();
        int playerId = createPlayers(gameID, userID, numPlayers);
        seasonService.checkAndUpdateSeason(gameID, 1);
        return new ResGame(gameID, playerId, "Red");
    }

    /**
     * Method to join a new game.
     *
     * @param userID the user id
     * @param gameId is the game id
     * @return ResJoinGame which is contains player id
     */
    public ResJoinGame joinGame(int userID, int gameId) {
        List<Player> players = playerRepository.findByGameIdAndUserId(gameId, null);
        ResJoinGame resJoinGame = new ResJoinGame();
        if (players.size() == 0) {
            resJoinGame.setPlayerID(-1);
        } else {
            Player player = players.get(0);
            resJoinGame.setColor(player.getName());
            resJoinGame.setPlayerID(player.getId());
            player.setUserId(userID);
            playerRepository.save(player);
        }
        return resJoinGame;
    }

    /**
     * Method to check if game is ready.
     *
     * @param gameID is the game id
     * @return Boolean true if game is ready else false
     */
    public Boolean isGameReady(int gameID) {
        List<Player> players = playerRepository.findByGameIdAndUserId(gameID, null);
        return players.size() == 0;
    }

    /**
     * Method to resume a game.
     *
     * @param gameID   is the game id
     * @param playerId the player id
     * @return Boolean true if game resumes else false
     */
    public Boolean resumeGame(int gameID, int playerId) {
        Player player = playerRepository.findByIdAndGameId(playerId, gameID);
        return player != null;
    }

    /**
     * method to get all games the user is part of
     *
     * @param userId the user id
     * @return Map<Integer, Integer> which is the gameIds and playerIds map
     */
    public ResGames getGames(int userId) {
        List<Player> playerList = playerRepository.findByUserId(userId);
        ResGames resGames = new ResGames();
        List<ResGame> resGameList = new ArrayList<>();
        for (Player player : playerList) {
            Game game = gameRepository.findById(player.getGameId()).get();
            if (!game.getGameOver()) {
                ResGame resGame = new ResGame(player.getGameId(), player.getId(), player.getName());
                resGameList.add(resGame);
            }
        }
        resGames.setGames(resGameList);
        return resGames;
    }

    /**
     * method to get game's map
     *
     * @param playerId the player id
     * @return ResGameMap which is game map
     */
    public ResGameMap getGameMap(int playerId) {
        Player player = playerRepository.findById(playerId).get();
        List<Player> players = playerRepository.findByGameId(player.getGameId());
        List<Territory> territories = territoryRepository.findByGameId(player.getGameId());
        Map<String, ResMapTerr> territoryMap = new HashMap<>();
        for (Territory territory : territories) {
            ResMapTerr resMapTerr;

            //Find the territory owner
            Player owner = player;
            for (Player plyr : players) {
                if (plyr.getId() == territory.getOwnerId()) {
                    owner = plyr;
                    break;
                }
            }

            //1. check if current player own it or if spy present or adjacent and not cloaked, if any true, set color and units as is
            if (territory.getOwnerId() == playerId
                    || getSpyNum(playerId, territory.getId()) > 0
                    || (isAdjacent(playerId, territory.getId()) && territory.getCloak() == 0)) {
                resMapTerr = getResMapTerr(owner.getName(), owner.getName(), getUnitsByTerritoryId(territory.getId()), getSpyNum(playerId, territory.getId()));
            }
            //2. if not owned by current player, check if adjacent and cloaked
            else if (isAdjacent(playerId, territory.getId()) && territory.getCloak() > 0) {
                resMapTerr = getResMapTerr(null, "white", null, 0);
            }
            //3. If all above are false then check territory-view table then color as grey and get units from units-view table
            else {
                Optional<TerritoryView> optTerritoryView = territoryViewRepository.findByTerritoryIdAndPlayerId(territory.getId(), playerId);
                if (optTerritoryView.isPresent()) {
                    TerritoryView tView = optTerritoryView.get();
                    resMapTerr = getResMapTerr(owner.getName(), "grey", getUnitViews(tView.getId()), 0);
                }
                else {
                    resMapTerr = getResMapTerr(null, "white", null, 0);
                }
            }

            territoryMap.put(territory.getName(), resMapTerr);
        }
        return new ResGameMap(territoryMap, 240 / players.size());
    }

    /**
     * method to get unit views
     *
     * @param territoryViewId the territory view id
     * @return List<edu.duke.ece651.team4.server.model.Unit> which is the unit list
     */
    private List<edu.duke.ece651.team4.server.model.Unit> getUnitViews(int territoryViewId) {
        List<UnitView> unitViews = unitViewRepository.findByTerritoryViewId(territoryViewId);
        List<edu.duke.ece651.team4.server.model.Unit> unitList = new ArrayList<>();
        for (UnitView unit : unitViews) {
            unitList.add(new edu.duke.ece651.team4.server.model.Unit(unit.getType(), unit.getCount()));
        }
        return unitList;
    }

    /**
     * method to get ResMapTerr
     *
     * @param owner  the owner name
     * @param color  the territory color
     * @param units  the units list
     * @param spyNum the number of spies
     * @return ResMapTerr which is ResMapTerr object
     */
    private ResMapTerr getResMapTerr(String owner, String color, List<edu.duke.ece651.team4.server.model.Unit> units, int spyNum) {
        ResMapTerr resMapTerr = new ResMapTerr();
        resMapTerr.setOwner(owner);
        resMapTerr.setColor(color);
        resMapTerr.setNumUnit(units);
        resMapTerr.setSpyNum(spyNum);
        return resMapTerr;
    }

    /**
     * method to check if a territory is adjacent to the current player's territory
     *
     * @param playerId    the player id
     * @param territoryId the territory id
     * @return boolean true if adjacent else false
     */
    protected boolean isAdjacent(int playerId, int territoryId) {
        List<Territory> territories = territoryRepository.findByOwnerId(playerId);
        for (Territory territory : territories) {
            if (neighborRepository.findByTerritoryIdAndNeighborId(territory.getId(), territoryId).size() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to get number of spies
     *
     * @param playerId    the player id
     * @param territoryId the territory id
     * @return int number of spies
     */
    private int getSpyNum(int playerId, int territoryId) {
        return spyRepository.findByPlayerIdAndTerritoryId(playerId, territoryId).size();
    }

    /**
     * method to get units
     *
     * @param territoryId the territory id
     * @return List<edu.duke.ece651.team4.server.model.Unit> which is the unit list
     */
    private List<edu.duke.ece651.team4.server.model.Unit> getUnitsByTerritoryId(int territoryId) {
        List<Unit> units = unitRepository.findByTerritoryId(territoryId);
        List<edu.duke.ece651.team4.server.model.Unit> unitList = new ArrayList<>();
        for (Unit unit : units) {
            unitList.add(new edu.duke.ece651.team4.server.model.Unit(unit.getType(), unit.getCount()));
        }
        return unitList;
    }

    /**
     * method to get game's turn
     *
     * @param gameId the game id
     * @return ResGetTurn which is game turn object
     */
    public ResGetTurn getTurn(int gameId) {
        Game game = gameRepository.findById(gameId).get();
        return new ResGetTurn(game.getTurnNum());
    }

    /**
     * method to get all game info
     *
     * @param playerId the player id
     * @return ResAllInfo which is game all info
     */
    public ResAllInfo getAllInfo(int playerId) {
        ResGameMap resGameMap = getGameMap(playerId);
        Player player = playerRepository.findById(playerId).get();
        Game game = gameRepository.findById(player.getGameId()).get();
        List<Territory> territories = territoryRepository.findByOwnerId(player.getId());
        int genFood = 0;
        int genTech = 0;
        List<String> territoryNames = new ArrayList<>();
        for (Territory territory : territories) {
            genFood += territory.getFoodGeneration();
            genTech += territory.getTechGeneration();
            territoryNames.add(territory.getName());
        }
        String winnerName = null;
        if (game.getNumAlivePlayers() == 1) {
            List<Player> players = playerRepository.findByGameId(game.getId());
            for (Player p : players) {
                if (p.getAlive()) {
                    winnerName = p.getName();
                    break;
                }
            }
        }
        ResTurnInfo resTurnInfo = new ResTurnInfo(player.getName(), game.getId(), game.getTurnNum(), game.getNumPlayers(), player.getTotalFood(), player.getTotalTech(), genFood, genTech, player.getTechLevel(), territoryNames, winnerName);
        ResLastRoundInfo resLastRoundInfo = getResResolve(game.getId());
        Season season = seasonRepository.findByGameId(game.getId());
        edu.duke.ece651.team4.server.model.Season seasonInfo = new edu.duke.ece651.team4.server.model.Season(season.getSeason(), season.getMessage());
        return new ResAllInfo(resGameMap, resTurnInfo, resLastRoundInfo, seasonInfo);
    }

    /**
     * method to play a bonus turn
     *
     * @param bonus the bonus turn
     * @return Integer which is the bonus round result
     */
    public Integer playBonus(Bonus bonus) {
        int playerId = bonus.getPlayerId();
        String type = bonus.getType();
        int roll = dice.roll();
        Player p = playerRepository.findById(playerId).get();
        if (type.equals("Food")) {
            p.setTotalFood(p.getTotalFood() + roll * 10);
            playerRepository.save(p);
        } else if (type.equals("Tech")) {
            p.setTotalTech(p.getTotalTech() + roll * 10);
            playerRepository.save(p);
        } else {
            Territory t = territoryRepository.findByNameAndOwnerId(bonus.getTerr(), playerId);
            Unit u = unitRepository.findByTerritoryIdAndType(t.getId(), 0);
            u.setCount(u.getCount() + roll);
            unitRepository.save(u);
        }
        OnePlayerTurn turn = new OnePlayerTurn(playerId, 0, new ArrayList<>(),
                new ArrayList<>(), false, p.getTechLevel(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        actionService.conductTurn(turn);
        return roll;
    }

    /**
     * Get attacks from last round.
     *
     * @param gameID is the game to fetch for.
     * @return an object containing all the things from last round.
     */
    protected ResLastRoundInfo getResResolve(int gameID) {
        List<ResResolve> resResolves = new ArrayList<>();
        List<Resolve> resolves = resolveRepository.findByGameId(gameID);
        for (Resolve r : resolves) {
            String attackedTerritory = territoryRepository.findById(r.getAttackedTerritoryId()).get().getName();
            String owner = playerRepository.findById(r.getOriginalOwnerId()).get().getName();
            String winner = playerRepository.findById(r.getWinnerId()).get().getName();
            List<ResolveDetail> attackers = resolveDetailRepository.findByResolveId(r.getId());
            List<ResAttacker> resAttackers = new ArrayList<>();
            for (ResolveDetail rd : attackers) {
                String pName = playerRepository.findById(rd.getAttackerId()).get().getName();
                String tName = territoryRepository.findById(rd.getTerritoryId()).get().getName();
                List<String> terrs = new ArrayList<>();
                terrs.add(tName);
                resAttackers.add(new ResAttacker(pName, terrs));
            }
            resResolves.add(new ResResolve(attackedTerritory, owner, winner, resAttackers));
        }
        ResLastRoundInfo resLastRoundInfo = new ResLastRoundInfo(resResolves);
        return resLastRoundInfo;
    }


    /**
     * Create the players. Automatically save to database.
     *
     * @param gameID     is the GameID created.
     * @param userID     is the first user to create this game.
     * @param numPlayers is the number of players playing the game.
     */
    protected int createPlayers(int gameID, int userID, int numPlayers) {
        HashMap<String, HashMap<String, Integer>> terNeigh = createTerritories();
        String[] ts = {"Michigan", "Michigan State", "Indiana", "Ohio State", "Kentucky", "WVU",
                "Northwestern", "Illinois", "Alabama", "Auburn", "Tennessee", "UVA", "Florida",
                "Georgia", "UNC", "South Carolina", "Duke", "NC State", "Maryland", "Villanova",
                "UPenn", "UConn", "Syracuse", "Penn State"};
        HashMap<String, Integer> terrKeys = new HashMap<>();
        Integer userToAdd = userID;
        int firstPlayerId = -1;
        String[] playerNames = {"Red", "Blue", "Green", "Yellow"};
        for (int i = 0; i < numPlayers; i++) {
            int playerID = playerRepository.save(new Player(gameID, userToAdd,
                    playerNames[i])).getId();
            if (i == 0) {
                firstPlayerId = playerID;
            }
            int start = i * 24 / numPlayers;
            int end = (i + 1) * 24 / numPlayers;
            addTerritories(ts, gameID, playerID, start, end, terrKeys);
            userToAdd = null;
        }
        addTerritoryNeighbors(terNeigh, terrKeys);
        return firstPlayerId;
    }

    /**
     * Add territories to repository. Add territory generation and save territory key.
     *
     * @param territories Array of all territories.
     * @param playerID    Unique player id.
     * @param start       The start index to add.
     * @param end         The end index to add.
     * @param keys        Territory keys.
     */
    protected void addTerritories(String[] territories, int gameID, int playerID, int start, int end,
                                  HashMap<String, Integer> keys) {
        int foodGeneration;
        int techGeneration;
        for (int i = start; i < end; i++) {
            if (i % 2 == 0) {
                foodGeneration = 10;
                techGeneration = 0;
            } else {
                foodGeneration = 0;
                techGeneration = 10;
            }
            int terID = territoryRepository.save(new Territory(playerID, gameID, territories[i], foodGeneration, techGeneration)).getId();
            keys.put(territories[i], terID);
        }
    }

    /**
     * Function that creates representation (in String and int) of map distances/adjacencies.
     *
     * @return HashMap representing all territories, their neighbors, and the distances to those
     * neighbors.
     */
    public HashMap<String, HashMap<String, Integer>> createTerritories() {
        HashMap<String, HashMap<String, Integer>> territoryNeighbors = new HashMap<String, HashMap<String, Integer>>();
        territoryHelper("Michigan", new String[]{"Michigan State", "Indiana", "Northwestern"},
                new Integer[]{1, 4, 3}, territoryNeighbors);
        territoryHelper("Michigan State", new String[]{"Michigan", "Ohio State", "Syracuse"},
                new Integer[]{1, 3, 8}, territoryNeighbors);
        territoryHelper("Indiana", new String[]{"Michigan", "Ohio State", "Kentucky", "Northwestern"},
                new Integer[]{4, 3, 2, 3}, territoryNeighbors);
        territoryHelper("Ohio State", new String[]{"Michigan State", "Penn State", "WVU", "Indiana"},
                new Integer[]{3, 2, 3, 3}, territoryNeighbors);
        territoryHelper("Kentucky", new String[]{"Indiana", "WVU", "Illinois"},
                new Integer[]{2, 3, 5}, territoryNeighbors);
        territoryHelper("WVU", new String[]{"Kentucky", "Ohio State", "Villanova", "Tennessee"},
                new Integer[]{3, 3, 5, 4}, territoryNeighbors);
        territoryHelper("Northwestern", new String[]{"Michigan", "Indiana", "Illinois"},
                new Integer[]{3, 3, 2}, territoryNeighbors);
        territoryHelper("Illinois", new String[]{"Northwestern", "Kentucky", "Alabama"},
                new Integer[]{2, 5, 7}, territoryNeighbors);
        territoryHelper("Alabama", new String[]{"Illinois", "Auburn"},
                new Integer[]{7, 1}, territoryNeighbors);
        territoryHelper("Auburn", new String[]{"Alabama", "Tennessee", "Florida", "Georgia"},
                new Integer[]{1, 8, 6, 6}, territoryNeighbors);
        territoryHelper("Tennessee", new String[]{"WVU", "UVA", "Auburn"},
                new Integer[]{4, 5, 8}, territoryNeighbors);
        territoryHelper("UVA", new String[]{"Maryland", "Duke", "Tennessee"},
                new Integer[]{3, 2, 5}, territoryNeighbors);
        territoryHelper("Florida", new String[]{"Auburn", "Georgia"},
                new Integer[]{6, 3}, territoryNeighbors);
        territoryHelper("Georgia", new String[]{"UNC", "South Carolina", "Florida", "Auburn"},
                new Integer[]{7, 3, 3, 6}, territoryNeighbors);
        territoryHelper("UNC", new String[]{"Duke", "NC State", "South Carolina", "Georgia"},
                new Integer[]{1, 2, 4, 7}, territoryNeighbors);
        territoryHelper("South Carolina", new String[]{"NC State", "Georgia", "UNC"},
                new Integer[]{3, 3, 4}, territoryNeighbors);
        territoryHelper("Duke", new String[]{"UVA", "NC State", "UNC"},
                new Integer[]{2, 1, 1}, territoryNeighbors);
        territoryHelper("NC State", new String[]{"Maryland", "South Carolina", "UNC", "Duke"},
                new Integer[]{6, 3, 2, 1}, territoryNeighbors);
        territoryHelper("Maryland", new String[]{"Villanova", "NC State", "UVA"},
                new Integer[]{2, 6, 3}, territoryNeighbors);
        territoryHelper("Villanova", new String[]{"UPenn", "Maryland", "WVU"},
                new Integer[]{1, 2, 5}, territoryNeighbors);
        territoryHelper("UPenn", new String[]{"UConn", "Villanova", "Penn State"},
                new Integer[]{6, 1, 3}, territoryNeighbors);
        territoryHelper("UConn", new String[]{"Syracuse", "UPenn"},
                new Integer[]{4, 6}, territoryNeighbors);
        territoryHelper("Syracuse", new String[]{"UConn", "Penn State", "Michigan State"},
                new Integer[]{4, 4, 8}, territoryNeighbors);
        territoryHelper("Penn State", new String[]{"Syracuse", "UPenn", "Ohio State"},
                new Integer[]{4, 3, 2}, territoryNeighbors);
        return territoryNeighbors;
    }

    /**
     * Adds to neighbors hashmap.
     *
     * @param t          is the territory to add neighbors.
     * @param neighbors  is the neighbors to add.
     * @param distances  is the array of distances to neighbors.
     * @param tNeighbors is the HashMap to add to.
     */
    private void territoryHelper(String t, String[] neighbors, Integer[] distances,
                                 HashMap<String, HashMap<String, Integer>> tNeighbors) {
        HashMap<String, Integer> hashNeighbors = new HashMap<>();
        for (int i = 0; i < neighbors.length; i++) {
            hashNeighbors.put(neighbors[i], distances[i]);
        }
        tNeighbors.put(t, hashNeighbors);
    }

    /**
     * Adds to the territory neighbor repository.
     *
     * @param terNeigh is the hashmap containing territories, their neighbors, and distances.
     * @param keys     is the hashmap containing territory keys.
     */
    protected void addTerritoryNeighbors(HashMap<String, HashMap<String, Integer>> terNeigh,
                                         HashMap<String, Integer> keys) {
        for (String t : terNeigh.keySet()) {
            Integer tID = keys.get(t);
            HashMap<String, Integer> neighbors = terNeigh.get(t);
            for (String n : neighbors.keySet()) {
                int nID = keys.get(n);
                int distance = neighbors.get(n);
                neighborRepository.save(new Neighbor(tID, nID, distance));
            }
        }
    }
}

