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

public class sendedGames extends AppCompatActivity {
    private String JSON_URL_getgame = url+"game/getgames/";
    SharedPreferences sharedpreferences;
    int userid;
    ListView listView;
    List<game> games;
    gameCustomAdapter adapter;
    int fid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sended_games);
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(sendedGames.this, Login.class);
            startActivity(gec);
        }
        else {
            listView=(ListView) findViewById(R.id.s_games);
            games=new ArrayList<>();
            Intent intent=getIntent();
            fid=intent.getIntExtra("friend",0);
            new getgames().execute();
        }
    }

    class getgames extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(JSON_URL_getgame+userid+"/"+fid);
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
            adapter = new gameCustomAdapter(sendedGames.this, games, getApplicationContext());
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
