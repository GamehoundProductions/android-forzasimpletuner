package com.apps.gamehoundgames.frozasimpletuning;

import android.util.Log;
import android.widget.EditText;

public class BumpStiffnessEntry extends TuningEntry {

    /**
     * @param exitTextFields : fields [min, max, result];
     */
    public BumpStiffnessEntry(EditText[] exitTextFields) {
        super(exitTextFields);
    }//ctor


    /**
     *  Use BumpStiffnessField instead of InputField. This will register FormulaBumpMin or Max
     * for min and max fields, so that later on, when Result field is updated, it will call this
     * input, which will calculate its value with the appropriate formula (val * 0.5 or val *0.75).
     * @param fields: array of fields to create InputFields from.
     * @return
     */
    @Override
    protected InputField[] makeInputFields(EditText[] fields) {
        BumpStiffnessField[] result = new BumpStiffnessField[fields.length];
        for(int i = 0; i < fields.length; i++){
            if(fields[i] == null){ //should not happened, but safety net to prevent crashes
                result[i] = null;
                continue;
            }
            result[i] = new BumpStiffnessField(fields[i]);

            Formula formula;
            if(i == 0) formula = new FormulaBumpMin();
            else formula = new FormulaBumpMax();

            result[i].SetFormula(formula);
        }//for

        return result;
    }


    private void configureBumpStiffness(){
        BumpStiffnessField[] inputs = (BumpStiffnessField[])this.GetMinMax();
        for(int i = 0; i < inputs.length; i++){
            this.resultField.AddListener(inputs[i]);
        }//for
    }//configureBumpStiffness


    @Override
    public void SetResultField(ResultDelegate newResultField) {
        super.SetResultField(newResultField);
        this.configureBumpStiffness();
    }
}//class
