package denary.app.models;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseUser;


//import com.parse.Parse;
//import com.parse.ParseUser;

import android.content.Context;
import android.test.AndroidTestCase;

public class ParseConfig {

    public ParseConfig() throws Exception {
        super();
        run();
    }

    private void run() throws ParseException {

        Parse.initialize("y6YnnaGukRlmugs9KphSLBH3i1SnJfJw3qWvn6GH",
                "L19EYqDDXZ9bBMyM0VQkVCErn8vDDBQJr4CJiqo6");
        String email = ParseUser.logIn("admin", "pass123").getEmail();
    }

}
