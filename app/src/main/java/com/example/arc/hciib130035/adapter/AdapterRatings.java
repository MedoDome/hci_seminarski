package com.example.arc.hciib130035.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.models.Rating;
import com.example.arc.hciib130035.models.User;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by arc on 04/01/17.
 */

public class AdapterRatings extends BaseAdapter {
    Context context;
    List<Rating> ratingList;
    LayoutInflater linflater;
    User tempUser;
    public AdapterRatings(){};
    public AdapterRatings(Context ctx, List<Rating> listofRatings)
    {
        this.context = ctx;
        linflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ratingList = listofRatings;
        this.tempUser = new User();
    }

    static class ListHolder
    {
        TextView rate_user;
        TextView rate_mark;
        TextView rate_message;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AdapterRatings.ListHolder  listHolder;
        if(convertView == null)
        {
            listHolder = new ListHolder();
            convertView = linflater.inflate(R.layout.listitem_ratings, parent, false);
            listHolder.rate_mark = (TextView)convertView.findViewById(R.id.rate_mark);
            listHolder.rate_message = (TextView)convertView.findViewById(R.id.rate_message);
            listHolder.rate_user = (TextView)convertView.findViewById(R.id.rate_user);

            convertView.setTag(listHolder);
        }
        else
        {
            listHolder = (ListHolder) convertView.getTag();
        }

        API.getServices.getAppApiServices().GetById(ratingList.get(position).getUserId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponseUserCall: " + new Gson().toJson(response));
                Log.d(TAG, "onResponseUserCall: "  + new Gson().toJson(response.body()));
                if(response.body() != null) {
                    tempUser = response.body();
                    listHolder.rate_mark.setText("Rate: " + String.valueOf(ratingList.get(position).getMark()));
                    listHolder.rate_message.setText("Message: " + String.valueOf(ratingList.get(position).getDescription()));
                    listHolder.rate_user.setText(String.valueOf(tempUser.getUsername()));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailureUserCall: " + new Gson().toJson(t));
            }
        });


        return  convertView;
    }

    @Override
    public int getCount() {
        return ratingList.size();
    }

    @Override
    public Object getItem(int position) {
        return ratingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ratingList.get(position).getRatingId();
    }

}
