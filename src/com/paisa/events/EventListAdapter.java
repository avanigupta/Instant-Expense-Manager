package com.paisa.events;


import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paisa.R;
import com.paisa.beans.CategoryDetails;
import com.paisa.beans.PolicyDetails;
import com.paisa.utils.TLog;
import com.paisa.utils.TypeFaceUtil;
import com.paisa.utils.TypeFaceUtil.EnumCustomTypeFace;

public class EventListAdapter extends BaseAdapter {

	private static final String TAG = "CategoryListAdapter";
	private LayoutInflater layoutInflater;
	private Context mContext;
	List<PolicyDetails> mCategoryData;
	private Typeface myAppIcomTheme;

	/**
	 * CategoryListAdapter constructor
	 * @param context
	 * @param myData
	 */
	public EventListAdapter(Context context,List<PolicyDetails> myData) {

		this.mContext = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.mCategoryData = myData;		
		myAppIcomTheme = Typeface.createFromAsset(context.getAssets(), "icomoon.ttf");


	}

	@Override
	public int getCount() {
		return mCategoryData.size();
	}

	@Override
	public PolicyDetails getItem(int arg0) {
		return mCategoryData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.event_row_item, null);


			viewHolder.eventDate = (TextView) convertView
					.findViewById(R.id.eventDate);

			viewHolder.eventName = (TextView) convertView
					.findViewById(R.id.eventName);

			viewHolder.eventType = (View) convertView
					.findViewById(R.id.viewType);


			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.eventName.setText(getItem(position).getName());
		viewHolder.eventDate.setText(getItem(position).getDate());
		if(getItem(position).getType() == 1)
		{
			viewHolder.eventType.setBackgroundColor(mContext.getResources().getColor(R.color.ligtest_green));
		}
		else
		{
			viewHolder.eventType.setBackgroundColor(mContext.getResources().getColor(R.color.ligtest_green_compliment));
		}

		return convertView;
	}

	private class ViewHolder {
		TextView eventDate;
		TextView eventName;
		View eventType;

	}

}