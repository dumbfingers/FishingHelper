package com.yeyaxi.android.fishinghelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yaxi on 01/04/2014.
 */
public class ViewRecordFragment extends SherlockFragment {

    private static final String TAG = ViewRecordFragment.class.getSimpleName();

    private EditText fishNameText;
    private EditText timestampText;
    private EditText locationText;
    //    private EditText anglerText;
    private EditText fishWeightText;
    private EditText fishWeightTextImp; //Additional text for imperial units
    private EditText fishLengthText;
    private EditText fishBaitText;
    private ImageView fishImage;
    private EditText noteText;

    private Button doneButton;

    private boolean isMetricUnit = true;

    private Fish fish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_record, container, false);

        fishNameText = (EditText) view.findViewById(R.id.text_fishname);
        timestampText = (EditText) view.findViewById(R.id.text_timestamp);
        locationText = (EditText) view.findViewById(R.id.text_location);
//        anglerText = (EditText) view.findViewById(R.id.text_angler);
        fishWeightText = (EditText) view.findViewById(R.id.text_fishweight);
        fishWeightTextImp = (EditText) view.findViewById(R.id.text_imperial);

        fishLengthText = (EditText) view.findViewById(R.id.text_fishlength);
        fishBaitText = (EditText) view.findViewById(R.id.text_bait);
        fishImage = (ImageView) view.findViewById(R.id.img_fish);
        noteText = (EditText) view.findViewById(R.id.text_note);

        View absView = getSherlockActivity().getSupportActionBar().getCustomView();
        doneButton = (Button) absView.findViewById(R.id.button_done);
        doneButton.setOnClickListener(onClickListener);

        // Lock the navigation drawer
        ((MainActivity)getSherlockActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ((MainActivity)getSherlockActivity()).mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSherlockActivity().getSupportActionBar().setHomeButtonEnabled(false);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.button_done:
                    // Hide the done button
                    doneButton.setVisibility(View.GONE);
                    saveToDbTask.execute();
                    break;
                case R.id.img_fish:
                    // call the camera
//                    launchCamera();
                    break;
            }

        }
    };

    @Override
    public void onResume() {
        super.onResume();

        doneButton.setVisibility(View.VISIBLE);

        if (BaseActivity.isMetricUnit == false) {
            // enable the input of imperial unit
            fishWeightTextImp.setVisibility(View.VISIBLE);
            fishWeightText.setHint("Fish Weight(lbs)");
            fishLengthText.setHint("Fish Length(inch)");
            isMetricUnit = false;
        } else {
            // enable the input of metric unit
            fishWeightTextImp.setVisibility(View.GONE);
            fishWeightText.setHint("Fish Weight(kg)");
            fishLengthText.setHint("Fish Length(cm)");
            isMetricUnit = true;
        }

        // load data from database
        long fishTimeStamp = BaseActivity.getTimeStampOfView();
        FishingDataOpenHelper db = new FishingDataOpenHelper(getSherlockActivity());
        fish = db.getFish(fishTimeStamp);

        // get image
        decodeByteArrayTask.execute(fish.getImgByteArray());
        // get name
        fishNameText.setText(fish.getFishName());
        // get time
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ssZ", Locale.UK);
        timestampText.setText(sdf.format(new Date(fish.getTimeStamp())));
        // get location
        locationText.setText(
                String.valueOf(fish.getLatitude()) + "," + String.valueOf(fish.getLongitude()));
        // get fish weight and length
        if (isMetricUnit == true) {
            fishWeightText.setText(String.valueOf(fish.getFishWeight()));
            fishLengthText.setText(String.valueOf(fish.getFishLength()));
        } else {
            float pound = fish.getFishWeight() * BaseActivity.KG_TO_POUND;
            float ounce = (pound % 1) * BaseActivity.POUND_TO_OUNCE;
            fishWeightText.setText(String.valueOf(pound));
            fishWeightTextImp.setText(String.valueOf(ounce));

            fishLengthText.setText(String.valueOf(
                    fish.getFishLength() * BaseActivity.METRES_TO_INCH));
        }

        // get bait
        fishBaitText.setText(fish.getBait());
        // get note
        noteText.setText(fish.getNote());


    }

    /**
     * will switch to main record list
     */
    private void switchToMain() {

        // unlock the navigation drawer
        ((MainActivity)getSherlockActivity()).mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        ((MainActivity)getSherlockActivity()).mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSherlockActivity().getSupportActionBar().setHomeButtonEnabled(true);

        getSherlockActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MainFragment(), "MainFragment")
                .commit();
    }

    AsyncTask<byte[], Void, Bitmap> decodeByteArrayTask = new AsyncTask<byte[], Void, Bitmap>() {
        @Override
        protected Bitmap doInBackground(byte[]... params) {
            Bitmap bm = BitmapFactory.decodeByteArray(params[0], 0, params[0].length);
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            fishImage.setImageBitmap(bitmap);
            fishImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    };

    private void saveToDb() {
        FishingDataOpenHelper db = new FishingDataOpenHelper(getSherlockActivity());

//        Fish tempfish = new Fish();

//        fish.setImgPath();
        fish.setFishName(fishNameText.getText().toString());
//        fish.setTimeStamp(fish.getTimeStamp());
        //TODO Manually choose the location
//        tempfish.setLatitude((float) currentLocation.getLatitude());
//        tempfish.setLongitude((float) currentLocation.getLongitude());

        if (fishWeightText.getText().toString().equals("") != true) {

            if (isMetricUnit == true) {

                fish.setFishWeight(Float.parseFloat(fishWeightText.getText().toString()));

            } else {

                if (fishWeightTextImp.getText().toString().equals("") == true) {

                    fishWeightTextImp.setText("0");

                }
                // convert to metric
                float weight = Float.parseFloat(fishWeightText.getText().toString()) * BaseActivity.POUND_TO_KG
                        + Float.parseFloat(fishWeightTextImp.getText().toString()) * BaseActivity.OUNCE_TO_KG;

                fish.setFishWeight(weight);
            }
        }
        if (fishLengthText.getText().toString().equals("") != true) {

            float length = 0;
            if (isMetricUnit == true) {
                length = Float.parseFloat(fishLengthText.getText().toString()) * BaseActivity.CM_TO_METRES;
            } else {
                length = Float.parseFloat(fishLengthText.getText().toString()) * BaseActivity.INCH_TO_METRES;
            }
            fish.setFishLength(length);
        }

        fish.setBait(fishBaitText.getText().toString());

//        if (filePath != null) {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//            if (bitmap.getWidth() > bitmap.getHeight() && bitmap.getWidth() > 3000 ||
//                    bitmap.getHeight() > bitmap.getWidth() && bitmap.getHeight() > 3000) {
//
//                // compress the bitmap so that the image size will not exceed 2MB
//                bitmap = bitmap.createScaledBitmap(
//                        bitmap, (int)(bitmap.getWidth() * 0.5), (int)(bitmap.getHeight() * 0.5), false);
//
//            }
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
//
//            fish.setImgByteArray(baos.toByteArray());
//        }
        fish.setNote(noteText.getText().toString());

        db.updateFish(fish, fish.getTimeStamp());
    }


    AsyncTask<Void, Void, Void> saveToDbTask = new AsyncTask<Void, Void, Void>() {

        @Override
        protected void onProgressUpdate(Void... progress) {
            Toast.makeText(getSherlockActivity(), "Saving...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            saveToDb();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Toast.makeText(getSherlockActivity(), "Fish Records Saved!", Toast.LENGTH_LONG).show();
            switchToMain();
        }
    };
}
