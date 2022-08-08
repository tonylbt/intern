package com.libt.intern.samples.cake.cake;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.litepal.LitePal;

import android.annotation.TargetApi;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.libt.intern.R;
import com.libt.intern.activity.BaseActivity;
import com.libt.intern.samples.cake.bean.PeopleInfo;
import com.libt.intern.samples.cake.control.EditPopupWindow;
import com.libt.intern.samples.cake.control.SelectPhotoPopupWindow;
import com.libt.intern.samples.cake.util.CircleHeadPhotoUtil;
import com.libt.intern.samples.cake.util.Constant;
import com.libt.intern.samples.cake.util.IsBarUtil;

/**
 * Created by Cake on 2018/8/8.
 */

/**
 * TODO to peiyun， Modify Tips：
 * 1. Modify load data option to subThread;
 * 2. params name like: userlist -> userList
 * 3. People, User, DataBean, DataBean. Some bean java may merge or replace.
 */
public class EditProfileActivity extends BaseActivity implements View.OnClickListener {
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
    private SelectPhotoPopupWindow menuWindow;
    // TODO to peiyun, EditPopupWindow instance can use a same one.

    private Uri uriImage;
    private String headPath = "";
    private String userName = "";

    private Handler handler;
    private List<PeopleInfo> infoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofil);
        IsBarUtil.isBar(this);

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
    }

    private void intiVariable() {
        userName = getIntent().getStringExtra("userName");
        headPath = "/sdcard/cc/" + userName + ".jpg";
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (infoList.get(0).getHeadPath() != null) {
                    File headFile = new File(infoList.get(0).getHeadPath());
                    if (headFile.exists()) {
                        CircleHeadPhotoUtil.setCirclePhoto(infoList.get(0).getHeadPath(), image);
                    }
                }
                nickName.setText(infoList.get(0).getNickName());
                email.setText(infoList.get(0).getEmail());
//				weShowID.setText(infoList.get(0).getWeShowID());
                weShowID.setText(userName);
                introduction.setText(infoList.get(0).getIntroduction());
            }
        };

    }

    private void myfindviewById() {
        backBut = (ImageButton) findViewById(R.id.back);
        image = (ImageView) findViewById(R.id.image);
        nickName = (TextView) findViewById(R.id.name);
        nameBut = (ImageButton) findViewById(R.id.namebut);
        weShowID = (TextView) findViewById(R.id.ID);
        idBut = (ImageButton) findViewById(R.id.IDbut);
        email = (TextView) findViewById(R.id.email);
        emailBut = (ImageButton) findViewById(R.id.emailbut);
        introduction = (TextView) findViewById(R.id.introduction);
        introductionBut = (ImageButton) findViewById(R.id.introductionbut);
        nameR = (RelativeLayout) findViewById(R.id.nameR);
        idR = (RelativeLayout) findViewById(R.id.IDR);
        emailR = (RelativeLayout) findViewById(R.id.emailR);
        introductionR = (RelativeLayout) findViewById(R.id.IntoductionR);
        image.setImageResource(R.drawable.head);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(EditProfileActivity.this, PeopleActivity.class);
                intent.putExtra("Nickname", nickName.getText().toString());
                intent.putExtra("Introduction", introduction.getText().toString());
                intent.putExtra("headPath", headPath);
                setResult(Constant.RESULT_EDIT_CODE, intent);
                // TODO to peiyun, Modify Load data on a subthread, not on Main UI thread.
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        infoList.get(0).setEmail(email.getText().toString());
                        infoList.get(0).setNickName(nickName.getText().toString());
                        infoList.get(0).setWeShowID(weShowID.getText().toString());
                        infoList.get(0).setIntroduction(introduction.getText().toString());
                        infoList.get(0).setHeadPath(headPath);
                        infoList.get(0).save();
                        finish();
                    }
                }).start();

                break;
            case R.id.image:
                menuWindow = new SelectPhotoPopupWindow(EditProfileActivity.this, itemsOnClick);
                menuWindow.showAtLocation(EditProfileActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.nameR:
                getEditPopupWindow(nickName, 20, false, EditProfileActivity.this.findViewById(R.id.nameR));
                break;
            case R.id.namebut:
                getEditPopupWindow(nickName, 20, false, EditProfileActivity.this.findViewById(R.id.nameR));
                break;
            case R.id.IDR:
                Toast.makeText(EditProfileActivity.this, "unchangeable", Toast.LENGTH_SHORT).show();
//				getEditPopupWindow(weShowID,20,true,EditProfileFragment.this.findViewById(R.id.IDR));
                break;
            case R.id.IDbut:
                Toast.makeText(EditProfileActivity.this, "unchangeable", Toast.LENGTH_SHORT).show();
//				getEditPopupWindow(weShowID,20,true,EditProfileFragment.this.findViewById(R.id.IDR));
                break;
            case R.id.emailR:
                getEditPopupWindow(email, 20, true, EditProfileActivity.this.findViewById(R.id.emailR));
                break;
            case R.id.emailbut:
                getEditPopupWindow(email, 20, true, EditProfileActivity.this.findViewById(R.id.emailR));
                break;
            case R.id.IntoductionR:
                getEditPopupWindow(introduction, 50, true, EditProfileActivity.this.findViewById(R.id.IntoductionR));
                break;
            case R.id.introductionbut:
                getEditPopupWindow(introduction, 50, true, EditProfileActivity.this.findViewById(R.id.IntoductionR));
                break;

            default:
                break;
        }
    }

    private void getEditPopupWindow(TextView textView, Integer maxAmount, boolean isUp, View view) {
        EditPopupWindow editPopupWindow = new EditPopupWindow(textView, EditProfileActivity.this, maxAmount);
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
                   // File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
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
                        uriImage = FileProvider.getUriForFile(EditProfileActivity.this,
                                "com.libt.cameraalbumtest.fileprovider", outputImage);
                    } else {
                        uriImage = Uri.fromFile(outputImage);
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
                    startActivityForResult(intent, Constant.TAKE_PHOTO);
                    break;
                case R.id.btn_pick_photo:
                    if (ContextCompat.checkSelfPermission(EditProfileActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this,
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case Constant.TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    CircleHeadPhotoUtil.setCirclePhoto(headPath, image);}
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
        if (DocumentsContract.isDocumentUri(this, uri)) {
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
            Toast.makeText(this, "fail to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
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
                    Toast.makeText(this, "YOU DENIED THE PERMISSION", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public final static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileprovider";
    }
}