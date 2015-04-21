package com.paisa.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.paisa.MainActivity;
import com.paisa.R;
import com.paisa.beans.CategoryDetails;
import com.paisa.database.MyExpenseDatabase;

public class UtilityMethods {


	private static int mId = 0;

	public static List<CategoryDetails> loadWords(Context mContext) throws IOException {

		String[] mStringList;		
		ArrayList<CategoryDetails> mCategoryList = new ArrayList<CategoryDetails>();
		mStringList  = mContext.getResources().getStringArray(R.array.my_cat_list_new);

		TLog.v("cddcdc", "mSt"+mStringList);
		for(int i=0;i<mStringList.length-1;i++)
		{

			String[] data = mStringList[i].split(":");
			CategoryDetails mCategory = new CategoryDetails();


			mCategory.setName(data[0]);
			mCategory.setIconSet(data[1]);

			mCategoryList.add(mCategory);
		}
		return mCategoryList;
	}

	public static long  displayTradusNotification(Bundle message, Context mContext)
	{


		MyExpenseDatabase myExpense = new MyExpenseDatabase(mContext);

		StringBuilder content = new StringBuilder();
		content.append(message.getString(ConstantUtil.MESSAGE));
		content.append(" Rs. ");

		content.append(message.getDouble(ConstantUtil.AMOUNT));
		content.append(ConstantUtil.FROM);
		content.append(message.getString(ConstantUtil.BANK));

		myExpense.open();
		long insertId =myExpense.insertInfo("", "", getCurrentTimeStampForHome(), message.getString(ConstantUtil.BANK), "", message.getDouble(ConstantUtil.AMOUNT),getCurrentMonth());
		myExpense.close();

		NotificationCompat.Builder mBuilder =new NotificationCompat.Builder( mContext)
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentTitle(message.getString(ConstantUtil.TITLE))
		.setAutoCancel(true)
		.setDefaults(Notification.DEFAULT_SOUND)
		.setContentText(content.toString());
		TaskStackBuilder stackBuilder = TaskStackBuilder.create( mContext);

		//			if(mSharedPreferences.getBoolean(SettingValues.SETTINGS_VIBRATE,true))
		//			{
		//				mBuilder.build().flags |= Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
		//				mBuilder.setVibrate(new long[]{0,300});		
		//			}
		//			else
		//			{
		//				mBuilder.setVibrate(new long[]{0,0});
		//			}
		//			if(mSharedPreferences.getBoolean(SettingValues.SETTINGS_PHONE_LED,true))
		//			{
		//				mBuilder.setLights(color.holo_orange_dark,1,0);
		//			}
		//			else
		//			{
		//				mBuilder.setLights(0,0,0);
		//			}

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(mContext,MainActivity.class);

		resultIntent.putExtra(ConstantUtil.FROM_PUSHNOTIFICATION, ConstantUtil.FROM_PUSHNOTIFICATION);
		resultIntent.putExtra(ConstantUtil.AMOUNT, message.getInt(ConstantUtil.AMOUNT));
		resultIntent.putExtra(ConstantUtil.BANK, message.getString(ConstantUtil.BANK));
		resultIntent.putExtra(ConstantUtil.MESSAGE, message.getString(ConstantUtil.MESSAGE));
		resultIntent.putExtra("id", insertId);
		resultIntent.putExtra("date",getCurrentTimeStampForHome());

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		// Adds the back stack for the Intent (but not the Intent itself)
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
						);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =(NotificationManager)  mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mId ++, mBuilder.build());
		return insertId;
	}
	public static String getCurrentTimeStampForHome() {
		SimpleDateFormat s = new SimpleDateFormat("dd MM yyyy");
		String format = s.format(new Date());
		return format;
	}
	public static int getCurrentMonth() {
		SimpleDateFormat s = new SimpleDateFormat("MM");
		String format = s.format(new Date());
		TLog.v("month", ""+Integer.parseInt(format));
		return Integer.parseInt(format);
	}


}
