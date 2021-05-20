package com.example.roomhoursuser.HomeFragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomhoursuser.CheckInScreen.CheckInActivity;
import com.example.roomhoursuser.FavFragmen.FavFragment;
import com.example.roomhoursuser.FilterScreen.FilterActivity;
import com.example.roomhoursuser.GPSTracker;
import com.example.roomhoursuser.HomeScreen.HomeActivity;
import com.example.roomhoursuser.LoginScreen.LoginActivity;
import com.example.roomhoursuser.MainActivityLogin;
import com.example.roomhoursuser.MapScreen.MapFragment;
import com.example.roomhoursuser.PaymentMwthod.PaymentMethodActivity;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.SearchScreen.SearchActivity;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;
import com.example.roomhoursuser.ViewAllRooms.ViewAllActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

   private View view;
   private TextView txt_view_all;
   private TextView txt_map;
   private TextView txt_list;
   private RelativeLayout RR_list;
   private RelativeLayout RR_map;
   private RelativeLayout RR_filter;
   private RelativeLayout RR_Search;
   private RecyclerView recycler_recommended;
    private RecyclerView recycler_best_match;
    private RecommendedRecyclerViewAdapter mAdapter;
    private BestMatchRecyclerViewAdapter mAdapter_best_match;
    private ArrayList<HomeDataModel> modelList = new ArrayList<>();
    private ArrayList<HomeDataModel> modelList_best_match = new ArrayList<>();

    String android_id ="";
    public static ProgressBar progressBar;
    private String email="";
    private String password="";
    private SessionManager sessionManager;

    private Fragment fragment;
    GPSTracker gpsTracker;
    String latitude ="";
    String longitude ="";

    TextView txt_room_emty;
    TextView txt_room_emty_one;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view = inflater.inflate(R.layout.fragment_main, container, false);
        view = inflater.inflate(R.layout.fragment_main, container, false);

        findView();

        txt_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), ViewAllActivity.class);
                intent.putExtra("type","");
                intent.putExtra("less_price","");
                intent.putExtra("private_room","");
                intent.putExtra("air_room","");
                intent.putExtra("heating","");
                startActivity(intent);
            }
        });

        try {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker=new GPSTracker(getActivity());

        if(gpsTracker.canGetLocation()){

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        }else{
            gpsTracker.showSettingsAlert();
        }

        //android device Id
        android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(getActivity());

        RR_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getActivity(), SearchActivity.class);
                Intent intent=new Intent(getActivity(), ViewAllActivity.class);
                intent.putExtra("type","");
                intent.putExtra("less_price","");
                intent.putExtra("private_room","");
                intent.putExtra("air_room","");
                intent.putExtra("heating","");
                startActivity(intent);
            }
        });
        RR_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });

        RR_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new MapFragment();
                loadFragment(fragment);

              /*  RR_list.setBackgroundResource(R.drawable.roundbttn_white);
                RR_map.setBackgroundResource(R.drawable.roundbttn_mehroon);
                txt_list.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                txt_map.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));*/
            }
        });



        if (sessionManager.isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            callRoomDetailsApi();
             callBestMatchApi(latitude,longitude);

        }else {
            Toast.makeText(getActivity(), getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void findView() {
        progressBar = view.findViewById(R.id.progressBar);
        RR_filter=view.findViewById(R.id.RR_filter);
        recycler_recommended=view.findViewById(R.id.recycler_recommended);
        recycler_best_match=view.findViewById(R.id.recycler_best_match);
        RR_Search=view.findViewById(R.id.RR_Search);
        RR_map=view.findViewById(R.id.RR_map);
        RR_list=view.findViewById(R.id.RR_list);
        txt_list=view.findViewById(R.id.txt_list);
        txt_map=view.findViewById(R.id.txt_map);
        txt_view_all=view.findViewById(R.id.txt_view_all);
        txt_room_emty=view.findViewById(R.id.txt_room_emty);
        txt_room_emty_one=view.findViewById(R.id.txt_room_emty_one);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
        //showMarker();

    private void setAdapter(ArrayList<HomeDataModel> modelList) {

      /*  this.modelList.add(new RecommendedModel("John Smith",R.drawable.home_room));
        this.modelList.add(new RecommendedModel("John Smith",R.drawable.room_one));
        this.modelList.add(new RecommendedModel("John Smith",R.drawable.home_room));
        this.modelList.add(new RecommendedModel("John Smith",R.drawable.room_one));
        this.modelList.add(new RecommendedModel("John Smith",R.drawable.home_room));*/

        mAdapter = new RecommendedRecyclerViewAdapter(getActivity(),modelList,HomeFragment.this);
        recycler_recommended.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_recommended.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recycler_recommended.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new RecommendedRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HomeDataModel model) {

                Preference.save(getActivity(),Preference.KEY_Room_id,model.getId());

                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setAdapterBestMatch(ArrayList<HomeDataModel> modelList_best_match) {

       // modelList_best_match.add(new BestMatchModel("John Smith"));

        mAdapter_best_match = new BestMatchRecyclerViewAdapter(getActivity(),modelList_best_match,HomeFragment.this);
        recycler_best_match.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_best_match.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        recycler_best_match.setAdapter(mAdapter_best_match);

        mAdapter_best_match.SetOnItemClickListener(new BestMatchRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HomeDataModel model) {

                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);
            }
        });


    }

    public void callRoomDetailsApi() {

        String User_Id= Preference.get(getActivity(),Preference.KEY_USER_ID);

        Call<HomeModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_room_details(User_Id);
        call.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    HomeModel myclass= response.body();

                    String status = myclass.getStatus();
                    String result = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")){
                        txt_room_emty.setVisibility(View.GONE);
                        modelList = (ArrayList<HomeDataModel>) myclass.getResult();

                        setAdapter(modelList);

                    }else {
                        txt_room_emty.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    txt_room_emty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txt_room_emty.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void callBestMatchApi(String latitude, String longitude) {

        String User_Id= Preference.get(getActivity(),Preference.KEY_USER_ID);

        Call<HomeModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .best_match_room(latitude,longitude);

        call.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    HomeModel myclass= response.body();

                    String status = myclass.getStatus();
                    String result = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")){

                        txt_room_emty_one.setVisibility(View.GONE);

                        modelList_best_match = (ArrayList<HomeDataModel>) myclass.getResult();

                        setAdapterBestMatch(modelList_best_match);

                    }else {
                        txt_room_emty_one.setVisibility(View.VISIBLE);
                       // Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    txt_room_emty_one.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txt_room_emty_one.setVisibility(View.VISIBLE);
              //  Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }
}
