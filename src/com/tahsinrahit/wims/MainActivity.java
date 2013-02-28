package com.tahsinrahit.wims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.net.sip.SipAudioCall.Listener;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.ClipData.Item;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Cursor c;
	public SimpleAdapter adapter ;
	public SimpleCursorAdapter mAdapter ;
	ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);    
		listView = (ListView) findViewById(R.id.search_result);
		
        generateList(null);
        EditText search_keyword = (EditText)findViewById(R.id.search_box);
        search_keyword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				MainActivity.this.adapter.getFilter().filter(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
		generateList(null);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()) {
        case R.id.action_settings:
        	Intent add_item_intent = new Intent(this, AddItemActivity.class);
        	startActivity(add_item_intent);
        	break;
        case R.id.help:
        	Toast t = Toast.makeText(getApplicationContext(), "This is a beta virsion of this app. Help is not ready yet",Toast.LENGTH_LONG );
    		t.show();
    		break;
        default:
        }	
    	return super.onOptionsItemSelected(item);
    }
    
    
    private boolean generateList(String keyword) {
        ArrayList<Map<String, String>> list = buildList(keyword);
        String[] from = {"item", "location"};
        int[] to = {android.R.id.text1, android.R.id.text2};
		this.adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);
		//mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c, from, to, 1);
		listView.setAdapter(this.adapter);	
		listView.setTextFilterEnabled(true);
		
		// Placing onClinck listener to each listItem to trigger edit activity
		OnItemClickListener listener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TextView item_text_view = (TextView) view.findViewById(android.R.id.text1);
				TextView location_text_view = (TextView) view.findViewById(android.R.id.text2);
				
				Intent edit_item_intent = new Intent(MainActivity.this,EditItemActivity.class);
				edit_item_intent.putExtra("ITEM", item_text_view.getText().toString() );
				edit_item_intent.putExtra("LOCATION", location_text_view.getText().toString());
				startActivity(edit_item_intent);
			}
		};
		listView.setOnItemClickListener(listener);
		return true;
	}
    
    
    private ArrayList<Map<String, String>> buildList(String keyword) {
    	ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

        WimsDatabaseOpenHelper db_helper = new WimsDatabaseOpenHelper(MainActivity.this);
		SQLiteDatabase db = db_helper.getReadableDatabase();
		String[] columns = {"item","location"} ;
		if(keyword != null)
			c = db.query("item_location", columns , "item LIKE ?", new String[]{"%"+keyword+"%"}, null, null, null);
		else
			c = db.query("item_location", columns , null, null, null, null, null);
		int i = 0, result_count = c.getCount();
    	c.moveToFirst();
    	while( i < result_count){
			list.add(putValue(c.getString(0), c.getString(1)));
        	i++;
        	c.moveToNext();
        }
		return list;
	}
    
    private HashMap<String, String> putValue(String item, String location ) {
		HashMap<String, String> val = new HashMap<String, String>();
		val.put("item", item);
		val.put("location", location);    	
    	return val;
	}
    
    
}
