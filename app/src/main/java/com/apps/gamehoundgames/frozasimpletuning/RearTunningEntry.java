package com.apps.gamehoundgames.frozasimpletuning;

import android.widget.EditText;

public class RearTunningEntry extends TuningEntry {


    /**
     * @param exitTextFields
     */
    public RearTunningEntry(EditText[] exitTextFields) {
        super(exitTextFields);
    }


    @Override
    protected InputField[] makeInputFields(EditText[] fields) {
        InputFieldCopyValue[] result = new InputFieldCopyValue[fields.length];
        for(int i = 0; i < fields.length; i++){
            if(fields[i] == null){ //should not happened, but safety net to prevent crashes
                result[i] = null;
                continue;
            }
            result[i] = new InputFieldCopyValue(fields[i]);
        }//for

        return result;
    }
}//class
