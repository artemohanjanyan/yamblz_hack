package yamblz.hack.learning.ui.cards;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yamblz.hack.learning.R;

public class CardsFragment extends Fragment implements CardsLayout.Listener {

    private View cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CardsLayout cardsLayout = (CardsLayout)
                inflater.inflate(R.layout.fragment_cards, container, false);
        cardsLayout.setListener(this);

        cardView = cardsLayout.findViewById(R.id.cardView);

        return cardsLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void OnRemember(CardsLayout view) {
        Log.d(this.getClass().getSimpleName(), "remembered");
        ObjectAnimator
                .ofFloat(cardView, "translationX", -view.getWidth(), 0)
                .setDuration(400)
                .start();
    }

    @Override
    public void OnForgot(CardsLayout view) {
        Log.d(this.getClass().getSimpleName(), "forgot :(");
        ObjectAnimator
                .ofFloat(cardView, "translationX", view.getWidth(), 0)
                .setDuration(400).start();
    }
}
