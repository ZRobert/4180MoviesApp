//Midterm
//ASyncFavorites.java
//Robert Payne
package com.example.midterm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class ASyncFavorites extends AsyncTask<String, Void, Void> {

	final String addOneFavUrl = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/addToFavorites.php";
	final String deleteAllFavUrl = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteAllFavorites.php";
	final String getAllFav = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/getAllFavorites.php";
	final String isFavoriteUrl = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/isFavorite.php";
	final String deleteFavUrl = "http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteFavorite.php";
	private int operation;
	ArrayList<Movie> movies = new ArrayList<Movie>();
	MoviesActivity	moviesActivity;
	Movie movie = null;
	public ASyncFavorites(){
		;
	}
	public ASyncFavorites(MoviesActivity moviesActivity){
		this.moviesActivity = moviesActivity;
	}

	@Override
	protected Void doInBackground(String... args) {
		operation = Integer.valueOf(args[0]);
		// 0. add to favorites uid; mid
		// 1. remove from favorites uid; mid
		// 2. remove all favorites uid
		// 3. get all favorites uid
		// 4. is favorite uid;mid
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost("");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid", Config.getUid()));

		switch (operation) {
		case 0:
			request = new HttpPost(addOneFavUrl);
			params.add(new BasicNameValuePair("mid", args[1]));
			break;
		case 1:
			request = new HttpPost(deleteFavUrl);
			params.add(new BasicNameValuePair("mid",args[1]));
			break;
		case 2:
			request = new HttpPost(deleteAllFavUrl);
			break;
		case 3:
			request = new HttpPost(getAllFav);
			break;
		case 4:
			request = new HttpPost(isFavoriteUrl);
			params.add(new BasicNameValuePair("mid", args[1]));
			break;

		}
		try {
			UrlEncodedFormEntity formParams = new UrlEncodedFormEntity(params);
			request.setEntity(formParams);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream ios = response.getEntity().getContent();
//				}
				FavoritesXMLParser.GetFavoritesXMLPullParser.parseFavorites(ios,moviesActivity);		
				if(operation == 4){
					if(ios.toString().contains("true")){
						movie.setFavorite(true);
					}
				}
				ios.close();
		}

		} catch (UnsupportedEncodingException e) {
			Log.d("ASync", "Error: " + e.getMessage());
		} catch (Exception e) {
			Log.d("ASync","Error: " +  e.getMessage());
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if(operation == 3)
			moviesActivity.receiveMessages();
			moviesActivity.dismissProgressDialog();
	}
	@Override
	protected void onPreExecute() {
		moviesActivity.showProgressDialog();
	}
	

}
