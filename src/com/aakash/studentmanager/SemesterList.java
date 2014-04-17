package com.aakash.studentmanager;

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

public class SemesterList extends ListActivity {

	SharedPreferences spf;
	Editor editor;
	String[] semList = null;
	int semListSize = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		editor = spf.edit();
		editor.putInt("SemesterSelected", 0);
		editor.commit();

		semList = spf.getString("SemesterAdd", null).split(",");
		semListSize = semList.length;

		setListAdapter(new ArrayAdapter<String>(SemesterList.this,
				android.R.layout.simple_list_item_1, semList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		super.onListItemClick(l, v, position, id);

		Intent i;
		if (position == semListSize - 1) {
			i = new Intent("com.aakash.studentmanager.SEMESTERADD");

		} else {

			editor.putInt("SemesterSelected", Integer.parseInt((l
					.getItemAtPosition(position).toString().split(" "))[1]));
			editor.commit();
			i = new Intent("com.aakash.studentmanager.COURSELIST");
			Toast.makeText(
					getApplicationContext(),
					"Semester " + spf.getInt("SemesterSelected", 0)
							+ " selected!", Toast.LENGTH_SHORT).show();
		}
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}