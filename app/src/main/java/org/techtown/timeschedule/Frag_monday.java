package org.techtown.timeschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_monday extends Fragment {
    private View view;

    public static Frag_monday newInstance(){
        Frag_monday frag_monday = new Frag_monday();
        return frag_monday;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_monday,container,false);
        return view;
    }
}
