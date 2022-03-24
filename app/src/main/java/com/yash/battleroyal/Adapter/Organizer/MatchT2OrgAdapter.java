package com.yash.battleroyal.Adapter.Organizer;

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
import com.yash.battleroyal.Activities.Organizer.OrgMatchActivity;
import com.yash.battleroyal.Model.MatchT2Model;
import com.yash.battleroyal.R;

import java.util.ArrayList;

public class MatchT2OrgAdapter extends RecyclerView.Adapter<MatchT2OrgAdapter.MatchViewHolder> {

    Context context;
    ArrayList<MatchT2Model> matchT2Models
            ;

    public MatchT2OrgAdapter(Context context, ArrayList<MatchT2Model> matchT2Models){
        this.context = context;
        this.matchT2Models = matchT2Models;
    }


    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_match,null);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        final MatchT2Model model = matchT2Models.get(position);

        holder.textView.setText(model.getMatchName());
        Glide.with(context)
                .load(model.getMatchImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrgMatchActivity.class);
                intent.putExtra("matchId", model.getMatchId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return matchT2Models.size();
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
