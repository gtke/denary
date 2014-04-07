package denary.app.models;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Collection;
import java.util.List;

/**
 * Created by gtkesh on 3/30/14.
 */
public class AccountModel implements AModel{


    @Override
    public void createAccount(User user, Account _account) {
        ParseObject account = new ParseObject("Account");
        account.put("name", _account.getName());
        account.put("owner", user.getEmail());
        account.put("bank_name", _account.getBank_name());
        account.put("tag", _account.getTag());
        account.put("balance", Integer.parseInt(_account.getBalance()));

        account.saveEventually();
    }

    @Override
    public void deleteAccount(User user, Account _account) {
        // Todo:
    }

    @Override
    public List<ParseObject> getAllAccounts(User user) {
        List<ParseObject> accounts = null;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo("owner", user.getEmail());
        try {
            accounts = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void deleteAllAccounts(User user) {
        List<ParseObject> accounts = getAllAccounts(user);
        for(ParseObject o : accounts){
            try {
                o.delete();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
