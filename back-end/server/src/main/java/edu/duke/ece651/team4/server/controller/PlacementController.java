package edu.duke.ece651.team4.server.controller;

import edu.duke.ece651.team4.server.model.UserPlacement;
import edu.duke.ece651.team4.server.service.PlacementService;
import edu.duke.ece651.team4.server.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The Placement controller to perform units placement
 */
@RestController
@CrossOrigin
public class PlacementController {

    /**
     * Placement service
     */
    @Autowired
    private PlacementService placementService;

    @Autowired
    private ViewService viewService;

    /**
     * method for units placement for a player
     *
     * @param userPlacement is the units placement
     * @return ResponseEntity with response OK
     */
    @PostMapping("/placement")
    @ResponseBody
    public ResponseEntity doUnitsPlacement(@RequestBody UserPlacement userPlacement) {
        placementService.doUnitsPlacement(userPlacement);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * method to check if the placement phase is complete
     *
     * @param gameId is the game id
     * @return ResponseEntity with json data
     */
    @GetMapping("/isPlacementDone/{gameId}")
    @ResponseBody
    public ResponseEntity<Boolean> isPlacementDone(@PathVariable int gameId) {
        boolean done = placementService.isPlacementDone(gameId);
        if (done) {
            viewService.updateAllViews(gameId);
        }
        return new ResponseEntity<>(done, HttpStatus.OK);
    }

}
