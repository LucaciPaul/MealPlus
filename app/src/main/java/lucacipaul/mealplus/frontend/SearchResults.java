package lucacipaul.mealplus.frontend;

import android.content.Context;
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
    public static final String EXTRA_ADVISER_VIEWS_CUSTOMER = "lucacipaul.mealplus.frontend.SearchResults.EXTRA_ADVISER_VIEW_CUSTOMER";

    private static ListView resultsListView;
    private static ArrayAdapter resultsAdapter;

    private static Context searchResultsContext;

    public static Customer customer;
    public static Adviser adviser;
    public static ArrayList<DietLogEntry> entries = new ArrayList<>();
    public static ArrayList<Customer> linkedCustomers = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static DietLogEntry entry;
    public static Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_activity);

        searchResultsContext = getBaseContext();

        resultsListView = (ListView)findViewById(R.id.resultsList);
        resultsListView.setOnItemClickListener(this);

        if(getIntent().getBooleanExtra(SearchItems.EXTRA_SEARCH_ITEMS, false)) {
            customer = SearchItems.customer;
            entries = SearchItems.results;
            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, DataManager.parseItemNames(entries));
        } else if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_LINKED_CUSTOMERS, false)) {
            linkedCustomers = AdviserDashboard.linkedCustomers;
            ArrayList<String> results = new ArrayList<String>();

            for(User user : linkedCustomers) {
                results.add(user.getEmail());
            }
            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        } else if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_ADVISER_SEARCHES_CUSTOMER, false)) {
            users = AdviserDashboard.customerAccounts;
            ArrayList<String> results = new ArrayList<String>();

            for(User user : users) {
                results.add(user.getEmail());
            }

            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        } else if(getIntent().getBooleanExtra(CustomerDashboard.EXTRA_DISPLAY_REPORTS, false)) {
            customer = CustomerDashboard.customer;
            ArrayList<String> results = new ArrayList<String>();

            for(Report report : customer.getReports()) {
                results.add(report.getDietLog().getDate().toString());
            }

            resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        }

        resultsListView.setAdapter(resultsAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        if(getIntent().getBooleanExtra(SearchItems.EXTRA_SEARCH_ITEMS, false)) {
            entry = entries.get(position);
            displayItem();
        } else if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_LINKED_CUSTOMERS, false)) {
            customer = linkedCustomers.get(position);
            displayCustomer();
        } else if(getIntent().getBooleanExtra(AdviserDashboard.EXTRA_ADVISER_SEARCHES_CUSTOMER, false)) {
            adviser = ((Adviser)DataManager.getLoggedUser());
            int response = ((Customer)users.get(position)).adviserStatus();
            if(response ==  -1) {
                adviser.sendAssociationRequest((Customer)users.get(position));
                Toast.makeText(getApplicationContext(), "Invitation sent!", Toast.LENGTH_LONG).show();
            } else if(response == 0) {
                Toast.makeText(getApplicationContext(), "Another Adviser has sent a request already", Toast.LENGTH_LONG).show();
            } else {
                if(adviser.getAssociatedCustomers().contains((Customer)users.get(position))){
                    customer = (Customer)users.get(position);
                    displayCustomer();
                } else {
                    Toast.makeText(getApplicationContext(), "This Customer has an Adviser", Toast.LENGTH_LONG).show();
                }
            }
        } else if(getIntent().getBooleanExtra(CustomerDashboard.EXTRA_DISPLAY_REPORTS, false)) {
            report = customer.getReports().get(position);
            displayReport();
        }
    }

    public void displayItem() {
        Intent intent = new Intent(this, ViewItem.class);
        intent.putExtra(EXTRA_VIEW_ENTRY, true);
        startActivity(intent);
    }

    public void displayCustomer() {
        Intent intent = new Intent(this, CustomerDashboard.class);
        intent.putExtra(EXTRA_ADVISER_VIEWS_CUSTOMER, true);
        startActivity(intent);
    }

    public void displayReport() {
        Intent intent = new Intent(this, ViewReport.class);
        intent.putExtra(EXTRA_VIEW_REPORT, true);
        startActivity(intent);
    }

    public static void removeItemEntryFromResults(DietLogEntry entry) {
        entries.remove(entry);
        resultsAdapter = new ArrayAdapter<>(searchResultsContext, android.R.layout.simple_list_item_1, DataManager.parseItemNames(entries));
        resultsListView.setAdapter(resultsAdapter);
    }

}
