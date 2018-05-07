package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.game;
import com.kose.faruk.mydictionary.models.kullanici;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static com.kose.faruk.mydictionary.settings.url.url;

/**
 * Created by Muhammed Faruk KOSE on 1.05.2018.
 */

public class gameAdd extends AppCompatActivity {

    private String url1=url+"kullanici/getfriend/";
    private String url2=url+"game/add/";
    private String url3=url+"game/addnf/";
    SharedPreferences sharedpreferences;
    int userid;
    Spinner spinner;
    adapter_package_add_friend adapter;
    List<kullanici> friends;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    kullanici friend;
    int dogrucevap=1001;
    EditText soru;
    EditText cevap1;
    EditText cevap2;
    EditText cevap3;
    EditText cevap4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_add);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(gameAdd.this, Login.class);
            startActivity(gec);
        }
        else {
            spinner = (Spinner) findViewById(R.id.spinner);
            new getfriends().execute();
            soru=(EditText) findViewById(R.id.editText13);
            cevap1=(EditText) findViewById(R.id.editText14);
            cevap2=(EditText) findViewById(R.id.editText15);
            cevap3=(EditText) findViewById(R.id.editText16);
            cevap4=(EditText) findViewById(R.id.editText12);

            radioButton1=(RadioButton) findViewById(R.id.radioButton);
            radioButton2=(RadioButton) findViewById(R.id.radioButton2);
            radioButton3=(RadioButton) findViewById(R.id.radioButton3);
            radioButton4=(RadioButton) findViewById(R.id.radioButton4);

            radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        dogrucevap=0;
                        radioButton1.setChecked(true);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                        radioButton4.setChecked(false);
                    }
                }
            });

            radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        dogrucevap=1;
                        radioButton2.setChecked(true);
                        radioButton1.setChecked(false);
                        radioButton3.setChecked(false);
                        radioButton4.setChecked(false);
                    }
                }
            });
            radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        dogrucevap=2;
                        radioButton3.setChecked(true);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton4.setChecked(false);
                    }
                }
            });
            radioButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        dogrucevap=3;
                        radioButton4.setChecked(true);
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(false);
                        radioButton3.setChecked(false);
                    }
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    friend=friends.get(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

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
            kullanici kullanici=new kullanici();
            kullanici.setName("lütfen seçim yapın");
            kullanici.setId(0);
            friends.add(0,kullanici);
            adapter = new adapter_package_add_friend(gameAdd.this, friends, getApplicationContext());
            spinner.setAdapter(adapter);
        }
    }

    class withFriend extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(url2+
                        soru.getText().toString() +"/"+
                        cevap1.getText().toString()+"/"+
                        cevap2.getText().toString()+"/"+
                        cevap3.getText().toString()+"/"+
                        cevap4.getText().toString()+"/"+
                        dogrucevap+"/"+
                        userid+"/"+
                        friend.id
                );
                JSONObject object=new JSONObject(results);
                Gson gson = new Gson();
                game game=gson.fromJson(String.valueOf(object),game.class);;
            } catch (Exception e) {
                Log.e("yyyyyyyy",e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"eklendi",Toast.LENGTH_SHORT).show();
        }
    }

    class notWithFriend extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(url3+
                        soru.getText().toString() +"/"+
                        cevap1.getText().toString()+"/"+
                        cevap2.getText().toString()+"/"+
                        cevap3.getText().toString()+"/"+
                        cevap4.getText().toString()+"/"+
                        dogrucevap+"/"+
                        userid
                );
                JSONObject object=new JSONObject(results);
                Gson gson = new Gson();
                game game=gson.fromJson(String.valueOf(object),game.class);
            } catch (Exception e) {
                Log.e("yyyyyyyy",e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"eklendi nf",Toast.LENGTH_SHORT).show();
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

    public void ekle(View view){
        if(dogrucevap==1001){
            Toast.makeText(getApplicationContext(),"doğru cevabı seçin!!",Toast.LENGTH_SHORT).show();
        }
        else {
            if (friend.id == 0) {
                new notWithFriend().execute();
            } else {
                new withFriend().execute();
            }
        }
    }
}
