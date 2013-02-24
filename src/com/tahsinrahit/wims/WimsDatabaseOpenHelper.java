/**
 * 
 */
package com.tahsinrahit.wims;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

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
		String sql = "CREATE TABLE IF NOT EXIST " + TABLE_NAME + " (id IN ) ";
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
