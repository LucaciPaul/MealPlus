package lucacipaul.mealplus.frontend;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.User;

public class Settings extends AppCompatActivity {

    User user;

    EditText firstName, lastName, email,
            calories, carbs, proteins, fats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        firstName = ((EditText)findViewById(R.id.forenameField));;
        lastName = ((EditText)findViewById(R.id.surnameField));;
        email = ((EditText)findViewById(R.id.emailField));

        calories = ((EditText)findViewById(R.id.caloriesField));
        carbs = ((EditText)findViewById(R.id.carbsField));
        proteins = ((EditText)findViewById(R.id.proteinsField));
        fats = ((EditText)findViewById(R.id.fatsField));

        if(getIntent().getBooleanExtra(CustomerDashboard.EXTRA_CUSTOMER_CHANGES_SETTINGS, false)) { // Customer changing own settings
            findViewById(R.id.adviserSettingsLayout).setVisibility(View.GONE);
            user = CustomerDashboard.customer;

            int adviserStatus = ((Customer)user).adviserStatus();

            if(adviserStatus == -1) { // Customer has no Adviser

                // Hide these settings AND
                // allow the Customer to modify the Nutritional Settings
                findViewById(R.id.requestSettingsLayout).setVisibility(View.GONE);
                displayNutritionalSettings();

            } else if(adviserStatus == 0 ) { // An invitation is pending an answer

                // Display Adviser's e-mail and recycle layout by hiding the 'Leave Adviser'
                // The 'Accept' and 'Deny' buttons will appear instead
                ((TextView)findViewById(R.id.adviserEmailText)).setText(((Customer)user).getAdviser().getLastName() + ", " +((Customer)user).getAdviser().getFirstName());
                findViewById(R.id.leaveAdviserButton).setVisibility(View.GONE);

                // Allow the Customer to modify these settings until Adviser's invitation is accepted
                displayNutritionalSettings();

            } else { // Customer has Adviser

                // Deny access to Nutritional Settings if Customer has Adviser
                findViewById(R.id.nutritionalSettingsLayout).setVisibility(View.GONE);

                // Display Adviser's e-mail and recycle layout by hiding the 'Accept' and 'Deny' buttons
                // The 'Leave Adviser' button will appear instead
                ((TextView)findViewById(R.id.adviserEmailText)).setText(((Customer)user).getAdviser().getLastName() + ", " +((Customer)user).getAdviser().getFirstName());
                findViewById(R.id.acceptRequestButton).setVisibility(View.GONE);
                findViewById(R.id.denyRequestButton).setVisibility(View.GONE);
            }

            // Make fields non-editable for prototype purposes ONLY! [For submission]
            firstName.setFocusable(false);
            lastName.setFocusable(false);
            email.setFocusable(false);

            // Hide these settings for prototype purposes ONLY! [For submission]
            findViewById(R.id.titleSpinner).setVisibility(View.GONE);
            findViewById(R.id.confirmEmailField).setVisibility(View.GONE);
            findViewById(R.id.passwordField).setVisibility(View.GONE);
            findViewById(R.id.confirmPasswordField).setVisibility(View.GONE);
            findViewById(R.id.customerSettingsLayout).setVisibility(View.GONE);

            displayAccountSettings();

        } else if(getIntent().getBooleanExtra(CustomerDashboard.EXTRA_ADVISER_CHANGES_NUTRITIONAL_SETTINGS, false)) { // Adviser has searched Customer
            user = SearchResults.customer;

            findViewById(R.id.titleSpinner).setVisibility(View.GONE);
            findViewById(R.id.confirmEmailField).setVisibility(View.GONE);
            findViewById(R.id.passwordField).setVisibility(View.GONE);
            findViewById(R.id.confirmPasswordField).setVisibility(View.GONE);

            findViewById(R.id.customerSettingsLayout).setVisibility(View.GONE);
            findViewById(R.id.requestSettingsLayout).setVisibility(View.GONE);
            findViewById(R.id.adviserSettingsLayout).setVisibility(View.GONE);

            firstName.setFocusable(false);
            lastName.setFocusable(false);
            email.setFocusable(false);

            displayAccountSettings();
            displayNutritionalSettings();

        } else if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_ADVISER_CHANGES_SETTINGS, false)) { // Adviser changing own settings
            user = (Adviser) AdviserDashboard.adviser;

            // Make fields non-editable for prototype purposes ONLY! [For submission]
            firstName.setFocusable(false);
            lastName.setFocusable(false);
            email.setFocusable(false);

            // Hide these settings for prototype purposes ONLY! [For submission]
            findViewById(R.id.titleSpinner).setVisibility(View.GONE);
            findViewById(R.id.confirmEmailField).setVisibility(View.GONE);
            findViewById(R.id.passwordField).setVisibility(View.GONE);
            findViewById(R.id.confirmPasswordField).setVisibility(View.GONE);
            findViewById(R.id.adviserSettingsLayout).setVisibility(View.GONE);

            findViewById(R.id.customerSettingsLayout).setVisibility(View.GONE);
            findViewById(R.id.requestSettingsLayout).setVisibility(View.GONE);
            findViewById(R.id.nutritionalSettingsLayout).setVisibility(View.GONE);

            displayAccountSettings();
        }
    }
    public void displayAccountSettings() {
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
    }

    public void displayNutritionalSettings() {
        calories.setText(Float.toString(((Customer)user).getCaloriesPerDay()));
        carbs.setText(Float.toString(((Customer)user).getCarbsPerDay()));
        proteins.setText(Float.toString(((Customer)user).getProteinsPerDay()));
        fats.setText(Float.toString(((Customer)user).getFatsPerDay()));
    }

    public void submitChangesButtonClicked(View view) {
        if(user instanceof Customer) {
            ((Customer)user).setCaloriesPerDay(Float.parseFloat(calories.getText().toString()));
            ((Customer)user).setCarbsPerDay(Float.parseFloat(carbs.getText().toString()));
            ((Customer)user).setProteinsPerDay(Float.parseFloat(proteins.getText().toString()));
            ((Customer)user).setFatsPerDay(Float.parseFloat(fats.getText().toString()));
        }
        displayNutritionalSettings();
        CustomerDashboard.updateFields();
    }

    public void acceptRequestButtonClicked(View view) {
        ((Customer)user).setPendingRequest(false);
        ((Customer)user).getAdviser().getAssociatedCustomers().add((Customer)user);
        findViewById(R.id.nutritionalSettingsLayout).setVisibility(View.GONE);
        findViewById(R.id.acceptRequestButton).setVisibility(View.GONE);
        findViewById(R.id.denyRequestButton).setVisibility(View.GONE);
        findViewById(R.id.leaveAdviserButton).setVisibility(View.VISIBLE);
    }
    public void denyRequestButtonClicked(View view) {
        ((Customer)user).setPendingRequest(false);
        ((Customer)user).setAdviser(null);
        findViewById(R.id.requestSettingsLayout).setVisibility(View.GONE);
    }
    public void leaveAdviserButtonClicked(View view) {
        ((Customer)user).setAdviser(null);
        ((Customer)user).setDefaultNutritionalValues();
        CustomerDashboard.updateFields();
        findViewById(R.id.requestSettingsLayout).setVisibility(View.GONE);
        findViewById(R.id.nutritionalSettingsLayout).setVisibility(View.VISIBLE);
        displayNutritionalSettings();
    }
}
