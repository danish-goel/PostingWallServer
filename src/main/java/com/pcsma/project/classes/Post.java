package com.pcsma.project.classes;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.common.base.Objects;

/**
 * A simple object to represent a video and its URL for viewing.
 * 
 * @author jules
 * 
 */
@Entity
public class Post 
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String title;
	private String content;
	private String poster;
	private Double latitude,longitude;
	private ArrayList<String> comments=new ArrayList<String>();
	private int likeNo;
	

	@ManyToOne
	private User user;

	public Post() 
	{
		
	}

	public Post(String title, String content,String poster,Double latitude,Double longitude) 
	{
		super();
		this.title=title;
		this.content=content;
		this.poster=poster;
		this.latitude=latitude;
		this.longitude=longitude;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
	

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ArrayList<String> getComments() {
		return comments;
	}

	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}

	public boolean addComment(String comment)
	{
		this.comments.add(comment);
		return true;
	}
	
	public int getLikeNo() {
		return likeNo;
	}

	public void setLikeNo(int likeNo) {
		this.likeNo = likeNo;
	}


	/**
	 * Two Videos will generate the same hashcode if they have exactly the same
	 * values for their name, url, and duration.
	 * 
	 */
	@Override
	public int hashCode() 
	{
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(title,content,poster,latitude,longitude);
	}

	/**
	 * Two Videos are considered equal if they have exactly the same values for
	 * their name, url, and duration.
	 * 
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (obj instanceof Post) 
		{
			Post other = (Post) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(title, other.title)
					&& Objects.equal(content, other.content)
					&& Objects.equal(poster, other.poster)
					;
		} 
		else
		{
			return false;
		}
	}

}
