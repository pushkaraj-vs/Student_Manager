package com.aakash.studentmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SelectDegree extends Activity implements OnClickListener {

	SharedPreferences spf;
	EditText etStName, etStID;
	RadioGroup rgDegree;
	Button bSubmit;
	int done = 0;
	String name, stId, degree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.selectdegree);
		etStName = (EditText) findViewById(R.id.etStName);
		etStID = (EditText) findViewById(R.id.etStID);
		rgDegree = (RadioGroup) findViewById(R.id.rgDegree);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bSubmit.setOnClickListener(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (done == 1) {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		name = etStName.getText().toString();
		stId = etStID.getText().toString();
		int rbId = rgDegree.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton) findViewById(rbId);
		degree = rb.getText().toString();

		if (name.length() != 0 && stId.length() != 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Name: " + name + "\nID: " + stId + "\nDegree: " + degree
							+ "\n\nContinue?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									done = 1;
									Editor editor = spf.edit();
									editor.putBoolean("first_login", false);
									editor.putString("StudentName", name);
									editor.putString("StudentID", stId);
									editor.putString("StudentDegree", degree);
									editor.putString("SemesterAdd",
											"Add Semester");
									editor.commit();

									Intent intent = new Intent(
											"com.aakash.studentmanager.SEMESTERLIST");
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
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
			d.setTitle("Confirm Personal Details:");
			d.show();
		} else {
			Dialog d = new Dialog(SelectDegree.this);
			d.setTitle("All fields are mandatory!");
			d.show();
		}
	}
}