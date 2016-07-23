package yamblz.hack.learning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import yamblz.hack.learning.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            String s = ((Button) view).getText().toString();
            int num = 0;
            if (s.equals(getString(R.string.ex1))) num = 1;
            if (s.equals(getString(R.string.ex2))) num = 2;
            if (s.equals(getString(R.string.ex3))) num = 3;
            if (s.equals(getString(R.string.ex4))) num = 4;
            if (s.equals(getString(R.string.ex5))) num = 5;
            if (s.equals(getString(R.string.ex6))) num = 6;
            if (s.equals(getString(R.string.ex7))) num = 7;
            if (s.equals(getString(R.string.ex8))) num = 8;
            Intent start = new Intent(this, MainActivity.class);
            start.putExtra("Type", num);
            startActivity(start);
        }
    }
}
