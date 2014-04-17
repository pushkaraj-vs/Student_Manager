package com.aakash.studentmanager;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AssignmentView extends Activity {

	private DBHelper dbh;
	SharedPreferences spf;

	TextView assn_number, assn_description, assn_due_date, assn_due_time;
	int semester;
	String course;
	int assn_num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assignmentview);
	}

	@Override
	protected void onResume() {
		super.onResume();

		spf = PreferenceManager.getDefaultSharedPreferences(this);

		assn_number = (TextView) findViewById(R.id.tvAssnNum);
		assn_description = (TextView) findViewById(R.id.tvAssnDescription);
		assn_due_date = (TextView) findViewById(R.id.tvAssnDueDate);
		assn_due_time = (TextView) findViewById(R.id.tvAssnDueTime);

		semester = spf.getInt("SemesterSelected", 0);
		course = spf.getString("CourseSelected", null);

		dbh = new DBHelper(this);
		Cursor rs = dbh.getAssignmentData(semester, course,
				(getIntent().getExtras()).getInt("assn_number"));
		rs.moveToFirst();
		assn_num = rs.getInt(rs.getColumnIndex(DBHelper.ASSIGNMENTS_NUMBER));
		assn_number.setText((CharSequence) String.valueOf(rs.getInt(rs
				.getColumnIndex(DBHelper.ASSIGNMENTS_NUMBER))));
		assn_description.setText((CharSequence) rs.getString(rs
				.getColumnIndex(DBHelper.ASSIGNMENTS_DESCRIPTION)));
		assn_due_date
				.setText((CharSequence) ((rs.getString(rs
						.getColumnIndex(DBHelper.ASSIGNMENTS_DUE_DATE)))
						.split(" "))[0]);
		assn_due_time
				.setText((CharSequence) ((rs.getString(rs
						.getColumnIndex(DBHelper.ASSIGNMENTS_DUE_DATE)))
						.split(" "))[1]);
		rs.close();
		dbh.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assignment_view, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.delAssn:

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Are you sure you want to delete this assignment?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dbh = new DBHelper(AssignmentView.this);
									dbh.deleteAssignment(semester, course,
											assn_num);
									Toast.makeText(
											getApplicationContext(),
											"Assignment " + assn_num
													+ " deleted successfully!",
											Toast.LENGTH_SHORT).show();
									Intent i = new Intent(
											getApplicationContext(),
											com.aakash.studentmanager.AssignmentList.class);
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