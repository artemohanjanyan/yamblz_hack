package yamblz.hack.learning.ui.tasks.cards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import yamblz.hack.learning.R;

public class CardsLayout extends FrameLayout {

    private Listener listener;
    @IdRes private int swipeViewId;
    private View swipeView;
    @IdRes private int rememberViewId;
    private View rememberView;
    @IdRes private int forgotViewId;
    private View forgotView;

    private float layoutWidth;
    private SwipeDetector swipeDetector;
    private GestureDetector gestureDetector;

    public CardsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CardsLayout, 0, 0);
        swipeViewId = typedArray.getResourceId(R.styleable.CardsLayout_swipe_view_id, 0);
        rememberViewId = typedArray.getResourceId(R.styleable.CardsLayout_remember_view_id, 0);
        forgotViewId = typedArray.getResourceId(R.styleable.CardsLayout_forgot_view_id, 0);
        reset();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private void reset() {
        swipeDetector = new SwipeDetector();
        gestureDetector = new GestureDetector(getContext(), swipeDetector);
    }

    private void animatedViewReset() {
        reset();

        ObjectAnimator animator0 = ObjectAnimator
                .ofFloat(swipeView, "translationX", swipeView.getTranslationX(), 0)
                .setDuration(400);

        ObjectAnimator animator1 = ObjectAnimator
                .ofFloat(swipeView, "rotation", swipeView.getRotation(), 0)
                .setDuration(400);

        ObjectAnimator animator2 = ObjectAnimator
                .ofFloat(rememberView, "alpha", rememberView.getAlpha(), 0)
                .setDuration(400);

        ObjectAnimator animator3 = ObjectAnimator
                .ofFloat(forgotView, "alpha", forgotView.getAlpha(), 0)
                .setDuration(400);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator0, animator1, animator2, animator3);
        animatorSet.start();
    }

    private void viewReset() {
        reset();
        swipeView.setTranslationX(0);
        swipeView.setRotation(0);
        rememberView.setAlpha(0);
        forgotView.setAlpha(0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        swipeView = findViewById(swipeViewId);
        rememberView = findViewById(rememberViewId);
        forgotView = findViewById(forgotViewId);

        layoutWidth = right - left;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(this.getClass().getSimpleName(), "onInterceptTouchEvent");
        gestureDetector.onTouchEvent(event);
        if (swipeDetector.isScrolled) {
            //noinspection RedundantIfStatement
            if (swipeDetector.isXDirection) {
                Log.d(this.getClass().getSimpleName(), "intercepted");
                swipeDetector.beginSwiping();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(this.getClass().getSimpleName(), "canceling");
                if (!swipeDetector.isFading) {
                    animatedViewReset();
                }
                break;
        }
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public interface Listener {
        void OnRemember(CardsLayout cardsLayout);
        void OnForgot(CardsLayout cardsLayout);
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        boolean isScrolled = false;
        boolean isXDirection;
        boolean isSwiping = false;
        boolean isFading = false;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!isSwiping) {
                isScrolled = true;
                isXDirection = Math.abs(distanceX) > Math.abs(distanceY);
            } else if (!isFading) {
                float ratio = swipeView.getTranslationX() / layoutWidth;

                float oldTranslation = swipeView.getTranslationX();
                swipeView.setTranslationX(oldTranslation - distanceX);
                swipeView.setRotation(swipeView.getTranslationX() / layoutWidth * 90);

                final float START_ALPHA = (float) 0.05;
                final float FINISH_ALPHA = (float) 0.3;

                final boolean isRightDirection = ratio > 0;
                float directionSign = isRightDirection ? 1 : -1;
                View indicatorView = isRightDirection ? rememberView : forgotView;
                ratio = Math.abs(ratio);

                if (ratio > START_ALPHA) {
                    indicatorView.setAlpha((ratio - START_ALPHA) / (FINISH_ALPHA - START_ALPHA));
                } else {
                    indicatorView.setAlpha(0);
                }

                if (ratio > FINISH_ALPHA) {
                    ObjectAnimator animator0 = ObjectAnimator
                            .ofFloat(swipeView, "rotation",
                                    swipeView.getRotation(), 90 * directionSign)
                            .setDuration(400);

                    ObjectAnimator animator1 = ObjectAnimator
                            .ofFloat(swipeView, "translationX",
                                    swipeView.getTranslationX(), layoutWidth * directionSign)
                            .setDuration(400);


                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(animator0, animator1);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            viewReset();
                            if (listener != null) {
                                if (isRightDirection) {
                                    listener.OnRemember(CardsLayout.this);
                                } else {
                                    listener.OnForgot(CardsLayout.this);
                                }
                            }
                        }
                    });
                    animatorSet.start();

                    isFading = true;
                }
            }
            return true;
        }

        private void beginSwiping() {
            isSwiping = true;
        }
    }
}
