package com.olssonjonas.grocerylistapp.services;

import android.os.AsyncTask;
import android.util.Log;

import com.olssonjonas.grocerylistapp.ServiceGenerator;
import com.olssonjonas.grocerylistapp.TokenHolder;
import com.olssonjonas.grocerylistapp.model.AuthenticationToken;
import com.olssonjonas.grocerylistapp.model.GroceryList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroceryListDataSource extends
        AsyncTask<String, Void, List<GroceryList>>{
    private static final String TAG = "GroceryListServiceImple";

    private List<GroceryList> groceryList;
    private OnDataAvailable callBack;
    private Call<List<GroceryList>> groceryCall;

    public GroceryListDataSource (OnDataAvailable callBack) {
        this.callBack = callBack;
    }
    @Override
    protected List<GroceryList> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: ");
        groceryList = new ArrayList<>();

        if(TokenHolder.getInstance().getToken() != null) {
            AuthenticationToken token = TokenHolder.getInstance().getToken();
            groceryCall = ServiceGenerator.
                    createService(GroceryListService.class).
                    findAllGroceryLists(token.getTokenId());
            try {
                Response<List<GroceryList>> list = groceryCall.execute();
                if (list.code() == 200) {
                    groceryList = list.body();
                    for (GroceryList i : groceryList) {
                        Log.d(TAG, "doInBackground: "+i.toString());
                    }
                }
            } catch ( Exception e) {
                Log.d(TAG, "getGroceryLists: "+e.getMessage());
            }
        }
        return groceryList;
    }

    @Override
    protected void onPostExecute(List<GroceryList> groceryLists) {
        if (callBack != null) {
            callBack.onDataAvailable(groceryLists);
        }
    }

    public interface OnDataAvailable {
        void onDataAvailable(List<GroceryList> data);
    }
}
