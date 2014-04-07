package denary.app.presenters;

import denary.app.models.Account;
import denary.app.models.AccountModel;
import denary.app.models.Transaction;
import denary.app.models.TransactionModel;
import denary.app.models.User;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/7/14.
 */
public class AddTransactionPresenter {
    private final IView myView;

    private final TransactionModel myModel;

    public AddTransactionPresenter(IView view, TransactionModel model) {
        myView = view;
        myModel = model;
    }

    public void onAddTransactionUserClick(User user, Account account, Transaction transaction){
        myModel.addTransaction(user,account,transaction);
        myView.advance();
    }
}
