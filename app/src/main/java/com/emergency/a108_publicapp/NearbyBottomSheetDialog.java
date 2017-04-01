package com.emergency.a108_publicapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shubham7stark on 1/4/17.
 */

public class NearbyBottomSheetDialog extends BottomSheetDialogFragment {
    public static NearbyBottomSheetDialog getInstance() {
        return new NearbyBottomSheetDialog();
    }

    TextView hospital, police, petrol, food;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_custom_bottomsheet, container, false);
        hospital = (TextView)view.findViewById(R.id.btn_hospital_map);
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.in/maps/search/hospital/"));
                intent.setPackage("com.google.android.apps.maps");
                getActivity().startActivity(intent);
            }

        });

        police = (TextView)view.findViewById(R.id.btn_police_map);
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.in/maps/search/police+station/"));
                intent.setPackage("com.google.android.apps.maps");
                getActivity().startActivity(intent);
            }

        });

        petrol = (TextView)view.findViewById(R.id.btn_petrol_map);
        petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.in/maps/search/petrol+pump/"));
                intent.setPackage("com.google.android.apps.maps");
                getActivity().startActivity(intent);
            }
        });

        food = (TextView)view.findViewById(R.id.btn_food_map);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.in/maps/search/food/"));
                intent.setPackage("com.google.android.apps.maps");
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

}