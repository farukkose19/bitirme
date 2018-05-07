package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kose.faruk.mydictionary.models.Package;
import org.json.JSONException;
import org.json.JSONObject;

public class fragmentPackage extends ListFragment {

    static fragment_adapter adapter;
    SharedPreferences sharedpreferences;
    ListView listView;
    static View v;
    Package aPackage;
    Button button;
    int idd;

    public fragmentPackage() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(v.getContext(),""+aPackage.id,Toast.LENGTH_LONG).show();
        Intent gec = new Intent(v.getContext(), packageDetail.class);
        gec.putExtra("packageid", aPackage.id);
        startActivity(gec);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v =  inflater.inflate(R.layout.fragment_fragment_package, container, false);
        Gson gson = new Gson();
        sharedpreferences = v.getContext().getSharedPreferences("veri", Context.MODE_PRIVATE);
        String json=sharedpreferences.getString(""+getArguments().getInt("text"),"");

        try {
            JSONObject object = new JSONObject(json);
            aPackage = gson.fromJson(String.valueOf(object),Package.class);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("---fragment package ---",e.toString());
        }

        listView=(ListView) v.findViewById(android.R.id.list);
        button=(Button) v.findViewById(R.id.textView7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(v.getContext(),""+aPackage.id,Toast.LENGTH_LONG).show();
                Intent gec = new Intent(v.getContext(), packageDetail.class);
                gec.putExtra("packageid", aPackage.id);
                startActivity(gec);
            }
        });
        return v;
    }

    public static fragmentPackage newInstance( int jsonword) {

        fragmentPackage f = new fragmentPackage();
        Bundle b = new Bundle();
        b.putInt("text", jsonword);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            adapter = new fragment_adapter(aPackage.words, v.getContext());
            button.setText("-"+aPackage.packageName+"-");
            //setListAdapter(adapter);
            listView.setAdapter(adapter);

        }catch (Exception e){
            Log.e("--trycatch--",e.toString());
        }


    }
}
