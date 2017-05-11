package com.developer.sangbarca.bkdictionary.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.sangbarca.bkdictionary.R;

/**
 * Created by SANG BARCA on 5/6/2017.
 */

public class FragmentGhiChu extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ghichu, container, false);
        return view;
    }
}
