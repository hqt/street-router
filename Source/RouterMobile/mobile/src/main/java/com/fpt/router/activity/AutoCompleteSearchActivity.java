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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.adapter.AutocompleteAdapter;
import com.fpt.router.library.model.motorbike.AutocompleteObject;
import com.fpt.router.library.utils.string.LevenshteinDistance;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AutoCompleteSearchActivity extends AppCompatActivity {
    public AutocompleteAdapter adapter;
   /* public AutoCompleteTextView autoComp;*/
    private EditText autoComp;
    private  ListView listView;
    private ProgressBar progressBar;
    private List<AutocompleteObject> listLocation = new ArrayList<>();
    private AutocompleteObject location;
    boolean state = false;
    String phraseShouldToSearch;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_search);

        listView = (ListView) findViewById(R.id.listview_autosearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // default. hide progress bar
        progressBar.setVisibility(View.INVISIBLE);

        adapter = new AutocompleteAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<AutocompleteObject>());
        /*autoComp = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);*/
        autoComp = (EditText) findViewById(R.id.autoCompleteTextView);
        Intent intent = new Intent();
        int number = getIntent().getIntExtra("number", 1);
        String message = getIntent().getStringExtra("message");
        if(number == 1){
            autoComp.setText(message);
        }
        if(number == 2){
            autoComp.setHint("Chọn điểm đến");
            if(!"".equals(message)){
                autoComp.setText(message);
            }
        } else if(number == 3) {
            autoComp.setHint("Điểm trung gian 1");
            if(!"".equals(message)){
                autoComp.setText(message);
            }
        } else  if (number == 4) {
            autoComp.setHint("Điểm trung gian 2");
            if(!"".equals(message)){
                autoComp.setText(message);
            }
        }
        adapter.setNotifyOnChange(true);
        /*autoComp.setAdapter(adapter);*/
        if(adapter != null) {
            listView.setAdapter(adapter);
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                location = listLocation.get(position);
                autoComp.setText(location.getName());
            }
        });

        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phraseShouldToSearch = autoComp.getText().toString().trim();

                if ((phraseShouldToSearch.length() > 0) && (!state)) {
                    startThreadSearch();
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
                        case KeyEvent.FLAG_EDITOR_ACTION:
                        case 12:
                            Intent intent = new Intent();
                            int number = getIntent().getIntExtra("number", 1);
                            if(location != null) {
                                intent.putExtra("NAME", location.getName());
                                intent.putExtra("PLACE_ID", location.getPlace_id());
                            } else if (!autoComp.getText().toString().equals("")) {
                                if (SearchRouteActivity.listLocation.size() >= number) {
                                    if(autoComp.getText().toString().equals(SearchRouteActivity.listLocation.get(number - 1 ).getName())) {
                                        intent.putExtra("NAME", SearchRouteActivity.listLocation.get(number - 1).getName());
                                        intent.putExtra("PLACE_ID", SearchRouteActivity.listLocation.get(number - 1).getPlace_id());
                                    }else {
                                        intent.putExtra("NAME", autoComp.getText().toString());
                                        intent.putExtra("PLACE_ID", "");
                                    }
                                } else {
                                    intent.putExtra("NAME", autoComp.getText().toString());
                                    intent.putExtra("PLACE_ID", "");
                                }
                            }
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

    private void startThreadSearch() {
        Log.e("hqthao", "start searching " + phraseShouldToSearch);
        progressBar.setVisibility(View.VISIBLE);
        state = true;
        GetPlacesTask task = new GetPlacesTask();
        //now pass the argument in the textview to the task
        task.execute(phraseShouldToSearch);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                Intent intent = new Intent();
                int number = getIntent().getIntExtra("number", 1);
                if(location != null) {
                    intent.putExtra("NAME", location.getName());
                    intent.putExtra("PLACE_ID", location.getPlace_id());
                } else if (!autoComp.getText().toString().equals("")) {
                    if (SearchRouteActivity.listLocation.size() >= number) {
                        if(autoComp.getText().toString().equals(SearchRouteActivity.listLocation.get(number - 1 ).getName())) {
                            intent.putExtra("NAME", SearchRouteActivity.listLocation.get(number - 1).getName());
                            intent.putExtra("PLACE_ID", SearchRouteActivity.listLocation.get(number - 1).getPlace_id());
                        }else {
                            intent.putExtra("NAME", autoComp.getText().toString());
                            intent.putExtra("PLACE_ID", "");
                        }
                    } else {
                        intent.putExtra("NAME", autoComp.getText().toString());
                        intent.putExtra("PLACE_ID", "");
                    }
                }
                setResult(number, intent);
                finish();//finishing activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class GetPlacesTask extends AsyncTask<String, Void, ArrayList<AutocompleteObject>> {

        private String searchString;

        @Override
        // three dots is java for an array of strings
        protected ArrayList<AutocompleteObject> doInBackground(String... args)
        {
            Log.d("gottaGo", "doInBackground");

            ArrayList<AutocompleteObject> predictionsArr = new ArrayList<>();
            try
            {
                //https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&types=geocode&language=fr&sensor=true&key=AddYourOwnKeyHere
                searchString = args[0];
                List<String> listUrl = GoogleAPIUtils.getGooglePlace(searchString);
                List<String> json = new ArrayList<>();
                for(int n = 0; n < listUrl.size(); n++) {
                    json.add(NetworkUtils.download(listUrl.get(n)));
                }
                if(json.get(0) == null){
                    return null;
                } else {
                    //turn that string into a JSON object
                    for (int x = 0; x < json.size(); x++) {
                        JSONObject predictions = new JSONObject(json.get(x));
                        //now get the JSON array that's inside that object
                        status = predictions.getString("status");
                        if(status.equals("OVER_QUERY_LIMIT")) {
                            return null;
                        } else {
                            JSONArray ja = new JSONArray(predictions.getString("predictions"));

                            for (int y = 0; y < ja.length(); y++) {
                                JSONObject jo = (JSONObject) ja.get(y);
                                String name = jo.getString("description");
                                String place_id = jo.getString("place_id");
                                predictionsArr.add(new AutocompleteObject(name, place_id));
                            }
                        }
                    }
                }
            } catch (JSONException e)
            {
                Log.e("YourApp", "GetPlacesTask : doInBackground", e);
            }

            return predictionsArr;
        }

        //then our post

        @Override
        protected void onPostExecute(ArrayList<AutocompleteObject> results){
            adapter.clear();

            state = false;
            if (!phraseShouldToSearch.equals(searchString)) {
                startThreadSearch();
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }

           /* for (String string : result) {
                Log.e("hqt", "onPostExecute : result = " + string);
               adapter.add(string);
            }*/
            if(results == null){
                AutocompleteObject autocompleteObject = new AutocompleteObject();
                results = new ArrayList<>();
                if(status.equals("OVER_QUERY_LIMIT")) {
                    Toast.makeText(AutoCompleteSearchActivity.this, "Hết quota cmnr", Toast.LENGTH_SHORT).show();
                } else if(!NetworkUtils.isNetworkConnected()) {
                    Toast.makeText(AutoCompleteSearchActivity.this, "Phải kết nối Internet", Toast.LENGTH_SHORT).show();
                }
                adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this,android.R.layout.simple_list_item_1,results);
                /*return;*/
            }
            listLocation = results;
            adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this, android.R.layout.simple_list_item_1, results);

            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            /*autoComp.setAdapter(adapter);*/
            Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());
        }
    }
}
