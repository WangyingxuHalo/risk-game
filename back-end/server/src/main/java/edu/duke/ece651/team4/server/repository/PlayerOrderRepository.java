package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.PlayerOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PlayerOrderRepository extends CrudRepository<PlayerOrder, Integer> {

    List<PlayerOrder> findByGameID(int gameId);
    @Transactional
    long deleteByGameID(int gameId);
}
