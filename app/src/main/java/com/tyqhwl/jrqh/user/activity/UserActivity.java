package com.tyqhwl.jrqh.user.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.tyqhwl.jrqh.ApplicationStatic;
import com.tyqhwl.jrqh.HttpApiConfig;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.EventBusTag;
import com.tyqhwl.jrqh.base.IntentSkip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import tech.com.commoncore.manager.GlideManager;
import tech.com.commoncore.utils.FileUtils;
import tech.com.commoncore.utils.ToastUtil;
import tech.com.commoncore.widget.photoPicker.MyPhotoPickerActivity;

/**
 * 用户界面
 * wmy
 * 2019-04-22
 */
public class UserActivity extends BaseActivity {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.user_act_login_myuser_image_text)
    TextView userActLoginMyuserImageText;
    @BindView(R.id.user_act_login_myuser_image)
    ImageView userActLoginMyuserImage;
    @BindView(R.id.user_act_login_myuser_image_layout)
    LinearLayout userActLoginMyuserImageLayout;
    @BindView(R.id.user_act_login_myusername_text)
    TextView userActLoginMyusernameText;
    @BindView(R.id.user_act_login_myusername_edittext)
    TextView userActLoginMyusernameEdittext;
    @BindView(R.id.user_act_login_myusername_layout)
    LinearLayout userActLoginMyusernameLayout;
    @BindView(R.id.user_act_unlogin_button)
    TextView userActUnloginButton;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int PRC_PHOTO_PICKER = 1;
    @Override
    public int getXMLLayout() {
        return R.layout.user_activity;
    }

    @Override
    public void initView() {

    }


    @Override
    public boolean isEventOrBindInit() {
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventThreadMain(Object o){
        if (o.equals(EventBusTag.UPDATE_USER_MESSAGE)){
            userActLoginMyusernameEdittext.setText(ApplicationStatic.getUserMessage().getUsername() + "");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        init();
    }


    private void init(){
        userActLoginMyusernameEdittext.setText(ApplicationStatic.getUserMessage().getUsername() + "");
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser.get("icon") != null) {
            GlideManager.loadCircleImg(avUser.get("icon"), userActLoginMyuserImage);
        } else {
            GlideManager.loadCircleImg(R.drawable.user_image, userActLoginMyuserImage);
        }
    }

    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.user_act_login_myuser_image_text,
            R.id.user_act_login_myuser_image, R.id.user_act_login_myuser_image_layout,
            R.id.user_act_login_myusername_text, R.id.user_act_login_myusername_edittext,
            R.id.user_act_login_myusername_layout, R.id.user_act_unlogin_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.user_act_login_myuser_image_text:
                //获取用户头像
                choicePhotoWrapper();
                break;
            case R.id.user_act_login_myuser_image:
                //获取用户头像
                choicePhotoWrapper();
                break;
            case R.id.user_act_login_myuser_image_layout:
                //获取用户头像
                choicePhotoWrapper();
                break;
            case R.id.user_act_login_myusername_text:
                //修改用户昵称
                IntentSkip.startIntent(this , new ModificationUserNameActivity() , null);
                break;
            case R.id.user_act_login_myusername_edittext:
                //修改用户昵称
                IntentSkip.startIntent(this , new ModificationUserNameActivity() , null);
                break;
            case R.id.user_act_login_myusername_layout:
                //修改用户昵称
                IntentSkip.startIntent(this , new ModificationUserNameActivity() , null);
                break;
            case R.id.user_act_unlogin_button:
                //退出登录
                logOut();
                break;
        }
    }



    @SuppressLint("ApplySharedPref")
    public void logOut() {
        ApplicationStatic.setUserMessage(new AVUser());
        ApplicationStatic.setUserLoginState(false);
        EventBus.getDefault().post(EventBusTag.LOGIN_QUIT);
        SharedPreferences sharedPreferences = this.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "");
        editor.putString("password", "");
        editor.commit();
        Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
        finish();
    }


    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(HttpApiConfig.PHOTO_PATH + getApplicationContext().getPackageName() + "/photos/");


//            MyPhotoPickerActivity myPhotoPickerActivity = new MyPhotoPickerActivity();
            Intent photoPickerIntent = new MyPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
//                    .selectedPhotos(mPhotoLayout.getData()) // 当前已选中的图片路径集合
                    .maxChooseCount(1) // 图片选择张数的最大值
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tip_photo_permissions_request), PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
//            mPhotoLayout.setData(MyPhotoPickerActivity.getSelectedPhotos(data));
            upLoadPic(MyPhotoPickerActivity.getSelectedPhotos(data).get(0)); //保存图片

        }
    }
    //上传图片
    private void upLoadPic(final String picUrl) {
        try {
            String fileName = FileUtils.splitFileName(picUrl);
            final AVFile avFile = AVFile.withAbsoluteLocalPath(fileName, picUrl);
            avFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
//                        userLogo.setImageURI(Uri.parse(picUrl));
//                        ToastUtil.show("图片上传成功");
                        AVUser user = AVUser.getCurrentUser();
                        user.put("icon", picUrl);
                        user.saveInBackground();
                        GlideManager.loadCircleImg(picUrl, userActLoginMyuserImage);
                        EventBus.getDefault().post(EventBusTag.UPDATE_USER_LOGO);
                    } else {
                        ToastUtil.show("图片上传失败,请重新发布");
                    }
                }
            }, new ProgressCallback() {
                @Override
                public void done(Integer integer) {

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }




}
