package lucacipaul.mealplus.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import lucacipaul.mealplus.backend.Customer;
import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.DietLogEntry;

public class ViewItem extends AppCompatActivity {

    TextView calories, carbs, proteins, fats, name, author;
    EditText quantity;
    Button dislike, confirm;

    public static DietLogEntry entry;
    public static Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_item_activity);

        name = (TextView)findViewById(R.id.itemName);
        author = (TextView)findViewById(R.id.itemAuthor);
        calories = (TextView)findViewById(R.id.itemCalories);
        carbs = (TextView)findViewById(R.id.itemCarbs);
        proteins = (TextView)findViewById(R.id.itemProteins);
        fats = (TextView)findViewById(R.id.itemFats);
        quantity = (EditText)findViewById(R.id.quantityInput);
        dislike = (Button)findViewById(R.id.dislikeButton);
        confirm = (Button)findViewById(R.id.addToMealButton);

        if(getIntent().getBooleanExtra(SearchResults.EXTRA_VIEW_ENTRY, false)){
            entry = SearchResults.entry;
            customer =  SearchResults.customer;
        } else if(getIntent().getBooleanExtra(CustomerDashboard.EXTRA_VIEW_ENTRY, false)) {
            entry = CustomerDashboard.entry;
            previewItemOnly();
        } else if(getIntent().getBooleanExtra(ViewReport.EXTRA_VIEW_ENTRY, false)) {
            entry = ViewReport.entry;
            previewItemOnly();
        }

        name.setText(entry.getEntry().getName());
        author.setText("by ");
        author.append(entry.getEntry().getAuthor()==null? "MealPlus":entry.getEntry().getAuthor().getFirstName());
        calories.setText(Float.toString(entry.getEntry().getCalories()));
        carbs.setText(Float.toString(entry.getEntry().getCarbs()));
        proteins.setText(Float.toString(entry.getEntry().getProteins()));
        fats.setText(Float.toString(entry.getEntry().getFats()));
    }

    public void addToMealButtonClicked(View view) {
        if(((EditText)findViewById(R.id.quantityInput)).getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter quantity first!", Toast.LENGTH_LONG).show();
            return;
        }
        entry.setQuantity(Float.parseFloat(((EditText)findViewById(R.id.quantityInput)).getText().toString()));
        customer.addFrequentlyEaten(entry);
        customer.getDietLog().addMealEntry(entry, entry.getMeal());
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
}
