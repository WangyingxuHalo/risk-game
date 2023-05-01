package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.Season;
import edu.duke.ece651.team4.server.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service used to update the Season, if necessary.
 */
@Service
public class SeasonService {
    /**
     * The season repository to update.
     */
    @Autowired
    SeasonRepository seasonRepository;

    /**
     * Given turn number, update the season if necessary.
     *
     * @param gameId the GameId of the game to update the season for.
     * @param turnNo the turn number at the end of the current turn.
     */
    public void checkAndUpdateSeason(int gameId, int turnNo) {
        int remainder = turnNo % 12;
        if (remainder % 3 == 1) {
            changeSeason(remainder, gameId);
        }
    }

    /**
     * Changes the season, given the month number.
     *
     * @param remainder is the month number (i.e. turn Number / 12)
     * @param gameId    is the game to change the season for.
     */
    protected void changeSeason(int remainder, int gameId) {
        seasonRepository.deleteByGameId(gameId);
        if (remainder > 0 && remainder < 4) {
            Season fall = generateFall(gameId);
            seasonRepository.save(fall);
        } else if (remainder > 3 && remainder < 7) {
            Season winter = generateWinter(gameId);
            seasonRepository.save(winter);
        } else if (remainder > 6 && remainder < 10) {
            Season spring = generateSpring(gameId);
            seasonRepository.save(spring);
        } else {
            Season summer = generateSummer(gameId);
            seasonRepository.save(summer);
        }
    }

    /**
     * Generate a Summer season object.
     *
     * @param gameId the game to generate for.
     * @return the Season object representing Summer.
     */
    protected Season generateSummer(int gameId) {
        String message = "The monsoons wash away the bridges, and moving between territories is" +
                " much harder, it costs 2 times normal to move";
        Season summer = new Season(gameId, "Summer", message, 1,
                1, 1, 2);
        return summer;
    }

    /**
     * Generate a Spring season object.
     *
     * @param gameId the game to generate for.
     * @return the Season object representing Spring.
     */
    protected Season generateSpring(int gameId) {
        double foodAdjust = genAdjust(0, 15, true);
        String message = "The rain is very beneficial for crops, food resource generation occurs at " +
                foodAdjust + " times normal.";
        Season spring = new Season(gameId, "Spring", message, 1,
                foodAdjust, 1, 1);
        return spring;
    }

    /**
     * Generate a Winter season object.
     *
     * @param gameId the game to generate for.
     * @return the Season object representing Winter.
     */
    protected Season generateWinter(int gameId) {
        double foodAdjust = genAdjust(2, 6, false);
        String message = "Food resource generation is challenging, food generation occurs at " +
                foodAdjust + " times normal rate (same rate for the whole game). However, your " +
                "enemies are cozy by the fire, and attacking is 0.5 the normal price!";
        Season winter = new Season(gameId, "Winter", message, 1,
                foodAdjust, 0.5, 1);
        return winter;
    }
    /**
     * Generate a Fall season object.
     *
     * @param gameId the game to generate for.
     * @return the Season object representing Fall.
     */
    protected Season generateFall(int gameId) {
        double techAdjust = genAdjust(5, 10, true);
        String message = "The tech geniuses are hard at work, there are several innovations! " +
                "Tech generation occurs at " + techAdjust + " times normal.";
        Season fall = new Season(gameId, "Fall", message, techAdjust,
                1, 1, 1);
        return fall;
    }

    /**
     * Generate a change number.
     * @param minVal is the minimum change.
     * @param maxVal is the maximum change.
     * @param add is whether to add or subtract the change from 1.
     * @return the double that is the change.
     */
    protected double genAdjust(int minVal, int maxVal, boolean add) {
        int change = (int) (Math.random() * (maxVal - minVal + 1) + minVal);
        if (add) {
            return 1.0 + 0.1 * change;
        } else {
            return 1.0 - 0.1 * change;
        }
    }
}
