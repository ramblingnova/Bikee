package com.example.tacademy.bikee.etc.manager;

import com.example.tacademy.bikee.etc.dao.Bike;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.Facebook;
import com.example.tacademy.bikee.etc.dao.Inquires;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.ReceiveObject1;
import com.example.tacademy.bikee.etc.dao.Reserve;
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
import java.util.List;

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
    private ServerUrl serverUrl;

    private NetworkManager() {
        OkHttpClient okHttpClient = new OkHttpClient();
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        okHttpClient.setCookieHandler(cookieManager);
        Client client = new OkClient(okHttpClient);
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
        RestAdapter restAdapter = new RestAdapter.Builder()
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
//        String baseUrl = "http://bikee.kr.pe";
//        String baseUrl = "http://192.168.0.14:3000";
        String baseUrl = "http://1.255.51.120:3000";

        // 회원 정보 조회하기
        @GET("/users/{userId}")
        void selectUser(@Path("userId") String user_id,
                        Callback<ReceiveObject> callback);

        // 회원(본인) 정보 조회하기
        @GET("/profile")
        void selectUserName(Callback<ReceiveObject> callback);

        // 회원 가입하기
        @POST("/users")
        void insertUser(@Body User user,
                        Callback<ReceiveObject> callback);

        // 이메일 중복 체크
        @POST("/users/check")
        void checkEmailDuplication(@Body User user,
                                   Callback<ReceiveObject> callback);

        // 인증 번호 요청하기
        @FormUrlEncoded
        @POST("/sms/auth")
        void requestAuthenticationNumber(@Field("mobile") String mobile,
                                         Callback<ReceiveObject> callback);

        // 인증 번호 확인하기
        @FormUrlEncoded
        @POST("/sms/check/{authid}")
        void confirmAuthenticationNumber(@Path("authid") String authid,
                                         @Field("auth_number") String auth_number,
                                         Callback<ReceiveObject> callback);

        // 회원 정보 수정하기
        @PUT("/users")
        void updateUser(@Body User user,
                        Callback<ReceiveObject> callback);

        // 로그인하기
        @FormUrlEncoded
        @POST("/users/session")
        void login(@Field("email") String email,
                   @Field("password") String password,
                   Callback<ReceiveObject> callback);

        // 로그아웃하기
        @POST("/logout")
        void logout(Callback<ReceiveObject> callback);

        // 세션클리어하기
        @GET("/first")
        void sessionClear(Callback<ReceiveObject> callback);

        // 페이스북 계정으로 회원 가입하기
        @POST("/users/facebook/token")
        void signUpFacebook(@Body Facebook facebook,
                            Callback<ReceiveObject> callback);

        // 페이스북 계정으로 로그인하기
        @POST("/users/facebook/session")
        void signInFacebook(@Body Facebook facebook,
                            Callback<ReceiveObject> callback);

        // 페이스북 로그인 여부 확인하기
        @POST("/users/facebook/check/{id}")
        void isSignedInFacebook(@Path("id") String id,
                                Callback<ReceiveObject> callback);

//        @Multipart
//        @POST("/bikes/users")
//        void insertBicycle(@PartMap Map<String, TypedFile> files,
//                           @Part("bike") Bike bike,
//                           @Part("size") int size,
//                           Callback<ReceiveObject> callback);

//        @POST("/images")
//        void tempInsertBicycle(@Body MultipartTypedOutput multipartTypedOutput,
//                               Callback<String> callback);

        // 자전거 등록하기(리스터)
        @POST("/bikes")
        void insertBicycle(@Part("image") List<TypedFile> list,
                           @Part("bike") Bike bike,
                           Callback<ReceiveObject> callback);

        // 보유하고 있는 자전거 조회하기(리스터)
        @GET("/bikes")
        void selectBicycle(Callback<ReceiveObject> callback);

        // 전체 자전거 조회하기
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

        // 전체 자전거(리스트) 조회하기
        @GET("/bikes/list/{lon}/{lat}/{lastindex}")
        void selectAllListBicycle(@Path("lon") String lon,
                                  @Path("lat") String lat,
                                  @Path("lastindex") String lastindex,
                                  @Query("filter") String filter,
                                  Callback<ReceiveObject> callback);
//                                  @Query("start") String start,
//                                  @Query("end") String end,
//                                  @Query("type") String type,
//                                  @Query("height") String height,
//                                  @Query("component") String component,
//                                  @Query("smartlock") Boolean smartlock,

        // 전체 자전거(맵) 조회하기
        @GET("/bikes/map/{lon}/{lat}")
        void selectAllMapBicycle(@Path("lon") String lon,
                                 @Path("lat") String lat,
                                 Callback<ReceiveObject> callback);

        // 자전거 상세 조회하기
        @GET("/bikes/{bikeId}")
        void selectBicycleDetail(@Path("bikeId") String bike_id,
                                 Callback<ReceiveObject> callback);

        // 보유하고 있는 자전거 수정하기(리스터)
        @Multipart
        @PUT("/bikes/{bikeId}")
        void updateBicycle(@Path("bikeId") String bike_id,
                           @Part("bike") Bike bike,
                           Callback<ReceiveObject> callback);

        // 보유하고 있는 자전거 "활성화" 또는 "비활성화"하기(리스터)
        @PUT("/bikes/{bikeId}/active")
        void updateBicycleOnOff(@Path("bikeId") String bike_id,
                                Callback<ReceiveObject> callback);

        // 보유하고 있는 자전거 삭제하기(리스터)
        @DELETE("/bikes/{bikeId}")
        void deleteBicycle(@Path("bikeId") String bike_id,
                           Callback<ReceiveObject> callback);

        // 자전거 후기 작성하기
        @POST("/comments/{bikeId}")
        void insertBicycleComment(@Path("bikeId") String bike_id,
                                  @Body Comment comment,
                                  Callback<ReceiveObject> callback);

        // 평가한 자전거 후기 보기(렌터)
        @GET("/comments")
        void selectUserComment(Callback<ReceiveObject> callback);

        // 자전거 한 대에 평가된 후기 보기(공통)
        @GET("/comments/{bikeId}")
        void selectBicycleComment(@Path("bikeId") String bike_id,
                                  Callback<ReceiveObject> callback);

        // 평가된 자전거 후기 보기(리스터)
        @GET("/comments/me")
        void selectMyBicycleComment(Callback<ReceiveObject> callback);

        // 자전거 예약 요청하기(렌터)
        @POST("/reserves/{bikeId}")
        void insertReservation(@Path("bikeId") String bike_id,
                               @Body Reserve reserve,
                               Callback<ReceiveObject> callback);

        // 예약한 자전거 목록 보기(렌터)
        @GET("/reserves/me")
        void selectReservationBicycle(Callback<ReceiveObject1> callback);

        // 예약된 자전거 목록 보기(리스터)
        @GET("/reserves")
        void selectRequestedBicycle(Callback<ReceiveObject> callback);

        // 예약 상태 가져오기
        @FormUrlEncoded
        @PUT("/reserves/{bikeId}/{reserveId}")
        void reserveStatus(@Path("bikeId")String bike_id,
                           @Path("reserveId") String reserveId,
                           @Field("status") String status,
                           Callback<ReceiveObject> callback);

        // 고객 문의 등록하기
        @POST("/inquiry")
        void insertInquiry(@Body Inquires inquires,
                           Callback<ReceiveObject> callback);

        // 결제 요청하기
        @GET("/import")
        void requestPayment(Callback<ReceiveObject> callback);

        // GCM 토큰 전송하기
        @FormUrlEncoded
        @POST("/devices")
        void sendGCMToken(@Field("deviceID") String deviceID,
                          @Field("token") String token,
                          @Field("os") String os,
                          Callback<ReceiveObject> callback);
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

    // 회원 정보 조회하기
    public void selectUser(String user_id,
                           Callback<ReceiveObject> callback) {
        serverUrl.selectUser(user_id, callback);
    }

    // 회원(본인) 정보 조회하기
    public void selectUserName(Callback<ReceiveObject> callback) {
        serverUrl.selectUserName(callback);
    }

    // 회원 가입하기
    public void insertUser(User user,
                           Callback<ReceiveObject> callback) {
        serverUrl.insertUser(user, callback);
    }

    // 이메일 중복 체크
    public void checkEmailDuplication(@Body User user,
                               Callback<ReceiveObject> callback) {
        serverUrl.checkEmailDuplication(user, callback);
    }

    // 인증 번호 요청하기
    public void requestAuthenticationNumber(String mobile,
                                            Callback<ReceiveObject> callback) {
        serverUrl.requestAuthenticationNumber(mobile, callback);
    }

    // 인증 번호 확인하기
    public void confirmAuthenticationNumber(String authid,
                                            String auth_number,
                                            Callback<ReceiveObject> callback) {
        serverUrl.confirmAuthenticationNumber(authid, auth_number, callback);
    }

    // 회원 정보 수정하기
    public void updateUser(User user,
                           Callback<ReceiveObject> callback) {
        serverUrl.updateUser(user, callback);
    }

    // 로그인하기
    public void login(String email,
                      String password,
                      Callback<ReceiveObject> callback) {
        serverUrl.login(email, password, callback);
    }

    // 로그아웃하기
    public void logout(Callback<ReceiveObject> callback) {
        serverUrl.logout(callback);
    }

    // 세션클리어하기
    public void sessionClear(Callback<ReceiveObject> callback) {
        serverUrl.sessionClear(callback);
    }

    // 페이스북 계정으로 회원 가입하기
    public void signUpFacebook(Facebook facebook,
                               Callback<ReceiveObject> callback) {
        serverUrl.signUpFacebook(facebook, callback);
    }

    // 페이스북 계정으로 로그인하기
    public void signInFacebook(Facebook facebook, Callback<ReceiveObject> callback) {
        serverUrl.signInFacebook(facebook, callback);
    }

    // 페이스북 로그인 여부 확인하기
    public void isSignedInFacebook(String id,
                                   Callback<ReceiveObject> callback) {
        serverUrl.isSignedInFacebook(id, callback);
    }

//    public void insertBicycle(Map<String, TypedFile> file, Bike bike, int size, Callback<ReceiveObject> callback) {
//        serverUrl.insertBicycle(file, bike, size, callback);
//    }

//    public void tempInsertBicycle(MultipartTypedOutput multipartTypedOutput, Callback<String> callback) {
//        serverUrl.tempInsertBicycle(multipartTypedOutput, callback);
//    }

    // 자전거 등록하기(리스터)
    public void insertBicycle(List<TypedFile> list, Bike bike, Callback<ReceiveObject> callback) {
        serverUrl.insertBicycle(list, bike, callback);
    }

    // 보유하고 있는 자전거 조회하기(리스터)
    public void selectBicycle(Callback<ReceiveObject> callback) {
        serverUrl.selectBicycle(callback);
    }

    // 전체 자전거 조회하기
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

    // 전체 자전거(리스트) 조회하기
    public void selectAllListBicycle(String lon,
                                     String lat,
                                     String lastindex,
                                     String filter,
//                                     String start,
//                                     String end,
//                                     String type,
//                                     String height,
//                                     String component,
//                                     Boolean smartlock,
                                     Callback<ReceiveObject> callback) {
        serverUrl.selectAllListBicycle(lon, lat, lastindex, filter, callback);
    }

    // 전체 자전거(맵) 조회하기
    public void selectAllMapBicycle(String lon,
                                    String lat,
                                    Callback<ReceiveObject> callback) {
        serverUrl.selectAllMapBicycle(lon, lat, callback);
    }

    // 자전거 상세 조회하기
    public void selectBicycleDetail(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.selectBicycleDetail(bike_id, callback);
    }

    // 보유하고 있는 자전거 수정하기(리스터)
    public void updateBicycle(String bike_id, Bike bike, Callback<ReceiveObject> callback) {
        serverUrl.updateBicycle(bike_id, bike, callback);
    }

    // 보유하고 있는 자전거 "활성화" 또는 "비활성화"하기(리스터)
    public void updateBicycleOnOff(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.updateBicycleOnOff(bike_id, callback);
    }

    // 보유하고 있는 자전거 삭제하기(리스터)
    public void deleteBicycle(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.deleteBicycle(bike_id, callback);
    }

    // 자전거 후기 작성하기
    public void insertBicycleComment(String bike_id, Comment comment, Callback<ReceiveObject> callback) {
        serverUrl.insertBicycleComment(bike_id, comment, callback);
    }

    // 평가한 자전거 후기 보기(렌터)
    public void selectUserComment(Callback<ReceiveObject> callback) {
        serverUrl.selectUserComment(callback);
    }

    // 자전거 한 대에 평가된 후기 보기(공통)
    public void selectBicycleComment(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.selectBicycleComment(bike_id, callback);
    }

    // 평가된 자전거 후기 보기(리스터)
    public void selectMyBicycleComment(Callback<ReceiveObject> callback) {
        serverUrl.selectMyBicycleComment(callback);
    }

    // 자전거 예약 요청하기(렌터)
    public void insertReservation(String bike_id, Reserve reserve, Callback<ReceiveObject> callback) {
        serverUrl.insertReservation(bike_id, reserve, callback);
    }

    // 예약한 자전거 목록 보기(렌터)
    public void selectReservationBicycle(Callback<ReceiveObject1> callback) {
        serverUrl.selectReservationBicycle(callback);
    }

    // 예약된 자전거 목록 보기(리스터)
    public void selectRequestedBicycle(Callback<ReceiveObject> callback) {
        serverUrl.selectRequestedBicycle(callback);
    }

    // 예약 상태 가져오기
    public void reserveStatus(String bike_id, String reserveId, String status, Callback<ReceiveObject> callback) {
        serverUrl.reserveStatus(bike_id, reserveId, status, callback);
    }

    // 고객 문의 등록하기
    public void insertInquiry(Inquires inquires, Callback<ReceiveObject> callback) {
        serverUrl.insertInquiry(inquires, callback);
    }

    // 결제 요청하기
    public void requestPayment(Callback<ReceiveObject> callback) {
        serverUrl.requestPayment(callback);
    }

    // GCM 토큰 전송하기
    public void sendGCMToken(String deviceID, String token, String os, Callback<ReceiveObject> callback) {
        serverUrl.sendGCMToken(deviceID, token, os, callback);
    }
}