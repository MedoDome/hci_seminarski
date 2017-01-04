package com.example.arc.hciib130035.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arc.hciib130035.MainActivity2;
import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.adapter.AdapterListOrders;
import com.example.arc.hciib130035.models.Order;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arc on 30/12/16.
 */

public class OrdersFragment extends Fragment {

    private static final String TAG = "OrdersFragment" ;
    View ordersView;


    List<Order> orderList;
    AdapterListOrders adapterOrders;
    ListView productListView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       ordersView = inflater.inflate(R.layout.order_fragmet,container,false);
        ((MainActivity2) getActivity()).setActionBarTitle("BuyBay - Orders");

//        orderCode = (TextView) ordersView.findViewById(R.id.lbl_code);
//        status = (TextView) ordersView.findViewById(R.id.lbl_status);
//        productName = (TextView) ordersView.findViewById(R.id.lbl_product);
//        price = (TextView) ordersView.findViewById(R.id.lbl_price);
        productListView = (ListView) ordersView.findViewById(R.id.list_orders);

        API.getServices.getAppApiServices().getOrderByBuyerId(String.valueOf(APIHelper.getApiHelper().getLoggedUserId())).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                Log.d(TAG, "onResponseOrdersSuccess: " + new  Gson().toJson(response));
                if(response.body() != null)
                {
                    orderList = new ArrayList<Order>();
                    orderList = response.body();
                    adapterOrders = new AdapterListOrders();
                    adapterOrders = new AdapterListOrders(getActivity(), orderList);

                    adapterOrders.notifyDataSetChanged();
                    productListView.setAdapter(adapterOrders);

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.d(TAG, "onResponseOrdersFAIL: " + new Gson().toJson(t.getMessage()) );
                Log.d(TAG, "onResponseOrdersFAILWHOLET: " + new Gson().toJson(t) );
                Toast.makeText(getActivity(), "FTS", Toast.LENGTH_SHORT).show();

            }


        });

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order od = new Order();
                od = (Order) orderList.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("Order",od);
                bundle.putSerializable("Type","Order");

                OrderDetailFragment odf = new OrderDetailFragment();
                odf.setArguments(bundle);

                getActivity().getFragmentManager().beginTransaction().replace(R.id.flyout, odf).addToBackStack(new OrdersFragment().getClass().getName()).commit();


            }
        });
        return ordersView;
    }
}
