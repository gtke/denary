package denary.app.models;

import java.util.Collection;

/**
 * Created by gtkesh on 3/30/14.
 */
public interface TModel {
    void addTransaction(User user, Account account, Transaction transaction);
    void deleteTransaction(User user, Account account, Transaction transaction);
    Collection<Transaction> getAllAccounts();
}
