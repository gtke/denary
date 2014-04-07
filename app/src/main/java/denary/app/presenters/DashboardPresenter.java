package denary.app.presenters;

import denary.app.models.Account;
import denary.app.models.AccountModel;
import denary.app.models.User;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/7/14.
 */
public class DashboardPresenter {
    private final IView myView;

    private final AccountModel myModel;

    public DashboardPresenter(IView view, AccountModel model) {
        myView = view;
        myModel = model;
    }

    public void advance(){
        myView.advance();
    }

}
