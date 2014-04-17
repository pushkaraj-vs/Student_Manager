package com.aakash.studentmanager;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AssignmentAdd extends Activity {

	EditText etAssnNum, etAssnDescription;
	Button bSubmit;
	DatePicker dpDate;
	TimePicker tpTime;
	SharedPreferences spf;
	DBHelper dbh;

	int assn_number;
	String assn_description;

	int semSelected;
	String courseSelected;
	int year, month, day, hour, minute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assignmentadd);
	}

	@Override
	protected void onResume() {
		super.onResume();
		etAssnNum = (EditText) findViewById(R.id.etAssnNum);
		etAssnDescription = (EditText) findViewById(R.id.etAssnDescription);
		bSubmit = (Button) findViewById(R.id.bAssnSubmit);
		dpDate = (DatePicker) findViewById(R.id.dpAssn);
		tpTime = (TimePicker) findViewById(R.id.tpAssn);

		spf = PreferenceManager.getDefaultSharedPreferences(this);

		semSelected = spf.getInt("SemesterSelected", 0);
		courseSelected = spf.getString("CourseSelected", null);

		bSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (etAssnNum.getText().toString().length() == 0) {
					Dialog d = new Dialog(AssignmentAdd.this);
					d.setTitle("Assignment Number cannot be empty!");
					d.show();
				} else {
					assn_number = Integer.parseInt(etAssnNum.getText()
							.toString());

					dbh = new DBHelper(AssignmentAdd.this);
					Cursor rs = dbh.getAssignmentData(semSelected,
							courseSelected, assn_number);

					rs.moveToFirst();
					if (rs.isAfterLast() == true) {

						assn_description = etAssnDescription.getText()
								.toString();
						year = dpDate.getYear();
						month = dpDate.getMonth();
						day = dpDate.getDayOfMonth();
						hour = tpTime.getCurrentHour();
						minute = tpTime.getCurrentMinute();

						String due_date_time = "" + day + "-" + (month + 1)
								+ "-" + year + " " + hour + ":" + minute;

						dbh.insertAssignment(semSelected, courseSelected,
								assn_number, assn_description, due_date_time);

						/**
						 * This intent invokes the activity DemoActivity, which
						 * in turn opens the AlertDialog window
						 */
						Intent i = new Intent(
								"com.aakash.studentmanager.demoactivity");

						/** Creating a Pending Intent */

						PendingIntent operation = PendingIntent.getActivity(
								getBaseContext(), 0, i,
								Intent.FLAG_ACTIVITY_MULTIPLE_TASK
										| Intent.FLAG_ACTIVITY_NEW_TASK);
						/**
						 * Getting a reference to the System Service
						 * ALARM_SERVICE
						 */

						AlarmManager alarmManager = (AlarmManager) getBaseContext()
								.getSystemService(ALARM_SERVICE);

						/**
						 * Creating a calendar object corresponding to the date
						 * and time set by the user
						 */
						GregorianCalendar calendar = new GregorianCalendar(
								year, month, day, hour, minute);

						/**
						 * Converting the date and time in to milliseconds
						 * elapsed since epoch
						 */
						long alarm_time = calendar.getTimeInMillis();

						/**
						 * Setting an alarm, which invokes the operation at
						 * alart_time
						 */
						alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time,
								operation);

						Toast.makeText(
								getApplicationContext(),
								"Assignment " + assn_number
										+ " added successfully! Due date is "
										+ due_date_time, Toast.LENGTH_SHORT)
								.show();

						Intent intent = new Intent(getApplicationContext(),
								com.aakash.studentmanager.AssignmentList.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						Dialog d = new Dialog(AssignmentAdd.this);
						d.setTitle("Duplicate Entry:");
						TextView tv = new TextView(AssignmentAdd.this);
						tv.setText("Assignment Number " + assn_number
								+ " already added!");
						d.setContentView(tv);
						d.show();
					}
					dbh.close();
				}
			}
		});
	}
}