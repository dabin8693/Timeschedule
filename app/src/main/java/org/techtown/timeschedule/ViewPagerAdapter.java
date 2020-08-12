package org.techtown.timeschedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {//상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
        switch (position){//프래그먼트 위치
            case 0:
                return "월요일";
            case 1:
                return "화요일";
            case 2:
                return "수요일";
            case 3:
                return "목요일";
            case 4:
                return "금요일";
            case 5:
                return "토요일";
            case 6:
                return "일요일";
            default:
                return null;
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//프래그먼트 교체를 보여주는 처리를 구현한 곳
        switch (position){//프래그먼트 위치
            case 0:
                return Frag_monday.newInstance();
            case 1:
                return Frag_tuesday.newInstance();
            case 2:
                return Frag_wednesday.newInstance();
            case 3:
                return Frag_thursday.newInstance();
            case 4:
                return Frag_friday.newInstance();
            case 5:
                return Frag_saturday.newInstance();
            case 6:
                return Frag_sunday.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 7;
    }
}
