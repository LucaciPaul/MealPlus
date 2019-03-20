package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.Dummy;
import lucacipaul.mealplus.backend.User;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_CUSTOMER_LOGIN = "lucacipaul.mealplus.frontend.LoginActivity.EXTRA_CUSTOMER_LOGIN";
    //public static final String EXTRA_ADVISER = "lucacipaul.mealplus.frontend.LoginActivity.EXTRA_ADVISER_LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Dummy.set();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    public void mainRegisterButtonClicked(View view) {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

    public void mainLoginButtonClicked(View view) {
        EditText email = (EditText)findViewById(R.id.emailField);
        EditText pwd = (EditText)findViewById(R.id.passwordField);

        User user = DataManager.getInstance().login(email.getText().toString(), pwd.getText().toString());

        if(user != null) {
            System.out.println(((Customer)user).getDietLog());

            Intent intent = new Intent(this, CustomerDashboard.class);
            intent.putExtra(EXTRA_CUSTOMER_LOGIN, (Customer)user);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid login", Toast.LENGTH_LONG).show();
        }
    }
}


































