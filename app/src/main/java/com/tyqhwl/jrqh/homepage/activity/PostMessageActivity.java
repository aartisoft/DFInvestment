package com.tyqhwl.jrqh.homepage.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.avos.avoscloud.AVFile;
import com.bumptech.glide.Glide;
import com.tyqhwl.jrqh.Config;
import com.tyqhwl.jrqh.R;
import com.tyqhwl.jrqh.base.BaseActivity;
import com.tyqhwl.jrqh.base.BaseTime;
import com.tyqhwl.jrqh.seek.view.InversorEntry;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.net.URL;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import tech.com.commoncore.widget.photoPicker.MyPhotoPickerActivity;

/**
 * 发帖页面
 * wmy
 * 2019-05-27
 */
public class PostMessageActivity extends BaseActivity {
    @BindView(R.id.homepage_frag_sign_in)
    ImageView homepageFragSignIn;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.my_quiz_frag_quiz)
    TextView myQuizFragQuiz;
    @BindView(R.id.user_feed_back_act_edittext)
    EditText userFeedBackActEdittext;
    @BindView(R.id.user_feed_back_act_index)
    TextView userFeedBackActIndex;
    @BindView(R.id.user_feed_back_act_image)
    ImageView userFeedBackActImage;
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private AVFile avFile1;
    private String fileName;
    private URL url;

    @Override
    public int getXMLLayout() {
        return R.layout.post_message_activity;
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        userFeedBackActEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 200) {
                    userFeedBackActIndex.setText(s.length() + "");
                } else {
                    userFeedBackActIndex.setText(200 + "");
                }
            }
        });
    }

    @OnClick({R.id.homepage_frag_sign_in, R.id.back, R.id.my_quiz_frag_quiz,
            R.id.user_feed_back_act_edittext, R.id.user_feed_back_act_index, R.id.user_feed_back_act_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_frag_sign_in:
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.my_quiz_frag_quiz:
                //发表
                if (userFeedBackActEdittext.getText().toString().length() > 0 && fileName.length() > 0){
                    EventBus.getDefault().post(new InversorEntry(userFeedBackActEdittext.getText().toString(),userFeedBackActEdittext.getText().toString() ,
                            userFeedBackActEdittext.getText().toString() , 0 , BaseTime.getCurrentTime(),"10" , true , fileName));
                    Toast.makeText(this , "发表成功" , Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(this , "请添加你的图片和想法", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.user_feed_back_act_edittext:
                break;
            case R.id.user_feed_back_act_index:
                break;
            case R.id.user_feed_back_act_image:
                choicePhotoWrapper();
                break;
        }
    }


    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Config.PHOTO_PATH);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            Glide.with(this)
                    .load(MyPhotoPickerActivity.getSelectedPhotos(data).get(0))
                    .into(userFeedBackActImage);
//            fileName = FileUtils.splitFileName(MyPhotoPickerActivity.getSelectedPhotos(data).get(0));
            fileName = MyPhotoPickerActivity.getSelectedPhotos(data).get(0);
//            String str = "填写字符串的链接地址";
//            try {
//                url = new URL(fileName);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//            fileName = url.getPath();
        }
    }



}
