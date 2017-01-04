package com.example.arc.hciib130035.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arc.hciib130035.MainActivity2;
import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.adapter.AdapterListArticle;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.models.Rating;
import com.example.arc.hciib130035.models.User;
import com.example.arc.hciib130035.models.UserImage;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arc on 30/12/16.
 */

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    ImageView userPicture;
    TextView username;
    TextView lastname;
    TextView firstname;
    TextView rating;
    ListView listViewProduct;

    User loggedUser;
    UserImage picLoggedUser;

    ProgressDialog progresD;

    AdapterListArticle adapterProduct;
    List<Product> productList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        ((MainActivity2) getActivity()).setActionBarTitle("BuyBay - Profile");



        //INIT

        userPicture = (ImageView) view.findViewById(R.id.user_picture);
        username = (TextView) view.findViewById(R.id.user_username);
        lastname = (TextView) view.findViewById(R.id.user_lastname);
        firstname = (TextView) view.findViewById(R.id.user_firstname);
        rating = (TextView) view.findViewById(R.id.user_rating);
        
        listViewProduct = (ListView) view.findViewById(R.id.lv_productsList);
        loggedUser = (User) APIHelper.getApiHelper().getUser();
        picLoggedUser = (UserImage) APIHelper.getLoggedUserImage();

        //Loading bar
        progresD = new ProgressDialog(getActivity());
        progresD.setTitle("Loading");
        progresD.setMessage("Profile is loading");


        //BIND

        username.setText("Username: " + loggedUser.getUsername());
        lastname.setText("Lastname: " + loggedUser.getLastName());
        firstname.setText("Firstname: " + loggedUser.getFirstName());
        Picasso.with(getActivity()).load(API.getServices.getApiBaseUrl() + API.getServices.getApiBasePIC() +
                picLoggedUser.FileName +
                picLoggedUser.Extension).into(userPicture);
        
        API.getServices.getAppApiServices().getRatingByUserId(loggedUser.getUserId()).enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                if(response.body() != null || response.code() != 404)
                {
                    List<Rating> tempRate = new ArrayList<Rating>();
                    tempRate = response.body();
                    Float tempRateFloat = new Float(0);
                    for (Rating item: tempRate) {
                        tempRateFloat += item.getMark();
                    }
                    tempRateFloat /= tempRate.size();
                    rating.setText("User rating: " + String.valueOf(tempRateFloat));
                }
                else
                {
                    rating.setText("User rating: 0");
                }
            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {

            }
        });

        progresD.show();
        API.getServices.getAppApiServices().getSearch("null", "-1",String.valueOf(APIHelper.getApiHelper().getLoggedUserId())).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG, "onResponseUserProfileProduct: " + new Gson().toJson(response));
                progresD.dismiss();
                if(response.body() != null)
                {
                    productList = new ArrayList<Product>();
                    productList = response.body();
                    adapterProduct = new AdapterListArticle();
                    adapterProduct = new AdapterListArticle(getActivity(), productList);
                    adapterProduct.notifyDataSetChanged();
                    listViewProduct.setAdapter(adapterProduct);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progresD.dismiss();
                Toast.makeText(getActivity(), "onFailtureUserProfileProduct", Toast.LENGTH_SHORT).show();

            }
        });

        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        return view;
    }
}
