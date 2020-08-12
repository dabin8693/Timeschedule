package org.techtown.timeschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_sunday extends Fragment {
    private View view;

    public static Frag_sunday newInstance(){
        Frag_sunday frag_sunday = new Frag_sunday();
        return frag_sunday;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_sunday,container,false);
        return view;
    }
}
