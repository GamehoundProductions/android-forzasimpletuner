package com.apps.gamehoundgames.frozasimpletuning;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

@SuppressLint("ValidFragment")
public class InfoMessage extends AppCompatDialogFragment {

    private String dialogTitle, dialogBody;

    @SuppressLint("ValidFragment")
    public InfoMessage(String title, String dsc){
        this.dialogTitle = title;
        this.dialogBody = dsc;
    }//ctor

    @Override
    public Dialog onCreateDialog(Bundle instanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(this.dialogTitle).
                setMessage(this.dialogBody)
                .setPositiveButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }//onCreateDialog

}//class
