package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;


public class ComposeTweetActivity extends Activity {

	private static final String TAG = "ComposeTweetActivity";
	private static final int MAX_LENGTH = 140;
	
	private EditText etComposeTweet;
	private TextView tvNbrChar;
	private Button btnTweet;
	
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
	 
        setup();
        displayNumberOfCharsEnter();
        
	}
	
	private void setup() {
		etComposeTweet = (EditText) findViewById(R.id.etComposeTweet);
		tvNbrChar = (TextView) findViewById(R.id.tvNbrChar);	
		btnTweet = (Button) findViewById(R.id.btnTweet);
		
		client = TwitterApp.getRestClient();
	}
	
	private void displayNumberOfCharsEnter() {
		etComposeTweet.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ( s.length() >= 140) {
					Toast.makeText(ComposeTweetActivity.this, "You've reached the 140 character"
							+ " limit", Toast.LENGTH_LONG).show(); 
					tvNbrChar.setText(MAX_LENGTH - s.length() + " left"); 
				}
				else {
					tvNbrChar.setText(MAX_LENGTH - s.length() + " left"); 
//					tvNbrChar.setText(MAX_LENGTH + "-" + s.length() + " = " +  
//							(MAX_LENGTH - s.length()) + " left"); 
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void afterTextChanged(Editable s) { }
		});

	}
	
	
	
	
	
	// handle the Tweet button, see xml for method name
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
	

	private void sendToTwitter(final String myNewTweet) {
		Log.d("debug", "sendToTwitter() ");
        client.postUpdate(myNewTweet,  new JsonHttpResponseHandler() {
        	@Override
        	public void onSuccess(int statusCode, JSONObject jsonTweetResponse) {
				Log.d("DEBUG", "Called onSuccess() in ComposeTweet."); 
				//Intent i = new Intent(getApplicationContext(), TimelineActivity.class);
	            //startActivity(i);
				// when return to TimelineActivity.java to see the new tweet, it is better
				// to have TimelineActivity to start intent with result and have this activity
				// return with result.
	            Intent i = new Intent();
				i.putExtra("myNewTweet", myNewTweet);
				setResult(RESULT_OK, i);
				finish(); 
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
