package com.fpt.router.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;
import com.fpt.router.library.utils.StringUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Huynh Quang Thao on 10/27/15.
 */
public class VoiceRecordActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    ImageView voiceRecordButton;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice_record);

        voiceRecordButton = (ImageView) findViewById(R.id.btnSpeak);

        speechTextView = (TextView) findViewById(R.id.speech_text);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);


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
                    "Tiếng Việt không được hỗ trợ trên thiết bị hiện tại.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if ((resultCode == RESULT_OK) && (null != data)) {

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
                }
                break;
            }
        }
    }
}
