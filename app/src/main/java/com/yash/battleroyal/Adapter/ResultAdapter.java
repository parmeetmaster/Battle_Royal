package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Model.ResultModel;
import com.yash.battleroyal.R;
import com.zolad.zoominimageview.ZoomInImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    Context context;
    ArrayList<ResultModel> resultModels;

    public ResultAdapter(Context context, ArrayList<ResultModel> resultModels) {
        this.context = context;
        this.resultModels = resultModels;
    }

    @NonNull
    @NotNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_result,parent , false);
        return new ResultAdapter.ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ResultAdapter.ResultViewHolder holder, int position) {

        holder.textView.setText(resultModels.get(position).getResultName());
        Glide.with(context)
                .load(resultModels.get(position).getResultImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return resultModels.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{

        ZoomInImageView imageView;
        TextView textView;

        public ResultViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.poster);
            textView = itemView.findViewById(R.id.result);
        }
    }
}
