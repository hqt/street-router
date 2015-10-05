package com.fpt.router.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.fpt.router.R;
import com.fpt.router.adapter.GooglePlacesAutocompleteAdapter;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    public GooglePlacesAutocompleteAdapter adapter;
    public AutoCompleteTextView autoComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adapter = new GooglePlacesAutocompleteAdapter(this, R.layout.list_item, null);
        autoComp = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        adapter.setNotifyOnChange(true);
        autoComp.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count%2 == 1) {
                    GetPlaces task = new GetPlaces();
                    //now pass the argument in the textview to the task
                    task.execute(autoComp.getText().toString());
                    Log.e("Nam", "Thanh cong");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
            public void afterTextChanged(Editable s) {
            }
        });

        autoComp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            String message = autoComp.getText().toString();
                            Intent intent = new Intent();
                            intent.putExtra("MESSAGE", message);
                            int number = getIntent().getIntExtra("number", 1);
                            setResult(number, intent);
                            finish();//finishing activity
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class GetPlaces extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        // three dots is java for an array of strings
        protected ArrayList<String> doInBackground(String... args)
        {
            Log.d("gottaGo", "doInBackground");

            ArrayList<String> predictionsArr = new ArrayList<String>();

            try
            {
                //https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&types=geocode&language=fr&sensor=true&key=AddYourOwnKeyHere
                String url = NetworkUtils.linkGooglePlace(args[0]);

                String json = NetworkUtils.download(url);
                //turn that string into a JSON object
                JSONObject predictions = new JSONObject(json);
                //now get the JSON array that's inside that object
                JSONArray ja = new JSONArray(predictions.getString("predictions"));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    //add each entry to our array
                    predictionsArr.add(jo.getString("description"));
                }
            } catch (JSONException e)
            {
                Log.e("YourApp", "GetPlaces : doInBackground", e);
            }

            return predictionsArr;
        }

        //then our post

        @Override
        protected void onPostExecute(ArrayList<String> result){
            adapter.clear();
            for (String string : result) {
                Log.e("hqt", "onPostExecute : result = " + string);
                adapter.add(string);
            }
            adapter = new GooglePlacesAutocompleteAdapter(SearchActivity.this, R.layout.list_item, result);
            autoComp.setAdapter(adapter);
            Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());
        }
    }
}
