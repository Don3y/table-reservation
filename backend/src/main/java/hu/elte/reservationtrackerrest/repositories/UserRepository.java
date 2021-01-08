package hu.elte.reservationtrackerrest.repositories;

import hu.elte.reservationtrackerrest.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
