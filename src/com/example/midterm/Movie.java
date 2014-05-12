//Midterm
//Movie.java
//Robert Payne
package com.example.midterm;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class Movie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String mpaaRating;
	String title;
	String posterUrl;
	String thumbUrl;
	String releaseDate;
	String runTime;
	String audienceRating;
	String criticRating;
	String webpageUrl;


	String genre1;
	String genre2;
	String genre3;
	boolean isFavorite;
	int hours;
	int minutes;
	final String startUrlOfAMovie = "http://api.rottentomatoes.com/api/public/v1.0/movies/";
	final String endUrlOfAMovie = ".json?apikey=zp3sq5yqde9hnszzr9sd78mt";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCriticScore() {
		return criticScore;
	}

	public void setCriticScore(String criticScore) {
		this.criticScore = criticScore;
	}

	public String getAudienceScore() {
		return audienceScore;
	}

	public void setAudienceScore(String audienceScore) {
		this.audienceScore = audienceScore;
	}

	String criticScore;
	String audienceScore;

	public Movie(String id) {
		this.id = id;
		this.isFavorite = true;

		// get movie based on individual id
	}

	public Movie(String id, String mpaaRating, String title, String posterUrl,
			String thumbUrl, String releaseDate, String runTime,
			String audienceRating, String criticRating, String webpageUrl,
			boolean isFavorite) {
		this.id = id;
		this.mpaaRating = mpaaRating;
		this.title = title;
		this.posterUrl = posterUrl;
		this.thumbUrl = thumbUrl;
		this.releaseDate = releaseDate;
		this.runTime = runTime;
		this.audienceRating = audienceRating;
		this.criticRating = criticRating;
		this.webpageUrl = webpageUrl;
		this.isFavorite = isFavorite;
		this.hours = (Integer.valueOf(runTime) / 60);
		this.minutes = (Integer.valueOf(runTime) % 60);
	}

	public Movie(JSONObject movieJSONObject) throws JSONException {
		// posterUrl, releaseDate, audienceRating, criticRating, webpageUrl,
		// isFavorite

		try {
			this.title = movieJSONObject.getString("title");
			this.runTime = movieJSONObject.getString("runtime");
//			if(movieJSONObject.has("genres")){
//				JSONArray genres = movieJSONObject.getJSONArray("genres");
//				if(genres.length() >= 3){
//					this.genre3 = genres.getString(2);
//				}
//				if(genres.length() >= 2){
//					this.genre2 = genres.getString(1);
//				}
//				if(genres.length() >= 1){
//					this.genre1 = genres.getString(0);
//				}
//				Log.d("GenreMOVIE", "Genres have been added");
//				Log.d("Genre1", genre1);
//				Log.d("Genre2", genre2);
//				Log.d("Genre3", genre3);
//			}
			if (movieJSONObject.has("ratings")) {

				JSONObject ratings = movieJSONObject.getJSONObject("ratings");
				if (ratings.has("critics_score")) {
					this.criticScore = ratings.getString("critics_score");
				} else {
					this.criticScore = "N/A";
				}

				if (ratings.has("critics_rating")) {
					this.criticRating = movieJSONObject
							.getJSONObject("ratings").getString(
									"critics_rating");
				} else {

					this.criticRating = "N/A";
				}
				if (ratings.has("audience_score")) {
					this.audienceScore = ratings.getString("audience_score");
				} else {
					this.audienceScore = "N/A";
				}
				if (ratings.has("audience_rating")) {
					this.audienceRating = ratings.getString("audience_rating");
				} else {

					this.audienceRating = "N/A";
				}
			}

			this.thumbUrl = movieJSONObject.getJSONObject("posters").getString(
					"thumbnail");
			this.posterUrl = movieJSONObject.getJSONObject("posters")
					.getString("detailed");
			this.webpageUrl = movieJSONObject.getJSONObject("links").getString(
					"alternate");
			this.releaseDate = movieJSONObject.getJSONObject("release_dates")
					.getString("theater");

			this.hours = (Integer.valueOf(runTime) / 60);
			this.minutes = (Integer.valueOf(runTime) % 60);
			this.mpaaRating = movieJSONObject.getString("mpaa_rating");

			this.id = movieJSONObject.getString("id");
		} catch (JSONException e) {
			Log.d("JSONEXE", e.getMessage());
			this.audienceRating = "N/A";
			this.criticRating = "N/A";

			this.runTime = movieJSONObject.getString("runtime");

			this.audienceScore = movieJSONObject.getJSONObject("ratings")
					.getString("audience_score");
			this.criticScore = movieJSONObject.getJSONObject("ratings")
					.getString("critics_score");
			this.thumbUrl = movieJSONObject.getJSONObject("posters").getString(
					"thumbnail");
			this.posterUrl = movieJSONObject.getJSONObject("posters")
					.getString("detailed");
			this.webpageUrl = movieJSONObject.getJSONObject("links").getString(
					"alternate");
			this.releaseDate = movieJSONObject.getJSONObject("release_dates")
					.getString("theater");

			this.hours = (Integer.valueOf(runTime) / 60);
			this.minutes = (Integer.valueOf(runTime) % 60);
			this.mpaaRating = movieJSONObject.getString("mpaa_rating");
		}
		Log.d("Attempt to set fav", this.title + " is fav?");
		new ASyncFavorite(this).execute("4", this.id);
	}

	public String getMpaaRating() {
		return mpaaRating;
	}

	public void setMpaaRating(String mpaaRating) {
		this.mpaaRating = mpaaRating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getAudienceRating() {
		return audienceRating;
	}

	public void setAudienceRating(String audienceRating) {
		this.audienceRating = audienceRating;
	}

	public String getCriticRating() {
		return criticRating;
	}

	public void setCriticRating(String criticRating) {
		this.criticRating = criticRating;
	}

	public String getWebpageUrl() {
		return webpageUrl;
	}

	public void setWebpageUrl(String webpageUrl) {
		this.webpageUrl = webpageUrl;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	@Override
	public String toString() {
		return id + "," + mpaaRating + "," + title + "," + posterUrl + ","
				+ thumbUrl + "," + releaseDate + "," + runTime + ","
				+ audienceRating + "," + criticRating + "," + webpageUrl + ","
				+ isFavorite + "," + hours + "," + minutes + "," + criticScore
				+ "," + audienceScore;
	}

	public Movie(String str[]) {
		this.id = str[0];
		this.mpaaRating = str[1];
		this.title = str[2];
		this.posterUrl = str[3];
		this.thumbUrl = str[4];
		this.releaseDate = str[5];
		this.runTime = str[6];
		this.audienceRating = str[7];
		this.criticRating = str[8];
		this.webpageUrl = str[9];
		this.isFavorite = Boolean.getBoolean(str[10]);
		this.hours = Integer.valueOf(str[11]);
		this.minutes = Integer.valueOf(str[12]);
		this.criticScore = str[13];
		this.audienceScore = str[14];

	}
	public String getGenre1() {
		return genre1;
	}

	public void setGenre1(String genre1) {
		this.genre1 = genre1;
	}

	public String getGenre2() {
		return genre2;
	}

	public void setGenre2(String genre2) {
		this.genre2 = genre2;
	}

	public String getGenre3() {
		return genre3;
	}

	public void setGenre3(String genre3) {
		this.genre3 = genre3;
	}
}
