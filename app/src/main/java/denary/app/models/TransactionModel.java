package denary.app.models;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Collection;
import java.util.List;

/**
 * Created by gtkesh on 3/30/14.
 */
public class TransactionModel implements TModel{
    @Override
    public void addTransaction(User user, Account account, Transaction _transaction) {
        if(user!=null && account!=null && _transaction!=null){
            ParseObject transaction = new ParseObject("Transaction");
            transaction.put("owner", user.getEmail());
            transaction.put("account", account.getName());
            transaction.put("name", _transaction.getName());
            transaction.put("tag", _transaction.getTag());
            Double temp_amount = Double.parseDouble(_transaction.getAmount());
            if(_transaction.getType().equals("Withdraw")){
                temp_amount = -(temp_amount);
            }
            transaction.put("amount", temp_amount);
            transaction.put("type", _transaction.getType());
            transaction.saveEventually();

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
            ParseObject parse_account = null;
            try {
                parse_account = query.whereEqualTo("name", account.getName()).getFirst();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Object balance =  parse_account.get("balance");
            Double curr_balance = Double.parseDouble(balance.toString());

            parse_account.put("balance", curr_balance + temp_amount);
            try {
                parse_account.save();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteTransaction(User user, Account account, Transaction transaction) {

    }

    @Override
    public List<ParseObject> getAllTransactions(User user) {
        List<ParseObject> transactions = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo("owner", user.getEmail());
        try {
            transactions = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void deleteAllTransactions(User user) {
        List<ParseObject> transactions = getAllTransactions(user);
        for(ParseObject o : transactions){
            try {
                o.delete();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
