package com.fpt.router.library.utils;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.fpt.router.library.model.bus.INode;
import com.fpt.router.library.model.bus.Path;
import com.fpt.router.library.model.bus.Segment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Huynh Quang Thao on 10/13/15.
 */
public class JSONUtils {

    public static Gson buildGson() {
        // for polymorphism
        final RuntimeTypeAdapterFactory<INode> typeFactory = RuntimeTypeAdapterFactory
                .of(INode.class, "Node")
                .registerSubtype(Path.class)
                .registerSubtype(Segment.class);

        // for joda time library
        GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(typeFactory).setPrettyPrinting();
        builder = Converters.registerDuration(builder);
        builder = builder.registerTypeAdapter(PeriodConverter.PERIOD_TYPE, new PeriodConverter());

        return builder.create();
    }
}
