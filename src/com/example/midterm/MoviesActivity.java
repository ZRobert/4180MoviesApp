package com.example.midterm;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MoviesActivity extends Activity{

	MoviesAdapter adapter;
	ListView moviesMenu;
	final String inTheatersUrl = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?page_limit=50&country=us&apikey=zp3sq5yqde9hnszzr9sd78mt";
	final String moviesOpeningUrl = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?page_limit=50&apikey=zp3sq5yqde9hnszzr9sd78mt";
	final String boxOfficeUrl = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?page_limit=50&apikey=zp3sq5yqde9hnszzr9sd78mt";
	final String upcomingMoviesUrl = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?page_limit=50&apikey=zp3sq5yqde9hnszzr9sd78mt";
	ArrayList<Movie> movies = new ArrayList<Movie>();
	boolean isFavoritesList = false;
	final static int REQ_CODE = 1983;
	final static String RESULT_KEY = "FAVORITE";
	final static String POSITION = "POSITION";
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies); // ""
		moviesMenu = (ListView) findViewById(R.id.moviesList);
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey("ID")) {
				int selection = Integer.valueOf(getIntent().getExtras()
						.getString("ID"));
				isFavoritesList = Boolean.getBoolean(getIntent().getExtras().getString("FAVORITE"));
				switch (selection) {
				case 0: // Favorite movies
				
					new ASyncFavorites(this).execute("3");
					break;
				case 1: // Box office movies
					

					new ASyncRottenTomatoes(this).execute(boxOfficeUrl);
					break;

				case 2: // Theater movies
					new ASyncRottenTomatoes(this).execute(inTheatersUrl);

					break;
				case 3: // Opening movies
					new ASyncRottenTomatoes(this).execute(moviesOpeningUrl);

					break;
				case 4: // Upcoming movies
					new ASyncRottenTomatoes(this).execute(upcomingMoviesUrl);

					break;
				default:
					break;
				}
			}
		}
		moviesMenu.setOnItemClickListener(new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("Main", position + "");
				Intent i = new Intent(getBaseContext(), MovieActivity.class);
				i.putExtra("MOVIE", movies.get(position).toString());
				i.putExtra("POSITION", String.valueOf(position));
				i.putExtra("FAVORITE", String.valueOf(isFavoritesList));
				startActivity(i);
				//startActivityForResult(i, REQ_CODE);

			}
		});
		moviesMenu.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("Long","Press");
				Log.d("Favorites", isFavoritesList +"");
				if(isFavoritesList){
					
					movies.get(position).setFavorite(false);
					new ASyncFavorites().execute("2", movies.get(position).getId());
					adapter.remove(adapter.getItem(position));
					movies.remove(position);
					
				}
				return false;
			}
		
		});

		Log.d("Favorites", isFavoritesList + "");

	}
/*	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQ_CODE){
			if(data.getExtras().containsKey(RESULT_KEY))
				if(resultCode == RESULT_OK &&data.getExtras().getString(RESULT_KEY).equals("false")&&isFavoritesList){
				adapter.remove(movies.get(Integer.valueOf(data.getExtras().getString("POSITION"))));
				movies.remove(Integer.valueOf(data.getExtras().getString("POSITION")));
				
			}
		}
	}
*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movies, menu);
		return true;
	}



	public void receiveMessages(ArrayList<Movie> result) {
		adapter = new MoviesAdapter(this, R.layout.movie_list_item, movies);
		adapter.setNotifyOnChange(true);
		moviesMenu.setAdapter(adapter);
	}

	public void addToList(Movie result) {
		// TODO Auto-generated method stub
		movies.add(result);
}
	public void addToListAndAdapter(Movie result){
		movies.add(result);
		adapter.clear();
	}

	public void responseError(String errorId) {
		Toast.makeText(this, errorId, Toast.LENGTH_LONG).show();

	}

	public void receiveMessages() {
		adapter = new MoviesAdapter(this, R.layout.movie_list_item, movies);
		adapter.setNotifyOnChange(true);
		moviesMenu.setAdapter(adapter);

	}
	public void showProgressDialog() {
		pd = new ProgressDialog(MoviesActivity.this);
		pd.setMessage("Loading Movies");
		pd.setCancelable(false);
		pd.show();

	}
	public void dismissProgressDialog() {
		pd.dismiss();
	}
}
