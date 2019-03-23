package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.DietLogEntry;
import lucacipaul.mealplus.backend.Meal;

public class CustomerDashboard extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    public static final String EXTRA_VIEW_ENTRY = "lucacipaul.mealplus.frontend.CustomerDashboard.EXTRA_VIEW_ENTRY";
    public static final String EXTRA_MEAL = "lucacipaul.mealplus.frontend.CustomerDashboard.EXTRA_MEAL";
    public static final String EXTRA_CUSTOMER = "lucacipaul.mealplus.frontend.CustomerDashboard.EXTRA_CUSTOMER";

    public static Customer customer;

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
            customer = (Customer) DataManager.getLoggedUser(); // getIntent().getParcelableExtra(LoginActivity.EXTRA_CUSTOMER_LOGIN);
        }
        if (customer == null) {
            customer = getIntent().getParcelableExtra(SearchResults.EXTRA_CUSTOMER);

            ((Button)findViewById(R.id.addBreakfastButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addSnack1Button)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addLunchButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addSnack2Button)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addDinnerButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addSnack3Button)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.closeDietLogButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.createFoodButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.createRecipeButton)).setVisibility(View.GONE);
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
        breakfast.setOnItemClickListener(this);
        snack1.setOnItemClickListener(this);
        lunch.setOnItemClickListener(this);
        snack2.setOnItemClickListener(this);
        dinner.setOnItemClickListener(this);
        snack3.setOnItemClickListener(this);
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

    public static ArrayList<String> parseItemNames(ArrayList<DietLogEntry> items) {
        ArrayList<String> itemNames = new ArrayList<String>();

        for(DietLogEntry item : items) {
            itemNames.add(item.getFood() != null? "Food: " + item.getEntry().getName():"Recipe: " + item.getEntry().getName());
        }

        return itemNames;
    }

    private void updateFields() {
        caloriesAllowed.setText(Float.toString(customer.getCaloriesPerDay()));
        caloriesUsed.setText(Float.toString(customer.getDietLog().getCaloriesTotal()));
        carbsAllowed.setText(Float.toString(customer.getCarbsPerDay()));
        carbsUsed.setText(Float.toString(customer.getDietLog().getCarbsTotal()));
        proteinsAllowed.setText(Float.toString(customer.getProteinsPerDay()));
        proteinsUsed.setText(Float.toString(customer.getDietLog().getProteinsTotal()));
        fatsAllowed.setText(Float.toString(customer.getFatsPerDay()));
        fatsUsed.setText(Float.toString(customer.getDietLog().getFatsTotal()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewItem.class);
        switch(parent.getId()) {
            case R.id.breakfastList:
                intent.putExtra(EXTRA_VIEW_ENTRY, customer.getDietLog().getBreakfast().get(position));
                startActivity(intent);
                break;
            case R.id.snack1List:
                intent.putExtra(EXTRA_VIEW_ENTRY, customer.getDietLog().getSnack1().get(position));
                startActivity(intent);
                break;
            case R.id.lunchList:
                intent.putExtra(EXTRA_VIEW_ENTRY, customer.getDietLog().getLunch().get(position));
                startActivity(intent);
                break;
            case R.id.snack2List:
                intent.putExtra(EXTRA_VIEW_ENTRY, customer.getDietLog().getSnack2().get(position));
                startActivity(intent);
                break;
            case R.id.dinnerList:
                intent.putExtra(EXTRA_VIEW_ENTRY, customer.getDietLog().getDinner().get(position));
                startActivity(intent);
                break;
            case R.id.snack3List:
                intent.putExtra(EXTRA_VIEW_ENTRY, customer.getDietLog().getSnack3().get(position));
                startActivity(intent);
                break;
        }
    }

    public static Meal meal;
    public void addBreakfastButtonClicked(View view) {
        meal = Meal.Breakfast;
        Intent intent = new Intent(this, SearchItems.class);
        intent.putExtra(EXTRA_MEAL, Meal.Breakfast);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        startActivity(intent);
    }
    public void addSnack1ButtonClicked(View view) {
        Intent intent = new Intent(this, SearchItems.class);
        intent.putExtra(EXTRA_MEAL, Meal.SnackOne);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        startActivity(intent);
    }
    public void addLunchButtonClicked(View view) {
        Intent intent = new Intent(this, SearchItems.class);
        intent.putExtra(EXTRA_MEAL, Meal.Lunch);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        startActivity(intent);
    }
    public void addSnack2ButtonClicked(View view) {
        Intent intent = new Intent(this, SearchItems.class);
        intent.putExtra(EXTRA_MEAL, Meal.SnackTwo);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        startActivity(intent);
    }
    public void addDinnerButtonClicked(View view) {
        Intent intent = new Intent(this, SearchItems.class);
        intent.putExtra(EXTRA_MEAL, Meal.Dinner);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        startActivity(intent);
    }
    public void addSnack3ButtonClicked(View view) {
        Intent intent = new Intent(this, SearchItems.class);
        intent.putExtra(EXTRA_MEAL, Meal.SnackThree);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        startActivity(intent);
    }
}
