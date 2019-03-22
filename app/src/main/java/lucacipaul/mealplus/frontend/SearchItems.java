package lucacipaul.mealplus.frontend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.ArrayList;

import lucacipaul.mealplus.backend.Amenities;
import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.DietLog;
import lucacipaul.mealplus.backend.DietLogEntry;
import lucacipaul.mealplus.backend.Meal;
import lucacipaul.mealplus.backend.Sellpoints;
import lucacipaul.mealplus.backend.Types;

public class SearchItems extends AppCompatActivity{

    public static final String EXTRA_RESULTS = "lucacipaul.mealplus.frontend.SearchItems.EXTRA_RESULTS";
    public static final String EXTRA_CUSTOMER = "lucacipaul.mealplus.frontend.SearchItems.EXTRA_CUSTOMER";

    String[] foodType =
    {
            "Meat", "Dairy", "Grains", "Vegetables", "Gluten-Free", "Dairy-Free",
            "Vegan", "Vegetarian", "Halal", "Kosher", "Nuts-Free", "Low-Fat",
            "Low-Sugar", "Whole Food"
    };
    String[] amenities = {"Microwave", "Cooker", "Oven"};
    String[] sellpoints =
    {
            "Waitrose", "Coop", "Sainsbury", "Asda", "Tesco", "Mark&Spencer",
            "Starbucks", "Pret", "Costa","McDonalds", "Itsu", "Abocado"
    };

    ArrayList<String> filtersList = new ArrayList<>();

    ArrayList<Types> foodTypeFilters = new ArrayList<>();
    ArrayList<Amenities> amenitiesFilters = new ArrayList<>();
    ArrayList<Sellpoints> sellpointsFilters = new ArrayList<>();

    Meal meal;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_item_activity);

        meal = (Meal) getIntent().getSerializableExtra(CustomerDashboard.EXTRA_MEAL);
        customer = getIntent().getParcelableExtra(CustomerDashboard.EXTRA_CUSTOMER);
    }


    public void searchItemButtonClicked(View view) {

        String token = ((EditText) findViewById(R.id.searchBarField)).getText().toString();

        boolean frequentlyEaten = ((ToggleButton) findViewById(R.id.searchFrequentlyEatenToggleButton)).isChecked();
        boolean selfMade = ((ToggleButton) findViewById(R.id.searchSelfMadeToggleButton)).isChecked();
        boolean recommend = ((ToggleButton) findViewById(R.id.recommendToggleButton)).isChecked();

        ArrayList<DietLogEntry> results = DataManager.getInstance().
                searchItems(
                        customer,
                        token, amenitiesFilters, foodTypeFilters, sellpointsFilters,
                        frequentlyEaten, selfMade, recommend
                );

        for(DietLogEntry result : results) {
            result.setMeal(meal);
        }

        Intent intent = new Intent(this, SearchResults.class);
        intent.putParcelableArrayListExtra(EXTRA_RESULTS, results);
        intent.putExtra(EXTRA_CUSTOMER, customer);
        startActivity(intent);
    }

    public void foodTypeButtonClicked(View view) {
        filtersList.removeAll(filtersList);
        selectFilters(foodType);
        for(String filter : filtersList) {
            if(filter.contains("-")) {
                filter = filter.replace("-", "");
            } else if(filter.contains("&")) {
                filter = filter.replace("&", "");
            } else if(filter.contains(" ")) {
                filter = filter.replace(" ", "");
            }
            foodTypeFilters.add(Types.valueOf(filter));
        }
    }

    public void amenitiesButtonClicked(View view) {
        filtersList.removeAll(filtersList);
        selectFilters(amenities);
        for(String filter : filtersList) {
            if(filter.contains("-")) {
                filter = filter.replace("-", "");
            } else if(filter.contains("&")) {
                filter = filter.replace("&", "");
            } else if(filter.contains(" ")) {
                filter = filter.replace(" ", "");
            }
            amenitiesFilters.add(Amenities.valueOf(filter));
        }
    }

    public void sellpointsButtonClicked(View view) {
        filtersList.removeAll(filtersList);
        selectFilters(sellpoints);
        for(String filter : filtersList) {
            if(filter.contains("-")) {
                filter = filter.replace("-", "");
            } else if(filter.contains("&")) {
                filter = filter.replace("&", "");
            } else if(filter.contains(" ")) {
                filter = filter.replace(" ", "");
            }
            sellpointsFilters.add(Sellpoints.valueOf(filter));
        }
    }

    public void selectFilters(final String[] filters) {
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message mesg)
            {
                throw new RuntimeException();
            }
        };

        // Boolean array for initial selected items
        final boolean[] checkedItems = new boolean[filters.length];

        builder.setMultiChoiceItems(filters, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedItems[which] = isChecked;
            }
        });

        builder.setCancelable(false);
        builder.setTitle("Your preferred search filters");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                for (int i = 0; i<checkedItems.length; i++){
                    if (checkedItems[i]) {
                        filtersList.add(filters[i]);
                    }
                }
                handler.sendMessage(handler.obtainMessage());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        try{ Looper.loop(); }
        catch(RuntimeException e) {}
    }
}
