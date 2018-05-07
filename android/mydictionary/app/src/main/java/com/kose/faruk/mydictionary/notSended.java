package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.game;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;

/**
 * Created by Muhammed Faruk KOSE on 1.05.2018.
 */

public class notSended extends AppCompatActivity {

    private String JSON_URL_getgame = url+"game/isnull/";
    SharedPreferences sharedpreferences;
    int userid;
    ListView listView;
    List<game> games;
    gameCustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_sended);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(notSended.this, Login.class);
            startActivity(gec);
        }
        else {
            games=new ArrayList<>();
            listView=(ListView) findViewById(R.id.ns_games);
            new getgames().execute();
        }
    }

    class getgames extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(JSON_URL_getgame+userid);
                JSONArray object = new JSONArray(results);
                Gson gson = new Gson();
                games = Arrays.asList(gson.fromJson(String.valueOf(object), game[].class));
            } catch (Exception e) {
                Log.e("yyyyyyyy",e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new gameCustomAdapter(notSended.this, games, getApplicationContext());
            listView.setAdapter(adapter);
        }
    }

    public String run (String url) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .build();
        Response response=client.newCall(request).execute();
        String results=response.body().string();
        return results;
    }
}
