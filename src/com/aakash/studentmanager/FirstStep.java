package com.aakash.studentmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FirstStep extends Activity implements OnClickListener {

	Button b;
	EditText etCourseCode, etCourseName, etDept, etFaculty, etFacultyEmail,
			etTimings, etVenue, etWebpage;
	Spinner sDept, sSlot;
	RadioGroup rgType;
	String[] deptCode = { "AE", "CL", "CH", "CE", "CS", "GS", "EE", "HS", "MA",
			"SI", "ME", "MM", "PH", "EP", "GP", "MG", "BM", "CR", "EN", "IE",
			"RE", "SC", "BT", "ES", "TD", "ID", "IN", "AN", "VC", "NR" };
	String[] deptName = { "Aerospace Engineering", "Chemical Engineering",
			"Chemistry", "Civil Engineering",
			"Computer Science and Engineering", "Earth Sciences",
			"Electrical Engineering", "Humanities and Social Sciences",
			"Mathematics", "Applied Statistics and Informatics",
			"Mechanical Engineering",
			"Metallurgical Engineering and Materials Science", "Physics",
			"Engineering Physics", "Applied Geophysics",
			"School of Management", "Biomedical Engineering",
			"Corrosion Science Engineering", "Energy Systems Engineering",
			"Industrial Engineering and Operations Research",
			"Reliability Engineering", "Systems and Control Engineering",
			"Biotechnology Centre",
			"Centre for Environmental Science and Engineering",
			"Center for Technology Alternatives for Rural Areas",
			"Industrial Design Centre", "Interaction Design", "Animation",
			"Visual Communication", "Centre of Studies in Resource Engineering" };
	String[] slotTimings = {
			"Mon 08:30-09:25, Tue 09:30-10:25, Thu 10:35-11:30",
			"Mon 09:30-10:25, Tue 10:35-11:30, Thu 11:35-12:30",
			"Mon 10:35-11:30, Tue 11:35-12:30, Thu 08:30-09:25",
			"Mon 11:35-12:30, Tue 08:30-09:25, Thu 09:30-10:25",
			"Wed 09:30-10:55, Fri 09:30-10:55",
			"Wed 11:05-12:30, Fri 11:05-12:30",
			"Wed 08:30-09:25, Fri 08:30-09:25",
			"Mon 14:00-15:25, Thu 14:00-15:25",
			"Mon 15:30-17:00, Thu 15:30-17:00",
			"Tue 14:00-15:25, Fri 14:00-15:25",
			"Tue 15:30-17:00, Fri 15:30-17:00",
			"Mon 17:05-18:30, Thu 17:05-18:30",
			"Mon 18:35-20:00, Thu 18:35-20:00",
			"Tue 17:05-18:30, Fri 17:05-18:30",
			"Tue 18:35-20:00, Fri 18:35-20:00", "Mon 14:00-17:00",
			"Tue 14:00-17:00", "Thu 14:00-17:00", "Fri 14:00-17:00",
			"Wed 14:00-17:00", "Wed 17:05-18:30", "Wed 18:35-20:00" };
	int semester;
	String course_code_dept, course_code_id, course_code, course_name, dept,
			instructor_name, instructor_email, course_type, lecture_slot,
			lecture_timings, lecture_venue, course_webpage;

	DBHelper dbh;
	SharedPreferences spf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firststep);
	}

	@Override
	protected void onResume() {
		super.onResume();
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		semester = spf.getInt("SemesterSelected", 0);

		b = (Button) findViewById(R.id.bSubmit);
		etCourseCode = (EditText) findViewById(R.id.etCourseCode);
		etCourseName = (EditText) findViewById(R.id.etCourseName);
		etDept = (EditText) findViewById(R.id.etDept);
		etFaculty = (EditText) findViewById(R.id.etFaculty);
		etFacultyEmail = (EditText) findViewById(R.id.etFacultyEmail);
		etTimings = (EditText) findViewById(R.id.etTimings);
		etVenue = (EditText) findViewById(R.id.etVenue);
		etWebpage = (EditText) findViewById(R.id.etWebpage);
		sSlot = (Spinner) findViewById(R.id.sSlot);
		sDept = (Spinner) findViewById(R.id.sDept);
		rgType = (RadioGroup) findViewById(R.id.rgType);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, deptCode);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sDept.setAdapter(dataAdapter);

		sDept.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				course_code_dept = deptCode[arg2];
				etDept.setText(deptName[arg2]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				course_code_dept = deptCode[0];
				etDept.setText(deptName[0]);
			}
		});

		sSlot.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				lecture_slot = arg0.getItemAtPosition(arg2).toString();
				lecture_timings = slotTimings[arg2];
				etTimings.setText(lecture_timings);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				lecture_slot = String.valueOf(1);
				lecture_timings = slotTimings[0];
				etTimings.setText(lecture_timings);
			}
		});

		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSubmit:
			if (etCourseCode.getText().toString().length() == 0) {
				Dialog d = new Dialog(this);
				d.setTitle("Course Code cannot be empty!");
				d.show();
			} else {
				course_code = course_code_dept + " "
						+ etCourseCode.getText().toString();
				dbh = new DBHelper(this);
				Cursor rs = dbh.getCourseData(semester, course_code);

				rs.moveToFirst();
				if (rs.isAfterLast() == true) {
					course_name = etCourseName.getText().toString();
					dept = etDept.getText().toString();
					instructor_name = etFaculty.getText().toString();
					instructor_email = etFacultyEmail.getText().toString();

					int id = rgType.getCheckedRadioButtonId();
					RadioButton rb = (RadioButton) findViewById(id);

					course_type = rb.getText().toString();
					lecture_venue = etVenue.getText().toString();
					course_webpage = etWebpage.getText().toString();

					dbh.insertCourse(semester, course_code, course_name, dept,
							instructor_name, instructor_email, course_type,
							lecture_slot, lecture_timings, lecture_venue,
							course_webpage);

					Toast.makeText(getApplicationContext(),
							"Course " + course_code + " added successfully!",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getApplicationContext(),
							com.aakash.studentmanager.CourseList.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} else {
					Dialog d = new Dialog(this);
					d.setTitle("Duplicate Entry:");
					TextView tv = new TextView(this);
					tv.setText("Course " + course_code + " already added!");
					d.setContentView(tv);
					d.show();
				}
				dbh.close();
			}
			break;
		}
	}
}