package com.contraslash.android.mini_llo.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.contraslash.android.mini_llo.config.Conf;
import com.contraslash.android.mini_llo.models.Board;
import com.contraslash.android.mini_llo.models.Idea;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ma0 on 2/26/18.
 * API configuration client
 */

public class ApiClient {
    private static final String TAG = "Api Client";
    private static ApiClient apiClient;
    private Context context;
    private static Retrofit retrofit;



    public static ApiClient getInstance(Context mContext) {
        if (apiClient == null)
            apiClient = new ApiClient(mContext);
        return apiClient;
    }

    private ApiClient(Context context) {
        this.context = context;
        configureClient(context);
    }

    public static void configureClient(Context context)
    {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final String CONFIGURED_HOST = String.format(
                Conf.HOST,
                Conf.DEFAULT_IP,
                Conf.DEFAULT_PORT
        ) + Conf.API_PREFIX;
        Log.i("API Client", CONFIGURED_HOST);

        OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder();

        oktHttpClient.addInterceptor(new Interceptor() {
              @Override
              public Response intercept(Interceptor.Chain chain) throws IOException {
                  Request original = chain.request();
                    Log.i(TAG, "Token "+preferences.getString(Conf.TOKEN_PREF_NAME, ""));
                  Request request = original.newBuilder()
                          .header("Authorization",   "Token "+preferences.getString(Conf.TOKEN_PREF_NAME, ""))
                          .method(original.method(), original.body())
                          .build();

                  return chain.proceed(request);
              }
          }
        );

        retrofit = new Retrofit.Builder()
                .baseUrl(CONFIGURED_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(oktHttpClient.build())
                .build();

    }

    public Call<List<Board>> getBoards() {
        ApiService service = retrofit.create(ApiService.class);
        return service.getBoards();
    }

    public Call<List<Idea>> getIdeas(int id) {
        ApiService service = retrofit.create(ApiService.class);
        return service.getIdeas(id);
    }

    public Call<Idea> addIdea(Idea idea) {
        ApiService service = retrofit.create(ApiService.class);
        return service.addIdea(idea);
    }



}
