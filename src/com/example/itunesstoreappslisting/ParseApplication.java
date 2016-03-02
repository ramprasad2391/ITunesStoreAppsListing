//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto
package com.example.itunesstoreappslisting;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "bnqWZkxzCDJgEiQdwvJampsAHpipT9wRFUjQK707", "uHeDFkJLX68asjaP6y6zURFpjBYYG0DRKM0I8ADw");

		//ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

	}
}
