package com.example.priyanshu.mappr.Fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.priyanshu.mappr.Adapters.CustomAdapter;
import com.example.priyanshu.mappr.Adapters.MapprDatabaseAdapter;
import com.example.priyanshu.mappr.Data.SingleRowData;
import com.example.priyanshu.mappr.R;
import com.example.priyanshu.mappr.network.VolleySingleton;
import com.software.shell.fab.ActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.priyanshu.mappr.Extras.Keys.LogIn.KEY_GROUPS_TITLE;
import static com.example.priyanshu.mappr.Extras.URLEndPoints.*;


/**
 * Created by rocky on 22/2/15.
 */
public class GroupsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CustomAdapter customAdapter;
    private ActionButton actionButton;
    private static List<SingleRowData> data = new ArrayList<>();
    private static boolean check = true;
//    private static MapprDatabaseAdapter mapprDatabaseAdapter;
    public static ArrayList<String> groupsTitles = new ArrayList<>();

    public GroupsFragment(ArrayList<String> titles) {
        groupsTitles = titles;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.layout_groups, container, false);
//        mapprDatabaseAdapter = new MapprDatabaseAdapter(container.getContext());
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.groups_list);
        customAdapter = new CustomAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(customAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        /**
         * Set up for Floating Action Button to add groups
         */
        actionButton = (ActionButton) layout.findViewById(R.id.group_action_button);
        actionButton.setButtonColor(getResources().getColor(R.color.primary));
        actionButton.setButtonColorPressed(getResources().getColor(R.color.primary_dark));
        actionButton.setImageResource(R.drawable.ic_action_new);
        actionButton.show();



        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(container.getContext());
                Button cancel, add;
                final EditText etTitle;

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_group);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                cancel = (Button) dialog.findViewById(R.id.cancel);
                add = (Button) dialog.findViewById(R.id.add);
                etTitle = (EditText) dialog.findViewById(R.id.editText);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // when cancel button is clicked
                        dialog.dismiss();
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // when add button is clicked
                        String title = etTitle.getText().toString();

                        if(title != null) {
                            addData(title);
                            customAdapter.notifyItemInserted(customAdapter.getItemCount());
                            dialog.dismiss();
                        }

//                        if(title != null) {
//                            addData(title);
//                            long id = mapprDatabaseAdapter.insertGroup(title);
//                            Toast.makeText(getActivity(), id + "", Toast.LENGTH_LONG).show();
//                            customAdapter.notifyItemInserted(customAdapter.getItemCount());
//                            dialog.dismiss();
//                        }

                    }
                });

                dialog.show();
            }
        });
        return layout;
    }


    public static List<SingleRowData> getData() {
        if(check)
        {
            check = false;
            for(int i = 0; i < groupsTitles.size(); i++) {
                SingleRowData current = new SingleRowData();
                current.setIconId(R.drawable.user);
                current.setText(groupsTitles.get(i));
                data.add(current);
            }
        }
        return data;

    }

    public static void addData(String title) {
//        SingleRowData current = new SingleRowData();
//        current.setIconId(R.drawable.user);
//        current.setText(title);
//        data.add(current);
    }
}


