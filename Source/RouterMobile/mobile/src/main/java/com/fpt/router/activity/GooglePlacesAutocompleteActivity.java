package com.fpt.router.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.fpt.router.R;
import com.fpt.router.adapter.GooglePlacesAutocompleteAdapter;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

import android.app.Activity;
import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;


public class GooglePlacesAutocompleteActivity extends Activity {

    private static final String TAG = "GooglePlacesAutocompleteActivity";

    public ArrayAdapter<String> adapter;
    public AutoCompleteTextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item);
        final AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView);
        adapter.setNotifyOnChange(true);
        textView.setAdapter(adapter);
        textView.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count%3 == 1) {
                    adapter.clear();
                    GetPlaces task = new GetPlaces();
                    //now pass the argument in the textview to the task
                    task.execute(textView.getText().toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {

            }

        });
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
                String googlePlaces =
                        "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" +
                                URLEncoder.encode(args[0], "UTF-8") +
                                "&types=geocode&language=vi&sensor=true&key=" +
                                "AIzaSyBkY1ok25IxoD6nRl_hunFAtTbh1EOss5A";

                String json;
                json = NetworkUtils.download(googlePlaces);
                //turn that string into a JSON object
                JSONObject predictions = new JSONObject(json);
                //now get the JSON array that's inside that object
                JSONArray ja = new JSONArray(predictions.getString("predictions"));

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = (JSONObject) ja.get(i);
                    //add each entry to our array
                    predictionsArr.add(jo.getString("description"));
                }
            } catch (IOException e)
            {
                Log.e("YourApp", "GetPlaces : doInBackground", e);
            } catch (JSONException e)
            {
                Log.e("YourApp", "GetPlaces : doInBackground", e);
            }

            return predictionsArr;
        }

        //then our post

        @Override
        protected void onPostExecute(ArrayList<String> result){

            Log.d("YourApp", "onPostExecute : " + result.size());
            //update the adapter
            adapter = new ArrayAdapter<>(getBaseContext(), R.layout.list_item);
            adapter.setNotifyOnChange(true);
            //attach the adapter to textview
            textView.setAdapter(adapter);
            for (String string : result) {
                Log.d("YourApp", "onPostExecute : result = " + string);
                adapter.add(string);
                adapter.notifyDataSetChanged();
            }

            Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());

        }

    }

}
