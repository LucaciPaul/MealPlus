package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.DietLogEntry;
import lucacipaul.mealplus.backend.Report;
import lucacipaul.mealplus.backend.User;

public class SearchResults extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_VIEW_ENTRY = "lucacipaul.mealplus.frontend.SearchResults.EXTRA_VIEW_ENTRY";
    public static final String EXTRA_VIEW_REPORT = "lucacipaul.mealplus.frontend.SearchResults.EXTRA_VIEW_REPORT";
    public static final String EXTRA_CUSTOMER = "lucacipaul.mealplus.frontend.SearchResults.EXTRA_CUSTOMER";

    ListView resultsListView;
    ArrayAdapter resultsAdapter;

    public static Customer customer;
    public static Adviser adviser;
    public static ArrayList<DietLogEntry> entries = new ArrayList<>(); boolean dietLogEntries = false;
    public static ArrayList<Customer> linkedCustomers = new ArrayList<>(); boolean linkedCustomerAccounts = false;
    public static ArrayList<User> users = new ArrayList<>(); boolean userAccounts = false;
    public static ArrayList<Report> reports = new ArrayList<>(); boolean viewReports = false;
    public static DietLogEntry entry;
    public static Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_activity);

        resultsListView = (ListView)findViewById(R.id.resultsList);
        resultsListView.setOnItemClickListener(this);

        if(getIntent().getBooleanExtra(SearchItems.EXTRA_RESULTS, true)) {
            customer = SearchItems.customer;
            entries = SearchItems.results;
            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CustomerDashboard.parseItemNames(entries));
            this.dietLogEntries = true;
        } else if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_LINKED_CUSTOMERS, true)) {
            linkedCustomers = AdviserDashboard.linkedCustomers;
            ArrayList<String> results = new ArrayList<String>();
            results.add(linkedCustomers.iterator().next().getEmail());
            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
            this.linkedCustomerAccounts = true;
        } else if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_SEARCH_CUSTOMERS, true)) {
            users = AdviserDashboard.customerAccounts;
            ArrayList<String> results = new ArrayList<String>();
            results.add(users.iterator().next().getEmail());
            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
            this.userAccounts = true;
        } else if(getIntent().getBooleanExtra(CustomerDashboard.EXTRA_DISPLAY_REPORTS, true)) {
            customer = CustomerDashboard.customer;
            ArrayList<String> results = new ArrayList<String>();
            results.add(reports.iterator().next().getDietLog().getDate().toString());
            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
            this.viewReports = true;
        }

        resultsListView.setAdapter(resultsAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        if(dietLogEntries) {
            entry = entries.get(position);
            intent = new Intent(this, ViewItem.class);
            startActivity(intent);
        } else if(linkedCustomerAccounts) {
            customer = linkedCustomers.get(position);
            intent = new Intent(this, CustomerDashboard.class);
            startActivity(intent);
        } else if(userAccounts) {
            if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_SEARCH_CUSTOMERS, true)) {
                adviser = ((Adviser)DataManager.getLoggedUser());
                int response = ((Customer)users.get(position)).adviserStatus();
                if(response ==  -1) {
                    adviser.sendAssociationRequest((Customer)users.get(position));
                    Toast.makeText(getApplicationContext(), "Invitation sent!", Toast.LENGTH_LONG).show();
                } else if(response == 0) {
                    Toast.makeText(getApplicationContext(), "Another Adviser has sent a request already", Toast.LENGTH_LONG).show();
                } else {
                    if(adviser.getAssociatedCustomers().contains((Customer)users.get(position))){
                        Toast.makeText(getApplicationContext(), "This is your Customer", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "This Customer has an Adviser", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } else if(viewReports) {
            report = reports.get(position);
            intent = new Intent(this, Report.class);
            intent.putExtra(EXTRA_VIEW_REPORT, true);
            startActivity(intent);
        }
    }
}
