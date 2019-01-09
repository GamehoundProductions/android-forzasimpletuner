package com.apps.gamehoundgames.frozasimpletuning.InputFields;

import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;

import java.util.ArrayList;
import java.util.List;


public class InputListener implements Delegate, TextWatcher, View.OnFocusChangeListener {

    private List<EventDelegate> delegates;
    private  final Object MUTEX = new Object();
    private boolean  hasChanged = false;
    private EditText itself;
    private String prefName;
//    private SharedPreferences sharedPrefs;
//    private String sharedProp;


    public InputListener(EditText itselfField){
        this.delegates = new ArrayList<>();
        this.itself = itselfField;

        String inputIdStr = Integer.toString(this.itself.getId());
        this.prefName = inputIdStr;
    }//ctor


    public InputListener(EditText itselfField, String prefName){
        this(itselfField);
        this.prefName = prefName;
    }//ctor


    @Override
    public void register(EventDelegate obj) {
        if(obj == null)
            return;

        synchronized (this.MUTEX){
            if(!this.delegates.contains(obj))
                this.delegates.add(obj);
        }//sync
    }//register


    @Override
    public void unregister(EventDelegate obj) {
        synchronized (this.MUTEX){
            this.delegates.remove(obj);
        }//sync
    }//unregister


    @Override
    public void callback() {
        SharedPreferences prefs = CommonActions.GetSharedPrefs(null);
        if(prefs != null) {
            prefs.edit().putString(this.prefName, itself.getText().toString()).apply();
            String wght = prefs.getString("Weight", "nothing");
        }

        //Capture current state of this.delegates list so that it can start notification routine and
        //not worry about delegate state change in the middle of the process.
        List<EventDelegate> currentDelegates;
        synchronized (this.MUTEX){
            if(!this.hasChanged)
                return;
            currentDelegates = new ArrayList<>(this.delegates);
            this.hasChanged = true;
        }//sync
        for(int i = 0; i < currentDelegates.size(); i++){
            currentDelegates.get(i).update(this.itself);
        }//for
    }//callback


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }


    @Override
    public void afterTextChanged(Editable s) {
        this.hasChanged = true;
        this.callback();
    }//afterTextChanged


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            this.hasChanged = true;
            this.callback();
        }
    }
}//class