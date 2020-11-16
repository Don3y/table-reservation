package hu.elte.reservationtrackerrest.repositories;

import hu.elte.reservationtrackerrest.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
