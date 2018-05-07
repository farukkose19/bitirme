package com.kose.faruk.mydictionary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.mean;
import com.kose.faruk.mydictionary.models.word;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;

public class worddetail extends AppCompatActivity {

    private String url1=url+"word/";
    private String urlmean=url+"mean/";
    private String JSON_URL_sil = url1+"delete/";
    private String JSON_URL_duzenle = url1+"update/";
    private String JSON_URL_meanekle = urlmean+"add/";
    private String JSON_URL_meansil = urlmean+"delete/";
    private String JSON_URL_meanduzenle = urlmean+"update/";
    private String JSON_URL_wgetid = url1+"getid/";
    TextView wordname;
    List<Integer> meanid;
    List<mean> meanList;
    ListView listView;
    word words;
    meancustomadapter adapter;
    DialogInterface dialog;
    int id;
    EditText kelimeduzenle;
    String kelimeadi;
    EditText wordmean;
    mean newmean;
    Dialog dialog2;
    Button duzenle;
    Button sil;
    EditText mean;
    int silmeanid;
    int meansira;
    mean silinecekmean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worddetail);

        Intent mIntent = getIntent();
        id=mIntent.getIntExtra("word",0);
        new getword().execute();
        wordname=(TextView)findViewById(R.id.textView);

        meanid=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listview2);

        meanList=new ArrayList<>();

        kelimeduzenle=(EditText)findViewById(R.id.editText);
        wordmean=(EditText)findViewById(R.id.editText2);
        dialog2=new Dialog(worddetail.this);
        dialog2.setTitle("Anlamı düzenle");
        dialog2.setContentView(R.layout.customdialog2);
        duzenle=(Button)dialog2.findViewById(R.id.bduzenle);
        sil=(Button)dialog2.findViewById(R.id.bsil);
        mean=(EditText)dialog2.findViewById(R.id.emeanduzenle);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mean s= (mean) listView.getItemAtPosition(i);
                silinecekmean=s;
                silmeanid=s.id;
                meansira=i;
                mean.setText(s.meanName);
                dialog2.show();
            }
        });
    }



    class getword extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {

                String results = run(JSON_URL_wgetid+id);
                JSONObject object = new JSONObject(results);
                Gson gson = new Gson();

                words=gson.fromJson(String.valueOf(object),word.class);
            } catch (Exception e) {
                Log.e("yyyyyyyy",e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            wordname.setText(words.name);
            meanList=words.means;
            means();
        }
    }

    public void means(){
        adapter=new meancustomadapter(worddetail.this,meanList,getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(dialog2.isShowing())
            dialog2.dismiss();
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
                    new wordsil().execute();
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

        class wordsil extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {

            try{
                String idd=String.valueOf(id);

                String results=run(JSON_URL_sil+id);
                JSONArray object = new JSONArray(results);
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
            Toast.makeText(getApplicationContext(),"kelime silindi",Toast.LENGTH_SHORT).show();
            Intent gec = new Intent(worddetail.this, MainActivity.class);
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

    class wordupdate extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {

            try{
                Gson gson = new Gson();
                kelimeadi=kelimeduzenle.getText().toString();
                String results=run(JSON_URL_duzenle+id+"/"+kelimeadi);
                JSONObject object=new JSONObject(results);
                words=gson.fromJson(String.valueOf(object),word.class);
            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
                sonuc=("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //dialog.dismiss();
            Toast.makeText(getApplicationContext(),"kelime düzenlendi",Toast.LENGTH_SHORT).show();
            words.name=kelimeadi;
            wordname.setText(words.name);
        }
    }

    class wordanlamekle extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String results = run(JSON_URL_meanekle+wordmean.getText().toString()+"/"+id);
                JSONObject object = new JSONObject(results);
                Gson gson = new Gson();
                newmean=gson.fromJson(String.valueOf(object),mean.class);
                System.out.println("mnmnmnmnm");

            } catch (Exception e) {
                Log.e("yyyyyyyy", e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"anlam eklendi",Toast.LENGTH_SHORT).show();
            wordmean.setText("");
            words.means.add(newmean);
            means();
        }
    }
    class meansil extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {


                String results = run(JSON_URL_meansil+silmeanid);
                JSONArray object = new JSONArray(results);
                System.out.println("mnmnmnmnm");

            } catch (Exception e) {
                Log.e("yyyyyyyy", e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"silindi",Toast.LENGTH_SHORT).show();
            words.means.remove(silinecekmean);
            means();

        }
    }

    class meanduzenle extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(JSON_URL_meanduzenle+silmeanid+"/"+mean.getText().toString());
                JSONObject object = new JSONObject(results);
                System.out.println("mnmnmnmnm");

            } catch (Exception e) {
                Log.e("yyyyyyyy", e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"düzenlendi",Toast.LENGTH_SHORT).show();

            silinecekmean.meanName=mean.getText().toString();
            //-----------------------------------
            means();
        }
    }

    public void duzenle(View view){
        new wordupdate().execute();
    }

    public void anlamekle(View view){
        new wordanlamekle().execute();
    }

    public void sill(View view){
        new meansil().execute();
    }

    public void duzenlee(View view){
        new meanduzenle().execute();
    }
}
