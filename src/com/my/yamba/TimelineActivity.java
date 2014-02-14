package com.my.yamba;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

public class TimelineActivity extends Activity {
	DbHelper dbHelper;
	SQLiteDatabase db;
	Cursor cursor;
	TextView textTimeline;
	private TextView Count;
	int timeCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_basic);
		
		textTimeline = (TextView) findViewById(R.id.textTimeline);
		Count = (TextView)findViewById(R.id.count); 
		Count.setText(Integer.toString(000)); 
		
		dbHelper = new DbHelper(this); 
		db = dbHelper.getReadableDatabase(); 
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.cursor.close();
		db.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		cursor = db.query(DbHelper.TABLE, null, null, null, null, null,DbHelper.C_CREATED_AT + " DESC"); 
		//startManagingCursor(cursor); 
		String user, text, output;
			while (cursor.moveToNext()) { 
				user = cursor.getString(cursor.getColumnIndex(DbHelper.C_USER)); 
				text = cursor.getString(cursor.getColumnIndex(DbHelper.C_TEXT));
				long data =  cursor.getLong(cursor.getColumnIndex(DbHelper.C_CREATED_AT));
				output = String.format("%s: %s: %s\n", user,text, DateUtils.getRelativeTimeSpanString(data)); 
				textTimeline.append(output); 
				timeCount++;
			}
			Count.setText(Integer.toString(timeCount)); 	
	}

}
