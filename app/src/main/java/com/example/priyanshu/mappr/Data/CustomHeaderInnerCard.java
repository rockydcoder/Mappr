package com.example.priyanshu.mappr.Data;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyanshu.mappr.Activities.TimelineSinglePost;
import com.example.priyanshu.mappr.Fragments.TimelineFragment;
import com.example.priyanshu.mappr.R;

import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by priyanshu-sekhar on 2/4/15.
 */
public class CustomHeaderInnerCard extends CardHeader {
    private String title;
    private String subTitle;
    private Context context;
    private String trimmedPost="";
    public static boolean single=false;
    public CustomHeaderInnerCard(Context context,String title,String subTitle) {
        super(context, R.layout.card_inner_header_layout);
        this.context=context;
        this.title=title;
        this.subTitle=subTitle;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view ) {

        if (view!=null){
            TextView t1 = (TextView) view.findViewById(R.id.title);
            if (t1!=null)
                t1.setText(Html.fromHtml(title));

            TextView t2 = (TextView) view.findViewById(R.id.subTitle);
            if(!single) {
                String seeMore = "";
                SpannableString ss = null;
                SpannableStringBuilder sb = new SpannableStringBuilder();
                if (t2 != null) {
                    int size = subTitle.length() / 40;
                    int height;
                    if (size <= 2)
                        height = 200;
                    else if (size == 3)
                        height = 300;
                    else if (size <= 6)
                        height = 500;
                    else {
                        trimmedPost = subTitle.substring(0, 170);
                        //seeMore = "<font color='#0D47A1'>...See More</font>";
                        seeMore="...See More";
                        sb.append(trimmedPost);
                        sb.append(seeMore);
                        ss = new SpannableString(trimmedPost);
                        ClickableSpan clickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                /*TimelineFragment.singleUrl=TimelineFragment.picUrl;
                                Log.d("url", TimelineFragment.picUrl);*/
                                Intent intent = new Intent(context, TimelineSinglePost.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("title", title);
                                intent.putExtra("subTitle", subTitle);
                                context.startActivity(intent);
                            }
                        };
                        sb.setSpan(clickableSpan, sb.length() - seeMore.length(), sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        height = 500;

                    }
                    if (size > 6) {
                    /*t2.setText(Html.fromHtml(trimmedPost.substring(0,230)
                            +"<font color='#0D47A1'>"+ss+"</font>"));*/
                        Spanned htmlText=Html.fromHtml(sb.toString());

                        t2.setText(sb);

                    } else
                        t2.setText(subTitle);
                    t2.setMovementMethod(LinkMovementMethod.getInstance());
                    view.getLayoutParams().height = height;
                }
            }
            else
                t2.setText(subTitle);

        }
    }
}