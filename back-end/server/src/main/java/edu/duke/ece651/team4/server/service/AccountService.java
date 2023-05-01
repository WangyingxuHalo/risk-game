package edu.duke.ece651.team4.server.service;

import edu.duke.ece651.team4.server.entity.GameUser;
import edu.duke.ece651.team4.server.repository.GameUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The AccountService class that allows user login and registration
 */
@Service
public class AccountService {

    /**
     * GameUser repository
     */
    @Autowired
    private GameUserRepository gameUserRepository;

    /**
     * method to attempt user login
     *
     * @param gameUser has the user login info
     * @return int which is the userId
     * @throws IllegalArgumentException is credentials are invalid
     */
    public int attemptLogin(GameUser gameUser) {
        GameUser userOnRecord = gameUserRepository.findByUsername(gameUser.getUsername());
        if (userOnRecord != null && userOnRecord.getPassword().equals(gameUser.getPassword())) {
            return userOnRecord.getId();
        }
        throw new IllegalArgumentException("Login credentials are invalid!");
    }

    /**
     * method to attempt user registration
     *
     * @param gameUser has the user login info
     * @return int which is the userId
     * @throws IllegalArgumentException is user already exists
     */
    public int attemptRegistration(GameUser gameUser) {
        GameUser userOnRecord = gameUserRepository.findByUsername(gameUser.getUsername());
        if (userOnRecord != null) {
            throw new IllegalArgumentException(gameUser.getUsername() + " user already exists!");
        }
        return gameUserRepository.save(new GameUser(gameUser.getUsername(), gameUser.getPassword())).getId();
    }
}
