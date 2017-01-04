package com.example.arc.hciib130035.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arc.hciib130035.R;
import com.example.arc.hciib130035.Utils.APIHelper;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arc on 01/01/17.
 */

public class OrderNewFragment extends Fragment {

    private static final String TAG = "OrderNewFragment";
    Button btn_order;
    EditText message;

    Bundle bundle;
    Product product;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View onfView = inflater.inflate(R.layout.ordernew_fragment,container,false);
        btn_order = (Button) onfView.findViewById(R.id.btn_order);
        message = (EditText) onfView.findViewById(R.id.et_message);

        bundle = getArguments();
        product = (Product)bundle.getSerializable("Product");


        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString().matches(""))
                {


                JsonObject jo = new JsonObject();
                jo.addProperty("Message", message.getText().toString());
                jo.addProperty("UserId",String.valueOf(APIHelper.getApiHelper().getLoggedUserId()));
                jo.addProperty("ProductId",String.valueOf(product.getProductId()));
                jo.addProperty("Price",String.valueOf(product.getPrice()));

                API.getServices.getAppApiServices().postOrder(jo).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d(TAG, "onResponseOrderNewFragment: " + new Gson().toJson(response));
                        if(response.body() != null)
                        {
                            Toast.makeText(getActivity(), "ResponseORderNewFragmentSUccess", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailOrderNewFragment: " + new Gson().toJson(t));

                    }
                });
                }
                else
                {
                    Toast.makeText(getActivity(), "Provide message", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return onfView;
    }
}
