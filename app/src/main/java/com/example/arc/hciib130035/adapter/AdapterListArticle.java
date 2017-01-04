package com.example.arc.hciib130035.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.network.API;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arc on 29/12/16.
 */

public class AdapterListArticle extends BaseAdapter {
    Context context;
    List<Product> productList;
    LayoutInflater layoutInflater;

    public AdapterListArticle() {
    }

    public AdapterListArticle(Context ctx, List<Product> productList) {
        context = ctx;
        this.layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.productList = productList;

    }

    static class ListHolder {

        TextView lbl_title;
        TextView lbl_price;
        ImageView pictureArticle;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {

        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ListHolder listHolder;
        if (convertView == null) {
            listHolder = new ListHolder();
            convertView = layoutInflater.inflate(R.layout.listitem_articles, parent, false);
            listHolder.lbl_title = (TextView) convertView.findViewById(R.id.lbl_title);
            listHolder.lbl_price = (TextView) convertView.findViewById(R.id.lbl_price);
            listHolder.pictureArticle = (ImageView) convertView.findViewById(R.id.pictureArticle);
            convertView.setTag(listHolder);
        } else {
            listHolder = (ListHolder) convertView.getTag();
        }

//        listHolder.lbl_title.setText(productList.get(position).Title);
          listHolder.lbl_title.setText(productList.get(position).Title);
          listHolder.lbl_price.setText("Price: " +String.valueOf(productList.get(position).Price + "\n" + productList.get(position).getCategoryName() ));
          Picasso.with(context).load(API.getServices.getApiBaseUrl() + API.getServices.getApiBasePIC() +
                productList.get(position).ProductImages.get(0).FileName +
                productList.get(position).ProductImages.get(0).Extension).into(listHolder.pictureArticle);

        return convertView;


    }
}
