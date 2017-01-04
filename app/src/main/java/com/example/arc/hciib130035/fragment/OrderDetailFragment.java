package com.example.arc.hciib130035.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.models.Order;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.models.Rating;
import com.example.arc.hciib130035.models.User;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by arc on 01/01/17.
 */

public class OrderDetailFragment extends Fragment {

    Button btn_rate;
    Button btn_accept;
    Button btn_decline;
    TextView orderCode;
    TextView buyer;
    TextView message;
    TextView ratePoint;
    TextView rateMessage;

    User usrTMP;

    ProgressDialog progresD;

    Bundle bundle;
    Order order;
    Rating rateMark;
    Product product;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View odfView = inflater.inflate(R.layout.orderdetail_fragment, container, false);
        final android.app.FragmentManager fm = getFragmentManager();

        //Loading bar
        progresD = new ProgressDialog(getActivity());
        progresD.setTitle("Loading");
        progresD.setMessage("Profile is loading");
//        progresD.show();

        btn_rate = (Button) odfView.findViewById(R.id.btn_rate);
        btn_accept = (Button) odfView.findViewById(R.id.btn_accept);
        btn_decline = (Button) odfView.findViewById(R.id.btn_decline);

        orderCode = (TextView) odfView.findViewById(R.id.tv_ordercode);
        buyer = (TextView) odfView.findViewById(R.id.tv_buyer);
        message = (TextView) odfView.findViewById(R.id.tv_message);
        ratePoint = (TextView) odfView.findViewById(R.id.tv_ratePoint);
        rateMessage = (TextView) odfView.findViewById(R.id.tv_rateMessage);

        bundle = getArguments();
        order = (Order) bundle.getSerializable("Order");



        API.getServices.getAppApiServices().getProductById(String.valueOf(order.getProductId())).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Log.d(TAG, "onResponseOrderDetailFragment: " + new Gson().toJson(response));

                if(response.body() != null) {
                    product = response.body();

                    Log.d(TAG, "onResponseOrderDetailFragment: " + new Gson().toJson(response));
                    Log.d(TAG, "onResponseOrderDetailFragment: " + new Gson().toJson(response.body()));
                    API.getServices.getAppApiServices().GetById(order.getUserId()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.body() != null)
                            {
                                usrTMP = response.body();
                                bindData(order);

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d(TAG, "onFailureOrderDetailFragment: " + new Gson().toJson(t));
            }
        });
        Log.d(TAG, "onCreateView: " + new Gson().toJson(product));


        btn_rate.setVisibility(View.GONE);
        btn_accept.setVisibility(View.GONE);
        btn_decline.setVisibility(View.GONE);

        if(order.getUserId() != APIHelper.getApiHelper().getLoggedUserId() && order.getStatusId() == 1)
        {
            btn_accept.setVisibility(View.VISIBLE);
            btn_decline.setVisibility(View.VISIBLE);
        }


        //CHECK OWNER

        //CHECK RATING
        if(order.getUserId() == APIHelper.getApiHelper().getLoggedUserId()) {
            API.getServices.getAppApiServices().postGetRatingByOrderCode(order.getOrderCode()).enqueue(new Callback<Rating>() {
                @Override
                public void onResponse(Call<Rating> call, Response<Rating> response) {
                    Log.d(TAG, "onResponseRating: " + new Gson().toJson(response));
                    Log.d(TAG, "onResponseRating: " + new Gson().toJson(response.body()));
                    if (response.body() == null || response.code() == 404) {

                        if (order.getStatusId() == 2) {
                            btn_rate.setVisibility(View.VISIBLE);


                        }
                    }
                    else
                    {
                        btn_rate.setVisibility(View.GONE);
                        Rating tempRate;
                        tempRate = response.body();

                        ratePoint.setText("Your rate: " + String.valueOf(tempRate.getMark()));
                        rateMessage.setText("Your message: " + String.valueOf(tempRate.getDescription()));
                    }
                }

                @Override
                public void onFailure(Call<Rating> call, Throwable t) {
                    Log.d(TAG, "onFailureResponse: " + new Gson().toJson(t));
                }
            });
        }
//        progresD.dismiss();
        //BTN ONCLICK
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObject jo = new JsonObject();
                jo.addProperty("OrderCode", order.getOrderCode());
                jo.addProperty("Message", "null");
                jo.addProperty("StatusId", "2");
                jo.addProperty("Status", "null");
                jo.addProperty("UserId", "0");
                jo.addProperty("User", "null");

                API.getServices.getAppApiServices().postOrderEditStatus(jo).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getActivity(), "You accepted offer", Toast.LENGTH_SHORT).show();
                        fm.popBackStack();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Order not accepted API FAIL", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jo = new JsonObject();
                jo.addProperty("OrderCode", order.getOrderCode());
                jo.addProperty("Message", "null");
                jo.addProperty("StatusId", "3");
                jo.addProperty("Status", "null");
                jo.addProperty("UserId", "0");
                jo.addProperty("User", "null");

                API.getServices.getAppApiServices().postOrderEditStatus(jo).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getActivity(), "You declined offer", Toast.LENGTH_SHORT).show();
                        fm.popBackStack();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Order not declined API FAIL", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Order", order);

                RateNewFragment rnf = new RateNewFragment();
                rnf.setArguments(bundle);

                getActivity().getFragmentManager().beginTransaction().add(R.id.flyout, rnf).commit();
            }
        });
        return odfView;
    }

    private void bindData(Order order) {
        orderCode.setText("OrderCode: " + order.getOrderCode());
        if(bundle.getString("Type").equals("Sales"))
            buyer.setText("Buyer: " + usrTMP.getUsername());
        else if(bundle.getString("Type").equals("Order"))
            buyer.setText("Seller: " +  product.getOwnerUsername());

        message.setText("Message: " + order.getMessage());
    }
}
