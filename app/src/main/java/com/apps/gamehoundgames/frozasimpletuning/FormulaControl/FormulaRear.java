package com.apps.gamehoundgames.frozasimpletuning.FormulaControl;

import com.apps.gamehoundgames.frozasimpletuning.FormulaControl.Formula;

public class FormulaRear extends Formula {
    @Override
    public float calculate(float frontWeight, float min, float max){
        float weight = 100 - frontWeight;
        return ((max - min) * (weight / 100)) + min;
    }//calculate
}//class
