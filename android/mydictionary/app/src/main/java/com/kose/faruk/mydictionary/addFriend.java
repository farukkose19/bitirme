package com.kose.faruk.mydictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.kullanici;
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

public class addFriend extends Fragment implements addnewfriend {

    private String searchfriend = url+"kullanici/searchfriend/";
    private String addnew = url+"kullanici/addfriend/";
    List<kullanici> friends;
    int userid;
    add_friend_custom_adapter adapter;
    ListView listView;
    View view;
    EditText editText;
    JSONArray object;
    List<kullanici> myfriends;
    int friendid;
    List<kullanici> liste;
    List<kullanici> addnewfriendliste;
    kullanici eklenecekArkadas;

    public addFriend() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_add_friend,container,false);
        friends=new ArrayList<>();
        liste=new ArrayList<>();
        addnewfriendliste=new ArrayList<>();
        editText=(EditText)view.findViewById(R.id.editText9);
        listView=(ListView)view.findViewById(R.id.searchfriend);
        Button button=(Button)view.findViewById(R.id.button10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new searchfriends().execute();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                friendid=liste.get(i).id;
                eklenecekArkadas=liste.get(i);
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("uyarı!")
                        .setMessage("arkadaş olmak ister misiniz?")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("evet", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                new add().execute();
                                //Toast.makeText(getActivity(),"asada",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("hayır", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getActivity(),"Hayııır",Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        return view;
    }

    class searchfriends extends AsyncTask<String,Void,String> {
        String sonuc="";
        @Override
        protected String doInBackground(String... strings) {
            try{
                Bundle bundle=getArguments();
                userid=bundle.getInt("userid");
                String name=editText.getText().toString();
                String results=run(searchfriend+name);
                object=new JSONArray(results);
                Gson gson = new Gson();
                friends = Arrays.asList(gson.fromJson(String.valueOf(object), kullanici[].class));
                if(results.equals("[]")){
                    sonuc="bulunamadi";
                }
            }catch (JSONException e){
                sonuc=e.getMessage();
                Log.e("yyyyyyyy",e.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(sonuc.equals("bulunamadi")){
               Toast.makeText(view.getContext(),"arama sonucu bulunamadı!!",Toast.LENGTH_SHORT).show();
                listView.setAdapter(null);
            }
            else{
                liste.clear();
                for(int i=0;i<friends.size();i++){
                    if(friends.get(i).id==userid){}
                    else
                        liste.add(friends.get(i));
                }
                adapter=new add_friend_custom_adapter(view,liste,getContext(),myfriends,addFriend.this,userid);
                listView.setAdapter(adapter);
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

    class add extends AsyncTask<String,Void,String> {
        String sonuc="";
        @Override
        protected String doInBackground(String... strings) {
            try{
                Bundle bundle=getArguments();
                userid=bundle.getInt("userid");
                String idd=String.valueOf(userid);
                String friend_id=String.valueOf(friendid);
                String results=run(addnew+idd+"/"+friend_id);
                JSONObject object=new JSONObject(results);
                //Gson gson = new Gson();
                //friends = Arrays.asList(gson.fromJson(String.valueOf(object), kullanici[].class));
            }catch (JSONException e){
                sonuc=e.getMessage();
                Log.e("yyyyyyyy",e.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
                remyfriend remyfriend=(remyfriend) getActivity();
                remyfriend.refresh(eklenecekArkadas);
                Toast.makeText(view.getContext(),"eklendi",Toast.LENGTH_SHORT).show();
                for(int i =0;i<myfriends.size();i++){
                    addnewfriendliste.add(myfriends.get(i));
                }
                addnewfriendliste.add(eklenecekArkadas);
                adapter=new add_friend_custom_adapter(view,liste,getContext(),addnewfriendliste,addFriend.this,userid);
                listView.setAdapter(adapter);
        }
    }

    public void myfriends(List<kullanici> myfriends){
        this.myfriends=new ArrayList<>();
        this.myfriends=myfriends;
    }

    @Override
    public void ekle() {
        //new add().execute();
    }
}
