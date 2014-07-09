package com.codepath.apps.basictwitter;

import org.json.JSONObject;

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
import com.codepath.apps.basictwitter.fragments.TweetsListFragment.OnFragmentTweetsListItemSelectedListener;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements
		OnFragmentTweetsListItemSelectedListener {

	private static final int GET_RESULT_TEXT = 10;
	// private final String TAG = this.getClass().getName();
	private final String TAG = "TimelineActivity";
	protected User mUser;
	private final String KEY_SCREEN_NAME="screen_name";
	

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
						new FragmentTabListener<HomeTimelineFragment>(
								R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tabHome);
		actionBar.selectTab(tabHome); // select the 1st tab as default

		Tab tabMentions = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_mentions)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>(
								R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tabMentions);
	}

	public void onProifleView(MenuItem mi) {
		Log.d("debug", "onProifleView() " + TAG);
		loadProfileAndStartProfileActity();
	}

	private void loadProfileAndStartProfileActity() {
		Log.d("debug", "loadProfile()  - " + TAG);
		TwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				User u = User.fromJSON(jsonObject);
				setUser(u);
				callProfileActivity();
			}
		});
	}
	
	protected void setUser(User user) {
		Log.d("debug", "setUser()  - " + TAG);
		mUser = user;
	}
	
	protected void callProfileActivity( ) {
		Log.d("debug", "callProfileActivity()  - " + TAG);
		Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
		i.putExtra("sign_in_user", mUser);
		startActivity(i);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		// return true;
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_compose_tweet:
			startActivityForResult(new Intent(TimelineActivity.this,
					ComposeTweetActivity.class), GET_RESULT_TEXT);
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
     			Log.d("debug", "onActivityResult()  - " + TAG + "tweet data= " +returnData);
			}
		}
	}

	// show another twitter's profile
	@Override
	public void onProfileImageClicked(User selectedUser) {
		// public void onProfileImageClicked(Tweet selectedTweet) {
		Log.d("debug",  "onProfileImageClicked() " + TAG);
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra(KEY_SCREEN_NAME, selectedUser.getScreenName());
		i.putExtra("selected_tweet_user", selectedUser);
		startActivity(i);
	}

}
