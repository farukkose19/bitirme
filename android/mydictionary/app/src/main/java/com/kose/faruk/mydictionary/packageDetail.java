package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.Package;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static com.kose.faruk.mydictionary.settings.url.url;

/**
 * Created by Muhammed Faruk KOSE on 31.03.2018.
 */

public class packageDetail extends AppCompatActivity {

    private String JSON_URL_getpackage = url+"package/getpackageid/";
    private String JSON_URL_sil = url+"package/delete/";
    SharedPreferences sharedpreferences;
    int userid;
    int id;
    Package aPackage;
    TextView textView;
    ListView listView;
    fragment_adapter adapter;
    DialogInterface dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_detail);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(packageDetail.this, Login.class);
            startActivity(gec);
        }
        else {

            Intent mIntent = getIntent();
            id=mIntent.getIntExtra("packageid",0);
            textView=(TextView) findViewById(R.id.textView9);
            listView=(ListView)findViewById(R.id.package_list);
            new getPackage().execute();
        }
    }

    class getPackage extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(JSON_URL_getpackage+id);
                JSONObject object = new JSONObject(results);
                Gson gson = new Gson();
                aPackage=gson.fromJson(String.valueOf(object),Package.class);

            } catch (Exception e) {
                Log.e("yyyyyyyy",e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(aPackage.packageName);
            adapter=new fragment_adapter(aPackage.words,getApplicationContext());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.sil) {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("SİL?")
                    .setMessage("Silmek istediğinizden emin misiniz?")
                    .setIcon(android.R.drawable.ic_menu_delete);

            builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog=dialogInterface;
                    new paketsil().execute();
                }
            });
            builder.setNegativeButton("VAZGEÇ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog alertDialog=builder.create();
            alertDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class paketsil extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {

            try{

                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(JSON_URL_sil+aPackage.id)
                        .build();
                Response response=client.newCall(request).execute();
                String results=response.body().string();
                //JSONArray object = new JSONArray(results);
                System.out.println("mnmnmnmnm");

            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
                sonuc=("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(),"paket silindi",Toast.LENGTH_SHORT).show();
            Intent gec = new Intent(packageDetail.this, my_friends_package.class);
            gec.putExtra("friendID", aPackage.friends.get(0).id);
            startActivity(gec);
        }
    }
}
