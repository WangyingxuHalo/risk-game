package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Season;
import edu.duke.ece651.team4.server.entity.UnitView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SeasonRepository extends CrudRepository<Season, Integer> {
    @Transactional
    long deleteByGameId(int gameId);

    /**
     * Find a season by the game.
     * @param gameId
     * @return the Season for that game.
     */
    Season findByGameId(int gameId);
}
