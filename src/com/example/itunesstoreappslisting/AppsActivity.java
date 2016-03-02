//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto
package com.example.itunesstoreappslisting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.view.View;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class AppsActivity extends Activity {
	SharedPreferences sharedPreferences;
	String currentUserName;
	ProgressDialog dialog;
	ArrayList<App> apps;
	String mode = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apps);
		apps = new ArrayList<App>();
		ParseUser currentUser = ParseUser.getCurrentUser();
		currentUserName = currentUser.getUsername();
		new GetAppList(this).execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
		mode = "ViewAll";
	}


	
	
	@Override
  public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.apps_menu, menu);
      return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();
      
      if (id == R.id.action_view_all) {
    	  mode = "ViewAll";	
    	  new GetAppList(this).execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");    	
      		return true;
      } 
      
      else if(id == R.id.action_view_favorites){
    	  	mode = "ViewFavorites";	
    	  	apps.clear();
    	  	final ListView ll = (ListView) findViewById(R.id.listViewApps);
	       	if(ll.getChildCount() > 0){
	       		ll.removeViews(0,ll.getChildCount());
	       	}
	       	AppAdapter adapter = new AppAdapter(AppsActivity.this,R.layout.row_item_layout,apps);
    			//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
    			ll.setAdapter(adapter);
	       	
	       	dialog = new ProgressDialog(this);
			dialog.setMessage("Loading Results...");
			dialog.show();
      		
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
      		Log.d("Current user name", currentUserName);
	 		query.whereEqualTo("Username",currentUserName);
	 		query.findInBackground(new FindCallback<ParseObject>() {
	 			@Override
	 			public void done(List<ParseObject> list, ParseException e) {
	 				// TODO Auto-generated method stub
	 				 if (e == null) {
	 					 Log.d("list of records",list.toString());
	 			            if(list != null && list.size() != 0){
	 			            	Iterator<ParseObject> itr = list.iterator();
	 			            	while(itr.hasNext()){
	 			            		ParseObject obj = itr.next();
	 			            		App app = new App(obj.getInt("AppId"), 
	 			            							obj.getString("AppTitle"),
	 			            							obj.getString("AppDeveloper"), 
	 			            							obj.getString("AppURL"), 
	 			            							obj.getString("AppSmallPhoto"),
	 			            							obj.getString("AppPrice"), 
	 			            							obj.getString("AppReleaseDate"),
	 			            							obj.getString("AppLargePhoto"));
	 			            		 Log.d("Each app", app.toString());
	 			            		apps.add(app);
	 			            	}
	 			            	if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
		 			       		if(apps != null){
		 			       			
		 			       			AppAdapter adapter = new AppAdapter(AppsActivity.this,R.layout.row_item_layout,apps);
		 			       			//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
		 			       			ll.setAdapter(adapter);
		 			       			ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		
		 			       				@Override
		 			       				public void onItemClick(AdapterView<?> parent, View view,
		 			       						int position, long id) {
		 			       					// TODO Auto-generated method stub					
		 			       					App app = apps.get(position);
		 			       					Intent intent;
		 			       					intent = new Intent(AppsActivity.this, com.example.itunesstoreappslisting.PreviewActivity.class);				
		 			       					intent.putExtra("ClickedApp",app);
		 			       					startActivity(intent);
		 			       				}			
		 			       			});
		 			       		}
		 			       		else{
		 			       			Toast.makeText(AppsActivity.this, "No favorites", Toast.LENGTH_SHORT).show();
		 			       		}
		 			       }
		 			       else{
		 			    	  if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
		 			            Toast.makeText(AppsActivity.this, "No favorites", Toast.LENGTH_SHORT).show();
		 			        }
		 			       } else {
		 			    	  if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
		 			            Log.d("favorites", "Error: " + e.getMessage());
		 			        }
		 			}
		 		});
      	return true;
      } 
      
      else if(id == R.id.action_clear_favorites){
    		dialog = new ProgressDialog(this);
			dialog.setMessage("Loading Results...");
			dialog.show();
      		
    	  
    	  ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
	 		query.whereEqualTo("Username",currentUserName);
	 		query.findInBackground(new FindCallback<ParseObject>() {
	 			@Override
	 			public void done(List<ParseObject> list, ParseException e) {
	 				// TODO Auto-generated method stub
	 				 if (e == null) {
	 					 
	 			            if(list != null && list.size() != 0){
	 			            	Iterator<ParseObject> itr = list.iterator();
	 			            	while(itr.hasNext()){
	 			            		ParseObject obj = itr.next();
	 			            		obj.deleteInBackground();
	 			            	}
	 			        		
	 			            	if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
	 			        		ListView ll = (ListView) findViewById(R.id.listViewApps);		 			       		
		 			       		if(mode.equals("ViewFavorites")){
			 			       		if(ll.getChildCount() > 0){
			 			       			ll.removeViews(0,ll.getChildCount());
			 			       		}
			 			       		apps.clear();
			 			       		AppAdapter adapter = new AppAdapter(AppsActivity.this,R.layout.row_item_layout,apps);
			 			       		//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
			 			       		ll.setAdapter(adapter);
			 			       		Toast.makeText(AppsActivity.this, "No favorites", Toast.LENGTH_SHORT).show();
		 			       		}
	 			            }
	 			            else{
	 			            	if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
	 			            	Toast.makeText(AppsActivity.this, "No favorites", Toast.LENGTH_SHORT).show();
	 			            }
		 			 } 
	 				 else {
	 					if(dialog.isShowing()){
			            		dialog.dismiss();
			            	}
		 			            Log.d("favorites", "Error: " + e.getMessage());
		 			 }
		 		}
		 	});
      	
      	return true;
      } 
      
      else if (id == R.id.action_view_shared){
    	  mode = "ViewShared";	
    	  apps.clear();
    	  final ListView ll = (ListView) findViewById(R.id.listViewApps);
      		if(ll.getChildCount() > 0){
      			ll.removeViews(0,ll.getChildCount());
      		}
      		AppAdapter adapter = new AppAdapter(AppsActivity.this,R.layout.row_item_layout,apps);
    			//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
    			ll.setAdapter(adapter);
      		dialog = new ProgressDialog(this);
			dialog.setMessage("Loading Results...");
			dialog.show();
      		
    	  ParseQuery<ParseObject> query = ParseQuery.getQuery("Shared");
	  		query.whereEqualTo("Username",currentUserName);
	  		query.findInBackground(new FindCallback<ParseObject>() {
	  			@Override
	  			public void done(List<ParseObject> list, ParseException e) {
	  				// TODO Auto-generated method stub
	  				 if (e == null) {
	  			            if(list != null && list.size() != 0){
	  			            	Iterator<ParseObject> itr = list.iterator();
	  			            	while(itr.hasNext()){
	  			            		ParseObject obj = itr.next();
	  			            		App app = new App(obj.getInt("AppId"), 
	  			            							obj.getString("AppTitle"),
	  			            							obj.getString("AppDeveloper"), 
	  			            							obj.getString("AppURL"), 
	  			            							obj.getString("AppSmallPhoto"),
	  			            							obj.getString("AppPrice"), 
	  			            							obj.getString("AppReleaseDate"),
	  			            							obj.getString("AppLargePhoto"));
	  			            		apps.add(app);
	  			            	}
	  			            	if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
	  			      		
	  			      		if(apps != null){
	  			      			
	  			      			AppAdapter adapter = new AppAdapter(AppsActivity.this,R.layout.row_item_layout,apps);
	  			      			//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
	  			      			ll.setAdapter(adapter);
	  			      			ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
	  			      				@Override
	  			      				public void onItemClick(AdapterView<?> parent, View view,
	  			      						int position, long id) {
	  			      					// TODO Auto-generated method stub					
	  			      					App app = apps.get(position);
	  			      					Intent intent;
	  			      					intent = new Intent(AppsActivity.this, com.example.itunesstoreappslisting.PreviewActivity.class);				
	  			      					intent.putExtra("ClickedApp",app);
	  			      					startActivity(intent);
	  			      				}			
	  			      			});
	  			      		}
	  			      		else{
	  			      			Toast.makeText(AppsActivity.this, "No Shared Apps", Toast.LENGTH_SHORT).show();
	  			      		}
	  			            	
	  			            }
	  			            else{
	  			            	if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
	  			            	Toast.makeText(AppsActivity.this, "No Shared Apps", Toast.LENGTH_SHORT).show();
	  			            }
	  			        } else {
	  			        	if(dialog.isShowing()){
 			            		dialog.dismiss();
 			            	}
	  			            Log.d("favorites", "Error: " + e.getMessage());
	  			        }
	  			}
	  		});
      	
      	return true;
      } else if (id == R.id.action_clear_shared){
        	
    		dialog = new ProgressDialog(this);
			dialog.setMessage("Loading Results...");
			dialog.show();
      		
    	  ParseQuery<ParseObject> query = ParseQuery.getQuery("Shared");
	 		query.whereEqualTo("Username",currentUserName);
	 		query.findInBackground(new FindCallback<ParseObject>() {
	 			@Override
	 			public void done(List<ParseObject> list, ParseException e) {
	 				// TODO Auto-generated method stub
	 				 if (e == null) {
	 					 
	 			            if(list != null && list.size() != 0){
	 			            	Iterator<ParseObject> itr = list.iterator();
	 			            	while(itr.hasNext()){
	 			            		ParseObject obj = itr.next();
	 			            		obj.deleteInBackground();
	 			            	}
	 			            	if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
	 			        		ListView ll = (ListView) findViewById(R.id.listViewApps);		 			       		
		 			       		if(mode.equals("ViewShared")){
			 			       		if(ll.getChildCount() > 0){
			 			       			ll.removeViews(0,ll.getChildCount());
			 			       		}
				 			       	apps.clear();
			 			       		AppAdapter adapter = new AppAdapter(AppsActivity.this,R.layout.row_item_layout,apps);
			 			       		//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
			 			       		ll.setAdapter(adapter);
			 			       		Toast.makeText(AppsActivity.this, "No Shared Apps", Toast.LENGTH_SHORT).show();
		 			       		}
	 			            }
	 			            else{
	 			            	if(dialog.isShowing()){
	 			            		dialog.dismiss();
	 			            	}
	 			            	Toast.makeText(AppsActivity.this, "No Shared Apps", Toast.LENGTH_SHORT).show();
	 			            }
		 			 } 
	 				 else {
	 					if(dialog.isShowing()){
			            		dialog.dismiss();
			            	}
		 			    Log.d("favorites", "Error: " + e.getMessage());
		 			 }
		 		}
		 	});
        	
        	return true;
        } else if (id == R.id.action_logout){
        	ParseUser.logOut();
        	finish();
        	Intent intent = new Intent(AppsActivity.this,LoginActivity.class);
        	startActivity(intent);
        	return true;
        }
      return super.onOptionsItemSelected(item);
  }
	
 
	
}
