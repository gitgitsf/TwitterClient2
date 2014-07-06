package com.codepath.apps.basictwitter.fragments;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;

public class TweetsListFragment extends Fragment  {
	
	protected ArrayList<Tweet> tweets;
	protected ArrayAdapter<Tweet> aTweets;
	protected ListView lvTweets;
	private long sinceId=1;
	private long maxId =1;
//	private TwitterClient client;
	
	ImageView ivProfileImage;
	FragmentListener listener;
	
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
		ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
	
		
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
	
	// return the adapter to the activity 
//	public TweetArrayAdapter getAdapter() {
//		return (TweetArrayAdapter) aTweets;
//	}
	
	// Delegate the adding to the internal adapter - is prefer.
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public interface FragmentListener {
		public void onProfileImageClick(int position);
	}
}
