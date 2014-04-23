package denary.app.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import denary.app.R;
import denary.app.models.DatabaseModel;
import denary.app.models.ParseConfig;
import denary.app.models.User;
import denary.app.presenters.LoginPresenter;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity implements IView{

    private LoginPresenter myPresenter;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myPresenter = new LoginPresenter(this, new DatabaseModel());

        // initialize Parse
        try {
            ParseConfig pf = new ParseConfig(this);
        } catch (Exception e) {
            e.printStackTrace();
        }



        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button registerButton = (Button)findViewById(R.id.email_register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                advanceAlternative();
            }
        });

        Button hintButton = (Button) findViewById(R.id.hintbtn);
        hintButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailView.getText().toString();
                String hint = myPresenter.onPasswordHintUserClick(email);

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, hint, duration);
                toast.show();
            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if(myPresenter.onLoginUserClick(new User(email, password))){
                Context context = getApplicationContext();
                CharSequence text = "Successfully logged in!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{
                Context context = getApplicationContext();
                CharSequence text = "Invalid email or password";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
            if(email.equals("admin")){
                return true;
            }
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }






    /**
     * Move to the next activity
     */
    @Override
    public void advance() {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }

    @Override
    public void advanceAlternative() {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }
}



