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

public class AddTransactionActivity extends Activity implements  IView {

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


    private CharSequence mTitle;
    private ParseUser parseUser;
    private static User user;
    private static String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        myPresenter = new AddTransactionPresenter(this, new TransactionModel());

        _name = (EditText) findViewById(R.id.transaction_name);
        _amount = (EditText)findViewById(R.id.transaction_amount);
        _tag = (EditText) findViewById(R.id.transaction_tags);
        _typeGroup = (RadioGroup) findViewById(R.id.radioType);

        Button addTransactionButton = (Button)findViewById(R.id.create_transaction_button);
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = _name.getText().toString();
                amount = _amount.getText().toString();
                tag = _tag.getText().toString();
                // get selected radio button from radioGroup
                int selectedId = _typeGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                _type = (RadioButton) findViewById(selectedId);
                type = _type.getText().toString();
                Account account = new Account(accountName);
                Transaction transaction = new Transaction(name,tag,amount,type);
                myPresenter.onAddTransactionUserClick(user,account,transaction);
                System.out.println("DONE ! ! ! !");
            }
        });

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
}
