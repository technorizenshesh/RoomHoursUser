package com.example.roomhoursuser.FilterScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.roomhoursuser.R;
import com.example.roomhoursuser.ViewAllRooms.ViewAllActivity;

public class FilterActivity extends AppCompatActivity {

    private RelativeLayout RR_apply_filter;
    private RelativeLayout RR_private_bathroom;
    private RelativeLayout RR_air_condition;
    private RelativeLayout RR_heating;

    private TextView txt_bedroom;
    private TextView txt_air_condition;
    private TextView txt_heating;

    boolean isBedRoom =false;
    boolean isAirCondition =false;
    boolean isHeating =false;

    boolean isCheckedLessPrice =false;
    boolean isNeayBy =false;


    String Bedroom = "";
    String AirCondition = "";
    String Heating = "";

    String LessPrice = "";

    LinearLayout RR_less_price;
    LinearLayout RR_nearBy_price;

    ImageView img_lessPrice;
    ImageView img_near;
    RatingBar rating;
    TextView txt_clear_all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        txt_clear_all=findViewById(R.id.txt_clear_all);
        RR_apply_filter=findViewById(R.id.RR_apply_filter);
        RR_private_bathroom=findViewById(R.id.RR_private_bathroom);
        RR_air_condition=findViewById(R.id.RR_air_condition);
        RR_heating=findViewById(R.id.RR_heating);
        txt_bedroom=findViewById(R.id.txt_bedroom);
        txt_air_condition=findViewById(R.id.txt_air_condition);
        txt_heating=findViewById(R.id.txt_heating);
        RR_less_price=findViewById(R.id.RR_less_price);
        RR_nearBy_price=findViewById(R.id.RR_nearBy_price);
        img_near=findViewById(R.id.img_near);
        img_lessPrice=findViewById(R.id.img_lessPrice);
        rating=findViewById(R.id.rating);

        RR_private_bathroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isBedRoom)
                {
                    RR_private_bathroom.setBackgroundResource(R.drawable.roundbttn_white);
                    txt_bedroom.setTextColor(getResources().getColor(R.color.black));

                    isBedRoom = false;

                    Bedroom ="";

                }else
                {
                    RR_private_bathroom.setBackgroundResource(R.drawable.roundbttn_white_mehroon);
                    txt_bedroom.setTextColor(getResources().getColor(R.color.white));
                    Bedroom ="true";
                    isBedRoom = true;
                }
            }
        });


        RR_air_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAirCondition)
                {
                    RR_air_condition.setBackgroundResource(R.drawable.roundbttn_white);
                    txt_air_condition.setTextColor(getResources().getColor(R.color.black));

                    isAirCondition =false;

                    AirCondition ="";

                }else
                {
                    RR_air_condition.setBackgroundResource(R.drawable.roundbttn_white_mehroon);
                    txt_air_condition.setTextColor(getResources().getColor(R.color.white));

                    AirCondition ="true";

                    isAirCondition =true;
                }




            }
        });

        RR_heating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isHeating)
                {
                    RR_heating.setBackgroundResource(R.drawable.roundbttn_white);
                    txt_heating.setTextColor(getResources().getColor(R.color.black));

                    isHeating = false;

                    Heating = "";

                }else
                {

                    RR_heating.setBackgroundResource(R.drawable.roundbttn_white_mehroon);

                    txt_heating.setTextColor(getResources().getColor(R.color.white));

                    Heating = "true";

                    isHeating = true;
                }

            }
        });

        RR_less_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isCheckedLessPrice)
                {
                    img_lessPrice.setImageResource(R.drawable.check_icon_new);

                    isCheckedLessPrice = false;

                    LessPrice = "";

                }else
                {

                    img_lessPrice.setImageResource(R.drawable.check_btn);

                    isCheckedLessPrice = true;

                    LessPrice = "true";

                }
            }
        });

        RR_nearBy_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNeayBy)
                {

                    img_near.setImageResource(R.drawable.check_icon_new);

                    isNeayBy = false;

                }else
                {
                    img_near.setImageResource(R.drawable.check_btn);

                    isNeayBy = true;

                }
            }
        });

        txt_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_lessPrice.setImageResource(R.drawable.check_icon_new);
                img_near.setImageResource(R.drawable.check_icon_new);
                rating.setRating(0.0f);

                RR_private_bathroom.setBackgroundResource(R.drawable.roundbttn_white);
                txt_bedroom.setTextColor(getResources().getColor(R.color.black));

                RR_air_condition.setBackgroundResource(R.drawable.roundbttn_white);
                txt_air_condition.setTextColor(getResources().getColor(R.color.black));

                RR_heating.setBackgroundResource(R.drawable.roundbttn_white);
                txt_heating.setTextColor(getResources().getColor(R.color.black));

                 isBedRoom =false;
                 isAirCondition =false;
                 isHeating =false;

                 isCheckedLessPrice =false;
                 isNeayBy =false;

                 Bedroom = "";
                 AirCondition = "";
                 Heating = "";
                LessPrice="";
            }
        });

        RR_apply_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(FilterActivity.this, ViewAllActivity.class);
                intent.putExtra("type","Filter");
                intent.putExtra("less_price",LessPrice);
                intent.putExtra("private_room",Bedroom);
                intent.putExtra("air_room",AirCondition);
                intent.putExtra("heating",Heating);
                startActivity(intent);

               // onBackPressed();

            }
        });
    }









}
