//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto
package com.example.itunesstoreappslisting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification.Action;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends Activity {

	SharedPreferences sharedPreferences;
	int appId;
	String appDetails;
	Boolean b;
	String currentUserName;
	ImageButton favButton;
	ImageButton shareButton;
	Boolean isFavorite;
	ArrayList<String> list2 = new ArrayList<String>();
	ArrayList<String> list3 = new ArrayList<String>();
	CharSequence users[] = {};
	CharSequence userNames[] = {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		ParseUser currentUser = ParseUser.getCurrentUser();
		currentUserName = currentUser.getUsername();
		b = false;
		if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("ClickedApp")){
			final App app = (App) getIntent().getExtras().getSerializable("ClickedApp");
			appId = app.getAppId();
			appDetails = app.toString();
			
			TextView tv1 = (TextView) findViewById(R.id.textView1);
			tv1.setText(app.getAppTitle());
			
			ImageButton img = (ImageButton) findViewById(R.id.imageButton1);
			if(app.getAppLargePhoto() != null){
				Picasso.with(this).load(app.getAppLargePhoto()).resize(500, 500).into(img);
			}
			else{
				img.setImageResource(R.drawable.default_thumbnail);
			}
			
			 

			
			
			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.whereNotEqualTo("username", currentUserName);
			query.findInBackground(new FindCallback<ParseUser>() {
			  public void done(List<ParseUser> objects, ParseException e) {
			    if (e == null) {
			        int i = 0;
			    	if(objects != null && objects.size() != 0){
			        	Iterator<ParseUser> itr = objects.iterator();
			        	while(itr.hasNext()){
			        		ParseUser user = itr.next();
			        		/*userNames[i] = user.getUsername();
			        		users[i] = user.getString("FirstName") + " " + user.getString("LastName");
			        		i = i+1;
			        		Log.d("share",(String) users[i]);*/
			        		if(user != null){
			        			list2.add(user.getUsername());
				        		list3.add(user.getString("FirstName") + " " + user.getString("LastName"));
			        		}			        		
			        	}
			        	Log.d("share","List of users is..." + list2);
			        }
			    	else{
			    		Log.d("share","No objects");
			    	}
			    } else {
			    	e.printStackTrace();
			    }
			  }
			});
			
			final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list3);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(PreviewActivity.this);
			builder.setTitle("Users")
			.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					saveShared(app, list2.get(which),list3.get(which));
					dialog.dismiss();
					
				}
			});
			
			final AlertDialog alert = builder.create();
			
			shareButton = (ImageButton) findViewById(R.id.imageButtonShared);
			
			shareButton.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d("share","Share button clicked");
					alert.show();
				}
			});
			
			img.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(PreviewActivity.this, WebViewActivity.class);
					intent.putExtra("AppUrl", app.getAppURL());
					startActivity(intent);
				}
			});
			favButton = (ImageButton) findViewById(R.id.imageButtonFav);			
			
			ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Favorites");
			query1.whereEqualTo("Username",currentUserName);
			query1.whereEqualTo("AppId", app.getAppId());
			query1.findInBackground(new FindCallback<ParseObject>() {
			    
				@Override
				public void done(List<ParseObject> list,
						ParseException e) {
					// TODO Auto-generated method stub
					 if (e == null) {
						 	isFavorite = false;
				            
						 	if(list != null && list.size() != 0){
								Iterator<ParseObject> itr = list.iterator();
								while(itr.hasNext()){
									ParseObject obj = itr.next();
									isFavorite = true;
									favButton.setImageResource(R.drawable.rating_important);									
									break;
								}
							}			            
						 	else if((list == null) || (list != null && list.size() == 0) || !isFavorite){
				            	favButton.setImageResource(R.drawable.rating_not_important);
				            }  
				        } else {
				            Log.d("score", "Error: " + e.getMessage());
				        }
				}
			});
			
			
			
			
			favButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Favorites");
					query.whereEqualTo("Username",currentUserName);
					query.whereEqualTo("AppId", app.getAppId());
					query.findInBackground(new FindCallback<ParseObject>() {
					    
						@Override
						public void done(List<ParseObject> list,
								ParseException e) {
							// TODO Auto-generated method stub
							 if (e == null) {
								 	isFavorite = false;
						            
								 	if(list != null && list.size() != 0){
										Iterator<ParseObject> itr = list.iterator();
										while(itr.hasNext()){
											ParseObject obj = itr.next();
											isFavorite = true;
											favButton.setImageResource(R.drawable.rating_not_important);
											obj.deleteInBackground();
											break;
										}
									}
						            
								 	else if((list == null) || (list != null && list.size() == 0) || !isFavorite){
						            	favButton.setImageResource(R.drawable.rating_important);
						            	saveFavorites(app);
						            }
						            
						         
						            
						        } else {
						            Log.d("score", "Error: " + e.getMessage());
						        }
						}
					});
					
					
					
					
					
				}
			});
			
			
			
			
		}
		
	}
	
	
	public void saveFavorites(App app) {		
			ParseObject favorites = new ParseObject("Favorites");				
			favorites.put("Username", currentUserName);
			favorites.put("AppId", app.getAppId());
			favorites.put("AppTitle", app.getAppTitle());
			favorites.put("AppDeveloper", app.getAppDeveloper());
			favorites.put("AppURL", app.getAppURL());
			favorites.put("AppSmallPhoto", app.getAppSmallPhoto());
			favorites.put("AppPrice", app.getAppPrice());
			favorites.put("AppReleaseDate", app.getAppReleaseDate());
			favorites.put("AppLargePhoto", app.getAppLargePhoto());
			favorites.saveInBackground();
			favButton.setImageResource(R.drawable.rating_important);		
	}
	
	
	public void saveShared(final App app, final CharSequence username,  final CharSequence user) {		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Shared");
		query.whereEqualTo("Username",username.toString());
		query.whereEqualTo("AppId",app.getAppId());
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> list, ParseException e) {
		        if (e == null) {
		           if(list != null && list.size() != 0){
		        	   Toast.makeText(getApplicationContext(), "Already Shared with " + user.toString() , Toast.LENGTH_SHORT).show();
		           }
		           else{
		        	   ParseObject shared = new ParseObject("Shared");				
		       		shared.put("Username", username.toString());
		       		shared.put("AppId", app.getAppId());
		       		shared.put("AppTitle", app.getAppTitle());
		       		shared.put("AppDeveloper", app.getAppDeveloper());
		       		shared.put("AppURL", app.getAppURL());
		       		shared.put("AppSmallPhoto", app.getAppSmallPhoto());
		       		shared.put("AppPrice", app.getAppPrice());
		       		shared.put("AppReleaseDate", app.getAppReleaseDate());
		       		shared.put("AppLargePhoto", app.getAppLargePhoto());
		       		shared.saveInBackground();
		       		Toast.makeText(getApplicationContext(), "Shared with " + user.toString() , Toast.LENGTH_SHORT).show();
		           }
		        } else {
		            e.printStackTrace();
		        }
		    }
		});
		
			
}

	

	
}
