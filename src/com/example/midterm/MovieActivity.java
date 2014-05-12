//Midterm
//MovieActivity.java
//Robert Payne
package com.example.midterm;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MovieActivity extends Activity implements
		android.view.View.OnClickListener {
	RelativeLayout movieActivity;
	ImageView moviePoster;
	ImageView audienceRating;
	ImageView criticRating;
	TextView audienceScore;
	TextView movieTitle;
	TextView criticScore;
	TextView hours;
	TextView minutes;
	TextView mpaaRating;
	TextView releaseDate;
	TextView genre1;
	TextView genre2;
	TextView genre3;
	ImageButton addToFavorites;
	ImageButton moviesSite;
	ImageButton back;
	int currentMovie;
	Movie movies = null;
	boolean isFavoriteList = false;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		// moviePoster, audienceRating, criticRating, audienceScore,
		// criticScore, hours, minutes, mpaaRating
		audienceScore = (TextView) findViewById(R.id.movieAudienceScore);
		criticScore = (TextView) findViewById(R.id.movieCriticScore);
		hours = (TextView) findViewById(R.id.movieHrValue);
		minutes = (TextView) findViewById(R.id.movieMinValue);
		mpaaRating = (TextView) findViewById(R.id.movieMpaa);
		movieTitle = (TextView) findViewById(R.id.movieTitle);
		releaseDate = (TextView) findViewById(R.id.movieRelease);
		audienceRating = (ImageView) findViewById(R.id.movieAudienceRating);
		criticRating = (ImageView) findViewById(R.id.movieCriticRating);
		moviePoster = (ImageView) findViewById(R.id.moviePoster);
		addToFavorites = (ImageButton) findViewById(R.id.movieAddToFavorites);
		moviesSite = (ImageButton) findViewById(R.id.movieWebView);
		back = (ImageButton) findViewById(R.id.movieBackButton);
		genre1 = (TextView) findViewById(R.id.movieGenre1);
		genre2 = (TextView) findViewById(R.id.movieGenre2);
		genre3 = (TextView) findViewById(R.id.movieGenre3);
		addToFavorites.setOnClickListener(this);
		moviesSite.setOnClickListener(this);
		back.setOnClickListener(this);

		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().containsKey("MOVIE")) {
				currentMovie = Integer.valueOf(getIntent().getExtras()
						.getString("POSITION"));
				String[] tempMovie = getIntent().getExtras().getString("MOVIE")
						.split(",");
				isFavoriteList = Boolean.getBoolean(getIntent().getExtras()
						.getString("FAVORITE"));
				movies = new Movie(tempMovie);
				new ASyncRottenTomatoMovie(this).execute(movies.getId());
				Log.d("fav?", movies.getTitle() + ": "+ movies.isFavorite());
				new ASyncFavorite(movies).execute("4", movies.getId());
				
				audienceScore.setText(movies.getAudienceScore() + "%");
				criticScore.setText(movies.getCriticScore() + "%");
				hours.setText(movies.getHours() + "");
				minutes.setText(movies.getMinutes() + "");
				mpaaRating.setText(movies.getMpaaRating());
				movieTitle.setText(movies.getTitle());

				String[] formatDate = movies.getReleaseDate().split("-");
				releaseDate.setText(formatDate[1] + "/" + formatDate[2] + "/"
						+ formatDate[0]);
				if (movies.getAudienceRating().equals("Upright")) {
					audienceRating.setImageResource(R.drawable.upright);
				} else if (movies.getAudienceRating().equals("Spilled")) {
					audienceRating.setImageResource(R.drawable.spilled);

				} else if (movies.getAudienceRating().equals("Rotten")) {
					audienceRating.setImageResource(R.drawable.rotten);

				} else if (movies.getAudienceRating().equals("Fresh")) {
					audienceRating.setImageResource(R.drawable.fresh);

				} else if (movies.getAudienceRating().equals("N/A")) {
					audienceRating.setImageResource(R.drawable.notranked);
				}
				if (movies.getCriticRating().equals("Upright")) {
					criticRating.setImageResource(R.drawable.upright);
				} else if (movies.getCriticRating().equals("Spilled")) {
					criticRating.setImageResource(R.drawable.spilled);

				} else if (movies.getCriticRating().equals("Rotten")) {
					criticRating.setImageResource(R.drawable.rotten);

				} else if (movies.getCriticRating().equals("Fresh")) {
					criticRating.setImageResource(R.drawable.fresh);

				} else if (movies.getCriticRating().equals("N/A")) {
					criticRating.setImageResource(R.drawable.notranked);
				}
				Log.d("fav2?", movies.isFavorite() + "");
				if(movies.isFavorite()){
					addToFavorites.setImageResource(R.drawable.rotten);
				} else {
					addToFavorites.setImageResource(R.drawable.fresh);
				}
				moviePoster.setTag(movies.getThumbUrl());	
				new ASyncLoadImage(moviePoster).execute(movies.getThumbUrl());
			
				//new ASyncLoadImage(moviePoster).execute(movies.getPosterUrl());
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
		return true;
	}

	public void showProgressDialog() {
		// TODO Auto-generated method stub

	}

	public void dismissProgressDialog() {
		// TODO Auto-generated method stub

	}

	public void receiveMessages(Drawable result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View which) {
		// TODO Auto-generated method stub
		int buttonId = which.getId();
		switch (buttonId) {

		case R.id.movieAddToFavorites:
			if (!movies.isFavorite()) {
				new ASyncFavorite().execute("0", movies.getId());
				movies.setFavorite(true);
				addToFavorites.setImageResource(R.drawable.fresh);


			} else {
				new ASyncFavorite().execute("1", movies.getId());
				movies.setFavorite(false);
				addToFavorites.setImageResource(R.drawable.rotten);

			}
			break;
		case R.id.movieBackButton:
/*			  Intent i = new Intent();
              i.putExtra(MoviesActivity.RESULT_KEY, Boolean.toString(movies.isFavorite()));
              i.putExtra(MoviesActivity.POSITION, Integer.toString(currentMovie));
              setResult(RESULT_OK, i);
 */             finish();
		
			break;
		case R.id.movieWebView:
			Intent q = new Intent(Intent.ACTION_VIEW);
			q.setData(Uri.parse(movies.getWebpageUrl()));
			startActivity(q);

			break;
		}
	}

	public void setGenre(Movie result) {  
	/*	if(result.getGenre1() != null){
			genre1.setText(result.getGenre1());
			if(result.getGenre2() != null){
				genre2.setText(" ," + result.getGenre2());
				if(result.getGenre3() != null){
					genre3.setText(" ," +result.getGenre3());
				}
			}
		}		
	 */}

}
