package com.apps.gamehoundgames.frozasimpletuning;

import android.widget.EditText;

public interface EventDelegate {
    //method to update the observer, used by subject
    void update(EditText whoIsCalling);
//    public void update();
    void SetDelegate(InputListener newDelegate);

    EditText GetSelf();
    InputListener GetDelegate();
}//interface
