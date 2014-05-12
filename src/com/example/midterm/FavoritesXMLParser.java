//Midterm
//FavoritesXMLParser.java
//Robert Payne
package com.example.midterm;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class FavoritesXMLParser {

	

	static public class GetFavoritesXMLPullParser {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		static void parseFavorites(InputStream xmlIn, MoviesActivity movie)
				throws XmlPullParserException, NumberFormatException,
				IOException {
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(xmlIn, "UTF-8");
			String errorId = null;
			String userId;
			String movieId;
			int loopCounter = 0;
			int event = parser.getEventType();
			MoviesActivity moviesActivity = movie;

			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("response")) {
						Log.d("XMLParser", "we got a response tag");
						//Parsing errors and uid
					} else if (parser.getName().equals("error")) {
						Log.d("XMLParser", "we got an error tag");
						parser.next();
						Log.d("error tag gets name", parser.getName());
						errorId = parser.nextText();
						Log.d("errorId", errorId);
						parser.next();
						errorId = parser.nextText();
					} else if (parser.getName().equals("user")) {
						Log.d("XMLParser", "we got a user tag");
						parser.next();
						userId = parser.nextText();
						Log.d("UserId", userId);
						//Parsing favorites
					} else if (parser.getName().equals("favorites")) {
						Log.d("XMLParser", "we got a favorites tag");
//						parser.next();
					} else if(parser.getName().equals("favorite")){
						//	while (parser.getName().equals("favorite")) {
					
						Log.d("XMLParser","Should be favorite : " + parser.getName() + "  "+ loopCounter);
							Log.d("equals favorite?", parser.getName().equals("favorite") + "");
							parser.next();
							Log.d("XMLParser", "Before the movie id : " + parser.getName());
							if(parser.getName().equals("favorite"))
								Log.d("After if equals fav", parser.nextText());
								Log.d("Error id?", errorId);
							if(event == XmlPullParser.END_TAG && parser.getName().equals("favorites"))
								break;
							Log.d("Tag? (movie id): ", parser.getName());
							movieId = parser.nextText();
							new ASyncRottenTomato(movie).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,movieId);
							Log.d("XMLParser", "Should be a movie id : " + movieId);
						
							Log.d("XMLParser", "Should be the next favorite : " + parser.getName());
							loopCounter++;
							event = parser.next();
							if(event == XmlPullParser.END_TAG && parser.getName().equals("favorites"))
								break;
	//					}
					} 
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("response")){
						break;
					 }
				default:
					break;
				}
				event = parser.next();
			}
			moviesActivity.responseError(errorId);
			moviesActivity.receiveMessages();
		}
	}
}
