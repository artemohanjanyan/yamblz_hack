package yamblz.hack.learning.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yamblz.hack.learning.R;
import yamblz.hack.learning.ui.cards.CardsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = new CardsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, fragment).commit();
        }
    }
}
