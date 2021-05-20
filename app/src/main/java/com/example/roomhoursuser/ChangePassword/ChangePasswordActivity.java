package com.example.roomhoursuser.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.roomhoursuser.FavFragmen.FavFragment;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private ImageView img_back;
    private RelativeLayout RR_save;
    private EditText edt_new_password;
    private EditText edt_new_Confrim_password;
    private ProgressBar progressBar;
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
        setContentView(R.layout.activity_change_password);

        progressBar=findViewById(R.id.progressBar);
        img_back=findViewById(R.id.img_back);
        RR_save=findViewById(R.id.RR_save);
        edt_new_password=findViewById(R.id.edt_new_password);
        edt_new_Confrim_password=findViewById(R.id.edt_new_Confrim_password);
        sessionManager = new SessionManager(ChangePasswordActivity.this);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RR_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NewPassword = edt_new_password.getText().toString();
                String ConfirmPassword = edt_new_Confrim_password.getText().toString();

                if(NewPassword.equalsIgnoreCase(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();

                }else if(ConfirmPassword.equalsIgnoreCase(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();

                }else if(! ConfirmPassword.equalsIgnoreCase(NewPassword))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Don't Match Password", Toast.LENGTH_SHORT).show();

                }else
                {
                    if (sessionManager.isNetworkAvailable()) {

                        progressBar.setVisibility(View.VISIBLE);

                        ChangePassword(ConfirmPassword);

                    }else {

                        Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }


    private void ChangePassword(String Password) {

        String User_Id= Preference.get(ChangePasswordActivity.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_ChnagePassword(User_Id,Password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    String  result = jsonObject.getString("result");

                    if (status.equalsIgnoreCase("1")){

                        Toast.makeText(ChangePasswordActivity.this, result, Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(ChangePasswordActivity.this, result, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}