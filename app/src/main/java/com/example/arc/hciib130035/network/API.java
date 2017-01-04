package com.example.arc.hciib130035.network;

import com.example.arc.hciib130035.models.Category;
import com.example.arc.hciib130035.models.Order;
import com.example.arc.hciib130035.models.Product;
import com.example.arc.hciib130035.models.ProductImage;
import com.example.arc.hciib130035.models.Rating;
import com.example.arc.hciib130035.models.Status;
import com.example.arc.hciib130035.models.User;
import com.example.arc.hciib130035.models.UserImage;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by arc on 29/12/16.
 */

public interface API {
//    String API_BASE_URL = "http://192.168.0.69:12881/";  //HOME
   String API_BASE_URL = "http://192.168.43.205:12881/";  //PHONE
    String API_BASE_PIC = "/ProductImages/";




    @GET("api/User/GetByUsername/{userId}")
    Call<User> GetByUsername(@Path("userId") String productname );

    @GET("api/User/GetById/{userId}")
    Call<User> GetById(@Path("userId") Integer userId );

    @Headers("Content-Type: application/json")
    @GET("api/User/GetAll/")
    Call<List<User>> GetAll();


    @GET("api/UserPicture/{id}")
    Call<UserImage> getUserImage(@Path("id") String id);

    // PRODUCTS

    @Headers("Content-Type: application/json")
    @GET("api/Product/")
    Call<List<Product>> ProductGetAll();

    @Headers("Content-Type: application/json")
    @POST("api/Product/")
    Call<ResponseBody> postProduct(@Body JsonObject Json);

    @GET("api/Product/GetProductList/{productname}/{categoryId}/{userId}")
    Call<List<Product>> getSearch(@Path("productname") String productname, @Path("categoryId") String categoryId, @Path("userId") String userId);

    @GET("api/Product/GetProductById/{id}")
    Call<Product> getProductById(@Path("id") String id);

    //ORDERS

    @Headers("Content-Type: application/json")
    @POST("api/Order/")
    Call<ResponseBody> postOrder(@Body JsonObject Json);

    @Headers("Content-Type: application/json")
    @POST("api/Order/EditStatus")
    Call<ResponseBody> postOrderEditStatus(@Body JsonObject Json);

    @GET("api/Order/GetByOrderCode/{code}")
    Call<Order> getOrderByCode(@Path("code") String code);

    @GET("api/Order/GetByBuyerId/{code}")
    Call<List<Order>> getOrderByBuyerId(@Path("code") String code);

    @GET("api/Order/GetSellList/{userId}/{statusId}")
    Call<List<Order>> getSellList(@Path("userId") String userId, @Path("statusId") String statusId );



    @Headers("Content-Type: application/json")
    @GET("api/Category/")
    Call<List<Category>> getAllCategories();

    @GET("api/Rating/GetByOrderCode/{code}")
    Call<Rating> postGetRatingByOrderCode(@Path("code") String code);

    @GET("api/Rating/GetUserRating/{id}")
    Call<List<Rating>> getRatingByUserId(@Path("id") Integer id);

    @GET("api/Rating/GetProductRating/{id}")
    Call<List<Rating>> getProductRating(@Path("id") Integer id);

    @Headers("Content-Type: application/json")
    @POST("api/Rating/")
    Call<ResponseBody> postRating(@Body JsonObject Json);

    @GET("api/Status/")
    Call<List<Status>> getStatuses();

    class getServices {


        public static String getApiBaseUrl() {
            return API_BASE_URL;
        }
        public static String getApiBasePIC() {
            return API_BASE_PIC;
        }

        private static Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

//odavde


        //        private static HttpLoggingInterceptor headerLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);
        private static HttpLoggingInterceptor bodyLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//        private static HttpLoggingInterceptor AllLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.);

//

        private static Retrofit medoRetrofit = null;
        public static API AppApiServices = null;
        private static OkHttpClient.Builder httpClient2 = new OkHttpClient.Builder().addInterceptor(bodyLoggingInterceptor);

        public static Retrofit getMedoRetrofit() {
            if (medoRetrofit == null) {
                httpClient2.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder requestBuilder = request.newBuilder()
                                .method(request.method(), request.body());
                        Request request1 = requestBuilder.build();
                        return chain.proceed(request1);

                    }
                });
//                        .addInterceptor(headerLoggingInterceptor);
                OkHttpClient client = httpClient2.build();
                medoRetrofit = builder.client(client).build();

                return medoRetrofit;
            } else {
                return medoRetrofit;
            }
        }

        public static API getAppApiServices() {
            if (AppApiServices == null) {
                AppApiServices = getMedoRetrofit().create(API.class);
                return AppApiServices;
            } else {
                return AppApiServices;
            }

        }

    }

}
