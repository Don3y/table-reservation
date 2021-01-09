package hu.elte.reservationtrackerrest.repositories;

import hu.elte.reservationtrackerrest.entities.ResturantTable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResturantTableRepository extends CrudRepository<ResturantTable, Integer> {
          List<ResturantTable> findByStatus(ResturantTable.Status status);
          ResturantTable findByTablename(String name);
}
