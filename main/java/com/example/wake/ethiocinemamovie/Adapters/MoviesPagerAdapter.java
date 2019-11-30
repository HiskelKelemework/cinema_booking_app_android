package com.example.wake.ethiocinemamovie.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MoviesPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> allMoviePagesFrags = new ArrayList<>();
    private final List<String> allMoviePagesTitle = new ArrayList<>();

    public MoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return allMoviePagesFrags.get(position);
    }

    @Override
    public int getCount() {
        return allMoviePagesFrags.size();
    }

    public void addPage(Fragment page, String title) {
        allMoviePagesFrags.add(page);
        allMoviePagesTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return allMoviePagesTitle.get(position);
    }

}
