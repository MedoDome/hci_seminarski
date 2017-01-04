package com.example.arc.hciib130035;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.fragment.LoginFragment;
import com.example.arc.hciib130035.models.Category;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    FrameLayout fl_Main;
    android.app.FragmentManager fm = getFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Login");
        fl_Main = (FrameLayout)findViewById(R.id.fl_Main);
        API.getServices.getAppApiServices().getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d(TAG, "onResponse: " + new Gson().toJson(response));
                if(response.body() != null)
                {
                    Toast.makeText(MainActivity.this, "Response Body", Toast.LENGTH_SHORT).show();

                    Log.d(TAG, "onResponseBody: " + new Gson().toJson(response.body()));

                     APIHelper.setGlobalCatList(response.body());
                    Category tempCat = new Category();
                    tempCat.CategoryId = -1;
                    tempCat.Title = "All products";

                    APIHelper.getGlobalCatList().add(0,tempCat);
                    fm.beginTransaction().add(R.id.fl_Main, new LoginFragment()).commit();

                }
                Toast.makeText(MainActivity.this, "Response fail", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAIL CATEGORY", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailureCategory: " + new Gson().toJson(t));
            }
        });


    }
}
