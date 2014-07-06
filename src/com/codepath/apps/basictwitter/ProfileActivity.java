package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle saveInstBundle) {
		super.onCreate(saveInstBundle);
		Log.d("debug", "onCreate()  - ProfileActivity.java");
		setContentView(R.layout.activity_profile);
		loadProfile();
	}

	private void loadProfile() {
		Log.d("debug", "loadProfile()  - ProfileActivity.java");
         TwitterApp.getRestClient().getMyInfo(
        		 new JsonHttpResponseHandler() {
        			@Override
        			public void onSuccess(JSONObject jsonObject) {
        				User u = User.fromJSON(jsonObject);
        				getActionBar().setTitle("@" +  u.getScreenName());
        				populateProifleHeader(u);
        			} 
        		 });		
	}

	protected void populateProifleHeader(User user) {
		
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		
	}
}

