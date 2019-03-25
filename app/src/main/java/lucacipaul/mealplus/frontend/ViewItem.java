package lucacipaul.mealplus.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DietLogEntry;

public class ViewItem extends AppCompatActivity implements TextWatcher {

    TextView caloriesText, carbsText, proteinsText, fatsText, nameText, authorText;
    EditText quantity;
    Button dislike, confirm;

    float calories, carbs, proteins, fats;

    public static DietLogEntry entry;
    public static Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_activity);

        nameText = (TextView)findViewById(R.id.itemName);
        authorText = (TextView)findViewById(R.id.itemAuthor);
        caloriesText = (TextView)findViewById(R.id.itemCalories);
        carbsText = (TextView)findViewById(R.id.itemCarbs);
        proteinsText = (TextView)findViewById(R.id.itemProteins);
        fatsText = (TextView)findViewById(R.id.itemFats);

        quantity = (EditText)findViewById(R.id.quantityInput);
        quantity.addTextChangedListener(this);

        dislike = (Button)findViewById(R.id.dislikeButton);
        confirm = (Button)findViewById(R.id.addToMealButton);


        if(getIntent().getBooleanExtra(SearchResults.EXTRA_VIEW_ENTRY, false)){
            entry = SearchResults.entry;
            customer =  SearchResults.customer;

            caloriesText.setText(Float.toString(entry.getEntry().getCalories()) + " / 100gr");
            carbsText.setText(Float.toString(entry.getEntry().getCarbs()) + " / 100gr");
            proteinsText.setText(Float.toString(entry.getEntry().getProteins()) + " / 100gr");
            fatsText.setText(Float.toString(entry.getEntry().getFats()) + " / 100gr");
        } else if(getIntent().getBooleanExtra(CustomerDashboard.EXTRA_VIEW_ENTRY, false)) {
            entry = CustomerDashboard.entry;
            previewItemOnly();

            setNutritionalValues(entry.getQuantity());
        } else if(getIntent().getBooleanExtra(ViewReport.EXTRA_VIEW_ENTRY, false)) {
            entry = ViewReport.entry;
            previewItemOnly();

            setNutritionalValues(entry.getQuantity());
        }

        nameText.setText(entry.getEntry().getName());
        authorText.setText("by ");
        authorText.append(entry.getEntry().getAuthor()==null? "MealPlus":entry.getEntry().getAuthor().getFirstName());
    }

    public void addToMealButtonClicked(View view) {
        if(((EditText)findViewById(R.id.quantityInput)).getText().toString().isEmpty() ||
                ((EditText)findViewById(R.id.quantityInput)).getText().toString().equalsIgnoreCase("0"))
        {
            Toast.makeText(getApplicationContext(), "Enter quantity first!", Toast.LENGTH_LONG).show();
            return;
        }

        entry.setQuantity(Float.parseFloat(((EditText)findViewById(R.id.quantityInput)).getText().toString()));
        customer.addFrequentlyEaten(entry);
        customer.getDietLog().addMealEntry(entry, entry.getMeal());

        customer.getDietLog().setCaloriesTotal(customer.getDietLog().getCaloriesTotal() + calories);
        customer.getDietLog().setCarbsTotal(customer.getDietLog().getCarbsTotal() + carbs);
        customer.getDietLog().setProteinsTotal(customer.getDietLog().getProteinsTotal() + proteins);
        customer.getDietLog().setFatsTotal(customer.getDietLog().getFatsTotal() + fats);

        CustomerDashboard.updateFields();
        CustomerDashboard.updateLists();

        finish();
        startActivity(CustomerDashboard.customerDashboardIntent);
    }

    public void dislikeButtonClicked(View view) {
        if(entry.getFood() != null) {
            customer.dislikeFood(entry.getFood());
        } else {
            customer.dislikeRecipe(entry.getRecipe());
        }
        SearchResults.removeItemEntryFromResults(entry);

        finish();
    }

    public void previewItemOnly() {
        quantity.setFocusable(false);
        quantity.setText(Float.toString(entry.getQuantity()));
        dislike.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().isEmpty())
            s = "0";
        setNutritionalValues(Float.parseFloat(s.toString()));
    }

    @Override
    public void afterTextChanged(Editable s) { }

    public void setNutritionalValues(float quantity) {
        calories = (quantity*entry.getEntry().getCalories())/100;
        carbs = (quantity*entry.getEntry().getCarbs())/100;
        proteins = (quantity*entry.getEntry().getProteins())/100;
        fats = (quantity*entry.getEntry().getFats())/100;

        caloriesText.setText(Float.toString(calories) + " / " + quantity + " gr");
        carbsText.setText(Float.toString(carbs) + " / " + quantity + " gr");
        proteinsText.setText(Float.toString(proteins) + " / " + quantity + " gr");
        fatsText.setText(Float.toString(fats) + " / " + quantity + " gr");
    }
}
