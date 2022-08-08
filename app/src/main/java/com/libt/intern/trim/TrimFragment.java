package com.libt.intern.trim;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.libt.intern.R;
import com.libt.intern.fragment.BaseFragment;
import com.libt.intern.util.PermissionUtils;

import java.io.File;

public class TrimFragment extends BaseFragment {
    private static final String TAG = "TrimFragment";
    private TextView mContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_trim, null);
        mContent = view.findViewById(R.id.tv_content);

        view.findViewById(R.id.btn_trim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sourceFile = new File(Environment.getExternalStorageDirectory() + "/Documents/WorldHistory.txt");
                startTrimInThread(sourceFile);
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionUtils.requestPermission(getActivity(), REQUEST_CODE_REQUEST_PERMISSION);
    }

    private static final int MSG_TRIM_FILE_OVER = 101;
    private static final int MSG_UPDATE_CONTENT = 102;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TRIM_FILE_OVER:
//                    break;
                case MSG_UPDATE_CONTENT:
                    String oldTx = "";
                    if (mContent.getText() != null) {
                        oldTx = mContent.getText().toString();
                    }
                    mContent.setText(oldTx + "\ntrim file over.");
                    break;
            }
        }
    };

    private void startTrimInThread(File file) {
        new Thread() {
            @Override
            public void run() {
                FileTrimHelper.trimTextFile(file);

                mHandler.sendEmptyMessage(MSG_TRIM_FILE_OVER);
            }
        }.start();
    }

    private static final int REQUEST_CODE_REQUEST_PERMISSION = 200;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REQUEST_PERMISSION) {

        }
    }

}

