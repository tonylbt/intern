package com.libt.intern;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.libt.intern.activity.BaseActivity;
import com.libt.intern.manager.Actions;
import com.libt.intern.manager.SamplesManager;

import java.util.ArrayList;

/**
 * Created by lbingt on 2018/08/10.
 */
public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";
    private RecyclerView mSamplesRv;
    private MainSamplesAdapter mRVAdapter;

//    private ListView mSampleListView;
//    private SampleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    public void initView() {
//        mSampleListView = (ListView)findViewById(R.id.lv_samples);

        mSamplesRv = (RecyclerView) findViewById(R.id.rv_samples);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSamplesRv.setLayoutManager(layoutManager);
    }

    private void initData() {
//        mAdapter = new SampleAdapter(this, SamplesManager.getInstance().getActions(this));
//        mSampleListView.setAdapter(mAdapter);
//        mSampleListView.setOnItemClickListener(mItemClickListener);

        mRVAdapter = new MainSamplesAdapter(this, SamplesManager.getInstance().getActions(this));
        mSamplesRv.setAdapter(mRVAdapter);
    }

//    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Actions action = (Actions) mAdapter.getItem(position);
//            action.doAction();
//
//            String content = (action).getName();
//            Toast.makeText(MainActivity.this, content, Toast.LENGTH_LONG).show();
//        }
//    };

//    private class SampleAdapter extends BaseAdapter {
//        private Context mContext;
//        private ArrayList<Actions> mItems;
//
//        public SampleAdapter(Context context, ArrayList<Actions> list) {
//            mContext = context;
//            mItems = list;
//        }
//
//        @Override
//        public int getCount() {
//            return mItems.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mItems.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                viewHolder = new ViewHolder();
//                convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
//                viewHolder.name = (TextView) convertView.findViewById(android.R.id.text1);
//
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            viewHolder.name.setText(mItems.get(position).getName());
//            viewHolder.name.setTextSize(16);
//
//            return convertView;
//        }
//    }

//    private class ViewHolder {
//        TextView name;
//    }
}
