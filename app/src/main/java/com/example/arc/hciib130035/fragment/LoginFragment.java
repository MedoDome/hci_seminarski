package com.example.arc.hciib130035.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arc.hciib130035.MainActivity2;
import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.models.User;
import com.example.arc.hciib130035.models.UserImage;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arc on 29/12/16.
 */

public class LoginFragment extends Fragment {

    EditText username;
    EditText password;
    Button btn_logIn;
    private static final String TAG = "LoginFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View logIn = inflater.inflate(R.layout.login_fragment, container, false);
        username = (EditText) logIn.findViewById(R.id.usernameInput);
        password = (EditText) logIn.findViewById(R.id.passwordInput);
        btn_logIn = (Button) logIn.findViewById(R.id.btn_logIn);

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString();
                String psd = password.getText().toString();
                JsonObject jo = new JsonObject();
                jo.addProperty("Username", usr);
//                jo.addProperty("password", psd);
                Log.d(TAG, "testJson: " + new Gson().toJson(jo));

                API.getServices.getAppApiServices().GetByUsername(usr).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, final Response<User> response) {
                        Log.d(TAG, "onLoginFragmentReponse: " + new Gson().toJson(response));
                        if (response.body() != null) {


                            APIHelper.getApiHelper().setUser(response.body());

                            //GET USER PIC

                            API.getServices.getAppApiServices().getUserImage(String.valueOf(APIHelper.getApiHelper().getLoggedUserId())).enqueue(new Callback<UserImage>() {
                                @Override
                                public void onResponse(Call<UserImage> call, Response<UserImage> response) {
                                    Log.d(TAG, "onResponseOfUserImage: " + new Gson().toJson(response));
                                    if (response.body() != null) {
                                        APIHelper.setLoggedUserImage(response.body());
                                        Toast.makeText(getActivity(), "UserImageInserted", Toast.LENGTH_SHORT).show();
                                        }
                                    }



                                @Override
                                public void onFailure(Call<UserImage> call, Throwable t) {
                                    Log.d(TAG, "onFailureOfUserImage: " + new Gson().toJson(response));
                                }
                            });
                            startHomeScreen();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }


                });

//                API.getServices.getAppApiServices().GetAll().enqueue(new Callback<List<User>>() {
//                    @Override
//                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                        Log.d(TAG, "onResponse2: " + new Gson().toJson(response));
//                        startHomeScreen();
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<User>> call, Throwable t) {
//
//                    }
//
//
//                });


//
//                API.getServices.getAppApiServices().GetByUsername(jo).enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        if(response != null)
//                        {
//                            if(response.body() != null) {
//                                APIHelper.getApiHelper().setUser(response.body());
//
//                                Log.d(TAG, "onResponse: " + new Gson().toJson(response));
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//                        Toast.makeText(getActivity(), "API ERROR", Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, "onFailure: "+ t.getMessage());
//                    }
//                });
            }
        });

        setCredentials();
        return logIn;
    }

    private void setCredentials()
    {
        username.setText("admin");
        password.setText("admin");


    }

    private void startHomeScreen()
    {
        Intent intent = new Intent(getActivity(), MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);

//        getActivity().getFragmentManager().beginTransaction().replace(R.id.fl_Main, new HomePageFragment()).addToBackStack(new LoginFragment().getClass().getName()).commit();

    }

}
