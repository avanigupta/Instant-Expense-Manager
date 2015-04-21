package com.paisa;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.FrameLayout.LayoutParams;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.paisa.listener.FragmentListener;
import com.paisa.utils.ConstantUtil;
import com.paisa.utils.TLog;

public class MainActivity extends SherlockFragmentActivity implements FragmentListener {

	private static final int CONTENT_VIEW_ID = 666;
	private static final String TAG = "MainActivity";
	private boolean isOnSaveInstantState;
	private int mFragmentHolder;
	private Fragment mCurrentFragment;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	public static final int TIME_DIALOG_ID = 0;
	public static final int DATE_DIALOG_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frame = new FrameLayout(this);
		frame.setId(CONTENT_VIEW_ID);
		setContentView(frame, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		Intent myIntent = getIntent();

		if(myIntent!=null && myIntent.getStringExtra(ConstantUtil.FROM_PUSHNOTIFICATION)!=null && myIntent.getStringExtra(ConstantUtil.FROM_PUSHNOTIFICATION).equals(ConstantUtil.FROM_PUSHNOTIFICATION))
		{
			TLog.v(TAG, "show ot");
			showFragment(ConstantUtil.FRAGMENT2FRAGMENT_REQUEST, Transactionfragment.newInstance(), true);
		}
		else
		{
			if(getSharedPreferences("FirstTime", 0).getBoolean("isFirst", false))
			{
				setInitialFragment(MyExpenseFragment.newInstance());
			}
			else
			{
				setInitialFragment(MainFragment.newInstance());
			}

		}

		//			if (savedInstanceState == null) {
		//			setInitialFragment();
		//		}
	}


	private void setInitialFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(CONTENT_VIEW_ID, fragment).commit();
	}


	@Override
	public void onItemSelected(Fragment frg, boolean animate) {
		showFragment(ConstantUtil.FRAGMENT2FRAGMENT_REQUEST, frg, animate);

	}
	/**
	 * shows any fragment requested
	 * uses positions for menu, -1 for rest
	 * for tab change event isTabTransaction parameter is set to true
	 */
	public void showFragment(int code, Fragment sFrag, boolean animate) {
		mCurrentFragment = getCurrentFragment();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if(animate)
			ft.setCustomAnimations(R.anim.slide_left_to_right, R.anim.slide_right_to_left,R.anim.slide_out_right_to_left, R.anim.slide_out_left_to_right);

		switch (code) {



		case ConstantUtil.HOME:
			ft.addToBackStack(null);
		case ConstantUtil.TAB_FRAGMENT_REQUEST:

			if(mCurrentFragment==null || (sFrag.getClass() != (mCurrentFragment.getClass())))
//				ft.addToBackStack(null);

			break;
		case ConstantUtil.FRAGMENT2FRAGMENT_REQUEST:
			if(mCurrentFragment==null || (sFrag.getClass() != (mCurrentFragment.getClass())))
//				ft.addToBackStack(null);
			break;
		case ConstantUtil.CATEGORY_HOME_REQUEST:
			if(mCurrentFragment==null || (sFrag.getClass() != (mCurrentFragment.getClass())))
//				ft.addToBackStack(null);
			break;
		case ConstantUtil.POP_RELAUNCH_FRAGMENT:
			if(mCurrentFragment==null || (sFrag.getClass() != (mCurrentFragment.getClass()))){
//				ft.addToBackStack(null);
			}
			break;
		default:
			ft.addToBackStack(null);
			break;
		}

		ft.replace(CONTENT_VIEW_ID,sFrag).commit();

		//		ft.replace(mFragmentHolder, sFrag);
		//		ft.commit();
		mCurrentFragment = sFrag;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		isOnSaveInstantState = true;
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		TLog.i(TAG,"onSaveInstanceStateonRestoreInstanceState");

		super.onRestoreInstanceState(savedInstanceState);
	}


	@Override
	public void requestStartActivity(Intent intent) {
		// TODO Auto-generated method stub

	}


	@Override
	public void requestStartActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub

	}
	/**
	 * This method returns the current fragment which is above in stack
	 * @return
	 */
	public Fragment getCurrentFragment()
	{	
		return getSupportFragmentManager().findFragmentById(mFragmentHolder);
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new DatePickerDialog(this,
					mTimeSetListener,
					mYear, mMonth, mDay);
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,
					mDateSetListener,
					mYear, mMonth, mDay);
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case TIME_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}    

	private void updateDisplay(EditText mDateDisplay) {
		mDateDisplay.setText(
				new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-")
				.append(mDay).append("-")
				.append(mYear).append(" "));
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay((EditText)findViewById(R.id.dateBudgetEditText));
		}
	};

	private DatePickerDialog.OnDateSetListener mTimeSetListener =
			new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay((EditText)findViewById(R.id.dateEditText));
		}
	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}


}
