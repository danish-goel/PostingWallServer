package com.pcsma.project.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pcsma.project.classes.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>
{
	public Collection<Location> findByName(String name);
}

