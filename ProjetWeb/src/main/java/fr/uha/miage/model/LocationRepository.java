package fr.uha.miage.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository  extends CrudRepository<Location, Long>{
	
    List<Location> findByTitre(String titre); 
}
