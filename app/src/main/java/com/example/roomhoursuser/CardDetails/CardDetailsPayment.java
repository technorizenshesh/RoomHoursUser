package com.example.roomhoursuser.CardDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardEditText;
import com.braintreepayments.cardform.view.CardholderNameEditText;
import com.braintreepayments.cardform.view.ExpirationDateEditText;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.Utills.RetrofitClients;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardDetailsPayment extends AppCompatActivity {

    private CardholderNameEditText et_card_holder_name;
    private CardEditText et_card_number;
    private ExpirationDateEditText et_card_expiry_date;
    private RelativeLayout RR_save_card;
    private ProgressBar progressBar;

    String CardHolderName="";
    String cardNumber="";
    String finalExpiryDate="";

    LinearLayout LL_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details_payment);

        et_card_holder_name=findViewById(R.id.et_card_holder_name);
        et_card_number=findViewById(R.id.et_card_number);
        et_card_expiry_date=findViewById(R.id.et_card_expiry_date);
        RR_save_card=findViewById(R.id.RR_save_card);
        progressBar=findViewById(R.id.progressBar);
        LL_scan=findViewById(R.id.LL_scan);

        RR_save_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String card_expiry_date=et_card_expiry_date.getMonth();
                String year=et_card_expiry_date.getYear();
                CardHolderName=et_card_holder_name.getText().toString();
                cardNumber=et_card_number.getText().toString();

                finalExpiryDate=card_expiry_date+" - "+year;

                if(CardHolderName.equalsIgnoreCase(""))
                {
                    Toast.makeText(CardDetailsPayment.this, "Please Enter Card Holder Name", Toast.LENGTH_SHORT).show();

                }else if(cardNumber.equalsIgnoreCase(""))
                {
                    Toast.makeText(CardDetailsPayment.this, "Please Enter Card Number", Toast.LENGTH_SHORT).show();

                }else if(card_expiry_date.equalsIgnoreCase(""))
                {
                    Toast.makeText(CardDetailsPayment.this, "Please correct Expiry Date", Toast.LENGTH_SHORT).show();

                }else if(year.equalsIgnoreCase(""))
                {
                    Toast.makeText(CardDetailsPayment.this, "Please correct Expiry Date", Toast.LENGTH_SHORT).show();

                }else
                {

                    Toast.makeText(CardDetailsPayment.this, "Save Card", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }

            }
        });

        LL_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void addPaymentCard() {

        String Userid= Preference.get(CardDetailsPayment.this, Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_payment(Userid,CardHolderName,cardNumber,finalExpiryDate);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    if (status.equalsIgnoreCase("1")) {

                        RR_save_card.setEnabled(true);

                        progressBar.setVisibility(View.GONE);

                        //startActivity(new Intent(CardDetailsPayment.this, AddPayementCard.class));

                        Toast.makeText(CardDetailsPayment.this, message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(CardDetailsPayment.this, message, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        RR_save_card.setEnabled(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_save_card.setEnabled(true);
                Toast.makeText(CardDetailsPayment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
