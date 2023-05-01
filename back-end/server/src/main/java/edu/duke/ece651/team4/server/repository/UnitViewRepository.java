package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.UnitView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitViewRepository extends CrudRepository<UnitView, Integer> {

    List<UnitView> findByTerritoryViewId(int territoryViewId);

    UnitView findByTerritoryViewIdAndType(int territoryViewId, int type);
}
