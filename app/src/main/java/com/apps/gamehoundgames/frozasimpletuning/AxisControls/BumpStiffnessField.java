package com.apps.gamehoundgames.frozasimpletuning.AxisControls;

import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.Formula;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputField;

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
        if(formula == null) {
            return;
        }

        float value = 0;
        String fieldText = whoIsCalling.getText().toString();
        //Russian language using commas instead of dots for decimal numbers. Java cant property
        //parse that format into the number. Thus - need to replace comas with dots and life will be good.
        fieldText = fieldText.replace(',', '.');

        try {
            value = Float.parseFloat(fieldText);
        }catch (NumberFormatException err){
            value = -1;
        }

        float result = this.formula.calculate(value, -1, -1);
        this.GetSelf().setText(String.valueOf(result));
    }


    public void SetFormula(Formula newFormula) { this.formula = newFormula; }

}//class
