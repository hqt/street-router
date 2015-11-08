package com.fpt.router.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.activity.SearchDetailActivity;
import com.fpt.router.library.model.bus.BusDetail;
import com.fpt.router.library.model.bus.BusImage;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Result;
import com.fpt.router.library.model.bus.Segment;
import com.fpt.router.library.utils.TimeUtils;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by asus on 10/13/2015.
 */
public class BusTwoPointAdapter extends RecyclerView.Adapter<BusTwoPointAdapter.BusViewHoder> {

    List<Result> results;
    Result result;

    public BusTwoPointAdapter(List<Result> results){
        this.results = results;
    }

    @Override
    public BusViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bus_two_point,parent,false);
        BusViewHoder busViewHoder = new BusViewHoder(v);
        return busViewHoder;
    }

    @Override
    public void onBindViewHolder(final BusViewHoder holder, int position) {
        List<BusImage> images = new ArrayList<BusImage>();
        List<BusDetail> details = new ArrayList<>();
        result = results.get(position);
        List<String> numberBusFinal = new ArrayList<>();
        List<INode> nodeList = result.nodeList;
        //set list image
        if(nodeList.get(0) instanceof Path){
            Path path = (Path) nodeList.get(0);
            images.add(new BusImage(R.drawable.ic_directions_walk_black_24dp,""));
            images.add(new BusImage(R.drawable.ic_chevron_right_black_24dp,""));
            String startWalking = "Từ "+path.stationFromName;
            long time = TimeUtils.convertPeriodToMinute(path.time);
            details.add(new BusDetail(R.drawable.ic_directions_walk_black_24dp,startWalking,String.valueOf(time)+"p"));
            holder.startLocation.setText(startWalking);
        }
        for (int i= 0 ;i<nodeList.size() -1;i++){
            if(nodeList.get(i) instanceof Segment){
                Segment segment = (Segment) nodeList.get(i);
                images.add(new BusImage(R.drawable.ic_directions_bus_black_24dp,String.valueOf(segment.routeNo)));
                images.add(new BusImage(R.drawable.ic_chevron_right_black_24dp,""));
                String busNameUp = "Lên trạm tại ";
                String busNameDown = "Xuống trạm tại ";
                List<Path> paths = new ArrayList<Path>();
                paths = segment.paths;
                busNameUp += paths.get(0).stationFromName;
                busNameDown += paths.get(paths.size()-1).stationToName;
                details.add(new BusDetail(R.drawable.ic_directions_bus_black_24dp,busNameUp,String.valueOf(segment.routeNo)));
                details.add(new BusDetail(R.drawable.ic_directions_bus_black_24dp,busNameDown,""));
                numberBusFinal.add(String.valueOf(segment.routeNo));
            }
        }
        if(nodeList.get(nodeList.size() - 1) instanceof Path){
            Path path = (Path) nodeList.get(nodeList.size() - 1);
            String endWalking = "Đến "+path.stationToName;
            long time = TimeUtils.convertPeriodToMinute(path.time);
            images.add(new BusImage(R.drawable.ic_directions_walk_black_24dp,""));
            details.add(new BusDetail(R.drawable.ic_directions_walk_black_24dp,endWalking,String.valueOf(time)+"p"));
            holder.endLocation.setText(endWalking);
        }

        if(position == 0){
            holder.txtTitle.setText("Tuyến đường được đề nghị ");
        }else if(position == 1){
            holder.txtTitle.setText("Thêm kết quả cho đi xe bus ");
        }else{
            holder.txtTitle.setVisibility(View.GONE);
        }

        BusImageAdapter showImage = new BusImageAdapter(holder.context,images);
        holder.tvList.setAdapter(showImage);

        BusDetailContentFourPointAdapter busDetail = new BusDetailContentFourPointAdapter(holder.context,details);
        holder.listView.setAdapter(busDetail);
        setListViewHeightBasedOnItems(holder.listView);

        holder.txtDuration.setText(result.minutes+" phút");
        double totalDistance = result.totalDistance/1000;
        totalDistance = Math.floor(totalDistance*100)/100;
        holder.txtDistance.setText(String.valueOf(totalDistance)+" km");
        String[]  nBusFinal = numberBusFinal.toArray(new String[numberBusFinal.size()]);
        holder.txtBusNumberEnd.setText("Đi "+Arrays.toString(nBusFinal));
        holder.viewDetail.setVisibility(View.GONE);
        holder.btnShow.setVisibility(View.VISIBLE);
        holder.btnHide.setVisibility(View.GONE);
        holder.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewDetail.setVisibility(View.VISIBLE);
                holder.btnShow.setVisibility(View.GONE);
                holder.btnHide.setVisibility(View.VISIBLE);
            }
        });
        holder.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.viewDetail.setVisibility(View.GONE);
                holder.btnShow.setVisibility(View.VISIBLE);
                holder.btnHide.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class BusViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final  Context context;
        TwoWayView tvList;
        TextView txtDuration;
        TextView txtDistance;
        TextView txtTitle;
        LinearLayout viewDetail;
        ListView listView;
        ImageButton btnShow;
        ImageButton btnHide;
        TextView startLocation;
        TextView endLocation;
        TextView txtBusNumberEnd;

        public BusViewHoder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();

            txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            txtDuration = (TextView) itemView.findViewById(R.id.txtDurationTime);
            tvList = (TwoWayView) itemView.findViewById(R.id.lvItems);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            viewDetail = (LinearLayout) itemView.findViewById(R.id.viewDetail);
            btnShow = (ImageButton) itemView.findViewById(R.id.btnShowDetail);
            btnHide = (ImageButton) itemView.findViewById(R.id.btnHide);
            listView = (ListView) itemView.findViewById(R.id.lvBusDetails);
            startLocation = (TextView) itemView.findViewById(R.id.startLocation);
            endLocation = (TextView) itemView.findViewById(R.id.endLocation);
            txtBusNumberEnd = (TextView) itemView.findViewById(R.id.txtBusNumberEnd);

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SearchDetailActivity.class);
            Bundle bundle = new Bundle();
            Result result = getResult(getPosition());
            bundle.putSerializable("result", result);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);

        }
    }

    public Result getResult(int position){
        return results.get(position);
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
