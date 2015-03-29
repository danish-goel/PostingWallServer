package com.pcsma.project.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.pcsma.project.classes.Constants;
import com.pcsma.project.classes.Post;
import com.pcsma.project.client.PostSvcApi;
import com.pcsma.project.places.GooglePlaces;
import com.pcsma.project.places.Places;
import com.pcsma.project.repository.PostRepository;

/**
 * This simple VideoSvc allows clients to send HTTP POST requests with
 * videos that are stored in memory using a list. Clients can send HTTP GET
 * requests to receive a JSON listing of the videos that have been sent to
 * the controller so far. Stopping the controller will cause it to lose the history of
 * videos that have been sent to it because they are stored in memory.
 * 
 * Notice how much simpler this VideoSvc is than the original VideoServlet?
 * Spring allows us to dramatically simplify our service. Another important
 * aspect of this version is that we have defined a VideoSvcApi that provides
 * strong typing on both the client and service interface to ensure that we
 * don't send the wrong paraemters, etc.
 * 
 * @author jules
 *
 */

// Tell Spring that this class is a Controller that should 
// handle certain HTTP requests for the DispatcherServlet
@Controller
public class PostSvc implements PostSvcApi 
{

	@Autowired
	private PostRepository posts;

	
	/*----------------------------------------------------------------------------*/
	
	@RequestMapping(value=PostSvcApi.POST_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addPost(@RequestBody Post v)
	{
		 posts.save(v);
		 return true;
	}
	
	@RequestMapping(value=PostSvcApi.POST_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Post> getPostList()
	{
		return Lists.newArrayList(posts.findAll());
	}
	
	
	
	
	@RequestMapping(value=PostSvcApi.POST_TITLE_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<Post> findByTitle(
			// Tell Spring to use the "title" parameter in the HTTP request's query
			// string as the value for the title method parameter
			@RequestParam(TITLE_PARAMETER) String title
	)
	{
		return posts.findByTitle(title);
	}

	
	
	@Override
	@RequestMapping(value=PostSvcApi.NEARBY_POST_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Post> getNearbyPostList(@RequestParam("latitude") Double lat,@RequestParam("longitude") Double longi) 
	{
		List<Post> nearbyPosts=new ArrayList<Post>();
		Constants c1=new Constants();
		for(Post post:posts.findAll())
		{
			Double post_lat=post.getLatitude();
			Double post_longi=post.getLongitude();
			if(lat==null || longi==null|| post_lat==null|| post_longi==null)
			{
			}
			else
			{
				long distance=c1.calculateDistance(lat, longi,post_lat,post_longi);
				if(distance<3)
				{
					nearbyPosts.add(post);
				}
			}


		}
		return nearbyPosts;
	}

	@Override
	@RequestMapping(value=PostSvcApi.NEARBY_PLACES_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Places> getNearbyPlacesList(@RequestParam("latitude") Double lat,@RequestParam("longitude") Double longi) 
	{
		try
		{
			String placesSearchStr ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
	        +URLEncoder.encode(String.valueOf(lat), "UTF-8")
	        +","
	        +URLEncoder.encode(String.valueOf(longi), "UTF-8")
	        +"&radius="
			+URLEncoder.encode("2000", "UTF-8")
			+"&sensor="
			+URLEncoder.encode("true", "UTF-8")
			+"&types="
			+URLEncoder.encode("food|movie_theater|restaurant", "UTF-8")
			+"&key="
			+URLEncoder.encode(GooglePlaces.api_key, "UTF-8");
			List<Places> placesList=new ArrayList<Places>();
			GooglePlaces gp=new GooglePlaces();
			placesList=gp.getNearbyPlaces(placesSearchStr);
			return placesList;
		}
		catch (UnsupportedEncodingException e)
		{
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        return null;
		}
		
	}


}
