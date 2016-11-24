package com.ws.notebook.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.notebook.R;
import com.ws.notebook.data.Db;

public class MyAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Bean> mdatas;
	private boolean ischeck;
	private HashMap<Integer, Boolean> map;
	private Db db;
	private Boolean isBack = false;
	private SQLiteDatabase dbwrDatabase;

	public MyAdapter(Context context, List<Bean> datas) {
		inflater = LayoutInflater.from(context);
		mdatas = datas;
		db = new Db(context);
		map = new HashMap<Integer, Boolean>();
		for (int i = 0; i < mdatas.size(); i++) {
			map.put(i, false);
		}
		// TODO Auto-generated constructor stub
	}

	public boolean isIscheck() {
		return ischeck;
	}

	public void setIsBack(Boolean isBack) {
		this.isBack = isBack;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mdatas.size();
	}

	@Override
	public Bean getItem(int position) {
		// TODO Auto-generated method stub
		return mdatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup parent) {
		Viewholder holder = null;
		if (convertview == null) {
			convertview = inflater.inflate(R.layout.item, parent, false);
			holder = new Viewholder();
			holder.day = (TextView) convertview.findViewById(R.id.tv_day);
			holder.time = (TextView) convertview.findViewById(R.id.tv_time);
			holder.content = (TextView) convertview
					.findViewById(R.id.tv_content);
			holder.ck = (CheckBox) convertview.findViewById(R.id.checkBox1);
			convertview.setTag(holder);

		} else {

			holder = (Viewholder) convertview.getTag();
		}
		Bean bean = mdatas.get(position);
		holder.day.setText(bean.getDay());
		holder.time.setText(bean.getTime());
		holder.content.setText(bean.getContent());
		if (ischeck) {
			holder.ck.setVisibility(View.VISIBLE);
			holder.ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					System.out.println(arg1);
					map.put(position, arg1);
				}
			});

		} else {

			holder.ck.setVisibility(View.GONE);

		}
		return convertview;
	}

	private class Viewholder {
		private CheckBox ck;
		private TextView day;
		private TextView time;
		private TextView content;
	}

	public void remove() {
		ArrayList<Bean> list = new ArrayList<Bean>();
		List<String> li = new ArrayList<String>();
		for (int i = 0; i < mdatas.size(); i++) {
			System.out.println(map.size());
			if (map.get(i)) {
				list.add(mdatas.get(i));
				System.out.println("1" + list);
				li.add(mdatas.get(i).getContent());

			}
		}

		mdatas.removeAll(list);
		notifyDataSetChanged();
		SQLiteDatabase write = db.getWritableDatabase();
		for (int j = 0; j < list.size(); j++) {
			write.delete(Db.TABLE_NOTE, Db.CONTEXT + "=?", new String[] { list
					.get(j).getContent() + "" });

		}
	}

}
