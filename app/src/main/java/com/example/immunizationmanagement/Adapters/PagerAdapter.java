package com.example.immunizationmanagement.Adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.immunizationmanagement.Fragments.BabyListFragment;
import com.example.immunizationmanagement.Fragments.VaccineListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BabyListFragment tab1 = new BabyListFragment();
                return tab1;
            case 1:
                VaccineListFragment tab2 = new VaccineListFragment();
                return tab2;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
