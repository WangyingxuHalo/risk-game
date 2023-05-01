package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Territory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerritoryRepository extends CrudRepository<Territory, Integer> {
    /**
     * Get a territory by name (i.e. Duke) and the PlayerID of the owner.
     * @param name is the name of the territory (i.e. Duke).
     * @param ownerId is the PlayerID of the owning player.
     * @return the Territory requested.
     */
    Territory findByNameAndOwnerId(String name, int ownerId);

    Territory findByNameAndGameId(String name, int gameId);

    List<Territory> findByOwnerId(int ownerId);

    List<Territory> findByGameId(int gameId);
}
