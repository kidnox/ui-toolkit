package kidnox.uitoolkit;

import android.animation.Animator;
import android.annotation.TargetApi;
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

    public static ViewPropertyAnimator fadeIn(View v, int duration) {
        ViewPropertyAnimator animator = makeTransparent(show(v)).animate().alpha(1f).setDuration(duration);
        animator.start();
        return animator;
    }

    public static ViewPropertyAnimator fadeOut(View v, int duration) {
        ViewPropertyAnimator animator = makeOpaque(show(v)).animate().alpha(0f).setDuration(duration);
        animator.start();
        return animator;
    }

    /**
     * Returns an Animator which can animate a clipping circle.
     * <p>
     * Any shadow cast by the View will respect the circular clip from this animator.
     * <p>
     * Only a single non-rectangular clip can be applied on a View at any time.
     * Views clipped by a circular reveal animation take priority over
     * {@link android.view.View#setClipToOutline(boolean) View Outline clipping}.
     * <p>
     * Note that the animation returned here is a one-shot animation. It cannot
     * be re-used, and once started it cannot be paused or resumed.
     *
     * @param view The View will be clipped to the animating circle.
     * @param centerX The x coordinate of the center of the animating circle.
     * @param centerY The y coordinate of the center of the animating circle.
     * @param startRadius The starting radius of the animating circle.
     * @param endRadius The ending radius of the animating circle.
     */
    @TargetApi(21)
    public static Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
        if (hasLollipopApi()) {
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        } else {
            return RevealSupport.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        }
    }


}
