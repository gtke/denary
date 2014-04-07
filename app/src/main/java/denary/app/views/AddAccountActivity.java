package denary.app.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.SimpleFormatter;

import denary.app.R;
import denary.app.models.Account;
import denary.app.models.AccountModel;
import denary.app.models.User;
import denary.app.presenters.AddAccountPresenter;

public class AddAccountActivity extends Activity implements IView {

    private AddAccountPresenter myPresenter;
    private String accountName;
    private String bankName;
    private String cardNumber;
    private String tag;
    private String balance;

    // UI refs
    private EditText _accountName;
    private EditText _bankName;
    private EditText _cardNumber;
    private EditText _tag;
    private EditText _balance;

    private  ParseUser parseUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        myPresenter = new AddAccountPresenter(this, new AccountModel());

        _accountName = (EditText) findViewById(R.id.account_name);
        _bankName = (EditText) findViewById(R.id.bank_name);
        _cardNumber = (EditText) findViewById(R.id.card_number);
        _tag = (EditText) findViewById(R.id.tag);
        _balance = (EditText)findViewById(R.id.balance);

        parseUser = ParseUser.getCurrentUser();
        user = new User(parseUser.getEmail());

        Button createAccountButton = (Button) findViewById(R.id.create_acc_btn);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check and validate
                accountName = _accountName.getText().toString();
                bankName = _bankName.getText().toString();
                cardNumber = _cardNumber.getText().toString();
                tag = _tag.getText().toString();
                balance = _balance.getText().toString();

                Account account = new Account(accountName,bankName, tag, balance, cardNumber);
                Context context = getApplicationContext();
                CharSequence text = "Account successfully created!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                myPresenter.onAddAccountUserClick(user, account);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_account, menu);
        return true;
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
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }

    @Override
    public void advanceAlternative() {

    }
}
