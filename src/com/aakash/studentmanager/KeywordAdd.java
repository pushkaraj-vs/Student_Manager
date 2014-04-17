package com.aakash.studentmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KeywordAdd extends Activity {

	SharedPreferences spf;
	private DBHelper dbh;

	int semSelected, lectSelected;
	String courseSelected;

	EditText keywordAdd, keywordAdded;
	Button addKeyToList, submitKey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keywordadd);
	}

	@Override
	protected void onResume() {
		super.onResume();
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		semSelected = spf.getInt("SemesterSelected", 0);
		courseSelected = spf.getString("CourseSelected", null);
		lectSelected = spf.getInt("LectureSelected", 0);

		keywordAdd = (EditText) findViewById(R.id.etKeywords);
		keywordAdded = (EditText) findViewById(R.id.etKeywordsAdded);

		addKeyToList = (Button) findViewById(R.id.bAddKeyword);
		submitKey = (Button) findViewById(R.id.bKeywordSubmit);

		addKeyToList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (keywordAdd.getText().toString().length() != 0) {
					if (keywordAdded.getText().toString().length() != 0)
						keywordAdded.append("\n"
								+ keywordAdd.getText().toString());
					else
						keywordAdded.append(keywordAdd.getText().toString());
				}
				keywordAdd.setText(null);
			}
		});

		submitKey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (keywordAdded.getText().toString().length() != 0) {
					dbh = new DBHelper(KeywordAdd.this);
					dbh.updateKeywords(semSelected, courseSelected,
							lectSelected, keywordAdded.getText().toString());
					dbh.close();

				} else {

					Toast.makeText(getApplicationContext(),
							"No keywords added!", Toast.LENGTH_SHORT).show();
				}
				Intent i = new Intent("com.aakash.studentmanager.KEYWORDLIST");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
}