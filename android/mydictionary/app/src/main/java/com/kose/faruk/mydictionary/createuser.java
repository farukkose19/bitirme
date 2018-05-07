package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kose.faruk.mydictionary.models.kullanici;
import com.kose.faruk.mydictionary.models.word;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;

/**
 * Created by Lenovo on 23.10.2017.
 */

public class createuser extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText name;
    EditText surname;
    int id;

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private String JSON_URL = url+"kullanici/add";

    kullanici kullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);

        username=(EditText)findViewById(R.id.editText7);
        password=(EditText)findViewById(R.id.editText8);
        name=(EditText)findViewById(R.id.editText10);
        surname=(EditText)findViewById(R.id.editText11);


    }
    class kullaniciolustur extends AsyncTask<String,Void,String> {
        int sonuc=0;

        @Override
        protected String doInBackground(String... strings) {
            try{
                String username1=username.getText().toString();
                String password1=password.getText().toString();
                String name1=name.getText().toString();
                String surname1=surname.getText().toString();

                JsonObject json = new JsonObject();
                json.addProperty("name",name1);
                json.addProperty("surname",surname1);
                json.addProperty("userName",username1);
                json.addProperty("password",password1);

                String jsonString = json.toString();

                OkHttpClient client=new OkHttpClient();

                RequestBody body =RequestBody.create(JSON, jsonString);

                Request request=new Request.Builder()
                        .url(JSON_URL)
                        .post(body)
                        .build();
                Response response=client.newCall(request).execute();
                String results=response.body().string();

                JSONObject object=new JSONObject(results);

                Gson gson = new Gson();
                kullanici=gson.fromJson(String.valueOf(object),kullanici.class);

                sonuc=response.code();

            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(sonuc==200){
                Toast.makeText(getApplicationContext(),"bassarili",Toast.LENGTH_LONG).show();
                SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.putInt("id",kullanici.id);
                editor.putString("name",kullanici.userName);
                editor.commit();
                Intent gec = new Intent(createuser.this, MainActivity.class);
                startActivity(gec);
            }
            else
                Toast.makeText(getApplicationContext(),sonuc,Toast.LENGTH_LONG).show();
        }
    }





    public void olustur(View view){
        new kullaniciolustur().execute();
    }

}
