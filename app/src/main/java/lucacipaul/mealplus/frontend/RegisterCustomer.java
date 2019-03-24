package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;

import java.util.Date;

import lucacipaul.mealplus.backend.ActivityLevel;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.DietLog;
import lucacipaul.mealplus.backend.Gender;
import lucacipaul.mealplus.backend.Goal;


public class RegisterCustomer extends AppCompatActivity
        implements OnItemSelectedListener, OnSeekBarChangeListener {

    public static final String EXTRA_CUSTOMER_FINAL = "lucacipaul.mealplus.frontend.RegisterCustomer.EXTRA_CUSTOMER_FINAL";

    private String[] genders = {"Female", "Male", "Other"};
    private String[] goals = {"Lose Weight", "Keep Weight", "Gain Weight"};

    private int activityProgress = 0;
    private int genderSpinPos = 0;
    private int goalSpinPos = 0;

    EditText age, size, weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_customer_activity);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner genderSpin = findViewById(R.id.genderSpinner);
        Spinner goalSpin = findViewById(R.id.goalSpinner);
        SeekBar activityBar = findViewById(R.id.activitySeekBar);

        genderSpin.setOnItemSelectedListener(this);
        goalSpin.setOnItemSelectedListener(this);
        activityBar.setOnSeekBarChangeListener(this);

        activityBar.setMax(2);
        activityBar.setProgress(0);

        ArrayAdapter genderAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genders);
        ArrayAdapter goalAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, goals);

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpin.setAdapter(genderAdapter);
        goalSpin.setAdapter(goalAdapter);

        age = (EditText) findViewById(R.id.ageField);
        size = (EditText) findViewById(R.id.sizeField);
        weight = (EditText) findViewById(R.id.weightField);

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> spinner, View arg1, int position, long id) {
        switch(spinner.getId()) {
            case R.id.genderSpinner:
                genderSpinPos = position;
                Toast.makeText(getApplicationContext(), genders[position], Toast.LENGTH_LONG).show();
                break;
            case R.id.goalSpinner:
                goalSpinPos = position;
                Toast.makeText(getApplicationContext(), goals[position], Toast.LENGTH_LONG).show();
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) { }

    public void registerButtonClicked(View view) {

        if(!sanityCheckCustomer()) return;

        Customer customer = RegisterUser.customer;
        customer.setAge(Integer.parseInt(age.getText().toString()));
        customer.setWeight(Float.parseFloat(weight.getText().toString()));
        customer.setSize(Float.parseFloat(size.getText().toString()));
        customer.setActivityLevel(ActivityLevel.values()[activityProgress]);
        customer.setGoal(Goal.values()[goalSpinPos]);
        customer.setGender(Gender.values()[genderSpinPos]);
        customer.setDietLog(new DietLog());
        customer.setDefaultNutritionalValues();

        if(!DataManager.getInstance().register(customer)) {
            Toast.makeText(getApplicationContext(), "Go back and check e-mail and password!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, CustomerDashboard.class);
            intent.putExtra(EXTRA_CUSTOMER_FINAL, true);
            startActivity(intent);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch(seekBar.getId()) {
            case R.id.activitySeekBar:
                activityProgress = seekBar.getProgress();
                Toast.makeText(getApplicationContext(), Integer.toString(seekBar.getProgress()),Toast.LENGTH_LONG).show();
                break;
        }
    }

    private boolean sanityCheckCustomer() {

        if(age.getText().toString().isEmpty() ||
                size.getText().toString().isEmpty() ||
                weight.getText().toString().isEmpty() )
        {
            Toast.makeText(getApplicationContext(), "Fill in empty mandatory fields!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
