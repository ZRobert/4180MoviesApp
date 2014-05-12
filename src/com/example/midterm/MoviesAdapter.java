//Midterm
//MoviesAdapter.java
//Robert Payne
package com.example.midterm;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
	
public class MoviesAdapter extends ArrayAdapter<Movie> {


		private Context mContext;
		ArrayList<Movie> movies;
		public MoviesAdapter(Context context, int textViewResourceId, ArrayList<Movie> movies) {
		super(context, textViewResourceId);
		mContext = context;
		this.movies = movies;
		
	}



		public int getCount() {
			return movies.size();
		}

		public Movie getItem(int position) {
			return movies.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		// create a new ImageView for each item referenced by the Adapter
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.movie_list_item, null);
			}

			Movie movie = movies.get(position);

			if (movie != null) {

				TextView title = (TextView) view.findViewById(R.id.movieListTitle);
				TextView year = (TextView) view.findViewById(R.id.movieListYear);
				TextView mpaa = (TextView) view.findViewById(R.id.movieListMpaa);
				ImageView thumb = (ImageView) view.findViewById(R.id.moviePosterSmall);
				ImageView audienceRating = (ImageView) view.findViewById(R.id.movieListAudience);
				ImageView criticRating  = (ImageView) view.findViewById(R.id.movieListCritic);
 
				if (movie.getTitle() != null){
					title.setText(movie.getTitle());
				}
				if (movie.getReleaseDate() != null){
					year.setText(movie.getReleaseDate().split("-")[0]);
				}
				if (movie.getMpaaRating() != null) {
					mpaa.setText(movie.getMpaaRating());
				}
				if (movie.getThumbUrl() != null){
					
					thumb.setTag(movie.getThumbUrl());
					new ASyncLoadImage(thumb).executeOnExecutor(
							AsyncTask.SERIAL_EXECUTOR, movies.get(position)
									.getThumbUrl());
					
				
				}
					if(movie.getAudienceRating().equals("Upright")){
						audienceRating.setImageResource(R.drawable.upright);
					}else if(movie.getAudienceRating().equals("Spilled")){
						audienceRating.setImageResource(R.drawable.spilled);
						
					}else if(movie.getAudienceRating().equals("Rotten")){
						audienceRating.setImageResource(R.drawable.rotten);
						
					}else if(movie.getAudienceRating().equals("Fresh")){
						audienceRating.setImageResource(R.drawable.fresh);
						
					}else if(movie.getAudienceRating().equals("N/A")) {
						audienceRating.setImageResource(R.drawable.notranked);
												
					}
				
				
					if(movie.getCriticRating().equals("Upright")){
						criticRating.setImageResource(R.drawable.upright);
					
					}else if(movie.getCriticRating().equals("Spilled")){
						criticRating.setImageResource(R.drawable.spilled);
						
					}else if(movie.getCriticRating().equals("Rotten")){
						criticRating.setImageResource(R.drawable.rotten);
						
					}else if(movie.getCriticRating().equals("Fresh")){
						criticRating.setImageResource(R.drawable.fresh);
						
					}else if(movie.getCriticRating().equals("N/A")) {
						criticRating.setImageResource(R.drawable.notranked);
												
					}
				

			}

			return view;
		}

		public ImageView updateView(Drawable image) {
			ImageView imageView = new ImageView(mContext);
			imageView.setImageDrawable(image);
			return imageView;
		}
	}
	

