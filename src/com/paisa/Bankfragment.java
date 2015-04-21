package com.paisa;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.paisa.events.EventsFragment;
import com.paisa.utils.ConstantUtil;

public class Bankfragment extends SherlockFragment implements OnClickListener{

	private static final String TAG = "Bankfragment";

	private Context mContext;
	private FragmentActivity mActivity;
	SharedPreferences sharedPreferences;

	private Button doneSelectingCategory;

	private EditText copyEditText;

	private Button pasteButton;
	private static ClipboardManager m_ClipboardManager;
	public static Fragment newInstance() {
		Fragment f = new Bankfragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sharedPreferences = getActivity().getBaseContext().getSharedPreferences(ConstantUtil.CATEGORY, 0);

		setRetainInstance(true);
		setHasOptionsMenu(true);

		mContext = getActivity().getApplicationContext();
		mActivity = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bank_on_home, container, false);

		doneSelectingCategory = (Button)view.findViewById(R.id.doneSelectingCategory);
		doneSelectingCategory.setOnClickListener(this);
		copyEditText = (EditText)view.findViewById(R.id.copyEditText);
//		pasteButton = (Button)view.findViewById(R.id.pasteButton);
		m_ClipboardManager = (ClipboardManager)     mContext.getSystemService(Context.CLIPBOARD_SERVICE); 
		copyEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater = ((SherlockFragmentActivity)getActivity()).getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * The action bar home/up action should open or close the drawer.
		 * mDrawerToggle will take care of this.
		 */
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
//		case R.id.pasteButton:
//
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//			{
//			    copyEditText.setText(m_ClipboardManager.getText().toString());
//			}
//			else
//			{
//				Toast.makeText(mContext, "Please type manually", Toast.LENGTH_SHORT).show();
//			}
//			break;
		case R.id.doneSelectingCategory:

			if(copyEditText.getEditableText().toString().trim().equals(""))
			{
				Toast.makeText(mContext, "Please type the senders name", Toast.LENGTH_SHORT).show();

			}
			else
			{
				sharedPreferences.edit().putString(ConstantUtil.BANK_NAME, copyEditText.getEditableText().toString().trim()).commit();
				mContext.getSharedPreferences("FirstTime", 0).edit().putBoolean("isFirst", true).commit();
				MyExpenseFragment sf = new MyExpenseFragment();
				sf.setArguments(new Bundle());
				((MainActivity)mActivity).onItemSelected(sf, true);
			}
			break;

		default:
			break;
		}

	}


}
