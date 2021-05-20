package com.example.roomhoursuser.BankScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.roomhoursuser.HomeFragment.HomeModel;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankDetailsActivity extends AppCompatActivity {

    private RelativeLayout RR_next_bank_detaols;

    private EditText edt_bak_holderName;
    private EditText edt_bankName;
    private EditText edt_bank_ac;
    private EditText edt_bank_code;
    private EditText edt_Area;

    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private String android_id = "";

    String BankHolderName = "";
    String BankName = "";
    String Banck_Ac = "";
    String ban_Code = "";
    String Area = "";

    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.mehroon));
        }


        RR_next_bank_detaols = findViewById(R.id.RR_next_bank_detaols);
        edt_bak_holderName = findViewById(R.id.edt_bak_holderName);
        edt_bankName = findViewById(R.id.edt_bankName);
        edt_bank_ac = findViewById(R.id.edt_bank_ac);
        edt_bank_code = findViewById(R.id.edt_bank_code);
        edt_Area = findViewById(R.id.edt_Area);
        progressBar = findViewById(R.id.progressBar);
        img_back = findViewById(R.id.img_back);

        edt_bak_holderName.setEnabled(false);
        edt_bankName.setEnabled(false);
        edt_bank_ac.setEnabled(false);
        edt_bank_code.setEnabled(false);
        edt_Area.setEnabled(false);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(this);

        RR_next_bank_detaols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  BankHolderName = edt_bak_holderName.getText().toString();
                BankName = edt_bankName.getText().toString();
                Banck_Ac = edt_bank_ac.getText().toString();
                ban_Code = edt_bank_code.getText().toString();
                Area = edt_Area.getText().toString();

                if (BankHolderName.equalsIgnoreCase("")) {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Bank Holder Name", Toast.LENGTH_SHORT).show();

                } else if (BankName.equalsIgnoreCase("")) {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();

                } else if (Banck_Ac.equalsIgnoreCase("")) {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();

                } else if (ban_Code.equalsIgnoreCase("")) {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Code", Toast.LENGTH_SHORT).show();

                } else if (Area.equalsIgnoreCase("")) {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Area", Toast.LENGTH_SHORT).show();

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    RR_next_bank_detaols.setEnabled(false);
                    if (sessionManager.isNetworkAvailable()) {

                        callbankMethod();

                    } else {
                        Toast.makeText(BankAccountDetails.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });

        if (sessionManager.isNetworkAvailable()) {

            callbankMethod();

        } else {
            Toast.makeText(BankDetailsActivity.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }

    }

    private void callbankMethod() {

       // String user_id = Preference.get(BankDetailsActivity.this, Preference.KEY_USER_ID);
        String user_id ="2";

        Call<BankModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_BankDetails(user_id);
        call.enqueue(new Callback<BankModel>() {
            @Override
            public void onResponse(Call<BankModel> call, Response<BankModel> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    BankModel myclass= response.body();

                    String status =myclass.getStatus().toString();
                    String message =myclass.getMessage().toString();
                    
                    if (status.equalsIgnoreCase("1")) {

                        String HolderName = myclass.getResult().get(0).getAccounHolderName();
                        String Ac = myclass.getResult().get(0).getBankAccountNumber();
                        String BankCode =  myclass.getResult().get(0).getBankCODE();
                        String BankName =  myclass.getResult().get(0).getBankName();
                        String AreaCode =  myclass.getResult().get(0).getAreaCode();

                        edt_bak_holderName.setText(HolderName);
                        edt_bank_ac.setText(Ac);
                        edt_bank_code.setText(BankCode);
                        edt_bankName.setText(BankName);
                        edt_Area.setText(AreaCode);

                      /*  Intent intent = new Intent(BankDetailsActivity.this, DoneActivity.class);
                        startActivity(intent);
                        finishAffinity();*/

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(BankDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<BankModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_next_bank_detaols.setEnabled(true);
                Toast.makeText(BankDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}