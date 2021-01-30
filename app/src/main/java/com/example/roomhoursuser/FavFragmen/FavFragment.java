package com.example.roomhoursuser.FavFragmen;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomhoursuser.CheckInScreen.CheckInActivity;
import com.example.roomhoursuser.FavFragmen.ApiModel.GetFavModel;
import com.example.roomhoursuser.FavFragmen.ApiModel.GetFavModelData;
import com.example.roomhoursuser.HomeFragment.BestMatchModel;
import com.example.roomhoursuser.HomeFragment.BestMatchRecyclerViewAdapter;
import com.example.roomhoursuser.HomeFragment.FavRoomAllRecyclerViewAdapter;
import com.example.roomhoursuser.HomeFragment.HomeDataModel;
import com.example.roomhoursuser.HomeFragment.HomeFragment;
import com.example.roomhoursuser.HomeFragment.HomeModel;
import com.example.roomhoursuser.HomeFragment.RecommendedModel;
import com.example.roomhoursuser.HomeFragment.RecommendedRecyclerViewAdapter;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.SearchScreen.SearchActivity;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {

   private View view;
    private RecyclerView recycler_best_match;
    private FavRoomAllRecyclerViewAdapter mAdapter_best_match;
    private ArrayList<GetFavModelData> modelList_best_match = new ArrayList<>();
    String android_id ="";
    public static ProgressBar progressBar;
    private SessionManager sessionManager;
    private TextView txt_emty;
    private RelativeLayout RR_fav;
    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view = inflater.inflate(R.layout.fragment_main, container, false);
        view = inflater.inflate(R.layout.fragment_fav, container, false);

        findView();

        //android device Id
        android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(getActivity());


        progressBar.setVisibility(View.VISIBLE);

        if (sessionManager.isNetworkAvailable()) {

            getFavuriteApi();

        }else {
            Toast.makeText(getActivity(), getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void findView() {
        recycler_best_match=view.findViewById(R.id.recycler_best_match);
        progressBar=view.findViewById(R.id.progressBar);
        txt_emty=view.findViewById(R.id.txt_emty);
        RR_fav=view.findViewById(R.id.RR_fav);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
        //showMarker();

    private void setAdapterBestMatch(ArrayList<GetFavModelData> modelList_best_match) {

        mAdapter_best_match = new FavRoomAllRecyclerViewAdapter(getActivity(), this.modelList_best_match, FavFragment.this);
        recycler_best_match.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_best_match.setLayoutManager(linearLayoutManager);
        recycler_best_match.setAdapter(mAdapter_best_match);

        mAdapter_best_match.SetOnItemClickListener(new FavRoomAllRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetFavModelData model) {

                Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);

            }
        });
    }

    public void getFavuriteApi() {

        String User_Id= Preference.get(getActivity(),Preference.KEY_USER_ID);

        Call<GetFavModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_favorite(User_Id);

        call.enqueue(new Callback<GetFavModel>() {
            @Override
            public void onResponse(Call<GetFavModel> call, Response<GetFavModel> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    GetFavModel myclass= response.body();

                    String status = myclass.getStatus();
                    String result = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")){
                        txt_emty.setVisibility(View.GONE);
                        RR_fav.setVisibility(View.VISIBLE);
                        modelList_best_match = (ArrayList<GetFavModelData>) myclass.getResult();

                        setAdapterBestMatch(modelList_best_match);

                    }else {
                        RR_fav.setVisibility(View.GONE);
                        txt_emty.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    txt_emty.setVisibility(View.VISIBLE);
                    RR_fav.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetFavModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txt_emty.setVisibility(View.VISIBLE);
                RR_fav.setVisibility(View.GONE);
            }
        });
    }


}
