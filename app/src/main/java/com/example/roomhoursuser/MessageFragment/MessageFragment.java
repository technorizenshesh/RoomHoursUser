package com.example.roomhoursuser.MessageFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomhoursuser.ChatActivity;
import com.example.roomhoursuser.HomeFragment.BestMatchModel;
import com.example.roomhoursuser.HomeFragment.BestMatchRecyclerViewAdapter;
import com.example.roomhoursuser.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

   private View view;
   private RelativeLayout RR_msg;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view = inflater.inflate(R.layout.fragment_main, container, false);
        view = inflater.inflate(R.layout.fragment_message, container, false);

        findView();
        RR_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void findView() {
        RR_msg =view.findViewById(R.id.RR_msg);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
        //showMarker();



}
