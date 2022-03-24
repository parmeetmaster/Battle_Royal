package com.yash.battleroyal.Adapter.Organizer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Activities.Organizer.TournamentRegistrationOrgActivity;
import com.yash.battleroyal.Activities.TournamentRegistrationActivity;
import com.yash.battleroyal.Model.TournamentModel;
import com.yash.battleroyal.R;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.ArrayList;

public class TournamentOrgAdapter extends RecyclerView.Adapter<TournamentOrgAdapter.TournamentViewHolder> {

    Context context;
    ArrayList<TournamentModel> tournamentModels;

    public TournamentOrgAdapter(Context context, ArrayList<TournamentModel> tournamentModels){
        this.context = context;
        this.tournamentModels = tournamentModels;
    }


    @NonNull
    @Override
    public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_tournament_match,parent , false);
        return new TournamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
        final TournamentModel model = tournamentModels.get(position);

        holder.textView.setText(model.getTournamentName());

        Glide.with(context)
                .load(model.getTournamentImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TournamentRegistrationOrgActivity.class);
                intent.putExtra("tournamentId", model.getTournamentId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tournamentModels.size();
    }

    public class TournamentViewHolder extends RecyclerView.ViewHolder{

        ZoomInImageView imageView;
        TextView textView;


        public TournamentViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.poster);
            textView = itemView.findViewById(R.id.match);

        }
    }
}
