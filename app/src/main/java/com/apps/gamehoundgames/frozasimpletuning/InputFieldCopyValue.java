package com.apps.gamehoundgames.frozasimpletuning;

import android.widget.EditText;

public class InputFieldCopyValue extends InputField {
    public InputFieldCopyValue(EditText itselfField) {
        super(itselfField);
    }//ctor

    @Override
    public void update(EditText whoIsCalling) {
        super.update(whoIsCalling);
        this.GetSelf().setText(whoIsCalling.getText());
    }//update
}//class
