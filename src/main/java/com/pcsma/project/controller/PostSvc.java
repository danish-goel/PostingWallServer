package com.pcsma.project.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import retrofit.http.POST;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.common.collect.Lists;
import com.pcsma.project.classes.Constants;
import com.pcsma.project.classes.Location;
import com.pcsma.project.classes.Post;
import com.pcsma.project.classes.User;
import com.pcsma.project.client.PostSvcApi;
import com.pcsma.project.places.GooglePlaces;
import com.pcsma.project.places.Places;
import com.pcsma.project.repository.LocationRepository;
import com.pcsma.project.repository.PostRepository;
import com.pcsma.project.repository.UserRepository;


// Tell Spring that this class is a Controller that should 
// handle certain HTTP requests for the DispatcherServlet
@Controller
public class PostSvc implements PostSvcApi 
{

	@Autowired
	private PostRepository posts;
	
	@Autowired
	private UserRepository users;
	
	@Autowired
	private LocationRepository locations;

	
	/*----------------------------------------------------------------------------*/
	
	@RequestMapping(value=PostSvcApi.POST_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addPost(@RequestBody Post v)
	{
		 posts.save(v);
		 
		 Sender sender = new Sender("AIzaSyBjfVfYpTn-DuWlauYMYozC6vKEu0pKIRk");
         Message message = new Message.Builder()
         .collapseKey("1")
         .addData("message",v.getContent())
         .build();
         
         List<User> users_list=Lists.newArrayList(users.findAll());
         List<String> user_regids=new ArrayList<String>();
         
         for(User u:users_list)
         {
        	 if(u.getEmail().equals(v.getUser().getEmail()))
        	 {
        		 
        	 }
        	 else
        	 {
        		 Collection<Location> list=u.getLocations();
        		 if(list.isEmpty())
        		 {
        			 
        		 }
        		 else
        		 {
        			 for(Location l:list)
        			 {
        				 if(Constants.calculateDistance(v.getLatitude(), v.getLongitude(),l.getLatitude(), l.getLongitude())<3)
        				 {
        					 user_regids.add(u.getGcm_regid());
        				 }
        			 }
        			 
        		 }
        	 }
         }

//         try 
//         {
//        	Result result = sender.send(message,v.getUser().getGcm_regid()+"", 5);
//         }
//         catch (IOException e) 
//         {
//			e.printStackTrace();
//         }
         
         try {

        	 if(!user_regids.isEmpty())
        	 {
		         MulticastResult result = sender.send(message,user_regids, 1);
		         System.out.println("Response: " + result.getResults().toString());
		         if (result.getResults() != null) {
		
		             int canonicalRegId = result.getCanonicalIds();
		             if (canonicalRegId != 0) {
		                 System.out.println("response " +canonicalRegId );
		             }
		         } else {
		             int error = result.getFailure();
		             System.out.println("Broadcast failure: " + error);
		         }
        	 }

         } catch (Exception e) {
             e.printStackTrace();
         }
//         
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
	public @ResponseBody List<Post> getNearbyPostList(@RequestParam("latitude") Double lat,@RequestParam("longitude") Double longi,@RequestParam("radius") Float radius) 
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
				if(distance<radius)
				{
					nearbyPosts.add(post);
				}
			}


		}
		return nearbyPosts;
	}

	@Override
	@RequestMapping(value=PostSvcApi.NEARBY_PLACES_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Places> getNearbyPlacesList(@RequestParam("latitude") Double lat,@RequestParam("longitude") Double longi,@RequestParam("radius") Float radius) 
	{
		String Placesradius=String.valueOf(radius*1000);
		try
		{
			String placesSearchStr ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
	        +URLEncoder.encode(String.valueOf(lat), "UTF-8")
	        +","
	        +URLEncoder.encode(String.valueOf(longi), "UTF-8")
	        +"&radius="
			+URLEncoder.encode(Placesradius, "UTF-8")
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

	@Override
	@RequestMapping(value=PostSvcApi.USERS_POST_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Post> getPostsForUser(@RequestParam("user") String userEmail)
	
	{
		System.out.println(userEmail);
		User u =users.findOne(userEmail);
		if(u!=null)
		{
			return Lists.newArrayList(u.getPosts());
		}
		else
		{
			return null;
		}
	}
	
	@Override
	@RequestMapping(value=PostSvcApi.USER_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addUser(@RequestBody User u)
	{
		User k=users.save(u);
		System.out.println(k.getEmail());
		return true;
	}

	@Override
	@RequestMapping(value=PostSvcApi.USER_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody Collection<User> getAllUsers()
	{
		return Lists.newArrayList(users.findAll());
	}

	@Override
	@RequestMapping(value=PostSvcApi.POST_SVC_PATH, method=RequestMethod.DELETE)
	public @ResponseBody boolean deletePost(@RequestParam("id") Long id)
	{
		posts.delete(id);
		return false;
	}

	@Override
	@RequestMapping(value=PostSvcApi.LOCATION_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addLocation(@RequestBody Location l) 
	{
		locations.save(l);
		return false;
	}

	@Override
	@RequestMapping(value=PostSvcApi.LOCATION_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Location> getAllLocations() 
	{
		return Lists.newArrayList(locations.findAll());
	}

	@Override
	@RequestMapping(value=PostSvcApi.USERS_LOCATION_PATH, method=RequestMethod.GET)
	public @ResponseBody List<Location> getLocationsForUser(@RequestParam("user") String userEmail) 
	{
		User u =users.findOne(userEmail);
		if(u!=null)
		{
			return Lists.newArrayList(u.getLocations());
		}
		else
		{
			return null;
		}
	}

	@Override
	@RequestMapping(value=PostSvcApi.USER_TITLE_SEARCH_PATH, method=RequestMethod.GET)
	public @ResponseBody User findUserByEmail(@RequestParam("user") String userEmail) 
	{
		return users.findOne(userEmail);
	}

	@Override
	@RequestMapping(value=PostSvcApi.COMMENT_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addComment(@RequestParam("index") long id,@RequestParam("comment") String comment) 
	{
		for(Post p:posts.findAll())
		{
			if(p.getId()==id)
			{
				p.addComment(comment);
				posts.save(p);
				return true;
			}
		}
		return true;
	}

	@Override
	@RequestMapping(value=PostSvcApi.COMMENT_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody ArrayList<String> getComments(@RequestParam("index") long id) 
	{
		for(Post p:posts.findAll())
		{
			if(p.getId()==id)
			{
				return p.getComments();
			}
		}
		return null;
	}

	@Override
	@RequestMapping(value=PostSvcApi.LIKE_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addLike(@RequestParam("index") long id) 
	{
		for(Post p:posts.findAll())
		{
			if(p.getId()==id)
			{
				p.setLikeNo(p.getLikeNo()+1);
				return true;
			}
		}
		return false;
	}

	@Override
	@RequestMapping(value=PostSvcApi.DISLIKE_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean addDisLike(@RequestParam("index") long id) 
	{
		for(Post p:posts.findAll())
		{
			if(p.getId()==id)
			{
				p.setLikeNo(p.getLikeNo()-1);
				return true;
			}
		}
		return false;
	}
	
}
