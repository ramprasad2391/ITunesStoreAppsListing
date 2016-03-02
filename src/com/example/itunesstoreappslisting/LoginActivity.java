//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.itunesstoreappslisting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	EditText email, password;
	Button createAccount, loginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if(ParseUser.getCurrentUser() == null){
			email = (EditText) findViewById(R.id.editTextEmail);
		    password = (EditText) findViewById(R.id.editTextPassword);
		    
		    createAccount = (Button) findViewById(R.id.buttonCreateNewAccount);
		    createAccount.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(LoginActivity.this, com.example.itunesstoreappslisting.SignUpActivity.class);
						finish();
						startActivity(intent);
					}
				});
		    
		    loginButton = (Button) findViewById(R.id.buttonLogin);
		    loginButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						final String inemail = email.getText().toString();
						String inpassword = password.getText().toString();
						
						ParseUser.logInInBackground(inemail, inpassword, new LogInCallback() {
		
							@Override
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
					    		      Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
					    		      intent.putExtra("Email", inemail);
					    		      finish();
					    		      startActivity(intent);
					    		    } else {
					    		    	Toast.makeText(getApplicationContext(), "Cannot login", Toast.LENGTH_SHORT).show();
					    		    }						
							}
							});
						// TODO Auto-generated method stub
						
					}
				});
			}
		else{
			Log.d("User",ParseUser.getCurrentUser().getUsername());	
			Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
		      intent.putExtra("Email", ParseUser.getCurrentUser().getUsername());
		      finish();
		      startActivity(intent);
		}
      
      
  }
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(ParseUser.getCurrentUser() != null){
			Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
		      intent.putExtra("Email", ParseUser.getCurrentUser().getUsername());
		      finish();
		      startActivity(intent);
		}
	}
	
	
}
