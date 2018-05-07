package com.kose.faruk.mydictionary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.kullanici;
import com.kose.faruk.mydictionary.models.word;
import com.kose.faruk.mydictionary.models.Package;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;


public class package_friends_add extends AppCompatActivity {

    private String url1=url+"kullanici/getfriend/";
    private String url_addPackage=url+"package/add/";
    SharedPreferences sharedpreferences;
    int userid;
    ListView listView;
    adapter_package_add_friend adapter;
    List<kullanici> friends;
    kullanici kullanici;
    List<word> words;
    Dialog dialog2;
    EditText packageName;
    Button kaydet;
    Button iptal;
    int selectedFriendId;
    Package newpackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_friends_add);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(package_friends_add.this, Login.class);
            startActivity(gec);
        }
        else {
            words=new ArrayList<>();
            Intent mIntent = getIntent();
            String json;
            json=mIntent.getStringExtra("addword");
            Gson gson = new Gson();
            JSONArray object= null;
            try {
                object = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            words = Arrays.asList(gson.fromJson(String.valueOf(object), word[].class));
            listView = (ListView) findViewById(R.id.list_package_add_friend);
            friends=new ArrayList<>();
            dialog2=new Dialog(package_friends_add.this);
            dialog2.setTitle("pakete isim ver");
            dialog2.setContentView(R.layout.customdialog3);
            kaydet=(Button)dialog2.findViewById(R.id.addpackagebutton);
            iptal=(Button)dialog2.findViewById(R.id.iptalpackage);
            packageName=(EditText)dialog2.findViewById(R.id.addpackagename);
            new getfriends().execute();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedFriendId=friends.get(i).id;
                    dialog2.show();
                }
            });
        }
    }

    class getfriends extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {
            try{
                String results=run(url1+userid);
                JSONObject object=new JSONObject(results);
                Gson gson = new Gson();
                kullanici=gson.fromJson(String.valueOf(object),kullanici.class);
                friends = kullanici.friends;
                System.console().printf("s");
            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
                sonuc=("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
                adapter = new adapter_package_add_friend(package_friends_add.this, friends, getApplicationContext());
                listView.setAdapter(adapter);
        }
    }

    class addPackage extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {
            try{
                String idler=""+words.get(0).id;
                for(int i=1;i<(words.size());i++){
                    idler=idler+","+words.get(i).id;
                }
                String name=packageName.getText().toString();
                String results=run(url_addPackage +
                        name + "/"+
                        userid + "/"+
                        selectedFriendId + "/"+
                        idler);
                JSONObject object=new JSONObject(results);
                Gson gson = new Gson();
                newpackage=gson.fromJson(String.valueOf(object),Package.class);
            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
                sonuc=("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"paketiniz oluşturuldu",Toast.LENGTH_LONG).show();
            Intent gec = new Intent(package_friends_add.this, MainActivity.class);
            startActivity(gec);
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

    public void packageiptal(View view){
        Intent gec = new Intent(this, MainActivity.class);
        startActivity(gec);
        dialog2.dismiss();
    }

    public void packagekaydet(View view){
        new addPackage().execute();
        dialog2.dismiss();
    }
}
