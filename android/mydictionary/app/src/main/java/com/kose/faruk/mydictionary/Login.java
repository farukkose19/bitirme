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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;

/**
 * Created by Lenovo on 18.10.2017.
 */

public class Login extends AppCompatActivity {

    private String JSON_URL = url+"kullanici/login/";
    EditText username;
    EditText password;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

    }

    class loginislemi extends AsyncTask<String,Void,String> {
        int sonuc=0;

        @Override
        protected String doInBackground(String... strings) {

            try{
                String username1=username.getText().toString();
                String password1=password.getText().toString();


                Response response=run(JSON_URL+username1+"/"+password1);
                String results=response.body().string();
                JSONObject object=new JSONObject(results);
                sonuc=response.code();
                id=object.getInt("id");

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
                editor.putInt("id",id);
                editor.putString("name",username.getText().toString());
                editor.commit();
                Intent gec = new Intent(Login.this, MainActivity.class);
                startActivity(gec);
            }
            else
                Toast.makeText(getApplicationContext(),"giriş başarısız...",Toast.LENGTH_LONG).show();
        }
    }

    public Response run (String url) throws IOException {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .build();
        Response response=client.newCall(request).execute();
        return response;
    }

    public void giris(View view){
        new loginislemi().execute();
    }

    public void createuserpage(View view){
        Intent gec = new Intent(Login.this, createuser.class);
        startActivity(gec);
    }

}
