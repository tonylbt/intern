package com.libt.intern.samples.cake.adapter;

import java.io.File;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.libt.intern.R;
import com.libt.intern.samples.cake.bean.PeopleInfo;
import com.libt.intern.samples.cake.util.CircleHeadPhotoUtil;

/**
 * Created by Cake on 2018/8/6.
 */

public class PeopleAdapter extends BaseAdapter {
	private List<PeopleInfo> mList;
	private Context mContext;
	private LayoutInflater mLayoutInflater;

	public PeopleAdapter(List<PeopleInfo> mList, Context mContext) {
		this.mList = mList;
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public PeopleInfo getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.people_item, parent, false);
			holder.mImageviView = (ImageView) convertView.findViewById(R.id.imageView);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.motto = (TextView) convertView.findViewById(R.id.motto);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        /**
         * TODO to peiyun， Modify Tips：
         * java.lang.NullPointerException
         * at java.io.File.<init>(File.java:268)
         * at com.libt.intern.samples.cake.adapter.PeopleAdapter.getView(PeopleAdapter.java:60)
         */
		if(mList.get(position).getHeadPath() != null && !mList.get(position).getNickName().equals("")){

			File headFile = new File(mList.get(position).getHeadPath());
			if(headFile.exists()){
				CircleHeadPhotoUtil.setCirclePhoto(mList.get(position).getHeadPath(), holder.mImageviView);
			}
		}else{
			holder.mImageviView.setImageResource(R.drawable.head);
		}
//		Toast.makeText(mContext,mList.get(position).getName(),Toast.LENGTH_SHORT).show();
		if(mList.get(position).getNickName() != null && !mList.get(position).getNickName().equals("")){
			holder.name.setText(mList.get(position).getNickName());
		}else{
			holder.name.setText(mList.get(position).getName());
		}
		holder.motto.setText(mList.get(position).getIntroduction());
		return convertView;
	}
	public class ViewHolder {
		ImageView mImageviView;
		TextView name;
		TextView motto;
	}
}
