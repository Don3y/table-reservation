package hu.elte.reservationtrackerrest.repositories;

import hu.elte.reservationtrackerrest.entities.Label;
import org.springframework.data.repository.CrudRepository;

public interface LabelRepository extends CrudRepository<Label, Integer> {
    
}
