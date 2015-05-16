package kidnox.uitoolkit;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.*;

public final class Views {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static <T extends View> T show(T view) {
        if(view.getVisibility() != VISIBLE) {
            view.setVisibility(VISIBLE);
        }
        return view;
    }

    public static <T extends View> T hide(T view) {
        if(view.getVisibility() != INVISIBLE) {
            view.setVisibility(INVISIBLE);
        }
        return view;
    }

    public static <T extends View> T gone(T view) {
        if(view.getVisibility() != GONE) {
            view.setVisibility(GONE);
        }
        return view;
    }

    public static <T extends View> T makeTransparent(T view) {
        if(view.getAlpha() != 0f) {
            view.setAlpha(0f);
        }
        return view;
    }

    public static <T extends View> T makeOpaque(T view) {
        if(view.getAlpha() != 1f) {
            view.setAlpha(1f);
        }
        return view;
    }

    public static void applySizeViaLP(View v, int width, int height) {
        if(v.getLayoutParams() == null) {
            v.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        } else {
            v.getLayoutParams().width = width;
            v.getLayoutParams().height = height;
        }
    }

    public static ViewGroup asGroup(View v) {
        return (ViewGroup) v;
    }

    public static ViewGroup getParent(View v) {
        return (ViewGroup) v.getParent();
    }

    public static boolean allowClick() {
        return allowClick(300);
    }

    static long lastClickTime = 0;
    public static boolean allowClick(int delay) {
        long time = System.currentTimeMillis();
        if(time - lastClickTime > delay) {
            lastClickTime = time;
            return true;
        }
        return false;
    }

    public static void post(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void postDelayed(Runnable runnable, int delay) {
        HANDLER.postDelayed(runnable, delay);
    }

    public static boolean hasLollipopApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasJellyBeanMR2Api() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean hasJellyBeanApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

}
