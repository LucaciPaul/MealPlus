package lucacipaul.mealplus.frontend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.DietLog;
import lucacipaul.mealplus.backend.DietLogEntry;
import lucacipaul.mealplus.backend.Dummy;
import lucacipaul.mealplus.backend.Meal;
import lucacipaul.mealplus.backend.Report;

public class CustomerDashboard extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    public static final String EXTRA_VIEW_ENTRY = "lucacipaul.mealplus.frontend.CustomerDashboard.EXTRA_VIEW_ENTRY";
    public static final String EXTRA_CUSTOMER_CHANGES_SETTINGS = "lucacipaul.mealplus.frontend.CustomerDashboard.EXTRA_CUSTOMER_CHANGES_SETTINGS";
    public static final String EXTRA_DISPLAY_REPORTS = "lucacipaul.mealplus.frontend.CustomerDashboard.EXTRA_DISPLAY_REPORTS";
    public static final String EXTRA_ADVISER_CHANGES_NUTRITIONAL_SETTINGS = "lucacipaul.mealplus.frontend.CustomerDashboard.EXTRA_ADVISER_CHANGES_NUTRITIONAL_SETTINGS";

    public static Customer customer;
    public static DietLogEntry entry;
    public static Meal meal;

    public static Intent customerDashboardIntent;

    public static TextView caloriesAllowed, carbsAllowed, proteinsAllowed, fatsAllowed,
                            caloriesUsed, carbsUsed, proteinsUsed, fatsUsed,
                            breakfastText, snack1Text, lunchText, snack2Text, dinnerText,
                            snack3Text;

    public static ListView breakfast, snack1, lunch, snack2, dinner, snack3;
    public static ArrayAdapter breakfastAdapter, snack1Adapter, lunchAdapter, snack2Adapter, dinnerAdapter, snack3Adapter;

    public static Context customerDashboardContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_dashboard_activity);

        customerDashboardContext = getBaseContext();
        customerDashboardIntent = getIntent();

        if(getIntent().getBooleanExtra(LoginActivity.EXTRA_CUSTOMER_LOGIN, false)) {
            customer = (Customer) DataManager.getLoggedUser();
        } else if(getIntent().getBooleanExtra(RegisterCustomer.EXTRA_CUSTOMER_FINAL, false)) {
            customer = (Customer) DataManager.getLoggedUser();
        } else if(getIntent().getBooleanExtra(SearchResults.EXTRA_ADVISER_VIEWS_CUSTOMER, false)) {
            customer = SearchResults.customer;

            ((Button)findViewById(R.id.addBreakfastButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addSnack1Button)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addLunchButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addSnack2Button)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addDinnerButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.addSnack3Button)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.closeDietLogButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.createFoodButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.createRecipeButton)).setVisibility(View.GONE);
            ((Button)findViewById(R.id.logOutCustomerButton)).setVisibility(View.GONE);
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

        breakfastText = (TextView) findViewById(R.id.breakfastText);
        snack1Text = (TextView) findViewById(R.id.snack1Text);
        lunchText = (TextView) findViewById(R.id.lunchText);
        snack2Text = (TextView) findViewById(R.id.snack2Text);
        dinnerText = (TextView) findViewById(R.id.dinnerText);
        snack3Text = (TextView) findViewById(R.id.snack3Text);

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

    public static void updateLists() {
        breakfastAdapter = new ArrayAdapter<String>(customerDashboardContext, android.R.layout.simple_list_item_1, DataManager.parseItemNames(customer.getDietLog().getBreakfast()));
        snack1Adapter = new ArrayAdapter<String>(customerDashboardContext, android.R.layout.simple_list_item_1, DataManager.parseItemNames(customer.getDietLog().getSnack1()));
        lunchAdapter = new ArrayAdapter<String>(customerDashboardContext, android.R.layout.simple_list_item_1, DataManager.parseItemNames(customer.getDietLog().getLunch()));
        snack2Adapter = new ArrayAdapter<String>(customerDashboardContext, android.R.layout.simple_list_item_1, DataManager.parseItemNames(customer.getDietLog().getSnack2()));
        dinnerAdapter = new ArrayAdapter<String>(customerDashboardContext, android.R.layout.simple_list_item_1, DataManager.parseItemNames(customer.getDietLog().getDinner()));
        snack3Adapter = new ArrayAdapter<String>(customerDashboardContext, android.R.layout.simple_list_item_1, DataManager.parseItemNames(customer.getDietLog().getSnack3()));

        breakfastText.setText("Breakfast " + customer.getDietLog().getCaloriesForMeal(Meal.Breakfast));
        snack1Text.setText("Snack1 " + customer.getDietLog().getCaloriesForMeal(Meal.SnackOne));
        lunchText.setText("Lunch " + customer.getDietLog().getCaloriesForMeal(Meal.Lunch));
        snack2Text.setText("Snack2 " + customer.getDietLog().getCaloriesForMeal(Meal.SnackTwo));
        dinnerText.setText("Dinner " + customer.getDietLog().getCaloriesForMeal(Meal.Dinner));
        snack3Text.setText("Snack3 " + customer.getDietLog().getCaloriesForMeal(Meal.SnackThree));

        breakfast.setAdapter(breakfastAdapter);
        snack1.setAdapter(snack1Adapter);
        lunch.setAdapter(lunchAdapter);
        snack2.setAdapter(snack2Adapter);
        dinner.setAdapter(dinnerAdapter);
        snack3.setAdapter(snack3Adapter);
    }
    public static void updateFields() {
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
                entry = customer.getDietLog().getBreakfast().get(position);
                break;
            case R.id.snack1List:
                entry = customer.getDietLog().getSnack1().get(position);
                break;
            case R.id.lunchList:
                entry = customer.getDietLog().getLunch().get(position);
                break;
            case R.id.snack2List:
                entry = customer.getDietLog().getSnack2().get(position);
                break;
            case R.id.dinnerList:
                entry = customer.getDietLog().getDinner().get(position);
                break;
            case R.id.snack3List:
                entry = customer.getDietLog().getSnack3().get(position);
                break;
        }

        intent.putExtra(EXTRA_VIEW_ENTRY, true);
        startActivity(intent);
    }

    public void addBreakfastButtonClicked(View view) {
        meal = Meal.Breakfast;
        Intent intent = new Intent(this, SearchItems.class);
        startActivity(intent);
    }
    public void addSnack1ButtonClicked(View view) {
        meal = Meal.SnackOne;
        Intent intent = new Intent(this, SearchItems.class);
        startActivity(intent);
    }
    public void addLunchButtonClicked(View view) {
        meal = Meal.Lunch;
        Intent intent = new Intent(this, SearchItems.class);
        startActivity(intent);
    }
    public void addSnack2ButtonClicked(View view) {
        meal = Meal.SnackTwo;
        Intent intent = new Intent(this, SearchItems.class);
        startActivity(intent);
    }
    public void addDinnerButtonClicked(View view) {
        meal = Meal.Dinner;
        Intent intent = new Intent(this, SearchItems.class);
        startActivity(intent);
    }
    public void addSnack3ButtonClicked(View view) {
        meal = Meal.SnackThree;
        Intent intent = new Intent(this, SearchItems.class);
        startActivity(intent);
    }


    public void customerSettingsButtonClicked(View view) {
        Intent intent = new Intent(this, Settings.class);
        if(getIntent().getBooleanExtra(LoginActivity.EXTRA_CUSTOMER_LOGIN, false) ||
                getIntent().getBooleanExtra(RegisterCustomer.EXTRA_CUSTOMER_FINAL, false))
        {
            intent.putExtra(EXTRA_CUSTOMER_CHANGES_SETTINGS, true);
        } else if(getIntent().getBooleanExtra(SearchResults.EXTRA_ADVISER_VIEWS_CUSTOMER, false)) {
            intent.putExtra(EXTRA_ADVISER_CHANGES_NUTRITIONAL_SETTINGS, true);
        }
        startActivity(intent);
    }

    public void closeDietLogButtonClicked(View view) {
        if(customer.getDietLog().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty diet logs will be closed by the system at midnight!", Toast.LENGTH_LONG).show();
            return;
        }

        customer.getDietLog().setClosed(true);

        Report report = new Report();
        report.setDietLog(customer.getDietLog());
        report.setCustomer(customer);
        report.setCaloriesPerDay(customer.getCaloriesPerDay());
        report.setCarbsPerDay(customer.getCarbsPerDay());
        report.setProteinsPerDay(customer.getProteinsPerDay());
        report.setFatsPerDay(customer.getFatsPerDay());
        customer.getReports().add(report);
        Dummy.reports.add(report);

        customer.setDietLog(new DietLog());

        updateFields();
        updateLists();
    }

    public void viewReportsButtonClicked(View view) {
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra(EXTRA_DISPLAY_REPORTS, true);
        startActivity(intent);
    }


    public void logOutCustomerButtonClicked(View view) {
        DataManager.getInstance().logout();
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
