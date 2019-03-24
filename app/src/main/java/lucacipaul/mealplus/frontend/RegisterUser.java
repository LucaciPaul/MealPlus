package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Date;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.Title;

public class RegisterUser extends AppCompatActivity implements OnItemSelectedListener {

    String[] titles={"Mx", "Mrs", "Ms", "Mr"};
    private int titleSpinPos = 0;

    EditText firstName, lastName, email, cemail, pwd, cpwd;

    public static Customer customer;
    public static Adviser adviser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user_activity);

        Spinner titleSpin = (Spinner)findViewById(R.id.titleSpinner);
        titleSpin.setOnItemSelectedListener(this);

        ArrayAdapter titleAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, titles);
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        titleSpin.setAdapter(titleAdapter);

        firstName = (EditText)findViewById(R.id.forenameField);
        lastName = (EditText)findViewById(R.id.surnameField);
        email = (EditText)findViewById(R.id.emailField);
        cemail = (EditText)findViewById(R.id.confirmEmailField);
        pwd = (EditText)findViewById(R.id.passwordField);
        cpwd = (EditText)findViewById(R.id.confirmPasswordField);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> spinner, View arg1, int position, long id) {
        switch(spinner.getId()) {
            case R.id.titleSpinner:
                titleSpinPos = position;
                Toast.makeText(getApplicationContext(), titles[position], Toast.LENGTH_LONG).show();
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) { }

    public void confirmButtonClicked(View view){

        if(!sanityCheckUser()) return;

        ToggleButton accountToggle = findViewById(R.id.accountToggle);
        if (accountToggle.isChecked()){
            Intent intent = new Intent(this, RegisterAdviser.class);

            adviser = new Adviser();
            adviser.setTitle(Title.values()[titleSpinPos]);
            adviser.setFirstName(firstName.getText().toString());
            adviser.setLastName(lastName.getText().toString());
            adviser.setEmail(email.getText().toString());
            adviser.setPwd(pwd.getText().toString());
            adviser.setRegistrationDate(new Date());

            startActivity(intent);
        } else {
            Intent intent = new Intent(this, RegisterCustomer.class);

            customer = new Customer();
            customer.setTitle(Title.values()[titleSpinPos]);
            customer.setFirstName(firstName.getText().toString());
            customer.setLastName(lastName.getText().toString());
            customer.setEmail(email.getText().toString());
            customer.setPwd(pwd.getText().toString());
            customer.setRegistrationDate(new Date());

            startActivity(intent);
        }
    }

    private boolean sanityCheckUser() {

        if(firstName.getText().toString().isEmpty() ||
                lastName.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() ||
                pwd.getText().toString().isEmpty() )
        {
            Toast.makeText(getApplicationContext(), "Fill in empty mandatory fields!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!cemail.getText().toString().equalsIgnoreCase(email.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Please confirm your e-mail!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!cpwd.getText().toString().equalsIgnoreCase(pwd.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Please confirm your password!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
