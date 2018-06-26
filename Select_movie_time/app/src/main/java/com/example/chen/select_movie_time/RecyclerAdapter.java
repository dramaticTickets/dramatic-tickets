package com.example.chen.select_movie_time;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AuthorViewHolder> {

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.item, parent, false);
        AuthorViewHolder viewHolder = new AuthorViewHolder(childView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    class AuthorViewHolder extends RecyclerView.ViewHolder {

        TextView movie_start_time,movie_end_time,movie_hall, movie_price;
        Button buy_ticket_button;
        public AuthorViewHolder(View itemView) {
            super(itemView);

            movie_start_time = (TextView) itemView.findViewById(R.id.movie_start_time);
            movie_end_time = (TextView) itemView.findViewById(R.id.movie_end_time);
            movie_hall = (TextView) itemView.findViewById(R.id.movie_hall);
            movie_price = (TextView) itemView.findViewById(R.id.price);
            buy_ticket_button = (Button) itemView.findViewById(R.id.buy_tickets_button);

        }
    }
}