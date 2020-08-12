package org.techtown.timeschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_saturday extends Fragment {
    private View view;

    public static Frag_saturday newInstance(){
        Frag_saturday frag_saturday = new Frag_saturday();
        return frag_saturday;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_saturday,container,false);
        return view;
    }
}
