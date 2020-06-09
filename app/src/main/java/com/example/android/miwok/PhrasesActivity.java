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
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }

    };

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange ==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK || focusChange == AudioManager.AUDIOFOCUS_LOSS){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private void releaseMediaPlayer() {
        if(mMediaPlayer != null ){
            mMediaPlayer.release();

            mMediaPlayer = null;
        }

    }
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

        final ArrayList<word> phrasesList = new ArrayList<word>();
        phrasesList.add(new word("Where are you going","minto wuksus",R.raw.phrase_where_are_you_going));
        phrasesList.add(new word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phrasesList.add(new word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phrasesList.add(new word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phrasesList.add(new word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phrasesList.add(new word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phrasesList.add(new word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        phrasesList.add(new word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        phrasesList.add(new word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        phrasesList.add(new word("Come here.","әnni'nem",R.raw.phrase_come_here));

        wordAdaptor phrasesAdaptor = new wordAdaptor(this,phrasesList,R.color.category_phrases);
        ListView phrasesListView = (ListView) findViewById(R.id.list);
        phrasesListView.setAdapter(phrasesAdaptor);


        phrasesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                releaseMediaPlayer();
                word word = phrasesList.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,word.getmAudioRID());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

    }
}
