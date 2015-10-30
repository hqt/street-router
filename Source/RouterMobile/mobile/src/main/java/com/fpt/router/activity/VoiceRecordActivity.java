package com.fpt.router.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.library.config.AppConstants.GoogleApiCode;
import com.fpt.router.library.config.AppConstants.SearchField;
import com.fpt.router.library.model.common.AutocompleteObject;
import com.fpt.router.library.utils.StringUtils;
import com.fpt.router.utils.GoogleAPIUtils;
import com.fpt.router.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.fpt.router.activity.SearchRouteActivity.mapLocation;

/**
 * Created by Huynh Quang Thao on 10/27/15.
 */
public class VoiceRecordActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    ImageView voiceRecordButton;
    Button cancelButton;
    Button acceptButton;

    TextView speechTextView;
    ProgressBar progressBar;


    TextView fromPlaceTextView;
    TextView toPlaceTextView;
    TextView firstMiddlePlaceTextView;
    TextView secondMiddlePlaceTextView;

    TextView realFromPlaceTextView;
    TextView realToPlaceTextView;
    TextView realFirstMiddlePlaceTextView;
    TextView realSecondMiddlePlaceTextView;

    private AutocompleteObject fromAutoCompleteObject;
    private AutocompleteObject firstMiddleAutoCompleteObject;
    private AutocompleteObject secondMiddleAutoCompleteObject;
    private AutocompleteObject toAutoCompleteObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice_record);

        voiceRecordButton = (ImageView) findViewById(R.id.btnSpeak);
        acceptButton = (Button) findViewById(R.id.btn_yes);
        cancelButton = (Button) findViewById(R.id.btn_no);

        speechTextView = (TextView) findViewById(R.id.speech_text);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.INVISIBLE);


        fromPlaceTextView = (TextView) findViewById(R.id.from_text_view);
        toPlaceTextView = (TextView) findViewById(R.id.to_text_view);
        firstMiddlePlaceTextView = (TextView) findViewById(R.id.first_middle_text_view);
        secondMiddlePlaceTextView = (TextView) findViewById(R.id.second_middle_text_view);
        realFromPlaceTextView = (TextView) findViewById(R.id.real_from_text_view);
        realToPlaceTextView = (TextView) findViewById(R.id.real_to_text_view);
        realFirstMiddlePlaceTextView = (TextView) findViewById(R.id.real_first_middle_text_view);
        realSecondMiddlePlaceTextView = (TextView) findViewById(R.id.real_second_middle_text_view);


        voiceRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating list locations return to search route activity
                List<AutocompleteObject> res = new ArrayList<>();
                if (fromAutoCompleteObject != null) {
                    mapLocation.put(SearchField.FROM_LOCATION, fromAutoCompleteObject);
                }
                if (toAutoCompleteObject != null) {
                    mapLocation.put(SearchField.TO_LOCATION, toAutoCompleteObject);
                }
                if (firstMiddleAutoCompleteObject != null) {
                    mapLocation.put(SearchField.WAY_POINT_1, firstMiddleAutoCompleteObject);
                }
                if (secondMiddleAutoCompleteObject != null) {
                    mapLocation.put(SearchField.WAY_POINT_2, secondMiddleAutoCompleteObject);
                }

                Log.e("hqthao", "Size: " + SearchRouteActivity.mapLocation.size());
                setResult(1, null);
                finish();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mời bạn yêu cầu địa điểm");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "nhận dạng giọng nói chưa được hỗ trợ trên thiết bị hiện tại.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if ((resultCode == RESULT_OK) && (null != data)) {

                    // reset again all text field
                    fromPlaceTextView.setText("");
                    toPlaceTextView.setText("");
                    firstMiddlePlaceTextView.setText("");
                    secondMiddlePlaceTextView.setText("");

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String speech = result.get(0);
                    speechTextView.setText(speech);

                    Map<Integer, String> voiceTokens = StringUtils.processSpeech(speech);
                    if (voiceTokens.get(1) != null) {
                        fromPlaceTextView.setText(voiceTokens.get(1));
                    }
                    if (voiceTokens.get(2) != null) {
                        toPlaceTextView.setText(voiceTokens.get(2));
                    }
                    if (voiceTokens.get(3) != null) {
                        firstMiddlePlaceTextView.setText(voiceTokens.get(3));
                    }
                    if (voiceTokens.get(4) != null) {
                        secondMiddlePlaceTextView.setText(voiceTokens.get(4));
                    }

                    getAutoCompleteResult();

                }
                break;
            }
        }
    }

    private void closeAnimation() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    AtomicInteger remainThread;

    private void getAutoCompleteResult() {
        progressBar.setVisibility(View.VISIBLE);
        remainThread = new AtomicInteger(4);
        new GetGooglePlaceTask(fromPlaceTextView.getText().toString(), 1).execute();
        new GetGooglePlaceTask(toPlaceTextView.getText().toString(), 2).execute();
        new GetGooglePlaceTask(firstMiddlePlaceTextView.getText().toString(), 3).execute();
        new GetGooglePlaceTask(secondMiddlePlaceTextView.getText().toString(), 4).execute();
    }

    class GetGooglePlaceTask extends AsyncTask<String, Void, ArrayList<AutocompleteObject>> {

        private String place;
        private int index;
        String status;

        public GetGooglePlaceTask(String place, int index) {
            this.place = place;
            this.index = index;
            if (place.trim().length() == 0) status = "NOT_SEARCH";
        }

        @Override
        // three dots is java for an array of strings
        protected ArrayList<AutocompleteObject> doInBackground(String... args) {
            if ((status != null) && (status.equals("NOT_SEARCH"))) return null;
            Pair<String, ArrayList<AutocompleteObject>> res = GoogleAPIUtils.getAutoCompleteObject(place);
            status = res.first;
            return res.second;
        }

        //then our post

        @Override
        protected void onPostExecute(ArrayList<AutocompleteObject> results) {

            int c = remainThread.decrementAndGet();
            if (c == 0) {
                closeAnimation();
            }

            // do nothing. because user not input for this field
            if (status.equals("NOT_SEARCH")) return;

            if (results == null) {
                results = new ArrayList<>();
                if (status.equals(GoogleApiCode.OVER_QUERY_LIMIT)) {
                    Toast.makeText(VoiceRecordActivity.this, "Hết quota. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                } else if (!NetworkUtils.isNetworkConnected()) {
                    Toast.makeText(VoiceRecordActivity.this, "Phải kết nối Internet", Toast.LENGTH_SHORT).show();
                }
            }

            // get first result (maybe most accurately)
            TextView textView = null;
            switch (index) {
                case 1:
                    textView = realFromPlaceTextView;
                    break;
                case 2:
                    textView = realToPlaceTextView;
                    break;
                case 3:
                    textView = realFirstMiddlePlaceTextView;
                    break;
                case 4:
                    textView = realSecondMiddlePlaceTextView;
                    break;
            }

            if (results.size() == 0) {
                textView.setText("không có kết quả");
                return;
            }

            // assign new result to autocomplete object
            AutocompleteObject obj = results.get(0);
            textView.setText(obj.getName());
            switch (index) {
                case 1:
                    fromAutoCompleteObject = obj;
                    break;
                case 2:
                    toAutoCompleteObject = obj;
                    break;
                case 3:
                    firstMiddleAutoCompleteObject = obj;
                    break;
                case 4:
                    secondMiddleAutoCompleteObject = obj;
                    break;
            }
        }
    }
}