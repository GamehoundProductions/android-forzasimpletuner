package com.apps.gamehoundgames.frozasimpletuning;

import com.apps.gamehoundgames.frozasimpletuning.AxisControls.BumpStiffnessEntry;
import com.apps.gamehoundgames.frozasimpletuning.AxisControls.TuningEntry;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;


public class DampingFragment extends BasePageActivity {

    private TuningEntry bumpFront, bumpRear;

    @Override
    protected void initFieldEntries(){
        super.initFieldEntries();

        //Bump Stiffness
        int[] bumpFrontId = this.GetBumpFrontId();
        int[] bumpRearId = this.GetBumpRearId();

        this.bumpFront = new BumpStiffnessEntry(CommonActions.getFieldsById(bumpFrontId, this.theView));
        this.bumpFront.SetResultField(this.frontAxis.GetResultField());

        this.bumpRear = new BumpStiffnessEntry(CommonActions.getFieldsById(bumpRearId, this.theView));
        this.bumpRear.SetResultField(this.rearAxis.GetResultField());
        this.bumpRear.SetFormula(this.GetRearFormula());
    }//initFieldEntries


    @Override
    public int GetHelpStringId() {
        return R.string.ReboundDsc;
    }


    @Override
    public String GetTag() { return "ActivityFragment"; }

    @Override
    public int GetFragmentId(){ return R.layout.fragment_damping; }

    @Override
    public int[] GetFrontAxisId(){
        return new int[]{
                R.id.ReboundFrntMin,
                R.id.ReboundFrntMax,
                R.id.ReboundFrontResult,
        };
    }//GetReboundFrontId

    @Override
    public int[] GetRearAxisId(){
        return new int[]{
                R.id.ReboundRearMin,
                R.id.ReboundRearMax,
                R.id.ReboundRearResult
        };
    }//GetReboundRearId

    public int[] GetBumpFrontId(){
        return new int[]{
                R.id.BumpFrontMin,
                R.id.BumpFrontMax,
                -1
        };
    }//GetBumpFrontId

    public int[] GetBumpRearId(){
        return new int[]{
                R.id.BumpRearMin,
                R.id.BumpRearMax,
                -1
        };
    }//GetBumpFrontId

}//class
