package edu.duke.ece651.team4.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Application class that launches the game server
 */
@SpringBootApplication
public class Application {

    /**
     * The main method that starts the spring application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
