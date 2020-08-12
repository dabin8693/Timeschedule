package org.techtown.timeschedule;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter2 extends FragmentPagerAdapter {
    private OnFragmentChangedListener listener;
    public ViewPagerAdapter2(@NonNull FragmentManager fm, OnFragmentChangedListener listener) {
        super(fm);
        this.listener = listener;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {//상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
        switch (position){//프래그먼트 위치
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "3";
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//프래그먼트 교체를 보여주는 처리를 구현한 곳
        Log.d("loglog,포지션",Integer.toString(position));
        if(position%3 == 0){
            return  frag2_week.newInstance(listener, position);
        }else if(position%3 == 1){
            return  frag3_week.newInstance(listener, position);
        }else if(position%3 == 2){
            return  frag1_week.newInstance(listener, position);
        }else{
            return null;
        }

    }

    @Override
    public int getCount() {
        return 3000;
    }

}
