package com.pcsma.project.classes;

public class Constants 
{
		public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
		
		public static long calculateDistance(double userLat, double userLng,double venueLat, double venueLng)
		{
		
		    double latDistance = Math.toRadians(userLat - venueLat);
		    double lngDistance = Math.toRadians(userLng - venueLng);
		
		    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
		      + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
		      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
		
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		
		    return (long) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));
		}

		
		public static String capitaliseFirstLetter(String string)
		{
			StringBuilder sb = new StringBuilder(string);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
			return sb.toString();
		}
}