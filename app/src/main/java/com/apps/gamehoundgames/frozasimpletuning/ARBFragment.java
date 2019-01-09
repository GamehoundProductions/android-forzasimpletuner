package com.apps.gamehoundgames.frozasimpletuning;


public class ARBFragment extends BasePageActivity {
    @Override
    public String GetTag() { return "AntiRollFragment"; }

    @Override
    public int GetFragmentId(){ return R.layout.fragment_arb; }

    @Override
    public int[] GetFrontAxisId(){
        return new int[]{
                R.id.RollBarsFrontMin,
                R.id.RollBarsFrontMax,
                R.id.RollBarsFrontResult,
        };
    }//GetReboundFrontId

    @Override
    public int[] GetRearAxisId(){
        return new int[]{
                R.id.RollBarsRearMin,
                R.id.RollBarsRearMax,
                R.id.RollBarsRearResult
        };
    }//GetReboundRearId

    @Override
    public int GetHelpStringId() {
        return R.string.AntiRollDsc;
    }

}//class
