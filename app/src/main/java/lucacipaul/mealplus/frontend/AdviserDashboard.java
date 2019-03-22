package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import lucacipaul.mealplus.backend.Adviser;
import lucacipaul.mealplus.backend.DataManager;

public class AdviserDashboard extends AppCompatActivity {

    public static final String EXTRA_LINKED_CUSTOMERS = "lucacipaul.mealplus.frontend.AdviserDashboard.EXTRA_LINKED_CUSTOMERS";
    public static final String EXTRA_ADVISER = "lucacipaul.mealplus.frontend.AdviserDashboard.EXTRA_ADVISER";

    Adviser adviser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adviser_dashboard_activity);

        adviser = getIntent().getParcelableExtra(LoginActivity.EXTRA_ADVISER_LOGIN);
    }

    public void linkedCustomersButtonClicked(View view) {
        Intent intent = new Intent(this, SearchResults.class);
        intent.putParcelableArrayListExtra(EXTRA_LINKED_CUSTOMERS, adviser.getAssociatedCustomers());
        startActivity(intent);
    }
    public void searchCustomersButtonClicked(View view) {
        Intent intent = new Intent(this, SearchUser.class);
        intent.putExtra(EXTRA_ADVISER, adviser);
        startActivity(intent);
    }
    public void logOutButtonClicked(View view) {
//        DataManager.getInstance().logout(adviser);
    }
}
