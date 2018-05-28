package com.olssonjonas.grocerylistapp.services;

import android.os.AsyncTask;
import android.util.Log;

import com.olssonjonas.grocerylistapp.ServiceGenerator;
import com.olssonjonas.grocerylistapp.TokenHolder;
import com.olssonjonas.grocerylistapp.model.AuthenticationToken;
import com.olssonjonas.grocerylistapp.model.GroceryList;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class DeleteGroceryList extends AsyncTask<Long, Void, Boolean>{
    private static final String TAG = "AddNewGroceryList";

    private Call<GroceryList> groceryCall;
    private GroceryList newItem;
    private OnDataDeleted callBack;

    public DeleteGroceryList(OnDataDeleted callBack) {
        this.callBack = callBack;
    }

    @Override
    protected Boolean doInBackground(Long... id) {
        Boolean success= false;
        if(TokenHolder.getInstance().getToken() != null) {
            AuthenticationToken token = TokenHolder.getInstance().getToken();
            groceryCall = ServiceGenerator.
                    createService(GroceryListService.class).
                    deleteGroceryList(token.getTokenId(), id[0]);
            try{
                Response<GroceryList> response = groceryCall.execute();
                if(response.code() == 200){
                    Log.d(TAG, "doInBackground: Deleting = " + id[0]);
                    success = true;
                }
            } catch ( IOException e) {
                Log.d(TAG, "doInBackground: " + e.getMessage());
            }

        } else{

        }

        return success;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (callBack != null) {
            callBack.onDataDeleted(success);
        }
    }

    public interface OnDataDeleted{
        void onDataDeleted(Boolean id);
    }
}
