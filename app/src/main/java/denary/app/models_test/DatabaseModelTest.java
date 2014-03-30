package denary.app.models_test;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;

import denary.app.models.DatabaseModel;
import denary.app.models.ParseConfig;
import denary.app.models.User;
import android.content.Context;
import android.test.ServiceTestCase;
import android.test.mock.MockContext;

import java.lang.reflect.Method;

/**
 * Created by mtownsend on 3/30/14.
 */
public class DatabaseModelTest extends TestCase {
    public void testLogin() throws Exception {
        User testUser = new User("Test User", "test@mailinator.com", "pass123");
        ParseConfig parse = new ParseConfig(null);

        DatabaseModel testDB = new DatabaseModel();
        Assert.assertTrue(testDB.login(testUser));

    }


    public void testRegister() throws Exception {

    }

}
