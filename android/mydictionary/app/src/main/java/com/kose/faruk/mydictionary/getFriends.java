package com.kose.faruk.mydictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.kullanici;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.kose.faruk.mydictionary.settings.url.url;

public class getFriends extends Fragment {

    private String getfriend = url+"kullanici/getfriend/";
    private String deletefriend = url+"kullanici/deletefriend/";
    List<kullanici> friends;
    friendcustomadapter adapter;
    ListView listView;
    int userid;
    View view;
    kullanici kullanici;
    DialogInterface dialog;
    int friendID;

    public getFriends() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_get_friends,container,false);
        friends=new ArrayList<>();
        listView=(ListView) view.findViewById(R.id.friend_list);
        new getfriends().execute();

        ((ListView) view.findViewById(R.id.friend_list)).setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent gec = new Intent(view.getContext(), my_package_management.class);
                gec.putExtra("friendID", friends.get(position).id);
                startActivity(gec);
            }
        });

        ((ListView) view.findViewById(R.id.friend_list)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                friendID=friends.get(i).id;
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("SİL?")
                        .setMessage("Silmek istediğinizden emin misiniz?")
                        .setIcon(android.R.drawable.ic_menu_delete);

                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog=dialogInterface;
                        new frienddelete().execute();
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
        });
        return view;
    }

    class getfriends extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {
            try{
                Bundle bundle=getArguments();
                userid=bundle.getInt("userid");
                String results=run(getfriend+userid);
                JSONObject object=new JSONObject(results);
                Gson gson = new Gson();
                kullanici=gson.fromJson(String.valueOf(object),kullanici.class);
                friends = kullanici.friends;
            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
                sonuc=("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(friends.size()==0){

            }else {
                adapter=new friendcustomadapter(view,friends,getContext());
                listView.setAdapter(adapter);
                sendmyfriends sendmyfriends=(sendmyfriends) getActivity();
                sendmyfriends.send(friends);
            }
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

    public void yeniArkGet(kullanici k){
        List<kullanici> liste=new ArrayList<>();
        for(int i=0;i<friends.size();i++){
            liste.add(friends.get(i));
        }
        liste.add(k);
        friends=new ArrayList<>();
        friends=liste;
        adapter=new friendcustomadapter(view,friends,getContext());
        listView.setAdapter(adapter);
    }

    class frienddelete extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {

            try{
                String results=run(deletefriend+userid+"/"+friendID);
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
            Toast.makeText(view.getContext(),"arkadaş silindi",Toast.LENGTH_SHORT).show();
            Intent gec = new Intent(view.getContext(), MainActivity.class);
            startActivity(gec);
        }
    }
}