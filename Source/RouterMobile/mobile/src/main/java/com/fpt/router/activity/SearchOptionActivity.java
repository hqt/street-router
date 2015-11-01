package com.fpt.router.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.config.AppConstants.SearchField;

import static com.fpt.router.activity.SearchRouteActivity.*;

/**
 * Created by asus on 10/6/2015.
 */
public class SearchOptionActivity extends Activity {

    private Button acceptButton, cancelButton;
    private ImageButton swapLocationButton;
    private TextView fromTextView;
    private TextView toTextView;
    private CheckBox optimizeCheckbox;
    private EditText walkingDistanceEditText;
    private Spinner transferNumberSpinner;
    Intent intent;
    private LinearLayout disableRelativeLayout;
    private ImageButton imgDeleteWaypoint_1;
    private ImageButton imgDeleteWaypoint_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_option);
        acceptButton = (Button) findViewById(R.id.btn_yes);
        cancelButton = (Button) findViewById(R.id.btn_no);
        fromTextView = (TextView) findViewById(R.id.fromId);
        toTextView = (TextView) findViewById(R.id.toId);
        walkingDistanceEditText = (EditText) findViewById(R.id.walking_distance_txt);
        transferNumberSpinner = (Spinner) findViewById(R.id.number_spinner);
        optimizeCheckbox = (CheckBox) findViewById(R.id.checkBox);
        swapLocationButton = (ImageButton) findViewById(R.id.swap_location_btn);
        disableRelativeLayout = (LinearLayout) findViewById(R.id.optimizeRelative);
        imgDeleteWaypoint_1 = (ImageButton) findViewById(R.id.btn_delete_waypoint_1);
        imgDeleteWaypoint_2 = (ImageButton) findViewById(R.id.btn_delete_waypoint_2);


        optimizeCheckbox.setChecked(optimize);
        transferNumberSpinner.setSelection(transferNumber - 1);
        walkingDistanceEditText.setText(walkingDistance + "");
/**
 * check position disable when motorbike
 */
        intent = getIntent();
        int tabPositon = intent.getIntExtra("postionTab", 0);
        if (tabPositon == 1) {
            disable(disableRelativeLayout);
        } else {
            disableRelativeLayout.setEnabled(true);
        }

        setTextToField();

        fromTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_1 = new Intent(SearchOptionActivity.this, AutoCompleteSearchActivity.class);
                intent_1.putExtra("number", SearchField.WAY_POINT_1);
                intent_1.putExtra("message", fromTextView.getText());
                startActivityForResult(intent_1, 3);// Activity is started with requestCode 1
            }
        });

        toTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_2 = new Intent(SearchOptionActivity.this, AutoCompleteSearchActivity.class);
                intent_2.putExtra("number", SearchField.WAY_POINT_2);
                intent_2.putExtra("message", toTextView.getText());
                startActivityForResult(intent_2, 4);// Activity is started with requestCode 2
            }
        });

        imgDeleteWaypoint_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapLocation.get(3) != null){
                    mapLocation.remove(3);
                    fromTextView.setText("");
                }
            }
        });

        imgDeleteWaypoint_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mapLocation.get(4) != null){
                    mapLocation.remove(4);
                    toTextView.setText("");
                }
            }
        });



        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchRouteActivity.class);
                boolean isChecked = optimizeCheckbox.isChecked();
                if (!"".equals(String.valueOf(walkingDistanceEditText.getText()))) {
                    walkingDistance = Integer.parseInt(String.valueOf(walkingDistanceEditText.getText()));
                }
                transferNumber = transferNumberSpinner.getSelectedItemPosition() + 1;
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
        // assign again to field
        setTextToField();
    }

    private void setTextToField() {
        if (mapLocation.get(SearchField.WAY_POINT_1) != null) {
            fromTextView.setText(mapLocation.get(SearchField.WAY_POINT_1).getName());
        }else {
            fromTextView.setText("");
        }

        if (mapLocation.get(SearchField.WAY_POINT_2) != null) {
            toTextView.setText(mapLocation.get(SearchField.WAY_POINT_2).getName());
        }else {
            toTextView.setText("");
        }
    }


    /**
     * disable view when click motorbike
     *
     * @param layout
     */
    private static void disable(ViewGroup layout) {
        layout.setEnabled(false);
        layout.setBackgroundColor(Color.parseColor("#CFD8DC"));
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disable((ViewGroup) child);
            } else {
                child.setEnabled(false);
            }
        }
    }
}
