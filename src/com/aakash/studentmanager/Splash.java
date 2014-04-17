package com.aakash.studentmanager;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;

public class Splash extends Activity {

	TextToSpeech ttobj;
	SharedPreferences spf;
	boolean first_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		spf = PreferenceManager.getDefaultSharedPreferences(this);
		first_login = spf.getBoolean("first_login", true);
		ttobj = new TextToSpeech(getApplicationContext(),
				new TextToSpeech.OnInitListener() {

					@Override
					public void onInit(int status) {
						if (status != TextToSpeech.ERROR) {
							ttobj.setLanguage(Locale.UK);
							String toSpeak = "Welcome to Student Manager. This application is developed by The Fantastic 4, at I. I. T. Bombay.";
							ttobj.setSpeechRate(0.9f);
							ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
						}
					}
				});

		setContentView(R.layout.splash);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(8000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent i;
					if (first_login) {
						i = new Intent("com.aakash.studentmanager.SELECTDEGREE");
					} else {
						i = new Intent("com.aakash.studentmanager.SEMESTERLIST");
					}
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		if (ttobj != null) {
			ttobj.stop();
			ttobj.shutdown();
		}

		super.onPause();
		finish();
	}
}