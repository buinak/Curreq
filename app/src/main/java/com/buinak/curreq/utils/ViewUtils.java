package com.buinak.curreq.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

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

    //dynamically outputs the needed textsize to fit all the text in the desired width
    public static void correctTextSize(TextView textView, int desiredWidth, boolean sizingUp)
    {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setTypeface(textView.getTypeface());
        float textSize;
        if (sizingUp) {
            textSize = 100F;
        } else {
            textSize = textView.getTextSize();
        }
        paint.setTextSize(textSize);
        String text = textView.getText().toString();
        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.width() > desiredWidth)
        {
            textSize--;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}
