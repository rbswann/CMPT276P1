/*	External libraries required (if not using Volley):
 * 		json-20200518.jar: https://github.com/stleary/JSON-java
 * 		json-simple-1.1.1.jar: https://code.google.com/archive/p/json-simple/downloads
 */
package com.group04.studentaide;

import java.net.*;
import java.util.*;
import java.time.LocalDateTime;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;;

// These libraries are for the old functions, if Volley functions work, you can delete
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/* 	File Name: studyStatistics.java
 * 	Team: ProjectTeam4
 * 	Programmers: Jason Leung, 
 * 
 * 	Changes:
 * 		November 5th: Finalized version 1 draft of the function.
 * 
 * 	Bugs:
 * 		Have not been tested yet.
 * 
 * 	Notes (Delete this section for the presentation):
 * 		I kept the old version of the functions I wrote just in case the Volley functions did not work properly or as intended. If the
 * 		Volley functions do work, you can delete the old functions and replace it with the Volley functions. I also added comments and
 * 		notes throughout the code, some with important information if you guys can go through and make sure everything is alright, and
 * 		that I didn't leave anything out. From reading the design document for version 1, all this function needs to do is return time 
 * 		spent studying, and so that's all I have returned.
 * 		- Jason Leung
 */

public class studyStatistics {
	
	// Gets total time spent studying on all courses (With Volley)
	public static float study_statistics_volley(String GUID) throws Exception {
		
		String url = serverURL.ROOT_URL + GUID;
		float totalTimeStudying = 0;
		
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
			
			@Override
			public void onResponse(JSONObject response) {
				
				try {
					
					JSONArray jsonArray = response.getJSONArray("study_schedule");
					
					for (int i = 0; i < jsonArray.length(); i++) {
						
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						
						float duration = jsonObject.getFloat("duration");
						
						// I'm assuming that the start date and time stored in "start" under study_schedule in the database is of data type 
						// String. If not, feel free to change the code
						String start = jsonObject.getString("start");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
						LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
						LocalDateTime currentDateTime = LocalDateTime.now();
						
						if (currentDateTime.isAfter(startDateTime)) {
							
							totalTimeStudying = totalTimeStudying + duration;
							
						}
						
					}
					
				} catch (JSONException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		}, new Response.ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				
				error.printStackTrace();
				
			}
			
		});
		
		requestQueue.add(jsonObjectRequest);
		return totalTimeStudying;
		
	}
	
	// Gets total time spent studying on a specific course (With Volley)
	public static float course_statistics_volley(String GUID, int courseCode) throws Exception {
		
		String url = serverURL.ROOT_URL + GUID;
		float timeStudying = 0;
		
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
			
			@Override
			public void onResponse(JSONObject response) {
				
				try {
					
					JSONArray jsonArray = response.getJSONArray("study_schedule");
					
					for (int i = 0; i < jsonArray.length(); i++) {
						
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						
						float duration = jsonObject.getFloat("duration");
						int courseID = jsonObject.getInt("course_id");
						
						// I'm assuming that the start date and time stored in "start" under study_schedule in the database is of data type 
						// String. If not feel free to change the code
						String start = jsonObject.getString("start");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
						LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
						LocalDateTime currentDateTime = LocalDateTime.now();
						
						if (currentDateTime.isAfter(startDateTime) && courseID == courseCode) {
							
							timeStudying = timeStudying + duration;
							
						}
						
					}
					
				} catch (JSONException e) {
					
					e.printStackTrace();
					
				}
				
			}
			
		}, new Response.ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				
				error.printStackTrace();
				
			}
			
		});
		
		requestQueue.add(jsonObjectRequest);
		return timeStudying;
		
	}
	
	// -----------------------------------------------------------------------------------------------------------------------
	// Old functions below (not using Volley)
	
	// Gets total time spent studying on all courses
	public static float study_statistics(String GUID) throws Exception {
		
		// Connect to the database at the endpoint and sends a GET request
		URL url = new URL("http://studentaide.canadacentral.cloudapp.azure.com/studentaide/study_schedule/" + GUID);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		
		float totalTimeStudying = 0;
		int responseCode = conn.getResponseCode();
		
		// If GET request goes through
		if(responseCode == HttpURLConnection.HTTP_OK) {
			
			// Logs response code if it goes through
			System.out.println("GET Response Code: " + responseCode);
			
			Scanner sc = new Scanner(url.openStream());
			String data = "";
			
			// Read data from the endpoint
			while (sc.hasNext()) {
				data += sc.nextLine();
			}
			sc.close();
			
			// Parse the data
			JSONParser parser = new JSONParser();
			JSONObject jsonObject1 = (JSONObject)parser.parse(data);
			JSONArray jsonArray = (JSONArray)jsonObject1.get("study_schedule");
			
			// Adds all of the duration of study sessions together to get total time spent studying
			for (int i = 0; i < jsonArray.size(); i++) {
				
				JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
				
				// I'm assuming that the start date stored in "start" under study_schedule in the database is of data type String.
				// If not feel free to change the code
				String start = jsonObject2.get("start");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
				LocalDateTime currentDateTime = LocalDateTime.now();
				
				if (currentDateTime.isAfter(startDateTime)) {
					
					totalTimeStudying = totalTimeStudying + (Float)jsonObject2.get("duration");
					
				}
				
			}
		
		} else {
			
			// Logs error code if GET request does not go through
			throw new RuntimeException("HTTP Response Code: " + responseCode);
			
		}
		
		// Disconnects from the database
		conn.disconnect();
		return totalTimeStudying;
		
	}
	
	// Gets total time spent studying a specific course
	public static float course_statistics(String GUID, int courseCode) throws Exception {
		
		// Connect to the database at the endpoint and sends a GET request
		URL url = new URL("http://studentaide.canadacentral.cloudapp.azure.com/studentaide/study_schedule/" + GUID);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		
		float timeStudying = 0;
		int responseCode = conn.getResponseCode();
		
		// If GET request goes through
		if(responseCode == HttpURLConnection.HTTP_OK) {
			
			// Logs response code if it goes through
			System.out.println("GET Response Code: " + responseCode);
			
			Scanner sc = new Scanner(url.openStream());
			String data = "";
			
			// Read data from the endpoint
			while (sc.hasNext()) {
				data += sc.nextLine();
			}
			sc.close();
			
			// Parse the data
			JSONParser parser = new JSONParser();
			JSONObject jsonObject1 = (JSONObject)parser.parse(data);
			JSONArray jsonArray = (JSONArray)jsonObject1.get("study_schedule");
			
			// Adds only the duration of study sessions for specific courses to get time spent studying for that course
			for (int i = 0; i < jsonArray.size(); i++) {
				
				JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
				
				// I'm assuming that the start date stored in "start" under study_schedule in the database is of data type String.
				// If not feel free to change the code
				String start = jsonObject2.get("start");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
				LocalDateTime currentDateTime = LocalDateTime.now();
				
				if (currentDateTime.isAfter(startDateTime) && (Integer)jsonObject2.get("course_id") == courseCode) {
					
					timeStudying = timeStudying + (Float)jsonObject2.get("duration");
					
				}
				
			}
		
		} else {
			
			// Logs error code if GET request does not go through
			throw new RuntimeException("HTTP Response Code: " + responseCode);
			
		}
		
		// Disconnects from the database
		conn.disconnect();
		return timeStudying;
		
	}

}