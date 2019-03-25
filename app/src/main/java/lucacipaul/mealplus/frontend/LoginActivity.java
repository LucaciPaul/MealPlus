package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import lucacipaul.mealplus.backend.Admin;
import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.Dummy;
import lucacipaul.mealplus.backend.User;

public class LoginActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    public static final String EXTRA_CUSTOMER_LOGIN = "lucacipaul.mealplus.frontend.LoginActivity.EXTRA_CUSTOMER_LOGIN";
    public static final String EXTRA_ADVISER_LOGIN = "lucacipaul.mealplus.frontend.LoginActivity.EXTRA_ADVISER_LOGIN";

    EditText email, pwd;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // [Prototype] Set dummy values only ONCE!
        // Dummy.set() could end up being called more than once as we "finish();" this Activity as
        // soon as we Login to an account
        // Upon logout, LoginActivity.Java is reopened with a new instance and Dummy.set() could
        // end up being called more than once without verifying Dummy.magicHappenedOnce.

        if(!Dummy.magicHappenedOnce) {
            Dummy.set();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        email = (EditText)findViewById(R.id.emailField);
        pwd = (EditText)findViewById(R.id.passwordField);

        login = (Button)findViewById(R.id.loginButton);

        pwd.setOnEditorActionListener(this);
    }

    public void mainRegisterButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

    public void mainLoginButtonClicked(View view) {

        User user = DataManager.getInstance().login(email.getText().toString(), pwd.getText().toString());

        if(user != null) {
            Intent intent;
            if(user instanceof Customer){
                intent = new Intent(this, CustomerDashboard.class);
                intent.putExtra(EXTRA_CUSTOMER_LOGIN, true);
                finish();
                startActivity(intent);
            } else if(user instanceof Adviser) {
                intent = new Intent(this, AdviserDashboard.class);
                intent.putExtra(EXTRA_ADVISER_LOGIN, true);
                finish();
                startActivity(intent);
            } else if(user instanceof Admin) {
                // [Prototype] No Admin Dashboard available
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid login", Toast.LENGTH_LONG).show();
        }
    }

    // Perform login if User presses "ENTER" button or "IME_ACTION_DONE" button from phone's keyboard
    // ... rather than register(as per usual)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            login.performClick();
        }
        return false;
    }
}