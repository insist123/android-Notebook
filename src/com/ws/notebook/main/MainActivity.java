package com.ws.notebook.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v4.app.ActionBarDrawerToggle;

import com.example.notebook.R;
import com.ws.notebook.data.Db;

public class MainActivity extends Activity {
	private EditText ed_name, ed_password;
	private Button btn_res, btn_log;
	private Db db;
	private SQLiteDatabase dbread;
	private LayoutInflater inflater;
	private View view;
	private Toast toast;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ������Ϊ���ɼ�
		setContentView(R.layout.activity_main);
		ed_name = (EditText) findViewById(R.id.ed_name);
		ed_password = (EditText) findViewById(R.id.ed_password);
		btn_res = (Button) findViewById(R.id.btn_res);
		btn_log = (Button) findViewById(R.id.btn_log);

		btn_res.setOnClickListener(new OnClickListener() {
			// ���ע�ᰴť ��ת��ע��ҳ��
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this, Aty_res.class);
				startActivity(i);
				finish();
			}
		});
		btn_log.setOnClickListener(new OnClickListener() {
			// �����¼��ť ��ѯ��������û��� �����Ƿ���ȷ ��ȷ����ת����ҳ�� ����ȷ ����ʾ
			@Override
			public void onClick(View arg0) {
				db = new Db(MainActivity.this);
				dbread = db.getReadableDatabase();
				Cursor c = dbread.query(Db.TABLE_USER, null, null, null, null,
						null, null);
				try {
					while (c.moveToNext()) {

						String username = c.getString(c.getColumnIndex(Db.NAME));

						if (ed_name.getText().toString().equals(username)) {
							int id = c.getInt(c.getColumnIndex(Db.USER_ID));
							String password = c.getString(2);
							if (ed_password.getText().toString()
									.equals(password)) {
								Intent i = new Intent(MainActivity.this,
										MainUI.class);
								i.putExtra("id", id);
								i.putExtra("name", "1");
								Log.e("���ݹ�ȥ��id", "" + id);
								startActivity(i);
								finish();
							}
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				db.close();
				dbread.close();
				// �Ի���
				final ProgressDialog pr = new ProgressDialog(MainActivity.this);
				pr.setMessage("���ڴ������ݣ����Ե�....");
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
				if (ed_name.getText().toString().equals("")
						&& ed_password.getText().toString().equals("")) {
					inflater = MainActivity.this.getLayoutInflater();
					view = inflater.inflate(R.layout.toast_wrong, null);
					toast = new Toast(MainActivity.this);
					toast.setView(view);
					toast.setDuration(5000);
					toast.show();
				}
			}

		});

	}

	// ���ذ�ť ��ʾ�Ի��� �˳�����
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builer = new AlertDialog.Builder(MainActivity.this);
		// 1.������ʾ����Ϣ 2.�ṩ��ѡ��
		builer.setTitle("��ʾ").setMessage("��ȷ��Ҫ�˳���ǰ������")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						MainActivity.this.finish();
					}
				}).setNegativeButton("��", null).show();

		return super.onKeyDown(keyCode, event);

	}
}