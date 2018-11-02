package com.apps.gamehoundgames.frozasimpletuning;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

/**
 * Object containng min,max and result fields for the of a tuning entry.
 */
public class TuningEntry {

    protected InputField[] fields;
    protected ResultDelegate resultField;

    /**
     *
     * @param exitTextFields: fields [min, max, result];
     */
    public TuningEntry(EditText[] exitTextFields){
        this.fields = this.makeInputFields(new EditText[]{exitTextFields[0], exitTextFields[1]});

        if(exitTextFields[2] != null) {
            this.resultField = new ResultDelegate(
                    exitTextFields[2],
                    MainActivity.WeightField.GetSelf(),
                    this.fields[0].GetSelf(),
                    this.fields[1].GetSelf());
        }//if

        this.configureReboundInputs();
        this.subscribeToWeightField();
    }//ctor


    /**
     * Create InputField(a custom class) objects from EditText fields.
     * Will be used for onTextChanged event delegation.
     *
     * @param fields: array of fields to create InputFields from.
     * @return: array of InputFields created from passed "fields".
     */
    protected InputField[] makeInputFields(EditText[] fields){
        InputField[] result = new InputField[fields.length];
        for(int i = 0; i < fields.length; i++){
            if(fields[i] == null){
                result[i] = null;
                continue;
            }
            result[i] = new InputField(fields[i]);
        }//for

        return result;
    }//makeInputFields


    protected void subscribeToWeightField(){
        MainActivity.WeightField.GetDelegate().register(this.resultField);

        MainActivity.WeightField.GetSelf().addTextChangedListener(MainActivity.WeightField.GetDelegate());
    }

    /**
     *  Set event delegation on addTextChange for the rebound inputs, where Result field will be
     * notified whenever min and max input is changed.
     * @return: a listening field that was created to listen for "fields" changes.
     */
    private void configureReboundInputs(){
        if(this.resultField == null) //sometimes, result is not set, but min and max are
            return;

        for(int i = 0; i < this.fields.length; i++){
            if(fields[i] == null)
                continue;

            InputListener fieldListener = new InputListener(fields[i].GetSelf());
            fieldListener.register(this.resultField);
            fields[i].SetDelegate(fieldListener);

//            fields[i].GetSelf().addTextChangedListener(fieldListener);
            fields[i].GetSelf().setOnFocusChangeListener(fieldListener);
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
