package denary.app.models;

import com.parse.ParseObject;

import java.util.Collection;
import java.util.List;

/**
 * Created by gtkesh on 3/30/14.
 */
public interface TModel {
    void addTransaction(User user, Account account, Transaction transaction);
    void deleteTransaction(User user, Account account, Transaction transaction);
    List<ParseObject> getAllTransactions(User user);
    void deleteAllTransactions(User user);
}
