package com.kose.faruk.mydictionary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kose.faruk.mydictionary.models.mean;
import com.kose.faruk.mydictionary.models.word;

import java.util.List;

/**
 * Created by Lenovo on 21.10.2017.
 */

public class meancustomadapter extends BaseAdapter {

    private List<mean> means;
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity activity;

    public meancustomadapter(Activity activity, List<mean> means, Context context ){
        layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.means=means;
        this.context=context;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return means.size();
    }

    @Override
    public Object getItem(int i) {
        return means.get(i);
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
        final mean mean=means.get(i);
        name.setText(mean.meanName);
        return satirview;

    }

}
