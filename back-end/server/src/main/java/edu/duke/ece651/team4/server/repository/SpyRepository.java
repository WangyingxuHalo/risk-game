package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Spy;
import edu.duke.ece651.team4.server.entity.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SpyRepository extends CrudRepository<Spy, Integer> {
    List<Spy> findByPlayerIdAndTerritoryId(int playerId, int territoryId);

    List<Spy> findByPlayerId(int playerId);
}
