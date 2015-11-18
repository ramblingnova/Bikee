package com.example.tacademy.bikee.etc.manager;

import com.example.tacademy.bikee.etc.dao.Bike;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.Inquires;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class NetworkManager {
    private static NetworkManager networkManager;
    RestAdapter restAdapter;
    private ServerUrl serverUrl;

    private NetworkManager() {
        OkHttpClient okHttpClient = new OkHttpClient();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        okHttpClient.setCookieHandler(cookieManager);
        Client client = new OkClient(okHttpClient);
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(ServerUrl.baseUrl)
                .setConverter(new GsonConverter(gson))
                .setClient(client)
                .build();
        serverUrl = restAdapter.create(ServerUrl.class);
    }

    public static NetworkManager getInstance() {
        if (networkManager == null)
            networkManager = new NetworkManager();
        return networkManager;
    }

    public interface ServerUrl {
        //        String baseUrl = "http://192.168.211.18:3000";
        String baseUrl = "http://bikee.kr.pe";

        // 본인정보조회 app.get('/users/:userId',users.profile) TODO id 없이 "본인정보조회"하기
        @GET("/users/{userId}")
        void selectUser(@Path("userId") String user_id, Callback<ReceiveObject> callback);

        // 회원가입 app.post('/users', users.create)
        @POST("/users")
        void insertUser(@Body User user, Callback<ReceiveObject> callback);

        // 회원수정 app.put('/users/:userId',userAuth,users.edit);
        @PUT("/users")
        void updateUser(@Body User user, Callback<ReceiveObject> callback);

        // 보유자전거등록 app.post('/bikes/users',auth.requiresLogin,bikes.create);
        @Multipart
        @POST("/bikes/users")
        void insertBicycle(@Part("image") TypedFile file1,
                           @Part("image") TypedFile file2,
                           @Part("bike") Bike bike,
                           Callback<ReceiveObject> callback);

        // 보유자전거조회 app.get('/bikes/users',auth.requiresLogin,bikes.myList); 유저아이디는?
        @GET("/bikes/users")
        void selectBicycle(Callback<ReceiveObject> callback);

        // 보유자전거수정 app.put('/bikes/users/:bikeId',bikeAuth,bikes.edit);
        @Multipart
        @PUT("/bikes/users/{bikeId}")
        void updateBicycle(@Path("bikeId") String bike_id, @Part("bike") Bike bike, Callback<ReceiveObject> callback);

        // 보유자전거 활성화/비활성화 app.put('/bikes/active/:bikeId',bikeAuth,bikes.active);
        @PUT("/bikes/active/{bikeId}")
        void updateBicycleOnOff(@Path("bikeId") String bike_id, Callback<ReceiveObject> callback);

        // 보유자전거삭제 app.delete('/bikes/users/:bikeId',bikeAuth,bikes.delete);
        @DELETE("/bikes/users/{bikeId}")
        void deleteBicycle(@Path("bikeId") String bike_id, Callback<ReceiveObject> callback);

        // 전체자전거조회 app.get('/bikes',bikes.index);
        @GET("/bikes")
        void selectAllBicycle(@Query("lat") String lat,
                              @Query("lon") String lon,
                              @Query("start") String start,
                              @Query("end") String end,
                              @Query("type") String type,
                              @Query("height") String height,
                              @Query("component") String component,
                              @Query("smartlock") Boolean smartlock,
                              Callback<ReceiveObject> callback);

        // 자전거상세조회 app.get('/bikes/:bikeId/detail',bikes.detail);
        @GET("/bikes/{bikeId}/detail")
        void selectBicycleDetail(@Path("bikeId") String bike_id, Callback<ReceiveObject> callback);

        // 로그인
        @FormUrlEncoded
        @POST("/users/session")
        void login(@Field("email") String email, @Field("password") String password, Callback<ReceiveObject> callback);

        // 세션클리어
        @GET("/first")
        void sessionClear(Callback<ReceiveObject> callback);

        // 내평가보기 app.get('/comments',auth.requiresLogin,comments.show);
        @GET("/comments")
        void selectUserComment(Callback<ReceiveObject> callback);

        // 자전거후기작성 app.post('/comments/:bikeId',commentAuth,comments.create);
        @POST("/comments/{bikeId}")
        void insertBicycleComment(@Path("bikeId") String bike_id, @Body Comment comments, Callback<ReceiveObject> callback);

        // 자전거후기보기 app.get('/comments/:bikeId',comments.bike);
        @GET("/comments/{bikeId}")
        void selectBicycleComment(@Path("bikeId") String bike_id, Callback<ReceiveObject> callback);

        // 고객문의등록 app.post('/inquiry',auth.requiresLogin,inquires.create);
        @POST("/inquiry")
        void insertInquiry(@Body Inquires inquires, Callback<ReceiveObject> callback);

//        @Multipart
//        @POST("/test")
//        void test(@Part("image") TypedFile file1, @Part("image") TypedFile file2, @Part("description") String description, Callback<String> cb);
//
//        @POST("/register/")
//        void registerGCM(@Field("token") String registerationID,
//                         @Field("deviceID") String deviceID,
//                         @Field("deviceName") String deviceName,
//                         @Field("email") String email);
//
//        @POST("/message/{userID}")
//        void sendMessage(@Path("userID") String user_id, @Field("message") String message);
    }

    // 모든 날짜 형식을 변환하는 메소드
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

    // 본인정보조회
    public void selectUser(String user_id, Callback<ReceiveObject> callback) {
        serverUrl.selectUser(user_id, callback);
    }

    // 회원가입
    public void insertUser(User user, Callback<ReceiveObject> callback) {
        serverUrl.insertUser(user, callback);
    }

    // 회원수정
    public void updateUser(User user, Callback<ReceiveObject> callback) {
        serverUrl.updateUser(user, callback);
    }

    // 보유자전거등록
    public void insertBicycle(TypedFile file1, TypedFile file2, Bike bike, Callback<ReceiveObject> callback) {
        serverUrl.insertBicycle(file1, file2, bike, callback);
    }

    // 보유자전거조회
    public void selectBicycle(Callback<ReceiveObject> callback) {
        serverUrl.selectBicycle(callback);
    }

    // 보유자전거수정
    public void updateBicycle(String bike_id, Bike bike, Callback<ReceiveObject> callback) {
        serverUrl.updateBicycle(bike_id, bike, callback);
    }

    // 보유자전거 활성화/비활성화
    public void updateBicycleOnOff(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.updateBicycleOnOff(bike_id, callback);
    }

    // 보유자전거삭제
    public void deleteBicycle(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.deleteBicycle(bike_id, callback);
    }

    // 전체자전거조회
    public void selectAllBicycle(String lat,
                                 String lon,
                                 String start,
                                 String end,
                                 String type,
                                 String height,
                                 String component,
                                 Boolean smartlock,
                                 Callback<ReceiveObject> callback) {
        serverUrl.selectAllBicycle(lat, lon, start, end, type, height, component, smartlock, callback);
    }

    // 자전거상세조회
    public void selectBicycleDetail(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.selectBicycleDetail(bike_id, callback);
    }

    // 로그인 정규식 체크 http://www.java2go.net/java/java_regex.html, http://wintness.tistory.com/225
    public void login(String email, String password, Callback<ReceiveObject> callback) {
        serverUrl.login(email, password, callback);
    }

    // 세션클리어
    public void sessionClear(Callback<ReceiveObject> callback) {
        serverUrl.sessionClear(callback);
    }

    // 내평가보기
    public void selectUserComment(Callback<ReceiveObject> callback) {
        serverUrl.selectUserComment(callback);
    }

    // 자전거후기작성
    public void insertBicycleComment(String bike_id, Comment comment, Callback<ReceiveObject> callback) {
        serverUrl.insertBicycleComment(bike_id, comment, callback);
    }

    // 자전거후기보기
    public void selectBicycleComment(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.selectBicycleComment(bike_id, callback);
    }

    // 고객문의등록
    public void insertInquiry(Inquires inquires, Callback<ReceiveObject> callback) {
        serverUrl.insertInquiry(inquires, callback);
    }

//    public void test(TypedFile file1, TypedFile file2, Callback<String> cb) {
//        serverUrl.test(file1, file2, "desc", cb);
//    }
}