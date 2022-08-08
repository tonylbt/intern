package com.libt.intern.samples.recycler.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.libt.intern.R;
import com.libt.intern.samples.recycler.MenuActivity;
import com.libt.intern.samples.recycler.adapter.UserAdapter;
import com.libt.intern.samples.recycler.bean.GsonBean;
import com.libt.intern.samples.recycler.bean.People;
import com.libt.intern.samples.recycler.recyclerutil.DataUtil;
import com.libt.intern.samples.recycler.recyclerutil.GetJson;
import com.libt.intern.samples.recycler.recyclerutil.SharedPreferencesUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2018/9/10.
 */

public class RecyclerFragment extends Fragment {
    private List<People> userList = new ArrayList<>();
    private UserAdapter adapter;
    private String jsonData;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        view = inflater.inflate(R.layout.rmain, container, false);
        LitePal.getDatabase();
        init();
        change();
        setPullRefresher();
        return view;    }


    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        GsonBean javabean = gson.fromJson(jsonData, GsonBean.class);
        for (GsonBean.DataBean dataBean : javabean.data) {
            int level = dataBean.getLevel();
            String userId = dataBean.getWork1().getUser().getUserId();
            String userId2 = dataBean.getWork2().getUser().getUserId();
            String thumbnailUrl = dataBean.getWork1().getVideo().getThumbnailUrl();
            String thumbnailUrl2 = dataBean.getWork2().getVideo().getThumbnailUrl();
            userList.add(new People(userId, userId2, level, level, thumbnailUrl, thumbnailUrl2, thumbnailUrl, thumbnailUrl2));
        }
    }

    private void setPullRefresher() {
        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        //set Header to MaterialHeader
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        //set Footer to classic style
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                change();
                RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
                adapter = new UserAdapter(userList);
                recyclerView.setAdapter(adapter);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                int start = userList.size();
                refreshlayout.finishLoadmore(2000/*,false*/);
                parseJSONWithGSON(jsonData);
                adapter.notifyItemRangeChanged(start, userList.size() - start);//add list
                // TODO to liuyc, Modify Load data on a subthread, not on Main UI thread.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<People> peoples = DataSupport.findAll(People.class);
                        if (peoples.size() < userList.size()) {
                            for (int k = peoples.size(); k < userList.size(); k++) {
                                People user=new People();
                                user.setItem(String.valueOf(k));
                                user.setLeftflag(0);
                                user.setRightflag(0);
                                user.save();
                            }
                        }
                    }
                }).start();


            }
        });
    }

    public void change() {
        String time = DataUtil.formatTime();  //get time
        String cs2 = SharedPreferencesUtil.getInstance(getContext()).getTime();//get saved time
        //judge whether time change
        // TODO to liuyc, Modify Load data on a subthread, not on Main UI thread.
        if (!time.equals(cs2)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<People> peoples = DataSupport.where("leftflag= ?", "1").find(People.class);
                    for (People people : peoples) {
                        people.setLeftflag(0);
                        people.setRightflag(0);
                        people.setLeftall(people.getLeftall());
                        people.setRightall(people.getRightall());
                        people.save();
                    }
                    List<People> peoples2 = DataSupport.where("rightflag= ?", "1").find(People.class);
                    for (People people : peoples2) {
                        people.setLeftflag(0);
                        people.setRightflag(0);
                        people.setLeftall(people.getLeftall());
                        people.setRightall(people.getRightall());
                        people.save();
                    }
                }
            }).start();
            SharedPreferencesUtil.getInstance(getContext()).putTime(time);
        }
    }

    public void init() {
        jsonData = GetJson.getJson("jsondata.json", getContext());
        TextView setting=(TextView)view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MenuActivity.class));
            }
        });
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserAdapter(userList);
        recyclerView.setAdapter(adapter);
        parseJSONWithGSON(jsonData);
        // TODO to liuyc, Modify Load data on a subthread, not on Main UI thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<People> peoples = DataSupport.findAll(People.class);
                if (peoples.size() == 0) {
                    for (int k = 0; k < userList.size(); k++) {
                        People people = new People();
                        people.setItem(String.valueOf(k));
                        people.save();
                    }
                }
            }
        }).start();
        Boolean isFirstRun= SharedPreferencesUtil.getInstance(getContext()).getFirstRun();
        if (isFirstRun) {
            String time = DataUtil.formatTime();//get now time
            SharedPreferencesUtil.getInstance(getContext()).putTime(time);
            SharedPreferencesUtil.getInstance(getContext()).putFirstRun(false);
        }
    }
}
