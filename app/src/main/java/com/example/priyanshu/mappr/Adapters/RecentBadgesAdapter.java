package com.example.priyanshu.mappr.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.priyanshu.mappr.Data.CommentRow;
import com.example.priyanshu.mappr.R;

import java.util.ArrayList;

/**
 * Created by priyanshu-sekhar on 5/3/15.
 */
public class RecentBadgesAdapter extends RecyclerView.Adapter<RecentBadgesAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    ArrayList<CommentRow> data;
    Context context;
    public RecentBadgesAdapter(Context context, ArrayList<CommentRow> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.recent_badges_row, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        context=viewGroup.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        CommentRow current = data.get(i);
        viewHolder.userName.setText(Html.fromHtml(current.userName));
        viewHolder.userComment.setText(Html.fromHtml(current.userComment));

        Resources res=context.getResources();
        viewHolder.userPic.setImageResource(R.drawable.soham);
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
