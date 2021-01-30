package com.example.roomhoursuser.Profile;


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

import com.example.roomhoursuser.BankScreen.BankDetailsActivity;
import com.example.roomhoursuser.ChangePassword.ChangePasswordActivity;
import com.example.roomhoursuser.LoginScreen.LoginActivity;
import com.example.roomhoursuser.LoginScreen.LoginModel;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View view;
    private TextView txt_name;
    private TextView txt_email;
    private TextView txt_logout;
    String android_id ="";
    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private RelativeLayout RR_bank_details;
    private RelativeLayout RR_chnagePassword;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view = inflater.inflate(R.layout.fragment_main, container, false);
        view = inflater.inflate(R.layout.activity_profile, container, false);

        progressBar =view.findViewById(R.id.progressBar);
        RR_bank_details =view.findViewById(R.id.RR_bank_details);
        txt_name =view.findViewById(R.id.txt_name);
        txt_email =view.findViewById(R.id.txt_email);
        txt_logout =view.findViewById(R.id.txt_logout);
        RR_chnagePassword =view.findViewById(R.id.RR_chnagePassword);

        //android device Id
        android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(getActivity());


        progressBar.setVisibility(View.VISIBLE);
        if (sessionManager.isNetworkAvailable()) {
            callProfileApi();
        }else {
            Toast.makeText(getActivity(), getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.clearPreference(getActivity());

                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });

        RR_bank_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), BankDetailsActivity.class);
                startActivity(intent);
            }
        });

        RR_chnagePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
    }
    //showMarker();

    private void callProfileApi() {

        String UserId = Preference.get(getActivity(), Preference.KEY_USER_ID);

        Call<LoginModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .getProfile(UserId);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                try {

                    LoginModel myLogin = response.body();

                    if (myLogin.getStatus().equalsIgnoreCase("1")){

                        String FName = myLogin.getResult().getFirstName().toString();
                        String Email = myLogin.getResult().getEmail().toString();

                        txt_name.setText(FName);
                        txt_email.setText(Email);

                    }else {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), myLogin.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
