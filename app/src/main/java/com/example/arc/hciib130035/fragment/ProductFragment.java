package com.example.arc.hciib130035.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.adapter.AdapterRatings;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.models.Rating;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by arc on 29/12/16.
 */

public class ProductFragment extends Fragment {

    ImageView picture;
    TextView title;
    TextView price;
    TextView owner;
    TextView desc;
    TextView category;
    Button btn_order;
    AdapterRatings adapterRatings;

    List<Rating> ratingList;
    ListView lv_ratingList;
    Bundle bundle;
    Product product;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View productView = inflater.inflate(R.layout.product_fragmet, container , false);
        title=(TextView)productView.findViewById(R.id.lbl_title);
        price=(TextView)productView.findViewById(R.id.lbl_price);
        owner=(TextView)productView.findViewById(R.id.lbl_owner);
        desc=(TextView)productView.findViewById(R.id.lbl_description);
        category=(TextView)productView.findViewById(R.id.lbl_category);
        btn_order = (Button) productView.findViewById(R.id.btn_order);
        picture = (ImageView)productView.findViewById(R.id.productImage);
        lv_ratingList = (ListView)productView.findViewById(R.id.lv_ratingList);

        bundle = getArguments();
        product = (Product)bundle.getSerializable("Product");

        bindData(product);

        API.getServices.getAppApiServices().getProductRating(product.getProductId()).enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                if(response.body() != null)
                {
                    ratingList = new ArrayList<Rating>();
                    ratingList = response.body();

                   adapterRatings = new AdapterRatings();
                    adapterRatings = new AdapterRatings(getActivity(), ratingList);
                    adapterRatings.notifyDataSetChanged();

                    lv_ratingList.setAdapter(adapterRatings);
                }
            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {
                Log.d(TAG, "onFailureRating: " + new Gson().toJson(t));
            }
        });
        if(APIHelper.getApiHelper().getLoggedUserId() == product.getUserId())
        {
            btn_order.setVisibility(View.GONE);
        }
        Log.d(TAG, "onCreateViewProductDetail: " + new Gson().toJson(product.getUserId() + " " +APIHelper.getApiHelper().getLoggedUserId()));
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product",product);

                OrderNewFragment onf = new OrderNewFragment();
                onf.setArguments(bundle);

                getActivity().getFragmentManager().beginTransaction().replace(R.id.flyout, onf).commit();

            }
        });
        return productView;

    }

    private void bindData(Product product) {
        this.title.setText("Title: " + product.getTitle());
        this.price.setText("Price: " + String.valueOf(product.getPrice()));
        this.owner.setText("Owner: " + product.getOwnerUsername());
        this.desc.setText("Description: " + product.getDescription());
        this.category.setText("Category: " + APIHelper.getGlobalCatList().get(product.getCategoryId()).Title );
        Picasso.with(getActivity()).load(API.getServices.getApiBaseUrl() + API.getServices.getApiBasePIC() +
                product.ProductImages.get(0).FileName +
                product.ProductImages.get(0).Extension).into(picture);


    }
}
