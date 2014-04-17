package com.aakash.studentmanager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AssignmentList extends ListActivity {

	SharedPreferences spf;

	ArrayList<String> assignmentList;
	int assignmentListSize = 1;
	int semSelected;
	String courseSelected;

	private DBHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		semSelected = spf.getInt("SemesterSelected", 0);
		courseSelected = spf.getString("CourseSelected", null);

		dbh = new DBHelper(this);
		assignmentList = dbh.getAllAssignments(semSelected, courseSelected);
		dbh.close();

		assignmentList.add(assignmentList.size(), "Add Assignment");
		assignmentListSize = assignmentList.size();

		// adding it to the list view.
		setListAdapter(new ArrayAdapter<String>(AssignmentList.this,
				android.R.layout.simple_list_item_1, assignmentList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		super.onListItemClick(l, v, position, id);

		Intent i;
		if (position == assignmentListSize - 1) {
			i = new Intent("com.aakash.studentmanager.ASSIGNMENTADD");
		} else {
			Bundle b = new Bundle();
			b.putInt("assn_number", Integer.parseInt(((l
					.getItemAtPosition(position).toString()).split(" "))[1]));
			i = new Intent("com.aakash.studentmanager.ASSIGNMENTVIEW");
			i.putExtras(b);
		}
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.course_details, menu);
		return true;
	}
}