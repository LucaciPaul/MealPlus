package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.User;

public class AdviserDashboard extends AppCompatActivity {

    public static final String EXTRA_LINKED_CUSTOMERS = "lucacipaul.mealplus.frontend.AdviserDashboard.EXTRA_LINKED_CUSTOMERS";
    public static final String EXTRA_ADVISER_SEARCHES_CUSTOMER = "lucacipaul.mealplus.frontend.AdviserDashboard.EXTRA_SEARCH_CUSTOMERS";
    public static final String EXTRA_ADVISER_CHANGES_SETTINGS = "lucacipaul.mealplus.frontend.AdviserDashboard.EXTRA_ADVISER_SETTINGS";

    public static Adviser adviser;
    public static ArrayList<Customer> linkedCustomers = new ArrayList<>();
    public static ArrayList<User> customerAccounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adviser_dashboard_activity);
        if(getIntent().getBooleanExtra(LoginActivity.EXTRA_ADVISER_LOGIN, false)) {
            adviser = (Adviser) DataManager.getLoggedUser();
            linkedCustomers = adviser.getAssociatedCustomers();
        }
    }

    public void linkedCustomersButtonClicked(View view) {
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra(EXTRA_LINKED_CUSTOMERS, true);
        startActivity(intent);
    }

    public void searchCustomersButtonClicked(View view) {
        Intent intent;
        customerAccounts = DataManager.getInstance().searchAccount(((EditText)findViewById(R.id.searchCustomerField)).getText().toString(), true);
        intent = new Intent(this, SearchResults.class);
        intent.putExtra(EXTRA_ADVISER_SEARCHES_CUSTOMER, true);
        startActivity(intent);
    }

    public void logOutAdviserButtonClicked(View view) {
        DataManager.getInstance().logout();
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void adviserSettingsButtonClicked(View view) {
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra(EXTRA_ADVISER_CHANGES_SETTINGS, true);
        startActivity(intent);
    }
}
