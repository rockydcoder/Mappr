package com.example.priyanshu.mappr;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * Created by priyanshu-sekhar on 24/2/15.
 */
public class TimelineFragment extends Fragment{
    ActionButton actionButton;
    RecyclerView mRecyclerView;
    CustomAdapter customAdapter;
    static String name="Priyanshu Sekhar Patra";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.layout_timeline,container,false);
//        mRecyclerView = (RecyclerView) layout.findViewById(R.id.groups_list);
//        //customAdapter = new CustomAdapter(getActivity(), getData());
//        mRecyclerView.setAdapter(customAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        actionButton =(ActionButton)layout.findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.tab_bg));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.primary_dark));
        actionButton.setImageResource(R.drawable.ic_action_edit);
        actionButton.show();

        //Create a Card
        Card card = new Card(getActivity().getBaseContext());
        //Create a CardHeader
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(getActivity().getBaseContext());

        //Add Header to card
        card.addCardHeader(header);
        CustomExpandCard expand=new CustomExpandCard(getActivity());
        expand.setTitle("Reply");
        card.addCardExpand(expand);
        //Set card in the cardView
        CardViewNative cardView = (CardViewNative)layout.findViewById(R.id.card);
        //Create thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());

        //Set resource
        thumb.setDrawableResource(R.drawable.me);

        //Add thumbnail to a card
        card.addCardThumbnail(thumb);

        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .setupView(cardView);
        card.setViewToClickToExpand(viewToClickToExpand);
        cardView.setCard(card);


        return layout;
    }
}
class CustomHeaderInnerCard extends CardHeader {

    public CustomHeaderInnerCard(Context context) {
        super(context, R.layout.card_inner_header_layout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view!=null){
            TextView t1 = (TextView) view.findViewById(R.id.text_example1);
            if (t1!=null)
                t1.setText("Patra Sekar Priyanshu");

            TextView t2 = (TextView) view.findViewById(R.id.text_example2);
            if (t2!=null)
                t2.setText("Banana");
        }
    }
}
class CustomExpandCard extends CardExpand {
    int customId=0;
    private RecyclerView mRecyclerView;
    private CustomAdapter customAdapter;
    //Use your resource ID for your inner layout
    public CustomExpandCard(Context context) {
        super(context, R.layout.card_inner_expand);

    }

    @Override
    public void setupInnerViewElements(final ViewGroup parent, View view) {

        if (view == null) return;
//        mRecyclerView=(RecyclerView)parent.findViewById(R.id.comments_list);
//        customAdapter=new CustomAdapter(parent.getContext(),getData());
//        mRecyclerView.setAdapter(customAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));
        final RelativeLayout l1=(RelativeLayout)view.findViewById(R.id.reply_layout);
        final EditText et  = (EditText)view.findViewById(R.id.comment);
        Button reply = (Button)view.findViewById(R.id.reply);
        customId=et.getId();
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText()!=null) {
                    final TextView comment = new TextView(getContext());
                    comment.setText(et.getText());
                    comment.setId(customId+1);
                    final RelativeLayout.LayoutParams params =
                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.addRule(RelativeLayout.BELOW,customId++);
                    l1.addView(comment, params);
                    et.setText(null);
                }
            }
        });


    }
    public static List<SingleRowData> getData() {
        List<SingleRowData> data = new ArrayList<>();
        String[] titles = {"Group 1", "Group 2", "Group 3", "Group 4", "Group 5"};
        for(int i = 0; i < titles.length; i++) {
            SingleRowData current = new SingleRowData();
            current.setIconId(R.drawable.user);
            current.setText(titles[i]);
            data.add(current);
        }

        return data;

    }
}
