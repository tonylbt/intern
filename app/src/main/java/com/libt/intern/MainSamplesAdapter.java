package com.libt.intern;

import java.util.List;

import com.libt.intern.manager.Actions;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainSamplesAdapter extends RecyclerView.Adapter<MainSamplesAdapter.SampleViewHolder> {
    private static final String TAG = "MainSamplesAdapter";

    private Context mContext;
    private List<Actions> mItems;

    public MainSamplesAdapter(Context context, List<Actions> items) {
        this.mContext = context;
        mItems = items;
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        SampleViewHolder viewHolder;
        view = LayoutInflater.from(mContext).inflate(R.layout.adapter_sample_item, parent, false);
        viewHolder = new SampleViewHolder(view);
        viewHolder.name = (TextView)view.findViewById(R.id.sample_item_title);
        viewHolder.desc = (TextView)view.findViewById(R.id.sample_item_desc);
        viewHolder.mView = view;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        Actions item = mItems.get(position);

        holder.name.setText(mItems.get(position).getName());
        holder.desc.setVisibility(View.GONE);

        holder.mView.setOnClickListener(mOnclickListener);
        holder.mView.setTag(item);
    }

    public void setItems(List<Actions> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).mCategory;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class SampleViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView desc;
        public View mView;

        public SampleViewHolder(View itemView) {
            super(itemView);
        }
    }

    private View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Actions action = (Actions) v.getTag();
            action.doAction();

            String content = (action).getName();
            Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
        }
    };
}
