package com.yash.battleroyal.Activities.Splash.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yash.battleroyal.Activities.Splash.OnBoardingFragment1;
import com.yash.battleroyal.Activities.Splash.OnBoardingFragment2;
import com.yash.battleroyal.Activities.Splash.OnBoardingFragment3;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OnBoardingFragment1();
            case 1:
                return new OnBoardingFragment2();
            case 2:
                return new OnBoardingFragment3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
