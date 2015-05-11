package kidnox.uitoolkit.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import kidnox.uitoolkit.Alerts;


public class MainActivity extends ActionBarActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Alerts.setConfiguration(getConfiguration());
    }

    @Override protected void onResume() {
        super.onResume();
        Alerts.onActivityResume(this);
    }

    @Override protected void onPause() {
        super.onPause();
        Alerts.onActivityPause(this);
    }

    public void showAlert(View v) {
        Alerts.showMessage("message = " + System.currentTimeMillis());
    }

    public void showLongAlert(View v) {
        Alerts.showLongMessage("long message = "+System.currentTimeMillis());
    }

    private static Alerts.Configuration getConfiguration() {
        return new Alerts.Configuration() {
            @Override public int getLayoutRes() {
                return R.layout.alert_layout;
            }

            @Override public int getTextViewId() {
                return R.id.tv_alert;
            }

            @Override public int getParentId() {
                return R.id.parent;
            }
        };
    }

}
