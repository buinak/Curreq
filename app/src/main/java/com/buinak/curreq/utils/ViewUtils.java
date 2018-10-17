package com.buinak.curreq.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.view.View;

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
}
