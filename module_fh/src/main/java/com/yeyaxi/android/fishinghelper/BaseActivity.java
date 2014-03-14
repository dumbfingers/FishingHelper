/**
 * Created by
 * @author yaxi
 * on  14/03/2014.
 *
 *
 *
 *
 * NOTE: Actionbar Sherlock is not working well with RelativeLayout in the Custom View. So change
 *      to another view if possible.
 */
package com.yeyaxi.android.fishinghelper;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.app.SherlockActivity;


public class BaseActivity extends SherlockActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View customView = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        // setup the Actionbar sherlock
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setCustomView(customView, lp);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setTitle("<Your Title Here>");

    }



}
