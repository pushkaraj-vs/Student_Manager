package com.aakash.studentmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDetails extends Activity {

	private DBHelper dbh;
	SharedPreferences spf;
	Editor ed;
	TextView course_code, course_name, dept, instructor_name, instructor_email,
			course_type, lecture_slot, lecture_timings, lecture_venue,
			course_webpage;
	Button go, lectures, assignments;
	int semester;
	String course;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewdetails);
	}

	@Override
	protected void onResume() {
		super.onResume();
		spf = PreferenceManager.getDefaultSharedPreferences(this);

		course_code = (TextView) findViewById(R.id.tvrhsCourseCode);
		course_name = (TextView) findViewById(R.id.tvrhsCourseName);
		dept = (TextView) findViewById(R.id.tvrhsDept);
		instructor_name = (TextView) findViewById(R.id.tvrhsFaculty);
		instructor_email = (TextView) findViewById(R.id.tvrhsFacultyEmail);
		course_type = (TextView) findViewById(R.id.tvrhsType);
		lecture_slot = (TextView) findViewById(R.id.tvrhsSlot);
		lecture_timings = (TextView) findViewById(R.id.tvrhsTimings);
		lecture_venue = (TextView) findViewById(R.id.tvrhsVenue);
		course_webpage = (TextView) findViewById(R.id.tvrhsWebpage);
		go = (Button) findViewById(R.id.bGo);
		lectures = (Button) findViewById(R.id.bLectures);
		assignments = (Button) findViewById(R.id.bAssignments);

		semester = spf.getInt("SemesterSelected", 0);
		course = spf.getString("CourseSelected", null);

		dbh = new DBHelper(this);
		Cursor rs = dbh.getCourseData(semester, course);
		rs.moveToFirst();
		course_code.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_CODE)));
		course_name.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_NAME)));
		dept.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_DEPT)));
		instructor_name.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_INSTRUCTOR_NAME)));
		instructor_email.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_INSTRUCTOR_EMAIL)));
		course_type.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_TYPE)));
		lecture_slot.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_LECTURE_SLOT)));
		lecture_timings.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_LECTURE_TIMINGS)));
		lecture_venue.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_LECTURE_VENUE)));
		course_webpage.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_WEBPAGE)));
		rs.close();
		dbh.close();

		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (course_webpage.getText().toString().length() != 0) {
					Bundle b = new Bundle();
					b.putString("webpage", course_webpage.getText().toString());
					Intent i = new Intent("com.aakash.studentmanager.MYBROWSER");
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtras(b);
					startActivity(i);
				} else {
					Dialog d = new Dialog(ViewDetails.this);
					d.setTitle("Course Webpage is empty!");
					d.show();
				}
			}
		});

		lectures.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent("com.aakash.studentmanager.LECTURELIST");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});

		assignments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent i = new Intent(
						"com.aakash.studentmanager.ASSIGNMENTLIST");
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.view_details, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.delCourse:

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to delete this course?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dbh = new DBHelper(ViewDetails.this);
									dbh.deleteCourse(semester, course);
									Toast.makeText(
											getApplicationContext(),
											"Course " + course
													+ " deleted successfully!",
											Toast.LENGTH_SHORT).show();
									Intent i = new Intent(
											getApplicationContext(),
											com.aakash.studentmanager.CourseList.class);
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