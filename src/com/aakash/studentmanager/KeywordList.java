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

public class KeywordList extends ListActivity {

	SharedPreferences spf;
	private DBHelper dbh;

	ArrayList<String> keywordList;
	int keywordListSize = 1;
	int semSelected, lectSelected;
	String courseSelected;

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
		lectSelected = spf.getInt("LectureSelected", 0);

		dbh = new DBHelper(this);
		keywordList = dbh.getAllKeywords(semSelected, courseSelected,
				lectSelected);
		dbh.close();

		keywordList.remove(0);
		keywordList.add(keywordList.size(), "Add Keyword");
		keywordListSize = keywordList.size();

		// adding it to the list view.
		setListAdapter(new ArrayAdapter<String>(KeywordList.this,
				android.R.layout.simple_list_item_1, keywordList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub

		super.onListItemClick(l, v, position, id);

		Intent i;
		if (position == keywordListSize - 1) {
			i = new Intent("com.aakash.studentmanager.KEYWORDADD");
		} else {
			Bundle keyword = new Bundle();
			keyword.putString("searchKey", l.getItemAtPosition(position)
					.toString());
			i = new Intent("com.aakash.studentmanager.SIMPLEBROWSER");
			i.putExtras(keyword);
		}
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
}