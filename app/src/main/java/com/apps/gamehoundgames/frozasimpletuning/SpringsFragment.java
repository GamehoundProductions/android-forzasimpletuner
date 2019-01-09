package com.apps.gamehoundgames.frozasimpletuning;


public class SpringsFragment extends BasePageActivity {

    @Override
    public int GetHelpStringId() {
        return R.string.SpringsDsc;
    }

    @Override
    public String GetTag() { return "SpringsFragment"; }

    @Override
    public int GetFragmentId(){ return R.layout.fragment_springs; }

    @Override
    public int[] GetFrontAxisId(){
        return new int[]{
                R.id.SpringsFrontMin,
                R.id.SpringsFrontMax,
                R.id.SpringsFrontResult,
        };
    }//GetReboundFrontId

    @Override
    public int[] GetRearAxisId(){
        return new int[]{
                R.id.SpringsRearMin,
                R.id.SpringsRearMax,
                R.id.SpringsRearResult
        };
    }//GetReboundRearId
}//class
