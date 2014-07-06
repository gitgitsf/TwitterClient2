package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
  
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String followersCount;
	private String friendsCount;
	private String description;
	
	
	// User.fromJSON(...)
	public static User fromJSON(JSONObject json) {
		User u = new User();
		try {
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImageUrl = json.getString("profile_image_url");
			u.followersCount = json.getString("followers_count");
			u.friendsCount = json.getString("friends_count");
			u.description = json.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getTagline() {
//		return getString("description");
		return description;
	}


	public String getFollowersCount() {
		return followersCount;
	}

	public String getFriendsCount() {
		return friendsCount;
	}

//	public String getDescription() {
//		return description;
//	}
//	
	
}
