package com.example.databasedemo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	// /data/data/com.example.databasedemo2/databases/mydatabase.db
	private static final String DB_NAME = "mydatabase.db";
	private static final int VERSION = 1; // > 0
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table Students (studentId integer primary key autoincrement, studentName text, address text, age integer)");
		db.execSQL("create table Colleges (collegeId integer primary key autoincrement, collegeName text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
