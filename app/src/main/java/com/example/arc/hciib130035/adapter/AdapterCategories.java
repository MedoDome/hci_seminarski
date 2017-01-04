package com.example.arc.hciib130035.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.models.Category;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.network.API;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arc on 29/12/16.
 */

public class AdapterCategories extends BaseAdapter {
        Context context;
        List<Category> categoryList;
        LayoutInflater layoutInflater;
        Category defaultCat;


        public AdapterCategories() {
        }

        public AdapterCategories(Context ctx, List<Category> productList) {
            context = ctx;
            this.layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//            FIX THIS

//            defaultCat = new Category();
//            defaultCat.CategoryId = 0;
//            defaultCat.Description = "Choose Category";
//            defaultCat.Title = "Choose Category";
//
//            if(!productList.contains(defaultCat)) {
//                productList.add(0, defaultCat);
//            }

            this.categoryList = productList;

        }

        static class ListHolder {

           TextView categoryName;
        }

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public Object getItem(int position) {

            return categoryList.get(position);
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
                convertView = layoutInflater.inflate(R.layout.listitem_category, parent, false);
                listHolder.categoryName = (TextView) convertView.findViewById(R.id.lbl_title);

                convertView.setTag(listHolder);
            } else {
                listHolder = (AdapterCategories.ListHolder) convertView.getTag();
            }

//        listHolder.lbl_title.setText(productList.get(position).Title);
            listHolder.categoryName.setText("Title " + categoryList.get(position).Title);


            return convertView;


        }

    }
