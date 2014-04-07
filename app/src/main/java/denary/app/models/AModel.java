package denary.app.models;

import com.parse.ParseObject;
import java.util.List;

/**
 * Created by gtkesh on 3/30/14.
 */
public interface AModel {
    void createAccount(final User user, final Account account);
    void deleteAccount(final User user, final Account account);
    List<ParseObject> getAllAccounts(User user);
    void deleteAllAccounts(User user);
}
