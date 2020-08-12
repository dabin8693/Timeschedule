package org.techtown.timeschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_tuesday extends Fragment {
    private View view;

    public static Frag_tuesday newInstance(){
        Frag_tuesday frag_tuesday = new Frag_tuesday();
        return frag_tuesday;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_tuesday,container,false);
        return view;
    }
}
