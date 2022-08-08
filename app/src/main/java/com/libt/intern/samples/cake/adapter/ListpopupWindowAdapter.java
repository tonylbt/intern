package com.libt.intern.samples.cake.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.libt.intern.R;

/**
 * Created by Cake on 2018/8/9.
 */

public class ListpopupWindowAdapter extends BaseAdapter {
	private List<String> mStringList = new ArrayList<>();
	private Context mContext;
	private String nowSeleteName;
	private int itemHeight;

	public ListpopupWindowAdapter(Context context, List<String> list, String selectName, float height) {
		mContext = context;
		mStringList = list;
		nowSeleteName = selectName;
		itemHeight = (int) height;
	}

	@Override
	public int getCount() {
		return mStringList.size();
	}

	@Override
	public Object getItem(int i) {
		return mStringList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder lViewHolder = null; // 一开始为null
		if (view == null) {
			lViewHolder = new ViewHolder();
			view = View.inflate(mContext, R.layout.listpopu_item, null);
			lViewHolder.itemTextView = (TextView) view.findViewById(R.id.textView);
			view.setTag(lViewHolder);
		} else {
			lViewHolder = (ViewHolder) view.getTag();
		}
		lViewHolder.itemTextView.setText(mStringList.get(i));
		LinearLayout.LayoutParams lp;
		lp = (LinearLayout.LayoutParams) lViewHolder.itemTextView.getLayoutParams();
		lp.height = itemHeight;
		lViewHolder.itemTextView.setLayoutParams(lp);
		return view;
	}

	private class ViewHolder {
		TextView itemTextView;

	}

}
