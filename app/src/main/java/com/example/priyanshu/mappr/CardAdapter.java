package com.example.priyanshu.mappr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{
    private List<CardInfo> cardList;
    static  Context context;
    public CardAdapter(Context context,List<CardInfo> cardList){
        this.cardList=cardList;
        this.context=context;
    }
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
            CardInfo ci=cardList.get(position);
        Log.d("cardList",position+" "+ci.title+" "+ci.subTitle);
            holder.setTitle(ci.title);
            holder.setSubTitle(ci.subTitle);
        //Create a Card
        Card card = new Card(context);
        //Log.d("card",context.toString());
        //Create a CardHeader
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(context,ci.title,ci.subTitle);

        //Add Header to card
        card.addCardHeader(header);
        CustomExpandCard expand=new CustomExpandCard(context);
        card.addCardExpand(expand);
        //Set card in the cardView
        CardViewNative cardView = new CardViewNative(context);

        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(context);

        //Set resource
        thumb.setDrawableResource(R.drawable.me);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .setupView(cardView);
        card.setViewToClickToExpand(viewToClickToExpand);
        cardView.setCard(card);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("onCreate",i+" "+context);
        View itemView = LayoutInflater.
                from(context).
                inflate(R.layout.custom_card_layout, viewGroup, false);
        Log.d("itemView",itemView.getContext()+"");
        return new CardViewHolder(itemView);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        private String title;
        private String subTitle;

        public CardViewHolder(View itemView) {
            super(itemView);
            title="hh";
            subTitle="hhj";

//            //Create a Card
//            Card card = new Card(context);
//            Log.d("card",context.toString());
//            //Create a CardHeader
//            CustomHeaderInnerCard header = new CustomHeaderInnerCard(context,title,subTitle);
//
//            //Add Header to card
//            card.addCardHeader(header);
//            CustomExpandCard expand=new CustomExpandCard(context);
//            card.addCardExpand(expand);
//            //Set card in the cardView
//            CardViewNative cardView = new CardViewNative(context);
//
//            //Create thumbnail
//            CardThumbnail thumb = new CardThumbnail(context);
//
//            //Set resource
//            thumb.setDrawableResource(R.drawable.me);
//
//            //Add thumbnail to a card
//            card.addCardThumbnail(thumb);
//
//            ViewToClickToExpand viewToClickToExpand =
//                    ViewToClickToExpand.builder()
//                            .setupView(cardView);
//            card.setViewToClickToExpand(viewToClickToExpand);
//            cardView.setCard(card);
        }

        public void setTitle(String title){
            this.title=title;
        }

        public void setSubTitle(String subTitle){
            this.subTitle=subTitle;
        }
    }

}