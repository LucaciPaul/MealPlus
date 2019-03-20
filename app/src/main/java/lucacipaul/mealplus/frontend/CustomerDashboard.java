package lucacipaul.mealplus.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DietLogEntry;

public class CustomerDashboard extends AppCompatActivity {

    Customer customer;

    TextView caloriesAllowed, carbsAllowed, proteinsAllowed, fatsAllowed;
    TextView caloriesUsed, carbsUsed, proteinsUsed, fatsUsed;

    ListView breakfast, snack1, lunch, snack2, dinner, snack3;
    ArrayAdapter breakfastAdapter, snack1Adapter, lunchAdapter, snack2Adapter, dinnerAdapter, snack3Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_dashboard_activity);

        customer = getIntent().getParcelableExtra(RegisterCustomer.EXTRA_CUSTOMER_FINAL);
        if(customer == null) {
            customer = getIntent().getParcelableExtra(LoginActivity.EXTRA_CUSTOMER_LOGIN);
        }

        caloriesAllowed = (TextView) findViewById(R.id.totalCaloriesAllowed);
        caloriesUsed = (TextView) findViewById(R.id.caloriesUsage);
        carbsAllowed = (TextView) findViewById(R.id.totalCarbsAllowed);
        carbsUsed = (TextView) findViewById(R.id.carbsUsage);
        proteinsAllowed = (TextView) findViewById(R.id.totalProteinsAllowed);
        proteinsUsed = (TextView) findViewById(R.id.proteinsUsage);
        fatsAllowed = (TextView) findViewById(R.id.totalFatsAllowed);
        fatsUsed = (TextView) findViewById(R.id.fatsUsage);
        updateFields();

        breakfast = (ListView) findViewById(R.id.breakfastList);
        snack1 = (ListView) findViewById(R.id.snack1List);
        lunch = (ListView) findViewById(R.id.lunchList);
        snack2 = (ListView) findViewById(R.id.snack2List);
        dinner = (ListView) findViewById(R.id.dinnerList);
        snack3 = (ListView) findViewById(R.id.snack3List);
        updateLists();
    }

    private void updateLists() {
        breakfastAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parseItemNames(customer.getDietLog().getBreakfast()));
        snack1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parseItemNames(customer.getDietLog().getSnack1()));
        lunchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parseItemNames(customer.getDietLog().getLunch()));
        snack2Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parseItemNames(customer.getDietLog().getSnack2()));
        dinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parseItemNames(customer.getDietLog().getDinner()));
        snack3Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, parseItemNames(customer.getDietLog().getSnack3()));

        breakfast.setAdapter(breakfastAdapter);
        snack1.setAdapter(snack1Adapter);
        lunch.setAdapter(lunchAdapter);
        snack2.setAdapter(snack2Adapter);
        dinner.setAdapter(dinnerAdapter);
        snack3.setAdapter(snack3Adapter);
    }

    private void updateFields() {
        caloriesAllowed.setText(Float.toString(customer.getCaloriesPerDay()));
        caloriesUsed.setText(Float.toString(customer.getDietLog().getCaloriesTotal()));
        carbsAllowed.setText(Float.toString(customer.getCarbsPerDay()));
        carbsAllowed.setText(Float.toString(customer.getDietLog().getCarbsTotal()));
        proteinsAllowed.setText(Float.toString(customer.getProteinsPerDay()));
        proteinsUsed.setText(Float.toString(customer.getDietLog().getProteinsTotal()));
        fatsAllowed.setText(Float.toString(customer.getFatsPerDay()));
        fatsUsed.setText(Float.toString(customer.getDietLog().getProteinsTotal()));
    }

    private ArrayList<String> parseItemNames(ArrayList<DietLogEntry> entries){
        ArrayList<String> itemNames = new ArrayList<String>();

        for(DietLogEntry entry : entries) {
            itemNames.add(entry.getEntry().getName());
        }

        return itemNames;
    }
}
