package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    List<Player> findByGameIdAndUserId(int gameId, Integer userId);

    Player findByIdAndGameId(int id, int gameId);

    List<Player> findByUserId(Integer userId);

    List<Player> findByGameId(int gameId);
}
