package yamblz.hack.learning.ui.tasks.cards;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import yamblz.hack.learning.R;
import yamblz.hack.learning.db.WordPair;
import yamblz.hack.learning.db.WordsLoader;

public class CardsFragment extends Fragment implements CardsLayout.Listener,
        LoaderManager.LoaderCallbacks<List<WordPair>> {

    private static final int WORD_N = 10;

    private View progressBar;
    private View cardView;
    private TextView textView;
    private View button;

    private List<WordPair> wordPairList;
    private int currentWord = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CardsLayout cardsLayout = (CardsLayout)
                inflater.inflate(R.layout.fragment_cards, container, false);
        cardsLayout.setListener(this);

        progressBar = cardsLayout.findViewById(R.id.fragment_cards_progress_bar);
        cardView = cardsLayout.findViewById(R.id.cardView);
        textView = (TextView) cardsLayout.findViewById(R.id.fragment_cards_text_view);
        button = cardsLayout.findViewById(R.id.fragment_cards_button);

        return cardsLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    private void inflateData() {
        progressBar.setVisibility(View.GONE);
        ObjectAnimator
                .ofFloat(cardView, "alpha", 0, 1)
                .setDuration(400)
                .start();
        textView.setText(wordPairList.get(currentWord).getFirstWord());
    }

    @Override
    public void OnRemember(CardsLayout view) {
        nextCard(view, -1);
    }

    @Override
    public void OnForgot(CardsLayout view) {
        nextCard(view, 1);
    }

    private void nextCard(CardsLayout view, int sign) {
        textView.setText(wordPairList.get((++currentWord) % wordPairList.size()).getFirstWord());
        ObjectAnimator
                .ofFloat(cardView, "translationX", sign * view.getWidth(), 0)
                .setDuration(400)
                .start();
    }

    @Override
    public Loader<List<WordPair>> onCreateLoader(int id, Bundle args) {
        return new WordsLoader(getContext(), WORD_N);
    }

    @Override
    public void onLoadFinished(Loader<List<WordPair>> loader, List<WordPair> data) {
        this.wordPairList = data;
        button.setClickable(true);
        inflateData();
    }

    @Override
    public void onLoaderReset(Loader<List<WordPair>> loader) {

    }
}
