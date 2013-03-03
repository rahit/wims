package com.tahsinrahit.wims;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends Activity {
	private static WimsDatabaseOpenHelper db_helper;
	private static SQLiteDatabase db;
	private static String item_id;
	private static EditText item;
	private static EditText location;
	private Button add;
	private Button del;
	private String cur_item;
	private String cur_location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		cur_item = getIntent().getExtras().getString("ITEM");
		cur_location = getIntent().getExtras().getString("LOCATION");
		add = (Button)findViewById(R.id.update_button);
		del = (Button)findViewById(R.id.delete_button);
		item = (EditText)findViewById(R.id.item_edit);
		location = (EditText)findViewById(R.id.location_edit);
		item.setText(cur_item);
		location.setText(cur_location);

		db_helper = new WimsDatabaseOpenHelper(EditItemActivity.this);
		db = db_helper.getWritableDatabase();
		
		Cursor ca = db.query(true, "item_location", new String[]{"_id"}, "item=? AND location=?", new String[]{cur_item,cur_location}, null, null, null, "1");
		ca.moveToFirst();
		item_id = ca.getString(0);
		
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Setting Date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String cur_date = sdf.format(new Date());
				
				//Key-Value pair
				ContentValues vals = new ContentValues();
				vals.put("item",item.getText().toString());
				vals.put("location", location.getText().toString());
				vals.put("modified", cur_date);
				db.update("item_location", vals, "_id=?", new String[]{item_id});
				Toast t = Toast.makeText(getApplicationContext(), item.getText().toString()+" successfully updated",Toast.LENGTH_LONG );
				t.show();
				finish();
			}
		});
		

		del.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				generate_dialoge();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
    
    private void generate_dialoge() {
    	AlertDialog.Builder delete_alert = new AlertDialog.Builder(this);
    	OnClickListener yes = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				delete_item();
			}
		};
		OnClickListener no = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		};    	
    	
    	delete_alert.setMessage("Delete " + item.getText().toString() + " ?");
    	delete_alert.setPositiveButton(R.string.delete_text, yes);
    	delete_alert.setNegativeButton(R.string.cancel, no);
    	delete_alert.show();
	}
    
    
    private void delete_item() {
		db.delete("item_location", "_id=?", new String[]{item_id});
		Toast t = Toast.makeText(getApplicationContext(), "Item successfully deleted",Toast.LENGTH_LONG );
		t.show();
		finish();
	}
    
    
}
