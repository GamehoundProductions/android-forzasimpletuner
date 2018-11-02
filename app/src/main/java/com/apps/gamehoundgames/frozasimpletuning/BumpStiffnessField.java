package com.apps.gamehoundgames.frozasimpletuning;

import android.util.Log;
import android.widget.EditText;

/**
 * This class is addition to base InputField. This will use Formula to calculate its field value
 * usin whoIsCalling text on update(whoIsCalling). This should be a Result field calling bump stiffness
 * to calculate % value.
 */
public class BumpStiffnessField extends InputField {

    Formula formula;

    public BumpStiffnessField(EditText itselfField) {
        super(itselfField);
    }

    @Override
    public void update(EditText whoIsCalling) {
        if(formula == null)
            return;

        float value = 0;
        try {
            value = Float.parseFloat(whoIsCalling.getText().toString());
        }catch (NumberFormatException err){
            value = -1;
        }
        float result = this.formula.calculate(value, -1, -1);
        this.GetSelf().setText(String.valueOf(result));
    }


    public void SetFormula(Formula newFormula) { this.formula = newFormula; }

}//class
