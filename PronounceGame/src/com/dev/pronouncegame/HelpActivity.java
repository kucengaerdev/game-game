package com.dev.pronouncegame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class HelpActivity extends Activity {

	private TextView helptext;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		Animation za = AnimationUtils.loadAnimation(this, R.anim.spin_anime);
		za.reset();
		helptext = (TextView)findViewById(R.id.help_text);
		helptext.clearAnimation();
		helptext.startAnimation(za);
		
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
			startActivity(new Intent(HelpActivity.this,PronounceGameActivity.class));
			HelpActivity.this.finish();
			return true;
		case R.id.about_game:
			startActivity(new Intent(HelpActivity.this,AboutActivity.class));
			return true;
		case R.id.help_game:
			startActivity(new Intent(HelpActivity.this,HelpActivity.class));
			return true;
		case R.id.score_game:
			startActivity(new Intent(HelpActivity.this,ScoreActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	
	
}
