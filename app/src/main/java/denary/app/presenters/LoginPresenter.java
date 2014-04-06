package denary.app.presenters;

import denary.app.models.DatabaseModel;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/5/14.
 */
public class LoginPresenter {

    private final IView myView;

    private final DatabaseModel myModel;

    public LoginPresenter(IView view, DatabaseModel model) {
        myView = view;
        myModel = model;
    }


    public void advance() {
        myView.advance();
    }
}
