package com.example.studyapp.oodler;

import android.content.Intent;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Locale;

public class DisplayMessageActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private AdView mAdView;
    private TextToSpeech tts;
    private Button btnSpeak;
    private String toSpeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        // AUDIO STUFF
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.leedle_sound);
        mp.start();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String toDisplay = "";
        for(int i = 0; i < message.length(); i++){
            if(message.charAt(i) == 'a' || message.charAt(i) == 'e' || message.charAt(i) == 'i' || message.charAt(i) == 'o' || message.charAt(i) == 'u'){
                toDisplay += "oodle";
            } else if (message.charAt(i) == 'A' || message.charAt(i) == 'E' || message.charAt(i) == 'I' || message.charAt(i) == 'O' || message.charAt(i) == 'U') {
                toDisplay += "Oodle";
            }else{
                toDisplay += message.charAt(i);
            }
        }

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);

        // TEXT TO SPEECH
        tts = new TextToSpeech(this, this);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
        toSpeak = toDisplay;
        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                speakOut();
            }
        });

        // CHANGING TEXT
        textView.setText(toDisplay);

        // AD
        MobileAds.initialize(this, "@string/adid");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
        }

    }

    private void speakOut() {
        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
}
