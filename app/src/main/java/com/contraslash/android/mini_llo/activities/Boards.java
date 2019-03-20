package com.contraslash.android.mini_llo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.contraslash.android.mini_llo.R;
import com.contraslash.android.mini_llo.config.Conf;
import com.contraslash.android.mini_llo.databinding.ActivityBoardsBinding;
import com.contraslash.android.mini_llo.models.Board;
import com.contraslash.android.mini_llo.network.ApiClient;
import com.contraslash.android.mini_llo.adapters.BoardArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Boards extends AppCompatActivity {
    private static final String TAG = "Boards";
    ActivityBoardsBinding activityBoardsBinding;

    SharedPreferences preferences;

    ApiClient apiClient;

    ArrayList<Board> elements;
    BoardArrayAdapter boardArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoardsBinding = DataBindingUtil.setContentView(this, R.layout.activity_boards);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getString(Conf.TOKEN_PREF_NAME, "").isEmpty())
        {
            openLogin();
        }

        apiClient = ApiClient.getInstance(this);
        Call<List<Board>> getBoards = apiClient.getBoards();

        elements = new ArrayList<>();
        boardArrayAdapter = new BoardArrayAdapter(this, R.layout.element_board, elements);

        activityBoardsBinding.boardsBoards.setAdapter(boardArrayAdapter);

        getBoards.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, @Nullable Response<List<Board>> response) {
                if (response!=null && response.body()!=null)
                {
                    Log.i(TAG, response.toString());
                    elements.clear();
                    List<Board> body = response.body();
                    for (Board b: body)
                    {
                        elements.add(b);
                        Log.i(TAG, b.getName());
                    }
                    boardArrayAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.i(TAG, "Empty response");
                }
            }

            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    private void openLogin()
    {
        Intent openLogin = new Intent(this, Login.class);
        openLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(openLogin);
    }
}
