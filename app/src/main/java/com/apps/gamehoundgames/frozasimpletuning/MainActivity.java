package com.apps.gamehoundgames.frozasimpletuning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    public static EventDelegate WeightField;

    private FormulaRear formulaRear = new FormulaRear();
    private FormulaBumpMax formulaBumpMax = new FormulaBumpMax();
    private EventDelegate[] reboundFrontFields, reboundRearFields; // [min, max]
    private EventDelegate[] springsFrontFields, springsRearFields;
    private ResultDelegate[] reboundResults; //[front, rear]
    private ResultDelegate[] bumpStiffnessFront = new ResultDelegate[2];
    private ResultDelegate[] bumpStiffnessRear = new ResultDelegate[2];

    private TuningEntry reboundFront, reboundRear;
    private TuningEntry bumpFront, bumpRear;
    private TuningEntry springsFront, springsRear;
    private TuningEntry rollBarsFront, rollBarsRear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText weightEditText = findViewById(R.id.WeightInput);
        this.WeightField = new InputField(weightEditText);
        InputListener weightListener = new InputListener(this.WeightField.GetSelf());
        this.WeightField.SetDelegate(weightListener);

        int[] reboundFrontId = this.GetReboundFrontId();
        int[] reboundRearId = this.GetReboundRearId();

        //Rebound Stiffness
        this.reboundFront = new TuningEntry(this.getFieldsById(reboundFrontId));
        this.reboundRear = new RearTunningEntry(this.getFieldsById(reboundRearId));
        this.reboundRear.SetFormula(this.formulaRear);

        this.configureRearMinMaxDelegate(this.reboundRear, this.reboundFront);

        //Bump Stiffness
        int[] bumpFrontId = this.GetBumpFrontId();
        int[] bumpRearId = this.GetBumpRearId();

        this.bumpFront = new BumpStiffnessEntry(this.getFieldsById(bumpFrontId));
        this.bumpFront.SetResultField(this.reboundFront.GetResultField());

        this.bumpRear = new BumpStiffnessEntry(this.getFieldsById(bumpRearId));
        this.bumpRear.SetResultField(this.reboundRear.GetResultField());

        //Springs
        int[] springsFrontId = this.GetSpringsFrontId();
        int[] springsRearId = this.GetSpringsRearId();

        this.springsFront = new TuningEntry(this.getFieldsById(springsFrontId));
        this.springsRear = new RearTunningEntry(this.getFieldsById(springsRearId));
        this.springsRear.SetFormula(this.formulaRear);

        this.configureRearMinMaxDelegate(this.springsRear, this.springsFront);

        //Anti-Roll Bars
        int[] barsFrontId = this.GetRollBarsFrontId();
        int[] barsRearId = this.GetRollBarsRearId();

        this.rollBarsFront = new TuningEntry(this.getFieldsById(barsFrontId));
        this.rollBarsRear = new RearTunningEntry(this.getFieldsById(barsRearId));
        this.rollBarsRear.SetFormula(this.formulaRear);

        this.configureRearMinMaxDelegate(this.rollBarsRear, this.rollBarsFront);

//        this.rollBarsFront.GetResultField().update(this.WeightField.GetSelf());
        this.rollBarsRear.GetResultField().update(this.WeightField.GetSelf());
    }//onCreate


    private void createTuningEntry(int[] frontId, int[] rearId, ResultDelegate frontResult, ResultDelegate rearResult){
        EditText[] reboundFrontField = getFieldsById(frontId);
        EditText[] reboundRearField = getFieldsById(rearId);

        EventDelegate[] frontFields = this.makeInputFields(reboundFrontField);
        EventDelegate[] rearFields = this.makeInputFieldCopyValues(reboundRearField);
    }//createTuningEntry


    public EditText[] getFieldsById(int[] fieldsId){
        EditText[] result = new EditText[fieldsId.length];
        for(int i = 0; i < fieldsId.length; i++){
            if(fieldsId[i] == -1) {
                result[i] = null;
                continue;
            }

            EditText input = findViewById(fieldsId[i]);
            result[i] = input;
        }//for
        return result;
    }//getFrontReboundFields


    /**
     * Create InputField(a custom class) objects from EditText fields.
     * Will be used for onTextChanged event delegation.
     *
     * @param fields: array of fields to create InputFields from.
     * @return: array of InputFields created from passed "fields".
     */
    private InputField[] makeInputFields(EditText[] fields){
        InputField[] result = new InputField[fields.length];
        for(int i = 0; i < fields.length; i++){
            if(fields[i] == null){ //should not happened, but safety net to prevent crashes
                result[i] = null;
                continue;
            }
            result[i] = new InputField(fields[i]);
        }//for

        return result;
    }//makeInputFields


    /**
     *  Same as makeInputFields, except using InputFieldCopyValue class.
     *
     * @param fields: array or EditText to create objects from.
     * @return: array of objects based of passed fields.
     */
    private InputFieldCopyValue[] makeInputFieldCopyValues(EditText[] fields){
        //FIXME: This is a copy of makeInputFields function. Need a better way to do this...
        InputFieldCopyValue[] result = new InputFieldCopyValue[fields.length];
        for(int i = 0; i < fields.length; i++){
            if(fields[i] == null){ //should not happened, but safety net to prevent crashes
                result[i] = null;
                continue;
            }
            result[i] = new InputFieldCopyValue(fields[i]);
        }//for

        return result;
    }//makeInputFields


    /**
     *  Set event delegation on addTextChange for the rebound inputs, where Result field will be
     * notified whenever min and max input is changed.
     * @param resultId: <R.id...> id of the input which will be listening to min/max changes.
     * @param fields: fields that will notify resultId field on change.
     * @return: a listening field that was created to listen for "fields" changes.
     */
    private ResultDelegate configureReboundInputs(int resultId, EventDelegate[] fields){
        EditText resultField = findViewById(resultId);
        ResultDelegate resultEvent = new ResultDelegate(
                resultField,
                this.WeightField.GetSelf(),
                fields[0].GetSelf(),
                fields[1].GetSelf()
                );

        for(int i = 0; i < fields.length; i++){
            if(fields[i] == null)
                continue;

            InputListener fieldListener = new InputListener(fields[i].GetSelf());
            fieldListener.register(resultEvent);
            fields[i].SetDelegate(fieldListener);

            fields[i].GetSelf().addTextChangedListener(fieldListener);
        }//for

        return resultEvent;
    }//configureReboundInputs


    private ResultDelegate[] configureBumpStiffness(EditText[] fields, ResultDelegate resultFields){
        ResultDelegate[] minMax = new ResultDelegate[fields.length];
        for(int i = 0; i < fields.length; i++){
            EditText min = null;
            EditText max = null;

            if(i == 0)  min = this.WeightField.GetSelf();
            else max = this.WeightField.GetSelf();

            minMax[i] = new ResultDelegate( fields[i], null, min, max);
            minMax[i].SetFormula(this.formulaBumpMax);
            resultFields.AddListener(minMax[i]);
        }//for
        return minMax;
    }//configureBumpStiffness


    /**
     * Set Rear min and max rebound values to be the same as Front min-max on their change.
     * Note: Front min-max is independant from Rear min-max.
     */
    private void configureRearMinMaxDelegate(TuningEntry field, TuningEntry delegate){
        delegate.GetMin().GetDelegate().register(field.GetMin());
        delegate.GetMax().GetDelegate().register(field.GetMax());
//        this.reboundFrontFields[0].GetDelegate().register(
//                this.reboundRearFields[0]
//        );
//
//        this.reboundFrontFields[1].GetDelegate().register(
//                this.reboundRearFields[1]
//        );
    }//configureRearMinMaxDelegate


    /**
     *  There are several fields (e.g. all result fields) that are listening to Weight field changes.
     * Thus, need to subscribe those fields to weight's input changes.
     */
    private void configureWeightDelegates(){
        EventDelegate[] fieldsToDelegateTo = new EventDelegate[]{
                this.reboundFront.GetResultField()
//                this.reboundResults[0],
//                this.reboundResults[1],
        };

        for(int i = 0; i < fieldsToDelegateTo.length; i++){
            this.WeightField.GetDelegate().register(fieldsToDelegateTo[i]);
        }//for

        this.WeightField.GetSelf().addTextChangedListener(this.WeightField.GetDelegate());
    }//configureWeightDelegates


    public int[] GetReboundFrontId(){
        return new int[]{
                R.id.ReboundFrntMin,
                R.id.ReboundFrntMax,
                R.id.ReboundFrontResult,
        };
    }//GetReboundFrontId

    public int[] GetReboundRearId(){
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

    public int[] GetSpringsFrontId(){
        return new int[]{
                R.id.SpringsFrontMin,
                R.id.SpringsFrontMax,
                R.id.SpringsFrontResult
        };
    }//GetSpringsRearId

    public int[] GetSpringsRearId(){
        return new int[]{
            R.id.SpringsRearMin,
            R.id.SpringsRearMax,
            R.id.SpringsRearResult
        };
    }//GetSpringsRearId

    public int[] GetRollBarsFrontId(){
        return new int[]{
            R.id.RollBarsFrontMin,
            R.id.RollBarsFrontMax,
            R.id.RollBarsFrontResult

        };
    }//GetRollBarsFrontId

    public int[] GetRollBarsRearId(){
        return new int[]{
                R.id.RollBarsRearMin,
                R.id.RollBarsRearMax,
                R.id.RollBarsRearResult
        };
    }//GetRollBarsFrontId

}//class
