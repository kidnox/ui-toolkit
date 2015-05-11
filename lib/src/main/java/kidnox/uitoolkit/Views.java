package kidnox.uitoolkit;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

public final class Views {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static <T extends View> T show(T view) {
        if(view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public static <T extends View> T hide(T view) {
        if(view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public static <T extends View> T gone(T view) {
        if(view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
        return view;
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
