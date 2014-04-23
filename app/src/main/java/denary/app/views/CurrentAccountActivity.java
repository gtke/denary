package denary.app.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import denary.app.R;
import denary.app.models.TransactionModel;
import denary.app.presenters.CurrentAccountPresenter;

public class CurrentAccountActivity extends Activity implements IDashboardView {
    private CurrentAccountPresenter myPresenter;
    private String accountName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_account);
        myPresenter = new CurrentAccountPresenter(this, new TransactionModel());

        try{
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if(extras == null) {
                    accountName = "";
                } else {
                    accountName = extras.getString("account_name");
                }
            } else {
                accountName = (String) savedInstanceState.getSerializable("account_name");
            }
            System.out.println(accountName);
        }catch(Exception e){
            e.printStackTrace();
        }

        Button addTransactionButton = (Button) findViewById(R.id.new_trans);
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPresenter.advance(accountName);
            }
        });


        TextView name = (TextView) findViewById(R.id.account_name_textview);
        name.setText("Account name: " + accountName);

        TextView accountBalance = (TextView) findViewById(R.id.account_balance_textview);
        try {
            accountBalance.setText("Balance: " + getAccountBalance());
        } catch (ParseException e) {
            e.printStackTrace();
        }




    }



    private String getAccountBalance() throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo("name", accountName);
        ParseObject balance = null;
        try {
            balance = query.getFirst();
        }catch (ParseException pe){
            pe.printStackTrace();
        }
        Object temp = "$0.00";
        if (balance != null) {
            temp = balance.get("balance");
        }
        //double b = 0.0;
        //b = Double.parseDouble(temp.toString());
        return temp.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.current_account, menu);
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

    }

    @Override
    public void advance(String s) {
        Intent newTransaction = new Intent(this, AddTransactionActivity.class);
        newTransaction.putExtra("account_name", accountName);
        startActivity(newTransaction);
    }

    @Override
    public void advanceAlternative() {

    }
}
