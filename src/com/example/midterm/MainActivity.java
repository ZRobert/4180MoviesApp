//Midterm
//MainActivity.java
//Robert Payne
package com.example.midterm;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity{
	ListView mainMenu;
	ArrayList<String> options = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	private static final int MENU1 = Menu.FIRST;
	private static final int MENU2 = Menu.FIRST + 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		options.add("My Favorite Movies");
		options.add("Box Office Movies");
		options.add("In Theaters Movies");
		options.add("Opening Movies");
		options.add("Upcoming Movies");
		
		
		mainMenu = (ListView) findViewById(R.id.MainMenuListView);
		mainMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getBaseContext(), MoviesActivity.class);
				i.putExtra("ID", position + "");
				if(position == 0) {
					i.putExtra("FAVORITE", String.valueOf(true));
				}else {
					i.putExtra("FAVORITE", String.valueOf(false));
				}
				startActivity(i);

			}
		});
		adapter = new ArrayAdapter<String>(this,
				R.layout.menu_list_view, R.id.menuItemText,
				options);
		mainMenu.setAdapter(adapter);
		
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU1, 0, "Clear all favorites");
		menu.add(0, MENU2, 0, "Exit");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case MENU1:
	            new ASyncFavorite().execute("2");
	            return true;
	        case MENU2:
	            finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
