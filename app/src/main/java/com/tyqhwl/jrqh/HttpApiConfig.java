package com.tyqhwl.jrqh;

import android.os.Environment;

public class HttpApiConfig {

    //app相关信息
    public static final String APP_ID = "rVgiXnST6yaLQ5DhsI34RYYC-gzGzoHsz";
    public static final String APP_KEY = "8X9FcJPTOkM8qy0MLnqXsCve";
    public static final String MASTER_KEY = "NzupTDWCoGoeUD0PbkJEWUzf";

    //登录(POST)
    public static final String LOGIN = "http://data.yingju8.com/api/user/public/login";
    //注册（POST）
    public static final String REGISTER = "http://data.yingju8.com/api/user/public/register";

    public static final String PHOTO_PATH = Environment.getExternalStorageDirectory() + "/" ;
}
