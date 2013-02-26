package com.tahsinrahit.wims;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        WimsDatabaseOpenHelper db_helper = new WimsDatabaseOpenHelper(MainActivity.this);
		SQLiteDatabase db = db_helper.getReadableDatabase();
		String[] columns = {"item","location"} ;
		Cursor c = db.query("item_location", columns , null , null, null, null, null);
        int i = 0;
        String[] result = new String[c.getCount()];
    	c.moveToFirst();
    	/*c.moveToNext();
    	String a = c.getString(0);
    	TextView test = (TextView)findViewById(R.id.test);
		test.setText(a);
		*/
    	while( i < c.getCount()){
			result[i] = c.getString(0);
        	i++;
        	c.moveToNext();
        }

		ListView listView = (ListView) findViewById(R.id.search_result);
		
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, result);
		// Assign adapter to ListView
		listView.setAdapter(adapter);
		
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	Intent add_item_intent = new Intent(this, AddItemActivity.class);
    	startActivity(add_item_intent);
    	
    	return super.onOptionsItemSelected(item);
    }
    
}
