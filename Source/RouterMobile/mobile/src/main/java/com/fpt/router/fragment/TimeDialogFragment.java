package com.fpt.router.fragment;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by ngoan on 10/29/2015.
 */
public class TimeDialogFragment extends DialogFragment implements OnTimeSetListener
{

    TimePickerDialog time;
    Context context;

    public TimeDialogFragment(){}
    public TimeDialogFragment(Context context)
    {
        this.context = context;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int hur = c.get( Calendar.HOUR );
        int min = c.get( Calendar.MINUTE );
        time = new TimePickerDialog(getActivity(), this, hur, min, false);
        time.setButton(DialogInterface.BUTTON_NEGATIVE, "Hủy", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(which == DialogInterface.BUTTON_NEGATIVE){
                    dialog.cancel();
                }


            }
        });
        time.setCancelable(true);
        return time;
    }

    @Override
    public void onTimeSet(TimePicker view, int hur, int min)
    {
        Log.i("ABC","000000000000");
        Toast.makeText(context, hur+" : "+min, Toast.LENGTH_SHORT).show();

    }

}