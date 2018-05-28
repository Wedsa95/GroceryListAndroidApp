package com.olssonjonas.grocerylistapp.model;

import java.io.Serializable;

public class AuthenticationToken implements Serializable {

    private static final long serialVersionUID = 3L;
    private String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
