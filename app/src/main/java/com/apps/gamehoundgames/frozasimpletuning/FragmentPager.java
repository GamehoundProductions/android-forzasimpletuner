package com.apps.gamehoundgames.frozasimpletuning;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.apps.gamehoundgames.frozasimpletuning.other.CommonActions;

import java.util.ArrayList;
import java.util.List;


public class FragmentPager extends FragmentStatePagerAdapter {

    private final List<Fragment> allFragments = new ArrayList<>();


    public FragmentPager(FragmentManager fm) {
        super(fm);
    }


    /**
     *  Add a fragment page to the list. Order matters in which fragments are added.
     * @param newFrag: a fragment to be added to display later.
     */
    public void addFragment(Fragment newFrag){
        if(!this.allFragments.contains(newFrag))
            this.allFragments.add(newFrag);
    }//addFragment


    @Override
    public Fragment getItem(int i) {
        Fragment frag = this.allFragments.get(i);
        return frag;
    }//getItem


    @Override
    public int getCount() {
        return this.allFragments.size();
    }//getCoount


    /**
     *  Activates fragment page through the ViewPager; refreshes result fields of the inputs and
     * hides keyboard from the previous fragment.
     * @param pager: the main pager with all the fragments
     * @param index: page/fragment to activate.
     */
    public void SetCurrentFragment(ViewPager pager, int index){
        Fragment frag = this.getItem(index);
        if(frag instanceof BasePageActivity){
            BasePageActivity fragtivity = (BasePageActivity)frag;
            fragtivity.RefreshResults();
        }

        pager.setCurrentItem(index);
        CommonActions.HideKeyboard(getItem(0).getView(), getItem(0).getActivity());
    }//SetCurrentFragment

}//class
