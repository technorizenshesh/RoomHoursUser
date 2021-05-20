package com.example.roomhoursuser.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.roomhoursuser.FavFragmen.FavFragment;
import com.example.roomhoursuser.HomeFragment.HomeFragment;
import com.example.roomhoursuser.MessageFragment.MessageFragment;
import com.example.roomhoursuser.Profile.ProfileFragment;
import com.example.roomhoursuser.R;

public class HomeActivity extends AppCompatActivity {

    private Fragment fragment;
    private RelativeLayout RR_Home;
    private RelativeLayout RR_fav;
    private RelativeLayout RR_map;
    private RelativeLayout RR_chat;
    private RelativeLayout RR_profile;
    private ImageView img_home;
    private ImageView img_fav;
    private ImageView img_map;
    private ImageView img_chat;
    private ImageView img_profile;

    boolean doubleBackToExitPressedOnce = false;

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

        setContentView(R.layout.activity_home);

        findView();

        RR_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new HomeFragment();
                loadFragment(fragment);

                img_home.setImageResource(R.mipmap.home);
                img_fav.setImageResource(R.mipmap.fav_one);
                img_map.setImageResource(R.mipmap.map_one);
                img_chat.setImageResource(R.mipmap.chat_one);
                img_profile.setImageResource(R.mipmap.profile_one);
            }
        });
        RR_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new FavFragment();
                loadFragment(fragment);

                img_home.setImageResource(R.mipmap.home_one);
                img_fav.setImageResource(R.mipmap.fav);
                img_map.setImageResource(R.mipmap.map_one);
                img_chat.setImageResource(R.mipmap.chat_one);
                img_profile.setImageResource(R.mipmap.profile_one);
            }
        });
        RR_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_home.setImageResource(R.mipmap.home_one);
                img_fav.setImageResource(R.mipmap.fav_one);
                img_map.setImageResource(R.mipmap.map);
                img_chat.setImageResource(R.mipmap.chat_one);
                img_profile.setImageResource(R.mipmap.profile_one);

            }
        });
        RR_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment =new MessageFragment();
                loadFragment(fragment);

                img_home.setImageResource(R.mipmap.home_one);
                img_fav.setImageResource(R.mipmap.fav_one);
                img_map.setImageResource(R.mipmap.map_one);
                img_chat.setImageResource(R.mipmap.chat);
                img_profile.setImageResource(R.mipmap.profile_one);

            }
        });

        RR_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new ProfileFragment();
                loadFragment(fragment);

                img_home.setImageResource(R.mipmap.home_one);
                img_fav.setImageResource(R.mipmap.fav_one);
                img_map.setImageResource(R.mipmap.map_one);
                img_chat.setImageResource(R.mipmap.chat_one);
                img_profile.setImageResource(R.mipmap.profile);

            }
        });

        fragment = new HomeFragment();
        loadFragment(fragment);


    }

    private void findView() {
        RR_Home= findViewById(R.id.RR_Home);
        RR_fav= findViewById(R.id.RR_fav);
        RR_map= findViewById(R.id.RR_map);
        RR_chat= findViewById(R.id.RR_chat);
        RR_profile= findViewById(R.id.RR_profile);

        img_home= findViewById(R.id.img_home);
        img_fav= findViewById(R.id.img_fav);
        img_map= findViewById(R.id.img_map);
        img_chat= findViewById(R.id.img_chat);
        img_profile= findViewById(R.id.img_profile);
    }


    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        fragment = new HomeFragment();
        loadFragment(fragment);
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                finishAffinity();
            }
        }, 2000);
    }


}
