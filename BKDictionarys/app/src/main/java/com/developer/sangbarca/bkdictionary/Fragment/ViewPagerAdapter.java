package com.developer.sangbarca.bkdictionary.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by SANG BARCA on 5/6/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrayFragment = new ArrayList<>();
    private ArrayList<String> arrayTitle = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return arrayFragment.get(position);
    }

    @Override
    public int getCount() {
        return arrayFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arrayTitle.get(position);
    }

    //function add
    public void AddFragment(Fragment fragment, String title){
        arrayFragment.add(fragment);
        arrayTitle.add(title);
    }
}
