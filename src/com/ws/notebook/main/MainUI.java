package com.ws.notebook.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notebook.R;
import com.ws.notebook.bean.Bean;
import com.ws.notebook.bean.MyAdapter;
import com.ws.notebook.data.Db;

public class MainUI extends Activity {

	private LayoutInflater inflater;
	private View view;
	private Toast toast;
	private Button btn_add;
	private ListView lv;
	private Db db;
	private SQLiteDatabase dbread, dbwrite;
	private List<Bean> datas;
	private MyAdapter adapter;
	private int position;
	private CheckBox ck;
	private MenuItem item;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_main);

		initView();// �ؼ��ĳ�ʼ��
		initDatas();// ���ݵĴ���

		lv.setOnItemClickListener(new OnItemClickListener() {
			// listview�ĵ���¼�

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				registerForContextMenu(lv);
				// ע��
				MainUI.this.position = position;
				// position ��Ϊȫ�ֱ���
			}
		});
		if (getIntent().getStringExtra("name") != null) {
			// ߀ӭ���

			inflater = this.getLayoutInflater();
			view = inflater.inflate(R.layout.toast, null);
			toast = new Toast(this);
			toast.setView(view);
			toast.setDuration(5000);
			toast.show();
		}

		if (getIntent().getStringExtra("back") != null) {
			// �����ӛ
			inflater = this.getLayoutInflater();
			view = inflater.inflate(R.layout.toast_dri, null);
			toast = new Toast(this);
			toast.setView(view);
			toast.setDuration(5000);
			toast.show();
		}

		System.out.println("�ռǵĸ���" + adapter.getCount());
		if (adapter.getCount() == 0) {
			// Ŀǰ�]�����I
			inflater = this.getLayoutInflater();
			view = inflater.inflate(R.layout.toast_no, null);
			toast = new Toast(this);
			toast.setView(view);
			toast.setDuration(5000);
			toast.show();
		}
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.lv);
		db = new Db(this);
		dbread = db.getReadableDatabase();
		dbwrite = db.getWritableDatabase();

	}

	private void initDatas() {
		// ��listview����ʾ
		try {
			Cursor c = dbread.query(Db.TABLE_NOTE, null, Db.OWNER + "=?",
					new String[] { getIntent().getIntExtra("id", -1) + "" },
					null, null, null);
			// ��ѯ����

			System.out.println(getIntent().getIntExtra("id", -1) + "list");
			datas = new ArrayList<Bean>();

			while (c.moveToNext()) {

				Bean bean = new Bean(c.getString(c.getColumnIndex(Db.DAY)),
						c.getString(c.getColumnIndex(Db.TIME)), c.getString(c
								.getColumnIndex(Db.CONTEXT)));
				datas.add(bean);
				// �������
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new MyAdapter(this, datas);
		lv.setAdapter(adapter);
		// ��ʾ��listview��
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// �������˵�
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.listmain, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.add) {
			// ��Ӱ�ť
			Intent i = new Intent(MainUI.this, aty_add.class);
			i.putExtra("id", getIntent().getIntExtra("id", -1));

			startActivity(i);
			finish();
		} else

		if (item.getItemId() == R.id.change) {
			// �༭��ť
			this.item = item;
			if (item.getTitle().equals("�༭")) {

				item.setTitle("ɾ��");
				adapter.setIscheck(true);
				adapter.notifyDataSetChanged();

			} else {
				item.setTitle("�༭");
				//
				adapter.remove();
				adapter.setIscheck(false);

			}

		} else if (item.getItemId() == R.id.ad_list) {
			// ����ť
			startActivity(new Intent(MainUI.this, Admin.class));
			finish();
		} else {
			onContextItemSelected(item);
		}
		return true;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// contextmenu
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("����");
		menu.add(0, 1, Menu.NONE, "�޸�");
		menu.add(0, 2, Menu.NONE, "ɾ��");

		menu.add(0, 3, Menu.NONE, "ɾ������");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		String title = null;// item�������
		if (adapter.getItem(position) != null) {
			title = adapter.getItem(position).getContent();
		}

		switch (item.getItemId()) {

		case 1:
			Intent i = new Intent(MainUI.this, Aty_complie.class);
			i.putExtra("complie", getIntent().getIntExtra("id", -1));
			i.putExtra("content", title);
			startActivity(i);
			finish();
			break;
		case 2:

			System.out.println(position);

			adapter.notifyDataSetChanged();

			System.out.println(title);

			dbwrite.delete(Db.TABLE_NOTE, Db.CONTEXT + "=?",
					new String[] { title });
			datas.remove(position);
			break;

		case 3:
			SQLiteDatabase write = db.getWritableDatabase();
			write.delete(Db.TABLE_NOTE, null, null);
			write.close();
			datas.clear();
			adapter.notifyDataSetChanged();

			break;

		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	public void onBackPressed() {

		if (adapter.isIscheck()) {
			adapter.setIscheck(false);
			item.setTitle("�༭");
			adapter.setIsBack(true);
			adapter.notifyDataSetChanged();
		} else {

			super.onBackPressed();
		}
	}

}
