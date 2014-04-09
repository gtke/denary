package denary.app.Tests;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import junit.framework.Assert;

import java.util.List;
import java.util.Random;

import denary.app.R;
import denary.app.models.Account;
import denary.app.models.AccountModel;
import denary.app.models.DatabaseModel;
import denary.app.models.Transaction;
import denary.app.models.TransactionModel;
import denary.app.models.User;
import denary.app.views.LoginActivity;

/**
 * Created by mtownsend on 4/6/14.
 */
public class UITests extends ActivityInstrumentationTestCase2<LoginActivity> {
    private DatabaseModel db;
    @TargetApi(Build.VERSION_CODES.FROYO)
    public UITests() {
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

    // Nayomi Mitchell
    @UiThreadTest
    public void testActualLogin() {
        ParseUser.logOut();
        AutoCompleteTextView mEmailView = (AutoCompleteTextView) getActivity().findViewById(R.id.email);
        EditText mPasswordView = (EditText) getActivity().findViewById(R.id.password);
        mEmailView.setText("admin");
        mPasswordView.setText("pass");
        Button mEmailSignInButton = (Button) getActivity().findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.performClick();
        Assert.assertNull(ParseUser.getCurrentUser());

        mPasswordView.setText("pass123");
        mEmailSignInButton.performClick();
        Assert.assertTrue(ParseUser.getCurrentUser().isAuthenticated());
        Assert.assertEquals("mtownsend@gatech.edu", ParseUser.getCurrentUser().getEmail());
    }




}