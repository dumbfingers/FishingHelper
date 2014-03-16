package com.yeyaxi.android.fishinghelper;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;

/**
 * @author yaxi
 */
public class MainActivity extends BaseActivity {


    private DrawerLayout mDrawerLayout;
    private View mSwitchView;
//    private View mainView;

//    private Button addRecordButton;

    private ActionBarDrawerToggle mDrawerToggle;

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
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }


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

        if (getSupportFragmentManager().findFragmentByTag("AddRecordFragment") != null
                && getSupportFragmentManager().findFragmentByTag("MainFragment") != null)  {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new MainFragment(), "MainFragment")
                    .commit();

        }

    }
}
