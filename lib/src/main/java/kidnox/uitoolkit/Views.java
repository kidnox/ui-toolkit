package kidnox.uitoolkit;

import android.view.View;

public final class Views {

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

}
