package edu.duke.ece651.team4.server.model;

import edu.duke.ece651.team4.server.entity.Unit;

import java.util.*;

/**
 * Check that all moves are valid.
 */
public class ResearchChecker extends ActionChecker {
    public ResearchChecker(ActionChecker next) {
        super(next);
    }

    /**
     * Checks that the player have enough food to upgrade.
     *
     * @param units         is the units in each territories.
     * @param neighbors     is the adjacency list.
     * @param turn          is the turn object representing the player moves.
     * @param playerResources resources currently available.
     * @return
     */
    @Override
    protected void checkMyRule(HashMap<String, List<Unit>> units,
                              HashMap<String, HashMap<String, Integer>> neighbors,
                              OnePlayerTurn turn,
                              Resources playerResources) {
    
    if(turn.getResearch()==false){
        return;
    }
    int[] researchCost ={20,40,80,160,320};
   if(turn.getTechLevel()>=6){
    throw new IllegalArgumentException("You already research the highest technology level.");

   }
      playerResources.consumeTechnology(researchCost[turn.getTechLevel()-1]);
        }
    }


