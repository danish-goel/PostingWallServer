package com.pcsma.project.classes;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents a Category of a Video. Each Video can
 * be in exactly one Category (e.g., romance, action, horror, etc.).
 * This version of the class uses @OneToMany.
 * 
 * @author jules
 *
 */
@Entity
public class User 
{

	@Id
	private String email;

	private String name;

	@JsonIgnore
	@OneToMany(mappedBy="user")
	private Collection<Post> posts;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private Collection<Location> locations;

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	/*
	 * JPA will automatically retrieve all Videos that have a category
	 * that refers to the primary key of current category when you call
	 * this method. Since each category has a unique name, if you assign
	 * a video to a given category name, then calling getVideos() on the
	 * Category object that has that name will include that video. JPA
	 * automatically updates this list as Video objects are added that
	 * refer to existing Categories. There is no need to explicitly tell
	 * JPA to update the list of videos for a given Category when you 
	 * save a new Video.
	 * 
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Post> getPosts() 
	{
		return posts;
	}

	public void setPosts(Collection<Post> posts) 
	{
		this.posts = posts;
	}

	public Collection<Location> getLocations() {
		return locations;
	}

	public void setLocations(Collection<Location> locations) {
		this.locations = locations;
	}
	


}
