package denary.app.presenters;

import denary.app.models.DatabaseModel;
import denary.app.models.User;
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

    public boolean onLoginUserClick(User user){
        if(myModel.login(user)){
            myView.advance();
            return true;
        }
        return false;
    }

    public String onPasswordHintUserClick(String email){
        return myModel.getPasswordHint(email);
    }

    public void onPasswordResetUserClick(String email){
        myModel.resetPassword(email);
    }
}
