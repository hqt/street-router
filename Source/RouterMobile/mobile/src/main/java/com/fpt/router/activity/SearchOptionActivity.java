package com.fpt.router.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.motorbike.AutocompleteObject;

/**
 * Created by asus on 10/6/2015.
 */
public class SearchOptionActivity extends Activity {

    private Button acceptButton, cancelButton;
    private ImageButton swapLocationButton;
    private TextView fromTextView;
    private TextView toTextView;
    private CheckBox optimizeCheckbox;
    private TextView walkingDistanceTextView;
    private Spinner transferNumberSpinner;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_option);
        acceptButton = (Button) findViewById(R.id.btn_yes);
        cancelButton = (Button) findViewById(R.id.btn_no);
        fromTextView = (TextView) findViewById(R.id.fromId);
        toTextView = (TextView) findViewById(R.id.toId);
        walkingDistanceTextView = (TextView) findViewById(R.id.walking_distance_txt);
        transferNumberSpinner = (Spinner) findViewById(R.id.number_spinner);
        optimizeCheckbox = (CheckBox) findViewById(R.id.checkBox);
        swapLocationButton = (ImageButton) findViewById(R.id.swap_location_btn);

        optimizeCheckbox.setChecked(SearchRouteActivity.optimize);

        if (SearchRouteActivity.listLocation.size() > 2) {
            fromTextView.setText(SearchRouteActivity.listLocation.get(2).getName());
        }

        if (SearchRouteActivity.listLocation.size() > 3) {
            toTextView.setText(SearchRouteActivity.listLocation.get(3).getName());
        }

        fromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_1 = new Intent(SearchOptionActivity.this, AutoCompleteSearchActivity.class);
                intent_1.putExtra("number", 3);
                intent_1.putExtra("message", fromTextView.getText());
                startActivityForResult(intent_1, 3);// Activity is started with requestCode 1
            }
        });

        toTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2 = new Intent(SearchOptionActivity.this, AutoCompleteSearchActivity.class);
                intent_2.putExtra("number", 4);
                intent_2.putExtra("message", toTextView.getText());
                startActivityForResult(intent_2, 4);// Activity is started with requestCode 2
            }
        });


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchRouteActivity.class);
                boolean isChecked = optimizeCheckbox.isChecked();
                intent.putExtra("optimize", isChecked);
                setResult(3, intent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
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
                String name = data.getStringExtra("NAME");
                String place_id = "";
                if(data.getStringExtra("PLACE_ID") != null) {
                    place_id = data.getStringExtra("PLACE_ID");
                }
<<<<<<< HEAD
                if ((!"".equals(name)) && (name != null)) {
                    txtfrom.setText(name);
=======
                if (!"".equals(name)) {
                    fromTextView.setText(name);
>>>>>>> f7dd8a38543f8f4869e2e44db98e796b9394a024
                    if (SearchRouteActivity.listLocation.size() > 2) {
                        SearchRouteActivity.listLocation.set(2, new AutocompleteObject(name, place_id));
                    } else {
                        for(int i = SearchRouteActivity.listLocation.size(); i < requestCode - 1; i++) {
                            SearchRouteActivity.listLocation.add(new AutocompleteObject("", ""));
                        }
                        SearchRouteActivity.listLocation.add(new AutocompleteObject(name, place_id));
                    }
                } else {
                    txtfrom.setText("");
                    SearchRouteActivity.listLocation.remove(requestCode - 1);
                }

            }
            if (requestCode == 4) {
                String name = data.getStringExtra("NAME");
                String place_id = "";
                if(data.getStringExtra("PLACE_ID") != null) {
                    place_id = data.getStringExtra("PLACE_ID");
                }
                if ((!"".equals(name)) && (name != null)) {
                    txtto.setText(name);
                    if (SearchRouteActivity.listLocation.size() > 3) {
                        SearchRouteActivity.listLocation.set(3, new AutocompleteObject(name, place_id));
                    } else {
                        for(int i = SearchRouteActivity.listLocation.size(); i < requestCode - 1; i++) {
                            SearchRouteActivity.listLocation.add(new AutocompleteObject("", ""));
                        }
                        SearchRouteActivity.listLocation.add(new AutocompleteObject(name, place_id));
                    }
                } else {
                    txtto.setText("");
                    SearchRouteActivity.listLocation.remove(requestCode-1);
                }

            }
        }

    }
}
