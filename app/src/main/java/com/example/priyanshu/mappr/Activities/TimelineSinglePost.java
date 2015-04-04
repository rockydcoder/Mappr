package com.example.priyanshu.mappr.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.priyanshu.mappr.Data.CustomExpandCard;
import com.example.priyanshu.mappr.Data.CustomHeaderInnerCard;
import com.example.priyanshu.mappr.Fragments.TimelineFragment;
import com.example.priyanshu.mappr.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by priyanshu-sekhar on 2/4/15.
 */
public class TimelineSinglePost extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_card_layout);
        CustomHeaderInnerCard.single=true;
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String subTitle=intent.getStringExtra("subTitle");
        //Create a Card
        Card card = new Card(this);

        //Add Header to card
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(this,title,subTitle);

        card.addCardHeader(header);

        CustomExpandCard expand = new CustomExpandCard(this);
        card.addCardExpand(expand);
        //thumbnail
        CardThumbnail thumb = new CardThumbnail(this);
      /*  if(TimelineFragment.singleUrl!=null)
          thumb.setUrlResource(TimelineFragment.singleUrl);
        else*/
          thumb.setDrawableResource(R.drawable.soham);
        card.addCardThumbnail(thumb);

        /*//attach expandable view
        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .highlightView(false)
                        .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
        card.setViewToClickToExpand(viewToClickToExpand);*/
        //Set card in the cardView
        CardViewNative cardView = (CardViewNative)findViewById(R.id.list_cardId);
        cardView.setCard(card);
        CustomHeaderInnerCard.single=false;
    }
}
