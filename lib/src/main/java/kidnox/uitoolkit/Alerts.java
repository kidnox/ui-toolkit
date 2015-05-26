package kidnox.uitoolkit;

import android.animation.Animator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kidnox.uitoolkit.utils.SimpleAnimatorListener;

import static kidnox.uitoolkit.Views.*;

public final class Alerts {

    private static Configuration configuration;
    private static Activity currentContext;
    private static Alert currentAlert;
    private static Alert pendingAlert;

    public static void setConfiguration(@NonNull Configuration _configuration) {
        configuration = _configuration;
    }

    private static Configuration getConfiguration() {
        if(configuration == null) {
            throw new NullPointerException("null configuration, you must call setConfiguration first");
        }
        return configuration;
    }

    private static boolean isActive() {
        return currentContext != null;
    }

    public static void onActivityResume(@NonNull Activity activity) {
        currentContext = activity;
    }

    public static void onActivityPause(@NonNull Activity activity) {
        if(activity == currentContext) currentContext = null;
    }

    public static void cancel() {
        if(isActive() && currentAlert != null) {
            currentAlert.hide(getConfiguration().getParentLayout(currentContext));
        }
    }

    public static void showMessage(final String message) {
        showMessage(message, false);
    }

    public static void showMessage(@StringRes int message) {
        if(isActive()) {
            showMessage(currentContext.getString(message), false);
        }
    }

    public static void showLongMessage(String message) {
        showMessage(message, true);
    }

    public static void showLongMessage(@StringRes int message) {
        if(isActive()) {
            showMessage(currentContext.getString(message), true);
        }
    }

    private static void showMessage(final String message, final boolean isLong) {
        post(new Runnable() {
            @Override public void run() {
                if(currentAlert == null) {
                    new Alert(message, isLong).show();
                } else if (pendingAlert == null){
                    pendingAlert = new Alert(message, isLong);
                } //else ignore
            }
        });
    }

    private static class Alert {
        final String message;
        final boolean isLong;
        private View alertView;

        private Alert(String message, boolean isLong) {
            this.message = message;
            this.isLong = isLong;
        }

        public void show() {
            if(!isActive()) {
                cancel();
                return;
            }
            final ViewGroup parent = getConfiguration().getParentLayout(currentContext);
            if(parent == null) {
                cancel();
                return;
            }
            currentAlert = this;
            alertView = getConfiguration().getLayout(parent);
            getConfiguration().setMessage(alertView, message);
            parent.addView(Views.hide(alertView));
            post(new Runnable() {
                @Override public void run() {
                    if(alertView != null) {
                        getConfiguration().animateAppearing(Views.show(alertView));
                    } else {
                        cancel();
                    }
                }
            });
            postDelayed(new Runnable() {
                @Override public void run() {
                    hide(parent);
                }
            }, isLong ? getConfiguration().getLongDuration() : getConfiguration().getDefaultDuration());
        }

        public void hide(final ViewGroup parent) {
            if(alertView == null || parent == null || alertView.getParent() == null) {
                cancel();
                return;
            }
            getConfiguration().animateDisappearing(alertView, new SimpleAnimatorListener() {
                @Override public void onAnimationEnd(Animator animation) {
                    if(alertView != null) {
                        parent.removeView(alertView);
                    }
                    cancel();
                }
            });
        }

        void cancel() {
            alertView = null;
            if(this == currentAlert) {
                currentAlert = null;
            }
            if(pendingAlert != null) {
                Alert temp = pendingAlert;
                pendingAlert = null;
                temp.show();
            }
        }
    }

    public static abstract class Configuration {
        public int getLayoutRes() {
            return -1;
        }

        public int getTextViewId() {
            return -1;
        }

        public int getParentId() {
            return -1;
        }

        public int getDefaultDuration() {
            return 3000;
        }

        public int getLongDuration() {
            return 5000;
        }

        public ViewGroup getParentLayout(@NonNull Activity activity) {
            int id = getParentId();
            if(id < 0) throw new IllegalArgumentException("you must override getParentId() or getParentLayout()");
            try {
                return (ViewGroup) activity.findViewById(id);
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException("parent view must be a ViewGroup");
            }
        }

        public View getLayout(@NonNull ViewGroup parent) {
            int res = getLayoutRes();
            if(res < 0) throw new IllegalArgumentException("you must override getLayoutRes() or getLayout()");
            return LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        }

        public void setMessage(@NonNull View alert, String message) {
            int res = getTextViewId();
            if(res < 0) throw new IllegalArgumentException("you must override getTextViewId() or setMessage()");
            TextView textView = (TextView) alert.findViewById(res);
            textView.setText(message);
        }

        public int getAnimationDuration() {
            return 250;
        }

        public void animateAppearing(@NonNull View view) {
            view.setTranslationY(-view.getHeight());
            view.animate()
                    .translationY(0)
                    .setDuration(getAnimationDuration())
                    .start();
        }

        public void animateDisappearing(@NonNull View view, Animator.AnimatorListener listener) {
            view.animate()
                    .translationY(-view.getHeight())
                    .setDuration(getAnimationDuration())
                    .setListener(listener)
                    .start();
        }

    }

}
