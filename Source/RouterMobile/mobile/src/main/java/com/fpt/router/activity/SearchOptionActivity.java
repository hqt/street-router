package com.fpt.router.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fpt.router.R;

/**
 * Created by asus on 10/6/2015.
 */
public class SearchOptionActivity extends Activity {

    private Button yes, no;
    private TextView txtfrom;
    private TextView txtto;
    private CheckBox checkBox;
    private Intent intent;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customer_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        txtfrom = (TextView) findViewById(R.id.fromId);
        txtto = (TextView) findViewById(R.id.toId);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(SearchRouteActivity.optimize);
        if (SearchRouteActivity.listLocation.size() > 2) {
            txtfrom.setText(SearchRouteActivity.listLocation.get(2));
        }
        if (SearchRouteActivity.listLocation.size() > 3) {
            txtto.setText(SearchRouteActivity.listLocation.get(3));
        }

        //get position disable radio button if postion is motorbike
        intent = getIntent();
        int positionTab = intent.getIntExtra("postionTab",1);
        if(positionTab == 1){
            for (int i=0; i<radioGroup.getChildCount();i++){
                ((RadioButton)radioGroup.getChildAt(i)).setEnabled(false);
            }
        }

        txtfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_1 = new Intent(SearchOptionActivity.this, AutoCompleteSearchActivity.class);
                intent_1.putExtra("number", 3);
                intent_1.putExtra("message",txtfrom.getText());
                startActivityForResult(intent_1, 3);// Activity is started with requestCode 1
            }
        });

        txtto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2 = new Intent(SearchOptionActivity.this, AutoCompleteSearchActivity.class);
                intent_2.putExtra("number", 4);
                intent_2.putExtra("message",txtto.getText());
                startActivityForResult(intent_2, 4);// Activity is started with requestCode 2
            }
        });


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchRouteActivity.class);
                intent.putExtra("optimize", true);
                if (!checkBox.isChecked()) {
                    intent.putExtra("optimize", false);
                }
                setResult(3, intent);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchRouteActivity.class);
                setResult(4, intent);
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if (requestCode == 3) {
                String message = data.getStringExtra("MESSAGE");
                if (!"".equals(message)) {
                    txtfrom.setText(message);
                    if (SearchRouteActivity.listLocation.size() > 2) {
                        SearchRouteActivity.listLocation.set(2, message);
                    } else {
                        SearchRouteActivity.listLocation.add(message);
                    }
                }

            }
            if (requestCode == 4) {
                String message = data.getStringExtra("MESSAGE");
                if (!"".equals(message)) {
                    txtto.setText(message);
                    if (SearchRouteActivity.listLocation.size() > 3) {
                        SearchRouteActivity.listLocation.set(3, message);
                    } else {
                        SearchRouteActivity.listLocation.add(message);
                    }
                }

            }
        }

    }
}
