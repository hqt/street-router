package com.fpt.router.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

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
    private  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_1);

        listView = (ListView) findViewById(R.id.listview_autosearch);
        adapter = new GooglePlacesAutocompleteAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        autoComp = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        Intent intent = new Intent();
        int number = getIntent().getIntExtra("number", 1);
        if(number == 2){
            autoComp.setHint("Chọn điểm đến");
        } else if(number == 3) {
            autoComp.setHint("Điểm trung gian 1");
        } else  if (number == 4) {
            autoComp.setHint("Điểm trung gian 2");
        }
        adapter.setNotifyOnChange(true);
        /*autoComp.setAdapter(adapter);*/
        if(adapter != null) {
            listView.setAdapter(adapter);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String itemValue = (String) listView.getItemAtPosition(position);
                autoComp.setText(itemValue);
            }
        });

        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count % 2 == 1) {
                    GetPlaces task = new GetPlaces();
                    //now pass the argument in the textview to the task
                    task.execute(autoComp.getText().toString());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
           /* for (String string : result) {
                Log.e("hqt", "onPostExecute : result = " + string);
               adapter.add(string);
            }*/
           adapter = new GooglePlacesAutocompleteAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, result);
           listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            /*autoComp.setAdapter(adapter);*/
            Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());
        }
    }
}
