package denary.app.models;



//import com.parse.Parse;
//import com.parse.ParseUser;

import android.content.Context;
import android.test.AndroidTestCase;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ParseConfig {

    public ParseConfig(Context context) throws Exception {
        super();
        parseInit(context);
    }

    private void parseInit(Context context) throws ParseException {
        Parse.initialize(context,"y6YnnaGukRlmugs9KphSLBH3i1SnJfJw3qWvn6GH",
                "L19EYqDDXZ9bBMyM0VQkVCErn8vDDBQJr4CJiqo6");
    }

}
