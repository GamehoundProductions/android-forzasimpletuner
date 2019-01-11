package com.apps.gamehoundgames.frozasimpletuning.AxisControls;

import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.Formula;
import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.FormulaBumpMax;
import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.FormulaBumpMin;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputField;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputListener;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;

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

            String inputIdStr = Integer.toString(result[i].GetSelf().getId());
            String savedVal = CommonActions.GetPrefValue(inputIdStr);
            result[i].GetSelf().setText(savedVal);
        }//for

        return result;
    }


    private void configureBumpStiffness(){
        BumpStiffnessField[] inputs = (BumpStiffnessField[])this.GetMinMax();
        for(int i = 0; i < inputs.length; i++){
            this.resultField.AddListener(inputs[i]);

            InputListener fieldListener = new InputListener(inputs[i].GetSelf());
            inputs[i].GetSelf().addTextChangedListener(fieldListener);
        }//for
    }//configureBumpStiffness


    @Override
    public void SetResultField(ResultDelegate newResultField) {
        super.SetResultField(newResultField);
        this.configureBumpStiffness();
    }
}//class
