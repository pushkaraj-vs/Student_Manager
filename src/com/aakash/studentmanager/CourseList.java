package com.aakash.studentmanager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CourseList extends ListActivity {

	SharedPreferences spf;
	Editor editor;
	ArrayList<String> courseList;
	int courseListSize = 1;
	int semSelected;
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

		editor = spf.edit();
		editor.putString("CourseSelected", null);
		editor.commit();

		dbh = new DBHelper(this);
		courseList = dbh.getAllCourses(semSelected);
		dbh.close();

		courseList.add(courseList.size(), "Add Course");
		courseListSize = courseList.size();

		// adding it to the list view.
		setListAdapter(new ArrayAdapter<String>(CourseList.this,
				android.R.layout.simple_list_item_1, courseList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		super.onListItemClick(l, v, position, id);

		Intent i;
		if (position == courseListSize - 1) {
			i = new Intent("com.aakash.studentmanager.FIRSTSTEP");
		} else {
			editor = spf.edit();
			editor.putString("CourseSelected",
					((l.getItemAtPosition(position).toString()).split(":"))[0]);
			editor.commit();
			i = new Intent("com.aakash.studentmanager.VIEWDETAILS");
			Toast.makeText(
					getApplicationContext(),
					"Course " + spf.getString("CourseSelected", null)
							+ " selected!", Toast.LENGTH_SHORT).show();
		}
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}