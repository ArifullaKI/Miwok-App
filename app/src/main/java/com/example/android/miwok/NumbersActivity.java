/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager  mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK || focusChange == AudioManager.AUDIOFOCUS_LOSS){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };


    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

// creating ArrayList of string
        final ArrayList<word> words = new ArrayList<word>();

        words.add(new word("one","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new word("two","ottiko",R.drawable.number_two,R.raw.number_two));
        words.add(new word("three","tolokuso",R.drawable.number_three,R.raw.number_three));
        words.add(new word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new word("five","massoka",R.drawable.number_five,R.raw.number_five));
        words.add(new word("six","temmoka",R.drawable.number_six,R.raw.number_six));
        words.add(new word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));

       wordAdaptor adaptor = new wordAdaptor(this,words,R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                releaseMediaPlayer();
                word word = words.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getmAudioRID());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });

        mAudioManager.abandonAudioFocus(mOnAudioChangeListener);

    }


    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }



}
