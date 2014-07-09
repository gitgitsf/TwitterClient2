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
	String mScreenName;

	@Override
	protected void onCreate(Bundle saveInstBundle) {
		super.onCreate(saveInstBundle);
		Log.d("debug", "onCreate()  - " + TAG);
		setContentView(R.layout.activity_profile);
//		mScreenName = getIntent().getStringExtra("screen_name"); 
//		checkForTwitterUser();
		populateProifleHeader(mUser);
	}
	
	
	private void checkForTwitterUser() {
		Log.d("debug", "checkForTwitterUser()  - " + TAG);
		User tweetUser = (User) getIntent().getSerializableExtra("selected_tweet_user"); 
		if (tweetUser != null) {
			mUser = tweetUser;
		} else {
			mUser = (User) getIntent().getSerializableExtra("sign_in_user"); 
		} 	
	}
 
	protected void populateProifleHeader(User user) {
		Log.d("debug", "populateProifleHeader()  - " + TAG);
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
		
		getActionBar().setTitle("@" +  user.getScreenName());
		mScreenName = user.getScreenName();
	}
	
	public String getScreenName() {
		return mScreenName;
	}
	
	@Override 
	public void onNewUserProfileCreated() {
		Log.d("debug", TAG + " onNewUserProfileCreated ");
		checkForTwitterUser();
		mScreenName = mUser.getScreenName(); 
		 
	}
	
	// show another twitter's profile
	// No action when profile image is clicked when in your profile
	@Override
	public void onProfileImageClicked(User selectedUser) {
		Log.d("debug", TAG + " onProfileImageClicked ");
		
	}

}

