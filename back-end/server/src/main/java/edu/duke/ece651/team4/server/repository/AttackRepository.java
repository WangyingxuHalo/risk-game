package edu.duke.ece651.team4.server.repository;

import edu.duke.ece651.team4.server.entity.Attack;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttackRepository extends CrudRepository<Attack, Integer> {

    List<Attack> findByOrderId(int orderId);
}
