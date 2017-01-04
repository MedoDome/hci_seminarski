package com.example.arc.hciib130035.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.models.Order;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by arc on 01/01/17.
 */

public class RateNewFragment extends Fragment {
    Order order;
    Spinner spinner;
    Button btn_submit;
    EditText message;
    Bundle bundle;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rnfView = inflater.inflate(R.layout.ratenew_fragment,container,false);

        final android.app.FragmentManager fm = getFragmentManager();

        spinner = (Spinner) rnfView.findViewById(R.id.spinner_marks);
        message = (EditText) rnfView.findViewById(R.id.et_description);
        btn_submit = (Button) rnfView.findViewById(R.id.btn_rateSubmit);
        Integer[] items = new Integer[]{1,2,3,4,5};


        bundle = getArguments();
        order = (Order) bundle.getSerializable("Order");
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                {
                    JsonObject jo = new JsonObject();

                    jo.addProperty("UserId",String.valueOf(APIHelper.getApiHelper().getLoggedUserId()));
                    jo.addProperty("OrderCode",order.getOrderCode());
                    jo.addProperty("OrderId",order.getOrderId());
                    jo.addProperty("Mark",spinner.getSelectedItemPosition() + 1);
                    jo.addProperty("Description",message.getText().toString());

                    API.getServices.getAppApiServices().postRating(jo).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.d(TAG, "onResponseRateNewFragment: " + new Gson().toJson(response));
                            if(response.body() != null)
                            {
                                Log.d(TAG, "onResponseRateNewFragmentBody: " + new Gson().toJson(response.body()));
                                fm.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d(TAG, "onFailureRateNewFragment: " + new Gson().toJson(t));
                            Toast.makeText(getActivity(), "onFailureRateNewFragment", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return rnfView;
    }

    private boolean validateInput() {
        if(message.getText().toString().matches(""))
        {
            Toast.makeText(getActivity(), "Please provide message", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
