package com.contraslash.android.mini_llo.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.contraslash.android.mini_llo.R;
import com.contraslash.android.mini_llo.adapters.BoardArrayAdapter;
import com.contraslash.android.mini_llo.adapters.IdeaArrayAdapter;
import com.contraslash.android.mini_llo.config.Conf;
import com.contraslash.android.mini_llo.databinding.ActivityBoardDetailBinding;
import com.contraslash.android.mini_llo.models.Board;
import com.contraslash.android.mini_llo.models.Idea;
import com.contraslash.android.mini_llo.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetail extends AppCompatActivity {

    private static final String TAG = "BoardDetail";

    ActivityBoardDetailBinding activityBoardDetailBinding;
    int board_id;
    String board_name;

    ApiClient apiClient;

    ArrayList<Idea> elements;
    IdeaArrayAdapter ideaArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBoardDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail);

        Bundle b = getIntent().getExtras();

        if (b!=null)
        {
            board_id = b.getInt(Conf.BOARD_ID, 0);
            board_name = b.getString(Conf.BOARD_NAME, "");
        }


        activityBoardDetailBinding.ideaTitle.setText(board_name);

        elements = new ArrayList<>();
        ideaArrayAdapter = new IdeaArrayAdapter(this, R.layout.element_idea, elements);

        activityBoardDetailBinding.ideaIdeas.setAdapter(ideaArrayAdapter);

        apiClient = ApiClient.getInstance(this);
        Call<List<Idea>> getIdeas = apiClient.getIdeas(board_id);

        getIdeas.enqueue(new Callback<List<Idea>>() {
            @Override
            public void onResponse(Call<List<Idea>> call, @Nullable Response<List<Idea>> response) {
                if (response!=null && response.body()!=null)
                {
                    Log.i(TAG, response.toString());
                    elements.clear();
                    List<Idea> body = response.body();
                    for (Idea b: body)
                    {
                        elements.add(b);
                        Log.i(TAG, b.getText());
                    }
                    ideaArrayAdapter.notifyDataSetChanged();
                }
                else
                {
                    Log.i(TAG, "Empty response");
                }
            }

            @Override
            public void onFailure(Call<List<Idea>> call, Throwable t) {
                t.printStackTrace();
            }

        });

        activityBoardDetailBinding.ideaSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIdea();
            }
        });

    }

    private void sendIdea()
    {
        Idea idea = new Idea();
        idea.setBoard(board_id);
        idea.setText(activityBoardDetailBinding.ideaNewIdea.getText().toString());

        Call<Idea> addIdea = apiClient.addIdea(idea);

        addIdea.enqueue(new Callback<Idea>() {
            @Override
            public void onResponse(Call<Idea> call, Response<Idea> response) {
                if (response!=null && response.body()!=null)
                {
                    Idea body = response.body();
                    elements.add(body);
                    Log.i(TAG, body.getText());
                    ideaArrayAdapter.notifyDataSetChanged();
                    activityBoardDetailBinding.ideaNewIdea.setText("");
                }
                else
                {
                    Log.i(TAG, "Empty response");
                }
            }

            @Override
            public void onFailure(Call<Idea> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
