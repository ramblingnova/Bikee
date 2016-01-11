package com.example.tacademy.bikee.etc.manager;

import com.example.tacademy.bikee.etc.dao.Bike;
import com.example.tacademy.bikee.etc.dao.Comment;
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
import java.util.Map;

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
import retrofit.http.PartMap;
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
//        String baseUrl = "http://bikee.kr.pe";
        // port 3000 로보몽고 접속할때 필요
        // url 테스팅은 포스트맨
        String baseUrl = "http://52.192.28.152:3000";
//        String baseUrl = "http://192.168.201.226:2222";

        // 본인정보조회 app.get('/users/:userId',users.profile) TODO id 없이 "본인정보조회"하기
        @GET("/users/{userId}")
        void selectUser(@Path("userId") String user_id, Callback<ReceiveObject> callback);

        // id없이 본인정보조회
        @GET("/profile")
        void selectUserName(Callback<ReceiveObject> callback);

        // 회원가입 app.post('/users', users.create)
        @POST("/users")
        void insertUser(@Body User user, Callback<ReceiveObject> callback);

        // 인증번호요청하기
        @FormUrlEncoded
        @POST("/sms/auth")
        void requestAuthenticationNumber(@Field("mobile") String mobile, Callback<ReceiveObject> callback);

        // 인증번호확인하기
        @FormUrlEncoded
        @POST("/sms/check/{authid}")
        void confirmAuthenticationNumber(@Path("authid") String authid, @Field("auth_number") String auth_number, Callback<ReceiveObject> callback);

        // 회원수정 app.put('/users/:userId',userAuth,users.edit);
        @PUT("/users")
        void updateUser(@Body User user, Callback<ReceiveObject> callback);

//        // 보유자전거등록 app.post('/bikes/users',auth.requiresLogin,bikes.create);
//        @Multipart
//        @POST("/bikes/users")
//        void insertBicycle(@Part("image") List<TypedFile> file1,
//                           @Part("image") TypedFile file2,
//                           @Part("bike") Bike bike,
//                           Callback<ReceiveObject> callback);

        // 보유자전거등록 app.post('/bikes/users',auth.requiresLogin,bikes.create);
        @Multipart
        @POST("/bikes/users")
        void insertBicycle(@PartMap Map<String, TypedFile> files,
                           @Part("bike") Bike bike,
                           @Part("size") int size,
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

        // 전체자전거조회(리스트)
        @GET("/bikes/list/{lon}/{lat}/{lastindex}")
        void selectAllListBicycle(@Path("lon") String lon,
                                  @Path("lat") String lat,
                                  @Path("lastindex") String lastindex,
                                  @Query("start") String start,
                                  @Query("end") String end,
                                  @Query("type") String type,
                                  @Query("height") String height,
                                  @Query("component") String component,
                                  @Query("smartlock") Boolean smartlock,
                                  Callback<ReceiveObject> callback);

        // 전체자전거조회(지도)
        @GET("/bikes/all/{lon}/{lat}")
        void selectAllMapBicycle(@Path("lon") String lon,
                                 @Path("lat") String lat,
                                 Callback<ReceiveObject> callback);

        // 자전거상세조회 app.get('/bikes/:bikeId/detail',bikes.detail);
        @GET("/bikes/{bikeId}/detail")
        void selectBicycleDetail(@Path("bikeId") String bike_id, Callback<ReceiveObject> callback);

        // 로그인
        @FormUrlEncoded
        @POST("/users/session")
        void login(@Field("email") String email, @Field("password") String password, Callback<ReceiveObject> callback);

        // 로그아웃
        @GET("/logout")
        void logout(Callback<ReceiveObject> callback);

        // 세션클리어
        @GET("/first")
        void sessionClear(Callback<ReceiveObject> callback);

        // 내가평가한자전거후기보기 app.get('/comments',auth.requiresLogin,comments.show);
        @GET("/comments")
        void selectUserComment(Callback<ReceiveObject> callback);

        // 자전거후기작성 app.post('/comments/:bikeId',commentAuth,comments.create);
        @POST("/comments/{bikeId}")
        void insertBicycleComment(@Path("bikeId") String bike_id, @Body Comment comment, Callback<ReceiveObject> callback);

        // 상세보기 -> 자전거후기보기 app.get('/comments/:bikeId',comments.bike);
        @GET("/comments/{bikeId}")
        void selectBicycleComment(@Path("bikeId") String bike_id, Callback<ReceiveObject> callback);

        // 고객문의등록 app.post('/inquiry',auth.requiresLogin,inquires.create);
        @POST("/inquiry")
        void insertInquiry(@Body Inquires inquires, Callback<ReceiveObject> callback);

        // 평가된자전거후기보기
        @GET("/comments/me")
        void selectMyBicycleComment(Callback<ReceiveObject> callback);

        // 렌터가예약한자전거목록보기
        @GET("/reserves/me")
        void selectReservationBicycle(Callback<ReceiveObject1> callback);

        // 리스터 -> 예약신청된자전거목록보기
        @GET("/reserves")
        void selectRequestedBicycle(Callback<ReceiveObject> callback);

        // 렌터가자전거예약요청
        @POST("/reserves/{bikeId}")
        void insertReservation(@Path("bikeId") String bike_id, @Body Reserve reserve, Callback<ReceiveObject> callback);

        // 리스터가...c
        @FormUrlEncoded
        @PUT("/reserves/{bikeId}/{reserveId}")
        void reserveStatus(@Path("bikeId")String bike_id, @Path("reserveId") String reserveId, @Field("status") String status, Callback<ReceiveObject> callback);

        // 결제요청
        @GET("/import")
        void requestPayment(Callback<ReceiveObject> callback);

//        // TODO :
//        @POST("/register/")
//        void registerGCM(@Field("token") String registerationID,
//                         @Field("deviceID") String deviceID,
//                         @Field("deviceName") String deviceName,
//                         @Field("email") String email);

//        // TODO :
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

    // id없이 본인정보조회
    public void selectUserName(Callback<ReceiveObject> callback) {
        serverUrl.selectUserName(callback);
    }

    // 회원가입
    public void insertUser(User user, Callback<ReceiveObject> callback) {
        serverUrl.insertUser(user, callback);
    }

    // 인증번호요청하기
    public void requestAuthenticationNumber(String mobile, Callback<ReceiveObject> callback) {
        serverUrl.requestAuthenticationNumber(mobile, callback);
    }

    public void confirmAuthenticationNumber(String authid, String auth_number, Callback<ReceiveObject> callback) {
        serverUrl.confirmAuthenticationNumber(authid, auth_number, callback);
    }

    // 회원수정
    public void updateUser(User user, Callback<ReceiveObject> callback) {
        serverUrl.updateUser(user, callback);
    }

//    // 보유자전거등록
//    public void insertBicycle(List<TypedFile> file1, TypedFile file2, Bike bike, Callback<ReceiveObject> callback) {
//        serverUrl.insertBicycle(file1, file2, bike, callback);
//    }

    // 보유자전거등록
    public void insertBicycle(Map<String, TypedFile> file, Bike bike, int size, Callback<ReceiveObject> callback) {
        serverUrl.insertBicycle(file, bike, size, callback);
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

    // 전체자전거조회(리스트)
    public void selectAllListBicycle(String lon,
                                     String lat,
                                     String lastindex,
                                     String start,
                                     String end,
                                     String type,
                                     String height,
                                     String component,
                                     Boolean smartlock,
                                     Callback<ReceiveObject> callback) {
        serverUrl.selectAllListBicycle(lon, lat, lastindex, start, end, type, height, component, smartlock, callback);
    }

    // 전체자전거조회(지도)
    public void selectAllMapBicycle(String lon,
                                    String lat,
                                    Callback<ReceiveObject> callback) {
        serverUrl.selectAllMapBicycle(lon, lat, callback);
    }

    // 자전거상세조회
    public void selectBicycleDetail(String bike_id, Callback<ReceiveObject> callback) {
        serverUrl.selectBicycleDetail(bike_id, callback);
    }

    // 로그인 정규식 체크 http://www.java2go.net/java/java_regex.html, http://wintness.tistory.com/225
    public void login(String email, String password, Callback<ReceiveObject> callback) {
        serverUrl.login(email, password, callback);
    }

    // 로그아웃
    public void logout(Callback<ReceiveObject> callback) {
        serverUrl.logout(callback);
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

    // 평가된자전거후기보기
    public void selectMyBicycleComment(Callback<ReceiveObject> callback) {
        serverUrl.selectMyBicycleComment(callback);
    }

    // 렌터가예약한자전거목록보기
    public void selectReservationBicycle(Callback<ReceiveObject1> callback) {
        serverUrl.selectReservationBicycle(callback);
    }

    // 리스터 -> 예약신청된자전거목록보기
    public void selectRequestedBicycle(Callback<ReceiveObject> callback) {
        serverUrl.selectRequestedBicycle(callback);
    }

    // 렌터가자전거예약요청
    public void insertReservation(String bike_id, Reserve reserve, Callback<ReceiveObject> callback) {
        serverUrl.insertReservation(bike_id, reserve, callback);
    }

    // 렌터결제요청
    public void requestPayment(Callback<ReceiveObject> callback) {
        serverUrl.requestPayment(callback);
    }
    // RR(x), RS승인, RC취소 둘 중 하나
    public void reserveStatus(String bike_id, String reserveId, String status, Callback<ReceiveObject> callback) {
        serverUrl.reserveStatus(bike_id, reserveId, status, callback);
    }

//    // FIXME 테스트용
//    public ServerUrl getServerUrl() {
//        return serverUrl;
//    }
//    // FIXME 테스트용
//    NetworkManager.getInstance().getServerUrl().insertBicycleComment2(bicycleId, comment, new Callback<Object>() {
//        @Override
//        public void success(Object receiveObject, Response response) {
//            Log.i("result", "onResponse receiveObject.toString() : " + receiveObject.toString() + ", response.getStatus() : " + response.getStatus());
//        }
//
//        @Override
//        public void failure(RetrofitError error) {
//            Log.e("error", "onFailure Error : " + error.toString());
//        }
//    });
}