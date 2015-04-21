package com.paisa;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.paisa.beans.CategoryDetails;
import com.paisa.category.CategoryListAdapter;
import com.paisa.events.EventsFragment;
import com.paisa.utils.ConstantUtil;
import com.paisa.utils.TLog;
import com.paisa.utils.UtilityMethods;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

public class MainFragment extends SherlockFragment implements OnItemClickListener,OnClickListener{

	private static final String TAG = "MainFragment";


	private GridView categoryGrid;
	private CategoryListAdapter categoryListAdapter;
	private List<CategoryDetails> mCategoryList;


	private Context mContext;

	private FragmentActivity mActivity;

	private Button doneSelectingCategory;
	SharedPreferences sharedPreferences;

	public static Fragment newInstance() {
		Fragment f = new MainFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		mContext = getActivity().getApplicationContext();
		mActivity = getActivity();


//		Bundle myBundle = new Bundle();
//		myBundle.putString(ConstantUtil.BANK, "hdfc");
//		myBundle.putInt(ConstantUtil.AMOUNT, 500);
//		myBundle.putString(ConstantUtil.DATE, "date");
//		myBundle.putString(ConstantUtil.MESSAGE, "You just spend");
//		myBundle.putString(ConstantUtil.TITLE, "Transaction");
//		myBundle.putString(ConstantUtil.TYPE, "Transaction");
//
//
//		
//		UtilityMethods.displayTradusNotification(myBundle, getActivity());
		
		TLog.v(TAG, "list"+mContext.getResources().getStringArray(R.array.my_cat_list_new));

		sharedPreferences = getActivity().getBaseContext().getSharedPreferences(ConstantUtil.CATEGORY, 0);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
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
		doneSelectingCategory = (Button) view.findViewById(R.id.doneSelectingCategory);
		doneSelectingCategory.setOnClickListener(this);
		if(mCategoryList!=null)
		{
			categoryListAdapter = new CategoryListAdapter(mContext,mCategoryList );
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

	/**
	 * This list item click listener implements very simple view switching by
	 * changing the primary content text. The drawer is closed when a selection
	 * is made.
	 */
//	private class DrawerItemClickListener implements ListView.OnItemClickListener {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			mContent.setText(Shakespeare.DIALOGUE[position]);
//			mActionBar.setTitle(Shakespeare.TITLES[position]);
//			mDrawerLayout.closeDrawer(listView);
//		}
//	}

	/**
	 * A drawer listener can be used to respond to drawer events such as
	 * becoming fully opened or closed. You should always prefer to perform
	 * expensive operations such as drastic relayout when no animation is
	 * currently in progress, either before or after the drawer animates.
	 * 
	 * When using ActionBarDrawerToggle, all DrawerLayout listener methods
	 * should be forwarded if the ActionBarDrawerToggle is not used as the
	 * DrawerLayout listener directly.
	 */

	/**
	 * Create a compatible helper that will manipulate the action bar if
	 * available.
	 */
	private ActionBarHelper createActionBarHelper() {
		return new ActionBarHelper();
	}

	

	private class ActionBarHelper {
		private final ActionBar mActionBar;
		private CharSequence mDrawerTitle;
		private CharSequence mTitle;

		private ActionBarHelper() {
			mActionBar = ((SherlockFragmentActivity)getActivity()).getSupportActionBar();
		}

		public void init() {
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mTitle = mDrawerTitle = getActivity().getTitle();
		}

		/**
		 * When the drawer is closed we restore the action bar state reflecting
		 * the specific contents in view.
		 */
		public void onDrawerClosed() {
			mActionBar.setTitle(mTitle);
		}

		/**
		 * When the drawer is open we set the action bar to a generic title. The
		 * action bar should only contain data relevant at the top level of the
		 * nav hierarchy represented by the drawer, as the rest of your content
		 * will be dimmed down and non-interactive.
		 */
		public void onDrawerOpened() {
			mActionBar.setTitle(mDrawerTitle);
		}

		public void setTitle(CharSequence title) {
			mTitle = title;
		}
	}

	


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		TLog.v(TAG, "clicked s");

		
		switch (arg0.getId()) {
		case R.id.categoryGrid:
			
			TLog.v(TAG, "clicked"+mCategoryList.get(arg2).isChecked());
			if(mCategoryList.get(arg2).isChecked())
			{
				mCategoryList.get(arg2).setChecked(false);
				categoryListAdapter.notifyDataSetChanged();
			}
			else
			{
				mCategoryList.get(arg2).setChecked(true);
				categoryListAdapter.notifyDataSetChanged();
			}
			
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onClick(View arg0) {

		Set<String> myCategory = new HashSet<String>() ;
		switch (arg0.getId()) {
		case R.id.doneSelectingCategory:
				
			
			for(int i=0;i<mCategoryList.size();i++)
			{
				if(mCategoryList.get(i).isChecked())
				{
					myCategory.add(mCategoryList.get(i).getName());
				}
			}
		
//			EventsFragment sf = new EventsFragment();
//			sf.setArguments(new Bundle());
//			((MainActivity)mActivity).onItemSelected(sf, true);
			
			//***  To skip the events fragment view we commented the above code and added BankFragment Instead
			Bankfragment sf = new Bankfragment();
			sf.setArguments(new Bundle());
			((MainActivity)mActivity).onItemSelected(sf, true);

			sharedPreferences.edit().clear().putString(ConstantUtil.CATEGORY, myCategory.toString()).commit();
//			Intent intent = new Intent(Intent.ACTION_MAIN);
//			intent.addCategory(Intent.CATEGORY_LAUNCHER);
//			intent.setClassName("com.android.mms", "com.android.mms.ui.ConversationList");		
//			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
