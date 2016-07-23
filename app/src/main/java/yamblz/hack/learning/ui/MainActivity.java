package yamblz.hack.learning.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import yamblz.hack.learning.R;
import yamblz.hack.learning.ui.tasks.cards.CardsFragment;
import yamblz.hack.learning.ui.tasks.truefalse.TrueFalseFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int num = getIntent().getIntExtra("Type", -1);
        if (num != -1) {
            switch (num) {
                case 1: getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.activity_main, new CardsFragment())
                        .commit();
                    break;
                case 6: getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.activity_main, new TrueFalseFragment())
                        .commit();

                    break;
                default: break;
            }

        }
    }

}
