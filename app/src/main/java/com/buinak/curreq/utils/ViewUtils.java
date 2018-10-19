package com.buinak.curreq.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class ViewUtils {
    private ViewUtils() {
    }

    public static void animateViewColourChange(View view, int colourFrom, int colourTo, int duration) {
        final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(view,
                "backgroundColor",
                new ArgbEvaluator(),
                colourFrom,
                colourTo);
        backgroundColorAnimator.setDuration(duration);
        backgroundColorAnimator.start();
    }

    public static void animateTextColourChange(TextView view, int colourFrom, int colourTo, int duration) {
        final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(view,
                "textColor",
                new ArgbEvaluator(),
                colourFrom,
                colourTo);
        backgroundColorAnimator.setDuration(duration);
        backgroundColorAnimator.start();
    }

    public static void animateCardViewColourChange(CardView view, int colourFrom, int colourTo, int duration) {
        final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(view,
                "cardBackgroundColor",
                new ArgbEvaluator(),
                colourFrom,
                colourTo);
        backgroundColorAnimator.setDuration(duration);
        backgroundColorAnimator.start();
    }
}
