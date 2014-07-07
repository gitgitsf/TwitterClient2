package com.codepath.apps.basictwitter.fragments;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;

public class TweetsListFragment extends Fragment  {
	
	private final String TAG = "TweetsListFragment";
//	private final String TAG = this.getClass().getName();
	protected ArrayList<Tweet> tweets;
	protected ArrayAdapter<Tweet> aTweets;
	protected ListView lvTweets;
	private long sinceId=1;
	private long maxId =1;
//	private TwitterClient client;
	
	ImageView ivProfileImage;
	OnFragmentTweetsListItemSelectedListener fragmentListener; //interface
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Non-view initialization
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	@Override
	public View onCreateView(LayoutInflater inflator, ViewGroup container, 
			Bundle saveInstanceState) {
		// inflate the layout
		View v = inflator.inflate(R.layout.fragment_tweets_list, container, false);
		// Assign our view reference
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				 //sent the Tweet object to Activity
				Log.d("debug", TAG + " onItemClick ");
//				Tweet selectedTweet = new Tweet();
//				selectedTweet =	tweets.get(position);
				User selectedUser = new User();
//				selectedUser =  selectedTweet.getUser();
				selectedUser =  tweets.get(position).getUser();
				Log.d("debug", TAG + " selected tweet position is==>" +  position);
//				Toast.makeText(getActivity(), "fr fragment -click profile image", Toast.LENGTH_SHORT).show();
				fragmentListener.onProfileImageClicked(selectedUser);
//				fragmentListener.onProfileImageClicked(selectedTweet);
				
			}
			
		}); 
 
//		
//		ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
//		ivProfileImage.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		}); 
	
		
		 
	 
//		lvTweets.setOnScrollListener(new EndlessScrollListener() {
//		    @Override
//		    public void onLoadMore(int page, int totalItemsCount) {
//	                // Triggered only when new data needs to be appended to the list
//	                // Add whatever code is needed to append new items to your AdapterView
//		        //customLoadMoreDataFromApi(page); 
//	           customLoadMoreDataFromApi(totalItemsCount); 
//		    }
//	        });
		// Return the layout view
		return v;
	}
	
	// Store the listener (activity) that will have events fired once the fragment is attached
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	      if (activity instanceof OnFragmentTweetsListItemSelectedListener) {
	        fragmentListener = (OnFragmentTweetsListItemSelectedListener) activity;
	      } else {
	        throw new ClassCastException(activity.toString()
	            + " must implement MyListFragment.OnItemSelectedListener");
	      }
	  }
 
	// return the adapter to the activity 
//	public TweetArrayAdapter getAdapter() {
//		return (TweetArrayAdapter) aTweets;
//	}
	
	// Delegate the adding to the internal adapter - is prefer.
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	// Create this interface for fragment to communicate with Activity
	// Fragment can fire listener events on an activity via an interface
	public interface OnFragmentTweetsListItemSelectedListener {
		public void onProfileImageClicked(User userJsonObject);  
//		public void onProfileImageClicked(Tweet tweetJsonObject);  


	}
	
 
}
