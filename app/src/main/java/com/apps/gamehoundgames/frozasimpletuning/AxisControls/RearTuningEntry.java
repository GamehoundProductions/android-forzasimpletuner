package com.apps.gamehoundgames.frozasimpletuning.AxisControls;

import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputField;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputFieldCopyValue;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;

public class RearTuningEntry extends TuningEntry {


    /**
     * @param exitTextFields
     */
    public RearTuningEntry(EditText[] exitTextFields) {
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

            String inputIdStr = Integer.toString(result[i].GetSelf().getId());
            result[i].GetSelf().setText(CommonActions.GetPrefValue(inputIdStr));
        }//for

        return result;
    }
}//class
