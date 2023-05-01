package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.TerritoryView;
import edu.duke.ece651.team4.server.entity.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerritoryViewRepository extends CrudRepository<TerritoryView, Integer> {
    Optional<TerritoryView> findByTerritoryIdAndPlayerId(int territoryId, int playerId);
}
