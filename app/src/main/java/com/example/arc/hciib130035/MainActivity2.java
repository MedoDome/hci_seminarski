package com.example.arc.hciib130035;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arc.hciib130035.Utils.GlobalVars;
import com.example.arc.hciib130035.fragment.HomePageFragment;
import com.example.arc.hciib130035.fragment.LoginFragment;
import com.example.arc.hciib130035.fragment.OrdersFragment;
import com.example.arc.hciib130035.fragment.ProductNewFragment;
import com.example.arc.hciib130035.fragment.ProfileFragment;
import com.example.arc.hciib130035.fragment.SalesFragment;
import com.example.arc.hciib130035.models.Status;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("BuyBay");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.flyout, new ProductNewFragment()).commit();

            }
        });
        //INIT

        bindStatuses();



        // MENU

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // END

        getFragmentManager().beginTransaction().add(R.id.flyout, new HomePageFragment()).commit();


    }

    private void bindStatuses() {
        if (GlobalVars.statusList == null) {
            API.getServices.getAppApiServices().getStatuses().enqueue(new Callback<List<Status>>() {
                @Override
                public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                    Log.d(TAG, "onResponseStatus: " + new Gson().toJson(response));
                    if (response.body() != null) {
                        GlobalVars.statusList = new ArrayList<Status>();
                        GlobalVars.statusList = response.body();
                    }
                }

                @Override
                public void onFailure(Call<List<Status>> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
////            getFragmentManager().beginTransaction().replace()
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getFragmentManager().beginTransaction().replace(R.id.flyout, new HomePageFragment()).commit();

        }
        else if (id == R.id.nav_profile) {
            getFragmentManager().beginTransaction().replace(R.id.flyout, new ProfileFragment()).commit();

        } else if (id == R.id.nav_orders) {
                getFragmentManager().beginTransaction().replace(R.id.flyout, new OrdersFragment()).commit();
        } else if (id == R.id.nav_sales) {
            getFragmentManager().beginTransaction().replace(R.id.flyout, new SalesFragment()).commit();

        } else if (id == R.id.nav_logout) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String title){
        setTitle(title);
    }

    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

}
