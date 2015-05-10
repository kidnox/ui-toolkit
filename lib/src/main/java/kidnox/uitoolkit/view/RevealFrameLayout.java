package kidnox.uitoolkit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import kidnox.uitoolkit.animation.RevealSupport;

public class RevealFrameLayout extends FrameLayout implements RevealSupport.ViewAnimator {

    final Path mRevealPath = new Path();

    boolean mClipOutlines;

    float mCenterX;
    float mCenterY;
    float mRadius;

    View mTarget;

    public RevealFrameLayout(Context context) {
        super(context);
    }

    public RevealFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RevealFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public void setTarget(View view) {
        mTarget = view;
    }

    @Override public void setCenter(float centerX, float centerY) {
        mCenterX = centerX;
        mCenterY = centerY;
    }

    @Override public void setClipOutlines(boolean clip) {
        mClipOutlines = clip;
    }

    @Override public void setRevealRadius(float radius) {
        mRadius = radius;
        invalidate();
    }

    @Override public View self() {
        return this;
    }

    @Override protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (!mClipOutlines && child != mTarget)
            return super.drawChild(canvas, child, drawingTime);

        final int state = canvas.save();

        mRevealPath.reset();
        mRevealPath.addCircle(mCenterX, mCenterY, mRadius, Path.Direction.CW);

        canvas.clipPath(mRevealPath);

        boolean isInvalided = super.drawChild(canvas, child, drawingTime);

        canvas.restoreToCount(state);

        return isInvalided;
    }

}
