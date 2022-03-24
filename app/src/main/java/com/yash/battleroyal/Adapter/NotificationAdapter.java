package com.yash.battleroyal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.battleroyal.Activities.NotificationActivity;
import com.yash.battleroyal.Model.NotificationModel;
import com.yash.battleroyal.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Context context;
    ArrayList<NotificationModel> notificationModels;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> notificationModels){
        this.context = context;
        this.notificationModels = notificationModels;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_notification,null);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final NotificationModel model = notificationModels.get(position);

        holder.textView.setText(model.getnName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotificationActivity.class);
                intent.putExtra("nId", model.getnId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.match);
        }
    }
}
