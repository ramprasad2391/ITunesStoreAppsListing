//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto
package com.example.itunesstoreappslisting;

import java.io.Serializable;

public class App implements Serializable{
	//id, app title, developer name, url, small photo url, large photo url, and app price.
	
	int appId;
	String appTitle, appDeveloper, appURL, appSmallPhoto,appPrice, appReleaseDate, appLargePhoto;
	
	
	
	public App() {
		
	}


	public App(int appId, String appTitle, String appDeveloper, String appURL,
			String appSmallPhoto, String appPrice, String appReleaseDate) {
		super();
		this.appId = appId;
		this.appTitle = appTitle;
		this.appDeveloper = appDeveloper;
		this.appURL = appURL;
		this.appSmallPhoto = appSmallPhoto;
		this.appPrice = appPrice;
		this.appReleaseDate = appReleaseDate;
	}

	
	public App(int appId, String appTitle, String appDeveloper, String appURL,
			String appSmallPhoto, String appPrice, String appReleaseDate, String appLargePhoto) {
		super();
		this.appId = appId;
		this.appTitle = appTitle;
		this.appDeveloper = appDeveloper;
		this.appURL = appURL;
		this.appSmallPhoto = appSmallPhoto;
		this.appPrice = appPrice;
		this.appReleaseDate = appReleaseDate;
		this.appLargePhoto = appLargePhoto;
	}
	
	
	/**
	 * @return the appLargePhoto
	 */
	public String getAppLargePhoto() {
		return appLargePhoto;
	}


	/**
	 * @param appLargePhoto the appLargePhoto to set
	 */
	public void setAppLargePhoto(String appLargePhoto) {
		this.appLargePhoto = appLargePhoto;
	}


	/**
	 * @return the appReleaseDate
	 */
	public String getAppReleaseDate() {
		return appReleaseDate;
	}


	/**
	 * @param appReleaseDate the appReleaseDate to set
	 */
	public void setAppReleaseDate(String appReleaseDate) {
		this.appReleaseDate = appReleaseDate;
	}


	/**
	 * @return the appId
	 */
	public int getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(int appId) {
		this.appId = appId;
	}

	/**
	 * @return the appTitle
	 */
	public String getAppTitle() {
		return appTitle;
	}

	/**
	 * @param appTitle the appTitle to set
	 */
	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	/**
	 * @return the appDeveloper
	 */
	public String getAppDeveloper() {
		return appDeveloper;
	}

	/**
	 * @param appDeveloper the appDeveloper to set
	 */
	public void setAppDeveloper(String appDeveloper) {
		this.appDeveloper = appDeveloper;
	}

	/**
	 * @return the appURL
	 */
	public String getAppURL() {
		return appURL;
	}

	/**
	 * @param appURL the appURL to set
	 */
	public void setAppURL(String appURL) {
		this.appURL = appURL;
	}

	/**
	 * @return the appSmallPhoto
	 */
	public String getAppSmallPhoto() {
		return appSmallPhoto;
	}

	/**
	 * @param appSmallPhoto the appSmallPhoto to set
	 */
	public void setAppSmallPhoto(String appSmallPhoto) {
		this.appSmallPhoto = appSmallPhoto;
	}

	

	/**
	 * @return the appPrice
	 */
	public String getAppPrice() {
		return appPrice;
	}

	


	/**
	 * @param appPrice the appPrice to set
	 */
	public void setAppPrice(String appPrice) {
		this.appPrice = appPrice;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "App [appId=" + appId + ", appTitle=" + appTitle
				+ ", appDeveloper=" + appDeveloper + ", appURL=" + appURL
				+ ", appSmallPhoto=" + appSmallPhoto + ", appPrice=" + appPrice
				+ ", appReleaseDate=" + appReleaseDate + ", appLargePhoto="
				+ appLargePhoto + "]";
	}
	

	
	
	
	
}
