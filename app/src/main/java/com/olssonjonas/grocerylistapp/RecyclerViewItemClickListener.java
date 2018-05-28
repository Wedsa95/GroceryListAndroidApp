package com.olssonjonas.grocerylistapp;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewItemClickListener extends
        RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerViewItemClick";

    interface OnRecyclerViewItemClickListener {
        void onGroceryItemLongClick(View v, int position);
    }

    private OnRecyclerViewItemClickListener listener;
    private GestureDetectorCompat gestureDetectorCompat;

    public RecyclerViewItemClickListener(Context context,
                                         final RecyclerView recyclerView,
                                         final OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
        this.gestureDetectorCompat = new GestureDetectorCompat(context,
                new GestureDetector.SimpleOnGestureListener(){

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && listener != null) {
                    listener.onGroceryItemLongClick(childView,
                            recyclerView.getChildAdapterPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (gestureDetectorCompat != null) {
            boolean result = gestureDetectorCompat.onTouchEvent(e);
            return result;
        } else {
            return true;
        }
    }
}
