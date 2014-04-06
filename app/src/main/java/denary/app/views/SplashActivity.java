package denary.app.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Handler;


import com.parse.ParseException;
import com.parse.ParseUser;

import denary.app.R;
import denary.app.extra.NanoConverter;
import denary.app.models.ParseConfig;
import denary.app.presenters.WelcomePresenter;

public class SplashActivity extends Activity implements IView{
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                advance(); // go to login screen
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
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
    public void advance(){

     //   Intent login_intent = new Intent(this, LoginActivity.class);
        Intent converter = new Intent(this, NanoConverter.class);
        startActivity(converter);

    }


}
