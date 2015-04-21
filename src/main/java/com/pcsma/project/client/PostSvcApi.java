package com.pcsma.project.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcsma.project.classes.Location;
import com.pcsma.project.classes.Post;
import com.pcsma.project.classes.User;
import com.pcsma.project.places.Places;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface PostSvcApi 
{
	/*------------------------------------------------------------------*/
	public static final String TITLE_PARAMETER = "title";
	public static final String POST_SVC_PATH = "/post";
	public static final String NEARBY_POST_PATH = "/nearbyposts";
	public static final String NEARBY_PLACES_PATH = "/nearbyplaces";
	public static final String POST_TITLE_SEARCH_PATH = POST_SVC_PATH + "/find";
	public static final String USER_SVC_PATH="/user";
	public static final String USERS_POST_PATH="/userspost";
	public static final String USER_TITLE_SEARCH_PATH=USER_SVC_PATH + "/find";
	public static final String USER_ID_PARAMETER = "id";
	public static final String LOCATION_SVC_PATH="/location";
	public static final String USERS_LOCATION_PATH="/userslocation";
	
	public static final String COMMENT_SVC_PATH="/comments";
	
	public static final String LIKE_SVC_PATH="/like";
	public static final String DISLIKE_SVC_PATH="/dislike";
	/*-----------------------------------------------------------------*/
	@GET(POST_SVC_PATH)
	public Collection<Post> getPostList();
	
	@POST(POST_SVC_PATH)
	public boolean addPost(@Body Post v);
	
	@DELETE(POST_SVC_PATH)
	public boolean deletePost(@Query("id") Long id);
	
	@GET(POST_TITLE_SEARCH_PATH)
	public Collection<Post> findByTitle(@Query(TITLE_PARAMETER) String title);
	
	@GET(USERS_POST_PATH)
	public List<Post> getPostsForUser(@Query("user") String userEmail);
	
	@GET(NEARBY_POST_PATH)
	public List<Post> getNearbyPostList(@Query("latitude") Double lat,@Query("longitude") Double longi,@Query("radius") Float radius);
	
	@GET(NEARBY_PLACES_PATH)
	public List<Places> getNearbyPlacesList(@Query("latitude") Double lat,@Query("longitude") Double longi,@Query("radius") Float radius);
	
	/*---------------------User-----------------------------------------------------------------------*/
	
	@POST(USER_SVC_PATH)
	public boolean addUser(@Body User u);
	
	@GET(USER_SVC_PATH)
	public Collection<User> getAllUsers();
	
	@GET(USER_TITLE_SEARCH_PATH)
	public User findUserByEmail(@Query("user") String userEmail);
	
	/*---------------------End User-----------------------------------------------------------------------*/
	
	
	
	/*---------------------Location-----------------------------------------------------------------------*/
	
	@POST(LOCATION_SVC_PATH)
	public boolean addLocation(@Body Location l);
	
	@GET(LOCATION_SVC_PATH)
	public List<Location> getAllLocations();
	
	@GET(USERS_LOCATION_PATH)
	public List<Location> getLocationsForUser(@Query("user") String userEmail);
	
	/*---------------------End Location-----------------------------------------------------------------------*/
	
	/*---------------------Post Comments-----------------------------------------------------------------------*/
	@POST(COMMENT_SVC_PATH)
	public boolean addComment(@RequestParam("index") long id,@RequestParam("comment") String comment);
	
	@GET(COMMENT_SVC_PATH)
	public ArrayList<String> getComments(@RequestParam("index") long id);
	
	/*---------------------Post Comments-----------------------------------------------------------------------*/
	
	/*---------------------Like & Dislike-----------------------------------------------------------------------*/
	@POST(LIKE_SVC_PATH)
	public boolean addLike(@RequestParam("index") long id);
	
	@POST(DISLIKE_SVC_PATH)
	public boolean addDisLike(@RequestParam("index") long id);
	
	/*---------------------Like & Dislike-----------------------------------------------------------------------*/
	
	
}
