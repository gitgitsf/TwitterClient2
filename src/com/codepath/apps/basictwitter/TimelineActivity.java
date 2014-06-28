package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;

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
		
	}
	
//	@Override
//	public void onSuccess(JSONArray json) {
//		Log.d("debug", "onSuccess()");
//		Log.d("debug", json.toString());
//	}
	
	private void populateTimeline() {
		Log.d("debug", "populateTimeline()- TimelineActivity.java");
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
}
