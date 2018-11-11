package com.apps.gamehoundgames.frozasimpletuning;

import android.support.v4.app.FragmentManager;
import android.view.View;

public class InfoMsgClickListener implements View.OnClickListener {

    private String dialogTitle, dialogBody;
    private FragmentManager dialogFragment;


    public InfoMsgClickListener(String title, String body, FragmentManager fragment){
        this.dialogBody = body;
        this.dialogTitle = title;
        this.dialogFragment = fragment;
    }//ctor


    @Override
    public void onClick(View v) {
        InfoMessage msg = new InfoMessage(this.dialogTitle, this.dialogBody);
        msg.show(this.dialogFragment, "How it works");
    }//onClick

}//class
