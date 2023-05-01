package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {
}
