package edu.duke.ece651.team4.server.controller;

import edu.duke.ece651.team4.server.model.*;
import edu.duke.ece651.team4.server.service.ActionService;
import edu.duke.ece651.team4.server.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * The Game controller to accept game related requests
 */
@RestController
@CrossOrigin
public class GameController {

    /**
     * Game service
     */
    @Autowired
    private GameService gameService;

    @Autowired
    private ActionService actionService;

    /**
     * method for new game creation
     *
     * @param userId     is the user id
     * @param numPlayers is the number of players
     * @return ResponseEntity with json data
     */
    @PostMapping("/createGame/{userId}/{numPlayers}")
    @ResponseBody
    public ResponseEntity<ResGame> createGame(@PathVariable int userId, @PathVariable int numPlayers) {
        ResGame resGame = gameService.createGame(userId, numPlayers);
        return new ResponseEntity<>(resGame, HttpStatus.OK);
    }

    /**
     * method to join new game
     *
     * @param userId is the user id
     * @param gameId is the game id
     * @return ResponseEntity with json data
     */
    @PostMapping("/joinGame/{userId}/{gameId}")
    @ResponseBody
    public ResponseEntity<ResJoinGame> joinGame(@PathVariable int userId, @PathVariable int gameId) {
        ResJoinGame resJoinGame = gameService.joinGame(userId, gameId);
        return new ResponseEntity<>(resJoinGame, HttpStatus.OK);
    }

    /**
     * method to check if the player can start the game
     *
     * @param gameId is the game id
     * @return ResponseEntity with json data
     */
    @GetMapping("/isGameReady/{gameId}")
    @ResponseBody
    public ResponseEntity<Boolean> isGameReady(@PathVariable int gameId) {
        return new ResponseEntity<>(gameService.isGameReady(gameId), HttpStatus.OK);
    }

    /**
     * method to resume a game
     *
     * @param gameId   is the game id
     * @param playerId is the player id
     * @return ResponseEntity with json data
     */
    @PostMapping("/resumeGame/{gameId}/{playerId}")
    @ResponseBody
    public ResponseEntity resumeGame(@PathVariable int gameId, @PathVariable int playerId) {
        if (gameService.resumeGame(gameId, playerId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * method to send all games the user is part of
     *
     * @param userId is the user id
     * @return ResponseEntity with json data
     */
    @GetMapping("/games/{userId}")
    @ResponseBody
    public ResponseEntity<ResGames> getGames(@PathVariable int userId) {
        ResGames games = gameService.getGames(userId);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    /**
     * method to send game map
     *
     * @param playerId is the player id
     * @return ResponseEntity with json data
     */
    @GetMapping("/gameMap/{playerId}")
    @ResponseBody
    public ResponseEntity<ResGameMap> getGameMap(@PathVariable int playerId) {
        ResGameMap gameMap = gameService.getGameMap(playerId);
        return new ResponseEntity<>(gameMap, HttpStatus.OK);
    }

    /**
     * method to receive a player's turn
     *
     * @param onePlayerTurn is the player's turn
     * @return ResponseEntity with json data
     */
    @PostMapping("/done")
    @ResponseBody
    public ResponseEntity<Map<String, String>> conductTurn(@RequestBody OnePlayerTurn onePlayerTurn) {
        String response = actionService.conductTurn(onePlayerTurn);
        return new ResponseEntity<>(Collections.singletonMap("result", response), HttpStatus.OK);
    }

    /**
     * method to send game turn number
     *
     * @param gameId is the game's id
     * @return ResponseEntity with json data
     */
    @GetMapping("/getTurn/{gameId}")
    @ResponseBody
    public ResponseEntity<ResGetTurn> getTurn(@PathVariable int gameId) {
        ResGetTurn resGetTurn = gameService.getTurn(gameId);
        return new ResponseEntity<>(resGetTurn, HttpStatus.OK);
    }

    /**
     * method to send all info about the game
     *
     * @param playerId is the player's id
     * @return ResponseEntity with json data
     */
    @GetMapping("/allInfo/{playerId}")
    @ResponseBody
    public ResponseEntity<ResAllInfo> getAllInfo(@PathVariable int playerId) {
        ResAllInfo resAllInfo = gameService.getAllInfo(playerId);
        return new ResponseEntity<>(resAllInfo, HttpStatus.OK);
    }

    /**
     * method to play a bonus turn
     *
     * @param bonus is the bonus turn
     * @return ResponseEntity with json data
     */
    @PostMapping("/bonus")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> playBonus(@RequestBody Bonus bonus) {
        Integer response = gameService.playBonus(bonus);
        return new ResponseEntity<>(Collections.singletonMap("result", response), HttpStatus.OK);
    }

}
