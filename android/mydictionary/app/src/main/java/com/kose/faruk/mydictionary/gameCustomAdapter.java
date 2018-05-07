package com.kose.faruk.mydictionary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kose.faruk.mydictionary.models.game;
import com.kose.faruk.mydictionary.models.word;

import java.util.List;

/**
 * Created by Muhammed Faruk KOSE on 1.05.2018.
 */

public class gameCustomAdapter extends BaseAdapter {
    private List<game> games;
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity activity;
    private Boolean goster;


    public gameCustomAdapter(Activity activity, List<game> games,Context context){
        layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.games=games;
        this.context=context;
        this.activity=activity;
        this.goster=goster;
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int i) {
        return games.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View satirview;
        satirview=layoutInflater.inflate(R.layout.games_adapter,null);
        if(view==null){
            view=layoutInflater.inflate(R.layout.games_adapter,viewGroup,false);
        }
        TextView name=(TextView) satirview.findViewById(R.id.textView10);
        final Button c1=(Button) satirview.findViewById(R.id.button14);
        final Button c2=(Button) satirview.findViewById(R.id.button15);
        final Button c3=(Button) satirview.findViewById(R.id.button16);
        final Button c4=(Button) satirview.findViewById(R.id.button17);

        final game game=games.get(i);
        name.setText(game.soru);
        c1.setText(game.cevap1);
        c2.setText(game.cevap2);
        c3.setText(game.cevap3);
        c4.setText(game.cevap4);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(game.dogruCevap==0){
                    c1.setBackgroundColor(Color.GREEN);
                }
                else {
                    c1.setBackgroundColor(Color.RED);
                    if (game.dogruCevap==1)
                        c2.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==2)
                        c3.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==3)
                        c4.setBackgroundColor(Color.GREEN);
                }
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.dogruCevap==1){
                    c2.setBackgroundColor(Color.GREEN);
                }
                else {
                    c2.setBackgroundColor(Color.RED);
                    if (game.dogruCevap==0)
                        c1.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==2)
                        c3.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==3)
                        c4.setBackgroundColor(Color.GREEN);
                }
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.dogruCevap==2){
                    c3.setBackgroundColor(Color.GREEN);
                }
                else {
                    c3.setBackgroundColor(Color.RED);
                    if (game.dogruCevap==0)
                        c1.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==1)
                        c2.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==3)
                        c4.setBackgroundColor(Color.GREEN);
                }
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.dogruCevap==3){
                    c4.setBackgroundColor(Color.GREEN);
                }
                else {
                    c4.setBackgroundColor(Color.RED);
                    if (game.dogruCevap==0)
                        c1.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==1)
                        c2.setBackgroundColor(Color.GREEN);
                    else if(game.dogruCevap==2)
                        c3.setBackgroundColor(Color.GREEN);
                }
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
            }
        });
        return satirview;

    }
}
