package denary.app.presenters;

import denary.app.models.Account;
import denary.app.models.Transaction;
import denary.app.models.TransactionModel;
import denary.app.models.User;
import denary.app.views.IDashboardView;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/7/14.
 */
public class CurrentAccountPresenter {
    private final IDashboardView myView;

    private final TransactionModel myModel;

    public CurrentAccountPresenter(IDashboardView view, TransactionModel model) {
        myView = view;
        myModel = model;
    }

    public void advance(String s){
        myView.advance(s);
    }
}
