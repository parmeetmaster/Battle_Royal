package com.yash.battleroyal.Adapter.Organizer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yash.battleroyal.Activities.Organizer.ContestRegisterOrgActivity;
import com.yash.battleroyal.Activities.Organizer.NewPaidContestOrgActivity;
import com.yash.battleroyal.Model.ContestModel;
import com.yash.battleroyal.Model.ContestPrevModel;
import com.yash.battleroyal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContestOrgPreviousAdapter extends RecyclerView.Adapter<ContestOrgPreviousAdapter.ContestViewHolder> {


    Context context;
    ArrayList<ContestPrevModel> contestPrevModels;



    public ContestOrgPreviousAdapter(Context context, ArrayList<ContestPrevModel> contestPrevModels) {
        this.context = context;
        this.contestPrevModels = contestPrevModels;
    }

    @NonNull
    @NotNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_paid_match,null);
        return new ContestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContestOrgPreviousAdapter.ContestViewHolder holder, int position) {
        final  ContestPrevModel model = contestPrevModels.get(position);

        String uid = FirebaseAuth.getInstance().getUid();

        String gameId = ((NewPaidContestOrgActivity) context).getIntent().getStringExtra("gameId");


        holder.contestName.setText(model.getcName());
        holder.dateNewTv.setText(model.getcDate());
        holder.timeNewTv.setText(model.getcTime());
        holder.mapNewTv.setText(model.getcMap());
        holder.perspectiveNewTv.setText(model.getcPerspective());
        holder.winnersNewTv.setText(model.getcWinner());
        holder.perKillNewTv.setText(model.getcKill());
        holder.bonusCoinsNewTv.setText(model.getcBonus());
        holder.feeNewTv.setText(model.getcFee());
        holder.typeNewTv.setText(model.getcType());
        holder.slotsNewTv.setText(model.getcSlots());

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database
                .collection("game")
                .document(gameId)
                .collection("contest")
                .document(model.getcId())
                .collection("registration")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int counter = (int) queryDocumentSnapshots.size();
                holder.playerNewTv.setText(Integer.toString(counter));

            }
        });
//
//        holder.joinBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String gameId = ((NewPaidContestOrgActivity) context).getIntent().getStringExtra("gameId");
//                Intent intent = new Intent(context, ContestRegisterOrgActivity.class);
//                intent.putExtra("contestId", model.getcId());
//                intent.putExtra("contestName", model.getcName());
//                intent.putExtra("contestSlots", model.getcSlots());
//                intent.putExtra("entryFee", model.getcFee());
//                intent.putExtra("bonus", model.getcBonus());
//                intent.putExtra("gameId", gameId);
//                context.startActivity(intent);
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return contestPrevModels.size();
    }

    public class ContestViewHolder extends RecyclerView.ViewHolder {
        TextView contestName, dateNewTv,timeNewTv,mapNewTv,perspectiveNewTv,
               winnersNewTv,perKillNewTv,feeNewTv,typeNewTv,
                slotsNewTv,joinBtn,playerNewTv,bonusCoinsNewTv;
        public ContestViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            contestName = itemView.findViewById(R.id.contestName);
            dateNewTv = itemView.findViewById(R.id.dateNewTv);
            timeNewTv = itemView.findViewById(R.id.timeNewTv);
            mapNewTv = itemView.findViewById(R.id.mapNewTv);
            perspectiveNewTv = itemView.findViewById(R.id.perspectiveNewTv);
            winnersNewTv = itemView.findViewById(R.id.winnersNewTv);
            perKillNewTv = itemView.findViewById(R.id.perKillNewTv);
            bonusCoinsNewTv = itemView.findViewById(R.id.bonusCoinsNewTv);
            feeNewTv = itemView.findViewById(R.id.feeNewTv);
            typeNewTv = itemView.findViewById(R.id.typeNewTv);
            slotsNewTv = itemView.findViewById(R.id.slotsNewTv);
            playerNewTv = itemView.findViewById(R.id.playerNewTv);
            joinBtn = itemView.findViewById(R.id.joinBtn);




        }
    }
}
