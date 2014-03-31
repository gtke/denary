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
    public void testLogin() throws Exception {
        User testUser = new User("Test User", "test@mailinator.com", "pass123");
        TestClass testClass = new TestClass();
        testClass.onCreate();


        DatabaseModel testDB = new DatabaseModel();
        Assert.assertTrue(testDB.login(testUser));

    }


    public void testRegister() throws Exception {

    }
    private class TestClass extends Application {
        @Override
        public void onCreate() {
            ParseConfig parse = new ParseConfig(this);
        }
    }

}
