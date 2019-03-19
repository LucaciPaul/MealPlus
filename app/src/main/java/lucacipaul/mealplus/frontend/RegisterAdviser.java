package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lucacipaul.mealplus.backend.Adviser;

public class RegisterAdviser extends AppCompatActivity {

    private static final String EXTRA_ADVISER_FINAL = "lucacipaul.mealplus.frontend.LoginActivity.Adviser.EXTRA_ADVISER_FINAL";
    EditText rNo, occupation, address1, address2, postCode, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_adviser_activity);

        rNo = findViewById(R.id.registrationNumberField);
        occupation = findViewById(R.id.occupationField);
        address1 = findViewById(R.id.address1Field);
        address2 = findViewById(R.id.address2Field);
        postCode = findViewById(R.id.postCodeField);
        phone = findViewById(R.id.phoneNumberField);
    }

    public void adviserRegisterClicked(View view) {

        if(!sanityCheckAdviser()) return;

        Adviser adviser = getIntent().getParcelableExtra(RegisterUser.EXTRA_ADVISER);
        adviser.setRegNo(rNo.getText().toString());
        adviser.setOccupation(occupation.getText().toString());
        adviser.setAddr1(address1.getText().toString());
        adviser.setAddr1(address2.getText().toString());
        adviser.setPostCode(postCode.getText().toString());
        adviser.setPhoneNo(phone.getText().toString());

        Intent intent = new Intent(this, RegisterAdviserFinal.class);
        intent.putExtra(EXTRA_ADVISER_FINAL, adviser);
        startActivity(intent);
    }

    private boolean sanityCheckAdviser() {
        if(rNo.getText().toString().isEmpty() ||
            occupation.getText().toString().isEmpty() ||
            address1.getText().toString().isEmpty() ||
            postCode.getText().toString().isEmpty() ||
            phone.getText().toString().isEmpty() )
        {
            Toast.makeText(getApplicationContext(), "Fill in mandatory fields empty!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!rNo.getText().toString().matches("^DT\\d{5}$"))
        {
            Toast.makeText(getApplicationContext(), "Invalid registration number!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
