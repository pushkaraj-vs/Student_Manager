package com.aakash.studentmanager;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MyBrowser extends Activity implements OnClickListener {

	EditText url;
	WebView ourBrow;
	InputMethodManager imm;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybrowser);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onResume() {
		super.onResume();
		ourBrow = (WebView) findViewById(R.id.wvMyBrowser);
		ourBrow.getSettings().setJavaScriptEnabled(true);
		ourBrow.getSettings().setLoadWithOverviewMode(true);
		ourBrow.getSettings().setUseWideViewPort(true);
		ourBrow.setWebViewClient(new WebViewClient());

		Button go = (Button) findViewById(R.id.bMyGo);
		Button back = (Button) findViewById(R.id.bMyBack);
		Button forward = (Button) findViewById(R.id.bMyForward);
		Button refresh = (Button) findViewById(R.id.bMyRefresh);
		Button clearHistory = (Button) findViewById(R.id.bMyHistory);

		url = (EditText) findViewById(R.id.etCoursePage);

		Bundle b = getIntent().getExtras();

		url.setText(b.getString("webpage"));
		ourBrow.loadUrl("http://" + url.getText().toString());

		go.setOnClickListener(this);
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		refresh.setOnClickListener(this);
		clearHistory.setOnClickListener(this);

		// Hiding keyboard after using an EditText
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bMyGo:
			String key = url.getText().toString();
			String theWebsite = "http://" + key;
			ourBrow.loadUrl(theWebsite);
			break;

		case R.id.bMyBack:
			if (ourBrow.canGoBack()) {
				ourBrow.goBack();
			}
			break;

		case R.id.bMyForward:
			if (ourBrow.canGoForward()) {
				ourBrow.goForward();
			}
			break;

		case R.id.bMyRefresh:
			ourBrow.reload();
			break;

		case R.id.bMyHistory:
			ourBrow.clearHistory();
			break;
		}
	}
}