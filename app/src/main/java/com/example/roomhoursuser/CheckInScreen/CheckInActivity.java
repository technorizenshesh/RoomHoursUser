package com.example.roomhoursuser.CheckInScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.roomhoursuser.AllImageView.AllImageViewpager;
import com.example.roomhoursuser.ChooseDateTime.ChooseDateTime;
import com.example.roomhoursuser.HomeFragment.BestMatchRecyclerViewAdapter;
import com.example.roomhoursuser.HomeFragment.HomeDataModel;
import com.example.roomhoursuser.HomeFragment.HomeModel;
import com.example.roomhoursuser.HomeFragment.RecommendedModel;
import com.example.roomhoursuser.HomeFragment.RecommendedRecyclerViewAdapter;
import com.example.roomhoursuser.LoginScreen.LoginActivity;
import com.example.roomhoursuser.MainActivityLogin;
import com.example.roomhoursuser.PaymentMwthod.PaymentMethodActivity;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    Marker mCurrLocationMarker;
    Location mLastLocation;

    private RelativeLayout RR_check_in_one;
    private RelativeLayout RR_check_in_two;
    private RecyclerView recycler_recommended;
    private RecommendedRecyclerViewAdapter mAdapter;
    private ArrayList<RecommendedModel> modelList = new ArrayList<>();

    String android_id ="";
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    private ImageView img_room;
    private TextView txt_roomName;
    private TextView txt_adress;
    private TextView txt_price;

    private TextView txt_one_hour;
    private TextView txt_two_hour;
    private TextView txt_three_hour;
    private TextView txt_four_hour;
    private TextView txt_description;
    private TextView txt_totl_price;

    CardView card_img;

    RelativeLayout rr_super_item;

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

        setContentView(R.layout.activity_check_in);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        recycler_recommended=findViewById(R.id.recycler_recommended);
        RR_check_in_one=findViewById(R.id.RR_check_in_one);
        RR_check_in_two=findViewById(R.id.RR_check_in_two);
        progressBar=findViewById(R.id.progressBar);
        card_img=findViewById(R.id.card_img);
        rr_super_item=findViewById(R.id.rr_super_item);

        img_room=findViewById(R.id.img_room);
        txt_roomName=findViewById(R.id.txt_roomName);
        txt_adress=findViewById(R.id.txt_adress);
        txt_price=findViewById(R.id.txt_price);
        txt_one_hour=findViewById(R.id.txt_one_hour);
        txt_two_hour=findViewById(R.id.txt_two_hour);
        txt_three_hour=findViewById(R.id.txt_three_hour);
        txt_four_hour=findViewById(R.id.txt_four_hour);
        txt_description=findViewById(R.id.txt_description);
        txt_totl_price=findViewById(R.id.txt_totl_price);
        img_room=findViewById(R.id.img_room);

        //android device Id
        android_id = Settings.Secure.getString(CheckInActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(CheckInActivity.this);

        card_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rr_super_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(CheckInActivity.this, AllImageViewpager.class);
                startActivity(intent);
            }
        });

        img_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(CheckInActivity.this, AllImageViewpager.class);
                startActivity(intent);
            }
        });

        //setAdapter();
        RR_check_in_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(CheckInActivity.this, PaymentMethodActivity.class);
                Intent intent = new Intent(CheckInActivity.this, ChooseDateTime.class);
                startActivity(intent);
            }
        });

        RR_check_in_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(CheckInActivity.this, PaymentMethodActivity.class);
                Intent intent = new Intent(CheckInActivity.this, ChooseDateTime.class);
                startActivity(intent);
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        if (sessionManager.isNetworkAvailable()) {
            callRoomDetailsApi();
            // callLoginApiSocial();

        }else {
            Toast.makeText(CheckInActivity.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {

    /*    modelList.add(new RecommendedModel("John Smith",R.drawable.home_room));
        modelList.add(new RecommendedModel("John Smith",R.drawable.room_one));
        modelList.add(new RecommendedModel("John Smith",R.drawable.home_room));
        modelList.add(new RecommendedModel("John Smith",R.drawable.room_one));
        modelList.add(new RecommendedModel("John Smith",R.drawable.home_room));


        mAdapter = new RecommendedRecyclerViewAdapter(CheckInActivity.this, modelList);
        recycler_recommended.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckInActivity.this);
        recycler_recommended.setLayoutManager(new LinearLayoutManager(CheckInActivity.this, LinearLayoutManager.HORIZONTAL, true));
        recycler_recommended.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new RecommendedRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RecommendedModel model) {

            }
        });*/
    }


    private void callRoomDetailsApi() {

        String Room_id= Preference.get(CheckInActivity.this,Preference.KEY_Room_id);

        Call<HomeDetailsModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_room_details_new(Room_id);
        call.enqueue(new Callback<HomeDetailsModel>() {
            @Override
            public void onResponse(Call<HomeDetailsModel> call, Response<HomeDetailsModel> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    HomeDetailsModel myclass= response.body();

                    String status = myclass.getStatus();
                    String result = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")){

                        List<String> ImageUrl= myclass.getResult().getImage();

                        String imageUr=ImageUrl.get(0).toString();

                        if(imageUr !=null)
                        {
                            Picasso.with(CheckInActivity.this).load(imageUr)
                                .placeholder(R.drawable.room_one)
                                .into(img_room);
                           /* Glide.with(CheckInActivity.this).load(imageUr).placeholder(R.drawable.room_one)
                                    .into(img_room);*/
                        }

                       String  TotalPrice = "€"+myclass.getResult().getRecommendedPrice();

                        Preference.save(CheckInActivity.this,Preference.KEY_payment_total,TotalPrice);
                        txt_roomName.setText(myclass.getResult().getTitle());
                        txt_adress.setText(myclass.getResult().getStreet()+","+myclass.getResult().getCountry());
                        txt_price.setText(TotalPrice);
                        txt_description.setText(myclass.getResult().getDescription());

                        String oneHour ="€"+myclass.getResult().getPriceOneHour();
                        String twoHour ="€"+myclass.getResult().getPriceTwoHours();
                        String threeHour ="€"+myclass.getResult().getPriceThreeHour();
                        String fourHour ="€"+myclass.getResult().getPriceFourSexHour();

                        txt_one_hour.setText(oneHour);
                        txt_two_hour.setText(twoHour);
                        txt_three_hour.setText(threeHour);
                        txt_four_hour.setText(fourHour);

                        Preference.save(CheckInActivity.this,Preference.KEY_oneHr,oneHour);
                        Preference.save(CheckInActivity.this,Preference.KEY_twoHr,twoHour);
                        Preference.save(CheckInActivity.this,Preference.KEY_threeHr,threeHour);
                        Preference.save(CheckInActivity.this,Preference.KEY_fourHr,fourHour);



                    }else {

                        Toast.makeText(CheckInActivity.this, result, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HomeDetailsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng latLng = new LatLng(22.719568, 75.857727);

        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("First Pit Stop")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }

}
