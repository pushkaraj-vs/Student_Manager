package com.aakash.studentmanager;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LectureList extends ListActivity {

	SharedPreferences spf;
	Editor editor;
	ArrayList<String> lectureList;
	int lectureListSize = 1;
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

		editor = spf.edit();
		editor.putInt("LectureSelected", 0);
		editor.commit();

		editor = spf.edit();
		editor.putString("LectureSelected", null);
		editor.commit();

		dbh = new DBHelper(this);
		lectureList = dbh.getAllLectures(semSelected, courseSelected);
		dbh.close();

		lectureList.add(lectureList.size(), "Add Lecture");
		lectureListSize = lectureList.size();

		// adding it to the list view.
		setListAdapter(new ArrayAdapter<String>(LectureList.this,
				android.R.layout.simple_list_item_1, lectureList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		super.onListItemClick(l, v, position, id);

		Intent i;
		if (position == lectureListSize - 1) {
			i = new Intent("com.aakash.studentmanager.LECTUREADD");
		} else {
			editor = spf.edit();
			editor.putInt("LectureSelected", Integer.parseInt(((((l
					.getItemAtPosition(position).toString()).split(":"))[0])
					.split(" "))[1]));
			editor.commit();
			i = new Intent("com.aakash.studentmanager.LECTUREVIEW");
			Toast.makeText(
					getApplicationContext(),
					"Lecture " + spf.getInt("LectureSelected", 0)
							+ " selected!", Toast.LENGTH_SHORT).show();
		}
		Bundle b = new Bundle();
		b.putBoolean("Update", false);
		i.putExtras(b);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		return true;
	}
}