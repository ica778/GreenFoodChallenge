package com.soyiz.greenfoodchallenge;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapeterForPledge extends RecyclerView
        .Adapter<RecyclerViewAdapeterForPledge.PledgeCardViewHolder> {



    public static class PledgeCardViewHolder extends RecyclerView.ViewHolder {
        CardView pledgeCardView;
        TextView userName;
        TextView pledgeAmount;



        PledgeCardViewHolder(View view) {
            super(view);
            pledgeCardView = view.findViewById(R.id.pledge_card_view);
            userName = view.findViewById(R.id.pledge_name);
            pledgeAmount = view.findViewById(R.id.pledge_amount);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    List<PledgeCard> pledgeCardList;
    RecyclerViewAdapeterForPledge(List<PledgeCard> pledgeCardList) {
        this.pledgeCardList = pledgeCardList;
    }

    @Override
    public int getItemCount() {
        return pledgeCardList.size();
    }

    @Override
    public PledgeCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pledge_card_view, viewGroup, false);
        PledgeCardViewHolder pledgeCardViewHolder = new PledgeCardViewHolder(view);
        return pledgeCardViewHolder;
    }



    @Override
    public void onBindViewHolder(PledgeCardViewHolder pledgeCardViewHolder, int position) {
        pledgeCardViewHolder.userName.setText(pledgeCardList.get(position).getUserName());
        pledgeCardViewHolder.pledgeAmount.setText(pledgeCardList.get(position).getPledgeAmount()+ " tons");


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
