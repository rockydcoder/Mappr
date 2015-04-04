package com.example.priyanshu.mappr.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.priyanshu.mappr.Adapters.MapprDatabaseAdapter;
import com.example.priyanshu.mappr.Data.CardInfo;
import com.example.priyanshu.mappr.Data.CustomExpandCard;
import com.example.priyanshu.mappr.Data.CustomHeaderInnerCard;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.network.VolleySingleton;
import com.software.shell.fab.ActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

import static com.example.priyanshu.mappr.Extras.Keys.LogIn.KEY_POST_PICTURE;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_PROFILE_IMAGE;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.URL_PROFILE_PICTURE;


/**
 * Created by priyanshu-sekhar on 24/2/15.
 */
public class TimelineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    public static ArrayList<Integer> postIds;
    public ArrayList<String> title_=new ArrayList<>();
    public ArrayList<String> subTitle_=new ArrayList<>();
    public ArrayList<String> timeStamp_=new ArrayList<>();
    public ArrayList<Bitmap> postPic_ =new ArrayList<>();

    private String url="http://www.mappr.in/ipa/get_status.php?request_type=get_this_post&postid=";
    public static String TimeStamp=System.currentTimeMillis()+"";
    ActionButton actionButton;
    AutoCompleteTextView group;
    Dialog dialog;

    ArrayList<CardInfo> cardData;
    String title="psp";
    String subTitle="dynamite";
    private ArrayList<Card> cards = new ArrayList<>();
    static final String cardInfoTAG="cards";

    public static String iconIdTag = "iconId";
    public static String userNameTag="userNames";
    public static String userCommentTag="userComment";
    public static String noOfCommentsTag="noOfComments";
    public static String name="Soham Choksi";

    /***************TimeStamp variables******************/
    private static final int SECONDS = 60;
    private static final int MINUTE_MILLIS = 60 * SECONDS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private int count=0;
    private boolean loading=false;
    private CustomRecycleViewAdapter mCardArrayAdapter;
    private MapprDatabaseAdapter mapprDatabaseAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageLoader imageLoader;
    private Bitmap dispPic;
    private Drawable post_pic;
    public  String picUrl=null;
    public  String singleUrl=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public TimelineFragment(ArrayList<Integer> postIds){
       // this.postIds=postIds;
    }
    public TimelineFragment(){
       // this.postIds=new ArrayList<>();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.layout_timeline, container, false);
        mapprDatabaseAdapter=new MapprDatabaseAdapter(getActivity().getApplicationContext());
        swipeRefreshLayout=(SwipeRefreshLayout)(layout.findViewById(R.id.card_swipe));
        swipeRefreshLayout.setOnRefreshListener(this);

        CustomHeaderInnerCard.single=false;
       /* LinearLayout cardMainLayout= (LinearLayout)layout.findViewById(R.id.card_recyclerview)
                .findViewById(R.id.list_cardId).findViewById(R.id.card_main_layout);

        cardMainLayout.getLayoutParams().height=300;
        cardMainLayout.invalidate();*/
        mCardArrayAdapter = new CustomRecycleViewAdapter(getActivity(), cards);
        if(savedInstanceState==null){
            cardData=new ArrayList<>();
            postIds=mapprDatabaseAdapter.getPostIds();
            /*ArrayList<Integer> userIds=mapprDatabaseAdapter.getUserIds();
            Log.d("userIds size=",userIds.size()+"");
            for(int userId:userIds)
                Log.d("student_userIds",userId+"");
            */
            final HashMap<String,HashMap<Integer,String>> users=mapprDatabaseAdapter.getUserNames();

            final ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setTitle("Retrieving your timeline");
            progress.setMessage("Receiving posts by Speed-Post :D ");
            progress.show();
            for(final int id:postIds){
                if(count<10) {
                    count++;
                    RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                            url + id,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String userType = response.getString("AccountType");
                                        Integer userId = Integer.parseInt(response.getString("AccountID"));
                                        Log.d("Account", userType + userId);
                                        HashMap<Integer, String> nameMap = users.get(userType);
                                        if (nameMap != null)
                                            name = nameMap.get(userId);
                                        if (name == null || nameMap == null)
                                            name = userType + " " + userId;
                                        title_.add(name);

                                        Log.d("user->", name);
                                        Log.d("Post Date:", response.getString("PostedOn"));
                                        subTitle_.add(response.getString("PostContent"));
                                        timeStamp_.add(response.getString("PostedOn"));
                                        String postPic = response.getString(KEY_POST_PICTURE);
                                        picUrl=getProfilePicRequestUrl(userType+"s", postPic);
                                        /*imageLoader.get(getProfilePicRequestUrl(userType, postPic), new ImageLoader.ImageListener() {
                                            @Override
                                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                                dispPic = response.getBitmap();
                                                post_pic=new BitmapDrawable(getActivity().getBaseContext().getResources(),
                                                        dispPic);
                                                postPic_.add(dispPic);
                                            }

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.d("Log in", "Display pic error");

                                            }
                                        });*/


                                        for (int i = 0; i < title_.size(); i++) {
                                            title = title_.get(i);
                                            subTitle = subTitle_.get(i);
                                            TimeStamp = timeStamp_.get(i);
                                            addCard(cards, title, subTitle, true);
                                        }
                                        progress.dismiss();
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


        }
        else{
            cardData=savedInstanceState.getParcelableArrayList(cardInfoTAG);
            Log.d("savedInstanceState","called");
            for(CardInfo data:cardData) {
                addCard(cards,data.title,data.subTitle,false);
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
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

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
        Log.d("onCreateVIew","size="+cardData.size());

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount=linearLayoutManager.getChildCount();
                int totalItemCount=linearLayoutManager.getItemCount();
                int pastVisibleItems=linearLayoutManager.findFirstVisibleItemPosition();
                if(loading){
                    if((visibleItemCount+pastVisibleItems)<totalItemCount) {
                        loading = false;
                    }

                }
            }
        });
        return layout;
    }

    private String getProfilePicRequestUrl(String userType, String postPic) {
        return URL_PROFILE_PICTURE + userType + URL_PROFILE_IMAGE + postPic;
    }

    @Override
    public void onResume() {
        super.onResume();
/*
        Log.d("onResume","size="+cardData.size());
        for(CardInfo data:cardData) {
            addCard(cards,data.title,data.subTitle,false);
            mCardArrayAdapter.notifyItemInserted(mCardArrayAdapter.getItemCount());
        }*/
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
        if(picUrl!=null) {
            Log.d("url_pic",picUrl);
            thumb.setUrlResource(picUrl);
        }
        else
            thumb.setDrawableResource(R.drawable.soham);
        card.addCardThumbnail(thumb);

        /*//attach expandable view
        ViewToClickToExpand viewToClickToExpand =
                ViewToClickToExpand.builder()
                        .highlightView(false)
                        .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
        card.setViewToClickToExpand(viewToClickToExpand);*/
        if(create) {
            cardData.add(new CardInfo(title, subTitle, ""));
            Log.d("cardData","added");
        }
        cards.add(card);
    }

    public static long currentDate() {


        return System.currentTimeMillis()/1000;
    }
    public static String getTimeAgo(long time) {

        long now = currentDate();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = (now - time);
        if (diff < 60)
            return "just now";
          else if (diff < 2*60)
            return "a minute ago";
          else if (diff < 50 * 60 )
            return diff / 60 + " minutes ago";
          else if (diff < 60 * 60 )
            return "an hour ago";
          else if (diff < 86400 )
            return diff / ( 3600) + " hours ago";
          else if (diff < 48 * 86400 )
            return "yesterday";
          else if (diff < 30 * 86400 )
            return diff / (86400)+" days ago";
          else if (diff < 2*30*86400)
            return "a month ago";
          else if (diff < 12*30*86400)
            return diff / (30*24*60*60) + " months ago";
          else if (diff < 2*365*86400)
            return "a year ago";
          else
            return diff / (365*86400)  + " years ago";

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(),"Loading new posts",Toast.LENGTH_SHORT).show();
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
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



