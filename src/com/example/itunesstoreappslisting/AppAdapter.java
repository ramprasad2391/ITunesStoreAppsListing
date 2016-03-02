//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto
package com.example.itunesstoreappslisting;

import java.util.List;


import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppAdapter extends ArrayAdapter<App> {

		List<App> mdata;
		Context mContext;
		int mResource;
		
		public AppAdapter(Context context, int resource, List<App> objects) {
			super(context, resource, objects);			
			this.mContext = context;
			this.mdata = objects;
			this.mResource = resource;
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
						
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(mResource, parent, false);
			}
			
			App app = mdata.get(position);
			
			ImageView img = (ImageView) convertView.findViewById(R.id.imageViewthumbnail);
			
			if(app.getAppSmallPhoto() != null){
				Picasso.with(mContext).load(app.getAppSmallPhoto()).into(img);
			}
			else{
				img.setImageResource(R.drawable.default_thumbnail);
			}
			
			
			TextView appTitleText = (TextView) convertView.findViewById(R.id.textViewRowTitle);
			appTitleText.setText(app.getAppTitle());
			
			TextView appDeveloperText = (TextView) convertView.findViewById(R.id.textViewRowDeveloperName);
			appDeveloperText.setText(app.getAppDeveloper());
			
			TextView appReleaseDateText = (TextView) convertView.findViewById(R.id.textViewRowReleaseDate);
			appReleaseDateText.setText(app.getAppReleaseDate());
			
			TextView appPriceText = (TextView) convertView.findViewById(R.id.textViewPrice);
			appPriceText.setText(app.getAppPrice());
			
	
			return convertView;
		}

	}



