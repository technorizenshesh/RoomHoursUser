package com.example.roomhoursuser.PaymentMwthod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomhoursuser.CardDetails.CardDetailsPayment;
import com.example.roomhoursuser.CheckInScreen.CheckInActivity;
import com.example.roomhoursuser.HomeScreen.HomeActivity;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;

public class PaymentMethodActivity extends AppCompatActivity {

    private RelativeLayout RR_next;
    private RelativeLayout RR_card;
    private RelativeLayout RR_cash;
    private TextView txt_price;
    private ImageView img_card_one;
    private ImageView img_cash_one;
    String PaymentType ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        RR_next=findViewById(R.id.RR_next);
        txt_price=findViewById(R.id.txt_price);
        RR_card=findViewById(R.id.RR_card);
        RR_cash=findViewById(R.id.RR_cash);
        img_card_one=findViewById(R.id.img_card_one);
        img_cash_one=findViewById(R.id.img_cash_one);

       String TotalAmt = Preference.get(PaymentMethodActivity.this,Preference.KEY_payment_total);
        txt_price.setText(TotalAmt);

        RR_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PaymentType.equalsIgnoreCase("Card"))
                {
                    Intent intent = new Intent(PaymentMethodActivity.this, CardDetailsPayment.class);
                    startActivity(intent);

                }else
                {
                    Toast.makeText(PaymentMethodActivity.this, "Room Booked.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PaymentMethodActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        RR_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_card_one.setImageResource(R.drawable.check_icon);
                img_cash_one.setImageResource(R.drawable.circle_new);

                PaymentType = "Card";
            }
        });

        RR_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_card_one.setImageResource(R.drawable.circle_new);
                img_cash_one.setImageResource(R.drawable.check_icon);

                PaymentType = "Cash";
            }
        });
    }
}
