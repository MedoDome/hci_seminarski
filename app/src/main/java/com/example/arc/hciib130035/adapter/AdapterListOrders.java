package com.example.arc.hciib130035.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.GlobalVars;
import com.example.arc.hciib130035.models.Order;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.network.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arc on 31/12/16.
 */

public class AdapterListOrders extends BaseAdapter {

    Context context;
    List<Order> orderList;
    LayoutInflater layoutInflater;

    public AdapterListOrders(){};
    public AdapterListOrders(Context ctx, List<Order> listofOrders)
    {
        this.context = ctx;
        this.layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orderList = listofOrders;
    }

    static class ListHolder
    {
        TextView orderCode;
        TextView status;
        TextView productName;
        TextView price;

    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       final ListHolder listHolder;
        if(convertView == null)
        {
            listHolder = new ListHolder();
            convertView = layoutInflater.inflate(R.layout.listitem_order, parent, false);
            listHolder.orderCode = (TextView) convertView.findViewById(R.id.lbl_code);
            listHolder.price = (TextView) convertView.findViewById(R.id.lbl_price);
            listHolder.productName = (TextView) convertView.findViewById(R.id.lbl_product);
            listHolder.status = (TextView) convertView.findViewById(R.id.lbl_status);

            convertView.setTag(listHolder);
        }
        else
        {
            listHolder = (ListHolder) convertView.getTag();
        }
        API.getServices.getAppApiServices().getProductById(String.valueOf(orderList.get(position).getProductId())).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.body() != null) {


                    listHolder.status.setText("Status: " + GlobalVars.statusList.get(orderList.get(position).getStatusId() - 1).getTitle());
                    listHolder.productName.setText("Product: " + ((Product) response.body()).getTitle().toString());
                    listHolder.orderCode.setText("Ordercode: " + orderList.get(position).getOrderCode());
                    listHolder.price.setText("Price: " + Float.toString(orderList.get(position).getPrice()));
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        return convertView;
    }
}
