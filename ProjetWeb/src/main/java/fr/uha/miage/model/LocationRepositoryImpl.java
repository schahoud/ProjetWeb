package fr.uha.miage.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LocationRepositoryImpl {
	

	@Autowired
	public LocationRepository locationRep ; 
	
	
	public LocationRepositoryImpl() {
		super();
	} 
	
	public void ajoutULocation(Location loc){
		locationRep.save(loc);
	}
	
	public Location rechercheLocationById(Long id) {
		return locationRep.findOne(id); 
	}

	public void suppLocation() {
		locationRep.deleteAll();
	}
	
	public void suppLocationById(Long id) {
		locationRep.delete(id);
	}
	
	public long countLocation(){
		return locationRep.count(); 
	}
	
	public List<Location> printRepClient() 
	{
		return (List<Location>)locationRep.findAll() ; 
	}

}
