package com.apps.gamehoundgames.frozasimpletuning.AxisControls;

import android.content.SharedPreferences;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.Formula;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.EventDelegate;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputListener;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultDelegate implements EventDelegate {

    private Formula formula;
    private EditText itselfField;
    private EditText weightField, minField, maxField;
    private InputListener delegate;
    private List<EventDelegate> otherListeners;
    private String prefName;
//    private Activity currActivity;


    public ResultDelegate(EditText itself, EditText weight, EditText min, EditText max){
        this.itselfField = itself;
        this.weightField = weight;
        this.minField = min;
        this.maxField = max;

        this.formula = new Formula();
        this.otherListeners = new ArrayList<>();

        String inputIdStr = Integer.toString(this.itselfField.getId());
        this.prefName = inputIdStr;
    }//ctor


    public void CalculateResult(){
        float weight;
        String weightStr = CommonActions.GetPrefWeight(null);

        weight = CommonActions.StringToFloat(weightStr);

        float min = GetValue(this.minField);
        float max = GetValue(this.maxField);

        float result = this.formula.calculate(weight, min, max);
        DecimalFormat df = new DecimalFormat("#.00");
        this.itselfField.setText(String.valueOf(df.format(result)));

        SharedPreferences prefs = CommonActions.GetSharedPrefs(null);
        if(prefs != null) {
            prefs.edit().putString(this.prefName, this.itselfField.getText().toString()).apply();
        }
    }//CalculateResult


    /*
     * Get fields to calculate result by using a Formula, whenever delegated input has changed (e.g
     * weight, min or max fields).
    */
    @Override
    public void update(EditText whoIsCalling) {
        this.CalculateResult();
        NotifyListeners();
    }//update


    public void NotifyListeners(){
        if(this.otherListeners == null)
            return;
        for(int i = 0; i < this.otherListeners.size(); i++){
            this.otherListeners.get(i).update(GetSelf());
        }//for
    }//NotifyListeners


    @Override
    public void SetDelegate(InputListener newDelegate) {
        this.delegate = newDelegate;
    }


    public float GetValue(EditText field){
        if(field == null)
            return -1;
        try {
            return Float.parseFloat(field.getText().toString());
        }catch (NumberFormatException err){
            return -1;
        }
    }//GetValue


    @Override
    public EditText GetSelf() { return this.itselfField; }


    @Override
    public InputListener GetDelegate() {
        return this.delegate;
    }


    public void SetFormula(Formula newFormula){ this.formula = newFormula; }


    public  void AddListener(EventDelegate listener) {
        if(this.otherListeners.contains(listener))
            return;
        this.otherListeners.add(listener);
    }//AddListener


//    public void SetActivity(Activity currActivity){
//        this.currActivity = currActivity;
//    }

}//class
