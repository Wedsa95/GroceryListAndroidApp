package com.olssonjonas.grocerylistapp;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olssonjonas.grocerylistapp.model.GroceryList;

import java.util.ArrayList;
import java.util.List;

class GroceriesRecyclerViewAdapter extends
        RecyclerView.Adapter<GroceriesRecyclerViewAdapter.GroceryViewHolder>{
    private static final String TAG = "RecyclerAdapter";

    private List<GroceryList> groceryItems;
    private List<Boolean> drawnThroughItems;
    private Context context;
    private int chosenList = 1;

    public GroceriesRecyclerViewAdapter(Context context, List<GroceryList> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;

    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new View");
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.grocery_item_layout, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if((groceryItems == null) || (groceryItems.size() == 0)){
            holder.groceryItemTextView.setText("No Groceries in this list");
        } else {
            GroceryList grocery = groceryItems.get(position);
            holder.groceryItemTextView.setText(grocery.getItemName());
        }
    }

    @Override
    public int getItemCount() {
        return ((groceryItems != null) && (groceryItems.size() !=0)
                ? groceryItems.size() : 1);
    }

    void loadNewDataList(List<GroceryList> newGrocerys) {
        Log.d(TAG, "loadNewDataList: Starts");
        this.groceryItems = new ArrayList<>();
        this.drawnThroughItems = new ArrayList<>();
        for (GroceryList g : newGrocerys) {
            if (g.getListName() == chosenList) {
                this.groceryItems.add(g);
                this.drawnThroughItems.add(false);
            }
        }
        notifyDataSetChanged();
    }

    public GroceryList getList(int position) {
        return ((groceryItems != null) && (groceryItems.size() !=0)
                ? groceryItems.get(position) : null);
    }
    static class GroceryViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        private static final String TAG = "GroceryViewHolder";
        TextView groceryItemTextView;

        private boolean drawnThrough = false;

        public GroceryViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "GroceryViewHolder: GroceryView = " +
                    itemView.toString() + " generated");
            this.groceryItemTextView =
                    itemView.findViewById(R.id.grocery_item_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!drawnThrough){
                        drawnThrough= true;
                        groceryItemTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }else {
                        drawnThrough= false;
                        groceryItemTextView.setPaintFlags(0);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public int getChosenList() {
        return chosenList;
    }

    public void setChosenList(int chosenList) {
        this.chosenList = chosenList;
    }
}
