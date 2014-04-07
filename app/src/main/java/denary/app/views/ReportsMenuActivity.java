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
import denary.app.presenters.ReportPresenter;

public class ReportsMenuActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, IView {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private static ReportPresenter myPresenter;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private static Button generate_report_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_menu);
        myPresenter = new ReportPresenter(this, new ReportModel());

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));




    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.reports_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void advance() {
        Intent i  = new Intent(this, ReportDisplayActivity.class);
        startActivity(i);
    }

    @Override
    public void advanceAlternative() {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_reports_menu, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

            generate_report_button = (Button) rootView.findViewById(R.id.generate_report_button);
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



                    EditText s = (EditText) rootView.findViewById(R.id.startDate);
                    try {
                        if (startDate != null && s.getText().toString().length() > 1) {
                            startDate = sourceFormat.parse(s.getText().toString());
                        }
                    } catch (java.text.ParseException e1) {
                        e1.printStackTrace();
                    }

                    EditText e = (EditText) rootView.findViewById(R.id.endDate);
                    try {
                        if (endDate != null && e.getText().toString().length() > 0) {
                            endDate = sourceFormat.parse(e.getText().toString());
                        }
                    } catch (java.text.ParseException e1) {
                        e1.printStackTrace();
                    }

                    ParseObject currentQuery = new ParseObject("currentQuery");
                    currentQuery.put("start", startDate);
                    currentQuery.put("end", endDate);
                    currentQuery.put("owner",ParseUser.getCurrentUser().getEmail());
                    try {
                        currentQuery.save();
                        currentQuery.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                myPresenter.onGenereteReportUserClick();
                            }
                        });
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((ReportsMenuActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
