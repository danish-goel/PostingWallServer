package com.pcsma.project.client;

import java.util.Collection;
import java.util.List;

import com.pcsma.project.classes.Post;
import com.pcsma.project.places.Places;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface PostSvcApi 
{
	/*------------------------------------------------------------------*/
	public static final String TITLE_PARAMETER = "title";
	public static final String POST_SVC_PATH = "/post";
	public static final String NEARBY_POST_PATH = "/nearbyposts";
	public static final String NEARBY_PLACES_PATH = "/nearbyplaces";
	public static final String POST_TITLE_SEARCH_PATH = POST_SVC_PATH + "/find";
	
	/*-----------------------------------------------------------------*/
	@GET(POST_SVC_PATH)
	public Collection<Post> getPostList();
	
	@POST(POST_SVC_PATH)
	public boolean addPost(@Body Post v);
	
	@GET(POST_TITLE_SEARCH_PATH)
	public Collection<Post> findByTitle(@Query(TITLE_PARAMETER) String title);
	
	@GET(NEARBY_POST_PATH)
	public List<Post> getNearbyPostList(@Query("latitude") Double lat,@Query("longitude") Double longi);
	
	@GET(NEARBY_PLACES_PATH)
	public List<Places> getNearbyPlacesList(@Query("latitude") Double lat,@Query("longitude") Double longi);
	
}
