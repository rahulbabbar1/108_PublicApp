package com.emergency.a108_publicapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shubham7stark on 1/4/17.
 */

public class NearbyBottomSheetDialog extends BottomSheetDialogFragment {
    public static NearbyBottomSheetDialog getInstance() {
        return new NearbyBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_custom_bottomsheet, container, false);
    }

}