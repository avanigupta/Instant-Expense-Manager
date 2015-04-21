package com.paisa.category;


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

public class CategoryListAdapter extends BaseAdapter {

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
	public CategoryListAdapter(Context context,List<CategoryDetails> myData) {

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
			convertView = layoutInflater.inflate(R.layout.category_home_grid_item, null);


			viewHolder.categoryIcon = (TextView) convertView
					.findViewById(R.id.categoryIcon);

			viewHolder.categoryTitle = (TextView) convertView
					.findViewById(R.id.categoryTitle);


			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		//		if(getItem(position).getIconSet().contains("&#x"))
		//		{
//					getItem(position).getIconSet().replace("&#x", "\'ue");
		viewHolder.categoryIcon.setTypeface(myAppIcomTheme);
					viewHolder.categoryIcon.setText(Html.fromHtml(getItem(position).getIconSet()));
//		viewHolder.categoryIcon.setText("");


		//		}
		if(getItem(position).isChecked())
		{
			viewHolder.categoryIcon.setBackgroundColor(mContext.getResources().getColor(R.color.category_color_compliment));
		}
		else
		{
			viewHolder.categoryIcon.setBackgroundColor(mContext.getResources().getColor(android.R.color.white));

		}

		//		viewHolder.categoryIcon.setTypeface(TypeFaceUtil.getInstance(mContext).getCustomTypeFace(EnumCustomTypeFace.APP_ICON_THEME));
		viewHolder.categoryTitle.setText(getItem(position).getName());
		TLog.d(TAG, "title"+getItem(position).getName());
		return convertView;
	}

	private class ViewHolder {
		TextView categoryIcon;
		TextView categoryTitle;

	}

}