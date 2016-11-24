package com.ws.notebook.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class Aty_res extends Activity {
	private Button res_btn;
	private EditText res_ed_name, res_ed_password, res_ed_aga;
	private Db db;
	private SQLiteDatabase dbwrite;
	private LayoutInflater inflater;
	private View view;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.aty_res);

		res_ed_name = (EditText) findViewById(R.id.res_ed_name);
		res_ed_password = (EditText) findViewById(R.id.res_ed_password);
		res_ed_aga = (EditText) findViewById(R.id.res_ed_aga);
		res_btn = (Button) findViewById(R.id.res_btn);
		res_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!res_ed_password.getText().toString()
						.equals(res_ed_aga.getText().toString())) {
					Toast.makeText(Aty_res.this, "俩次输入错误，请重新输入",
							Toast.LENGTH_SHORT).show();
				} else {
					try {
						db = new Db(Aty_res.this);
						dbwrite = db.getWritableDatabase();
						ContentValues cv = new ContentValues();
						cv.put(Db.NAME, res_ed_name.getText().toString());
						cv.put(Db.PASSWORD, res_ed_password.getText()
								.toString());
						dbwrite.insert(Db.TABLE_USER, null, cv);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					db.close();

				}
				final ProgressDialog pr = new ProgressDialog(Aty_res.this);
				pr.setMessage("正在处理数据，请稍等....");
				pr.setIndeterminate(false);
				pr.setCanceledOnTouchOutside(false);
				new Thread() {

					public void run() {

						try {
							sleep(2500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						pr.dismiss();
					};
				}.start();
				pr.show();
				startActivity(new Intent(Aty_res.this, MainActivity.class));
				inflater = Aty_res.this.getLayoutInflater();
				view = inflater.inflate(R.layout.toast_new, null);
				toast = new Toast(Aty_res.this);
				toast.setView(view);
				toast.setDuration(5000);
				toast.show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		startActivity(new Intent(Aty_res.this, MainActivity.class));
		finish();
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builer = new AlertDialog.Builder(Aty_res.this);
		// 1.它所提示的消息 2.提供的选择
		builer.setTitle("提示").setMessage("您确定要退出当前程序吗？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Aty_res.this.finish();
					}
				}).setNegativeButton("否", null).show();

		return super.onKeyDown(keyCode, event);

	}
}
