package denary.app.views;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import denary.app.R;

public class ReportDisplayActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_display);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("currentQuery");
        query.whereEqualTo("owner", ParseUser.getCurrentUser().getEmail());
        query.orderByDescending("createdAt");
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> dateQuery, ParseException e) {
                if (e == null) {
                    try {
                        TextView display = (TextView) findViewById(R.id.textView);
                        display.setText("");
                        generateSpendingReport((Date) dateQuery.get(0).get("start"),
                                (Date) dateQuery.get(0).get("end"));
                        generateIncomeReport((Date) dateQuery.get(0).get("start"),
                                (Date) dateQuery.get(0).get("end"));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }


                } else {
                    System.out.println("Failed to get transactions");

                }
            }
        });



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
            getMenuInflater().inflate(R.menu.report_display, menu);
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
    private void generateSpendingReport(Date start, Date end) throws ParseException {
        // todo:
        // get the correct date range from the user input (need to figure out)
        // query all the tags for the given user for a give DATE RANGE (user input)
        // sum up all the transactions that belong to a certain TAG (e.g. FOOD, CHICKS, whatever)
        // repeat that process over the account set for the particular user and then represent the report

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
        query.whereEqualTo("owner", ParseUser.getCurrentUser().getEmail());
        query.whereGreaterThanOrEqualTo("createdAt", start);
        query.whereLessThanOrEqualTo("createdAt", end);
        List<ParseObject> results = query.find();

        HashMap<String, Double> map = new HashMap<String, Double>();

        for(ParseObject o : results){
            String key = o.get("tag").toString();
            Double value = Double.parseDouble(o.get("amount").toString());
            if(map.get(key)==null){
                if(value < 0.0){
                    map.put(key,value);
                }
            }else{
                if(value < 0.0){
                    Double old_value = map.get(key);
                    map.put(key, old_value + value);
                }
            }
        }

        DateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");

        TextView display = (TextView) findViewById(R.id.textView);

        display.append("Spending Category Report for " + ParseUser.getCurrentUser().get("name")+"\n");
        display.append(displayFormat.format(start) + " - " + displayFormat.format(end) + "\n");
        double total = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            display.append("\t" + key+ " : " + Math.abs(value)+"\n");
            total += Math.abs(value);

        }
        display.append("\n");
        display.append("\t"+ "Total: " + total);
        display.append("\n");

    }

    private void generateIncomeReport(Date start, Date end) throws ParseException{
        // todo:
        // get the correct date range from the user input (need to figure out)
        // query all the tags for the given user for a give DATE RANGE (user input)
        // sum up all the transactions that belong to a certain TAG (e.g. FOOD, CHICKS, whatever)
        // repeat that process over the account set for the particular user and then represent the report

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
        query.whereEqualTo("owner", ParseUser.getCurrentUser().getEmail());
        query.whereGreaterThanOrEqualTo("createdAt", start);
        query.whereLessThanOrEqualTo("createdAt", end);
        List<ParseObject> results = query.find();

        HashMap<String, Double> map = new HashMap<String, Double>();

        for(ParseObject o : results){
            String key = o.get("tag").toString();
            Double value = Double.parseDouble(o.get("amount").toString());
            if(map.get(key)==null){
                if(value > 0.0){
                    map.put(key,value);
                }
            }else{
                if(value > 0.0){
                    Double old_value = map.get(key);
                    map.put(key, old_value + value);
                }
            }
        }

        DateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");

        TextView display = (TextView) findViewById(R.id.textView);

        display.append("\n"+"Income Report for " + ParseUser.getCurrentUser().get("name") + "\n");
        display.append(displayFormat.format(start) + " - " + displayFormat.format(end) + "\n");
        double total = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            display.append("\t" + key+ " : " + Math.abs(value)+"\n");
            total += Math.abs(value);

        }
        display.append("\n");
        display.append("\t"+ "Total: " + total);
        display.append("\n");


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
            View rootView = inflater.inflate(R.layout.fragment_report_display, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((ReportDisplayActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
