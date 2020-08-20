package org.techtown.timeschedule;

import android.app.ProgressDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class frag_week extends Fragment implements OnFragmentChangedListener {
    static int WEEK_CALENDER_SIZE;
    static int WEEK_START_TIME;
    static int WEEK_END_TIME;
    //static int height_size;
    static int WIDTH_SIZE;
    static int PAGE_INDEX;
    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ProgressDialog progressDialog;

    public frag_week(ProgressDialog progressDialog){
        this.progressDialog = progressDialog;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("주간 프래그먼트 온크리에이트","ㅇㄴㄹ");
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        WIDTH_SIZE = size.x;
        View view = inflater.inflate(R.layout.frag_week,container,false);

        viewPager = view.findViewById(R.id.week_viewpager);

        fragmentPagerAdapter = new ViewPagerAdapter2(getChildFragmentManager(),this);
        //프래그먼트내에서 프래그먼트쓸때 액티비티의getfragmentmanager가 아니라 프래그먼트의getchildfragmentmanager을 써야된다 안그러면 프래그먼트 전환시 화면이 사라지는 경우가 생김
        //TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("loglog페이저번호",Integer.toString(position));
                PAGE_INDEX = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(1500);
        Log.d("loglog주간 프래그먼트 온스타트","ㅇㄴㄹ");
        //progressDialog.dismiss();
        return view;
    }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onStart() {
        super.onStart();



    }


    @Override
    public void onFragmentChanged() {
        Log.d("loglog,리셋","ㄴㄹㄴ");
        fragmentPagerAdapter.notifyDataSetChanged();
    }
}
