package com.apps.gamehoundgames.frozasimpletuning.InputFields;

import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputField;

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
