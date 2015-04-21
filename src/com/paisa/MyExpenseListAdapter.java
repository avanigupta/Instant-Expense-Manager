package com.paisa;


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
import com.paisa.utils.TLog;
import com.paisa.utils.TypeFaceUtil;
import com.paisa.utils.TypeFaceUtil.EnumCustomTypeFace;

public class MyExpenseListAdapter extends BaseAdapter {

	private static final String TAG = "CategoryListAdapter";
	private LayoutInflater layoutInflater;
	private Context mContext;
	List<CategoryDetails> mCategoryData;
	private Typeface myAppIcomTheme;

	/**
	 * CategoryListAdapter constructor
	 * @param context
	 * @param myData
	 */
	public MyExpenseListAdapter(Context context,List<CategoryDetails> myData) {

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
	public CategoryDetails getItem(int arg0) {
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
			convertView = layoutInflater.inflate(R.layout.grid_item_layout, null);


			viewHolder.categoryIcon = (TextView) convertView
					.findViewById(R.id.iconSet);

			viewHolder.categoryTitle = (TextView) convertView
					.findViewById(R.id.categoryName);
			
			viewHolder.categoryPrice = (TextView) convertView
					.findViewById(R.id.categoryPrice);



			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(getItem(position).getPosition() == 2)
		{
			viewHolder.categoryIcon.setText(getItem(position).getIconSet());
			viewHolder.categoryTitle.setVisibility(View.GONE);
		}
		else
		{
			viewHolder.categoryIcon.setText(Html.fromHtml(getItem(position).getIconSet()));
			viewHolder.categoryIcon.setTypeface(myAppIcomTheme);
			viewHolder.categoryTitle.setText(getItem(position).getName());

		}

		
		viewHolder.categoryPrice.setText(""+getItem(position).getCategoryAmount());

		return convertView;
	}

	private class ViewHolder {
		public TextView categoryPrice;
		TextView categoryIcon;
		TextView categoryTitle;

	}

}