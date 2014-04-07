package denary.app.Tests;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import junit.framework.Assert;


import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import junit.framework.Assert;

import java.util.List;

import denary.app.models.Account;
import denary.app.models.AccountModel;
import denary.app.models.Transaction;
import denary.app.models.TransactionModel;
import denary.app.models.User;
import denary.app.views.LoginActivity;
import denary.app.models.DatabaseModel;

/**
 * Created by mtownsend on 4/6/14.
 */
public class BasicTests extends ActivityInstrumentationTestCase2<LoginActivity> {
    private DatabaseModel db;
    @TargetApi(Build.VERSION_CODES.FROYO)
    public BasicTests() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        LoginActivity mLogin = getActivity();
        Parse.initialize(mLogin.getApplicationContext(),
                "y6YnnaGukRlmugs9KphSLBH3i1SnJfJw3qWvn6GH",
                "L19EYqDDXZ9bBMyM0VQkVCErn8vDDBQJr4CJiqo6");
        db = new DatabaseModel();
    }

    public void testValidLogin() throws ParseException {
        User user = new User("admin", "pass123");
        boolean test = db.login(user);
        Assert.assertTrue(test);
    }

    public void testInvalidLogin() {
        User user = new User("zzzzzz", "zzzzzz");
        boolean test = db.login(user);
        Assert.assertFalse(test);
    }


    public void testValidRegister() {
        User user = new User("wayne rooney", "wrooney1@gmail.com","pass123");
        boolean test = db.register(user);
        Assert.assertTrue(test);
    }

    public void testInvalidRegister(){
        User user = new User("wayne rooney", "wrooney@gmail.com", "pass123");
        boolean test = db.register(user);
        Assert.assertFalse(test);
    }

    public void testValidCreateAccount() throws ParseException {
        User user = new User("wayne rooney", "wrooney@gmail.com", "pass123");
        Account account = new Account("Primary", "Bank of America", "school", "200.00", "1111000022220000");
        AccountModel am = new AccountModel();
        am.createAccount(user, account);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo("name", account.getName());
        ParseObject obj = query.find().get(0);

        Assert.assertEquals("wrooney@gmail.com", obj.get("owner"));


    }

    public void testInvalidCreateAccount() throws ParseException {
        User user = null;
        Account account = new Account("Secondary", "Bank of America", "school", "1000.00", "1111000022220000");
        AccountModel am  = new AccountModel();
        am.createAccount(user,account);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo("name", account.getName());
        List<ParseObject> obj =  query.find();

        Assert.assertEquals(0, obj.size());

    }

    public void testValidMakeTransaction() throws ParseException {
        User user = new User("wayne rooney", "wrooney@gmail.com", "pass123");
        Account account = new Account("Primary", "Bank of America", "school", "1000.00", "1111000022220000");
        Transaction transaction = new Transaction("got iphone", "personal", "800.00", "withdraw");

        TransactionModel tm = new TransactionModel();
        tm.addTransaction(user,account,transaction);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Transaction");
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.whereEqualTo("owner", user.getEmail());
        query.whereEqualTo("account", account.getName());
        query.whereEqualTo("name", transaction.getName());


        ParseObject obj =  (ParseObject) query.find().get(0);

        Assert.assertEquals("wrooney@gmail.com", obj.get("owner"));
        Assert.assertEquals("Primary", obj.get("account"));
        Assert.assertEquals("got iphone", obj.get("name"));

    }

    public void testInvalidMakeTransaction() {

    }
}