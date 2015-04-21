package com.paisa.listener;


import com.paisa.utils.ConstantUtil;
import com.paisa.utils.UtilityMethods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class IncomingSms extends BroadcastReceiver {

	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();

	public void onReceive(Context context, Intent intent) {

		final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();

					Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

					int duration = Toast.LENGTH_LONG;
					
					System.out.println((context.getSharedPreferences(ConstantUtil.CATEGORY, 0).getString(ConstantUtil.BANK_NAME, "")).toUpperCase());
					
					if(senderNum.contains(context.getSharedPreferences(ConstantUtil.CATEGORY, 0).getString(ConstantUtil.BANK_NAME, "").toUpperCase()))
						
					{
//						Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration).show();
						stringExtract(message,context);
					}
					else
					{
						clearAbortBroadcast();
					}
				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);

		}
	}

	public void stringExtract(String smsMsg,Context mContext)
	{

		// TODO Auto-generated method stub
		//		String smsMsg = "Your a/c XX2504 is debited on 25/01/2014 by INR 130.00 towards Purchase. Avl Bal : INR 5,689.11. For more details login to m.sc.com/in - StanChart";
		//        String smsMsg = "Test INR 15000.78 to test INR 1257";
		if(checkINR(smsMsg)){
			Bundle myBundle = new Bundle();
			myBundle.putString(ConstantUtil.BANK, mContext.getSharedPreferences(ConstantUtil.CATEGORY, 0).getString(ConstantUtil.BANK_NAME, ""));
			myBundle.putDouble(ConstantUtil.AMOUNT, Double.parseDouble(extractINR(smsMsg)));
			myBundle.putString(ConstantUtil.MESSAGE, "You just spend");
			myBundle.putString(ConstantUtil.TITLE, "Transaction");
			myBundle.putString(ConstantUtil.TYPE, "Transaction");

				UtilityMethods.displayTradusNotification(myBundle, mContext);

			System.out.println(extractINR(smsMsg));
		}else if(checkRS(smsMsg)){
			Bundle myBundle = new Bundle();
			myBundle.putString(ConstantUtil.BANK, "hdfc");
			myBundle.putDouble(ConstantUtil.AMOUNT, Double.parseDouble(extractRS(smsMsg)));
			myBundle.putString(ConstantUtil.DATE, "date");
			myBundle.putString(ConstantUtil.MESSAGE, "You just spend");
			myBundle.putString(ConstantUtil.TITLE, "Transaction");
			myBundle.putString(ConstantUtil.TYPE, "Transaction");

			for(int i=0;i<1;i++)
				UtilityMethods.displayTradusNotification(myBundle, mContext);

			System.out.println();
		}else{
			System.out.println("nothing to extract");    
		}


	}
	public boolean checkINR(String msg){
		if(msg.toLowerCase().indexOf("inr")>0 && msg.toLowerCase().indexOf("debit")>0){
		
				return true;
		}else{
			return false;
		}
	}
//	public boolean checkRS(String msg){
//		if(msg.indexOf("Rs.")>0){
//			if(msg.indexOf("Rs") == msg.lastIndexOf("Rs.")){
//				return false;
//			}else{
//				return true;
//			}
//		}else{
//			return false;
//		}
//	}
	
	// Added condition to check word debit as well
	public boolean checkRS(String msg){
		if(msg.toLowerCase().indexOf("rs")>0 && msg.toLowerCase().indexOf("debit")>0){
	
				return true;
		}else{
			return false;
		}
	}
	public String extractINR(String msg){
		int inrIndex = msg.toLowerCase().indexOf("inr");
		int spaceIndex = msg.indexOf(" ",inrIndex+4);

		String s1 = msg.substring(inrIndex+3,spaceIndex);
		s1=s1.replaceAll(",", "");
		return s1.trim();
	}

	public String extractRS(String msg){
		
		int rsIndex = msg.toLowerCase().indexOf("rs");
		int spaceIndex = msg.indexOf(" ",rsIndex+4);

		String s1 = msg.substring(rsIndex+2,spaceIndex);
		s1=s1.replaceAll(",", "");
        if(s1.indexOf(".")==0){
        s1=s1.substring(1,s1.length());
        }

		return s1.trim();
	}



}