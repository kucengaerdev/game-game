package com.dev.pronouncegame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	
	ListView view;
	TextView vie;
	ArrayList<ScoreItem> list = new ArrayList<ScoreItem>();
	ScoreAdapter adapter;
	String[] projection = {"Nama","Nilai"};
	DatabaseGame dbg;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_all);
	    dbg = new DatabaseGame(this);
	    vie = (TextView)findViewById(R.id.empty_text);
	    view = (ListView)findViewById(R.id.list_score);
	    adapter = new ScoreAdapter(ScoreActivity.this, R.layout.row_layout, list);
	    view.setAdapter(adapter);
	    if(exists()){	
	    	
	    	if(list.size()>0){
	    		list.clear();
	    	}
	    	setList();
	    	vie.setVisibility(View.INVISIBLE);
	    }
	    adapter.notifyDataSetChanged();
	}
	
	private void setList() {
		// TODOs Auto-generated method stub
		Uri url = Uri
				.parse("content://com.dev.pronouncegame.scoredatabaseprovider/element");
		Cursor cursor = ScoreActivity.this.getContentResolver().query(
				url, projection, null, null, null);
		cursor.moveToFirst();
		int i=1;
		do {

			String nama = cursor.getString(cursor.getColumnIndex("Nama"));
			String nilai = cursor.getString(cursor.getColumnIndex("Nilai"));
			String nomer = Integer.toString(i)+".";
			list.add(new ScoreItem(nama, nilai,nomer));
			i++;

		} while (cursor.moveToNext());
		adapter.notifyDataSetChanged();
		cursor.close();
	}
	
	private boolean exists(){
		SQLiteDatabase db = dbg.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Score", null);
		if(cursor !=null && cursor.getCount()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODOs Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODOs Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.new_game:
			startActivity(new Intent(ScoreActivity.this,PronounceGameActivity.class));
			ScoreActivity.this.finish();
			return true;
		case R.id.about_game:
			startActivity(new Intent(ScoreActivity.this,AboutActivity.class));
			return true;
		case R.id.help_game:
			startActivity(new Intent(ScoreActivity.this,HelpActivity.class));
			return true;
		case R.id.score_game:
			startActivity(new Intent(ScoreActivity.this,ScoreActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	
	
}
