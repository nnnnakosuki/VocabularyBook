package com.vocabularybook.vocabularybook;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPageAdapter extends FragmentStateAdapter {

    public MyPageAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0) {
            return new SecondFragment();
        } else if(position == 1) {
            return new FirstFragment();
        } else {
            return new ThirdFragment();
        }
    }
    @Override
    public int getItemCount() { //页面数量
        return 3;
    }
}