package denary.app.extra;

import denary.app.R;
import denary.app.views.*;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import junit.framework.Assert;

/**
 * Created by mtownsend on 4/6/14.
 */
public class BasicTests extends ActivityInstrumentationTestCase2<SplashActivity> {
    private SplashActivity mSplashActivity;
    private TextView mTestText;

    public BasicTests() {
        super(SplashActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSplashActivity = getActivity();
        mTestText =
                (TextView) mSplashActivity
                        .findViewById(R.id.title);
        Parse.initialize(mSplashActivity.getApplicationContext(),
                "y6YnnaGukRlmugs9KphSLBH3i1SnJfJw3qWvn6GH",
                "L19EYqDDXZ9bBMyM0VQkVCErn8vDDBQJr4CJiqo6");
    }

    public void testValidLogin() throws ParseException {
        ParseUser test = ParseUser.logIn("admin", "pass123");
        Assert.assertEquals("mtownsend@gatech.edu", test.getEmail());
    }

    public void testInvalidLogin() {
        try {
            ParseUser test = ParseUser.logIn("zzzzz", "zzzzz");
        } catch (ParseException e) {
            assertEquals("invalid login credentials", e.getMessage());
        }

        try {
            ParseUser test = ParseUser.logIn("", "");
        } catch (ParseException e) {
            assertEquals("invalid login credentials", e.getMessage());
        }
    }

    public void regi
}