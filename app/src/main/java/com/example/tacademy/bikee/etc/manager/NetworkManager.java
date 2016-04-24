package com.example.tacademy.bikee.etc.manager;

import com.example.tacademy.bikee.etc.dao.Bike;
import com.example.tacademy.bikee.etc.dao.Comment;
import com.example.tacademy.bikee.etc.dao.Facebook;
import com.example.tacademy.bikee.etc.dao.FilterSendObject;
import com.example.tacademy.bikee.etc.dao.GetChannelResInfoReceiveObject;
import com.example.tacademy.bikee.etc.dao.Inquires;
import com.example.tacademy.bikee.etc.dao.ReceiveObject;
import com.example.tacademy.bikee.etc.dao.ReceiveObject1;
import com.example.tacademy.bikee.etc.dao.Reserve;
import com.example.tacademy.bikee.etc.dao.SendBirdSendObject;
import com.example.tacademy.bikee.etc.dao.GetChannelInfoReceiveObject;
import com.example.tacademy.bikee.etc.dao.User;
import com.google.gson.GsonBuilder;
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
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Tacademy on 2015-11-04.
 */
public class NetworkManager {
    private static NetworkManager networkManager;
    private ServerUrl serverUrl;
    private OkHttpClient client;

    private NetworkManager() {
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerUrl.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create()))
                .client(client)
                .build();
        serverUrl = retrofit.create(ServerUrl.class);
    }

    public static NetworkManager getInstance() {
        if (networkManager == null)
            networkManager = new NetworkManager();
        return networkManager;
    }

    public interface ServerUrl {
//        String baseUrl = "http://bikee.kr.pe";
//        String baseUrl = "http://192.168.0.14:3000";
//        String baseUrl = "http://1.255.51.120";
//        String baseUrl = "http://192.168.0.8:3000";
        String baseUrl = "https://api.bikee.kr";

        // 회원 정보 조회하기
        @GET("/users/{userId}")
        Call<ReceiveObject> selectUser(@Path("userId") String user_id);

        // 회원(본인) 정보 조회하기
        @GET("/profile")
        Call<ReceiveObject> receiveProfile();

        // 회원 가입하기
        @POST("/users")
        Call<ReceiveObject> insertUser(@Body User user);

        // 이메일 중복 체크
        @POST("/users/check")
        Call<ReceiveObject> checkEmailDuplication(@Body User user);

        // 인증 번호 요청하기
        @FormUrlEncoded
        @POST("/sms/auth")
        Call<ReceiveObject> requestAuthenticationNumber(@Field("mobile") String mobile);

        // 인증 번호 확인하기
        @FormUrlEncoded
        @POST("/sms/check/{authid}")
        Call<ReceiveObject> confirmAuthenticationNumber(@Path("authid") String authid,
                                                        @Field("auth_number") String auth_number);

        // 회원 정보 수정하기
        @PUT("/users")
        Call<ReceiveObject> updateUser(@Body User user);

        // 로그인하기
        @FormUrlEncoded
        @POST("/users/session")
        Call<ReceiveObject> login(@Field("email") String email,
                                  @Field("password") String password);

        // 로그아웃하기
        @POST("/logout")
        Call<ReceiveObject> logout();

        // 세션클리어하기
        @GET("/first")
        Call<ReceiveObject> sessionClear();

        // 페이스북 계정으로 회원 가입하기
        @POST("/users/facebook/token")
        Call<ReceiveObject> signUpFacebook(@Body Facebook facebook);

        // 페이스북 계정으로 로그인하기
        @POST("/users/facebook/session")
        Call<ReceiveObject> signInFacebook(@Body Facebook facebook);

        // 페이스북 로그인 여부 확인하기
        @POST("/users/facebook/check/{id}")
        Call<ReceiveObject> isSignedInFacebook(@Path("id") String id);

        // 채팅방 목록 정보 가져오기
        @GET("/sendbird/{channel_url}")
        Call<GetChannelInfoReceiveObject> getChannelInfo(@Path("channel_url") String channelUrl);

        // 채팅방 예약 정보 조회
        @POST("/sendbird/reserves")
        Call<GetChannelResInfoReceiveObject> getChannelResInfo(@Body SendBirdSendObject sendBirdSendObject);

        // 채팅방 등록
        @POST("/sendbird")
        Call<ReceiveObject> createChannel(@Body SendBirdSendObject sendBirdSendObject);

//        @Multipart
//        @POST("/bikes/users")
//        void insertBicycle(@PartMap Map<String, TypedFile> files,
//                           @Part("bike") Bike bike,
//                           @Part("size") int size,
//                           Callback<ReceiveObject> callback);

//        @POST("/images")
//        void tempInsertBicycle(@Body MultipartTypedOutput multipartTypedOutput,
//                               Callback<String> callback);

//        // 자전거 등록하기(리스터)
//        @POST("/bikes")
//        void insertBicycle(@Part("image") List<TypedFile> list,
//                           @Part("bike") Bike bike,
//                           Call<ReceiveObject> callback);

        // 보유하고 있는 자전거 조회하기(리스터)
        @GET("/bikes")
        Call<ReceiveObject> selectBicycle();

        // 전체 자전거 조회하기
        @GET("/bikes")
        Call<ReceiveObject> selectAllBicycle(@Query("lat") String lat,
                                             @Query("lon") String lon,
                                             @Query("start") String start,
                                             @Query("end") String end,
                                             @Query("type") String type,
                                             @Query("height") String height,
                                             @Query("component") String component,
                                             @Query("smartlock") Boolean smartlock);

        // 전체 자전거(리스트) 조회하기
        @GET("/bikes/list/{lon}/{lat}/{lastindex}")
        Call<ReceiveObject> selectAllListBicycle(@Path("lon") String lon,
                                                 @Path("lat") String lat,
                                                 @Path("lastindex") String lastindex,
                                                 @Query("filter") String filter);

        // 전체 자전거(맵) 조회하기
        @GET("/bikes/map/{lon}/{lat}")
        Call<ReceiveObject> selectAllMapBicycle(@Path("lon") String lon,
                                                @Path("lat") String lat);

        // 자전거 상세 조회하기
        @GET("/bikes/{bikeId}")
        Call<ReceiveObject> selectBicycleDetail(@Path("bikeId") String bike_id);

        // 보유하고 있는 자전거 수정하기(리스터)
        @Multipart
        @PUT("/bikes/{bikeId}")
        Call<ReceiveObject> updateBicycle(@Path("bikeId") String bike_id,
                                          @Part("bike") Bike bike);

        // 보유하고 있는 자전거 "활성화" 또는 "비활성화"하기(리스터)
        @PUT("/bikes/{bikeId}/active")
        Call<ReceiveObject> updateBicycleOnOff(@Path("bikeId") String bike_id);

        // 보유하고 있는 자전거 삭제하기(리스터)
        @DELETE("/bikes/{bikeId}")
        Call<ReceiveObject> deleteBicycle(@Path("bikeId") String bike_id);

        // 자전거 후기 작성하기
        @POST("/comments/{bikeId}")
        Call<ReceiveObject> insertBicycleComment(@Path("bikeId") String bike_id,
                                                 @Body Comment comment);

        // 평가한 자전거 후기 보기(렌터)
        @GET("/comments")
        Call<ReceiveObject> selectUserComment();

        // 자전거 한 대에 평가된 후기 보기(공통)
        @GET("/comments/{bikeId}")
        Call<ReceiveObject> selectBicycleComment(@Path("bikeId") String bike_id);

        // 평가된 자전거 후기 보기(리스터)
        @GET("/comments/me")
        Call<ReceiveObject> selectMyBicycleComment();

        // 자전거 예약 요청하기(렌터)
        @POST("/reserves/{bikeId}")
        Call<ReceiveObject> insertReservation(@Path("bikeId") String bike_id,
                                              @Body Reserve reserve);

        // 예약한 자전거 목록 보기(렌터)
        @GET("/reserves/me")
        Call<ReceiveObject1> selectReservationBicycle();

        // 예약된 자전거 목록 보기(리스터)
        @GET("/reserves")
        Call<ReceiveObject> selectRequestedBicycle();

        // 예약 상태 가져오기
        @FormUrlEncoded
        @PUT("/reserves/{bikeId}/{reserveId}")
        Call<ReceiveObject> reserveStatus(@Path("bikeId") String bike_id,
                                          @Path("reserveId") String reserveId,
                                          @Field("status") String status);

        // 고객 문의 등록하기
        @POST("/inquiry")
        Call<ReceiveObject> insertInquiry(@Body Inquires inquires);

        // 결제 요청하기
        @GET("/import")
        Call<ReceiveObject> requestPayment();

        // GCM 토큰 전송하기
        @FormUrlEncoded
        @POST("/devices")
        Call<ReceiveObject> sendGCMToken(@Field("deviceID") String deviceID,
                                         @Field("token") String token,
                                         @Field("os") String os);
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
                           Stack<Call> callStack,
                           Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectUser(user_id);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 회원(본인) 정보 조회하기
    public void receiveProfile(Stack<Call> callStack,
                               Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.receiveProfile();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 회원 가입하기
    public void insertUser(User user,
                           Stack<Call> callStack,
                           Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.insertUser(user);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 이메일 중복 체크
    public void checkEmailDuplication(User user,
                                      Stack<Call> callStack,
                                      Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.checkEmailDuplication(user);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 인증 번호 요청하기
    public void requestAuthenticationNumber(String mobile,
                                            Stack<Call> callStack,
                                            Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.requestAuthenticationNumber(mobile);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 인증 번호 확인하기
    public void confirmAuthenticationNumber(String authid,
                                            String auth_number,
                                            Stack<Call> callStack,
                                            Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.confirmAuthenticationNumber(authid, auth_number);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 회원 정보 수정하기
    public void updateUser(User user,
                           Stack<Call> callStack,
                           Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.updateUser(user);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 로그인하기
    public void login(String email,
                      String password,
                      Stack<Call> callStack,
                      Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.login(email, password);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 로그아웃하기
    public void logout(Stack<Call> callStack,
                       Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.logout();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 세션클리어하기
    public void sessionClear(Stack<Call> callStack,
                             Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.sessionClear();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 페이스북 계정으로 회원 가입하기
    public void signUpFacebook(Facebook facebook,
                               Stack<Call> callStack,
                               Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.signUpFacebook(facebook);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 페이스북 계정으로 로그인하기
    public void signInFacebook(Facebook facebook,
                               Stack<Call> callStack,
                               Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.signInFacebook(facebook);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 페이스북 로그인 여부 확인하기
    public void isSignedInFacebook(String id,
                                   Stack<Call> callStack,
                                   Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.isSignedInFacebook(id);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 채팅방 목록 정보 가져오기
    public void getChannelInfo(String channelUrl,
                               Stack<Call> callStack,
                               Callback<GetChannelInfoReceiveObject> callback) {
        Call<GetChannelInfoReceiveObject> call = serverUrl.getChannelInfo(channelUrl);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 채팅방 예약 정보 조회
    public void getChannelResInfo(SendBirdSendObject sendBirdSendObject,
                                  Stack<Call> callStack,
                                  Callback<GetChannelResInfoReceiveObject> callback) {
        Call<GetChannelResInfoReceiveObject> call = serverUrl.getChannelResInfo(sendBirdSendObject);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 채팅방 등록
    public void createChannel(SendBirdSendObject sendBirdSendObject,
                              Stack<Call> callStack,
                              Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.createChannel(sendBirdSendObject);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

//    public void insertBicycle(Map<String, TypedFile> file, Bike bike, int size, Callback<ReceiveObject> callback) {
//        serverUrl.insertBicycle(file, bike, size, callback);
//    }

//    public void tempInsertBicycle(MultipartTypedOutput multipartTypedOutput, Callback<String> callback) {
//        serverUrl.tempInsertBicycle(multipartTypedOutput, callback);
//    }

//    // 자전거 등록하기(리스터)
//    public void insertBicycle(List<TypedFile> list, Bike bike, Callback<ReceiveObject> callback) {
//        serverUrl.insertBicycle(list, bike, callback);
//    }

    // 보유하고 있는 자전거 조회하기(리스터)
    public void selectBicycle(Stack<Call> callStack,
                              Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectBicycle();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
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
                                 Stack<Call> callStack,
                                 Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectAllBicycle(lat, lon, start, end, type, height, component, smartlock);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 전체 자전거(리스트) 조회하기
    public void selectAllListBicycle(String lon,
                                     String lat,
                                     String lastindex,
                                     String filter,
                                     Stack<Call> callStack,
                                     Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectAllListBicycle(lon, lat, lastindex, filter);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 전체 자전거(맵) 조회하기
    public void selectAllMapBicycle(String lon,
                                    String lat,
                                    Stack<Call> callStack,
                                    Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectAllMapBicycle(lon, lat);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 자전거 상세 조회하기
    public void selectBicycleDetail(String bike_id,
                                    Stack<Call> callStack,
                                    Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectBicycleDetail(bike_id);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 보유하고 있는 자전거 수정하기(리스터)
    public void updateBicycle(String bike_id,
                              Bike bike,
                              Stack<Call> callStack,
                              Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.updateBicycle(bike_id, bike);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 보유하고 있는 자전거 "활성화" 또는 "비활성화"하기(리스터)
    public void updateBicycleOnOff(String bike_id,
                                   Stack<Call> callStack,
                                   Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.updateBicycleOnOff(bike_id);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 보유하고 있는 자전거 삭제하기(리스터)
    public void deleteBicycle(String bike_id,
                              Stack<Call> callStack,
                              Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.deleteBicycle(bike_id);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 자전거 후기 작성하기
    public void insertBicycleComment(String bike_id,
                                     Comment comment,
                                     Stack<Call> callStack,
                                     Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.insertBicycleComment(bike_id, comment);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 평가한 자전거 후기 보기(렌터)
    public void selectUserComment(Stack<Call> callStack,
                                  Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectUserComment();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // TODO
    // 자전거 한 대에 평가된 후기 보기(공통)
    public void selectBicycleComment(String bike_id,
                                     Stack<Call> callStack,
                                     Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectBicycleComment(bike_id);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 평가된 자전거 후기 보기(리스터)
    public void selectMyBicycleComment(Stack<Call> callStack,
                                       Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectMyBicycleComment();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 자전거 예약 요청하기(렌터)
    public void insertReservation(String bike_id,
                                  Reserve reserve,
                                  Stack<Call> callStack,
                                  Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.insertReservation(bike_id, reserve);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 예약한 자전거 목록 보기(렌터)
    public void selectReservationBicycle(Stack<Call> callStack,
                                         Callback<ReceiveObject1> callback) {
        Call<ReceiveObject1> call = serverUrl.selectReservationBicycle();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 예약된 자전거 목록 보기(리스터)
    public void selectRequestedBicycle(Stack<Call> callStack,
                                       Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.selectRequestedBicycle();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 예약 상태 가져오기
    public void reserveStatus(String bike_id,
                              String reserveId,
                              String status,
                              Stack<Call> callStack,
                              Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.reserveStatus(bike_id, reserveId, status);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 고객 문의 등록하기
    public void insertInquiry(Inquires inquires,
                              Stack<Call> callStack,
                              Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.insertInquiry(inquires);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // 결제 요청하기
    public void requestPayment(Stack<Call> callStack,
                               Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.requestPayment();
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }

    // GCM 토큰 전송하기
    public void sendGCMToken(String deviceID,
                             String token,
                             String os,
                             Stack<Call> callStack,
                             Callback<ReceiveObject> callback) {
        Call<ReceiveObject> call = serverUrl.sendGCMToken(deviceID, token, os);
        if (callStack != null)
            callStack.push(call);
        call.enqueue(callback);
    }
}