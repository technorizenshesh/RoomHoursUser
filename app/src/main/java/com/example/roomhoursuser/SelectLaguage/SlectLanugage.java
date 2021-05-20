package com.example.roomhoursuser.SelectLaguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.SliderScreen.SliderActivity;

import java.util.Locale;

public class SlectLanugage extends AppCompatActivity {

    ImageView RRArbic;
    ImageView RREng;

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

        setContentView(R.layout.activity_slect_lanugage);

        init();
    }


    private void init() {
        
        RRArbic =findViewById(R.id.RRArbic);
        RREng =findViewById(R.id.RREng);

        RREng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateResources(SlectLanugage.this,"en");

                Preference.save(SlectLanugage.this,Preference.KEY_language,"en");

                startActivity(new Intent(SlectLanugage.this,SliderActivity.class));

            }
        });

        RRArbic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.save(SlectLanugage.this,Preference.KEY_language,"es");

                updateResources(SlectLanugage.this,"es");
                startActivity(new Intent(SlectLanugage.this, SliderActivity.class));
            }
        });




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