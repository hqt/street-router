package com.fpt.router.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fpt.router.R;

/**
 * Created by asus on 10/6/2015.
 */
public class Optional extends Dialog implements View.OnClickListener {

    public Activity optional;
    public Dialog dialog;
    public Button yes, no;
    public TextView waypoint_1;
    public TextView waypoint_2;
    public static String waypoint_text_1;
    public static String waypoint_text_2;
    public Optional(Activity optional) {
        super(optional);
        this.optional = optional;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customer_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        waypoint_1 = (TextView) findViewById(R.id.fromId);
        waypoint_2 = (TextView) findViewById(R.id.toId);
        //edit text 1
        waypoint_1.setOnClickListener(this);
        waypoint_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fromId:
                Intent intent_3 = new Intent(optional, SearchActivity.class);
                intent_3.putExtra("number", 1);
                optional.startActivityForResult(intent_3, 3);// Activity is started with requestCode 1
            case R.id.toId:
                Intent intent_4 = new Intent(optional, SearchActivity.class);
                intent_4.putExtra("number", 2);
                optional.startActivityForResult(intent_4, 4);// Activity is started with requestCode 2
            case R.id.btn_yes:
                optional.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
