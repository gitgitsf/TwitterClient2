package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;


public class ComposeTweetActivity extends Activity {

	private static final String TAG = "ComposeTweetActivity";
	private EditText etComposeTweet;
	private TextView tvNbrChar;
	private Button btnTweet;
	
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		etComposeTweet = (EditText) findViewById(R.id.etComposeTweet);
		client = TwitterApp.getRestClient();
		tvNbrChar = (TextView) findViewById(R.id.tvNbrChar);	
		btnTweet = (Button) findViewById(R.id.btnTweet);
        
        
	}
	public void postNewTweet(View v) {
		Log.d(TAG, "postNewTweet(View v)");
		int charLeft =  (etComposeTweet.getText().toString()).length();
		//do it onKeyUp
		tvNbrChar.setText(""+ charLeft ); 
		if (charLeft == 0) {
			toastMessage("Please enter some text!");
			return;
		}
		else if (charLeft > 140) {
			toastMessage("Your tweet is too long! Max 140 chars.");
			return;
		}
		
		// Sent a tweet to Twitter -use POST statuses/update
		sendToTwitter(etComposeTweet.getText().toString());
		 
	}
	private void toastMessage(String string) {
		Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
		
	}
	

	private void sendToTwitter(String myNewTweet) {
		Log.d("debug", "sendToTwitter() ");
        client.postUpdate(myNewTweet,  new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int statusCode, JSONObject jsonTweetResponse) {
				Log.d("DEBUG", "Called onSuccess() in ComposeTweet."); 
				Log.d("debug", " jsonTweetResponse===" +jsonTweetResponse.toString());
				Intent i = new Intent(getApplicationContext(), TimelineActivity.class);
	            startActivity(i);
			}	

        	@Override
        	public void onFailure( Throwable e, String s) {
        		Log.d("debug", e.toString());
        		Log.d("debug", s.toString());
        		toastMessage("Unable to post new tweet to Twitter");
        	}
        });	
		
	}
}
