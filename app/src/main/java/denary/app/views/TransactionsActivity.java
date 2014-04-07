package denary.app.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import denary.app.R;
import denary.app.models.TransactionModel;
import denary.app.presenters.TransactionsPresenter;

public class TransactionsActivity extends Activity implements ITransactionView{
    private TransactionsPresenter myPresenter;
    private ParseQueryAdapter<ParseObject> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        myPresenter = new TransactionsPresenter(this, new TransactionModel());

        adapter = new ParseQueryAdapter<ParseObject>(this, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery<ParseObject> create() {
                ParseQuery query = new ParseQuery("Transaction");
                query.whereEqualTo("owner", ParseUser.getCurrentUser().getEmail());
                query.orderByDescending("createdAt");
                return query;
            }
        });
        adapter.setTextKey("name");
        final ListView listView = (ListView) findViewById(R.id.transaction_listview);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transactions, menu);
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
        Intent addTransaction = new Intent(this, AddTransactionActivity.class);
        startActivity(addTransaction);
    }

    @Override
    public void advance(String s) {
        Intent current_transaction = new Intent(this, CurrentTransactionActivity.class);
        startActivity(current_transaction);
    }

    @Override
    public void advanceAlternative() {

    }
}
