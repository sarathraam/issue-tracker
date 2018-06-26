package au.com.domain.demo.tracker.repository;

import au.com.domain.demo.actor.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

}
