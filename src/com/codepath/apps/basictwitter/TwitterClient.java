package com.codepath.apps.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
//	public static final String REST_URL = "http://api.flickr.com/services"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "7J0gYZGjWl718hr7IMH6mZJrb";       // Change this
	public static final String REST_CONSUMER_SECRET = "m2oTqimvlastPGAL1y9Lx75fhrSCCswA8SFyRh9MsBbdB3NLoF"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictwitter"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		Log.d("debug", "getHomeTimeline() - TwitterClient.java");
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		client.get(apiUrl, params, handler);
	}

	public void getHomeTimeline(long sinceId, long maxId, AsyncHttpResponseHandler handler) {
		Log.d("debug", "getHomeTimeline() 2 - TwitterClient.java");
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		String strSinceId="";
		strSinceId =Long.toString(sinceId);;
		String strMaxId="";
		strMaxId =Long.toString(maxId);;
		//params.put("since_id", strSinceId);
		params.put("since_id", "1");
		params.put("max_id", strMaxId);
		client.get(apiUrl, params, handler);
	}
	
	public void getMentionTimeline(AsyncHttpResponseHandler handler) {
		Log.d("debug", "getMentionTimeline()  - TwitterClient.java");
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		String strMaxId="1";
//		params.put("since_id", "1");
//		params.put("max_id", strMaxId);
//		client.get(apiUrl, params, handler);  //not work
		client.get(apiUrl, null, handler); //works
		
	}
	
	public void getMyInfo(AsyncHttpResponseHandler handler) {
		Log.d("debug", "getMyInfo()  - TwitterClient.java");
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, handler);  
	}
	
	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		Log.d("debug", "getUserTimeline()  - TwitterClient.java");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		client.get(apiUrl, params, handler);  
	}
	
	// post a new tweet to Twitter
	public void postUpdate(String myNewTweet, AsyncHttpResponseHandler handler){
		Log.d("debug", "postUpdate() - TwitterClient.java");
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", myNewTweet);
		client.post(apiUrl, params, handler);
	}

	
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	/*public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	} */

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}