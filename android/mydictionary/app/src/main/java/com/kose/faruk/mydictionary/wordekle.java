package com.kose.faruk.mydictionary;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.kullanici;
import com.kose.faruk.mydictionary.models.mean;
import com.kose.faruk.mydictionary.models.word;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;


/**
 * Created by Lenovo on 21.10.2017.
 */

public class wordekle extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    private String JSON_URL = url+"word/add/";
    private String JSON_URL_mean = url+"mean/add/";
    EditText wordname;
    EditText meanname;
    int userid;
    int wordid;
    int meanid;
    String meanName;

    Dialog dialog;
    EditText customname;
    Button kaydet;
    Button vazgec;
    word word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordekle);
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        wordname=(EditText)findViewById(R.id.editText3);
        meanname=(EditText)findViewById(R.id.editText4);

        dialog=new Dialog(this);
        dialog.setTitle("Daha Fazla Anlam Ekle");
        dialog.setContentView(R.layout.customdiyalog);
        kaydet=(Button)dialog.findViewById(R.id.button5);
        vazgec=(Button)dialog.findViewById(R.id.button6);
        customname=(EditText)dialog.findViewById(R.id.editText6);


    }

    class addword extends AsyncTask<String,Void,String> {
        int sonuc=0;

        @Override
        protected String doInBackground(String... strings) {

            try{
                    String userid1=String.valueOf(userid);
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url(JSON_URL+wordname.getText().toString()+"/"+userid1)
                            .build();
                    Response response=client.newCall(request).execute();
                    String results=response.body().string();

                    JSONObject object=new JSONObject(results);
                    Gson gson = new Gson();
                    word=gson.fromJson(String.valueOf(object),word.class);
                    sonuc=response.code();
                    wordid=object.getInt("id");

            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //adapter=new meancustomadapter(worddetail.this,meanList,getApplicationContext());
            //listView.setAdapter(adapter);
            meanName=meanname.getText().toString();
            new addmean().execute();
        }
    }

    class addmean extends AsyncTask<String,Void,String> {
        int sonuc=0;

        @Override
        protected String doInBackground(String... strings) {

            try{
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(JSON_URL_mean+meanName+"/"+word.id)
                        .build();
                Response response=client.newCall(request).execute();
                String results=response.body().string();

                JSONObject object=new JSONObject(results);
                sonuc=response.code();
                meanid=object.getInt("id");
                System.out.println("aaaaaaaaafffffff");

            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //adapter=new meancustomadapter(worddetail.this,meanList,getApplicationContext());
            //listView.setAdapter(adapter);
            wordname.setText("");
            meanname.setText("");
            if(dialog.isShowing()){
                customname.setText("");
            }
            else {
                dialog.show();
            }
        }
    }

    public void eklebuton(View view){
        new addword().execute();
    }
    public void kaydet(View view){

        meanName=customname.getText().toString();
        new addmean().execute();
    }
    public void vazgec(View view){
        dialog.dismiss();
    }
}