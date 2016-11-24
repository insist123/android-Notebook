package com.ws.notebook.main;

import java.util.ArrayList;
import java.util.List;

import com.example.notebook.R;
import com.ws.notebook.bean.AdminAdapter;
import com.ws.notebook.bean.AdminBean;
import com.ws.notebook.data.Db;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class Admin extends Activity {

	private ListView lv1;
	private Db db;
	private SQLiteDatabase dbread;
	private AdminAdapter ad;
	private List<AdminBean> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);
		initview();
		initdatas();

	}

	private void initview() {
		// TODO Auto-generated method stub
		lv1 = (ListView) findViewById(R.id.admin_lv);
		db = new Db(this);
		dbread = db.getReadableDatabase();
	}

	private void initdatas() {
		// TODO Auto-generated method stub
		Cursor c = dbread.query(Db.TABLE_USER, null, null, null, null, null,
				null);
		datas = new ArrayList<AdminBean>();
		while (c.moveToNext()) {
			AdminBean bean = new AdminBean(c.getString(c
					.getColumnIndex(Db.NAME)), c.getString(c
					.getColumnIndex(Db.PASSWORD)));
			datas.add(bean);
		}
		ad = new AdminAdapter(this, datas);
		lv1.setAdapter(ad);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main_ad, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		startActivity(new Intent(Admin.this, MainActivity.class));
		finish();
		return super.onMenuItemSelected(featureId, item);
	}

}
