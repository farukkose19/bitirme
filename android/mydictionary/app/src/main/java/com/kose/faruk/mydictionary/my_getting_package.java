package com.kose.faruk.mydictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.Package;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static com.kose.faruk.mydictionary.settings.url.url;

public class my_getting_package extends Fragment {

    private String getpackage = url+"package/getpackage/";
    List<Package> packages;
    LinearLayout ll;
    LinearLayout fragContainer;
    SharedPreferences sharedpreferences;
    int userid;
    int friendID;
    fragment_adapter adapter;
    View view;

    public my_getting_package(){
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_friends_get_package,container,false);

        Bundle bundle=getArguments();
        friendID=bundle.getInt("friendID");
        userid=bundle.getInt("userid");
        fragContainer = (LinearLayout) view.findViewById(R.id.llFragmentContainer2);

        ll = new LinearLayout(view.getContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setId(54321);

        packages = new ArrayList<Package>();
        new getpackage2().execute();
        return view;
    }

    class getpackage2 extends AsyncTask<String,Void,String> {
        String sonuc="giriş işlemi başarısız lütfen daha sonra tekrar deneyin!!";
        @Override
        protected String doInBackground(String... strings) {

            try{
                String results=run(getpackage+friendID+"/"+userid);
                JSONArray object=new JSONArray(results);
                Gson gson = new Gson();
                packages = Arrays.asList(gson.fromJson(String.valueOf(object), Package[].class));

            }catch (Exception e){
                Log.e("yyyyyyyy",e.toString());
                sonuc=("sonuç başarısız daha sonra tekrar deneyin");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(packages.size()>0){
                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("veri", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                Gson gson = new Gson();
                for(int i=0;i<packages.size();i++){
                    String json = gson.toJson(packages.get(i));
                    editor.putString(""+i, json);
                    getFragmentManager().beginTransaction().add(ll.getId(), fragmentPackage.newInstance(i),
                            "someTag"+i).commit();
                }

                editor.commit();
            }
            fragContainer.addView(ll);
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
}
