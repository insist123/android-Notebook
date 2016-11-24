package com.ws.notebook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {

	public Db(Context context) {
		super(context, "DB", null, 1);
		// TODO Auto-generated constructor stub
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_USER + "(" + USER_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME
				+ " TEXT NOT NULL," + PASSWORD + " TEXT NOT NULL)");
		db.execSQL("CREATE TABLE " + TABLE_NOTE + "(" + NOTE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTEXT
				+ " TEXT NOT NULL," + DAY + " TEXT NOT NULL," + OWNER
				+ " TEXT REFERENCES TABLE_USER(USER_ID)," + TIME
				+ " TEXT NOT NULL)");

	}

	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		

	}

	public static final String TABLE_USER = "user";
	public static final String USER_ID = "_id";
	public static final String NAME = "username";
	public static final String PASSWORD = "password";

	public static final String TABLE_NOTE = "note";
	public static final String NOTE_ID = "_id";
	public static final String CONTEXT = "content";
	public static final String TIME = "time";
	public static final String DAY = "day";
	public static final String OWNER = "owner";

}
