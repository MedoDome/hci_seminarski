package com.example.arc.hciib130035.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arc.hciib130035.MainActivity2;
import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.adapter.AdapterCategories;
import com.example.arc.hciib130035.adapter.AdapterListArticle;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.models.User;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arc on 29/12/16.
 */

public class HomePageFragment extends Fragment {


    private static final String TAG = "HomePageFragment";
    ListView productListView;
    AdapterListArticle adapterProduct;

    List<Product> productList;



    Spinner spinner;
    AdapterCategories adapterSpinner;

    ProgressDialog progresD;

    Button btn_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        ((MainActivity2) getActivity()).setActionBarTitle("BuyBay");


        // category dropdown
        spinner = (Spinner) view.findViewById(R.id.spinner_categories);
        adapterSpinner = new AdapterCategories(getActivity(),APIHelper.getGlobalCatList());
        adapterSpinner.notifyDataSetChanged();

        spinner.setAdapter(adapterSpinner);

        //Loading
        progresD = new ProgressDialog(getActivity());
        progresD.setTitle("Loading");
        progresD.setMessage("Button search loading");

       final String giveMe1 = "-1";

        btn_search = (Button) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int categoryid= spinner.getSelectedItemPosition();
                JsonObject jo = new JsonObject();
                jo.addProperty("productname","null");
                jo.addProperty("categoryId",categoryid);
                jo.addProperty("userId",1);

                progresD.show();
//                API.getServices.getAppApiServices().getSearch("null",String.valueOf(categoryid), "-1").enqueue(new Callback<List<Product>>() {
                API.getServices.getAppApiServices().getSearch("null", String.valueOf(categoryid+1) , giveMe1).enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        Toast.makeText(getActivity(), "Refresh button RESMONSE not null", Toast.LENGTH_SHORT).show();
                        progresD.dismiss();
                        if(response.body() != null) {
                            Toast.makeText(getActivity(), "Refresh button RESMONSE not null", Toast.LENGTH_SHORT).show();
                            productList = new ArrayList<Product>();
                            productList = response.body();
                            adapterProduct = new AdapterListArticle();
                            adapterProduct = new AdapterListArticle(getActivity(), productList);

                            adapterProduct.notifyDataSetChanged();
                            productListView.setAdapter(adapterProduct);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Refresh button FAILED", Toast.LENGTH_SHORT).show();
                        progresD.dismiss();
                    }
                });

            }
        });

        productListView = (ListView) view.findViewById(R.id.lv_productsList);
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product temp = new Product();
                temp = (Product) productList.get(position);
                Log.d(TAG, "onItemClick: " + temp.getUserId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product",temp);

                ProductFragment pfm = new ProductFragment();

                pfm.setArguments(bundle);

                getActivity().getFragmentManager().beginTransaction().replace(R.id.flyout, pfm).addToBackStack(new HomePageFragment().getClass().getName()).commit();

            }
        });





        API.getServices.getAppApiServices().ProductGetAll().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG, "***onResponseHomeListProduct: " + new Gson().toJson(response));

                Toast.makeText(getActivity(), "RESMONSE not null", Toast.LENGTH_SHORT).show();

                if(response.body() != null) {
                    Toast.makeText(getActivity(), "RESMONSE not null", Toast.LENGTH_SHORT).show();

                    productList = new ArrayList<Product>();
                    productList = response.body();
                    adapterProduct = new AdapterListArticle();
                    adapterProduct = new AdapterListArticle(getActivity(), productList);

                    adapterProduct.notifyDataSetChanged();
                    productListView.setAdapter(adapterProduct);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG, "***onResponseHomeListProduct: " + new Gson().toJson(t.getMessage()));
                Log.d(TAG, "onFailure: "+new Gson().toJson(t));
                Toast.makeText(getActivity(), "FAILUREEEE", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }
}
