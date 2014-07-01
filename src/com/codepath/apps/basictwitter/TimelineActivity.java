package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	private static final int GET_RESULT_TEXT = 10;
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private long sinceId=1;
	private long maxId =1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApp.getRestClient();
		populateTimeline();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
//		aTweets = new ArrayAdapter<Tweet>(this, android.R.layout.simple_list_item_1, tweets);
		// Use a custom made array adapter, only needs to change this line.
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
		// Attach the listener to the AdapterView onCreate
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	    @Override
	    public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
	        //customLoadMoreDataFromApi(page); 
           customLoadMoreDataFromApi(totalItemsCount); 
	    }
        });
        
        
	}
	protected void customLoadMoreDataFromApi(int totalItemsCount) {
		Log.d("DEBUG", "customLoadMoreDataFromApi");
		// get the last tweet in the tweets array, use the totalItemsCount that
		// is maintained by EndlessScrollListerer.java
		if (!tweets.isEmpty()) {
			Tweet lastRetrieveTweet = tweets.get(totalItemsCount - 1);
			sinceId = lastRetrieveTweet.getUid();
			maxId = sinceId -1;
			getAndDisplayMoreTimeline();
		}
		    
	}
	
	
	private void getAndDisplayMoreTimeline() {
		Log.d("debug", "populateTimeline()- TimelineActivity.java");
        client.getHomeTimeline(sinceId, maxId, new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int arg0, JSONArray jsonArray) {
        		super.onSuccess(arg0, jsonArray);
        		aTweets.addAll(Tweet.fromJSONArray(jsonArray));
        		Log.d("debug", "arg0=" + arg0);
		        Log.d("debug", jsonArray.toString());
        	}
        	
        	@Override
        	public void onFailure( Throwable e, String s) {
        		Log.d("debug", e.toString());
        		Log.d("debug", s.toString());
        	}
        });	
		
	}
	
	
	private void populateTimeline() {
		Log.d("debug", "populateTimeline()- TimelineActivity.java");
//        client.getHomeTimeline(sinceId, maxId, new JsonHttpResponseHandler() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int arg0, JSONArray json) {
        		super.onSuccess(arg0, json);
        		aTweets.addAll(Tweet.fromJSONArray(json));
        		Log.d("debug", "arg0=" + arg0);
		        Log.d("debug", json.toString());
        	}
        	
        	
        	@Override
        	public void onFailure( Throwable e, String s) {
        		Log.d("debug", e.toString());
        		Log.d("debug", s.toString());
        	}
        });		
	}
	
	// Inflate the menu; this adds items to the action bar if it is present.
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.timeline, menu);
//			return true;
			return super.onCreateOptionsMenu(menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		        case R.id.action_compose_tweet:
		            startActivityForResult(
		  				  new Intent(TimelineActivity.this, ComposeTweetActivity.class), 
		  				    GET_RESULT_TEXT);
		            
		        	return true;
		        case R.id.action_settings:
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
		
		// Handle the result once the activity returns a result, display contact
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (requestCode == 0) {
				if (resultCode == RESULT_OK) {
					String returnData = data.getStringExtra("myNewTweet");
					Toast.makeText(this, returnData, Toast.LENGTH_SHORT).show();
				}
			}
		}
}
