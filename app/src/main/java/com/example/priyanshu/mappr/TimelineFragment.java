package com.example.priyanshu.mappr;


import android.content.Context;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Window;
import android.view.WindowManager;
import com.software.shell.fab.ActionButton;
import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import it.gmariotti.cardslib.library.view.CardViewNative;


/**
 * Created by priyanshu-sekhar on 24/2/15.
 */
public class TimelineFragment extends Fragment{

    private ActionButton actionButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_timeline, container, false);

//        RecyclerView cardList = (RecyclerView) layout.findViewById(R.id.cardList);
//        LinearLayoutManager llm = new LinearLayoutManager(layout.getContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        cardList.setLayoutManager(llm);
//
//        CardAdapter cr = new CardAdapter(getActivity().getBaseContext(), getData(10));
//       // cardList.setAdapter(cr);
        actionButton = (ActionButton) layout.findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.primary));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.primary_dark));
        actionButton.setImageResource(R.drawable.ic_action_edit);
        actionButton.show();

//        //Create a Card
//        Card card = new Card(getActivity().getBaseContext());
//        //Create a CardHeader
//        CustomHeaderInnerCard header = new CustomHeaderInnerCard(getActivity().getBaseContext(),"psp","kadali");
//        //Add Header to card
//        card.addCardHeader(header);
//        CustomExpandCard expand=new CustomExpandCard(getActivity());
//        expand.setTitle("Reply");
//        card.addCardExpand(expand);
//        //Set card in the cardView
//        CardViewNative cardView = (CardViewNative)layout.findViewById(R.id.card);
//        //Create thumbnail
//        CardThumbnail thumb = new CardThumbnail(getActivity());
//
//        //Set resource
//        thumb.setDrawableResource(R.drawable.me);
//
//        //Add thumbnail to a card
//        card.addCardThumbnail(thumb);
//
//        ViewToClickToExpand viewToClickToExpand =
//                ViewToClickToExpand.builder()
//                        .setupView(cardView);
//        card.setViewToClickToExpand(viewToClickToExpand);
//        cardView.setCard(card);
//
        final ArrayList<Card> cards = new ArrayList<Card>();

        for(int i=1;i<=3;i++) {
            addCard(cards);
        }
        final CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) layout.findViewById(R.id.card_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);
        }

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(container.getContext());
                Button cancel, okay;

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_post);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                cancel = (Button) dialog.findViewById(R.id.cancel);
                okay = (Button) dialog.findViewById(R.id.okay);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // when cancel button is clicked
                        dialog.dismiss();
                    }
                });

                okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCard(cards);
                        mCardArrayAdapter.notifyItemInserted(mCardArrayAdapter.getItemCount());
                        dialog.dismiss();
                        // when okay button is clicked
                    }
                });

                dialog.show();

            }
        });
        return layout;
    }

    private void addCard(ArrayList<Card> cards) {
        //Create a Card
        Card card = new Card(getActivity().getBaseContext());

        //Add Header to card
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(getActivity().getBaseContext(), "psp", "dynamite");
        card.addCardHeader(header);
        CustomExpandCard expand = new CustomExpandCard(getActivity());
        card.addCardExpand(expand);
        //thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());
        thumb.setDrawableResource(R.drawable.me);
        card.addCardThumbnail(thumb);

        //attach expandable view
        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .highlightView(false)
                        .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
        card.setViewToClickToExpand(viewToClickToExpand);

        cards.add(card);
    }

    public  List<CardInfo> getData(int size) {
            List<CardInfo> data = new ArrayList<>();
            for(int i = 0; i < size; i++) {
                CardInfo info=new CardInfo();
                info.title="Patra Sekhar Priyanshu";
                info.subTitle="Banana";
                data.add(info);
            }

            return data;

       }

}
class CustomHeaderInnerCard extends CardHeader {
    private String title;
    private String subTitle;
    public CustomHeaderInnerCard(Context context,String title,String subTitle) {
        super(context, R.layout.card_inner_header_layout);
        this.title=title;
        this.subTitle=subTitle;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view!=null){
            TextView t1 = (TextView) view.findViewById(R.id.text_example1);
            if (t1!=null)
                t1.setText(title);

            TextView t2 = (TextView) view.findViewById(R.id.text_example2);
            if (t2!=null)
                t2.setText(subTitle);
        }
    }
}
class CustomExpandCard extends CardExpand {
    int customId=0;
    //Use your resource ID for your inner layout
    public CustomExpandCard(Context context) {
        super(context, R.layout.card_inner_expand);

    }

    @Override
    public void setupInnerViewElements(final ViewGroup parent, View view) {

        if (view == null) return;
        final RelativeLayout l1=(RelativeLayout)view.findViewById(R.id.reply_layout);
        final EditText et  = (EditText)view.findViewById(R.id.comment);
        Button reply = (Button)view.findViewById(R.id.reply);
        customId=et.getId();
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText()!=null) {
                    /*final TextView comment = new TextView(getContext());
                    comment.setText(et.getText());
                    comment.setId(customId+1);
                    final RelativeLayout.LayoutParams params =
                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.addRule(RelativeLayout.BELOW,customId++);
                    l1.addView(comment, params);
                    et.setText(null);*/
                }
            }
        });


    }






}

