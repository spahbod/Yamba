package com.my.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;


public class TimelineActivity extends Activity {
	DbHelper dbHelper;
	SQLiteDatabase db;
	Cursor cursor; 
	ListView listTimeline; 
	TimelineAdapter adapter; 
	static final String[] FROM = { DbHelper.C_CREATED_AT, DbHelper.C_USER,DbHelper.C_TEXT }; 
	static final int[] TO = { R.id.textCreatedAt, R.id.textUser, R.id.textText }; 
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.timeline_basic);
	listTimeline = (ListView) findViewById(R.id.listTimeline);
	dbHelper = new DbHelper(this);
	db = dbHelper.getReadableDatabase();
	}

@Override
public void onDestroy() {
	super.onDestroy();
	cursor.close();
	db.close();
	}

@Override
protected void onResume() {
	super.onResume();
	cursor = db.query(DbHelper.TABLE, null, null, null, null, null,DbHelper.C_CREATED_AT + " DESC");
	
	adapter = new TimelineAdapter(this, cursor); 
	listTimeline.setAdapter(adapter);

	}
}