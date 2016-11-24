package com.ws.notebook.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notebook.R;
import com.ws.notebook.data.Db;

public class aty_add extends Activity {
	private EditText ed_add;
	private Db db;
	private SQLiteDatabase dbwrite;
	private Calendar c;
	private LayoutInflater inflater;
	private View view;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_add);
		ed_add = (EditText) findViewById(R.id.ed_add);
		db = new Db(this);
		dbwrite = db.getWritableDatabase();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		//标题栏
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.addmain, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		try {
			//添加数据
			ContentValues cv = new ContentValues();
			cv.put(Db.CONTEXT, ed_add.getText().toString());
			cv.put(Db.TIME, gettime());
			cv.put(Db.DAY, getday());
			cv.put(Db.OWNER, getIntent().getIntExtra("id", -1));
			System.out.println(getIntent().getIntExtra("id", -1) + "add");

			dbwrite.insert(Db.TABLE_NOTE, null, cv);
			db.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		dbwrite.close();
		Intent i2 = new Intent(aty_add.this, MainUI.class);
		i2.putExtra("id", getIntent().getIntExtra("id", -1));
		//把id传回去
		i2.putExtra("back", "2");
		startActivity(i2);
		finish();
		return true;
	}

	public String getday() {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		Date date = new Date();
		String str = format.format(date);
		return str;

	}

	public String gettime() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		String str = format.format(date);
		return str;

	}

}
