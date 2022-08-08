package com.libt.intern.samples.recycler.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.libt.intern.R;
import com.libt.intern.samples.cake.bean.PeopleInfo;
import com.libt.intern.samples.cake.control.EditPopupWindow;
import com.libt.intern.samples.cake.control.SelectPhotoPopupWindow;
import com.libt.intern.samples.cake.util.CircleHeadPhotoUtil;
import com.libt.intern.samples.cake.util.Constant;
import com.libt.intern.samples.cake.util.SharedPreferencesUtil;
import com.libt.intern.samples.recycler.RecyclerActivity;

import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by qwe on 2018/9/10.
 */

public class EditProfileFragment extends Fragment implements View.OnClickListener {
    private ImageButton backBut;
    private ImageView image;
    private TextView nickName;
    private ImageButton nameBut;
    private TextView weShowID;
    private ImageButton idBut;
    private TextView email;
    private ImageButton emailBut;
    private TextView introduction;
    private ImageButton introductionBut;
    private RelativeLayout nameR;
    private RelativeLayout idR;
    private RelativeLayout emailR;
    private RelativeLayout introductionR;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private SelectPhotoPopupWindow menuWindow;
    private Button exit;
    // TODO to peiyun, EditPopupWindow instance can use a same one.

    private Uri uriImage;
    private String headPath = "";
    private String userName = "";
    private Handler handler;
    private List<PeopleInfo> infoList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.editprofil, container, false);
        // IsBarUtil.isBar(getContext());
        myfindviewById();
        intiVariable();
        myOnClick();
        new Thread(new Runnable() {
            @Override
            public void run() {
                infoList = LitePal.where("name like ?", userName).find(PeopleInfo.class);
                handler.sendEmptyMessage(100);
            }
        }).start();
        return view;
    }

    private void myOnClick() {
        backBut.setOnClickListener(this);
        image.setOnClickListener(this);
        nameR.setOnClickListener(this);
        idR.setOnClickListener(this);
        emailR.setOnClickListener(this);
        introductionR.setOnClickListener(this);
        nameBut.setOnClickListener(this);
        idBut.setOnClickListener(this);
        emailBut.setOnClickListener(this);
        introductionBut.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    private void intiVariable() {
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance(getContext());
        userName = sharedPreferencesUtil.getString(SharedPreferencesUtil.USER_NAME);
        headPath = getActivity().getExternalCacheDir() + userName + ".jpg";
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (headPath != null) {
                    File headFile = new File(headPath);
                    if (headFile.exists()) {
                        CircleHeadPhotoUtil.setCirclePhoto(headPath, image);
                    }
                nickName.setText(infoList.get(0).getNickName());
                email.setText(infoList.get(0).getEmail());
//				weShowID.setText(infoList.get(0).getWeShowID());
                weShowID.setText(userName);
                introduction.setText(infoList.get(0).getIntroduction());
            }}
        };

    }

    private void myfindviewById() {
        backBut = (ImageButton) view.findViewById(R.id.back);
        image = (ImageView) view.findViewById(R.id.image);
        nickName = (TextView) view.findViewById(R.id.name);
        nameBut = (ImageButton) view.findViewById(R.id.namebut);
        weShowID = (TextView) view.findViewById(R.id.ID);
        idBut = (ImageButton) view.findViewById(R.id.IDbut);
        email = (TextView) view.findViewById(R.id.email);
        emailBut = (ImageButton) view.findViewById(R.id.emailbut);
        introduction = (TextView) view.findViewById(R.id.introduction);
        introductionBut = (ImageButton) view.findViewById(R.id.introductionbut);
        nameR = (RelativeLayout) view.findViewById(R.id.nameR);
        idR = (RelativeLayout) view.findViewById(R.id.IDR);
        emailR = (RelativeLayout) view.findViewById(R.id.emailR);
        introductionR = (RelativeLayout) view.findViewById(R.id.IntoductionR);
        image.setImageResource(R.drawable.head);
        exit = (Button) view.findViewById(R.id.exit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(getContext(), RecyclerActivity.class);
                startActivity(intent);
                // TODO to peiyun, Modify Load data on a subthread, not on Main UI thread.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        infoList.get(0).setEmail(email.getText().toString());
                        infoList.get(0).setNickName(nickName.getText().toString());
                        infoList.get(0).setWeShowID(weShowID.getText().toString());
                        infoList.get(0).setIntroduction(introduction.getText().toString());
                        infoList.get(0).save();
                        //  finish();
                    }
                }).start();

                break;
            case R.id.image:
                menuWindow = new SelectPhotoPopupWindow(getContext(), itemsOnClick);
                menuWindow.showAtLocation(this.view.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.nameR:
                getEditPopupWindow(nickName, 20, false, this.view.findViewById(R.id.nameR));
                break;
            case R.id.namebut:
                getEditPopupWindow(nickName, 20, false, this.view.findViewById(R.id.nameR));
                break;
            case R.id.IDR:
                Toast.makeText(getContext(), "unchangeable", Toast.LENGTH_SHORT).show();
//				getEditPopupWindow(weShowID,20,true,EditProfileFragment.this.view.findViewById(R.id.IDR));
                break;
            case R.id.IDbut:
                Toast.makeText(getContext(), "unchangeable", Toast.LENGTH_SHORT).show();
//				getEditPopupWindow(weShowID,20,true,EditProfileFragment.this.view.findViewById(R.id.IDR));
                break;
            case R.id.emailR:
                getEditPopupWindow(email, 20, true, this.view.findViewById(R.id.emailR));
                break;
            case R.id.emailbut:
                getEditPopupWindow(email, 20, true, this.view.findViewById(R.id.emailR));
                break;
            case R.id.IntoductionR:
                getEditPopupWindow(introduction, 50, true, this.view.findViewById(R.id.IntoductionR));
                break;
            case R.id.introductionbut:
                getEditPopupWindow(introduction, 50, true, this.view.findViewById(R.id.IntoductionR));
                break;
            case R.id.exit:
                sharedPreferencesUtil.putBoolean(SharedPreferencesUtil.IS_AUTO, false);
                Intent intentExit = new Intent(getContext(), com.libt.intern.samples.recycler.RegisteActivity.class);
                //setResult(Constant.REGISTRATION__EXIT_CODE,intentExit);
                startActivity(intentExit);
                // finish();
            default:
                break;
        }
    }


    private void getEditPopupWindow(TextView textView, Integer maxAmount, boolean isUp, View view) {
        EditPopupWindow editPopupWindow = new EditPopupWindow(textView, getContext(), maxAmount);
        if (isUp) {
            editPopupWindow.showUp2(view);
        } else {
            editPopupWindow.showAsDropDown(view, 0, 0);
        }
        editPopupWindow.showSoft();
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    File outputImage = new File(headPath);
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= 24) {
                        uriImage = FileProvider.getUriForFile(getContext(),
                                "com.libt.cameraalbumtest.fileprovider", outputImage);
                    } else {
                        uriImage = Uri.fromFile(outputImage);
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
                    startActivityForResult(intent, Constant.TAKE_PHOTO);
                    break;
                case R.id.btn_pick_photo:
                    if (ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) getContext(),
                                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        openAlbum();
                    }

                    break;
                default:
                    break;
            }
        }


    };

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, Constant.CHOOSE_PHOTO);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case Constant.TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    CircleHeadPhotoUtil.setCirclePhoto(headPath, image);
                }
                break;

            case Constant.CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals((uri.getAuthority()))) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downlosds/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();

        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            CircleHeadPhotoUtil.saveHeadPath(imagePath, headPath);
            CircleHeadPhotoUtil.setCirclePhoto(headPath, image);

        } else {
            Toast.makeText(getContext(), "fail to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getContext(), "YOU DENIED THE PERMISSION", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public final static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileprovider";
    }
}

