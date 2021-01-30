package com.example.roomhoursuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.roomhoursuser.HomeScreen.HomeActivity;
import com.example.roomhoursuser.LoginScreen.LoginActivity;
import com.example.roomhoursuser.SignUpScreen.SignUpActivity;
import com.example.roomhoursuser.SliderScreen.SliderActivity;

public class SplashScreen extends AppCompatActivity {

    private int SPLASH_SCREEN_TIMEOUT =3000;

    String User_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.mehroon));
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                User_id=Preference.get(SplashScreen.this,Preference.KEY_USER_ID);

                if(User_id != null && !User_id.trim().equalsIgnoreCase("0")){

                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }else
                {
                    Intent intent = new Intent(SplashScreen.this, SliderActivity.class);
                    startActivity(intent);
                    finish();
                }




            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}
