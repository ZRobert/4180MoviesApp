//Midterm
//ASyncRottenTomatoes.java
//Robert Payne
package com.example.midterm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class ASyncRottenTomatoes extends
		AsyncTask<String, Void, ArrayList<Movie>> {
	private MoviesActivity movies;
	private HttpClient client = new DefaultHttpClient();

	public ASyncRottenTomatoes(MoviesActivity movies) {
		this.movies = movies;
	}

	@Override
	protected ArrayList<Movie> doInBackground(String... params) {
		
		String urlString = params[0];
		ArrayList<Movie> result = new ArrayList<Movie>();
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
				result = MoviesJSONParser.MoviesJSONParserUtil.parseMovies(sb.toString(), movies);
			} else {
			}
		} catch (UnsupportedEncodingException e) {
		} catch (Exception e) {
		}
		
		return result;
	}

	@Override
	protected void onPreExecute() {
		this.movies.showProgressDialog();

	}

	@Override
	protected void onPostExecute(ArrayList<Movie> result) {
		this.movies.dismissProgressDialog();
		this.movies.receiveMessages(result);

	}

}
