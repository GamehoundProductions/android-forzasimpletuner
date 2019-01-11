package com.apps.gamehoundgames.frozasimpletuning.InputFields;

import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputListener;

public interface EventDelegate {
    //method to update the observer, used by subject
    void update(EditText whoIsCalling);
//    public void update();
    void SetDelegate(InputListener newDelegate);

    EditText GetSelf();
    InputListener GetDelegate();
}//interface
