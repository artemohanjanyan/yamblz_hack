package yamblz.hack.learning.ui.tasks.truefalse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import yamblz.hack.learning.R;
import yamblz.hack.learning.db.WordPair;
import yamblz.hack.learning.db.WordsLoader;
import yamblz.hack.learning.ui.StartActivity;

public class TrueFalseFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<WordPair>>, View.OnClickListener {

    private static final int WORD_N = 10;

    private TextView en, ru;

    private List<WordPair> wordPairList;
    private int currentWord = 0;
    private boolean answer = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cardsLayout = inflater.inflate(R.layout.fragment_true_false, container, false);

        en = (TextView) cardsLayout.findViewById(R.id.txt_en);
        ru = (TextView) cardsLayout.findViewById(R.id.txt_ru);

        ((Button) cardsLayout.findViewById(R.id.btn_corect)).setOnClickListener(this);
        ((Button) cardsLayout.findViewById(R.id.btn_incorect)).setOnClickListener(this);

        return cardsLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    private void inflateData() {
        en.setText(wordPairList.get(currentWord).getFirstWord());
        int r = new Random().nextInt(2);
        answer = (r > 0);
        if (answer) {
            ru.setText(wordPairList.get(currentWord).getSecondWord());
        } else {
            ru.setText("crap");
        }
    }

    @Override
    public Loader<List<WordPair>> onCreateLoader(int id, Bundle args) {
        return new WordsLoader(getContext(), WORD_N);
    }

    @Override
    public void onLoadFinished(Loader<List<WordPair>> loader, List<WordPair> data) {
        this.wordPairList = data;
        inflateData();
    }

    @Override
    public void onLoaderReset(Loader<List<WordPair>> loader) {

    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            if (answer && ((Button) view).getText().toString().equals(getString(R.string.correct)) ||
                    !answer && !((Button) view).getText().toString().equals(getString(R.string.correct))) {

                Toast toast = Toast.makeText(getActivity(),
                        "YES!", Toast.LENGTH_SHORT);
                toast.show();

            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "NO!", Toast.LENGTH_SHORT);
                toast.show();
            }
            currentWord++;
            if (currentWord == WORD_N) {
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
            } else {
                inflateData();
            }

        }
    }
}
