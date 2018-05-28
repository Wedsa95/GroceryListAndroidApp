package com.olssonjonas.grocerylistapp.services;

import android.os.AsyncTask;
import android.util.Log;

import com.olssonjonas.grocerylistapp.ServiceGenerator;
import com.olssonjonas.grocerylistapp.TokenHolder;
import com.olssonjonas.grocerylistapp.model.AuthenticationToken;
import com.olssonjonas.grocerylistapp.model.GroceryList;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AddNewGroceryList extends AsyncTask<GroceryList, Void, GroceryList>{
    private static final String TAG = "AddNewGroceryList";

    private Call<GroceryList> groceryCall;
    private GroceryList newItem;
    private OnDataAdded callBack;

    public AddNewGroceryList(OnDataAdded callBack) {
        this.callBack = callBack;
    }

    @Override
    protected GroceryList doInBackground(GroceryList... groceryList) {
        GroceryList grocery = null;
        if(TokenHolder.getInstance().getToken() != null) {
            AuthenticationToken token = TokenHolder.getInstance().getToken();
            groceryCall = ServiceGenerator.
                    createService(GroceryListService.class).
                    createGroceryList(token.getTokenId(), groceryList[0]);
            try{
                Response<GroceryList> response = groceryCall.execute();
                if(response.code() == 201){
                    grocery = response.body();
                }
            } catch ( IOException e) {
                Log.d(TAG, "doInBackground: " + e.getMessage());
            }

        } else{

        }

        return grocery;
    }

    @Override
    protected void onPostExecute(GroceryList groceryList) {
        if (callBack != null) {
            callBack.onDataAdded(groceryList);
        }
    }

    public interface OnDataAdded{
        void onDataAdded(GroceryList groceryList);
    }
}
