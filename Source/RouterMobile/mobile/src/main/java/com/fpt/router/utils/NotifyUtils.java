package com.fpt.router.utils;

import android.content.Context;
import android.widget.Toast;

import com.fpt.router.library.config.AppConstants;
import com.fpt.router.library.utils.NotificationUtils;
import com.fpt.router.library.utils.SoundUtils;

/**
 * Created by Nguyen Trung Nam on 11/20/2015.
 */
public class NotifyUtils {
    public static void notifyUnderRequest(int event, Context context, boolean isPlaySound) {
        if(event == AppConstants.SAI_DUONG) {
            Toast.makeText(context, "Bạn đang đi sai đường", Toast.LENGTH_SHORT).show();
            if (isPlaySound) {
                SoundUtils.playSoundFromAsset(context, event + ".wav");
            }
            NotificationUtils.run(context, "Sai đường", "Bạn đang đi sai đường",
                    "Thông tin chi tiết", "Xin tham khảo bản đồ để biết thêm");
        }
        if(event == AppConstants.DI_THANG) {
            Toast.makeText(context, "Tiếp tục đi thẳng", Toast.LENGTH_SHORT).show();
            if (isPlaySound) {
                SoundUtils.playSoundFromAsset(context, event + ".wav");
            }
            NotificationUtils.run(context, "Đi thẳng", "Tiếp tục đi thẳng",
                    "Thông tin chi tiết", "Xin tiếp tục đi thẳng trên con đường hiện tại");
        }
    }
}
