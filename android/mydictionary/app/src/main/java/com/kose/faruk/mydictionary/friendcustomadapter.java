package com.kose.faruk.mydictionary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kose.faruk.mydictionary.models.kullanici;
import java.util.List;

/**
 * Created by Lenovo on 4.12.2017.
 */

public class friendcustomadapter extends BaseAdapter {

    private List<kullanici> friends;
    private LayoutInflater layoutInflater;
    private Context context;
    private View activity;

    public friendcustomadapter(View activity, List<kullanici> friends, Context context ){
        //layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return i;
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
        final kullanici kullanicii=friends.get(i);
        name.setText(kullanicii.name);
        return satirview;

    }

}
