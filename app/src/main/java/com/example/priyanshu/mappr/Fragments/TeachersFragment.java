package com.example.priyanshu.mappr.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanshu.mappr.Adapters.CustomAdapter;
import com.example.priyanshu.mappr.Data.SingleRowData;
import com.example.priyanshu.mappr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocky on 22/2/15.
 */
public class TeachersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CustomAdapter customAdapter;
    private static boolean check = true;
    private static List<SingleRowData> data = new ArrayList<>();
    private static ArrayList<String> teachersNames = new ArrayList<>();

    public TeachersFragment(ArrayList<String> names) {
        teachersNames = names;
    }

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

        if(check) {
            check = false;

            for(int i = 0; i < teachersNames.size(); i++) {
                SingleRowData current = new SingleRowData();
                current.setIconId(R.drawable.user);
                current.setText(teachersNames.get(i));
                data.add(current);
            }
        }


        return data;

    }
}


