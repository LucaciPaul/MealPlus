package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.DataManager;

public class RegisterAdviser extends AppCompatActivity {

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

        if(!DataManager.getInstance().register(adviser)) {
            Toast.makeText(getApplicationContext(), "Go back and check e-mail and password!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, RegisterAdviserFinal.class);
            startActivity(intent);
        }
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
