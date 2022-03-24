package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Model.RegistrationRequest;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityRowJoinedPlayersBinding;
import com.yash.battleroyal.databinding.ActivityRowLeaderboardBinding;

import java.util.ArrayList;

public class ContestPlayersAdapter extends RecyclerView.Adapter<ContestPlayersAdapter.LeaderboardViewHolder> {


    Context context;
    ArrayList<RegistrationRequest> registrationRequests;

    public ContestPlayersAdapter(Context context, ArrayList<RegistrationRequest> registrationRequests) {
        this.context = context;
        this.registrationRequests = registrationRequests;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_row_joined_players, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        RegistrationRequest registrationRequest = registrationRequests.get(position);


        holder.binding.name.setText(registrationRequest.getRequestedBy());
        holder.binding.teamName.setText(registrationRequest.getTeamName());
        holder.binding.index.setText(String.format("#%d", position+1));

    }

    @Override
    public int getItemCount() {
        return registrationRequests.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        ActivityRowJoinedPlayersBinding binding;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ActivityRowJoinedPlayersBinding.bind(itemView);
        }
    }
}
