package com.example.a40001811.fragmentexample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class XMLParser extends android.support.v4.app.Fragment {
    public XMLParser() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        TextView textView=(TextView)view.findViewById(R.id.textView);
        textView.setTextSize(40.0f);
        textView.setText("Hello Anyone");
        return view;

    }
}
