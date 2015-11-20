package com.fpt.router.utils;

import android.content.Context;
import android.widget.Toast;

import com.fpt.router.library.utils.NotificationUtils;
import com.fpt.router.library.utils.SoundUtils;

/**
 * Created by Nguyen Trung Nam on 11/20/2015.
 */
public class NotifyUtils {
    public static void notifyUnderRequest(String event, Context context, boolean isPlaySound) {
        if(event.equals("saiduong")) {
            Toast.makeText(context, "Bạn đang đi sai đường", Toast.LENGTH_SHORT).show();
            if (isPlaySound) {
                SoundUtils.playSoundFromAsset(context, event + ".wav");
            }
            NotificationUtils.run(context, "Sai đường", "Bạn đang đi sai đường",
                    "Thông tin chi tiết", "Xin tham khảo bản đồ để biết thêm");
        }
        if(event.equals("dithang")) {
            Toast.makeText(context, "Tiếp tục đi thẳng", Toast.LENGTH_SHORT).show();
            if (isPlaySound) {
                SoundUtils.playSoundFromAsset(context, event + ".wav");
            }
            NotificationUtils.run(context, "Đi thẳng", "Tiếp tục đi thẳng",
                    "Thông tin chi tiết", "Xin tiếp tục đi thẳng trên con đường hiện tại");
        }
        if(event.equals("retrai")) {
            Toast.makeText(context, "Rẽ trái", Toast.LENGTH_SHORT).show();
            if (isPlaySound) {
                SoundUtils.playSoundFromAsset(context, event + ".wav");
            }
            NotificationUtils.run(context, "Rẽ trái", "Rẽ trái tại ngã đường này",
                    "Thông tin chi tiết", "Xin rẽ trái để đi tiếp lộ trình");
        }
        if(event.equals("rephai")) {
            Toast.makeText(context, "Rẽ phải", Toast.LENGTH_SHORT).show();
            if (isPlaySound) {
                SoundUtils.playSoundFromAsset(context, event + ".wav");
            }
            NotificationUtils.run(context, "Rẽ phải", "Rẽ phải tại ngã đường này",
                    "Thông tin chi tiết", "Xin rẽ phải để đi tiếp lộ trình");
        }
    }
}
