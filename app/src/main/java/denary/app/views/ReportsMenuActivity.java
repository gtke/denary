package denary.app.views;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import denary.app.R;
import denary.app.models.ReportModel;
import denary.app.models.User;
import denary.app.presenters.ReportPresenter;

public class ReportsMenuActivity extends Activity implements  IView {



    private static ReportPresenter myPresenter;

    private CharSequence mTitle;
    private static Button generate_report_button;
    private TextView reportView;
    private String report = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_menu);
        myPresenter = new ReportPresenter(this, new ReportModel());
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
            }
         });

    }


    @Override
    public void advance() {

    }

    @Override
    public void advanceAlternative() {

    }


}
