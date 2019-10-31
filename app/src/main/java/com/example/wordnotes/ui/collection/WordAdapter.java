package com.example.wordnotes.ui.collection;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


    public class WordAdapter extends RecyclerView.Adapter<com.example.wordnotes.ui.collection.WordAdapter.ViewHolder> {
        private ArrayList<String[]> mWordlist;

        static class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(@NonNull View view) {
                super(view);


            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
}
