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

        DetailRoute route_6 = new DetailRoute();
        route_6.setTitle("Đi hướng tây lên cầu vượt sóng thần");
        route_6.setSubTitle("Tiếp tục đi theo QL 1");
        route_6.setImage(R.drawable.ic_image);
        routes.add(route_6);

        DetailRoute route_7 = new DetailRoute();
        route_7.setTitle("Rẽ phải để vào Đào Trinh Nhất");
        route_7.setSubTitle("Băng qua quảng cáo Việt");
        route_7.setImage(R.drawable.ic_done);
        routes.add(route_7);

        DetailRoute route_8 = new DetailRoute();
        route_8.setTitle("Rẽ phải để vào Đào Trinh Nhất");
        route_8.setSubTitle("Băng qua quảng cáo Việt");
        route_8.setImage(R.drawable.ic_done);
        routes.add(route_8);

        DetailRoute route_9 = new DetailRoute();
        route_9.setTitle("Đi hướng tây lên cầu vượt sóng thần");
        route_9.setSubTitle("Tiếp tục đi theo QL 1");
        route_9.setImage(R.drawable.ic_emoticon);
        routes.add(route_9);

        DetailRoute route_10 = new DetailRoute();
        route_10.setTitle("Rẽ phải để vào Đào Trinh Nhất");
        route_10.setSubTitle("Băng qua quảng cáo Việt");
        route_10.setImage(R.drawable.ic_done);
        routes.add(route_10);
    }

    public List<DetailRoute> getRoutes(){
        return routes;
    }
}
