package com.example.priyanshu.mappr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.priyanshu.mappr.Fragments.TimelineFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by priyanshu-sekhar on 27/2/15.
 */
public class CommentPage extends Activity {
    RecyclerView mRecyclerView;
    CommentAdapter commentAdapter;
    CommentRow[] data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Log.d("onCreate",intent.getIntExtra(TimelineFragment.noOfCommentsTag,0)+"");
        data=new CommentRow[intent.getIntExtra(TimelineFragment.noOfCommentsTag,0)];
        int num=0;
        for(int iconId:intent.getIntegerArrayListExtra(TimelineFragment.iconIdTag)){
            Log.d("num",num+"");
            data[num]=new CommentRow();
            data[num++].iconId=iconId;
        }
        num=0;
        for(String userName:intent.getStringArrayListExtra(TimelineFragment.userNameTag)){
            data[num++].userName=userName;

        }
        num=0;
        for(String userComment:intent.getStringArrayListExtra(TimelineFragment.userCommentTag)){
            data[num++].userComment=userComment;

        }

        setContentView(R.layout.comment_page);
        mRecyclerView = (RecyclerView)findViewById(R.id.comment_list);
        commentAdapter = new CommentAdapter(this, new ArrayList<>(Arrays.asList(data)));
        mRecyclerView.setAdapter(commentAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onStart() {
        Log.d("Group", "Start");
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
