package com.apps.gamehoundgames.frozasimpletuning.InputFields;

import android.widget.EditText;


public class InputField implements EventDelegate {

    private EditText itself;
    private InputListener delegate;


    public InputField(EditText itselfField){
        this.itself = itselfField;
    }//ctor


    @Override
    public void update(EditText whoIsCalling) {
    }//update

    @Override
    public void SetDelegate(InputListener newDelegate) {
        this.delegate = newDelegate;
    }//SetDelegate


    @Override
    public EditText GetSelf() { return this.itself; }


    @Override
    public InputListener GetDelegate() {
        return this.delegate;
    }

}//class
