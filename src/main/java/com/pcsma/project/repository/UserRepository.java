package com.pcsma.project.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pcsma.project.classes.User;

/**
 * An interface for a repository that can store Category
 * objects.
 * 
 * @author jules
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long>
{

	
}

