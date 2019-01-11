package com.apps.gamehoundgames.frozasimpletuning.AxisControls;

import android.content.SharedPreferences;
import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.Formula;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputField;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputListener;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;

/**
 * Object containng min,max and result fields for the of a tuning entry.
 */
public class TuningEntry {

    protected InputField[] fields;
    protected ResultDelegate resultField;
//    protected Activity currentActivity;

    /**
     *
     * @param editTextFields: fields [min, max, result];
     */
    public TuningEntry(EditText[] editTextFields){
        this.init(editTextFields);
    }//ctor


    protected void init(EditText[] editTextFields){
        this.fields = this.makeInputFields(new EditText[]{editTextFields[0], editTextFields[1]});

        if(editTextFields[2] != null) {
            this.resultField = new ResultDelegate(
                    editTextFields[2],
                    null,
                    this.fields[0].GetSelf(),
                    this.fields[1].GetSelf());

            String resultIdStr = Integer.toString(this.resultField.GetSelf().getId());
            String fieldValue = CommonActions.GetPrefValue(resultIdStr);
            this.resultField.GetSelf().setText(fieldValue);
        }//if

        this.configureReboundInputs();
//        this.subscribeToWeightField();
    }//init


    /**
     * Create InputField(a custom class) objects from EditText fields.
     * Will be used for onTextChanged event delegation.
     *
     * @param fields: array of fields to create InputFields from.
     * @return: array of InputFields created from passed "fields".
     */
    protected InputField[] makeInputFields(EditText[] fields){
        SharedPreferences prefs = CommonActions.GetSharedPrefs(null);
        InputField[] result = new InputField[fields.length];
        for(int i = 0; i < fields.length; i++){
            if(fields[i] == null){
                result[i] = null;
                continue;
            }
            result[i] = new InputField(fields[i]);

            String inputIdStr = Integer.toString(result[i].GetSelf().getId());
            result[i].GetSelf().setText(CommonActions.GetPrefValue(inputIdStr));
        }//for

        return result;
    }//makeInputFields


    protected void subscribeToWeightField(){
//        MainActivity.WeightField.GetDelegate().register(this.resultField);

//        MainActivity.WeightField.GetSelf().addTextChangedListener(MainActivity.WeightField.GetDelegate());
    }

    /**
     *  Set event delegation on addTextChange for the rebound inputs, where Result field will be
     * notified whenever min and max input is changed.
     * @return: a listening field that was created to listen for "fields" changes.
     */
    protected void configureReboundInputs(){
        if(this.resultField == null) //sometimes, result is not set, but min and max are
            return;

        for(int i = 0; i < this.fields.length; i++){
            if(fields[i] == null)
                continue;

            InputListener fieldListener = new InputListener(fields[i].GetSelf());
            fieldListener.register(this.resultField);
            fields[i].SetDelegate(fieldListener);

            fields[i].GetSelf().setOnFocusChangeListener(fieldListener);
            fields[i].GetSelf().addTextChangedListener(fieldListener);
        }//for
    }//configureReboundInputs


    public ResultDelegate GetResultField() { return this.resultField; }


    public void SetFormula(Formula newFormula) {
        if(newFormula == null)
            return;
        this.resultField.SetFormula(newFormula);
    }//SetFormula


    public void SetResultField(ResultDelegate newResultField) {
        this.resultField = newResultField;
    }


    public InputField GetMin() { return this.fields[0]; }

    public InputField GetMax() { return this.fields[1]; }

    public InputField[] GetMinMax() { return this.fields; }

}//class
