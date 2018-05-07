package com.kose.faruk.mydictionary;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.Package;
import com.kose.faruk.mydictionary.models.mean;
import com.kose.faruk.mydictionary.models.word;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    private String JSON_URL_getwordwithuser = url+"word/wgetuser/";
    private String JSON_URL_arama = url+"word/search/";
    private String JSON_URL_add_package = url+"package/addjustpackage/";
    ListView listView;
    List<String> name;
    List<Integer> id;
    wordcustomadapter adapter;
    wordselectedadapter selectedadapter;
    List<word> persons;
    EditText arama;
    String aranacakword;
    List<word> wordss;
    Button ara;
    int userid;
    List<word> words;
    List<word> secilenIDler;
    List<Integer> selected;
    DialogInterface dialog;
    Dialog dialog2;
    EditText customname;
    Button kaydet;
    Button vazgec;
    String package_name;
    Package aPackage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        userid=sharedpreferences.getInt("id",0);
        String username=sharedpreferences.getString("name","kullanici mevcut değil");
        if(userid==0){
            Intent gec = new Intent(MainActivity.this, Login.class);
            startActivity(gec);
        }
        else {
            persons = new ArrayList<word>();
            secilenIDler = new ArrayList<>();
            selected = new ArrayList<>();
            listView = (ListView) findViewById(R.id.listview);

            dialog2=new Dialog(this);
            dialog2.setTitle("Daha Fazla Anlam Ekle");
            dialog2.setContentView(R.layout.customdialog3);
            kaydet=(Button)dialog2.findViewById(R.id.addpackagebutton);
            vazgec=(Button)dialog2.findViewById(R.id.iptalpackage);
            customname=(EditText)dialog2.findViewById(R.id.addpackagename);

            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                    boolean varMi = false;
                    for (int j = 0; j < selected.size(); j++) {
                        if (selected.get(j) == i) {
                            varMi = true;
                        }
                    }
                    if (varMi == true) {
                        secilenIDler.remove(words.get(i));
                        for (int j = 0; j < selected.size(); j++) {
                            if (i == selected.get(j)) {
                                selected.remove(selected.get(j));
                            }
                        }
                    } else {
                        secilenIDler.add(words.get(i));
                        selected.add(i);
                    }
                    selectedadapter = new wordselectedadapter(MainActivity.this, words, getApplicationContext(), selected);
                    listView.setAdapter(selectedadapter);
                }

                @Override
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    MenuInflater menuInflater = actionMode.getMenuInflater();
                    menuInflater.inflate(R.menu.action, menu);
                    selectedadapter = new wordselectedadapter(MainActivity.this, words, getApplicationContext(), selected);
                    listView.setAdapter(selectedadapter);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    int id = menuItem.getItemId();

                    if (id == R.id.group) {

                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("ARKADAŞ")
                                .setMessage("Arkadaşınıza göndermek ister misiniz?")
                                .setIcon(android.R.drawable.ic_menu_send);

                        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog=dialogInterface;
                                Gson gson = new Gson();
                                String json = gson.toJson(secilenIDler);
                                Intent gec = new Intent(MainActivity.this,package_friends_add.class);
                                gec.putExtra("addword", json);
                                startActivity(gec);
                        }
                        });
                        builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialog2.show();

                                dialogInterface.dismiss();
                            }
                        });

                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();



                        return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode actionMode) {
                    secilenIDler.clear();
                    selected.clear();
                    adapter = new wordcustomadapter(MainActivity.this, words,getApplicationContext());
                    listView.setAdapter(adapter);
                }
            });
            name = new ArrayList<String>();
            id = new ArrayList<Integer>();
            new getword().execute();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    word s = (word) listView.getItemAtPosition(i);
                    Intent gec = new Intent(MainActivity.this, worddetail.class);
                    gec.putExtra("word", s.id);
                    startActivity(gec);
                }
            });
            arama = (EditText) findViewById(R.id.editText5);
            ara = (Button) findViewById(R.id.button3);
            ara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aranacakword = arama.getText().toString();
                    if (!aranacakword.equals(""))
                        new getmean().execute();
                    else
                        new getword().execute();
                }
            });
        }
    }

    class getword extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String results = run(JSON_URL_getwordwithuser+userid);
                JSONArray object = new JSONArray(results);
                Gson gson = new Gson();
                words = new ArrayList<word>();
                words = Arrays.asList(gson.fromJson(String.valueOf(object), word[].class));
            } catch (Exception e) {
                Log.e("yyyyyyyy",e.toString());
                sonuc = ("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new wordcustomadapter(MainActivity.this, words, getApplicationContext());
            listView.setAdapter(adapter);
        }
    }

    class addnewpackage extends AsyncTask<String,Void,String> {
        String sonuc = "giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";

        @Override
        protected String doInBackground(String... strings) {

            try {
                String idler=""+secilenIDler.get(0).id;
                for(int i=1;i<(secilenIDler.size());i++){
                    idler=idler+","+secilenIDler.get(i).id;
                }
                String results = run(JSON_URL_add_package+package_name+"/"+userid+"/"+idler);
                JSONObject object=new JSONObject(results);
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
            Toast.makeText(getApplicationContext(),aPackage.packageName,Toast.LENGTH_SHORT).show();
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

    class getmean extends AsyncTask<String,Void,String> {
        int sonuc=0;

        @Override
        protected String doInBackground(String... strings) {

            try{
                OkHttpClient client=new OkHttpClient();

                Request request=new Request.Builder()
                        .url(JSON_URL_arama+userid+"/"+aranacakword.trim())
                        .build();
                Response response=client.newCall(request).execute();
                String results=response.body().string();
                //JSONArray object=new JSONArray(results);
                JSONObject object=new JSONObject(results);

                sonuc=response.code();
                if(sonuc==200){
                Gson gson = new Gson();
                word wrd=new word();
                wrd=gson.fromJson(String.valueOf(object),word.class);
                wordss = new ArrayList<word>();
                wordss.add(wrd);
                }
                //word = Arrays.asList(gson.fromJson(String.valueOf(object), word[].class));

            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(sonuc==200){
            adapter=new wordcustomadapter(MainActivity.this,wordss,getApplicationContext());
            listView.setAdapter(adapter);
            }
            else{
                Toast.makeText(getApplicationContext(),"aranan kelime bulunamadı",Toast.LENGTH_LONG).show();
                new getword().execute();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ekle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.ekle) {
            Intent gec = new Intent(MainActivity.this, wordekle.class);
            startActivity(gec);
            return true;
        }
        if (id == R.id.logout) {
            logout();
            Intent gec = new Intent(MainActivity.this, Login.class);
            startActivity(gec);
            return true;
        }
        if (id == R.id.friends) {
            Intent gec = new Intent(MainActivity.this, friend.class);
            startActivity(gec);
            return true;
        }
        if (id == R.id.get_package) {
            Intent gec = new Intent(MainActivity.this, all_package.class);
            startActivity(gec);
            return true;
        }
        if (id == R.id.game) {
            Intent gec = new Intent(MainActivity.this, oyunlar.class);
            startActivity(gec);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        int userid=sharedpreferences.getInt("id",0);
        if(userid==0){
            Intent gec = new Intent(MainActivity.this, Login.class);
            startActivity(gec);
        }
        else{
            new getword().execute();
        }
    }

    public  void logout(){
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getApplicationContext(),"tıklandı",Toast.LENGTH_SHORT).show();
    }

    public void packageiptal(View view){
        dialog2.dismiss();
    }

    public void packagekaydet(View view){
        //Toast.makeText(getApplicationContext(),"aa",Toast.LENGTH_SHORT).show();
        package_name=customname.getText().toString();
        new addnewpackage().execute();
        dialog2.dismiss();
    }


}
