package com.fpt.router.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpt.router.R;
import com.fpt.router.adapter.DesignDemoRecyclerAdapter;

/**
 * Created by USER on 9/27/2015.
 */
public class DesignDemoFragment extends Fragment {
    private static final String TAB_POSITION = "tab_position";
    public DesignDemoFragment(){

    }
    public DesignDemoFragment newInstance(int tabPosition){

        DesignDemoFragment fragment = new DesignDemoFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION,tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // list view with RecyclerView
        Bundle args = getArguments();
        int tabPosition = args.getInt(TAB_POSITION);


        int [] prgmImages= {R.drawable.ic_history_white_24dp, R.drawable.ic_history_white_24dp,R.drawable.ic_history_white_24dp, R.drawable.ic_history_white_24dp,
                R.drawable.ic_history_white_24dp,R.drawable.ic_history_white_24dp,R.drawable.ic_history_white_24dp,
                R.drawable.ic_history_white_24dp,R.drawable.ic_history_white_24dp};
        String [] titleList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
       String [] contentList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
        /*final ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            items.add("Tab #" + tabPosition + " item #" + i);
        }*/

        View v =  inflater.inflate(R.layout.fragment_list_view, container, false);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new DesignDemoRecyclerAdapter(titleList, contentList, prgmImages));
        return v;

    }
}
