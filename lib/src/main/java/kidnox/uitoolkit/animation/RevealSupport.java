package kidnox.uitoolkit.animation;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.View;

import kidnox.uitoolkit.utils.SimpleAnimatorListener;

import static kidnox.uitoolkit.Views.hasJellyBeanMR2Api;

public final class RevealSupport {

    public static Animator createCircularReveal(View view, int centerX, int centerY,
                                                float startRadius, float endRadius) {
        final RevealSupport.ViewAnimator revealLayout;
        try {
            revealLayout = (RevealSupport.ViewAnimator) view.getParent();
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("View must be inside RevealSupport.ViewAnimator.");
        }
        revealLayout.setTarget(view);
        revealLayout.setCenter(centerX, centerY);

        Rect bounds = new Rect();
        view.getHitRect(bounds);
        ValueAnimator animator = ValueAnimator.ofFloat(startRadius, endRadius);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                revealLayout.setRevealRadius((Float) animation.getAnimatedValue());
            }
        });
        animator.addListener(RevealSupport.getForPlatform(revealLayout, bounds));
        return animator;
    }

    static RevealFinishedListener getForPlatform(ViewAnimator target, Rect bounds) {
        if (hasJellyBeanMR2Api()) {
            return new RevealFinishedListener(target, bounds, View.LAYER_TYPE_HARDWARE);
        } else {
            return new RevealFinishedListener(target, bounds, View.LAYER_TYPE_SOFTWARE);
        }
    }

    static class RevealFinishedListener extends SimpleAnimatorListener {
        final Rect mInvalidateBounds;
        final int newLayerType;
        final int mLayerType;
        ViewAnimator target;

        RevealFinishedListener(ViewAnimator target, Rect bounds, int layerType) {
            this.target = target;
            this.newLayerType = layerType;
            mInvalidateBounds = bounds;
            mLayerType = target.self().getLayerType();
        }

        @Override public void onAnimationStart(android.animation.Animator animation) {
            target.self().setLayerType(newLayerType, null);
        }

        @Override public void onAnimationEnd(android.animation.Animator animation) {
            target.self().setLayerType(mLayerType, null);
            target.setClipOutlines(false);
            target.setCenter(0, 0);
            target.setTarget(null);
            target.invalidate(mInvalidateBounds);
            target = null;
        }

    }

    public interface ViewAnimator {

        void setClipOutlines(boolean clip);

        void setCenter(float cx, float cy);

        void setTarget(View target);

        void setRevealRadius(float value);

        void invalidate(Rect bounds);

        View self();

    }

}
