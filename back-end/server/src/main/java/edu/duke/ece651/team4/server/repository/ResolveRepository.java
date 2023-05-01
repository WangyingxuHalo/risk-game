package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Resolve;
import edu.duke.ece651.team4.server.entity.Territory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResolveRepository extends CrudRepository<Resolve, Integer> {
    Optional<Resolve> findByAttackedTerritoryId(int attackedTerritoryId);
    List<Resolve> findByGameId(int gameId);
}
