package com.syafikha.e_baebishita;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ratingAdapter extends RecyclerView.Adapter<ratingAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<booking> itemList;

    public ratingAdapter (Context context, ArrayList<booking> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ratingAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.activity_rating_adapter, parent, false);

        ratingAdapter.ItemViewHolder itemViewHolder = new ratingAdapter.ItemViewHolder(view);

        return itemViewHolder;


    }
    @Override
    public void onBindViewHolder(ratingAdapter.ItemViewHolder holder, int position) {
        final booking item = itemList.get(position);


        holder.txtID.setText(item.bookingID);



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent detail = new Intent(context,ratingDetails.class);
                detail.putExtra("booking",item);
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(detail);
                Toast.makeText(context,"Click" +item.bookingID,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemList!= null){
            return itemList.size();
        }
        return 0;
    }
    //ViewHolder class
    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public CardView cvItem;
        public TextView txtID;
        public View mView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mView= itemView;
            cvItem = (CardView)itemView.findViewById(R.id.cvRating);
            txtID = (TextView)itemView.findViewById(R.id.txtBookingID);

        }
    }

}
