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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }

    };
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
        }
    }

        private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if(focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK){
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                }if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                    releaseMediaPlayer();
                }if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                    mMediaPlayer.start();
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

        final ArrayList<word> familyList = new ArrayList<word>();
        familyList.add(new word("mother", "әṭa",R.drawable.family_mother,R.raw.family_mother));
        familyList.add(new word("father","әpә",R.drawable.family_father,R.raw.family_father));
        familyList.add(new word("son","angsi",R.drawable.family_son,R.raw.family_son));
        familyList.add(new word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        familyList.add(new word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyList.add(new word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyList.add(new word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyList.add(new word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyList.add(new word("grand mother","ama",R.drawable.family_mother,R.raw.family_grandmother));
        familyList.add(new word("grand father","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));


        wordAdaptor familyAdaptor = new wordAdaptor(this,familyList,R.color.category_family);
        ListView familylistView = (ListView) findViewById(R.id.list);
        familylistView.setAdapter(familyAdaptor);

        familylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                releaseMediaPlayer();
                word word = familyList.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmAudioRID());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);


    }
}
