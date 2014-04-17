package com.aakash.studentmanager;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<Context, Void, Void> {

	MediaPlayer mysound;
	AlertDemo ast;

	public MyAsyncTask(AlertDemo ad) {
		ast = ad;
	}

	@Override
	protected Void doInBackground(Context... params) {
		// TODO Auto-generated method stub
		mysound = MediaPlayer.create(params[0], R.raw.startingtune);

		for (int i = 0; i < 10; i++) {
			mysound = MediaPlayer.create(params[0], R.raw.startingtune);
			mysound.start();
			while (mysound.getCurrentPosition() < 4999) {
				if (ast.flag == 1) {
					mysound.release();
					ast.flag = 0;
					return null;
				}
			}
			mysound.release();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mysound.release();
	}
}