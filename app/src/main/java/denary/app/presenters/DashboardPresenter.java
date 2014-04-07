package denary.app.presenters;

import denary.app.models.Account;
import denary.app.models.AccountModel;
import denary.app.models.User;
import denary.app.views.IDashboardView;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/7/14.
 */
public class DashboardPresenter {
    private final IDashboardView myView;

    private final AccountModel myModel;

    public DashboardPresenter(IDashboardView view, AccountModel model) {
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
