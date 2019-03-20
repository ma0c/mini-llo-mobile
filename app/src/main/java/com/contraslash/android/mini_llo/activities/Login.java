package com.contraslash.android.mini_llo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.contraslash.android.mini_llo.R;
import com.contraslash.android.mini_llo.config.Conf;
import com.contraslash.android.mini_llo.databinding.ActivityLoginBinding;
import com.contraslash.android.mini_llo.network.LoginHttpManager;
import com.contraslash.android.mini_llo.network.OnLoginFailure;
import com.contraslash.android.mini_llo.network.OnLoginSuccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public class Login extends AppCompatActivity implements OnLoginSuccess, OnLoginFailure {

    ActivityLoginBinding activityLoginBinding;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_login
        );

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        activityLoginBinding.loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLoginRequest();

            }
        });
    }

    private void doLoginRequest()
    {
        LoginHttpManager loginHttpManager = new LoginHttpManager(
                this,
                this);
        final String CONFIGURED_HOST = String.format(
                Conf.HOST,
                Conf.DEFAULT_IP,
                Conf.DEFAULT_PORT
        ) + Conf.API_PREFIX;
        Log.i("API Login", CONFIGURED_HOST);
        loginHttpManager.setBaseHost(CONFIGURED_HOST);
        loginHttpManager.setUsername(activityLoginBinding.loginUsername.getText().toString());
        loginHttpManager.setPassword(activityLoginBinding.loginPassword.getText().toString());
//        loginHttpManager.setUsername("ma0");
//        loginHttpManager.setPassword("Sumao58001994@");
        loginHttpManager.execute();
    }

    private void goToBoards()
    {
        Intent openInventory = new Intent(this, Boards.class);
        startActivity(openInventory);
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(this, getResources().getString(R.string.login_failure_message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginsuccess(String responseText) {
        try
        {
            JSONObject jsonObject = new JSONObject(responseText);
            String token = jsonObject.getString(Conf.TOKEN_PREF_NAME);
            preferences.edit()
                    .putString(Conf.TOKEN_PREF_NAME, token)
                    .apply();
            goToBoards();
        }catch (JSONException jse)
        {
            jse.printStackTrace();
            onLoginFailure();
        }
    }
}
