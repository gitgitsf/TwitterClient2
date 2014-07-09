package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApp;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	
	public static final String  TAG = "UserTimelineFragment";
	private TwitterClient client;
	OnProfileChangedListener profileListener;
	
	String mScreenName;
	
	
	public interface OnProfileChangedListener {
		public void onNewUserProfileCreated();
		public String getScreenName();
	}
 
	 
	@Override 
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		// get the activity with getActivity()
		Log.d("debug", "OnActivityCreated fired");
	} 
		 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("debug", "onCreate()- " + TAG);
		client = TwitterApp.getRestClient();
		// Have the activity 
		
		populateTimeline();
	}

	private void populateTimeline() {
		Log.d("debug", "populateTimeline()- " + TAG);
		client.getUserTimeline(mScreenName, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONArray json) {
				super.onSuccess(arg0, json);
				addAll(Tweet.fromJSONArray(json));
				Log.d("debug", "populateTimeline()" + TAG + "json==>" + json.toString());
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	// Store the listener (activity) that will have events fired once the
	// fragment is attached
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("debug", "onAttach()- " + TAG);
		if (activity instanceof OnProfileChangedListener) {
			profileListener = (OnProfileChangedListener) activity;
			profileListener.onNewUserProfileCreated( );
			mScreenName = profileListener.getScreenName( );
		} else {
			throw new ClassCastException(
					activity.toString()
							+ " must implement UserTimelineFragment.onNewUserProfileCreated");
		}
	}
	 
  
}
