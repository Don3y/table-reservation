package hu.elte.reservationtrackerrest.repositories;

import hu.elte.reservationtrackerrest.entities.Reservation;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    List<Reservation> findByUser_id(Integer id);
    
}
