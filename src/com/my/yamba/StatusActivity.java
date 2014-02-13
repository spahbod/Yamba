package com.my.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

import com.my.yamba.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatusActivity extends Activity  implements OnClickListener,TextWatcher{
	
	private static final String TAG="StatusActivity";
	private EditText editText;
	private Button updateButton;
	private Button timeButton;
	private TextView textCount;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.status);
		
		editText =(EditText)findViewById(R.id.editText);
		updateButton =(Button)findViewById(R.id.buttonUpdate);
		updateButton.setOnClickListener(this);
		
		timeButton = (Button)findViewById(R.id.time);
		timeButton.setOnClickListener(this);
		
		textCount = (TextView)findViewById(R.id.textCount); 
		textCount.setText(Integer.toString(140)); //
		textCount.setTextColor(Color.BLACK); 
		editText.addTextChangedListener(this);

		
		
		 Log.d(TAG, "START");
	}
		
		class PostToTwitter extends AsyncTask<String, Integer, String>{
			@Override
			protected String doInBackground(String... arg0) {
					try {
							YambaApplication app = ((YambaApplication) getApplication());
							Twitter.Status status = app.getTwitter().updateStatus(arg0[0]);
							return status.text;
						} catch (TwitterException e) {
							Log.e(TAG, e.toString());
							e.printStackTrace();
							return "Failed to post";
						}			
				}
		}
				
	public void onClick(View v){
		Button click = (Button)v;
		int clickId = click.getId();
		
		int timeButtonId = this.timeButton.getId();
		
		if(timeButtonId == clickId ){
			startActivity(new Intent(this, TimelineActivity.class)); 
			
		}else{
		
		  String status = editText.getText().toString();
		    new PostToTwitter().execute(status);
		    Log.d(TAG, "onClicked");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.status, menu); 
		return true; 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { 
		case R.id.itemServiceStart:
			startService(new Intent(this, UpdaterService.class)); 
			break;
		case R.id.itemServiceStop:
			stopService(new Intent(this, UpdaterService.class)); 
			break;	
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsActivity.class)); 
			break;
		}
	return true; 
	}
	
	
	@Override
	public void afterTextChanged(Editable arg0) {
		int count = 140 - arg0.length(); 
		textCount.setText(Integer.toString(count));
		if (count < 10)
			textCount.setTextColor(Color.GREEN);
		if (count < 0)
			textCount.setTextColor(Color.YELLOW);

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
		
}



	