package com.wqy.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * The HelloAndroidActivity represents
 * @version $Id$
 * @author wangqingyi
 */
public class HelloAndroidActivity extends Activity implements OnClickListener {

	private Button button;
	private EditText editText;
	private static String TAG = "AutoReadSms";

	/**
	 * Called when the activity is first created.
	 * @param savedInstanceState If the activity is being re-initialized after
	 *            previously being shut down then this Bundle contains the data it most
	 *            recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		IntentFilter intentFilter = new IntentFilter("SECURITY.CODE.SMS_RECEIVED");
		registerReceiver(this.smsReceiver, intentFilter);
		new InitSocketConnection().execute("");
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);
		findViews();
	}

	/**
	 * {@inheritDoc}
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	private class InitSocketConnection extends AsyncTask<String, Integer, String> {

		/**
		 * This creates a InitSocketConnection
		 */
		public InitSocketConnection() {
		}

		@Override
		protected String doInBackground(String... arg0) {
			Looper.prepare();
			return null;
		}
	}

	private void findViews() {
		this.button = (Button) findViewById(R.id.button1);
		this.button.setOnClickListener(this);
		this.editText = (EditText) findViewById(R.id.editText1);
	}

	/**
	 * {@inheritDoc}
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		// TODO this can send a command to server,when wait for security code

	}

	/**
	 * This
	 */
	public BroadcastReceiver smsReceiver = new BroadcastReceiver() {

		/**
		 * {@inheritDoc}
		 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "receive a message!!");
			String msgBody = intent.getStringExtra("security_code");
			String securtyCode = TextUtils.split(msgBody, ":")[1];
			HelloAndroidActivity.this.editText.setText(securtyCode);
		}
	};

}
