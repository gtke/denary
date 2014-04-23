package denary.app.models;

import android.content.Intent;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
//import com.parse.ParseException;
//import com.parse.ParseUser;


import java.util.IllegalFormatConversionException;

/**
 * Created by gtkesh on 3/30/14.
 */
public class DatabaseModel implements DBModel {


    @Override
    public boolean login(User user) {
        boolean success = true;
        try {
            ParseUser.logIn(user.getEmail(), user.getPassword());
        } catch (ParseException e) {
            success = false;
           //e.printStackTrace();
            switch (e.getCode()) {
                case ParseException.USERNAME_MISSING:
                    System.out.println("Sorry, you must supply a username to register.");
                    break;
                case ParseException.PASSWORD_MISSING:
                    System.out.println("Sorry, you must supply a password to register.");
                    break;
                case ParseException.OBJECT_NOT_FOUND:
                    System.out.println("Sorry, those credentials were invalid.");
                    break;
                default:
                    System.out.println(e.getLocalizedMessage());
                    break;
            }
        }
        return success;
    }

    @Override
    public boolean register(User user) {
        boolean success = true;
        ParseUser parseUser = new ParseUser();
        // Fill User data
        parseUser.setUsername(user.getEmail());
        parseUser.setEmail(user.getEmail());
        parseUser.setPassword(user.getPassword());
        parseUser.put("name",user.getName());
        parseUser.put("hint", user.getHint());

        try {
            parseUser.signUp();
        } catch (ParseException e) {
            success = false;
            //e.printStackTrace();
            switch (e.getCode()) {
                case ParseException.EMAIL_TAKEN:
                    System.out.println("Sorry, email you specified is already taken.");
                    break;
                case ParseException.USERNAME_MISSING:
                    System.out.println("Sorry, you must supply a username to register.");
                    break;
                case ParseException.PASSWORD_MISSING:
                    System.out.println("Sorry, you must supply a password to register.");
                    break;
                case ParseException.OBJECT_NOT_FOUND:
                    System.out.println("Sorry, those credentials were invalid.");
                    break;
                default:
                    System.out.println(e.getLocalizedMessage());
                    break;
            }
        }
        return success;
    }

    @Override
    public String getPasswordHint(String email) {
        String hint = null;
        System.out.println("USERMAIL: " + email);
        System.out.println("USERMAIL: " + email);
        System.out.println("USERMAIL: " + email);
        System.out.println("USERMAIL: " + email);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", email);
        ParseObject user = null;
        try {
            user =  query.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(user!=null){
            hint = user.get("hint").toString();
        }
        return hint;
    }
}
