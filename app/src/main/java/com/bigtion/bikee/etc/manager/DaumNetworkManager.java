package com.bigtion.bikee.etc.manager;

import com.bigtion.bikee.etc.dao.GetAddressReceiveObject;
import com.bigtion.bikee.etc.dao.GetGeoLocationReceiveObject;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Stack;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 2016-06-02.
 */
public class DaumNetworkManager {
    private static DaumNetworkManager daumNetworkManager;
    private ServerUrl serverUrl;
    private OkHttpClient client;

    private DaumNetworkManager() {
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerUrl.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        serverUrl = retrofit.create(ServerUrl.class);
    }

    public static DaumNetworkManager getInstance() {
        if (daumNetworkManager == null)
            daumNetworkManager = new DaumNetworkManager();
        return daumNetworkManager;
    }

    public interface ServerUrl {
        String baseUrl = "https://apis.daum.net";

        // 주소 얻기
        @GET("/local/geo/coord2addr")
        Call<GetAddressReceiveObject> getAddress(@Query("apikey") String apikey,
                                                 @Query("longitude") double longitude,
                                                 @Query("latitude") double latitude,
                                                 @Query("inputCoordSystem") String inputCoordSystem,
                                                 @Query("output") String output);

        // 좌표 얻기
        @GET("/local/geo/addr2coord")
        Call<GetGeoLocationReceiveObject> getGeoLocation(@Query("apikey") String apikey,
                                                         @Query("q") String q,
                                                         @Query("output") String output);
    }

    // 주소 얻기
    public void getAddress(double longitude,
                           double latitude,
                           Stack<Call> callStack,
                           Callback<GetAddressReceiveObject> callback) {
        Call<GetAddressReceiveObject> call =
                serverUrl.getAddress(
                        "8146b049424c9ebef9f73d68f3910f0c",
                        longitude,
                        latitude,
                        "WGS84",
                        "json"
                );
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 좌표 얻기
    public void getGeoLocation(String q,
                               Stack<Call> callStack,
                               Callback<GetGeoLocationReceiveObject> callback) {
        Call<GetGeoLocationReceiveObject> call =
                serverUrl.getGeoLocation(
                        "8146b049424c9ebef9f73d68f3910f0c",
                        q,
                        "json"
                );
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }
}
