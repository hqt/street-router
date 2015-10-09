package com.fpt.router.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fpt.router.R;

/**
 * Created by asus on 10/6/2015.
 */
public class Optional extends Activity {

    private Button yes, no;
    private TextView txtfrom;
    private TextView txtto;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customer_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        txtfrom = (TextView) findViewById(R.id.fromId);
        txtto = (TextView) findViewById(R.id.toId);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        txtfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_1 = new Intent(Optional.this, SearchActivity.class);
                intent_1.putExtra("number", 3);
                startActivityForResult(intent_1, 3);// Activity is started with requestCode 1
            }
        });

        txtto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2 = new Intent(Optional.this, SearchActivity.class);
                intent_2.putExtra("number", 4);
                startActivityForResult(intent_2, 4);// Activity is started with requestCode 2
            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Optional.this, MainActivity.class);
                intent.putExtra("optimize", true);
                if(!checkBox.isChecked()) {
                    intent.putExtra("optimize", false);
                }
                setResult(3, intent);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Optional.this, MainActivity.class);
                setResult(4, intent);
               finish();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3) {
            String message = data.getStringExtra("MESSAGE");
            txtfrom.setText(message);
            if(MainActivity.listLocation.size() > 2) {
                MainActivity.listLocation.set(2, message);
            } else {
                MainActivity.listLocation.add(message);
            }
        }
        if(requestCode == 4){
            String message = data.getStringExtra("MESSAGE");
            txtto.setText(message);
            if(MainActivity.listLocation.size() > 3) {
                MainActivity.listLocation.set(3, message);
            } else {
                MainActivity.listLocation.add(message);
            }
        }
    }
}
