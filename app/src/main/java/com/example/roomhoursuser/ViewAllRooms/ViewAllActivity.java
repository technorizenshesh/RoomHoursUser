package com.example.roomhoursuser.ViewAllRooms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.roomhoursuser.CheckInScreen.CheckInActivity;
import com.example.roomhoursuser.HomeFragment.HomeDataModel;
import com.example.roomhoursuser.HomeFragment.HomeFragment;
import com.example.roomhoursuser.HomeFragment.HomeModel;
import com.example.roomhoursuser.HomeFragment.RecommendedRecyclerViewAdapter;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllActivity extends AppCompatActivity {

    String android_id ="";
    private RecyclerView recycler_recommended;
    ViewAllRecyclerViewAdapter mAdapter;
    private ArrayList<HomeDataModel> modelList = new ArrayList<>();
    public static ProgressBar progressBar;
    public static RelativeLayout RR_back;
    private SessionManager sessionManager;

    private EditText edt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(this);

        recycler_recommended=findViewById(R.id.recycler_recommended);
        progressBar=findViewById(R.id.progressBar);
        RR_back=findViewById(R.id.RR_back);
        edt_search=findViewById(R.id.edt_search);


        if (sessionManager.isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            callRoomDetailsApi();
            // callLoginApiSocial();

        }else {
            Toast.makeText(this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }

        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });
    }

    void filter(String text){
        List<HomeDataModel> temp = new ArrayList();
        for(HomeDataModel d: modelList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getTitle().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        mAdapter.updateList((ArrayList<HomeDataModel>) temp);
    }

    private void setAdapter(ArrayList<HomeDataModel> modelList) {

        mAdapter = new ViewAllRecyclerViewAdapter(this,modelList);
        recycler_recommended.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewAllActivity.this);
        recycler_recommended.setLayoutManager(linearLayoutManager);
        recycler_recommended.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new ViewAllRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HomeDataModel model) {

                Preference.save(ViewAllActivity.this,Preference.KEY_Room_id,model.getId());

                Intent intent = new Intent(ViewAllActivity.this, CheckInActivity.class);
                startActivity(intent);

            }
        });
    }

    public void callRoomDetailsApi() {

        String User_Id= Preference.get(ViewAllActivity.this,Preference.KEY_USER_ID);

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

                        modelList = (ArrayList<HomeDataModel>) myclass.getResult();

                        setAdapter(modelList);

                    }else {

                        Toast.makeText(ViewAllActivity.this, result, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ViewAllActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}