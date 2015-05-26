package kidnox.uitoolkit;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;

import kidnox.uitoolkit.animation.RevealSupport;

import static kidnox.uitoolkit.Views.*;

public final class Animations {

    private static int defAnimDuration = 300;

    public static void setDefaultAnimationDuration(int duration) {
        if(duration <= 0) throw new IllegalArgumentException();
        defAnimDuration = duration;
    }

    public static ViewPropertyAnimator fadeIn(View v) {
        return fadeIn(v, defAnimDuration);
    }

    public static ViewPropertyAnimator fadeOut(View v) {
        return fadeOut(v, defAnimDuration);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static ViewPropertyAnimator fadeIn(View v, int duration) {
        ViewPropertyAnimator animator = makeTransparent(show(v)).animate().alpha(1f).setDuration(duration);
        if(Info.hasJellyBeanMR2Api()) {
            animator.withLayer();
        }
        animator.start();
        return animator;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static ViewPropertyAnimator fadeOut(View v, int duration) {
        ViewPropertyAnimator animator = makeOpaque(show(v)).animate().alpha(0f).setDuration(duration);
        if(Info.hasJellyBeanMR2Api()) {
            animator.withLayer();
        }
        animator.start();
        return animator;
    }

    @TargetApi(21)
    public static Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
        if (Info.hasLollipopApi()) {
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        } else {
            return RevealSupport.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        }
    }


}
