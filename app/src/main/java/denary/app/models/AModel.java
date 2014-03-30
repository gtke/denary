package denary.app.models;

import java.util.Collection;

/**
 * Created by gtkesh on 3/30/14.
 */
public interface AModel {
    void createAccount(final User user, final Account account);
    void deleteAccount(final User user, final Account account);
    Collection<Account> getAllAccounts();
}
