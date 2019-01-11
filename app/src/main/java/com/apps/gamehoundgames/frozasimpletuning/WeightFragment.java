package com.apps.gamehoundgames.frozasimpletuning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.gamehoundgames.frozasimpletuning.InputFields.EventDelegate;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputField;
import com.apps.gamehoundgames.frozasimpletuning.InputFields.InputListener;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;


public class WeightFragment extends BasePageActivity {
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        this.theView  = inflater.inflate(R.layout.fragment_weight, container, false);
        this.updateWeightField(theView);

        this.UpdateDscText();
        this.InitHelpSwitchAction();

        return this.theView;
    }//onCreateView

    @Override
    public int GetHelpStringId() {
        return R.string.WeightDistroDsc;
    }


    protected void updateWeightField(View view){
        EditText weightEditText = view.findViewById(R.id.WeightInput);
        EventDelegate weightField = new InputField(weightEditText);
        InputListener weightListener = new InputListener(weightField.GetSelf(), "Weight");
        weightField.SetDelegate(weightListener);
        weightField.GetSelf().setOnFocusChangeListener(weightListener);
        weightField.GetSelf().addTextChangedListener(weightListener);

        String weightStr = CommonActions.GetPrefWeight(this.getActivity());
        weightField.GetSelf().setText(weightStr);

        MainActivity.SetWeightField(weightField);
    }//updateWeightField

    @Override
    public final int GetFragmentId(){ return R.layout.fragment_weight; }

    @Override
    public int[] GetFrontAxisId() { //not needed here for
        return new int[0];
    }

    @Override
    public int[] GetRearAxisId() {
        return new int[0];
    } //not needed here

}//class
