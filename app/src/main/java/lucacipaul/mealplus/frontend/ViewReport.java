package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lucacipaul.mealplus.backend.DataManager;
import lucacipaul.mealplus.backend.DietLogEntry;
import lucacipaul.mealplus.backend.Report;

public class ViewReport extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String EXTRA_VIEW_ENTRY = "lucacipaul.mealplus.frontend.ViewReport.EXTRA_VIEW_ENTRY";

    TextView caloriesAllowed, carbsAllowed, proteinsAllowed, fatsAllowed;
    TextView caloriesUsed, carbsUsed, proteinsUsed, fatsUsed;

    ListView breakfast, snack1, lunch, snack2, dinner, snack3;
    ArrayAdapter breakfastAdapter, snack1Adapter, lunchAdapter, snack2Adapter, dinnerAdapter, snack3Adapter;

    Report report;
    public static DietLogEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_activity);

        report = SearchResults.report;

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
        breakfastAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DataManager.parseItemNames(report.getDietLog().getBreakfast()));
        snack1Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DataManager.parseItemNames(report.getDietLog().getSnack1()));
        lunchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DataManager.parseItemNames(report.getDietLog().getLunch()));
        snack2Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DataManager.parseItemNames(report.getDietLog().getSnack2()));
        dinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DataManager.parseItemNames(report.getDietLog().getDinner()));
        snack3Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, DataManager.parseItemNames(report.getDietLog().getSnack3()));

        breakfast.setAdapter(breakfastAdapter);
        snack1.setAdapter(snack1Adapter);
        lunch.setAdapter(lunchAdapter);
        snack2.setAdapter(snack2Adapter);
        dinner.setAdapter(dinnerAdapter);
        snack3.setAdapter(snack3Adapter);
    }

    private void updateFields() {
        caloriesAllowed.setText(Float.toString(report.getCaloriesPerDay()));
        caloriesUsed.setText(Float.toString(report.getDietLog().getCaloriesTotal()));
        carbsAllowed.setText(Float.toString(report.getCarbsPerDay()));
        carbsUsed.setText(Float.toString(report.getDietLog().getCarbsTotal()));
        proteinsAllowed.setText(Float.toString(report.getProteinsPerDay()));
        proteinsUsed.setText(Float.toString(report.getDietLog().getProteinsTotal()));
        fatsAllowed.setText(Float.toString(report.getFatsPerDay()));
        fatsUsed.setText(Float.toString(report.getDietLog().getFatsTotal()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ViewItem.class);

        switch(parent.getId()) {
            case R.id.breakfastList:
                entry = report.getDietLog().getBreakfast().get(position);
                break;
            case R.id.snack1List:
                entry = report.getDietLog().getSnack1().get(position);
                break;
            case R.id.lunchList:
                entry = report.getDietLog().getLunch().get(position);
                break;
            case R.id.snack2List:
                entry = report.getDietLog().getSnack2().get(position);
                break;
            case R.id.dinnerList:
                entry = report.getDietLog().getDinner().get(position);
                break;
            case R.id.snack3List:
                entry = report.getDietLog().getSnack3().get(position);
                break;
        }

        intent.putExtra(EXTRA_VIEW_ENTRY, true);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
