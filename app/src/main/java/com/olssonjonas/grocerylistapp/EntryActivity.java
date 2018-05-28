package com.olssonjonas.grocerylistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.olssonjonas.grocerylistapp.model.GroceryList;
import com.olssonjonas.grocerylistapp.model.AuthenticationToken;
import com.olssonjonas.grocerylistapp.services.AddNewGroceryList;
import com.olssonjonas.grocerylistapp.services.DeleteGroceryList;
import com.olssonjonas.grocerylistapp.services.GroceryListService;
import com.olssonjonas.grocerylistapp.services.GroceryListDataSource;
import com.olssonjonas.grocerylistapp.services.UserCredentialsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class EntryActivity extends BaseActivity implements
        RecyclerViewItemClickListener.OnRecyclerViewItemClickListener,
        GroceryListDataSource.OnDataAvailable,
        AddNewGroceryList.OnDataAdded,
        DeleteGroceryList.OnDataDeleted{
    private static final String TAG = "EntryActivity";

    private List<GroceryList> groceryList;
    private AuthenticationToken token;
    private GroceryListService groceryListService;
    private UserCredentialsService userCredentialsService;
    private Retrofit retrofit;
    private EditText addGrocery;

    private GroceriesRecyclerViewAdapter groceriesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        addGrocery = findViewById(R.id.add_grocery);
        addGrocery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    Log.d(TAG, "onEditorAction: STARTS");
                    saveGroceryItem();
                    return true;
                }
                return false;
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this, recyclerView, this));

        groceriesRecyclerViewAdapter= new GroceriesRecyclerViewAdapter(
                this, new ArrayList<GroceryList>());
        recyclerView.setAdapter(groceriesRecyclerViewAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        GroceryListDataSource source = new GroceryListDataSource(this);
        source.execute();
    }

    @Override
    public void onDataAvailable(List<GroceryList> data) {
            groceriesRecyclerViewAdapter.loadNewDataList(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_list_1:
                groceriesRecyclerViewAdapter.setChosenList(1);
                onResume();
                break;
            case R.id.menu_list_2:
                groceriesRecyclerViewAdapter.setChosenList(2);
                onResume();
                break;
            case R.id.menu_list_3:
                groceriesRecyclerViewAdapter.setChosenList(3);
                onResume();
                break;
            case R.id.menu_list_4:
                groceriesRecyclerViewAdapter.setChosenList(4);
                onResume();
                break;
            case R.id.menu_list_5:
                groceriesRecyclerViewAdapter.setChosenList(5);
                onResume();
                break;
            case R.id.menu_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }
    public void saveGroceryItem(){
        String itemName = addGrocery.getText().toString();
        if (validateItemName(itemName)) {
            GroceryList newGrocery = new GroceryList(
                    itemName, groceriesRecyclerViewAdapter.getChosenList());

            AddNewGroceryList addNewGroceryList =
                    new AddNewGroceryList(this);
            addNewGroceryList.execute(newGrocery);
        }
        addGrocery.setText("");
    }
    private boolean validateItemName(String itemName){
        if(itemName.length() == 0 || itemName.trim() == "" ){
            Snackbar.make(this.addGrocery, "Not a valid item name", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void deleteGroceryItem(int position){
        GroceryList grocery = groceriesRecyclerViewAdapter.getList(position);
        if (grocery != null) {
            DeleteGroceryList deleteGroceryList =
                    new DeleteGroceryList(this);
            deleteGroceryList.execute(Long.valueOf(grocery.getId()));
        } else {
            Snackbar.make(this.addGrocery, "This Item do not exists", Snackbar.LENGTH_LONG).show();
        }
        addGrocery.setText("");
    }
    @Override
    public void onDataDeleted(Boolean success) {
        if(!success) {
            onResume();
        }
    }
    @Override
    public void onDataAdded(GroceryList groceryList) {
        if(groceryList != null) {
            onResume();
        }
    }

    @Override
    public void onGroceryItemLongClick(View v, int position) {
        Log.d(TAG, "onGroceryItemLongClick: "+
                groceriesRecyclerViewAdapter.getList(position));
        deleteGroceryItem(position);
    }
}
