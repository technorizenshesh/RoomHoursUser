package com.example.roomhoursuser.LoginScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomhoursuser.GPSTracker;
import com.example.roomhoursuser.HomeScreen.HomeActivity;
import com.example.roomhoursuser.MainActivityLogin;
import com.example.roomhoursuser.Preference;
import com.example.roomhoursuser.R;
import com.example.roomhoursuser.SignUpScreen.SignUpActivity;
import com.example.roomhoursuser.Utills.RetrofitClients;
import com.example.roomhoursuser.Utills.SessionManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "FaceBook HashKEy" ;
    private TextView txt_newLogin;
    private RelativeLayout RR_login;

    //Google SignIn
    private RelativeLayout RR_faceBook_login;
    private RelativeLayout RR_google_login;
    private SignInButton signInButton;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;

    String android_id ="";
    private ProgressBar progressBar;
    private String email="";
    private String password="";
    private SessionManager sessionManager;

    private EditText edt_User;
    private EditText edt_Password;

    String result="";

    //FaceBook
    CallbackManager mCallbackManager;
    LoginButton loginButton;
    GPSTracker gpsTracker;
    String latitude ="";
    String longitude ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.mehroon));
        }
        progressBar = findViewById(R.id.progressBar);


        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker=new GPSTracker(this);

        if(gpsTracker.canGetLocation()){

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        }else{
            gpsTracker.showSettingsAlert();
        }

        edt_Password = findViewById(R.id.edt_Password);
        edt_User = findViewById(R.id.edt_User);
        RR_faceBook_login=findViewById(R.id.RR_faceBook_login);
        loginButton = findViewById(R.id.connectWithFbButton);

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(this);

        txt_newLogin=findViewById(R.id.txt_allready_registed);
        RR_login=findViewById(R.id.RR_login);
        RR_google_login=findViewById(R.id.RR_google_login);


        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
        RR_faceBook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        txt_newLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        RR_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
           /*     Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);*/
            }
        });

        RR_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

        //Google SignIn
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //FaceBook
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "btnCancel", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Btnerrror", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //   Toast.makeText(Activity_LoginOption.this, ""+token, Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            String UsernAME=user.getDisplayName();
                            String email=user.getEmail();
                            String SocialId=user.getUid();
                            Uri Url=user.getPhotoUrl();

                          /*  Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
*/
                           // Toast.makeText(LoginActivity.this, "Success"+task.getException(), Toast.LENGTH_SHORT).show();

                          if (sessionManager.isNetworkAvailable()) {

                                progressBar.setVisibility(View.VISIBLE);

                              SocialLoginMethod(UsernAME,"123456",email,"fghgh",SocialId);

                            }else {

                                Toast.makeText(LoginActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                            }


                        } else {

                            Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    //Google Login
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            handleSignInResult( result );
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            String UsernAME=account.getDisplayName();
            String email=account.getEmail();
            String SocialId=account.getId();
            Uri Url=account.getPhotoUrl();

         /*   Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();*/

            if (sessionManager.isNetworkAvailable()) {

                progressBar.setVisibility(View.VISIBLE);

                SocialLoginMethod(UsernAME,"123456",email,"fghgh",SocialId);

            }else {

                Toast.makeText(LoginActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText( this, "Login Unsuccessful", Toast.LENGTH_SHORT ).show();

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void validation() {

        email=edt_User.getText().toString();
        password=edt_Password.getText().toString();

        if(!isValidEmail(email))
        {
            Toast.makeText(this, "Please Enter email.", Toast.LENGTH_SHORT).show();


        }else if(password.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter password.", Toast.LENGTH_SHORT).show();

        }else
        {
          /*  Intent intent = new Intent(LoginActivity.this, OtpMatchActivity.class);
            startActivity(intent);*/
            progressBar.setVisibility(View.VISIBLE);
            RR_login.setEnabled(false);
            if (sessionManager.isNetworkAvailable()) {
                callLoginApi();
                // callLoginApiSocial();

            }else {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
            }

        }
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void callLoginApi() {

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .loginApi(email,password,android_id,"42","3242","User");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    RR_login.setEnabled(true);
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    result = jsonObject.getString("result");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    String UserId = resultOne.getString("id");
                    if (status.equalsIgnoreCase("1")){
                        //String userId = jsonObject.getString("user_id");
                        //sessionManager.saveUserId(userId);
                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,UserId);

                        progressBar.setVisibility(View.GONE);
                        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }else {
                        progressBar.setVisibility(View.GONE);
                        RR_login.setEnabled(true);
                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    RR_login.setEnabled(true);
                } catch (IOException e) {
                    RR_login.setEnabled(true);
                    e.printStackTrace();

                }finally {
                   // Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    RR_login.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    //  btn_login.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // btn_login.setEnabled(true);
                RR_login.setEnabled(true);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void SocialLoginMethod(String FirstName,String Password,String email,String register_id,String socialId) {

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .SocialloginApi(FirstName,email,register_id,socialId,latitude,longitude);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    RR_login.setEnabled(true);
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    result = jsonObject.getString("result");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    String UserId = resultOne.getString("id");
                    if (status.equalsIgnoreCase("1")){

                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,UserId);

                        progressBar.setVisibility(View.GONE);
                        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }else {
                        progressBar.setVisibility(View.GONE);
                        RR_login.setEnabled(true);
                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    RR_login.setEnabled(true);
                } catch (IOException e) {
                    RR_login.setEnabled(true);
                    e.printStackTrace();

                }finally {
                   // Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    RR_login.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    //  btn_login.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // btn_login.setEnabled(true);
                RR_login.setEnabled(true);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
