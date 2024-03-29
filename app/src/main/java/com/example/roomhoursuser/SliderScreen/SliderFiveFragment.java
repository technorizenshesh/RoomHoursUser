package com.example.roomhoursuser.SliderScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.roomhoursuser.LoginScreen.LoginActivity;
import com.example.roomhoursuser.MainActivityLogin;
import com.example.roomhoursuser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFiveFragment extends Fragment {

    private View view;
    private RelativeLayout RR_comtinue;

    public SliderFiveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_slider_five, container, false);


        RR_comtinue = view.findViewById(R.id.RR_comtinue);

        RR_comtinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivityLogin.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
