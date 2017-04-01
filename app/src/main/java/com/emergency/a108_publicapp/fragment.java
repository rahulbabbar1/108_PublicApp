package com.emergency.a108_publicapp;

/**
 * Created by admin on 01-04-2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MG on 17-07-2016.
 */
public class fragment extends BottomSheetDialogFragment {

    String mString;

    static fragment newInstance(String string) {
       fragment f = new fragment();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheet_modal, container, false);
        TextView tv = (TextView) v.findViewById(R.id.text);
        return v;
    }
}
