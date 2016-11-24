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

		initView();// 控件的初始化
		initDatas();// 数据的处理

		lv.setOnItemClickListener(new OnItemClickListener() {
			// listview的点击事件

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				registerForContextMenu(lv);
				// 注册
				MainUI.this.position = position;
				// position 成为全局变量
			}
		});
		if (getIntent().getStringExtra("name") != null) {
			// 還迎登陸

			inflater = this.getLayoutInflater();
			view = inflater.inflate(R.layout.toast, null);
			toast = new Toast(this);
			toast.setView(view);
			toast.setDuration(5000);
			toast.show();
		}

		if (getIntent().getStringExtra("back") != null) {
			// 添加日記
			inflater = this.getLayoutInflater();
			view = inflater.inflate(R.layout.toast_dri, null);
			toast = new Toast(this);
			toast.setView(view);
			toast.setDuration(5000);
			toast.show();
		}

		System.out.println("日记的个数" + adapter.getCount());
		if (adapter.getCount() == 0) {
			// 目前沒有日誌
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
		// 在listview中显示
		try {
			Cursor c = dbread.query(Db.TABLE_NOTE, null, Db.OWNER + "=?",
					new String[] { getIntent().getIntExtra("id", -1) + "" },
					null, null, null);
			// 查询数据

			System.out.println(getIntent().getIntExtra("id", -1) + "list");
			datas = new ArrayList<Bean>();

			while (c.moveToNext()) {

				Bean bean = new Bean(c.getString(c.getColumnIndex(Db.DAY)),
						c.getString(c.getColumnIndex(Db.TIME)), c.getString(c
								.getColumnIndex(Db.CONTEXT)));
				datas.add(bean);
				// 添加数据
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new MyAdapter(this, datas);
		lv.setAdapter(adapter);
		// 显示到listview中
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 标题栏菜单
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.listmain, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.add) {
			// 添加按钮
			Intent i = new Intent(MainUI.this, aty_add.class);
			i.putExtra("id", getIntent().getIntExtra("id", -1));

			startActivity(i);
			finish();
		} else

		if (item.getItemId() == R.id.change) {
			// 编辑按钮
			this.item = item;
			if (item.getTitle().equals("编辑")) {

				item.setTitle("删除");
				adapter.setIscheck(true);
				adapter.notifyDataSetChanged();

			} else {
				item.setTitle("编辑");
				//
				adapter.remove();
				adapter.setIscheck(false);

			}

		} else if (item.getItemId() == R.id.ad_list) {
			// 管理按钮
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

		menu.setHeaderTitle("操作");
		menu.add(0, 1, Menu.NONE, "修改");
		menu.add(0, 2, Menu.NONE, "删除");

		menu.add(0, 3, Menu.NONE, "删除所有");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		String title = null;// item里的内容
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
			item.setTitle("编辑");
			adapter.setIsBack(true);
			adapter.notifyDataSetChanged();
		} else {

			super.onBackPressed();
		}
	}

}
