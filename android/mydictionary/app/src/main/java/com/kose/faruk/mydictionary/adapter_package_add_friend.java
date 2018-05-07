package com.kose.faruk.mydictionary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kose.faruk.mydictionary.models.kullanici;
import com.kose.faruk.mydictionary.models.word;

import java.util.List;

/**
 * Created by Muhammed Faruk KOSE on 10.03.2018.
 */

public class adapter_package_add_friend extends BaseAdapter {

    private List<kullanici> friends;
    private Context context;
    private Activity activity;
    private LayoutInflater layoutInflater;

    public adapter_package_add_friend(Activity activity, List<kullanici> friends, Context context){
        super();
        layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.friends=friends;
        this.context=context;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int i) {
        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return friends.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View satirview;
        satirview=layoutInflater.inflate(R.layout.adapter,null);
        if(view==null){
            view=layoutInflater.inflate(R.layout.adapter,viewGroup,false);
        }
        TextView name=(TextView) satirview.findViewById(R.id.tv);
        final kullanici kullanici=friends.get(i);
        name.setText(kullanici.name);
        return satirview;
    }
}
