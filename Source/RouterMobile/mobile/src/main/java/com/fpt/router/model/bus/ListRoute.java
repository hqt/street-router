package com.fpt.router.model.bus;

import com.fpt.router.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 10/5/2015.
 */
public class ListRoute {
    private List<DetailRoute> routes;
    public ListRoute(){
        routes = new ArrayList<DetailRoute>();
        DetailRoute route_1 = new DetailRoute();
        route_1.setTitle("Đi hướng tây lên cầu vượt sóng thần");
        route_1.setSubTitle("Tiếp tục đi theo QL 1");
        route_1.setImage(R.drawable.ic_image);
        routes.add(route_1);

        DetailRoute route_2 = new DetailRoute();
        route_2.setTitle("Rẽ phải để vào Đào Trinh Nhất");
        route_2.setSubTitle("Băng qua quảng cáo Việt");
        route_2.setImage(R.drawable.ic_done);
        routes.add(route_2);

        DetailRoute route_3 = new DetailRoute();
        route_3.setTitle("Rẽ phải để vào Đào Trinh Nhất");
        route_3.setSubTitle("Băng qua quảng cáo Việt");
        route_3.setImage(R.drawable.ic_done);
        routes.add(route_3);

        DetailRoute route_4 = new DetailRoute();
        route_4.setTitle("Đi hướng tây lên cầu vượt sóng thần");
        route_4.setSubTitle("Tiếp tục đi theo QL 1");
        route_4.setImage(R.drawable.ic_emoticon);
        routes.add(route_4);

        DetailRoute route_5 = new DetailRoute();
        route_5.setTitle("Rẽ phải để vào Đào Trinh Nhất");
        route_5.setSubTitle("Băng qua quảng cáo Việt");
        route_5.setImage(R.drawable.ic_done);
        routes.add(route_5);
    }

    public List<DetailRoute> getRoutes(){
        return routes;
    }
}
