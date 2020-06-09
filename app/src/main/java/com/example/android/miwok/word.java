package com.example.android.miwok;

import android.view.View;

public class word {


    private String mMiwokTranslation;
    private String mDefaultTranslation;
    private int mRid = NO_IMAGE;
    private static final  int NO_IMAGE = -1;
    private int mAudioRID;



    // Creatinf a constractor for the word

    public  word (String defaultTranslaton,String miwokTranslation,int audioRID){
        mMiwokTranslation= miwokTranslation;
        mDefaultTranslation=defaultTranslaton;
        mAudioRID = audioRID;
    }

    // constractor for the 3 param
    public  word (String defaultTranslaton,String miwokTranslation,int Rid,int audioRID){
        mMiwokTranslation= miwokTranslation;
        mDefaultTranslation=defaultTranslaton;
        mRid = Rid;
        mAudioRID = audioRID;
    }

    public void setmDefaultTranslation(String mDefaultTranslation) {
        this.mDefaultTranslation = mDefaultTranslation;
    }

    public void setmMiwokTranslation(String mMiwokTranslation) {
        this.mMiwokTranslation = mMiwokTranslation;
    }

    public int getmAudioRID(){
        return mAudioRID;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getRid(){
        return mRid;
    }

    public boolean hasImage(){
        return mRid != NO_IMAGE;
    }


}
