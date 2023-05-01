package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.ResolveDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ResolveDetailRepository extends CrudRepository<ResolveDetail, Integer> {
    @Transactional
    long deleteByResolveId(int resolveId);

    List<ResolveDetail> findByResolveId(int resolveId);

}
