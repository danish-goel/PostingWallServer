package com.pcsma.project.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pcsma.project.classes.Post;

/**
 * An interface for a repository that can store Video
 * objects and allow them to be searched by title.
 * 
 * @author jules
 *
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long>{

	// Find all videos with a matching title (e.g., Video.name)
	public Collection<Post> findByTitle(String title);
	
}
