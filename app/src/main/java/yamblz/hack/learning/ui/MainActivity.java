package yamblz.hack.learning.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import yamblz.hack.learning.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new ListFragment())
                    .commit();
        }
    }
}
