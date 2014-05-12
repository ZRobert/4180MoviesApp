//Midterm
//ASyncRottenTomatoMovie.java
//Robert Payne
package com.example.midterm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class ASyncRottenTomatoMovie extends
		AsyncTask<String, Void, Movie> {
	private HttpClient client = new DefaultHttpClient();
	MoviesActivity moviesActivity = null;
	MovieActivity movieActivity = null;


	public ASyncRottenTomatoMovie(MovieActivity movieActivity) {
		// TODO Auto-generated constructor stub
		this.movieActivity = movieActivity;
	}


	@Override
	protected void onPostExecute(Movie result) {		
		
	
	}


	@Override
	protected Movie doInBackground(String... params) {
		String urlString = "http://api.rottentomatoes.com/api/public/v1.0/movies/" + params[0] + ".json?apikey=zp3sq5yqde9hnszzr9sd78mt"; 
		Movie movie = null;
		try {

			HttpGet request = new HttpGet(urlString);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				while ((line = in.readLine()) != null) {
					sb.append(line + "\n");
				}
				in.close();
				
				JSONObject movieJSONObject = new JSONObject(sb.toString());
				movie = new Movie(movieJSONObject);
				movie.setFavorite(true);
			} else {
			}
		} catch (UnsupportedEncodingException e) {
		} catch (Exception e) {
		}
		return movie;

	}
	

}
