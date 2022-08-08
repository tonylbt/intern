package com.libt.intern.samples.recycler.adapter;

import java.util.List;

import org.litepal.crud.DataSupport;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.libt.intern.R;
import com.libt.intern.samples.recycler.bean.People;
import com.libt.intern.samples.recycler.recyclerutil.GlideCircleTransform;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by qwe on 2017/11/13.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
	private List<People> mUserList;
	static class ViewHolder extends RecyclerView.ViewHolder {
		ImageView image1, image2, t1, t2;
		TextView username1, username2, l1, l2, tv;
		View fruitView;
		CheckBox xh1, xh2;
		TextView zs1, zs2;
		public ViewHolder(View view) {
			super(view);
			fruitView = view;
			tv = (TextView) view.findViewById(R.id.textView);
			String str = " Battle <font color='#ffff00'>#</font>Bollywood Dizlogue";
			tv.setText(Html.fromHtml(str));
			image1 = (ImageView) view.findViewById(R.id.yh1);
			image1.setScaleType(ImageView.ScaleType.CENTER_CROP);
			image2 = (ImageView) view.findViewById(R.id.yh2);
			image2.setScaleType(ImageView.ScaleType.CENTER_CROP);
			username1 = (TextView) view.findViewById(R.id.name1);
			username2 = (TextView) view.findViewById(R.id.name2);
			l1 = (TextView) view.findViewById(R.id.level1);
			l2 = (TextView) view.findViewById(R.id.level2);
			t1 = (ImageView) view.findViewById(R.id.tx1);
			t2 = (ImageView) view.findViewById(R.id.tx2);
			xh1 = (CheckBox) view.findViewById(R.id.xh1);
			xh2 = (CheckBox) view.findViewById(R.id.xh2);
			zs1 = (TextView) view.findViewById(R.id.ps1);
			zs2 = (TextView) view.findViewById(R.id.ps2);
		}
	}

	public UserAdapter(List<People> userList) {
		mUserList = userList;
	}

	@Override
	public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
		final TextView zs1 = (TextView) view.findViewById(R.id.ps1);
		final TextView zs2 = (TextView) view.findViewById(R.id.ps2);
		final ViewHolder holder = new ViewHolder(view);
		holder.xh1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = holder.getAdapterPosition();
				String p = String.valueOf(position);
				People people1 = new People();
				people1.setLeftflag(6);
				people1.updateAll("item=?", p);
				if (holder.xh2.isChecked()) {
					holder.xh1.setChecked(false);
					Toast.makeText(v.getContext(), "只能给两人中的一名投票", Toast.LENGTH_SHORT).show();
				} else if (holder.xh1.isChecked()) {
					int a = Integer.parseInt(zs1.getText().toString());
					zs1.setText(String.valueOf(a + 1));
					List<People> peoples = DataSupport.where("item like ?", p).find(People.class);
					for (People people : peoples) {
						people.setLeftflag(1);
						people.setRightflag(0);
						people.setRightall(people.getRightall());
						people.setLeftall(people.getLeftall() + 1);
						people.save();
					}
				} else {
					int a = Integer.parseInt(zs1.getText().toString());
					zs1.setText(String.valueOf(a - 1));
					List<People> peoples = DataSupport.where("item like ?", p).find(People.class);
					for (People people : peoples) {
						people.setLeftflag(0);
						people.setRightflag(0);
						people.setRightall(people.getRightall());
						people.setLeftall(people.getLeftall() - 1);
						people.save();
					}
				}
			}
		});
		holder.xh2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = holder.getAdapterPosition();
				String p = String.valueOf(position);
				People people1 = new People();
				people1.setRightflag(6);
				people1.updateAll("item=?", p);
				People user = mUserList.get(position);
				if (holder.xh1.isChecked()) {
					holder.xh2.setChecked(false);
					Toast.makeText(v.getContext(), "只能给两人中的一名投票", Toast.LENGTH_SHORT).show();
				} else if (holder.xh2.isChecked()) {
					int a = Integer.parseInt(zs2.getText().toString());
					zs2.setText(String.valueOf(a + 1));
					List<People> peoples = DataSupport.where("item like ?", p).find(People.class);
					for (People people : peoples) {
						people.setRightflag(1);
						people.setLeftflag(0);
						people.setLeftall(people.getLeftall());
						people.setRightall(people.getRightall() + 1);
						people.save();
					}
				} else {
					int a = Integer.parseInt(zs2.getText().toString());
					zs2.setText(String.valueOf(a - 1));
					List<People> peoples = DataSupport.where("item like ?", p).find(People.class);
					for (People people : peoples) {
						people.setRightflag(0);
						people.setLeftflag(0);
						people.setLeftall(people.getLeftall());
						people.setRightall(people.getRightall() - 1);
						people.save();

					}
				}
			}
		});
		return holder;
	}
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		People user = mUserList.get(position);
		String p = String.valueOf(position);
		Glide.with(holder.t1.getContext()).load(user.getTx1()).centerCrop().placeholder(R.drawable.money)
				.transform(new GlideCircleTransform(holder.t1.getContext(), 5,
						(holder.t1.getContext()).getResources().getColor(R.color.colorAccent)))
				.diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.t1);
		Glide.with(holder.t2.getContext()).load(user.getTx2()).centerCrop().placeholder(R.drawable.money)
				.transform(new GlideCircleTransform(holder.t2.getContext(), 5,
						(holder.t2.getContext()).getResources().getColor(R.color.colorPrimary)))
				.diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.t2);
		Glide.with(holder.image1.getContext()).load(user.getImage1()).into(holder.image1);
		Glide.with(holder.image2.getContext()).load(user.getImage2()).into(holder.image2);
		holder.username1.setText(user.getName1());
		holder.username2.setText(user.getName2());
		holder.l1.setText("Lv." + String.valueOf(user.getLevel1()));
		holder.l2.setText("Lv." + String.valueOf(user.getLevel2()));
		List<People> peoples = DataSupport.where("item like ?", p).find(People.class);
		holder.xh1.setChecked(false);
		holder.xh2.setChecked(false);
		for (People people : peoples) {
			holder.zs1.setText(String.valueOf(people.getLeftall()));
			holder.zs2.setText(String.valueOf(people.getRightall()));
			if (people.getLeftflag() == 1) {
				holder.xh1.setChecked(true);
			}
			if (people.getRightflag() == 1) {
				holder.xh2.setChecked(true);
			}
		}
	}
	@Override
	public int getItemCount() {
		return mUserList.size();
	}
}