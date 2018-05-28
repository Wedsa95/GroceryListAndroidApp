package com.olssonjonas.grocerylistapp;

import com.olssonjonas.grocerylistapp.model.AuthenticationToken;

public class TokenHolder {
    private static TokenHolder instance = null;
    private AuthenticationToken token;

    private TokenHolder(){

    }
    public static TokenHolder getInstance() {
        if (instance == null) {
            instance = new TokenHolder();
        }
        return instance;
    }
    public AuthenticationToken getToken() {
        return token;
    }
    public void setToken(AuthenticationToken token) {
        this.token = token;
    }
}
