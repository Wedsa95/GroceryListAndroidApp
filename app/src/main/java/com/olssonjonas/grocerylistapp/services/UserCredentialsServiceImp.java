package com.olssonjonas.grocerylistapp.services;

import android.util.Log;

import com.olssonjonas.grocerylistapp.ServiceGenerator;
import com.olssonjonas.grocerylistapp.TokenHolder;
import com.olssonjonas.grocerylistapp.model.AuthenticationToken;
import com.olssonjonas.grocerylistapp.model.LoginCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCredentialsServiceImp {
    private static final String TAG = "UserCredentialsServiceI";

    private AuthenticationToken token;
    private UserCredentialsService userCredentialsService;

    public void authenticateUser(LoginCredentials credentials) {


        Call<AuthenticationToken> userCredentials = ServiceGenerator.createService(UserCredentialsService.class).authenticate(credentials);

        try{
            userCredentials.enqueue(new Callback<AuthenticationToken>() {
                @Override
                public void onResponse(Call<AuthenticationToken> call, Response<AuthenticationToken> response) {
                    if (response.isSuccessful()){
                        Log.d(TAG, "onResponse:  =" + response.body().toString());

                        String responseHeaders = response.headers().get("Authorization");
                        token = new AuthenticationToken();
                        token.setTokenId(responseHeaders);

                        Log.d(TAG, "onResponse: ResponserHeders Authorization = " + responseHeaders.toString() );
                        TokenHolder.getInstance().setToken(token);
                    }
                }

                @Override
                public void onFailure(Call<AuthenticationToken> call, Throwable t) {
                    Log.d(TAG, "onFailure: FAILIURE TO AUTHENTICATE");
                }
            });
        }catch (Exception e) {
            Log.d(TAG, "authenticateUser: Cauth "+ e.getMessage());

        }
    }

    public AuthenticationToken getToken() {
        return token;
    }

    public void setToken(AuthenticationToken token) {
        this.token = token;
    }
}
