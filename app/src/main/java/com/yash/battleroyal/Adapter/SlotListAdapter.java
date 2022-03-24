package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Model.SlotListModel;
import com.yash.battleroyal.Model.TournamentModel;
import com.yash.battleroyal.R;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.ArrayList;

public class SlotListAdapter extends RecyclerView.Adapter<SlotListAdapter.SlotListViewHolder> {
    Context context;
    ArrayList<SlotListModel> slotListModels;

    public SlotListAdapter(Context context, ArrayList<SlotListModel> slotListModels){
        this.context = context;
        this.slotListModels = slotListModels;
    }


    @NonNull
    @Override
    public SlotListAdapter.SlotListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_slot_list,parent , false);
        return new SlotListAdapter.SlotListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotListAdapter.SlotListViewHolder holder, int position) {

        holder.textView.setText(slotListModels.get(position).getSlotName());
        Glide.with(context)
                .load(slotListModels.get(position).getSlotImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return slotListModels.size();
    }

    public class SlotListViewHolder extends RecyclerView.ViewHolder{

        ZoomInImageView imageView;
        TextView textView;


        public SlotListViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.poster);
            textView = itemView.findViewById(R.id.match);

        }
    }
}
