package com.contraslash.android.mini_llo.network;

import com.contraslash.android.mini_llo.models.Board;
import com.contraslash.android.mini_llo.models.Idea;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("core/boards/")
    Call<List<Board>> getBoards();

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("core/board/{boardId}/ideas")
    Call<List<Idea>> getIdeas(@Path("boardId") int batch_id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("core/create-idea/")
    Call<Idea> addIdea(@Body Idea idea);


}
