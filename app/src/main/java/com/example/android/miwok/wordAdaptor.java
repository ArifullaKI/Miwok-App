package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class wordAdaptor extends ArrayAdapter {

    //inteializing the color recource id var.
    private int mColorRID;

    public wordAdaptor(Context context, ArrayList<word> words,int colorRid) {

        super(context,0, words);
        mColorRID = colorRid;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        word local_word = (word) getItem(position);

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        miwokTextView.setText(local_word.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        defaultTextView.setText(local_word.getDefaultTranslation());


        ImageView imageTextView = (ImageView) listItemView.findViewById(R.id.imageView);

        if(local_word.hasImage()) {
            imageTextView.setImageResource(local_word.getRid());
            imageTextView.setVisibility(View.VISIBLE);
        }
        else {
            imageTextView.setVisibility(View.GONE);
        }

        //set theme color for the list item.
        View textContainer = (View) listItemView.findViewById(R.id.textContainer);
        //find the color fromm the colorRID.
        int color= ContextCompat.getColor(getContext(),mColorRID);
        //Set the Background color for the text Container.
        textContainer.setBackgroundColor(color);



        return listItemView;


    }

}
