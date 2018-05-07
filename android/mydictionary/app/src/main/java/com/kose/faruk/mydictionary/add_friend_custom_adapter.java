package com.kose.faruk.mydictionary;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kose.faruk.mydictionary.models.kullanici;

import java.util.List;

/**
 * Created by Lenovo on 6.12.2017.
 */

public class add_friend_custom_adapter extends BaseAdapter {

    private List<kullanici> friends;
    private List<kullanici> myfriends;
    private LayoutInflater layoutInflater;
    private Context context;
    private View activity;
    private addnewfriend addnewfriend;
    private int userid;

    public add_friend_custom_adapter(View activity, List<kullanici> friends, Context context,
                                     List<kullanici> myfriends,addnewfriend addnewfriend,
                                     int userid){
        super();
        //layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.friends=friends;
        this.context=context;
        this.activity=activity;
        this.myfriends=myfriends;
        this.addnewfriend=addnewfriend;
        this.userid=userid;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View satirview;
        satirview=layoutInflater.inflate(R.layout.add_friend_adapter,null);
        if(view==null){
            view=layoutInflater.inflate(R.layout.add_friend_adapter,viewGroup,false);
        }
            TextView name=(TextView) satirview.findViewById(R.id.tv_add_friend);
            final kullanici kullanicii=friends.get(i);
            name.setText(kullanicii.name);


        final ImageView imageView =(ImageView)satirview.findViewById(R.id.imageView);
        if(varmi(friends.get(i).id)){
            imageView.setBackgroundResource(R.drawable.check);

        }
        else {
        }

        return satirview;

    }
    public boolean varmi(int id){
        for(int i=0;i<myfriends.size();i++){
            if(myfriends.get(i).id==id){
                return true;
            }
        }
        return false;
    }
}
