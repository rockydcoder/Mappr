package com.example.priyanshu.mappr;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocky on 22/2/15.
 */
public class StudentsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CustomAdapter customAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.list_fragment_default, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.default_list);
        customAdapter = new CustomAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(customAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public static List<SingleRowData> getData() {
        List<SingleRowData> data = new ArrayList<>();
        String[] titles = {"Student 1", "Student 2", "Student 3", "Student 4", "Student 5"};
        for(int i = 0; i < titles.length; i++) {
            SingleRowData current = new SingleRowData();
            current.setIconId(R.drawable.user);
            current.setText(titles[i]);
            data.add(current);
        }

        return data;

    }
}


