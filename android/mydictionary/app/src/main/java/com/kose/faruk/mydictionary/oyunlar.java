package com.kose.faruk.mydictionary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.kullanici;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static com.kose.faruk.mydictionary.settings.url.url;

/**
 * Created by Muhammed Faruk KOSE on 29.04.2018.
 */

public class oyunlar extends AppCompatActivity {

    private String url1=url+"kullanici/getfriend/";
    SharedPreferences sharedpreferences;
    int userid;
    Dialog dialog;
    ListView listView;
    adapter_package_add_friend adapter;
    List<kullanici> friends;
    boolean secim=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oyunlar);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(oyunlar.this, Login.class);
            startActivity(gec);
        }
        else {

            dialog = new Dialog(this);
            dialog.setTitle("arkadaş seç!");
            dialog.setContentView(R.layout.custom_dialog_game);
            listView = (ListView) dialog.findViewById(R.id.game_custom_lv);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(secim==true){
                        Intent gec = new Intent(oyunlar.this, sendedGames.class);
                        gec.putExtra("friend",friends.get(i).id);
                        startActivity(gec);
                    }
                    else if(secim==false)
                    {
                        Intent gec = new Intent(oyunlar.this, Incoming.class);
                        gec.putExtra("friend",friends.get(i).id);
                        startActivity(gec);
                    }
                    dialog.dismiss();
                }
            });
        }

    }

    class getfriends extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(url1+userid);
                JSONObject object=new JSONObject(results);
                Gson gson = new Gson();
                kullanici kullanici=gson.fromJson(String.valueOf(object),kullanici.class);
                friends = kullanici.friends;
            } catch (Exception e) {
                Log.e("yyyyyyyy",e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new adapter_package_add_friend(oyunlar.this, friends, getApplicationContext());
            listView.setAdapter(adapter);
            dialog.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.ekle) {
            Intent gec = new Intent(oyunlar.this, gameAdd.class);
            startActivity(gec);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sended(View view){
        secim=true;
        new getfriends().execute();
    }

    public void incoming(View view){
        secim=false;
        new getfriends().execute();
    }

    public void notSended(View view){
        Intent gec = new Intent(oyunlar.this, notSended.class);
        startActivity(gec);
    }
}
