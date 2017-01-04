package com.example.arc.hciib130035.Utils;

import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.arc.hciib130035.models.Category;
import com.example.arc.hciib130035.models.Status;
import com.example.arc.hciib130035.network.API;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by arc on 02/01/17.
 */

public class GlobalVars {
    public static List<Status> statusList;
    public static List<Category> categoryList;



}
