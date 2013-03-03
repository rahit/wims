package com.tahsinrahit.wims;

import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.net.sip.SipAudioCall.Listener;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ClipData.Item;
import android.content.DialogInterface.OnClickListener;
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
        case R.id.action_add:
        	Intent add_item_intent = new Intent(this, AddItemActivity.class);
        	startActivity(add_item_intent);
        	break;
        case R.id.action_help:
        	help();
    		break;
        default:
        }	
    	return super.onOptionsItemSelected(item);
    }

    private void help(){

    	AlertDialog.Builder delete_alert = new AlertDialog.Builder(this);
    	
		OnClickListener no = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		};    	
    	
    	delete_alert.setMessage("WIMS(Where Is My Stuff) is an opensource item tracking application for android. Keep track of your valuable items such as pendrive, ID card, important docs, keys etc easily. Read online Documentation for detail.");
    	//delete_alert.setPositiveButton(R.string.delete_text, yes);
    	delete_alert.setNegativeButton(R.string.cancel, no);
    	delete_alert.show();
    }
    
    
    
    private boolean generateList(String keyword) {
        ArrayList<Map<String, String>> list = buildList(keyword);
        String[] from = {"item", "location","modified"};
        int[] to = {R.id.textView1, R.id.textView2, R.id.textView3};
		this.adapter = new SimpleAdapter(this, list, R.layout.listview_item, from, to);
		//mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c, from, to, 1);
		listView.setAdapter(this.adapter);	
		listView.setTextFilterEnabled(true);
		
		// Placing onClinck listener to each listItem to trigger edit activity
		OnItemClickListener listener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TextView item_text_view = (TextView) view.findViewById(R.id.textView1);
				TextView location_text_view = (TextView) view.findViewById(R.id.textView2);
				
				Intent edit_item_intent = new Intent(MainActivity.this,EditItemActivity.class);
				edit_item_intent.putExtra("ID", id);
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
		String[] columns = {"item","location","modified"} ;
		if(keyword != null)
			c = db.query("item_location", columns , "item LIKE ?", new String[]{"%"+keyword+"%"}, null, null, null);
		else
			c = db.query("item_location", columns , null, null, null, null, null);
		int i = 0, result_count = c.getCount();
    	c.moveToFirst();
    	while( i < result_count){
			list.add(putValue(c.getString(0), c.getString(1), c.getString(2)));
        	i++;
        	c.moveToNext();
        }
		return list;
	}
    
    private HashMap<String, String> putValue(String item, String location, String modified ) {
		HashMap<String, String> val = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String m = new String();
		Date d = new Date();
		try {
			d = sdf.parse(modified);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("EEEE, d MMM, yyyy @KK:mm a");
		m = sdf.format(d);
		val.put("item", item);
		val.put("location", location);    
		val.put("modified", m);  	
    	return val;
	}
    
    
}
