package com.example.priyanshu.mappr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by priyanshu-sekhar on 27/2/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    ArrayList<CommentRow> data;
    Context context;
    public CommentAdapter(Context context, ArrayList<CommentRow> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.comment_row, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        context=viewGroup.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        CommentRow current = data.get(i);
        viewHolder.userName.setText(current.userName);
        viewHolder.userComment.setText(current.userComment);

        Resources res=context.getResources();
        viewHolder.userPic.setImageResource(R.drawable.me);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView userName,userComment;
        private ImageView userPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userComment = (TextView) itemView.findViewById(R.id.userComment);
            userPic = (ImageView) itemView.findViewById(R.id.profile_picture);
        }
    }
}
