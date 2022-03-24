package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Model.FixtureModel;
import com.yash.battleroyal.Model.SlotListModel;
import com.yash.battleroyal.R;
import com.zolad.zoominimageview.ZoomInImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder> {
    Context context;
    ArrayList<FixtureModel> fixtureModels;

    public FixtureAdapter(Context context, ArrayList<FixtureModel> fixtureModels) {
        this.context = context;
        this.fixtureModels = fixtureModels;
    }

    @NonNull
    @NotNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_fixture,parent , false);
        return new FixtureAdapter.FixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FixtureAdapter.FixtureViewHolder holder, int position) {

        holder.textView.setText(fixtureModels.get(position).getFixName());
        Glide.with(context)
                .load(fixtureModels.get(position).getFixImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return fixtureModels.size();
    }

    public class FixtureViewHolder extends RecyclerView.ViewHolder{
        ZoomInImageView imageView;
        TextView textView;
        public FixtureViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster);
            textView = itemView.findViewById(R.id.match);
        }
    }
}