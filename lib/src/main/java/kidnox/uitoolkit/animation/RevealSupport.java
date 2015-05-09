package kidnox.uitoolkit.animation;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.view.View;

import kidnox.uitoolkit.utils.SimpleAnimationListener;

import static android.os.Build.VERSION.SDK_INT;

public final class RevealSupport {

    public static Animator createCircularReveal(View view, int centerX, int centerY,
                                                float startRadius, float endRadius) {
        RevealSupport.ViewAnimator revealLayout;
        try {
            revealLayout = (RevealSupport.ViewAnimator) view.getParent();
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("View must be inside RevealAnimator.");
        }
        revealLayout.setTarget(view);
        revealLayout.setCenter(centerX, centerY);

        Rect bounds = new Rect();
        view.getHitRect(bounds);
        ObjectAnimator reveal = ObjectAnimator.ofFloat(revealLayout, "revealRadius", startRadius, endRadius);
        reveal.addListener(RevealSupport.getForPlatform(revealLayout, bounds, SDK_INT));
        return reveal;
    }

    static RevealFinishedListener getForPlatform(ViewAnimator target, Rect bounds, int platform) {
        if(platform >= 18) { //JB_MR2
            return new RevealFinishedListener(target, bounds, View.LAYER_TYPE_HARDWARE);
        } else {
            return new RevealFinishedListener(target, bounds, View.LAYER_TYPE_SOFTWARE);
        }
    }

    static class RevealFinishedListener extends SimpleAnimationListener {
        final Rect mInvalidateBounds;
        final int newLayerType;
        ViewAnimator target;

        int mLayerType = -1;

        RevealFinishedListener(ViewAnimator target, Rect bounds, int layerType) {
            this.target = target;
            this.newLayerType = layerType;
            mInvalidateBounds = bounds;
            if (target.self().getLayerType() != layerType) {
                mLayerType = target.self().getLayerType();
            }
        }

        @Override public void onAnimationStart(android.animation.Animator animation) {
            super.onAnimationStart(animation);
            if(mLayerType > 0) {
                target.self().setLayerType(newLayerType, null);
            }
        }

        @Override public void onAnimationEnd(android.animation.Animator animation) {
            super.onAnimationEnd(animation);
            if (mLayerType > 0) {
                target.self().setLayerType(mLayerType, null);
            }
            if (target.self().getWindowToken() == null) {
                target = null;
                return;
            }
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

        float getRevealRadius();

        void invalidate(Rect bounds);

        View self();

    }

}
