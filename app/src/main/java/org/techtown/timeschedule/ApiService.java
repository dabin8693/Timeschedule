package org.techtown.timeschedule;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    //베이스 Url
    static final String BASEURL = "http://api.openweathermap.org/";
    static final String APPID ="9e40023c77b305ca158da11f5fe58017 ";
    public String iconname = "04n@2x.png";
    //get 메소드를 통한 http rest api 통신
    @GET("data/2.5/weather?")
    Call<JsonObject> getCurrentWeatherData (@Query("lat") String lat, @Query("lon") String lon,
                                @Query("appid") String appid);

    @GET
    Call<ResponseBody> IconData (@Url String fileURl);

}
