package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.GameUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameUserRepository extends CrudRepository<GameUser, Integer> {

    GameUser findByUsername(String username);
}
