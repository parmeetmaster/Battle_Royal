package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Activities.Match_Details_T1;
import com.yash.battleroyal.Model.MatchT1Model;
import com.yash.battleroyal.R;

import java.util.ArrayList;

public class MatchT1Adapter extends RecyclerView.Adapter<MatchT1Adapter.MatchViewHolder> {

    Context context;
    ArrayList<MatchT1Model> matchT1Models;

    public MatchT1Adapter(Context context, ArrayList<MatchT1Model> matchT1Models){
        this.context = context;
        this.matchT1Models = matchT1Models;
    }


    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_match,null);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        final MatchT1Model matchT1Model = matchT1Models.get(position);

        holder.textView.setText(matchT1Model.getMatchName());
        Glide.with(context)
                .load(matchT1Model.getMatchImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Match_Details_T1.class);
                intent.putExtra("matchId", matchT1Model.getMatchId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return matchT1Models.size();
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView ;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.match);

        }
    }
}
