package com.example.roomhoursuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.roomhoursuser.HomeScreen.HomeActivity;
import com.example.roomhoursuser.LoginScreen.LoginActivity;
import com.example.roomhoursuser.SelectLaguage.SlectLanugage;
import com.example.roomhoursuser.SignUpScreen.SignUpActivity;
import com.example.roomhoursuser.SliderScreen.SliderActivity;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    private int SPLASH_SCREEN_TIMEOUT =3000;

    String User_id;
    GPSTracker gpsTracker;
    private String latitude ="";
    private String longitude ="";

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

      String Language=  Preference.get(SplashScreen.this,Preference.KEY_language);

        if(Language.equalsIgnoreCase("es"))
        {
            updateResources(SplashScreen.this,"es");

        }else
        {
            updateResources(SplashScreen.this,"en");

        }

        gpsTracker=new GPSTracker(SplashScreen.this);

        if(gpsTracker.canGetLocation()){

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        }else{

            gpsTracker.showSettingsAlert();

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
                    Intent intent = new Intent(SplashScreen.this, SlectLanugage.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_SCREEN_TIMEOUT);
    }

    private static void updateResources(Context context, String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
