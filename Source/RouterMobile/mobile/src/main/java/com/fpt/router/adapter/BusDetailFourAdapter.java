package com.fpt.router.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoan on 10/23/2015.
 */
public class BusDetailFourAdapter extends ArrayAdapter<Result> {

    List<Result> results;
    private Context mContext;
    private int resource;
    Result result;
    List<INode> iNodeList = new ArrayList<INode>();

    public BusDetailFourAdapter(Context context, int resource, List<Result> results){
        super(context,resource,results);
        this.mContext = context;
        this.resource = resource;
        this.results = results;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        result = results.get(position);
        ListView listView = (ListView) convertView.findViewById(R.id.lvItems);
        /*TextView duration = (TextView) convertView.findViewById(R.id.txtDurationTime);
        TextView distance = (TextView) convertView.findViewById(R.id.txtDistance);

        duration.setText(result.minutes+ " phút");
        double totalDistance = result.totalDistance/1000;
        totalDistance = Math.floor(totalDistance*100)/100;
        distance.setText(String.valueOf(totalDistance) + " km");*/
        iNodeList = result.nodeList;
        BusDetailAdapter adapterItem = new BusDetailAdapter(mContext, R.layout.adapter_show_detail_bus_steps,iNodeList);
        listView.setAdapter(adapterItem);
        setListViewHeightBasedOnItems(listView);
        return convertView;
    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems;itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + 100;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

}
