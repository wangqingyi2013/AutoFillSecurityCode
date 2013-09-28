package com.wqy.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * The Smsreceiveandmask represents
 * @version $Id$
 * @author wangqingyi
 */
public class Smsreceiveandmask extends BroadcastReceiver {

	private final String TAG = "AutoReadSms";
	private final static String PANDABUS_SMS = "SMS";
	private final static String BROADCAST_KEY = "SECURITY.CODE.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(this.TAG, ">>>>>>>onReceive start");
		StringBuilder body = new StringBuilder();// 短信内容
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			Object[] _pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] message = new SmsMessage[_pdus.length];
			for (int i = 0; i < _pdus.length; i++) {
				message[i] = SmsMessage.createFromPdu((byte[]) _pdus[i]);
			}
			for (SmsMessage currentMessage : message) {
				body.append(currentMessage.getDisplayMessageBody());
			}
			broadcastMessage(context, body.toString());
		}
		// This to abort this broadcast.so can not show this message to user or any others receiver
		// this.abortBroadcast();
		Log.i(this.TAG, ">>>>>>>onReceive end");
	}

	private void broadcastMessage(Context context, String msg) {
		if (msg.contains(PANDABUS_SMS)) {
			Intent i = new Intent(BROADCAST_KEY);
			i.putExtra("security_code", msg);
			context.sendBroadcast(i);
		}
	}
}
