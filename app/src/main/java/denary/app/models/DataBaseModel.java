package denary.app.models;

import com.parse.LogInCallback;
//import com.parse.ParseException;
//import com.parse.ParseUser;

import org.parse4j.ParseUser;
import org.parse4j.ParseException;

import java.util.IllegalFormatConversionException;

/**
 * Created by gtkesh on 3/30/14.
 */
public class DatabaseModel implements DBModel {


    @Override
    public boolean login(User user) {
        ParseUser activeUser = null;
        try {
            activeUser = ParseUser.logIn(user.getEmail(), user.getPassword());
        } catch (ParseException e) {
            e.printStackTrace();
        } {

        }
        return activeUser != null;
    }



    @Override
    public void register(User user) {

    }
}
