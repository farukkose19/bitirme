package com.kose.faruk.mydictionary;

import android.app.Activity;
import android.content.Context;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kose.faruk.mydictionary.models.word;

import java.util.List;

/**
 * Created by Lenovo on 21.10.2017.
 */

public class wordcustomadapter extends BaseAdapter{

        private List<word> words;
        private LayoutInflater layoutInflater;
        private Context context;
        private Activity activity;
        private Boolean goster;


        public wordcustomadapter(Activity activity, List<word> words,Context context){
            layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.words=words;
            this.context=context;
            this.activity=activity;
            this.goster=goster;
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
            LinearLayout linearLayout=(LinearLayout) satirview.findViewById(R.id.word_linearlayout);
            final word word=words.get(i);
            name.setText(word.name);
            return satirview;

        }
}
