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

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.paisa.beans.CategoryDetails;
import com.paisa.category.CategoryListAdapter;
import com.paisa.database.MyExpenseDatabase;
import com.paisa.utils.ConstantUtil;
import com.paisa.utils.TLog;
import com.paisa.utils.UtilityMethods;

public class Transactionfragment extends SherlockFragment implements OnItemClickListener{

	private static final String TAG = "Transactionfragment";

	private GridView categoryGrid;
	private CategoryListAdapter categoryListAdapter;
	private List<CategoryDetails> mCategoryList;
	private Context mContext;
	private FragmentActivity mActivity;
	SharedPreferences sharedPreferences;

	private Button doneSelectingCategory;

	private Bundle b;

	private String myPersonalCategory;

	public static Fragment newInstance() {
		Fragment f = new Transactionfragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    b = getActivity().getIntent().getExtras();
	    TLog.v(TAG, "data"+b.toString());
		

		sharedPreferences = getActivity().getBaseContext().getSharedPreferences(ConstantUtil.CATEGORY, 0);
		myPersonalCategory = sharedPreferences.getString(ConstantUtil.CATEGORY,"");

		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		mContext = getActivity().getApplicationContext();
		mActivity = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.category_on_home, container, false);

		try {
			mCategoryList = UtilityMethods.loadWords(mContext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set the Category Adapter
		categoryGrid = (GridView) view.findViewById(R.id.categoryGrid); 
		doneSelectingCategory = (Button)view.findViewById(R.id.doneSelectingCategory);
		doneSelectingCategory.setVisibility(View.GONE);
		List<CategoryDetails> mPersonalList = new ArrayList<CategoryDetails>();

		if(mCategoryList!=null)
		{
			for (int i = 0; i < mCategoryList.size(); i++) {
				if(myPersonalCategory.toLowerCase().contains(mCategoryList.get(i).getName().toLowerCase()))
				{
					mPersonalList.add(mCategoryList.get(i));
				}
				
			}
			categoryListAdapter = new CategoryListAdapter(mContext,mPersonalList );
			categoryGrid.setAdapter(categoryListAdapter);
		}
		
		categoryGrid.setOnItemClickListener(this);
		
		
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TLog.v(TAG, "clicked s");

		
		switch (arg0.getId()) {
		case R.id.categoryGrid:
			
					
				MyExpenseDatabase med = new MyExpenseDatabase(mContext);
				med.open();
				if(b!=null)
				{
					TLog.v(TAG, ""+b.getLong("id"));
					med.updateTable(b.getLong("id"),"", mCategoryList.get(arg2).getIconSet(), mCategoryList.get(arg2).getName());
				}
				med.close();
				mActivity.finish();
//				Intent i = new Intent();
//				  i.setAction(Intent.ACTION_MAIN);
//				  i.addCategory(Intent.CATEGORY_HOME);
//				  this.startActivity(i);

				break;
		default:
			break;
		}
		
	}


}
