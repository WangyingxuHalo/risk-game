package edu.duke.ece651.team4.server.controller;

import edu.duke.ece651.team4.server.entity.GameUser;
import edu.duke.ece651.team4.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * The Account controller to accept user account related requests
 */
@RestController
@CrossOrigin
public class AccountController {

    /**
     * Account service
     */
    @Autowired
    private AccountService accountService;

    /**
     * method for user login
     *
     * @param gameUser has the user login info
     * @return ResponseEntity with json data
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody GameUser gameUser) {
        try {
            int userId = accountService.attemptLogin(gameUser);
            return new ResponseEntity<>(Collections.singletonMap("userId", userId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * method for user registration
     *
     * @param gameUser has the user registration info
     * @return ResponseEntity with json data
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity register(@RequestBody GameUser gameUser) {
        try {
            int userId = accountService.attemptRegistration(gameUser);
            return new ResponseEntity<>(Collections.singletonMap("userId", userId), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.CONFLICT);
        }
    }

}
