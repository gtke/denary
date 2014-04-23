package denary.app.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import denary.app.R;
import denary.app.models.DatabaseModel;
import denary.app.models.ParseConfig;
import denary.app.models.User;
import denary.app.presenters.RegisterPresenter;

public class RegisterActivity extends Activity implements IView {

    private RegisterPresenter myPresenter;

    // UI references.
    private EditText _name;
    private EditText _email;
    private EditText _password;
    private EditText _confirm_password;
    private EditText _passhint;

    //
    private String name;
    private String email;
    private String password;
    private String confirm_password;
    private String passhint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myPresenter = new RegisterPresenter(this, new DatabaseModel());

        // initialize Parse
        try {
            ParseConfig pf = new ParseConfig(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        _name = (EditText) findViewById(R.id.reg_name);
        _email = (EditText) findViewById(R.id.reg_email);
        _password = (EditText) findViewById(R.id.reg_password);
        _confirm_password = (EditText) findViewById(R.id.conf_reg_password);
        _passhint = (EditText) findViewById(R.id.passhint);



        Button registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = _name.getText().toString();
                email = _email.getText().toString();
                password = _password.getText().toString();
                confirm_password = _confirm_password.getText().toString();
                passhint = _passhint.getText().toString();

                int duration = Toast.LENGTH_SHORT;
                CharSequence text;
                // check if correct data
                if(passwordsMatch(password, confirm_password)){
                    text = "Successfully Registered!";
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                    User user = new User(name,email,password, passhint);
                    myPresenter.onRegisterUserClick(user);
                }else{
                    text = "Passwords do not match";
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void advance() {
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
    }

    @Override
    public void advanceAlternative() {
        //
    }

    // check if password and confirm_password match
    private boolean passwordsMatch(String pass1, String pass2){
        return pass1.equals(pass2);
    }
}
