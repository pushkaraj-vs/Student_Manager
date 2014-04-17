package com.aakash.studentmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class Email extends Activity implements View.OnClickListener {

	DatePicker appointmentDate;
	String student_name, student_id, student_degree, course_code, emailAdd;
	int degree_year, semester, lecture_number;
	Button sendEmail;

	SharedPreferences spf;
	DBHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		initializeVars();
		sendEmail.setOnClickListener(this);
	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		appointmentDate = (DatePicker) findViewById(R.id.dpApptDate);
		sendEmail = (Button) findViewById(R.id.bSentEmail);
		spf = PreferenceManager.getDefaultSharedPreferences(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

		semester = spf.getInt("SemesterSelected", 0);
		course_code = spf.getString("CourseSelected", null);
		lecture_number = spf.getInt("LectureSelected", 0);
		student_name = spf.getString("StudentName", null);
		student_id = spf.getString("StudentID", null);
		student_degree = spf.getString("StudentDegree", null);

		dbh = new DBHelper(this);
		Cursor rs = dbh.getCourseData(semester, course_code);
		rs.moveToFirst();
		emailAdd = rs.getString(rs
				.getColumnIndex(DBHelper.COURSES_INSTRUCTOR_EMAIL));
		rs.close();
		dbh.close();
		String emailaddress[] = { emailAdd };
		String message = "Dear Sir/Madam,\nI am "
				+ student_name
				+ ", a student of "
				+ student_degree
				+ "-"
				+ (int) (Math.ceil(semester / 2.0))
				+ " in your course "
				+ course_code
				+ ".\nI have a doubt in lecture number "
				+ lecture_number
				+ " and would like to meet you in order to clear my doubt. Please let me know if you are free at some convenient time on "
				+ (appointmentDate.getDayOfMonth())
				+ "-"
				+ (appointmentDate.getMonth() + 1)
				+ "-"
				+ appointmentDate.getYear()
				+ " for the meeting. If not, kindly let me know a convenient date and time for the same.\n\nThanks & Regards,\n"
				+ student_name + "\n" + student_id;

		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, course_code
				+ ": Appointment for clearing doubts");
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		startActivity(emailIntent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}