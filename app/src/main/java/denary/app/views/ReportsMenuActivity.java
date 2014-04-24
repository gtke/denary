package denary.app.views;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import denary.app.R;
import denary.app.models.ReportModel;
import denary.app.models.User;
import denary.app.presenters.ReportPresenter;

public class ReportsMenuActivity extends Activity implements  IView,TextToSpeech.OnInitListener {



    private static ReportPresenter myPresenter;

    private static Button generate_report_button;
    private static Button translate_button;
    private static Button share_button;
    private static Button dashboard_button;
    private TextView reportView;
    private String report = "";
    private TextToSpeech tts;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_menu);
        myPresenter = new ReportPresenter(this, new ReportModel());
        tts = new TextToSpeech(this, this);
        reportView = (TextView) findViewById(R.id.reportview);
        generate_report_button = (Button) findViewById(R.id.generate_report_button);
        generate_report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");

                Date startDate = null;
                try {
                    startDate = sourceFormat.parse("01/01/1971");
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                Date endDate = null;
                try {
                    endDate = sourceFormat.parse("01/01/3000");
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                EditText s = (EditText) findViewById(R.id.startDate);
                try {
                    if (startDate != null && s.getText().toString().length() > 1) {
                        startDate = sourceFormat.parse(s.getText().toString());
                    }
                } catch (java.text.ParseException e1) {
                    e1.printStackTrace();
                }

                EditText e = (EditText) findViewById(R.id.endDate);
                try {
                    if (endDate != null && e.getText().toString().length() > 0) {
                        endDate = sourceFormat.parse(e.getText().toString());
                    }
                } catch (java.text.ParseException e1) {
                    e1.printStackTrace();
                }
                User user = new User(ParseUser.getCurrentUser().getEmail().toString());
                report = myPresenter.onGenerateReportUserClick(user, startDate, endDate);
                System.out.println("REPORT for  " + user.getEmail() + "  :" + report);
                reportView.setText(report);
                speakOut(report);
            }
         });
        translate_button = (Button) findViewById(R.id.translate_button);
        translate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(v);
            }
        });

        share_button = (Button) findViewById(R.id.sharebtn);
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String s = report;
                sendIntent.putExtra(Intent.EXTRA_TEXT, s);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        dashboard_button = (Button) findViewById(R.id.dashboardbtn);
        dashboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advance();
            }
        });
        checkVoiceRecognition();
    }

    public void checkVoiceRecognition() {
        // Check if voice recognition is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            translate_button.setEnabled(false);
            translate_button.setText("Voice recognizer not present");
            Toast.makeText(this, "Voice recognizer not present",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void advance() {
        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
    }

    @Override
    public void advanceAlternative() {

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
                Log.e("TTS", "This Language is not supported");
            } else {
                generate_report_button.setEnabled(true);
                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        //Start the Voice recognizer activity for the result.
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            //If Voice recognition is successful then it returns RESULT_OK
            if(resultCode == RESULT_OK) {

                ArrayList<String> textMatchList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (!textMatchList.isEmpty()) {
                    String t = textMatchList.get(0).toLowerCase();
                    if (t.contains("italian")) {
                       tts.setLanguage(Locale.ITALIAN);
                    }else if(t.contains("french")){
                        tts.setLanguage(Locale.FRENCH);
                    }else if(t.contains("german")){
                        tts.setLanguage(Locale.GERMAN);
                    }else if(t.contains("japanese")){
                        tts.setLanguage(Locale.JAPANESE);
                    }else if(t.contains("korean")){
                        tts.setLanguage(Locale.KOREAN);
                    }else if(t.contains("stop")){
                        tts.setLanguage(Locale.US);
                        speakOut("");
                    }
                    else {
                        System.out.println("NOT RECOGNIZED: " + textMatchList.toString());
                        tts.setLanguage(Locale.US);
                        String s = "Language Not Supported";
                        showToastMessage(s);
                        speakOut(s);
                    }

                }
                //Result code for various error.
            }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
                showToastMessage("Audio Error");
            }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
                showToastMessage("Client Error");
            }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
                showToastMessage("Network Error");
            }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
                showToastMessage("No Match");
            }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
                showToastMessage("Server Error");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * Helper method to show the toast message
     **/
    void showToastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


