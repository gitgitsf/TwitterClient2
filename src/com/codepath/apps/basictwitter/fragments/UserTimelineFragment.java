package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class UserTimelineFragment extends TweetsListFragment {
	
	private TwitterClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApp.getRestClient();
		populateTimeline();
	}
	
	private void populateTimeline() {
		Log.d("debug", "populateTimeline()- MentionTimelineFragment.java");
        client.getUserTimeline(new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int arg0, JSONArray json) {
        		super.onSuccess(arg0, json);
        		addAll(Tweet.fromJSONArray(json));
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
