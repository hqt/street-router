package com.fpt.router.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.BusDetail;
import com.fpt.router.library.model.bus.BusImage;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.utils.TimeUtils;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngoan on 10/21/2015.
 */
public class BusDetailOneResultFourPointAdapter extends RecyclerView.Adapter<BusDetailOneResultFourPointAdapter.BusItemViewHolder> {

    List<Result> results;
    Result result;

    public BusDetailOneResultFourPointAdapter(List<Result> results) {
        this.results = results;
    }

    @Override
    public BusItemViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bus_detail_one_result_four_point, parent, false);
        BusItemViewHolder viewHolder = new BusItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BusItemViewHolder holder, int position) {

        List<BusImage> images = new ArrayList<BusImage>();
        List<BusDetail> details = new ArrayList<>();
        result = results.get(position);
        List<INode> nodeList = result.nodeList;
        //set list image and detail bus
        if (nodeList.get(0) instanceof Path) {
            Path path = (Path) nodeList.get(0);
            images.add(new BusImage(R.drawable.ic_directions_walk_black_24dp, ""));
            images.add(new BusImage(R.drawable.ic_chevron_right_black_24dp, ""));
            String startWalking = "Từ địa chỉ " + path.stationFromName;
            details.add(new BusDetail(R.drawable.ic_directions_walk_black_24dp, startWalking, String.valueOf((int)path.distance) + " m"));
        }

        for (int i = 0; i < nodeList.size() - 1; i++) {
            if (nodeList.get(i) instanceof Segment) {
                Segment segment = (Segment) nodeList.get(i);
                images.add(new BusImage(R.drawable.ic_directions_bus_black_24dp, String.valueOf(segment.routeNo)));
                images.add(new BusImage(R.drawable.ic_chevron_right_black_24dp, ""));
                String busNameUp = "Lên trạm tại ";
                String busNameDown = "Xuống trạm tại ";
                List<Path> paths = new ArrayList<Path>();
                paths = segment.paths;
                busNameUp += paths.get(0).stationFromName;
                busNameDown += paths.get(paths.size() - 1).stationToName;
                details.add(new BusDetail(R.drawable.ic_directions_bus_black_24dp, busNameUp, String.valueOf(segment.routeNo)));
                details.add(new BusDetail(R.drawable.ic_directions_bus_black_24dp, busNameDown, String.valueOf(segment.routeNo)));
            }
        }
        if (nodeList.get(nodeList.size() - 1) instanceof Path) {
            Path path = (Path) nodeList.get(nodeList.size() - 1);
            String endWalking = "Đến địa chỉ " + path.stationToName;
            long time = TimeUtils.convertToMinute(path.time);
            images.add(new BusImage(R.drawable.ic_directions_walk_black_24dp, ""));
            details.add(new BusDetail(R.drawable.ic_directions_walk_black_24dp, endWalking, String.valueOf((int)path.distance) + " m"));
        }


        BusImageAdapter showImage = new BusImageAdapter(holder.context, images);
        holder.tvList.setAdapter(showImage);
        BusDetailContentFourPointAdapter busDetail = new BusDetailContentFourPointAdapter(holder.context, details);
        holder.lvItemsBusDetails.setAdapter(busDetail);
        setListViewHeightBasedOnItems(holder.lvItemsBusDetails);
        holder.txtDuration.setText(result.minutes + " phút");
        double totalDistance = result.totalDistance / 1000;
        totalDistance = Math.floor(totalDistance * 100) / 100;
        holder.txtDistance.setText(String.valueOf(totalDistance) + " km");
    }

    @Override
    public int getItemCount() {
        return results.size();   
    }

    public class BusItemViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TwoWayView tvList;
        ListView lvItemsBusDetails;
        TextView txtDuration;
        TextView txtDistance;


        public BusItemViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
            tvList = (TwoWayView) itemView.findViewById(R.id.lvItems);
            lvItemsBusDetails = (ListView) itemView.findViewById(R.id.lvBusDetails);
        }

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
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
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
