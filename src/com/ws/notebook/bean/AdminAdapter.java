package com.ws.notebook.bean;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notebook.R;
import com.ws.notebook.data.Db;

public class AdminAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<AdminBean> mdatas;


	public AdminAdapter(Context context, List<AdminBean> datas) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		mdatas = datas;
	

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdatas.size();
	}

	@Override
	public AdminBean getItem(int position) {
		// TODO Auto-generated method stub
		return mdatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {

		// TODO Auto-generated method stub
		Viewholder holder = null;
		if (convertview == null) {
			convertview = inflater.inflate(R.layout.admin_item, parent, false);
			holder = new Viewholder();
			holder.username = (TextView) convertview.findViewById(R.id.ad_user);
			holder.password = (TextView) convertview
					.findViewById(R.id.ad_password);

			convertview.setTag(holder);
		} else {
			holder = (Viewholder) convertview.getTag();
		}
		AdminBean bean = mdatas.get(position);
		holder.username.setText(bean.getUsername());
		holder.password.setText(bean.getPassword());
		return convertview;
	}

	private class Viewholder {
		private TextView username;
		private TextView password;
	}
}
