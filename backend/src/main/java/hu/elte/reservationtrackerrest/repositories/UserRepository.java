package hu.elte.reservationtrackerrest.repositories;

import hu.elte.reservationtrackerrest.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
   @Query(value="SELECT * FROM users u WHERE u.username = :username AND u.password = :password", nativeQuery = true)
    User findValidUser(@Param("username") String username, @Param("password") String password);
}
