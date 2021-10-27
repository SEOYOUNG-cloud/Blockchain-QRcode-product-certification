package com.example.PARM;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/clogin")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/mlogin")
    Call<LoginResponse2> manuLogin(@Body LoginData data);

    @POST("/user/dlogin")
    Call<LoginResponse2> disLogin(@Body LoginData data);

    @POST("/user/slogin")
    Call<LoginResponse2> shopLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData_customer data);

    @POST("/user/mjoin")
    Call<JoinResponse> manuJoin(@Body JoinData_ data);

    @POST("/user/djoin")
    Call<JoinResponse> disJoin(@Body JoinData_ data);

    @POST("/user/sjoin")
    Call<JoinResponse> shopJoin(@Body JoinData_ data);

    @POST("/user/checkId")
    Call<Join_checkJD> checkID(@Body checkidData data);

    @POST("/user/mcheckId")
    Call<Join_checkJD> checkmID(@Body checkidData data);

    @POST("/user/dcheckId")
    Call<Join_checkJD> checkdID(@Body checkidData data);

    @POST("/user/scheckId")
    Call<Join_checkJD> checksID(@Body checkidData data);

}
