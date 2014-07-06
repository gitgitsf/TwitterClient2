package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {
	
	private static final int GET_RESULT_TEXT = 10;
	private final String TAG = this.getClass().getName();
//	private TwitterClient client;
//	private ArrayList<Tweet> tweets;
//	private ArrayAdapter<Tweet> aTweets;
//	private ListView lvTweets;
//	private TweetsListFragment fragmentTweetsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tabHome = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home", HomeTimelineFragment.class));
								

		actionBar.addTab(tabHome);
		actionBar.selectTab(tabHome); // select the 1st tab as default

		Tab tabMentions = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, 
			    		"mentions", MentionsTimelineFragment.class));

		actionBar.addTab(tabMentions);
	}
	
	public void onProifleView(MenuItem mi) {
		Log.d("DEBUG", "onProifleView");
		Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
		startActivity(i);
	}
	
    public  void onListItemClick(View v ) {
    
		Log.d("debug", TAG + "position= ?");
		Toast.makeText(this, "click profile image", Toast.LENGTH_SHORT).show();
//	    super.onListItemClick(l, v, position, id);
//
//	    Intent intent = new Intent(this, Detail.class);
//	    intent.putExtra("test", "test");
//	    startActivity(intent);
	}
//	protected void customLoadMoreDataFromApi(int totalItemsCount) {
//		Log.d("DEBUG", "customLoadMoreDataFromApi");
//		// get the last tweet in the tweets array, use the totalItemsCount that
//		// is maintained by EndlessScrollListerer.java
//		if (!tweets.isEmpty()) {
//			Tweet lastRetrieveTweet = tweets.get(totalItemsCount - 1);
//			sinceId = lastRetrieveTweet.getUid();
//			maxId = sinceId -1;
//			getAndDisplayMoreTimeline();
//		}
//		    
//	}
//	
//	
//	private void getAndDisplayMoreTimeline() {
//		Log.d("debug", "populateTimeline()- TimelineActivity.java");
//        client.getHomeTimeline(sinceId, maxId, new JsonHttpResponseHandler() {
//        	@Override
//        	public void onSuccess(int arg0, JSONArray jsonArray) {
//        		super.onSuccess(arg0, jsonArray);
////        		aTweets.addAll(Tweet.fromJSONArray(jsonArray));
//        		fragmentTweetsList.addAll(Tweet.fromJSONArray(jsonArray));
//        		Log.d("debug", "arg0=" + arg0);
//		        Log.d("debug", jsonArray.toString());
//        	}
//        	
//        	@Override
//        	public void onFailure( Throwable e, String s) {
//        		Log.d("debug", e.toString());
//        		Log.d("debug", s.toString());
//        	}
//        });	
//		
//	}
//	
	
//	private void populateTimeline() {
//		Log.d("debug", "populateTimeline()- TimelineActivity.java");
//        client.getHomeTimeline(new JsonHttpResponseHandler() {
//        	@Override
//        	public void onSuccess(int arg0, JSONArray json) {
//        		super.onSuccess(arg0, json);
//        		fragmentTweetsList.addAll(Tweet.fromJSONArray(json));
////        		aTweets.addAll(Tweet.fromJSONArray(json));
//        		Log.d("debug", "arg0=" + arg0);
//		        Log.d("debug", json.toString());
//        	}
//        	
//        	
//        	@Override
//        	public void onFailure( Throwable e, String s) {
//        		Log.d("debug", e.toString());
//        		Log.d("debug", s.toString());
//        	}
//        });		
//	}
//	
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
