package denary.app.models;

import android.content.Context;

import com.parse.Parse;

/**
 * Created by mtownsend on 3/30/14.
 */
public class ParseConfig {
    private String auth_key = "y6YnnaGukRlmugs9KphSLBH3i1SnJfJw3qWvn6GH";
    private String secret_key = "L19EYqDDXZ9bBMyM0VQkVCErn8vDDBQJr4CJiqo6";

    public ParseConfig(Context context){
        Parse.initialize(context, auth_key, secret_key);
    }



}
