package com.ws.notebook.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.notebook.R;
import com.ws.notebook.data.Db;

public class Aty_complie extends Activity {
	private EditText ed_complie;
	private Db db;
	private SQLiteDatabase dbwrite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 标题栏
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complie);
		ed_complie = (EditText) findViewById(R.id.ed_complie);

		ed_complie = (EditText) findViewById(R.id.ed_complie);
		String title = getIntent().getStringExtra("content");
		ed_complie.setText(title);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.complie, menu);
		return super.onCreateOptionsMenu(menu);
		// 绑定菜单
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		db = new Db(this);
		dbwrite = db.getWritableDatabase();
		try {// 修改数据
			ContentValues cv = new ContentValues();
			cv.put(Db.CONTEXT, ed_complie.getText().toString());
			cv.put(Db.DAY, getday1());
			cv.put(Db.TIME, gettime1());
			cv.put(Db.OWNER, getIntent().getIntExtra("complie", -1));
			System.out.println("修改的id" + getIntent().getIntExtra("complie", -1)
					+ "");
			// cv.put(Db.OWNER, getIntent().getStringExtra("content"));
			dbwrite.update(Db.TABLE_NOTE, cv, Db.CONTEXT + "=?",
					new String[] { getIntent().getStringExtra("content") });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dbwrite.close();

		Intent i = new Intent(Aty_complie.this, MainUI.class);
		i.putExtra("id", getIntent().getIntExtra("complie", -1));
		startActivity(i);
		finish();

		return true;
	}

	public String getday1() {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		Date date = new Date();
		String str1 = format.format(date);
		return str1;

	}

	public String gettime1() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		String str2 = format.format(date);
		return str2;

	}
}
