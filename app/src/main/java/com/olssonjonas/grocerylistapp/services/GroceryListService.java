package com.olssonjonas.grocerylistapp.services;

import com.olssonjonas.grocerylistapp.model.GroceryList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroceryListService {

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET("/api/grocery-lists")
    Call<List<GroceryList>> findAllGroceryLists(@Header("Authorization") String authorization);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @DELETE("/api/grocery-lists/{id}")
    Call<GroceryList> deleteGroceryList(@Header("Authorization") String authorization,
                                               @Path("id") Long id);

    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @POST("/api/grocery-lists")
    Call<GroceryList> createGroceryList(@Header("Authorization") String authorization,
                                            @Body GroceryList groceryList);
}
