package yamblz.hack.learning.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import yamblz.hack.learning.R;
import yamblz.hack.learning.ui.tasks.cards.CardsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main, new CardsFragment())
                    .commit();
        }
    }
}
