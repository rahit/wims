package com.tahsinrahit.wims;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		
		Button add = (Button)findViewById(R.id.item_add);
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Getting Values
				EditText item = (EditText)findViewById(R.id.item_name);
				EditText location = (EditText)findViewById(R.id.item_location);
				//Setting Date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String cur_date = sdf.format(new Date());
				
				WimsDatabaseOpenHelper db_helper = new WimsDatabaseOpenHelper(AddItemActivity.this);
				SQLiteDatabase db = db_helper.getWritableDatabase();
				//Key-Value pair
				ContentValues vals = new ContentValues();
				vals.put("item",item.getText().toString());
				vals.put("location", location.getText().toString());
				vals.put("created", cur_date);
				vals.put("modified", cur_date);
				
				db.insert("item_location", null, vals);
				Toast t = Toast.makeText(getApplicationContext(), item.getText().toString()+" successfully added",Toast.LENGTH_LONG );
				t.show();
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

}
