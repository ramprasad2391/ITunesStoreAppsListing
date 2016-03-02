//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.itunesstoreappslisting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.xml.sax.SAXException;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GetAppList extends AsyncTask<String, Integer, ArrayList<App>>{

	ProgressDialog dialog;
	Activity mActivity;
	String type;
	
	public GetAppList(Activity mActivity) {
		super();
		this.mActivity = mActivity;
	}

	@Override
	protected ArrayList<App> doInBackground(String... params) {
		
		try {
			URL url = new URL((String) params[0]);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int statusCode = con.getResponseCode();
			if(statusCode == HttpURLConnection.HTTP_OK){
				return XMLUtil.AppSAXParser.parseApps(con.getInputStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(final ArrayList<App> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		
		if(result != null){
			ListView ll = (ListView) mActivity.findViewById(R.id.listViewApps);
			AppAdapter adapter = new AppAdapter(mActivity,R.layout.row_item_layout,result);
			//ArrayAdapter<Story> adapter = new ArrayAdapter<Story>(StoriesActivity.this,android.R.layout.simple_list_item_1,result);
			ll.setAdapter(adapter);
			ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub					
					App app = result.get(position);
					Intent intent;
					intent = new Intent(mActivity, com.example.itunesstoreappslisting.PreviewActivity.class);				
					intent.putExtra("ClickedApp",app);
					mActivity.startActivity(intent);
				}			
			});
		}
		else{
			Toast.makeText(mActivity,"No Apps",Toast.LENGTH_LONG).show();
		}
		
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog = new ProgressDialog(mActivity);
		dialog.setMessage("Loading Results...");
		dialog.show();				
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);	
	}
	
}



