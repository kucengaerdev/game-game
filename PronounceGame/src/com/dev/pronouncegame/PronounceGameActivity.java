package com.dev.pronouncegame;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PronounceGameActivity extends Activity {

	private ArrayList<String> list = new ArrayList<String>();
	private String randtext, chanceString;
	String[] projection = { "kata" };
	String[] alldata = { "apple", "air conditiener", "ancient", "bird",
			"birth", "connect", "create", "chinese", "database", "dog",
			"delete", "elephant", "eleven", "great", "drug", "hangout",
			"increase", "javanesse", "japanesse", "walk", "mango", "tiger",
			"lion", "snake", "legend", "play", "stop", "point", "community",
			"basic" };
	private int score = 0;
	DatabaseGame dbg;
	private int chance = 3;
	private int indexrand;
	private Random rand;
	private TextView scoretext, textrand, chanceText, statustext;
	private Button start;
	private Animation z, x;
	private final int SPEECH_CODE = 50;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dbg = new DatabaseGame(this);
		z = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
		z.reset();
		x = AnimationUtils.loadAnimation(this, R.anim.spin_anime);
		x.reset();
		scoretext = (TextView) findViewById(R.id.score);
		textrand = (TextView) findViewById(R.id.textrand);
		chanceText = (TextView) findViewById(R.id.chance);
		statustext = (TextView) findViewById(R.id.state);
		statustext.setVisibility(View.INVISIBLE);
		chanceString = Integer.toString(chance);
		chanceText.setText(chanceString);
		animateChance();
		start = (Button) findViewById(R.id.button_start);
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODOs Auto-generated method stub
				startSpeech();
			}
		});
		rand = new Random();
		if (exists()) {
			setList();
			indexrand = rand.nextInt(list.size());
			randtext = list.get(indexrand);
			textrand.setText(randtext);
			animateRandomText();
			startSpeech();
		} else {

			for (String item : alldata) {

				ContentValues values = new ContentValues();
				values.put("kata", item);

				PronounceGameActivity.this
						.getContentResolver()
						.insert(Uri
								.parse("content://com.dev.pronouncegame.databaseprovider/element"),
								values);

			}
			PronounceGameActivity.this.startActivity(new Intent(
					PronounceGameActivity.this, PronounceGameActivity.class));

		}

	}

	private void startSpeech() {
		// TODOs Auto-generated method stub
		Intent speechIntent = new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
				Locale.getDefault());
		speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.prompt_speech) + "...");

		try {
			startActivityForResult(speechIntent, SPEECH_CODE);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.error_supp), Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODOs Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SPEECH_CODE:
			if (resultCode == RESULT_OK && data != null) {
				ArrayList<String> hasil = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				if (hasil.get(0).equalsIgnoreCase(randtext)) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.benar), Toast.LENGTH_SHORT)
							.show();
					score += 10;
					String scoreString = Integer.toString(score);
					scoretext.setText(scoreString);
					animateScore();
					if ((score == 100) || (score == 95)) {
						createAlertBuilder();
						animateStart();
						statustext.setVisibility(View.VISIBLE);
						statustext.setText(getString(R.string.endwin));
						animateStatus();
					} else {

						indexrand = rand.nextInt(list.size());
						randtext = list.get(indexrand);
						textrand.setText(randtext);
						animateRandomText();
						startSpeech();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							getString(R.string.salah), Toast.LENGTH_SHORT)
							.show();
					chance -= 1;
					chanceString = Integer.toString(chance);
					chanceText.setText(chanceString);
					animateChance();
					if (chance < 1) {
						kurangiscore();
						createAlertBuilder();
						animateStart();
						statustext.setVisibility(View.VISIBLE);
						statustext.setText(getString(R.string.endlose));
						animateStatus();
					} else {

							kurangiscore();
							indexrand = rand.nextInt(list.size());
							randtext = list.get(indexrand);
							textrand.setText(randtext);
							animateRandomText();
							startSpeech();

					} 
				}
			}
			break;

		}
	}

	private void kurangiscore() {
		if(score<1){
			
		}else{
		    score -= 5;
		    String scoreString = Integer.toString(score);
		    scoretext.setText(scoreString);
		    animateScore();
		}
	}

	private void createAlertBuilder() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		final EditText edittext = new EditText(PronounceGameActivity.this);
		alert.setMessage("Save Score");
		alert.setTitle("your name");
		alert.setView(edittext);

		alert.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// What ever you want to do with the value

						String nama = edittext.getText().toString();
						String nilai = Integer.toString(score);
						ContentValues values = new ContentValues();
						values.put("Nama", nama);
						values.put("Nilai", nilai);

						PronounceGameActivity.this
								.getContentResolver()
								.insert(Uri
										.parse("content://com.dev.pronouncegame.scoredatabaseprovider/element"),
										values);

					}
				});

		alert.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// what ever you want to do with No option.
						dialog.cancel();
					}
				});

		alert.show();
	}

	private void animateStart() {
		start.setEnabled(false);
		start.setVisibility(View.INVISIBLE);
		start.clearAnimation();
		start.startAnimation(x);
	}

	private void animateRandomText() {
		textrand.clearAnimation();
		textrand.startAnimation(z);
	}

	private void animateChance() {
		chanceText.clearAnimation();
		chanceText.startAnimation(z);
	}

	private void animateScore() {
		scoretext.clearAnimation();
		scoretext.startAnimation(z);
	}

	private void animateStatus() {
		statustext.clearAnimation();
		statustext.startAnimation(x);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODOs Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODOs Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.new_game:
			startActivity(new Intent(PronounceGameActivity.this,
					PronounceGameActivity.class));
			PronounceGameActivity.this.finish();
			return true;
		case R.id.about_game:
			startActivity(new Intent(PronounceGameActivity.this,
					AboutActivity.class));
			return true;
		case R.id.help_game:
			startActivity(new Intent(PronounceGameActivity.this,
					HelpActivity.class));
			return true;
		case R.id.score_game:
			startActivity(new Intent(PronounceGameActivity.this,
					ScoreActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void setList() {
		// TODOs Auto-generated method stub
		Uri url = Uri
				.parse("content://com.dev.pronouncegame.databaseprovider/element");
		Cursor cursor = PronounceGameActivity.this.getContentResolver().query(
				url, projection, null, null, null);

		if (cursor.getCount() > 1) {
			cursor.moveToFirst();
			do {

				String kata = cursor.getString(cursor.getColumnIndex("kata"));
				list.add(kata);

			} while (cursor.moveToNext());
		}
		// adapter.notifyDataSetChanged();
		cursor.close();
	}

	private boolean exists() {
		SQLiteDatabase db = dbg.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Kata", null);
		if (cursor != null && cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
