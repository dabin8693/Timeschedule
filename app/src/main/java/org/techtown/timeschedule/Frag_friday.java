package org.techtown.timeschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_friday extends Fragment {
    private View view;

    public static Frag_friday newInstance(){
        Frag_friday frag_friday = new Frag_friday();
        return frag_friday;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_friday,container,false);
        return view;
    }
}
