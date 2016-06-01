package com.bikee.www.etc.manager;

import com.bikee.www.etc.dao.IAmPortSendObject;
import com.bikee.www.etc.dao.IAmPortReceiveObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by User on 2016-04-25.
 */
public class IAmPortNetworkManager {
    private static IAmPortNetworkManager iAmPortNetworkManager;
    private ServerUrl serverUrl;
    private OkHttpClient client;

    private IAmPortNetworkManager() {
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

    public static IAmPortNetworkManager getInstance() {
        if (iAmPortNetworkManager == null)
            iAmPortNetworkManager = new IAmPortNetworkManager();
        return iAmPortNetworkManager;
    }

    public interface ServerUrl {
        String baseUrl = "https://api.iamport.kr";
//        String baseUrl = "http://192.168.0.8:3000";

        @POST("/payments/prepare")
        Call<IAmPortReceiveObject> prepayment(@Query("_token") String token,
                                              @Body IAmPortSendObject iAmPortSendObject);
    }

    public class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String date = json.getAsString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            try {
                return format.parse(date.replaceAll("Z$", "+0000"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void prepayment(String token,
                           IAmPortSendObject iAmPortSendObject,
                           Stack<Call> callStack,
                           Callback<IAmPortReceiveObject> callback) {
        Call<IAmPortReceiveObject> call = serverUrl.prepayment(token, iAmPortSendObject);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }
}
