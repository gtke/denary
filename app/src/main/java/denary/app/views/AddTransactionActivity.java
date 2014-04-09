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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import denary.app.R;
import denary.app.models.Account;
import denary.app.models.Transaction;
import denary.app.models.TransactionModel;
import denary.app.models.User;
import denary.app.presenters.AddTransactionPresenter;

public class AddTransactionActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, IView {

    private static AddTransactionPresenter myPresenter;

    // UI references
    private static EditText _name;
    private static EditText _amount;
    private static RadioButton _type;
    private static RadioGroup _typeGroup;
    private static EditText _tag;

    // UI references
    private static String name;
    private static String amount;
    private static String tag;
    private static String type;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private ParseUser parseUser;
    private static User user;
    private static String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        myPresenter = new AddTransactionPresenter(this, new TransactionModel());

        parseUser = ParseUser.getCurrentUser();
        user = new User(parseUser.getEmail());

        try{
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if(extras == null) {
                    accountName = "";
                } else {
                    accountName = extras.getString("account_name");
                    System.out.println("YLE :" +  accountName);

                }
            } else {
                accountName = (String) savedInstanceState.getSerializable("account_name");
            }
            System.out.println(accountName);
        }catch(Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),
                "Account: " + accountName, Toast.LENGTH_LONG)
                .show();


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
            getMenuInflater().inflate(R.menu.add_transaction, menu);
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
        Intent transactions = new Intent(this, TransactionsActivity.class);
        transactions.putExtra("account_name", accountName);
        startActivity(transactions);
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
            final View rootView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //    textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            _name = (EditText)rootView.findViewById(R.id.transaction_name);
            _amount = (EditText)rootView.findViewById(R.id.transaction_amount);
            _tag = (EditText) rootView.findViewById(R.id.transaction_tags);
            _typeGroup = (RadioGroup) rootView.findViewById(R.id.radioType);

            Button addTransactionButton = (Button)rootView.findViewById(R.id.create_transaction_button);
            addTransactionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = _name.getText().toString();
                    amount = _amount.getText().toString();
                    tag = _tag.getText().toString();
                    // get selected radio button from radioGroup
                    int selectedId = _typeGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    _type = (RadioButton) rootView.findViewById(selectedId);
                    type = _type.getText().toString();
                    Account account = new Account(accountName);
                    Transaction transaction = new Transaction(name,tag,amount,type);
                    myPresenter.onAddTransactionUserClick(user,account,transaction);
                    System.out.println("DONE ! ! ! !");
                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((AddTransactionActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



}
