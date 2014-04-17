package com.aakash.studentmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager.LayoutParams;

public class AlertDemo extends DialogFragment {

	AsyncTask<Context, Void, Void> MyAsyncTask;
	@SuppressWarnings("rawtypes")
	AsyncTask task;
	public int flag = 0;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		/**
		 * Turn Screen On and Unlock the keypad when this alert dialog is
		 * displayed
		 */
		getActivity().getWindow().addFlags(
				LayoutParams.FLAG_TURN_SCREEN_ON
						| LayoutParams.FLAG_DISMISS_KEYGUARD);

		/** Creating a alert dialog builder */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		/** Setting title for the alert dialog */
		builder.setTitle("Assignment alert");

		/** Setting the content for the alert dialog */
		builder.setMessage("IMPORTANT: Assignment due today!");

		Context context = getActivity().getApplicationContext();
		try {
			task = (new MyAsyncTask(this)).execute(context);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		/** Defining an OK button event listener */
		builder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/** Exit application on click OK */
				try {
					if (task != null
							&& task.getStatus() != AsyncTask.Status.FINISHED) {
						flag = 1;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					getActivity().finish();
				}
			}
		});

		/** Creating the alert dialog window */
		return builder.create();
	}

	/** The application should be exit, if the user presses the back button */
	@Override
	public void onDestroy() {
		super.onDestroy();
		flag = 1;
	}
}