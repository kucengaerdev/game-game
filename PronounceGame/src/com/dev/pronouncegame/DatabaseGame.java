package com.dev.pronouncegame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseGame extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "games.db";
	private static final int DATABASE_VERSION = 1;
	
	public  final String TABLE_SCORE = "Score";
	public  final String TABLE_KATA = "Kata";
	
	public final String ID = "id";
	
	// row score
	public final String NAMA = "Nama";
	public final String NILAI = "Nilai";
	
	//row kata 
	public  final String KATA = "kata";
	
	//create table kata
	private final String CREATE_TABLE_KATA = "create table "+TABLE_KATA+" ("+ID+" integer primary key,"+KATA+" text);";
	
	//create table score
	private final String CREATE_TABLE_SCORE = "create table "+TABLE_SCORE+" ("+ID+" integer primary key,"+NAMA+" text,"+NILAI+" text);";
	
	public DatabaseGame(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODOs Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODOs Auto-generated method stub
		db.execSQL(CREATE_TABLE_KATA);
		db.execSQL(CREATE_TABLE_SCORE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldDb, int newDb) {
		// TODOs Auto-generated method stub
		db.execSQL("drop table if exists "+TABLE_KATA);
		db.execSQL("drop table if exists "+TABLE_SCORE);
		onCreate(db);
	}

}
