package com.aakash.studentmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SimpleBrowser extends Activity implements OnClickListener,
		OnItemSelectedListener {

	EditText searchKey;
	WebView ourBrow;
	InputMethodManager imm;
	String filetype;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplebrowser);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onResume() {
		super.onResume();

		ourBrow = (WebView) findViewById(R.id.wvBrowser);
		ourBrow.getSettings().setJavaScriptEnabled(true);
		ourBrow.getSettings().setLoadWithOverviewMode(true);
		ourBrow.getSettings().setUseWideViewPort(true);
		ourBrow.setWebViewClient(new WebViewClient());

		Button go = (Button) findViewById(R.id.bGo);
		Button back = (Button) findViewById(R.id.bBack);
		Button forward = (Button) findViewById(R.id.bForward);
		Button refresh = (Button) findViewById(R.id.bRefresh);
		Button clearHistory = (Button) findViewById(R.id.bHistory);

		searchKey = (EditText) findViewById(R.id.etSearchKey);

		Bundle b = getIntent().getExtras();
		searchKey.setText(b.getString("searchKey"));
		ourBrow.loadUrl("http://www.google.com/#q=" + b.getString("searchKey"));

		go.setOnClickListener(this);
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		refresh.setOnClickListener(this);
		clearHistory.setOnClickListener(this);

		// Hiding keyboard after using an EditText
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchKey.getWindowToken(), 0);

		Spinner spinner = (Spinner) findViewById(R.id.sfiletype);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.filetype_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bGo:
			String key = searchKey.getText().toString();
			String theWebsite = "http://www.google.com/#q=" + key;
			if (!filetype.equals("Default"))
				theWebsite += " filetype:" + filetype;
			ourBrow.loadUrl(theWebsite);
			break;

		case R.id.bBack:
			if (ourBrow.canGoBack()) {
				ourBrow.goBack();
			}
			break;

		case R.id.bForward:
			if (ourBrow.canGoForward()) {
				ourBrow.goForward();
			}
			break;

		case R.id.bRefresh:
			ourBrow.reload();
			break;

		case R.id.bHistory:
			ourBrow.clearHistory();
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

		filetype = arg0.getItemAtPosition(arg2).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		filetype = arg0.getItemAtPosition(0).toString();
	}
}