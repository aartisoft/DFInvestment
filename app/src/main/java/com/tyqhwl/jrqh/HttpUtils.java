package com.tyqhwl.jrqh;

import android.accounts.NetworkErrorException;



import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;
import tech.com.commoncore.utils.NetworkUtils;

/**
 * Created by dell on 2019/4/16.
 */

public class HttpUtils {
    public static boolean isNotNetwork(Exception e) {
        if (e == null) return true;
        if (!NetworkUtils.isConnected(App.getInstance()) ||
                e instanceof NetworkErrorException ||
                e instanceof ConnectException ||
                e instanceof SocketException ||
                e instanceof HttpException ||
                e instanceof UnknownHostException ||
                e instanceof SocketTimeoutException || e instanceof TimeoutException
                ) {
            return true;
        } else {
            return false;
        }
    }
}
