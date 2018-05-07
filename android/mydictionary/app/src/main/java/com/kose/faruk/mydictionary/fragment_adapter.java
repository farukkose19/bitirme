package com.kose.faruk.mydictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kose.faruk.mydictionary.models.word;
import java.util.List;


public class fragment_adapter extends BaseAdapter {

    private List<word> words;

    private LayoutInflater layoutInflater;
    private Context context;

    public fragment_adapter( List<word> words,
                             Context context){
        this.words=words;
        this.context=context;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int i) {
        return words.get(i);
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
        final word wordx=words.get(i);
        name.setText(wordx.name);
        return satirview;

    }
}
