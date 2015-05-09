package kidnox.uitoolkit;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import kidnox.uitoolkit.animation.RevealSupport;

import static android.os.Build.VERSION.SDK_INT;

public final class Animations {

    private final static boolean LOLLIPOP_PLUS = SDK_INT >= 21;

    /**
     * Lifting view
     *
     * @param view         The animation target
     * @param baseRotation initial Rotation X in 3D space
     * @param fromY        initial Y position of view
     * @param duration     aniamtion duration
     * @param startDelay   start delay before animation begin
     */
    public static void liftingFromBottom(View view, float baseRotation, float fromY, int duration, int startDelay) {
        view.setRotation(baseRotation);
        view.setTranslationY(fromY);

        view.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(duration)
                .setStartDelay(startDelay)
                .rotationX(0)
                .translationY(0)
                .start();

    }

    /**
     * Lifting view
     *
     * @param view         The animation target
     * @param baseRotation initial Rotation X in 3D space
     * @param duration     aniamtion duration
     * @param startDelay   start delay before animation begin
     */
    public static void liftingFromBottom(View view, float baseRotation, int duration, int startDelay) {
        view.setRotation(baseRotation);
        view.setTranslationY(view.getHeight() / 3);

        view.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(duration)
                .setStartDelay(startDelay)
                .rotationX(0)
                .translationY(0)
                .start();
    }

    /**
     * Lifting view
     *
     * @param view         The animation target
     * @param baseRotation initial Rotation X in 3D space
     * @param duration     aniamtion duration
     */
    public static void liftingFromBottom(View view, float baseRotation, int duration) {
        view.setRotation(baseRotation);
        view.setTranslationY(view.getHeight() / 3);

        view.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(duration)
                .rotationX(0)
                .translationY(0)
                .start();
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
        if (LOLLIPOP_PLUS) {
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        } else {
            return RevealSupport.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        }
    }


}
