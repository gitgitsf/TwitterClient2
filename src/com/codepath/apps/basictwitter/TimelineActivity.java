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
		Log.d("DEBUG", "onProifleView");
		loadProfile();
		Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
		// ? getMyInfo not working
		// it was fine in ProfileActivity
		i.putExtra("sign_in_user", mUser);
		startActivity(i);
	}

	private void loadProfile() {
		Log.d("debug", "loadProfile()  - TimelineActivity ");
		TwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				User u = User.fromJSON(jsonObject);
				mUser = u;
				Log.d("debug", TAG + "return from getMyInfo" + mUser.toString());
			}
		});
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
//				Toast.makeText(this, returnData, Toast.LENGTH_SHORT).show();
			}
		}
	}

	// show another twitter's profile
	@Override
	public void onProfileImageClicked(User selectedUser) {
		// public void onProfileImageClicked(Tweet selectedTweet) {
		Log.d("debug", TAG + " onProfileImageClicked ");
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("selected_tweet_user", selectedUser);
		startActivity(i);
	}

}
