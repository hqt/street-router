package com.fpt.router.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.adapter.AutocompleteAdapter;
import com.fpt.router.dal.SearchLocationDAL;
import com.fpt.router.library.config.AppConstants.GoogleApiCode;
import com.fpt.router.library.config.AppConstants.SearchField;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.model.SearchLocation;
import com.fpt.router.utils.GoogleAPIUtils;

import java.util.ArrayList;
import java.util.List;

import static com.fpt.router.activity.SearchRouteActivity.mapLocation;

public class AutoCompleteSearchActivity extends AppCompatActivity {
    public AutocompleteAdapter adapter;
    /*public AutoCompleteTextView autoComp;*/
    private EditText autoComp;
    public ListView listView;
    private ProgressBar progressBar;
    private List<AutocompleteObject> listLocation = new ArrayList<>();
    private AutocompleteObject location;
    boolean state = false;
    String phraseShouldToSearch;
    String status;
    private ImageButton mbVoiceSearch;
    List<SearchLocation> searchLocations = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete_search);

        listView = (ListView) findViewById(R.id.listview_autosearch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mbVoiceSearch = (ImageButton) findViewById(R.id.btn_voice);
        // default. hide progress bar
        progressBar.setVisibility(View.INVISIBLE);
        /*autoComp = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);*/

        searchLocations = SearchLocationDAL.getListSearchLocation();
        if((searchLocations == null) || (searchLocations.size() > 0)){
            AutocompleteAdapter.autoCompleteType = AutocompleteAdapter.AutoCompleteType.HISTORY_TYPE;
            List<AutocompleteObject> autoList = new ArrayList<>();
            AutocompleteObject autoObject;

            for (SearchLocation searchLocation: searchLocations){
                autoObject = new AutocompleteObject(searchLocation.getPlaceName(),searchLocation.getPlaceId());
                autoList.add(autoObject);
            }
            listLocation = autoList;

            adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this,autoList);
        }else{
            adapter = new AutocompleteAdapter(this, new ArrayList<AutocompleteObject>());
        }
        autoComp = (EditText) findViewById(R.id.autoCompleteTextView);
        int number = getIntent().getIntExtra("number", 1);
        String message = getIntent().getStringExtra("message");
        if ((message != null) && (message.length() > 0)) {
            autoComp.setText(message);
        }
        switch (number) {
            case SearchField.FROM_LOCATION:
                autoComp.setHint("Chọn điểm khởi hành");
                break;
            case SearchField.TO_LOCATION:
                autoComp.setHint("Chọn điểm đến");
                break;
            case SearchField.WAY_POINT_1:
                autoComp.setHint("Điểm trung gian 1");
                break;
            case SearchField.WAY_POINT_2:
                autoComp.setHint("Điểm trung gian 2");
                break;
        }

        adapter.setNotifyOnChange(true);
        /*autoComp.setAdapter(adapter);*/
        if (adapter != null) {
            listView.setAdapter(adapter);
           // autoComp.setAdapter(adapter);
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

        /**
         * voice listener
         */
        mbVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutoCompleteSearchActivity.this, VoiceRecordActivity.class);
                startActivity(intent);
            }
        });

        autoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phraseShouldToSearch = autoComp.getText().toString().trim();

                if ((phraseShouldToSearch.length() > 0) && (!state)) {
                    AutocompleteAdapter.autoCompleteType = AutocompleteAdapter.AutoCompleteType.SEARCH_TYPE;
                    startThreadSearch();
                } else {
                    AutocompleteAdapter.autoCompleteType = AutocompleteAdapter.AutoCompleteType.HISTORY_TYPE;
                    adapter.clear();
                    List<AutocompleteObject> autoList = new ArrayList<AutocompleteObject>();
                    AutocompleteObject autoObject;

                    Log.e("NgoanTT --->",searchLocations.size()+"");
                    for (SearchLocation searchLocation: searchLocations){
                        autoObject = new AutocompleteObject(searchLocation.getPlaceName(),searchLocation.getPlaceId());
                        autoList.add(autoObject);
                    }
                    listLocation = autoList;

                    adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this,autoList);
                    listView.setAdapter(adapter);
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
                        case KeyEvent.KEYCODE_ENTER:
                            return returnPreviousActivity();
                        default:
                            break;
                    }
                }
                //Test
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

    private boolean returnPreviousActivity() {
        int number = getIntent().getIntExtra("number", 1);
        // user has chosen one result in auto complete list
        if(mapLocation.get(number) != null) {
            if (!autoComp.getText().toString().equals(mapLocation.get(number).getName())) {
            if (!mapLocation.get(number).getName().equals(autoComp.getText().toString())) {
                SearchRouteActivity.isDataChange = true;
            }
        }
        if ((location != null)) {
            if (autoComp.getText().toString().equals(location.getName())) {
                mapLocation.put(number, location);
            } else {
                AutocompleteObject obj = new AutocompleteObject(autoComp.getText().toString(), "");
                mapLocation.put(number, obj);
            }
        } else {
            if (mapLocation.get(number) != null) {
                // user delete field
                if (autoComp.getText().toString().length() == 0) {
                    mapLocation.remove(number);
                }
                // user types random text.
                else if ((autoComp.getText().toString().length() > 0) &&
                        (!mapLocation.get(number).getName().equals(autoComp.getText().toString()))) {
                    AutocompleteObject obj = new AutocompleteObject(autoComp.getText().toString(), "");
                    mapLocation.put(number, obj);
                }
            } else {
                AutocompleteObject obj = new AutocompleteObject(autoComp.getText().toString(), "");
                mapLocation.put(number, obj);
            }
        }
        setResult(number, null);
        finish();//finishing activity
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return returnPreviousActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class GetPlacesTask extends AsyncTask<String, Void, ArrayList<AutocompleteObject>> {

        private String searchString;

        @Override
        // three dots is java for an array of strings
        protected ArrayList<AutocompleteObject> doInBackground(String... args) {
            Log.d("gottaGo", "doInBackground");

            //https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&types=geocode&language=fr&sensor=true&key=AddYourOwnKeyHere
            searchString = args[0];
            Pair<String, ArrayList<AutocompleteObject>> res = GoogleAPIUtils.getAutoCompleteObject(searchString);
            status = res.first;
            return res.second;
        }

        //then our post

        @Override
        protected void onPostExecute(ArrayList<AutocompleteObject> results) {
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
            if (results == null) {
                results = new ArrayList<>();
                if (status.equals(GoogleApiCode.NO_NETWORK)) {
                    Toast.makeText(AutoCompleteSearchActivity.this, "Phải kết nối Internet", Toast.LENGTH_SHORT).show();
                } else if (status.equals(GoogleApiCode.OVER_QUERY_LIMIT)) {
                    Toast.makeText(AutoCompleteSearchActivity.this, "Hết quota cmnr", Toast.LENGTH_SHORT).show();
                }
                adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this, results);

                // adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, results);
                /*return;*/
            }

            listLocation = results;
            adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this, results);
            // adapter = new AutocompleteAdapter(AutoCompleteSearchActivity.this, android.R.layout.simple_spinner_dropdown_item, results);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // autoComp.setAdapter(adapter);
            Log.d("YourApp", "onPostExecute : autoCompleteAdapter" + adapter.getCount());
        }
    }
}
