package com.olssonjonas.grocerylistapp.services;



import com.olssonjonas.grocerylistapp.model.AuthenticationToken;
import com.olssonjonas.grocerylistapp.model.LoginCredentials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserCredentialsService {

    @Headers({"Accept: application/json",
    "Content-Type: application/json"})
    @POST("/api/authenticate")
    Call<AuthenticationToken> authenticate(@Body LoginCredentials credentials);

}
