package denary.app.presenters;

import denary.app.models.AccountModel;
import denary.app.models.Transaction;
import denary.app.models.TransactionModel;
import denary.app.views.IDashboardView;
import denary.app.views.ITransactionView;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/7/14.
 */
public class TransactionsPresenter {
    private final ITransactionView myView;

    private final TransactionModel myModel;

    public TransactionsPresenter(ITransactionView view, TransactionModel model) {
        myView = view;
        myModel = model;
    }

    public void advance(){
        myView.advance();
    }
    public void advance(String s){
        myView.advance(s);
    }
    public void advanceAlternative(){
        myView.advanceAlternative();
    }
}
