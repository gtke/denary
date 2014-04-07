package denary.app.presenters;

import denary.app.models.DatabaseModel;
import denary.app.models.User;
import denary.app.views.IView;

/**
 * Created by gtkesh on 4/6/14.
 */
public class RegisterPresenter {

    private final IView myView;

    private final DatabaseModel myModel;

    public RegisterPresenter(IView view, DatabaseModel model) {
        myView = view;
        myModel = model;
    }

    public boolean onRegisterUserClick(User user){
        if(myModel.register(user)){
            myView.advance();
            return true;
        }
        return false;
    }
}
