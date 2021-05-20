package com.example.roomhoursuser.PaymentMwthod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomhoursuser.CardDetails.CardDetailsPayment;
import com.example.roomhoursuser.CheckInScreen.CheckInActivity;
import com.example.roomhoursuser.ChooseDateTime.ChooseDateTime;
import com.example.roomhoursuser.HomeScreen.HomeActivity;
import com.example.roomhoursuser.LoginScreen.LoginActivity;
import com.example.roomhoursuser.LoginScreen.LoginModel;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.SignUpScreen.SignUpActivity;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends AppCompatActivity {

    private RelativeLayout RR_next;
    private RelativeLayout RR_card;
    private RelativeLayout RR_cash;
    private TextView txt_price;

    private ImageView img_card_one;
    private ImageView img_cash_one;
    private ProgressBar progressBar;
    String PaymentType ="Cash";
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.mehroon));
        }

        setContentView(R.layout.activity_payment_method);

        RR_next=findViewById(R.id.RR_next);
        txt_price=findViewById(R.id.txt_price);
        RR_card=findViewById(R.id.RR_card);
        RR_cash=findViewById(R.id.RR_cash);
        img_card_one=findViewById(R.id.img_card_one);
        img_cash_one=findViewById(R.id.img_cash_one);
        progressBar=findViewById(R.id.progressBar);
        sessionManager = new SessionManager(this);

         String TotalAmt = Preference.get(PaymentMethodActivity.this,Preference.KEY_payment_total);
        txt_price.setText(TotalAmt);

        RR_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PaymentType.equalsIgnoreCase("Card"))
                {
                    Toast.makeText(PaymentMethodActivity.this, "Comming Soon.", Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(PaymentMethodActivity.this, CardDetailsPayment.class);
                    startActivity(intent);*/

                }else
                {

                    if (sessionManager.isNetworkAvailable()) {
                        RR_next.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        CallRoomBooked();
                    }else {
                        Toast.makeText(PaymentMethodActivity.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
                    }
                /*    Intent intent = new Intent(PaymentMethodActivity.this, HomeActivity.class);
                    startActivity(intent);*/
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


    private void CallRoomBooked() {
        String Userid = Preference.get(PaymentMethodActivity.this,Preference.KEY_USER_ID);
        String Room_id= Preference.get(PaymentMethodActivity.this,Preference.KEY_Room_id);
        String CheckIn_id=Preference.get(PaymentMethodActivity.this,Preference.KEY_CheckInDate);
        String CheckOut_id=Preference.get(PaymentMethodActivity.this,Preference.KEY_CheckOutDate);
        String TotalPrice=Preference.get(PaymentMethodActivity.this,Preference.KEY_payment_total);

        Call<BookingModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .room_booking(Userid,Room_id,TotalPrice,CheckIn_id,CheckOut_id,"1","cash");
        call.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                progressBar.setVisibility(View.GONE);
                RR_next.setEnabled(true);
                try {
                    BookingModel myLogin = response.body();
                    if (myLogin.getStatus().equalsIgnoreCase("1")){
                        Intent intent = new Intent(PaymentMethodActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(PaymentMethodActivity.this, "Room Booked.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PaymentMethodActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                finally {
                    progressBar.setVisibility(View.GONE);
                    RR_next.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<BookingModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_next.setEnabled(true);
                Toast.makeText(PaymentMethodActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }





}
