package com.aakash.studentmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LectureAdd extends Activity implements OnClickListener {

	EditText etLectNum, etLectTitle, etLectDescription;
	TextView tvLastLect;
	Button bSubmit;
	SharedPreferences spf;
	Editor ed;
	DBHelper dbh;

	int lecture_number;
	String lecture_title, lecture_description;

	int semSelected;
	String courseSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lectureadd);
	}

	@Override
	protected void onResume() {
		super.onResume();

		etLectNum = (EditText) findViewById(R.id.etLectNum);
		etLectTitle = (EditText) findViewById(R.id.etLectTitle);
		etLectDescription = (EditText) findViewById(R.id.etLectDescription);
		tvLastLect = (TextView) findViewById(R.id.tvLastLect);
		bSubmit = (Button) findViewById(R.id.bLectSubmit);

		spf = PreferenceManager.getDefaultSharedPreferences(this);
		ed = spf.edit();

		semSelected = spf.getInt("SemesterSelected", 0);
		courseSelected = spf.getString("CourseSelected", null);

		dbh = new DBHelper(this);

		if (getIntent().getExtras().getBoolean("Update")) {

			Cursor rs = dbh.getLectureData(semSelected, courseSelected,
					spf.getInt("LectureSelected", 0));
			rs.moveToFirst();
			etLectNum.setText(String.valueOf(rs.getInt(rs
					.getColumnIndex(DBHelper.LECTURES_NUMBER))));
			etLectNum.setFocusable(false);
			etLectNum.setClickable(false);
			etLectTitle.setText(rs.getString(rs
					.getColumnIndex(DBHelper.LECTURES_TITLE)));
			etLectDescription.setText(rs.getString(rs
					.getColumnIndex(DBHelper.LECTURES_DESCRIPTION)));
			rs.close();
			dbh.close();
		} else {
			tvLastLect.setText("Last Lecture Number added: "
					+ dbh.getLastAddedLecture(semSelected, courseSelected));
		}
		bSubmit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (etLectNum.getText().toString().length() == 0) {
			Dialog d = new Dialog(this);
			d.setTitle("Lecture Number cannot be empty!");
			d.show();
		} else {
			lecture_number = Integer.parseInt(etLectNum.getText().toString());
			lecture_title = etLectTitle.getText().toString();
			lecture_description = etLectDescription.getText().toString();

			dbh = new DBHelper(this);

			if (getIntent().getExtras().getBoolean("Update")) {
				dbh.updateLecture(semSelected, courseSelected, lecture_number,
						lecture_title, lecture_description, "");
				Toast.makeText(getApplicationContext(),
						"Lecture " + lecture_number + " updated successfully!",
						Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(getApplicationContext(),
						com.aakash.studentmanager.LectureList.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			} else {

				Cursor rs = dbh.getLectureData(semSelected, courseSelected,
						lecture_number);

				rs.moveToFirst();
				if (rs.isAfterLast() == true) {

					dbh.insertLecture(semSelected, courseSelected,
							lecture_number, lecture_title, lecture_description,
							"");
					Toast.makeText(
							getApplicationContext(),
							"Lecture " + lecture_number
									+ " added successfully!",
							Toast.LENGTH_SHORT).show();

					Intent intent = new Intent(getApplicationContext(),
							com.aakash.studentmanager.LectureList.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					Dialog d = new Dialog(this);
					d.setTitle("Duplicate Entry:");
					TextView tv = new TextView(this);
					tv.setText("Lecture Number " + lecture_number
							+ " already added!");
					d.setContentView(tv);
					d.show();
				}
			}
			dbh.close();
		}
	}
}