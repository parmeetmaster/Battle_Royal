package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Model.SliderModel;
import com.yash.battleroyal.Model.SlotListModel;
import com.yash.battleroyal.R;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    Context context;
    ArrayList<SliderModel> sliderModels;

    public SliderAdapter(Context context, ArrayList<SliderModel> sliderModels){
        this.context = context;
        this.sliderModels = sliderModels;
    }


    @NonNull
    @Override
    public SliderAdapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_slider,parent , false);
        return new SliderAdapter.SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.SliderViewHolder holder, int position) {

        Glide.with(context)
                .load(sliderModels.get(position).getSliderImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return sliderModels.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{

        ZoomInImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.poster);

        }
    }
}
