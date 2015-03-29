package com.pcsma.project.places;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;


public class GooglePlaces
{
	public static String api_key="AIzaSyDteSkjlWPKZLRGJsYa0KRj7DuTk6xWaCg";
	private ArrayList<Places> google_places_Array=new ArrayList<>();

	public void getNearbyPlaces(String placesURL)
	{
			//fetch places
			//build result as string
			StringBuilder placesBuilder = new StringBuilder();
			//process search parameter string(s)
					HttpClient placesClient = new DefaultHttpClient();
					try 
					{
							//try to fetch the data
							
							//HTTP Get receives URL string
							HttpGet placesGet = new HttpGet(placesURL);
							//execute GET with Client - return response
							HttpResponse placesResponse = placesClient.execute(placesGet);
							//check response status
							StatusLine placeSearchStatus = placesResponse.getStatusLine();
							//only carry on if response is OK
							if (placeSearchStatus.getStatusCode() == 200) 
							{
									//get response entity
									HttpEntity placesEntity = placesResponse.getEntity();
									//get input stream setup
									InputStream placesContent = placesEntity.getContent();
									//create reader
									InputStreamReader placesInput = new InputStreamReader(placesContent);
									//use buffered reader to process
									BufferedReader placesReader = new BufferedReader(placesInput);
									//read a line at a time, append to string builder
									String lineIn;
									while ((lineIn = placesReader.readLine()) != null) 
									{
										placesBuilder.append(lineIn);
									}
							}
							//create JSONObject, pass stinrg returned from doInBackground
							JSONObject resultObject = new JSONObject(placesBuilder.toString());
							//get "results" array
							JSONArray placesArray = resultObject.getJSONArray("results");
//							Log.d("places",placesArray.toString());
							for (int p=0; p<placesArray.length(); p++) 
							{
								Places pl=new Places();
								boolean missingValue=false;
								String placeName="";
								String vicinity="";
								String rating;
								try
								{
									
									//attempt to retrieve place data values
									missingValue=false;
									//get place at this index
									JSONObject placeObject = placesArray.getJSONObject(p);
									//get location section
									JSONObject loc = placeObject.getJSONObject("geometry")
											.getJSONObject("location");
									
									//get types
									JSONArray types = placeObject.getJSONArray("types");
									ArrayList<String> place_types=new ArrayList<String>();
									for(int i=0;i<types.length();i++)
									{
										String x=types.getString(i);
										if(!x.isEmpty())
											place_types.add(x);
									}
							
									vicinity = placeObject.getString("vicinity");
									try
									{
										rating=placeObject.getString("rating");
									}
									catch(Exception e)
									{
										rating="N/A";
									}
									placeName = placeObject.getString("name");
									pl.setName(placeName);
									pl.setVicinity(vicinity);
									pl.setTypes(place_types);
									pl.setRating(rating);
								}
								catch(JSONException jse)
								{
									missingValue=true;
									jse.printStackTrace();
								}
								if(missingValue==false)
									google_places_Array.add(pl);
							}
					}
					catch(Exception e)
					{ 
						e.printStackTrace(); 
					}
			
	}
	
	public ArrayList<Places> getGoogle_places_Array() 
	{
		return google_places_Array;
	}

	public void setGoogle_places_Array(ArrayList<Places> google_places_Array) 
	{
		this.google_places_Array = google_places_Array;
	}
}