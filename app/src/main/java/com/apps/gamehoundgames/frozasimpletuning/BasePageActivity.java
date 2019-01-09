package com.apps.gamehoundgames.frozasimpletuning;

import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.Formula;
import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.FormulaRear;
import com.apps.gamehoundgames.frozasimpletuning.AxisControls.RearTuningEntry;
import com.apps.gamehoundgames.frozasimpletuning.AxisControls.TuningEntry;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;


public abstract class BasePageActivity extends Fragment {

    protected TuningEntry frontAxis, rearAxis;
    protected View theView;

    private FormulaRear rearFormula;
    private EditText helpTextField;
    private Switch helpSwitch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.theView = inflater.inflate(GetFragmentId(), container, false);
        this.initFieldEntries();
        UpdateDscText();
        InitHelpSwitchAction();
        RefreshResults();
        return this.theView;
    }//onCreateView


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }//onViewCreated


    public void UpdateDscText(){
        if(this.helpTextField == null)
            this.helpTextField = GetHelpEditText();
        if(this.helpTextField != null)
            this.helpTextField.setText(getString(GetHelpStringId()));
    }//UpdateDscText


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        int helpId = this.GetHelpSwitchId();
    }


    protected void initFieldEntries(){
        int[] frontId = this.GetFrontAxisId();
        int[] rearId = this.GetRearAxisId();

        EditText[] frontAxisEditText, rearAxisEditText;
        //FrontAxis EditText fields
        if(frontId != null) {
            frontAxisEditText = CommonActions.getFieldsById(frontId, this.theView);
            this.frontAxis = new TuningEntry(frontAxisEditText);
        }
        if(rearId != null) {
            rearAxisEditText = CommonActions.getFieldsById(rearId, this.theView);
            this.rearAxis = new RearTuningEntry(rearAxisEditText);
            this.rearAxis.SetFormula(this.GetRearFormula());
        }

        CommonActions.configureRearMinMaxDelegate(this.rearAxis, this.frontAxis);
    }//initFieldEntries


    public void RefreshResults(){
        try{
            this.frontAxis.GetResultField().CalculateResult();
            this.rearAxis.GetResultField().CalculateResult();
        }catch (Exception e){
            return;
        }
    }//RefreshResults


    public void InitHelpSwitchAction(){
        this.helpSwitch = this.theView.findViewById(this.GetHelpSwitchId());
        if(this.helpSwitch == null)
            return;

        final String viewId = GetTag();

        helpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isCool = CommonActions.SetPrefValue(viewId + "_helpState",  Boolean.toString(isChecked));
                if(isChecked){
                    GetHelpEditText().setVisibility(View.VISIBLE);
                }else{
                    GetHelpEditText().setVisibility(View.INVISIBLE);
                }
            }
        });

        String switchState = CommonActions.GetPrefValue(String.format("%s_helpState", viewId));
        boolean isChecked = false;
        if(switchState.equals("true"))
            isChecked = true;

        this.helpSwitch.setChecked(isChecked);
        if(isChecked)
            GetHelpEditText().setVisibility(View.VISIBLE);
        else
            GetHelpEditText().setVisibility(View.INVISIBLE);
    }//helpSwitch


    /* **************** GETTERS **************** */

    public Formula GetRearFormula(){
        if(this.rearFormula == null)
            this.rearFormula = new FormulaRear();
        return this.rearFormula;
    }//GetRearFormula


    public int GetEditTextId() { return R.id.helpTextField; }

    public EditText GetHelpEditText() {
        return this.theView.findViewById(this.GetEditTextId());
    }

    public int GetHelpSwitchId() { return R.id.helpSwitch; }

    public String GetTag() {
        int id = GetFragmentId();
        if(id == -1)
            return "no_view_id";
        else
            return this.theView.getResources().getResourceName(id);
    }//GetTag

    public abstract int GetHelpStringId();

    public abstract int GetFragmentId();

    public abstract int[] GetFrontAxisId();

    public abstract int[] GetRearAxisId();


    public TuningEntry GetFrontAxis() { return this.frontAxis; }
    public TuningEntry GetRearAxis() { return this.rearAxis; }

}//class
