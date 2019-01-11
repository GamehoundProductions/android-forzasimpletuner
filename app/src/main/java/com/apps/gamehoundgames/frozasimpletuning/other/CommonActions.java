package com.apps.gamehoundgames.frozasimpletuning.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.apps.gamehoundgames.frozasimpletuning.AxisControls.TuningEntry;

public class CommonActions {

    private static SharedPreferences Prefs;

    public static SharedPreferences GetSharedPrefs(Activity theActivity){
        if(theActivity == null)
            return Prefs;
        if(Prefs == null)
            Prefs = theActivity.getSharedPreferences("MainPref", Context.MODE_PRIVATE);
        return Prefs;
    }//GetSharedPrefs


    /**
     * Set Rear min and max rebound values to be the same as Front min-max on their change.
     * Note: Front min-max is independant from Rear min-max.
     */
    public static void configureRearMinMaxDelegate(TuningEntry field, TuningEntry delegate){
        try {
            delegate.GetMin().GetDelegate().register(field.GetMin());
            delegate.GetMax().GetDelegate().register(field.GetMax());
        }catch(Exception e){
            return;
        }
    }//configureRearMinMaxDelegate


    public static void HideKeyboard(View view, Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (Exception e){
            return;
        }
    }//hideKeyboard


    /**
     * Pass array of field IDs and get an array of EditText.
     * @param fieldsId: View IDs to get EditText for.
     * @param theActivity: your current activity (e.g. "this" object).
     * @return: EditText[] for each fieldsId, indices of which assigned with respect to fieldsID.
     */
    public static EditText[] getFieldsById(int[] fieldsId, Activity theActivity){
        EditText[] result = new EditText[fieldsId.length];
        for(int i = 0; i < fieldsId.length; i++){
            if(fieldsId[i] == -1) {
                result[i] = null;
                continue;
            }

            EditText input = theActivity.findViewById(fieldsId[i]);
            result[i] = input;
        }//for
        return result;
    }//getFrontReboundFields


    public static EditText[] getFieldsById(int[] fieldsId, View theView){
        EditText[] result = new EditText[fieldsId.length];
        for(int i = 0; i < fieldsId.length; i++){
            if(fieldsId[i] == -1) {
                result[i] = null;
                continue;
            }
            EditText input = theView.findViewById(fieldsId[i]);
            result[i] = input;
        }//for
        return result;
    }//getFrontReboundFields


    /**
     *  Converts string value to a float.
     * @param yourStrValue: string value to be parsed.
     * @return: float value on success. -1 if invalid value.
     */
    public static float StringToFloat(String yourStrValue){
        try {
            return Float.parseFloat(yourStrValue);
        }catch (NumberFormatException err){
            return -1;
        }
    }//StringToFloat


    public static String GetPrefWeight(Activity yourActivity){
        return GetSharedPrefs(yourActivity).getString("Weight", "-1");
    }//GetPrefWeight


    public static boolean SetPrefValue(String prefName, String prefValue){
        SharedPreferences prefs = GetSharedPrefs(null);
        if(prefs == null)
            return false;
        prefs.edit().putString(prefName, prefValue).apply();
        Log.d("SetPrefValue",  String.format("Setting %s with value %s", prefName, prefValue));
        return true;
    }//SetPrefValue


    public static String GetPrefValue(String prefName){
        SharedPreferences prefs = GetSharedPrefs(null);
        if(prefs == null)
            return "0";
        String value = prefs.getString(prefName, "0");
        Log.d("GetPrefValue",  String.format("Getting %s with value %s", prefName, value));
        return value;
    }

}//class
