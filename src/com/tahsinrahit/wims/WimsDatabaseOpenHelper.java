/**
 * 
 */
package com.tahsinrahit.wims;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * @author Rahit
 *
 */
public class WimsDatabaseOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "wims";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "item_location";
	
	public WimsDatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY NOT NULL, item VARCHAR(200), location VARCHAR(200), created DATETIME, modified DATETIME ) ";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertItem(SQLiteDatabase db, String item, String location){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String cur_date_time = sdf.format(new Date());
		String sql = "INSERT INTO " + TABLE_NAME + " (item,location,created,modified) values (`" + item + "`, `" + location + "`, `" + cur_date_time + "`, `" + cur_date_time + "`)";
		db.execSQL(sql);
	}

}
