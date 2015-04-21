package com.paisa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.paisa.beans.CategoryDetails;
import com.paisa.category.CategoryListAdapter;
import com.paisa.database.MyExpenseDatabase;
import com.paisa.utils.ConstantUtil;
import com.paisa.utils.TLog;
import com.paisa.utils.UtilityMethods;

public class DetailExpenseFragment extends SherlockFragment{

	private static final String TAG = "DetailExpenseFragment";

	private DetailExpenseListAdapter detailExpenseListAdapter;
	private List<CategoryDetails> mCategoryList;
	private Context mContext;
	private FragmentActivity mActivity;
	SharedPreferences sharedPreferences;
	private ListView categoryList;

	private String myPersonalCategory;

	private Bundle b;

	public static Fragment newInstance() {
		Fragment f = new DetailExpenseFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    b = getArguments();
	    TLog.v(TAG, "data"+b.toString());
		

		myPersonalCategory = b.getString(ConstantUtil.CATEGORY);

		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		mContext = getActivity().getApplicationContext();
		mActivity = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.category_list, container, false);

		getCategoryList(myPersonalCategory);

		// Set the Category Adapter
		categoryList = (ListView) view.findViewById(R.id.categoryList);
		detailExpenseListAdapter = new DetailExpenseListAdapter(mContext, mCategoryList);
		categoryList.setAdapter(detailExpenseListAdapter);
		
		return view;
	}
	
	private void getCategoryList(String myPersonalCategory) {
		mCategoryList = new ArrayList<CategoryDetails>();
		MyExpenseDatabase med = new MyExpenseDatabase(mContext);
		med.open();
		Cursor c = med.getCategoryInfo(myPersonalCategory);
		TLog.v(TAG, "mcur"+c.getCount());
		if(c.moveToNext())
		{
			do
			{
				CategoryDetails cd = new CategoryDetails();
				cd.setName(c.getString(5));
				cd.setIconSet(c.getString(2));
				cd.setDate(c.getString(3));
				cd.setCategoryAmount(c.getDouble(6));
				TLog.v(TAG,""+c.getDouble(6));
				TLog.v(TAG, "MyExpense1"+cd);

				mCategoryList.add(cd);
			}
			while(c.moveToNext());
			TLog.v(TAG, "mCategoryList"+mCategoryList.size());

		}
		med.close();
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


}
