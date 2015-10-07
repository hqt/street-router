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
public class Optional extends Activity {

    private Button yes, no;
    private TextView txtfrom;
    private TextView txtto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customer_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        txtfrom = (TextView) findViewById(R.id.fromId);
        txtto = (TextView) findViewById(R.id.toId);


        txtfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_3 = new Intent(Optional.this, SearchActivity.class);
                intent_3.putExtra("number", 1);
                startActivityForResult(intent_3, 3);// Activity is started with requestCode 1
            }
        });

        txtto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_4 = new Intent(Optional.this, SearchActivity.class);
                intent_4.putExtra("number", 2);
                startActivityForResult(intent_4, 4);// Activity is started with requestCode 2
            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Optional.this, MainActivity.class);
                startActivity(intent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        }
        if(requestCode == 4){
            String message = data.getStringExtra("MESSAGE");
            txtto.setText(message);
        }
    }
}
