package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	private final String TAG = this.getClass().getName();
	private TwitterClient client;
	private long sinceId=1;
	private long maxId =1;
	
	ImageView ivProfileImage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApp.getRestClient();
		populateTimeline();
		
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//		    @Override
//		    public void onLoadMore(int page, int totalItemsCount) {
//	                // Triggered only when new data needs to be appended to the list
//	                // Add whatever code is needed to append new items to your AdapterView
//		        //customLoadMoreDataFromApi(page); 
//	           customLoadMoreDataFromApi(totalItemsCount); 
//		    }
//	        });
		
	}
  
 
	private void populateTimeline() {
		Log.d("debug", "populateTimeline()- TimelineActivity.java");
        client.getHomeTimeline(new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int arg0, JSONArray json) {
        		super.onSuccess(arg0, json);
        		addAll(Tweet.fromJSONArray(json));
//        		fragmentTweetsList.addAll(Tweet.fromJSONArray(json));
//        		aTweets.addAll(Tweet.fromJSONArray(json));
        	}
        	
        	@Override
        	public void onFailure( Throwable e, String s) {
        		Log.d("debug", e.toString());
        		Log.d("debug", s.toString());
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
			maxId = sinceId - 1;
			getAndDisplayMoreTimeline();
		}

	}
	
	private void getAndDisplayMoreTimeline() {
		Log.d("debug", "populateTimeline()- TimelineActivity.java");
		client.getHomeTimeline(sinceId, maxId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray jsonArray) {
				super.onSuccess(arg0, jsonArray);
				// aTweets.addAll(Tweet.fromJSONArray(jsonArray));
				addAll(Tweet.fromJSONArray(jsonArray));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});

	}
	

}
