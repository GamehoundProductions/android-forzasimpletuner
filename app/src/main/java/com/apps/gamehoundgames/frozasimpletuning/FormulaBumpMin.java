package com.apps.gamehoundgames.frozasimpletuning;

public class FormulaBumpMin extends Formula {

    /**
     *
     * @param frontWeight: not really a front weight! Instead - a value to calculate Min value from.
     * @param min: set to -1
     * @param max: set to -1
     * @return
     */
    @Override
    public float calculate(float frontWeight, float min, float max) {
        return frontWeight * 0.50f;
    }//calculate

}//class
