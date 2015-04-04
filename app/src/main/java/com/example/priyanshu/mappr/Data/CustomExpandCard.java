package com.example.priyanshu.mappr.Data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.priyanshu.mappr.Activities.CommentPage;
import com.example.priyanshu.mappr.Adapters.CustomAdapter;
import com.example.priyanshu.mappr.Fragments.TimelineFragment;
import com.example.priyanshu.mappr.R;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.CardExpand;

/**
 * Created by priyanshu-sekhar on 2/4/15.
 */
public class CustomExpandCard extends CardExpand {
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
                Log.d(TimelineFragment.noOfCommentsTag, noOfComments + "");
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
