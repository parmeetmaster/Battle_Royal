package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yash.battleroyal.Model.User;
import com.yash.battleroyal.R;
import com.yash.battleroyal.databinding.ActivityRowLeaderboardBinding;

import java.util.ArrayList;

public class LeaderboardTopEarnerAdapter extends RecyclerView.Adapter<LeaderboardTopEarnerAdapter.LeaderboardViewHolder> {


    Context context;
    ArrayList<User> users1;

    public LeaderboardTopEarnerAdapter(Context context, ArrayList<User> users1) {
        this.context = context;
        this.users1 = users1;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_row_leaderboard, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        User user = users1.get(position);

        holder.binding.name.setText(user.getName());
        holder.binding.coins.setText(String.valueOf(user.getCoins()+ user.getbCoins()));
        holder.binding.index.setText(String.format("#%d", position+1));

        Glide.with(context)
                .load(user.getName())
                .into(holder.binding.imageView2);

    }

    @Override
    public int getItemCount() {
        return users1.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        ActivityRowLeaderboardBinding binding;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ActivityRowLeaderboardBinding.bind(itemView);
        }
    }
}
