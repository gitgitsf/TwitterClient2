package com.codepath.apps.basictwitter;



import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.TweetsListFragment.OnFragmentTweetsListItemSelectedListener;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment.OnProfileChangedListener;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity 
      implements OnFragmentTweetsListItemSelectedListener, OnProfileChangedListener  {
	
	private final String TAG = "ProfileActivity";
	
	User mUser;

	@Override
	protected void onCreate(Bundle saveInstBundle) {
		super.onCreate(saveInstBundle);
		Log.d("debug", "onCreate()  - " + TAG);
		setContentView(R.layout.activity_profile);
		
		checkForAnotherTwitterUser();
		loadProfile();
		
//		populateProifleHeader(mUser);
	}
	
	
	private void checkForAnotherTwitterUser() {
		Log.d("debug", "checkForAnotherTwitterUser()  - " + TAG);
		
		User tweetUser = (User) getIntent().getSerializableExtra("selected_tweet_user"); 
		if (tweetUser != null) {
			Log.d("debug", " selected user prifile is passed  -  " + TAG);
			mUser = tweetUser;
			getActionBar().setTitle("@" +  mUser.getScreenName());
			populateProifleHeader(mUser);
		} else {
//			mUser = (User) getIntent().getSerializableExtra("sign_in_user"); 
			Log.d("debug", " no selected user prifile is passed  -  " + TAG);
		} 	
		
	}

	private void loadProfile() {
		Log.d("debug", "loadProfile()  -  " + TAG);
         TwitterApp.getRestClient().getMyInfo(
        		 new JsonHttpResponseHandler() {
        			@Override
        			public void onSuccess(JSONObject jsonObject) {
        				User u = User.fromJSON(jsonObject);
        				mUser = u;
//        				getActionBar().setTitle("@" +  u.getScreenName());
//        				populateProifleHeader(u);
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
	
	@Override 
	public void onNewUserProfileCreated() {
		Log.d("debug", TAG + " onProfileImageClicked ");
		 
	}
	
	// show another twitter's profile
	// No action when profile image is clicked when in your profile
	@Override
	public void onProfileImageClicked(User selectedUser) {
		
	}

}

