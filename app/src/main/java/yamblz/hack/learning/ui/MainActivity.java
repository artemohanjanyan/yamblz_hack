package yamblz.hack.learning.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import yamblz.hack.learning.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title);
        if (getFragmentManager().findFragmentById(R.id.fragment_content) == null) {
            Fragment fragment = ListFragment.newInstance();
            FragmentTransaction fTrans = getFragmentManager().beginTransaction();
            fTrans.add(R.id.fragment_content, fragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
        }
    }
}
