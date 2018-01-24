package com.syafikha.e_baebishita;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by Acer on 1/10/2018.
 */

public class babysitterAdapter extends RecyclerView.Adapter<babysitterAdapter.ItemViewHolder> {
    private Context context;
    private ArrayList<babysitter> itemList;

    public babysitterAdapter (Context context, ArrayList<babysitter> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.babysitter_cardview, parent, false);

        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;


    }
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final babysitter item = itemList.get(position);
        Picasso.with(context)

                .load(item.image)
                .placeholder(R.drawable.baebishita)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImg);



        holder.txtName.setText(item.babysitter_name);
        holder.txtReligion.setText(item.babysitter_religion);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent detail = new Intent(context,details.class);
                detail.putExtra("babysitter",item);
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(detail);
                Toast.makeText(context,"Click" +item.babysitter_name,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemList != null){
            return itemList.size();
        }
        return 0;
    }
    //ViewHolder class
    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public CardView cvItem;
        public ImageView ivImg;
        public TextView txtName,txtReligion;
        public View mView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mView= itemView;
            cvItem = (CardView)itemView.findViewById(R.id.cvBabysitter);
            ivImg = (ImageView)itemView.findViewById(R.id.image);
            txtName = (TextView)itemView.findViewById(R.id.txtBabysitterName);
            txtReligion = (TextView)itemView.findViewById(R.id.txtBabysitterReligion);

        }
    }

}
