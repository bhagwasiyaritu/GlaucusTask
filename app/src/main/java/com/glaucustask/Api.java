package com.glaucustask;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "https://devfrontend.gscmaven.com/wmsweb/webapi/";

    @GET("email/")
    Call<List<Data>> getMailList();

    @POST("email/")
    Call<Data> createMail(@Body Data data);

    @PUT("email/{idtableEmail}")
    Call<Data> updateMail(@Path("idtableEmail") int idtableEmail, @Body Data data);

    @DELETE("email/{idtableEmail}")
    Call<Boolean> deleteId(@Path("idtableEmail") int idtableEmail);
}
