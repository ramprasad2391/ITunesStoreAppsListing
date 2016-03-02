//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto

package com.example.itunesstoreappslisting;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.util.Xml;

public class XMLUtil {

	public static class AppSAXParser extends DefaultHandler
	
	{
		ArrayList<App> results;
		App app;
		StringBuilder xmlInnerText;
		boolean tag;
		boolean img;
		int appId;
		String appTitle, appDeveloper, appURL, appSmallPhoto, appLargePhoto, appPrice, appReleaseDate;
		
		public ArrayList<App> getResults() {
			return results;
		}

		static public ArrayList<App> parseApps(InputStream in) throws IOException, SAXException{
			AppSAXParser parser = new AppSAXParser();
			Xml.parse(in, Xml.Encoding.UTF_8, parser);		
			Log.d("demo",parser.getResults().toString());
			return parser.getResults();			
		}
		
		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
			tag = false;
			results = new ArrayList<App>();
			xmlInnerText = new StringBuilder();
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}

		
		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			if(tag){
				xmlInnerText.append(ch,start,length);
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			if(localName.equals("entry"))
			{
				tag = true;
				app = new App();
			}
			else if(localName.equals("id")){
				if(tag){
					app.setAppId(Integer.parseInt(attributes.getValue("im:id")));
				}
			}
			
			else if(localName.equals("link")){
				if(tag){
					app.setAppURL(attributes.getValue("href"));
				}
			}
			
			else if(qName.equals("im:price")){
				if(tag){
					app.setAppPrice(attributes.getValue("amount")+ " "+ attributes.getValue("currency"));
				}
			}
			else if(qName.equals("im:releaseDate")){
				if(tag){
					app.setAppReleaseDate((attributes.getValue("label")));
				}
			}
			else if(localName.equals("title")){
				if(tag){
					
					xmlInnerText.setLength(0);
				}
			}
			else if(qName.equals("im:artist")){
				if(tag){
					
					xmlInnerText.setLength(0);
				}
			}
			
			else if(qName.equals("im:image")){
				if(tag){
					
						xmlInnerText.setLength(0);
						if(attributes.getValue(0).equals("53")){
							img = true;
						}
						if(attributes.getValue(0).equals("100")){
							img = false;
						}
						
										
				}
			}
			
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			if(localName.equals("entry"))
			{			
				results.add(app);
				tag = false;
			}
			
			else if(localName.equals("title")){
				if(tag){
					app.setAppTitle(xmlInnerText.toString());
					xmlInnerText.setLength(0);
				}
			}
			else if(qName.equals("im:artist")){
				if(tag){
					app.setAppDeveloper(xmlInnerText.toString());
					xmlInnerText.setLength(0);
				}
			}
			
			else if(qName.equals("im:image")){
				if(tag){
					if(img){
						app.setAppSmallPhoto(xmlInnerText.toString());
					}
					else{
						app.setAppLargePhoto(xmlInnerText.toString());
					}
					
					xmlInnerText.setLength(0);					
				}
			}
		}
	}
}
