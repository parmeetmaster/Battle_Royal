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
import com.yash.battleroyal.Activities.ContestDetailsActivity;
import com.yash.battleroyal.Model.GameNameModel;
import com.yash.battleroyal.R;

import java.util.ArrayList;

public class GameNameAdapter extends RecyclerView.Adapter<GameNameAdapter.GameViewHolder> {

    Context context;
    ArrayList<GameNameModel> gameNameModels;

    public GameNameAdapter(Context context, ArrayList<GameNameModel> gameNameModels){
        this.context = context;
        this.gameNameModels = gameNameModels;
    }


    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_contest_match,null);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        final GameNameModel model = gameNameModels.get(position);

        holder.textView.setText(model.getGameName());
        Glide.with(context)
                .load(model.getGameImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContestDetailsActivity.class);
                intent.putExtra("gameId", model.getGameId());
                intent.putExtra("gameName", model.getGameName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gameNameModels.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView ;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.match);
        }
    }
}
