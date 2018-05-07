package com.kose.faruk.mydictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.Package;
import org.json.JSONArray;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static com.kose.faruk.mydictionary.settings.url.url;

/**
 * Created by Muhammed Faruk KOSE on 29.04.2018.
 */

public class all_package extends AppCompatActivity {
    private String getpackage = url+"package/userpackage/";
    List<Package> packages;
    LinearLayout ll;
    LinearLayout fragContainer;
    SharedPreferences sharedpreferences;
    int userid;
    fragment_adapter adapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_package);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(all_package.this, Login.class);
            startActivity(gec);
        }

        fragContainer = (LinearLayout) findViewById(R.id.llFragmentContainer);

        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setId(12345);

        new getpackage2().execute();

        //getFragmentManager().beginTransaction().add(ll.getId(), fragmentPackage.newInstance("I am frag 1"), "someTag1").commit();
        //getFragmentManager().beginTransaction().add(ll.getId(), fragmentPackage.newInstance("I am frag 2"), "someTag2").commit();

        //fragContainer.addView(ll);

    }

    class getpackage2 extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {

            try{
                String results=run(getpackage+userid);
                JSONArray object=new JSONArray(results);
                Gson gson = new Gson();
                packages = Arrays.asList(gson.fromJson(String.valueOf(object), Package[].class));

            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
                sonuc=("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(packages.size()>0){
                SharedPreferences sharedpreferences =  getSharedPreferences("veri", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                Gson gson = new Gson();
                for(int i=0;i<packages.size();i++){
                    String json = gson.toJson(packages.get(i));
                    editor.putString(""+i, json);
                    getFragmentManager().beginTransaction().add(ll.getId(), fragmentPackage2.newInstance(i),
                            "someTag"+i).commit();
                }

                editor.commit();
            }
            fragContainer.addView(ll);
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