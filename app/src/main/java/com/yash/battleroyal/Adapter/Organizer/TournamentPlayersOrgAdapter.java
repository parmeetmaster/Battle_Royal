package com.yash.battleroyal.Adapter.Organizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.battleroyal.Model.RegistrationRequest;
import com.yash.battleroyal.Model.TournamentRegistrationModel;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityRowJoinedPlayersOrgBinding;

import java.util.ArrayList;

public class TournamentPlayersOrgAdapter extends RecyclerView.Adapter<TournamentPlayersOrgAdapter.LeaderboardViewHolder> {


    Context context;
    ArrayList<TournamentRegistrationModel> tournamentRegistrationModels;

    public TournamentPlayersOrgAdapter(Context context, ArrayList<TournamentRegistrationModel> tournamentRegistrationModels) {
        this.context = context;
        this.tournamentRegistrationModels = tournamentRegistrationModels;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_row_joined_players_org, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        TournamentRegistrationModel tournamentRegistrationModel = tournamentRegistrationModels.get(position);


        holder.binding.name.setText(tournamentRegistrationModel.getPlayerName());
        holder.binding.teamName.setText(tournamentRegistrationModel.getTeamName());
        holder.binding.index.setText(String.format("#%d", position+1));

    }

    @Override
    public int getItemCount() {
        return tournamentRegistrationModels.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        ActivityRowJoinedPlayersOrgBinding binding;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ActivityRowJoinedPlayersOrgBinding.bind(itemView);
        }
    }
}
