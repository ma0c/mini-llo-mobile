package com.contraslash.android.mini_llo.network;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ma0 on 3/16/18.
 */

public class LoginHttpManager extends AsyncTask<String,Void,String> {
    OkHttpClient client;
    OnLoginSuccess onLoginSuccess;
    OnLoginFailure onLoginFailure;

    String username;
    String password;
    String baseHost;

    public LoginHttpManager(OnLoginSuccess onLoginSuccess, OnLoginFailure onLoginFailure)
    {
        this.onLoginSuccess = onLoginSuccess;
        this.onLoginFailure = onLoginFailure;
        client = new OkHttpClient();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseHost() {
        return baseHost;
    }

    public void setBaseHost(String baseHost) {
        this.baseHost = baseHost;
    }

    @Override
    protected String doInBackground(String... strings) {
        RequestBody formBody = new FormBody.Builder()
                .add("username", this.username)
                .add("password", this.password)
                .build();

        Request request = new Request.Builder()
                .url(baseHost+"authentication/log-in/")
                .post(formBody)
                .build();


        try {
            Response response = client.newCall(request).execute();
            Log.i("Login", response.toString());
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        if(response!= null)
        {
            onLoginSuccess.onLoginsuccess(response);
        }
        else
        {
            onLoginFailure.onLoginFailure();
        }

    }
}

