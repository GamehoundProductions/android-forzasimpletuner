package com.apps.gamehoundgames.frozasimpletuning;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.apps.gamehoundgames.frozasimpletuning.InputFields.EventDelegate;
import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;


public class MainActivity extends AppCompatActivity {
    public static EventDelegate WeightField;

//    private FormulaBumpMax formulaBumpMax = new FormulaBumpMax();
//    private EventDelegate[] reboundFrontFields, reboundRearFields; // [min, max]
//    private EventDelegate[] springsFrontFields, springsRearFields;
//    private ResultDelegate[] reboundResults; //[front, rear]
//    private ResultDelegate[] bumpStiffnessFront = new ResultDelegate[2];
//    private ResultDelegate[] bumpStiffnessRear = new ResultDelegate[2];
//
//    private TuningEntry reboundFront, reboundRear;
//    private TuningEntry bumpFront, bumpRear;
//    private TuningEntry springsFront, springsRear;
//    private TuningEntry rollBarsFront, rollBarsRear;
//
//    private Button nextButton;
//    private SharedPreferences sharedPrefs;

    private BottomNavigationView navbar;
    private FragmentPager fragPager;
    private ViewPager pager;
    private Activity thisActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.thisActivity = this;

        CommonActions.GetSharedPrefs(this); //to initiate Prefs state.

        this.pager = findViewById(R.id.pager);
        setupFragments();
        this.pager.setAdapter(this.fragPager);
        setupNavbar();

        this.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int i) {
                navbar.setSelectedItemId(GetNavbarButtonsIds()[i]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }//onCreate


    private void setupFragments() {
        if(this.fragPager == null)
            this.fragPager = new FragmentPager(getSupportFragmentManager());

        this.fragPager.addFragment(new WeightFragment());
        this.fragPager.addFragment(new DampingFragment());
        this.fragPager.addFragment(new SpringsFragment());
        this.fragPager.addFragment(new ARBFragment());
    }//setupFragments


    public void setupNavbar(){
        if(this.navbar == null){
            this.navbar = findViewById(R.id.navigation);
        }

        this.navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_weight:
                        fragPager.SetCurrentFragment(pager, 0);
                        break;
                    case R.id.nav_damping:
                        fragPager.SetCurrentFragment(pager, 1);
                        break;
                    case R.id.nav_springs:
                        fragPager.SetCurrentFragment(pager, 2);
                        break;
                    case R.id.nav_arb:
                        fragPager.SetCurrentFragment(pager, 3);
                        break;
                }
                return true;
            }//onNavigationItemSelected
        });

        pager.setCurrentItem(0);
    }//setupNavbar


    public static void SetWeightField(EventDelegate theField){
        if(theField != null)
            WeightField = theField;
    }//SetWeightField


    public int[] GetNavbarButtonsIds() {
        return new int[]{
                R.id.nav_weight,
                R.id.nav_damping,
                R.id.nav_springs,
                R.id.nav_arb
        };
    }


//    private void initFieldEntries(){
//        int[] reboundFrontId = this.GetReboundFrontId();
//        int[] reboundRearId = this.GetReboundRearId();
//
//        //Rebound Stiffness
//        this.reboundFront = new TuningEntry(this.getFieldsById(reboundFrontId));
//        this.reboundRear = new RearTuningEntry(this.getFieldsById(reboundRearId));
//        this.reboundRear.SetFormula(this.formulaRear);
//
//        this.configureRearMinMaxDelegate(this.reboundRear, this.reboundFront);
//
//        //Bump Stiffness
//        int[] bumpFrontId = this.GetBumpFrontId();
//        int[] bumpRearId = this.GetBumpRearId();
//
//        this.bumpFront = new BumpStiffnessEntry(this.getFieldsById(bumpFrontId));
//        this.bumpFront.SetResultField(this.reboundFront.GetResultField());
//
//        this.bumpRear = new BumpStiffnessEntry(this.getFieldsById(bumpRearId));
//        this.bumpRear.SetResultField(this.reboundRear.GetResultField());
//
//        //Springs
//        int[] springsFrontId = this.GetSpringsFrontId();
//        int[] springsRearId = this.GetSpringsRearId();
//
//        this.springsFront = new TuningEntry(this.getFieldsById(springsFrontId));
//        this.springsRear = new RearTuningEntry(this.getFieldsById(springsRearId));
//        this.springsRear.SetFormula(this.formulaRear);
//
//        this.configureRearMinMaxDelegate(this.springsRear, this.springsFront);
//
//        //Anti-Roll Bars
//        int[] barsFrontId = this.GetRollBarsFrontId();
//        int[] barsRearId = this.GetRollBarsRearId();
//
//        this.rollBarsFront = new TuningEntry(this.getFieldsById(barsFrontId));
//        this.rollBarsFront.GetResultField().GetSelf().setText("33.00");
//        this.rollBarsRear = new RearTuningEntry(this.getFieldsById(barsRearId));
//        this.rollBarsRear.SetFormula(this.formulaRear);
//
//        //REAR Roll Bars
//        this.configureRearMinMaxDelegate(this.rollBarsRear, this.rollBarsFront);
//
//        this.rollBarsRear.GetResultField().update(this.WeightField.GetSelf());
//    }//initFieldEntries
//
//
//    /**
//     * Make all headers text views clickable to display alert message with description string.
//     */
//    private void initInfoButtons(){
//        int[] ids = this.GetInfoId();
//        String headerText = getString(R.string.PopupMsgHeader);
//        for(int i = 0; i < ids.length; i++){
//            TextView infoView = findViewById(ids[i]);
//            InfoMsgClickListener clicker = new InfoMsgClickListener(
//                    headerText,
//                    infoView.getContentDescription().toString(),
//                    getSupportFragmentManager()
//            );
//            infoView.setOnClickListener(clicker);
//        }//for
//    }//initInfoButtons
//
//
//    /**
//     * Pass array of field IDs and get an array of EditText.
//     * @param fieldsId: View IDs to get EditText for.
//     * @return: EditText[] for each fieldsId, indices of which assigned with respect to fieldsID.
//     */
//    public EditText[] getFieldsById(int[] fieldsId){
//        EditText[] result = new EditText[fieldsId.length];
//        for(int i = 0; i < fieldsId.length; i++){
//            if(fieldsId[i] == -1) {
//                result[i] = null;
//                continue;
//            }
//
//            EditText input = findViewById(fieldsId[i]);
//            result[i] = input;
//        }//for
//        return result;
//    }//getFrontReboundFields
//
//
//
//    /**
//     * Set Rear min and max rebound values to be the same as Front min-max on their change.
//     * Note: Front min-max is independant from Rear min-max.
//     */
//    private void configureRearMinMaxDelegate(TuningEntry field, TuningEntry delegate){
//        delegate.GetMin().GetDelegate().register(field.GetMin());
//        delegate.GetMax().GetDelegate().register(field.GetMax());
//    }//configureRearMinMaxDelegate
//
//    /* View Fields ID groups */
//
//    public int[] GetReboundFrontId(){
//        return new int[]{
//                R.id.ReboundFrntMin,
//                R.id.ReboundFrntMax,
//                R.id.ReboundFrontResult,
//        };
//    }//GetReboundFrontId
//
//    public int[] GetReboundRearId(){
//        return new int[]{
//                R.id.ReboundRearMin,
//                R.id.ReboundRearMax,
//                R.id.ReboundRearResult
//        };
//    }//GetReboundRearId
//
//    public int[] GetBumpFrontId(){
//        return new int[]{
//                R.id.BumpFrontMin,
//                R.id.BumpFrontMax,
//                -1
//        };
//    }//GetBumpFrontId
//
//    public int[] GetBumpRearId(){
//        return new int[]{
//                R.id.BumpRearMin,
//                R.id.BumpRearMax,
//                -1
//        };
//    }//GetBumpFrontId
//
//    public int[] GetSpringsFrontId(){
//        return new int[]{
//                R.id.SpringsFrontMin,
//                R.id.SpringsFrontMax,
//                R.id.SpringsFrontResult
//        };
//    }//GetSpringsRearId
//
//    public int[] GetSpringsRearId(){
//        return new int[]{
//            R.id.SpringsRearMin,
//            R.id.SpringsRearMax,
//            R.id.SpringsRearResult
//        };
//    }//GetSpringsRearId
//
//    public int[] GetRollBarsFrontId(){
//        return new int[]{
//            R.id.RollBarsFrontMin,
//            R.id.RollBarsFrontMax,
//            R.id.RollBarsFrontResult
//
//        };
//    }//GetRollBarsFrontId
//
//    public int[] GetRollBarsRearId(){
//        return new int[]{
//                R.id.RollBarsRearMin,
//                R.id.RollBarsRearMax,
//                R.id.RollBarsRearResult
//        };
//    }//GetRollBarsFrontId
//
//    public int[] GetInfoId(){
//        return new int[]{
//                R.id.WeightInfoBtn,
//                R.id.MinInfoBtn, R.id.MaxInfoBtn, R.id.EstimatedInfoBtn,
//                R.id.ReboundInfoBtn,
//                R.id.BumpInfoBtn,
//                R.id.SpringsInfoBtn,
//                R.id.AntirollInfoBtn
//        };
//    }//GetInfoId

}//class
