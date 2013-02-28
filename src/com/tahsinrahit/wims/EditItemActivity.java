package com.tahsinrahit.wims;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends Activity {
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
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Setting Date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String cur_date = sdf.format(new Date());
				
				WimsDatabaseOpenHelper db_helper = new WimsDatabaseOpenHelper(EditItemActivity.this);
				SQLiteDatabase db = db_helper.getWritableDatabase();
				//Key-Value pair
				ContentValues vals = new ContentValues();
				vals.put("item",item.getText().toString());
				vals.put("location", location.getText().toString());
				vals.put("created", cur_date);
				vals.put("modified", cur_date);
				db.update("item_location", vals, "item=? AND location=?", new String[]{cur_item,cur_location});
				Toast t = Toast.makeText(getApplicationContext(), item.getText().toString()+" successfully updated",Toast.LENGTH_LONG );
				t.show();
				finish();
			}
		});
		

		del.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WimsDatabaseOpenHelper db_helper = new WimsDatabaseOpenHelper(EditItemActivity.this);
				SQLiteDatabase db = db_helper.getWritableDatabase();
				db.delete("item_location", "item=? AND location=?", new String[]{cur_item,cur_location});
				Toast t = Toast.makeText(getApplicationContext(), "Item successfully deleted",Toast.LENGTH_LONG );
				t.show();
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	Intent add_item_intent = new Intent(this, AddItemActivity.class);
    	startActivity(add_item_intent);
    	
    	return super.onOptionsItemSelected(item);
    }
}
