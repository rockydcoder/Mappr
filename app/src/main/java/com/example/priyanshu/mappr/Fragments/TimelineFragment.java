package com.example.priyanshu.mappr.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.priyanshu.mappr.Activities.CommentPage;
import com.example.priyanshu.mappr.Adapters.CustomAdapter;
import com.example.priyanshu.mappr.Adapters.MapprDatabaseAdapter;
import com.example.priyanshu.mappr.Data.CardInfo;
import com.example.priyanshu.mappr.Data.SingleRowData;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.network.VolleySingleton;
import com.software.shell.fab.ActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;


/**
 * Created by priyanshu-sekhar on 24/2/15.
 */
public class TimelineFragment extends Fragment{

    private ActionButton actionButton;
    ArrayList<CardInfo> cardData=new ArrayList<>();
    String title="psp";
    String subTitle="dynamite";
    ArrayList<Card> cards=new ArrayList<>();
    public ArrayList<Integer> postIds;
    public ArrayList<String> title_=new ArrayList<>();
    public ArrayList<String> subTitle_=new ArrayList<>();
    public ArrayList<String> timeStamp_=new ArrayList<>();

    private String url="http://www.mappr.in/ipa/get_status.php?request_type=get_this_post&postid=";
    public static String TimeStamp=System.currentTimeMillis()+"";
    static final String cardInfoTAG="cards";

    public static String iconIdTag="iconId";
    public static String userNameTag="userNames";
    public static String userCommentTag="userComment";
    public static String noOfCommentsTag="noOfComments";
    public static String name="Soham Choksi";
    /***************TimeStamp variables******************/
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    AutoCompleteTextView group;
    Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public TimelineFragment(ArrayList<Integer> postIds){
        this.postIds=postIds;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.layout_timeline, container, false);
       /* LinearLayout cardMainLayout= (LinearLayout)layout.findViewById(R.id.card_recyclerview)
                .findViewById(R.id.list_cardId).findViewById(R.id.card_main_layout);

        cardMainLayout.getLayoutParams().height=300;
        cardMainLayout.invalidate();*/
        final CustomRecycleViewAdapter mCardArrayAdapter = new CustomRecycleViewAdapter(getActivity(), cards);
        if(savedInstanceState==null){
            for(int id:postIds){
                Log.d("Id",id+"");
                RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                        url+id,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    title_.add(response.getString("AccountType")+
                                            response.getString("AccountID"));
                                    subTitle_.add(response.getString("PostContent"));
                                    timeStamp_.add(response.getString("PostedOn"));
                                    for(int i=0;i<title_.size();i++){
                                        title=title_.get(i);
                                        subTitle=subTitle_.get(i);
                                        TimeStamp=timeStamp_.get(i);
                                        addCard(cards,title,subTitle,true);
                                    }
                                    mCardArrayAdapter.notifyItemInserted(mCardArrayAdapter.getItemCount());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("error", error.getMessage());

                            }
                        }

                );
               requestQueue.add(request);

            }


        }
        else{
            cardData=savedInstanceState.getParcelableArrayList(cardInfoTAG);
            Log.d("savedInstanceState","called");
            for(CardInfo data:cardData) {
                addCard(cards, data.title, data.subTitle,false);
            }
        }

        actionButton = (ActionButton) layout.findViewById(R.id.action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.primary));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.primary_dark));
        actionButton.setImageResource(R.drawable.ic_action_edit);
        actionButton.show();



        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) layout.findViewById(R.id.card_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Set the empty view
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mCardArrayAdapter);

        }

//        title = "<b>" + name + "</b>" + "<font color='#BBBBBB'> posted in </font>" +
//                "<b>" + "" + "</b>";
//        subTitle = "mk";
//        addCard(cards, title, subTitle, true);
        mCardArrayAdapter.notifyItemInserted(mCardArrayAdapter.getItemCount());
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapprDatabaseAdapter mapprDatabaseAdapter=new MapprDatabaseAdapter(container.getContext());
                ArrayList<String> groups=mapprDatabaseAdapter.getGroups();
                if(groups.size()==0){
                    Toast.makeText(getActivity(),"Please add a group first",Toast.LENGTH_SHORT).show();
                }
                else {
                    Button cancel, okay;
                    dialog = new Dialog(container.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.layout_dialog_post);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    group=(AutoCompleteTextView)dialog.findViewById(R.id.group);
                    ArrayAdapter adapter = new ArrayAdapter(dialog.getContext(),
                            android.R.layout.simple_expandable_list_item_1, groups);
                    group.setAdapter(adapter);

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
                            EditText post = (EditText) dialog.findViewById(R.id.post);
                            title = "<b>" + name + "</b>" + "<font color='#BBBBBB'> posted in </font>" +
                                    "<b>" + group.getText().toString() + "</b>";
                            subTitle = post.getText().toString();
                            addCard(cards, title, subTitle, true);
                            mCardArrayAdapter.notifyItemInserted(mCardArrayAdapter.getItemCount());
                            dialog.dismiss();
                            // when okay button is clicked
                        }
                    });
                    dialog.show();
                }


            }
        });


        return layout;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(cardInfoTAG,cardData);
        super.onSaveInstanceState(outState);

    }


    private void addCard(ArrayList<Card> cards,String title,String subTitle,boolean create) {
        //Create a Card
        Card card = new Card(getActivity().getBaseContext());

        //Add Header to card
        CustomHeaderInnerCard header = new CustomHeaderInnerCard(getActivity().getBaseContext(),title,subTitle);

        card.addCardHeader(header);

        CustomExpandCard expand = new CustomExpandCard(getActivity());
        card.addCardExpand(expand);
        //thumbnail
        CardThumbnail thumb = new CardThumbnail(getActivity());
        thumb.setDrawableResource(R.drawable.soham);
        card.addCardThumbnail(thumb);

        //attach expandable view
        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .highlightView(false)
                        .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
        card.setViewToClickToExpand(viewToClickToExpand);
        if(create)
            cardData.add(new CardInfo(title,subTitle,""));
        cards.add(card);
    }

    public static long currentDate() {


        return System.currentTimeMillis();
    }
    public static String getTimeAgo(long time) {

        long now = currentDate();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
                t1.setText(Html.fromHtml(title));

            TextView t2 = (TextView) view.findViewById(R.id.text_example2);
            if (t2!=null) {
                t2.setText(subTitle);
                int size=subTitle.length()/30;
                int height=200;
                if(size<=2)
                    height=200;
                else if(size==3)
                    height=300;
                else if(size<=6)
                    height=500;
                else
                    height=700;
                view.getLayoutParams().height=height;
            }

        }
    }
}
class CustomExpandCard extends CardExpand {
    private RecyclerView mRecyclerView;
    private CustomAdapter customAdapter;
    static ArrayList<SingleRowData> comments;
    String title=TimelineFragment.name;
    int noOfComments=0;
    View commentRow1,commentRow2;
    ArrayList<Integer> iconId = null;
    ArrayList<String>  userNames=null;
    ArrayList<String>  userComments=null;

    ImageView userPic1,userPic2;
    TextView moreComments,userName1,userName2,userComment1,userComment2;
    LinearLayout totalLayout,comment1Layout,comment2Layout;
    EditText et;
    ImageButton reply;
    //Use your resource ID for your inner layout
    public CustomExpandCard(Context context) {
        super(context, R.layout.card_inner_expand);
    }


    @Override
    public void setupInnerViewElements(final ViewGroup parent, View view) {
        if (view == null) return;
        iconId=new ArrayList<>();
        userNames=new ArrayList<>();
        userComments=new ArrayList<>();

        commentRow1=view.findViewById(R.id.commentRow1);
        commentRow2=view.findViewById(R.id.commentRow2);
        moreComments=(TextView)view.findViewById(R.id.more_comment);
        userPic1=(ImageView)commentRow1.findViewById(R.id.profile_picture);
        userPic2=(ImageView)commentRow2.findViewById(R.id.profile_picture);
        userName1=(TextView)commentRow1.findViewById(R.id.userName);
        userName2=(TextView)commentRow2.findViewById(R.id.userName);
        userComment1=(TextView)commentRow1.findViewById(R.id.userComment);
        userComment2=(TextView)commentRow2.findViewById(R.id.userComment);

        totalLayout=(LinearLayout)view.findViewById(R.id.commentLayout);
        comment1Layout=(LinearLayout)totalLayout.findViewById(R.id.comment1Layout);
        comment2Layout=(LinearLayout)totalLayout.findViewById(R.id.comment2Layout);
        et  = (EditText)view.findViewById(R.id.commentText);
        reply = (ImageButton)view.findViewById(R.id.replyButton);
        updateVisibility(view);
        et.requestFocus();
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et.getText()!=null) {
                    String userC2=userComment2.getText().toString();
                    userComment2.setText("");
                    if(noOfComments==0){
                        userPic1.setImageResource(R.drawable.soham);
                        userName1.setText(title);
                        userComment1.setText(et.getText());

                    }
                    else if(noOfComments==1){
                        userPic2.setImageResource(R.drawable.soham);
                        userName2.setText(title);
                        userComment2.setText(et.getText());

                    }
                    else {
                        userPic1.setImageDrawable(userPic2.getDrawable());
                        userName1.setText(userName2.getText());
                        userComment1.setText(userC2);
                        userPic2.setImageResource(R.drawable.soham);
                        userName2.setText(title);
                        userComment2.setText(et.getText());
                        String text=(noOfComments-1)==1?"...view 1 more comment":
                                "...view "+(noOfComments-1)+" more comments";
                        moreComments.setText(text);

                    }
                    iconId.add(userPic1.getId());
                    userNames.add(title);
                    userComments.add(et.getText().toString());
                    noOfComments++;
                    view.refreshDrawableState();
                    updateVisibility(view);
                    et.setText(null);
                }
            }
        });
        moreComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), CommentPage.class);
                intent.putExtra(TimelineFragment.noOfCommentsTag,noOfComments);
                Log.d(TimelineFragment.noOfCommentsTag,noOfComments+"");
                intent.putExtra(TimelineFragment.iconIdTag,iconId);
                intent.putExtra(TimelineFragment.userNameTag,userNames);
                intent.putExtra(TimelineFragment.userCommentTag,userComments);
                view.getContext().startActivity(intent);
            }
        });

    }


    public void updateVisibility(View view){
        /*totalLayout.setVisibility(View.VISIBLE);
        moreComments.setVisibility(View.GONE);
        userPic1.setVisibility(View.GONE);
        userName1.setVisibility(View.GONE);
        userComment1.setVisibility(View.GONE);
        userPic2.setVisibility(View.GONE);
        userName2.setVisibility(View.GONE);
        userComment2.setVisibility(View.GONE);
        et.setVisibility(View.GONE);
        reply.setVisibility(View.GONE);*/


        if(noOfComments==0){

            moreComments.setVisibility(View.GONE);
            //divider1.setVisibility(View.GONE);
            userPic1.setVisibility(View.GONE);
            userName1.setVisibility(View.GONE);
            userComment1.setVisibility(View.GONE);
            //divider2.setVisibility(View.GONE);
            userName2.setVisibility(View.GONE);
            userPic2.setVisibility(View.GONE);
            userComment2.setVisibility(View.GONE);

            et.setVisibility(View.VISIBLE);
            reply.setVisibility(View.VISIBLE);
            //totalLayout.setVisibility(View.GONE);


            //divider3.setVisibility(View.GONE);
//            ((FrameLayout)view.getParent()).removeView(moreComments);
        }
        else if (noOfComments==1){
            et.setVisibility(View.GONE);
            reply.setVisibility(View.GONE);
            //totalLayout.setVisibility(View.VISIBLE);
            moreComments.setVisibility(View.VISIBLE);
            userPic1.setVisibility(View.VISIBLE);
            userName1.setVisibility(View.VISIBLE);
            userComment1.setVisibility(View.VISIBLE);
            userName2.setVisibility(View.GONE);
            userPic2.setVisibility(View.GONE);
            userComment2.setVisibility(View.GONE);

            moreComments.setVisibility(View.GONE);
            et.setVisibility(View.VISIBLE);
            reply.setVisibility(View.VISIBLE);

            et.requestFocus();



        }
        else if(noOfComments==2){
            et.setVisibility(View.GONE);
            reply.setVisibility(View.GONE);
            userPic1.setVisibility(View.GONE);
            userName1.setVisibility(View.GONE);
            userComment1.setVisibility(View.GONE);


            moreComments.setVisibility(View.VISIBLE);
            userPic1.setVisibility(View.VISIBLE);
            userName1.setVisibility(View.VISIBLE);
            userComment1.setVisibility(View.VISIBLE);
            userPic2.setVisibility(View.VISIBLE);
            userName2.setVisibility(View.VISIBLE);
            userComment2.setVisibility(View.VISIBLE);
            moreComments.setVisibility(View.GONE);
            et.setVisibility(View.VISIBLE);
            reply.setVisibility(View.VISIBLE);

            et.requestFocus();

        }
        else{
            et.setVisibility(View.GONE);
            reply.setVisibility(View.GONE);
            userPic2.setVisibility(View.GONE);
            userName2.setVisibility(View.GONE);
            userComment2.setVisibility(View.GONE);
            userPic1.setVisibility(View.GONE);
            userName1.setVisibility(View.GONE);
            userComment1.setVisibility(View.GONE);
            moreComments.setVisibility(View.GONE);

            et.setVisibility(View.GONE);
            reply.setVisibility(View.GONE);
            moreComments.setVisibility(View.VISIBLE);
            userPic1.setVisibility(View.VISIBLE);
            userName1.setVisibility(View.VISIBLE);
            userComment1.setVisibility(View.VISIBLE);
            userPic2.setVisibility(View.VISIBLE);
            userName2.setVisibility(View.VISIBLE);
            userComment2.setVisibility(View.VISIBLE);
            et.setVisibility(View.VISIBLE);
            reply.setVisibility(View.VISIBLE);

            et.requestFocus();
        }
    }



}
class CustomRecycleViewAdapter extends CardArrayRecyclerViewAdapter{
    //List<Card> cards;
    public CustomRecycleViewAdapter(Context context, List<Card> cards) {
        super(context, cards);
        //this.cards=cards;
    }


    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int position) {
        super.onBindViewHolder(cardViewHolder, position);
//        Thread time=new Thread(new Runnable() {
//            @Override
//            public void run() {
//               Log.d("thread","running");
//                try {
//                    wait(60000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                for(Card card:mCards)
//                    ;//card.
//            }
//        });
//        time.start();
        TextView reply=(TextView)cardViewHolder.itemView.findViewById(R.id.reply);
        TextView timeStamp=(TextView)cardViewHolder.itemView.findViewById(R.id.timestamp);
        timeStamp.setText(TimelineFragment.getTimeAgo(Long.parseLong(TimelineFragment.TimeStamp)));
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCards.get(position).doToogleExpand();
            }
        });
        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}



