package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Unit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Integer> {
    /**
     * Select all units by territory ID.
     */
    @Transactional
    List<Unit> findByTerritoryId(int territoryId);
    Unit findByTerritoryIdAndType(int territoryID, int type);
}
