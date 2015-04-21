package com.paisa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.paisa.beans.CategoryDetails;
import com.paisa.database.MyExpenseDatabase;
import com.paisa.utils.ConstantUtil;
import com.paisa.utils.TLog;
import com.paisa.utils.UtilityMethods;
import com.sherlock.navigationdrawer.compat.SherlockActionBarDrawerToggle;

public class MyExpenseFragment extends SherlockFragment implements OnItemClickListener,OnClickListener{

	private static final String TAG = "MyExpenseFragment";

	private DrawerLayout mDrawerLayout;

	private ActionBarHelper mActionBar;

	private SherlockActionBarDrawerToggle mDrawerToggle;
	private Context mContext;

	private FragmentActivity mActivity;
	SharedPreferences sharedPreferences;

	private LinearLayout linearLayout1;

	private GridView expenseGrid;

	private TextView timeBudget;
	List<CategoryDetails> mCategoryDetails = new ArrayList<CategoryDetails>();

	private Button monthlyExpense;


	private Button categoriseExpense;
	Map<Integer, String > myCalender = new HashMap<Integer, String>();

	private RelativeLayout categoryLayout;


	public static Fragment newInstance() {
		Fragment f = new MyExpenseFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fillMap();
		Bundle myBundle = new Bundle();
		sharedPreferences = getActivity().getBaseContext().getSharedPreferences(ConstantUtil.CATEGORY, 0);
		setRetainInstance(true);
		setHasOptionsMenu(true);

		mContext = getActivity().getApplicationContext();
		mActivity = getActivity();

	}

	private void fillMap() {

		myCalender.put(1, "Jan");
		myCalender.put(2, "Feb");
		myCalender.put(3, "Mar");
		myCalender.put(4, "Apr");
		myCalender.put(5, "May");
		myCalender.put(6, "Jun");
		myCalender.put(7, "Jul");
		myCalender.put(8, "Aug");
		myCalender.put(9, "Sep");
		myCalender.put(10, "Oct");
		myCalender.put(11, "Nov");
		myCalender.put(12, "Dec");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.expense_on_home, container, false);


		mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

		mDrawerLayout.setDrawerListener(new DemoDrawerListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
		categoryLayout = (RelativeLayout) view.findViewById(R.id.categoryLayout);
		
		expenseGrid = (GridView) view.findViewById(R.id.expenseGrid);
		timeBudget = (TextView) view.findViewById(R.id.timeBudget);
		monthlyExpense = (Button) view.findViewById(R.id.button1);
		categoriseExpense = (Button) view.findViewById(R.id.button2);

		monthlyExpense.setOnClickListener(this);
		categoriseExpense.setOnClickListener(this);
		expenseGrid.setOnItemClickListener(this);

		mActionBar = createActionBarHelper();
		mActionBar.init();

		getAllCategoryInfo();

		double mGrandTotal = 0;
		for(int i=0;i<mCategoryDetails.size();i++)
		{
			mGrandTotal = mGrandTotal + mCategoryDetails.get(i).getCategoryAmount();
		}
		if(mGrandTotal == 0){ 
			timeBudget.setText(R.string.you_have_not_yet_started_anything);
//		mActivity.finish();
		}
		else
			timeBudget.setText(getString(R.string.your_current_monthly_spending_)+mGrandTotal);

		TLog.v(TAG, "mCategoryDetails"+mCategoryDetails);
		// ActionBarDrawerToggle provides convenient helpers for tying together
		// the
		// prescribed interactions between a top-level sliding drawer and the
		// action bar.
		mDrawerToggle = new SherlockActionBarDrawerToggle(this.getActivity(), mDrawerLayout, R.drawable.ic_drawer_light, R.string.app_name, R.string.app_name);
		mDrawerToggle.syncState();




		return view;
	}

	private void getMonthlyInfo() {
		mCategoryDetails = new ArrayList<CategoryDetails>();

		MyExpenseDatabase med = new MyExpenseDatabase(mContext);
		med.open();
		Cursor c = med.getMonthlyBudget();
		TLog.v(TAG, "coiunt"+c.getCount());
		if(c.moveToNext())
		{
			do
			{
				CategoryDetails cd = new CategoryDetails();
				cd.setName(c.getString(5));
				TLog.v(TAG, "month"+myCalender.get(c.getInt(8)));
				cd.setIconSet(myCalender.get(c.getInt(8)));
				cd.setDate(c.getString(3));
				cd.setCategoryAmount(c.getDouble(7));
				cd.setPosition(2);
				TLog.v(TAG,""+c.getDouble(7));
				mCategoryDetails.add(cd);
			}
			while(c.moveToNext());

		}
		med.close();
		MyExpenseListAdapter mela = new MyExpenseListAdapter(mContext, mCategoryDetails);
		expenseGrid.setAdapter(mela);

	}

	private void getAllCategoryInfo() {
		mCategoryDetails = new ArrayList<CategoryDetails>();
		MyExpenseDatabase med = new MyExpenseDatabase(mContext);
		med.open();
		Cursor c = med.getAllInfo();
		if(c.moveToNext())
		{
			do
			{
				CategoryDetails cd = new CategoryDetails();
				cd.setName(c.getString(5));
				cd.setIconSet(c.getString(2));
				cd.setDate(c.getString(3));
				cd.setCategoryAmount(c.getDouble(7));
				TLog.v(TAG,""+c.getDouble(7));
				mCategoryDetails.add(cd);
				TLog.v(TAG, "MyExpense1"+mCategoryDetails);
			}
			while(c.moveToNext());

		}
		med.close();

		MyExpenseListAdapter mela = new MyExpenseListAdapter(mContext, mCategoryDetails);
		expenseGrid.setAdapter(mela);
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
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
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
	private class DemoDrawerListener implements DrawerLayout.DrawerListener {
		@Override
		public void onDrawerOpened(View drawerView) {
			mDrawerToggle.onDrawerOpened(drawerView);
			mActionBar.onDrawerOpened();
		}

		@Override
		public void onDrawerClosed(View drawerView) {
			mDrawerToggle.onDrawerClosed(drawerView);
			mActionBar.onDrawerClosed();
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState) {
			mDrawerToggle.onDrawerStateChanged(newState);
		}
	}

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

		case R.id.expenseGrid:

			DetailExpenseFragment def = new DetailExpenseFragment();
			Bundle myBundle = new Bundle();
			myBundle.putString(ConstantUtil.CATEGORY, mCategoryDetails.get(arg2).getName());
			def.setArguments(myBundle);
			((MainActivity)mActivity).showFragment(ConstantUtil.HOME,def, true);

		default:
			break;
		}

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {

		case R.id.button1:
			getMonthlyInfo();
			if (mDrawerLayout.isDrawerOpen(categoryLayout))
				mDrawerLayout.closeDrawer(categoryLayout);


			break;
		case R.id.button2:
			getAllCategoryInfo();
			if (mDrawerLayout.isDrawerOpen(categoryLayout))
				mDrawerLayout.closeDrawer(categoryLayout);


			break;

		default:
			break;
		}
	}

}
