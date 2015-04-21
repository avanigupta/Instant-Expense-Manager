package com.paisa.events;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.paisa.Bankfragment;
import com.paisa.MainActivity;
import com.paisa.R;
import com.paisa.beans.PolicyDetails;
import com.paisa.utils.ConstantUtil;

public class EventsFragment extends DialogFragment implements OnItemClickListener,OnClickListener,TextWatcher{

	private static final String TAG = "EventsFragment";
	private Context mContext;
	private FragmentActivity mActivity;
	private Button doneSelectingCategory;
	private EditText nameEditText;
	private EditText dateEditText;
	private Button addIconButton;
	private Button addReminderButton;
	private ListView listView1;
	private SharedPreferences sharedPreferences;
	private RelativeLayout reminderLayout;
	private Typeface  myAppIcomTheme;
	private List<PolicyDetails> myEvents = new ArrayList<PolicyDetails>();
	private EventListAdapter eventListAdapter;
	private EditText budgetAmountEditText;
	private EditText dateBudgetEditText;
	private Button addBudgetIconButton;
	private RelativeLayout budgetLayout;
	private Button addBudget;
	// date and time
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;

	static final int TIME_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID = 1;




	public static Fragment newInstance() {
		Fragment f = new EventsFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);

		mContext = getActivity().getApplicationContext();
		mActivity = getActivity();
		sharedPreferences = getActivity().getBaseContext().getSharedPreferences(ConstantUtil.CATEGORY, 0);
		myAppIcomTheme = Typeface.createFromAsset(mContext.getAssets(), "icomoon.ttf");


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_on_home, container, false);

		doneSelectingCategory = (Button) view.findViewById(R.id.doneSelectingCategory);
		nameEditText = (EditText) view.findViewById(R.id.nameEditText);
		dateEditText = (EditText) view.findViewById(R.id.dateEditText);
		addIconButton = (Button) view.findViewById(R.id.addIconButton);
		addReminderButton = (Button) view.findViewById(R.id.addReminderButton);
		listView1 = (ListView) view.findViewById(R.id.listView1);
		reminderLayout = (RelativeLayout)view.findViewById(R.id.reminderLayout);


		budgetAmountEditText = (EditText) view.findViewById(R.id.budgetAmountEditText);
		dateBudgetEditText = (EditText) view.findViewById(R.id.dateBudgetEditText);
		addBudgetIconButton = (Button) view.findViewById(R.id.addBudgetIconButton);
		budgetLayout = (RelativeLayout)view.findViewById(R.id.budgetLayout);
		addBudget = (Button) view.findViewById(R.id.addBudget);

		addBudget.setOnClickListener(this);
		addIconButton.setTypeface(myAppIcomTheme);
		addIconButton.setOnClickListener(this);
		addReminderButton.setOnClickListener(this);
		addBudgetIconButton.setTypeface(myAppIcomTheme);
		addBudgetIconButton.setOnClickListener(this);
		dateEditText.setOnClickListener(this);
		dateBudgetEditText.setOnClickListener(this);
		reminderLayout.setVisibility(View.GONE);
		listView1.setVisibility(View.GONE);
		budgetLayout.setVisibility(View.GONE);
		doneSelectingCategory.setOnClickListener(this);
		return view;
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

	/**
	 * Create a compatible helper that will manipulate the action bar if
	 * available.
	 */


	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {


		case R.id.doneSelectingCategory:
			
			Bankfragment sf = new Bankfragment();
			sf.setArguments(new Bundle());
			((MainActivity)mActivity).onItemSelected(sf, true);

			break;
		case R.id.dateEditText:
			mActivity.showDialog(MainActivity.TIME_DIALOG_ID);
			break;

		case R.id.dateBudgetEditText:
				mActivity.showDialog(MainActivity.DATE_DIALOG_ID);
				break;
		case R.id.addIconButton:


			if(nameEditText.getEditableText().toString().equals("") && dateEditText.getEditableText().toString().equals(""))
			{
				Toast.makeText(getActivity(), "Please enter a name/date", Toast.LENGTH_SHORT).show();
			}
			else
			{
				PolicyDetails pd = new PolicyDetails();
				pd.setName(nameEditText.getEditableText().toString().trim());
				pd.setDate(dateEditText.getEditableText().toString().trim());
				myEvents.add(pd);
				nameEditText.getEditableText().clear();
				dateEditText.getEditableText().clear();

				eventListAdapter = new EventListAdapter(mContext, myEvents);
				listView1.setAdapter(eventListAdapter);
				listView1.setVisibility(View.VISIBLE);
				reminderLayout.setVisibility(View.GONE);
				budgetLayout.setVisibility(View.GONE);
			}
			break;

		case R.id.addBudgetIconButton:


			if(budgetAmountEditText.getEditableText().toString().equals("") && dateBudgetEditText.getEditableText().toString().equals(""))
			{
				Toast.makeText(getActivity(), "Please enter an amount/date", Toast.LENGTH_SHORT).show();
			}
			else
			{
				PolicyDetails pd = new PolicyDetails();
				pd.setName(budgetAmountEditText.getEditableText().toString().trim());
				pd.setDate(dateBudgetEditText.getEditableText().toString().trim());
				pd.setType(1);
				myEvents.add(pd);
				budgetAmountEditText.getEditableText().clear();
				dateBudgetEditText.getEditableText().clear();

				eventListAdapter = new EventListAdapter(mContext, myEvents);
				listView1.setAdapter(eventListAdapter);
				listView1.setVisibility(View.VISIBLE);
				reminderLayout.setVisibility(View.GONE);
				budgetLayout.setVisibility(View.GONE);
			}
			break;


		case R.id.addReminderButton:

			listView1.setVisibility(View.GONE);
			budgetLayout.setVisibility(View.GONE);
			reminderLayout.setVisibility(View.VISIBLE);

			break;


		case R.id.addBudget:

			listView1.setVisibility(View.GONE);
			budgetLayout.setVisibility(View.VISIBLE);
			reminderLayout.setVisibility(View.GONE);

			break;
		default:

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

}
