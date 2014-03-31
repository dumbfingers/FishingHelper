package com.yeyaxi.android.fishinghelper;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.actionbarsherlock.view.MenuItem;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @author yaxi
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public DrawerLayout mDrawerLayout;
    private View mSwitchView;
//    private View mainView;

    private RadioGroup radioUnit;
    private RadioButton radioImperial;
    private RadioButton radioMetric;

//    private Button addRecordButton;

    public ActionBarDrawerToggle mDrawerToggle;

    private boolean isMetricUnit = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MainFragment(), "MainFragment")
                .commit();

//        addRecordButton = (Button) findViewById(R.id.button_new_record);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // we will swap this view on the fly later
//        mainView = (View) findViewById(R.id.content_frame);

        mSwitchView = (View) findViewById(R.id.left_drawer);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                // TODO Auto-generated method stub
                getSupportActionBar().setTitle(getTitle());
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                // Set the title on the action when drawer open
                getSupportActionBar().setTitle("Options");
                super.onDrawerOpened(drawerView);

                if (isNewerThanKitKat() == true) {
                    // patch the status & navigation bar tint
                    SystemBarTintManager tintManager = new SystemBarTintManager(MainActivity.this);
                    SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

                    drawerView.setPadding(0, config.getPixelInsetTop(true),
                            config.getPixelInsetRight(), config.getPixelInsetBottom());
                }
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        radioImperial = (RadioButton) mSwitchView.findViewById(R.id.radioButton_imp);
        radioMetric = (RadioButton) mSwitchView.findViewById(R.id.radioButton_metric);
        radioUnit = (RadioGroup) mSwitchView.findViewById(R.id.radioGroup_unit);

        radioUnit.setOnCheckedChangeListener(checkedChangeListener);

        // set the tint of actionbar and navigation bar
        if (isNewerThanKitKat() == true) {
            // set tint
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.action_bar_tint));

            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintColor(getResources().getColor(R.color.navigation_bar_tint));
        }
    }




    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

//            Log.d(TAG, "" + checkedId);

            switch (checkedId) {
                case R.id.radioButton_metric:
                    BaseActivity.isMetricUnit = true;
                    break;
                case R.id.radioButton_imp:
                    BaseActivity.isMetricUnit = false;
                    break;
            }

        }
    };


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (item.getItemId() == android.R.id.home) {

            if (mDrawerLayout.isDrawerOpen(mSwitchView)) {
                mDrawerLayout.closeDrawer(mSwitchView);
            } else {
                mDrawerLayout.openDrawer(mSwitchView);
            }
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        //TODO prompt user to confirm "Save/Dicard", label the unfinished records

//        if (getSupportFragmentManager().findFragmentByTag("AddRecordFragment") != null
//                && getSupportFragmentManager().findFragmentByTag("MainFragment") != null)  {
          if (getSupportFragmentManager().findFragmentByTag("AddRecordFragment") != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new MainFragment(), "MainFragment")
                    .commit();

        }

    }

    private boolean isNewerThanKitKat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return true;
        } else {
            return false;
        }
    }
}
