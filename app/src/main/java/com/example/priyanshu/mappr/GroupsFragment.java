package com.example.priyanshu.mappr;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocky on 22/2/15.
 */
public class GroupsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CustomAdapter customAdapter;
//    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.list_fragment_default, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.groups_list);
        customAdapter = new CustomAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(customAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    @Override
    public void onStart() {
        Log.d("Group","Start");
        super.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("Tag", "Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Tag", "Pause");
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


