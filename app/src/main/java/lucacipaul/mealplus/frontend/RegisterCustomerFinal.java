package lucacipaul.mealplus.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterCustomerFinal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_customer_ok_activity);
    }

    public void logInButtonClicked(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
