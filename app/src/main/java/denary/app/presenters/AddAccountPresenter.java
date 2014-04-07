package denary.app.presenters;

import denary.app.models.Account;
import denary.app.models.AccountModel;
import denary.app.models.User;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/7/14.
 */
public class AddAccountPresenter {
    private final IView myView;

    private final AccountModel myModel;

    public AddAccountPresenter(IView view, AccountModel model) {
        myView = view;
        myModel = model;
    }

    public void onAddAccountUserClick(User user, Account account){
        myModel.createAccount(user, account);
        myView.advance();
    }
}
