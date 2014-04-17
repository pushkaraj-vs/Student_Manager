package com.aakash.studentmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LectureView extends Activity {

	private DBHelper dbh;
	SharedPreferences spf;

	TextView lecture_number, lecture_title, lecture_description;
	Button keywords, bookAppointment;
	int semester, lecture;
	String course;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lectureview);
	}

	@Override
	protected void onResume() {
		super.onResume();

		spf = PreferenceManager.getDefaultSharedPreferences(this);

		lecture_number = (TextView) findViewById(R.id.tvLectNum);
		lecture_title = (TextView) findViewById(R.id.tvLectTitle);
		lecture_description = (TextView) findViewById(R.id.tvLectDescription);
		keywords = (Button) findViewById(R.id.bKeywords);
		bookAppointment = (Button) findViewById(R.id.bAppointment);

		semester = spf.getInt("SemesterSelected", 0);
		course = spf.getString("CourseSelected", null);
		lecture = spf.getInt("LectureSelected", 0);

		dbh = new DBHelper(this);
		Cursor rs = dbh.getLectureData(semester, course, lecture);
		rs.moveToFirst();
		lecture_number.setText((CharSequence) String.valueOf(rs.getInt(rs
				.getColumnIndex(DBHelper.LECTURES_NUMBER))));
		lecture_title.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.LECTURES_TITLE)));
		lecture_description.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.LECTURES_DESCRIPTION)));
		rs.close();
		dbh.close();

		keywords.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent("com.aakash.studentmanager.KEYWORDLIST");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});

		bookAppointment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent("com.aakash.studentmanager.EMAIL");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lecture_view, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		
		case R.id.editLect:
			
			Bundle b = new Bundle();
			b.putBoolean("Update", true);
			Intent i = new Intent(getApplicationContext(),
					com.aakash.studentmanager.LectureAdd.class);
			i.putExtras(b);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return true;

		case R.id.delLect:

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to delete this lecture?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dbh = new DBHelper(LectureView.this);
									dbh.deleteLecture(semester, course, lecture);
									Toast.makeText(
											getApplicationContext(),
											"Lecture " + lecture
													+ " deleted successfully!",
											Toast.LENGTH_SHORT).show();
									Intent i = new Intent(
											getApplicationContext(),
											com.aakash.studentmanager.LectureList.class);
									i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(i);
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// User cancelled the dialog
								}
							});
			AlertDialog d = builder.create();
			d.setTitle("Action to be performed: Delete");
			d.show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}