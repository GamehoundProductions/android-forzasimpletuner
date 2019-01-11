package com.apps.gamehoundgames.frozasimpletuning.FormulaControl;

public class Formula {

    public float calculate(float frontWeight, float min, float max){
        return ((max - min) * (frontWeight / 100)) + min;
    }//calculate

}//class
