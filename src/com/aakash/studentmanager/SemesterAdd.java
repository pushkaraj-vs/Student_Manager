package com.aakash.studentmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SemesterAdd extends Activity implements OnClickListener {

	SharedPreferences spf;
	Button bSubmit;
	Spinner sSem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.semesteradd);
		bSubmit = (Button) findViewById(R.id.bSemSubmit);
		sSem = (Spinner) findViewById(R.id.sSem);
		bSubmit.setOnClickListener(this);
		spf = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String sem_to_add = sSem.getSelectedItem().toString();
		String sem_added = spf.getString("SemesterAdd", null);

		if (sem_added.contains(sem_to_add + ",")) {
			Dialog d = new Dialog(this);
			d.setTitle("Invalid Selection:");
			TextView tv = new TextView(this);
			tv.setText("Semester " + sem_to_add + " already added!");
			d.setContentView(tv);
			d.show();
		} else {
			Editor editor = spf.edit();
			editor.putString("SemesterAdd", "Semester " + sem_to_add + ","
					+ sem_added);
			editor.commit();

			Toast.makeText(getApplicationContext(),
					"Semester " + sem_to_add + " added successfully!",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getApplicationContext(),
					com.aakash.studentmanager.SemesterList.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}
}