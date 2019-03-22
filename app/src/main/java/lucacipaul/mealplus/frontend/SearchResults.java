package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DietLogEntry;

public class SearchResults extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_VIEW_ENTRY = "lucacipaul.mealplus.frontend.SearchResults.EXTRA_VIEW_ENTRY";
    public static final String EXTRA_CUSTOMER = "lucacipaul.mealplus.frontend.SearchResults.EXTRA_CUSTOMER";

    ListView resultsListView;
    ArrayAdapter resultsAdapter;


    ArrayList<DietLogEntry> entries; boolean dietLogEntries = false;
    Customer customer;
    Adviser adviser;
    ArrayList<Customer> linkedCustomers; boolean linkedCustomerAccounts = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results_activity);

        resultsListView = (ListView)findViewById(R.id.resultsList);
        resultsListView.setOnItemClickListener(this);

        entries = getIntent().getParcelableArrayListExtra(SearchItems.EXTRA_RESULTS);
        linkedCustomers = getIntent().getParcelableArrayListExtra(AdviserDashboard.EXTRA_LINKED_CUSTOMERS);

        if(entries!=null) {
            resultsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CustomerDashboard.parseItemNames(entries));
            customer = getIntent().getParcelableExtra(SearchItems.EXTRA_CUSTOMER);
            this.dietLogEntries = true;
        } else if(linkedCustomers!=null) {
            ArrayList<String> results = new ArrayList<String>();
            results.add(linkedCustomers.iterator().next().getEmail());
            resultsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
            this.linkedCustomerAccounts = true;
        }

        resultsListView.setAdapter(resultsAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent;
        if(this.dietLogEntries) {
            intent = new Intent(this, ViewItem.class);
            intent.putExtra(EXTRA_VIEW_ENTRY, entries.get(position));
            intent.putExtra(EXTRA_CUSTOMER, customer);
            startActivity(intent);
        } else if(linkedCustomerAccounts) {
            intent = new Intent(this, CustomerDashboard.class);
            intent.putExtra(EXTRA_CUSTOMER, linkedCustomers.get(position));
            startActivity(intent);
        }
    }
}
