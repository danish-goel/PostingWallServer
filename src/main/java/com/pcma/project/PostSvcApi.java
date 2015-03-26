package com.pcma.project;

import java.util.Collection;


import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * This interface defines an API for a VideoSvc. The
 * interface is used to provide a contract for client/server
 * interactions. The interface is annotated with Retrofit
 * annotations so that clients can automatically convert the
 * 
 * 
 * @author jules
 *
 */
public interface PostSvcApi {
	
	public static final String TITLE_PARAMETER = "title";

	// The path where we expect the VideoSvc to live
	public static final String POST_SVC_PATH = "/post";

	// The path to search videos by title
	public static final String POST_TITLE_SEARCH_PATH = POST_SVC_PATH + "/find";

	@GET(POST_SVC_PATH)
	public Collection<Post> getVideoList();
	
	@POST(POST_SVC_PATH)
	public boolean addVideo(@Body Post v);
	
	@GET(POST_TITLE_SEARCH_PATH)
	public Collection<Post> findByTitle(@Query(TITLE_PARAMETER) String title);
	
}