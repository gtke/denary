package denary.app.models_test;


import junit.framework.Assert;
import junit.framework.TestCase;

import denary.app.models.DatabaseModel;
import denary.app.models.ParseConfig;
import denary.app.models.User;

import android.app.Application;

/**
 * Created by mtownsend on 3/30/14.
 */
public class DatabaseModelTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ParseConfig parse = new ParseConfig();
    }

    public void testLogin() throws Exception {
        User testUser = new User("Test User", "test@mailinator.com", "pass123");
        DatabaseModel testDB = new DatabaseModel();
        Assert.assertTrue(testDB.login(testUser));

    }


    public void testRegister() throws Exception {

    }

}
