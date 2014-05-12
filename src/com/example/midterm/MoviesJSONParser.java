//Midterm
//MoviesJSONParser.java
//Robert Payne
package com.example.midterm;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class MoviesJSONParser {

	static public class MoviesJSONParserUtil {

		static ArrayList<Movie> parseMovies(String jsonString, MoviesActivity moviesAct)
				throws JSONException {
			MoviesActivity moviesActivity = moviesAct;
			ArrayList<Movie> movies = new ArrayList<Movie>();
			JSONObject moviesJSONObject = new JSONObject(jsonString);
			JSONArray moviesJSONArray = moviesJSONObject.getJSONArray("movies");
			Log.d("JSONARRAY", moviesJSONArray.toString());
			for (int i = 0; i < moviesJSONArray.length(); i++) {
				JSONObject movieJSONObject = moviesJSONArray.getJSONObject(i);
				Movie movie = new Movie(movieJSONObject);
				Log.d("Did it really get created?", movie.getTitle());
				movies.add(movie);
				Log.d("Did it get added?", movies.toString());
				moviesActivity.addToList(movie); 
			}
			Log.d("MovieList JSONParser",movies.toString());
			return movies;
		}
	}
}
