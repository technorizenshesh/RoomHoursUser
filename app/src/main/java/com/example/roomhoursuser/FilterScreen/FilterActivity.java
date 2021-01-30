package com.example.roomhoursuser.FilterScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.roomhoursuser.R;

public class FilterActivity extends AppCompatActivity {

    private RelativeLayout RR_apply_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        RR_apply_filter=findViewById(R.id.RR_apply_filter);
        RR_apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
