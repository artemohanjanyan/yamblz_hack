package yamblz.hack.learning.ui.tasks.cards;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import yamblz.hack.learning.R;
import yamblz.hack.learning.db.WordPair;
import yamblz.hack.learning.db.WordPairUpdater;
import yamblz.hack.learning.db.WordsLoader;
import yamblz.hack.learning.ui.StartActivity;

public class CardsFragment extends Fragment implements CardsLayout.Listener,
        LoaderManager.LoaderCallbacks<List<WordPair>> {

    private static final int WORD_N = 10;

    private View loadingProgressBar;
    private View cardView;
    private TextView textView;
    private View button;
    private ImageView learnCount;
    private ProgressBar progressBar;
    private TextView translationView;

    private List<WordPair> wordPairList;
    private int currentWord = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CardsLayout cardsLayout = (CardsLayout)
                inflater.inflate(R.layout.fragment_cards, container, false);
        cardsLayout.setListener(this);

        loadingProgressBar = cardsLayout.findViewById(R.id.fragment_cards_loading_progress_bar);
        cardView = cardsLayout.findViewById(R.id.cardView);
        textView = (TextView) cardsLayout.findViewById(R.id.fragment_cards_text_view);
        button = cardsLayout.findViewById(R.id.fragment_cards_button);
        learnCount = (ImageView) cardsLayout.findViewById(R.id.fragment_cards_learn_count);
        progressBar = (ProgressBar) cardsLayout.findViewById(R.id.fragment_cards_progress_bar);
        translationView = (TextView) cardsLayout.findViewById(R.id.fragment_cards_text_translation);

        return cardsLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    private void inflateData() {
        loadingProgressBar.setVisibility(View.GONE);
        updateData();
        ObjectAnimator
                .ofFloat(cardView, "alpha", 0, 1)
                .setDuration(400)
                .start();
    }

    private void updateData() {
        textView.setText(wordPairList.get(currentWord).getFirstWord());
        int drawableId = 0;
        switch (wordPairList.get(currentWord).getLearnCount()) {
            case 0:
                drawableId = R.drawable.learnt_0;
                break;
            case 1:
                drawableId = R.drawable.learnt_1;
                break;
            case 2:
                drawableId = R.drawable.learnt_2;
                break;
            case 3:
                drawableId = R.drawable.learnt_3;
                break;
            case 4:
                Log.e(this.getClass().getSimpleName(), "max learnt count");
                drawableId = R.drawable.learnt_4;
                break;
        }
        learnCount.setImageResource(drawableId);
        progressBar.setProgress(currentWord);
        translationView.setText(wordPairList.get(currentWord).getSecondWord());
        textView.setTranslationY(0);
        translationView.setTranslationY(0);
        translationView.setAlpha(0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator1 = ObjectAnimator
                        .ofFloat(textView, "translationY", 0, -48)
                        .setDuration(300);
                ObjectAnimator animator2 = ObjectAnimator
                        .ofFloat(translationView, "translationY", 0, 48)
                        .setDuration(300);
                ObjectAnimator animator3 = ObjectAnimator
                        .ofFloat(translationView, "alpha", 0, 1)
                        .setDuration(300);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator1, animator2, animator3);
                animatorSet.start();
                button.setOnClickListener(null);
            }
        });
    }

    @Override
    public void OnRemember(CardsLayout view) {
        new WordPairUpdater(getContext()).execute(wordPairList.get(currentWord));
        nextCard(view, -1);
    }

    @Override
    public void OnForgot(CardsLayout view) {
        nextCard(view, 1);
    }

    private void nextCard(CardsLayout view, int sign) {
        if (++currentWord == wordPairList.size()) {
            Intent intent = new Intent(getContext(), StartActivity.class);
            startActivity(intent);
            return;
        }
        updateData();
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
        wordPairList = data;
        button.setClickable(true);
        inflateData();
    }

    @Override
    public void onLoaderReset(Loader<List<WordPair>> loader) {

    }
}
