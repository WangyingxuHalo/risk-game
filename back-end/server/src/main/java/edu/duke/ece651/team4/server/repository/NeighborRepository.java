package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Neighbor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeighborRepository extends CrudRepository<Neighbor, Integer> {
    /**
     * Query neighbor.
     */
    List<Neighbor> findByNeighborId(int neighborId);

    List<Neighbor> findByTerritoryIdAndNeighborId(int territoryId, int neighborId);
}
