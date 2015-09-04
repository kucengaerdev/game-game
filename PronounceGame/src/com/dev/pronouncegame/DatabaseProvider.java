package com.dev.pronouncegame;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class DatabaseProvider extends ContentProvider {
	
	public static final Uri CONTENT_URI = Uri.parse("content://com.dev.pronouncegame.databaseprovider/element");
	private static final int ALLROWS = 1;
	private static final int NAME=2;
	private static final int NAME_TO_DELETE=3;
	private static final int NAME_FILTER=4;
	private static final String CALLER_IS_SYNC_ADAPTER = "Utang";
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.dev.pronouncegame.databaseprovider","element",ALLROWS);
		uriMatcher.addURI("com.dev.pronouncegame.databaseprovider","element/list/*", NAME);
		uriMatcher.addURI("com.dev.pronouncegame.databaseprovider", "element/lists", NAME_TO_DELETE);
		uriMatcher.addURI("com.dev.pronouncegame.databaseprovider","element/filter/*", NAME_FILTER);
	}

	
	private boolean CallerIsSyncAdapter(Uri uri){
		final String is_sync_adapter = uri.getQueryParameter(CALLER_IS_SYNC_ADAPTER);
		return is_sync_adapter != null && !is_sync_adapter.equals("0");
	}
	
	private DatabaseGame gamedb;

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODOs Auto-generated method stub
		int UriType = uriMatcher.match(uri);
		SQLiteDatabase db = gamedb.getWritableDatabase();
		int rowsDeleted = 0;
		switch (UriType){
		    case ALLROWS:
			    rowsDeleted = db.delete(gamedb.TABLE_KATA,selection,selectionArgs);
			    break;
		    case NAME_TO_DELETE:
			    rowsDeleted = db.delete(gamedb.TABLE_KATA,gamedb.KATA+"= '"+selection+"'", selectionArgs);
			    break;
	 	    default :
			    throw new IllegalArgumentException("Unknown Uri : "+uri);
		}
		getContext().getContentResolver().notifyChange(uri,null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri arg0) {
		// TODOs Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODOs Auto-generated method stub
		Log.d("ListProvider","firsttime");
		int uritype = uriMatcher.match(uri);
		SQLiteDatabase db = gamedb.getWritableDatabase();
		long id = 0;
		switch(uritype){
		    case ALLROWS:
		    	id = db.insert(gamedb.TABLE_KATA, null, values);
		        break;
		    default :
		    	throw new IllegalArgumentException("unKnown uri : "+uri);
		}
		getContext().getContentResolver().notifyChange(uri,null,!CallerIsSyncAdapter(uri));
		return Uri.parse("element"+"/"+id);
	}

	@Override
	public boolean onCreate() {
		// TODOs Auto-generated method stub
		gamedb = new DatabaseGame(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODOs Auto-generated method stub
		Log.d("ListProvider",uri.toString());
		SQLiteQueryBuilder queryB = new SQLiteQueryBuilder();
		checkColumns(projection);
		queryB.setTables(gamedb.TABLE_KATA);
		int uriType = uriMatcher.match(uri);
		Log.d("ListProvider", "uriType "+uriType);
		switch (uriType) {
		case ALLROWS:
			
			break;
		
		case NAME:
			
			queryB.appendWhere(gamedb.KATA+"= '"+uri.getLastPathSegment()+"'");
			break;
		
		case NAME_FILTER:
			
			queryB.appendWhere(gamedb.KATA+" LIKE '%"+uri.getLastPathSegment()+"%'");
			break;

		default:
			throw new IllegalArgumentException("Unknown uri : "+uri);
		}
		SQLiteDatabase db = gamedb.getReadableDatabase();
		Cursor cursor = queryB.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	private void checkColumns(String[] projection) {
		// TODOs Auto-generated method stub
		String[] avail = {gamedb.KATA};
		if (projection != null){
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availColumns = new HashSet<String>(Arrays.asList(avail));
			if(!availColumns.containsAll(requestedColumns)){
				throw new IllegalArgumentException("proyeksi database tak dikenal.");
			}
		}
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODOs Auto-generated method stub
		return 0;
	}

}
