package com.yeyaxi.android.fishinghelper;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yaxi on 16/03/2014.
 */
public class AddRecordFragment extends SherlockFragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private static final String TAG = AddRecordFragment.class.getSimpleName();

    private EditText fishNameText;
    private EditText timestampText;
    private EditText locationText;
//    private EditText anglerText;
    private EditText fishWeightText;
    private EditText fishLengthText;
    private EditText fishBaitText;
    private ImageView fishImage;
    private EditText noteText;

    private Button doneButton;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;
    private String filePath;
    private Long currentTime;
    private Location currentLocation;

    private LocationClient locationClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

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
        fishLengthText = (EditText) view.findViewById(R.id.text_fishlength);
        fishBaitText = (EditText) view.findViewById(R.id.text_bait);
        fishImage = (ImageView) view.findViewById(R.id.img_fish);
        noteText = (EditText) view.findViewById(R.id.text_note);

        View absView = getSherlockActivity().getSupportActionBar().getCustomView();

        doneButton = (Button) absView.findViewById(R.id.button_done);

        doneButton.setVisibility(View.VISIBLE);

        doneButton.setOnClickListener(onClickListener);
        fishImage.setOnClickListener(onClickListener);

        // invoke the location manager
        locationClient = new LocationClient(getSherlockActivity(), this, this);


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
                    launchCamera();
                    break;
            }

        }
    };

    /**
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     * @param dataBundle
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        Log.d(TAG, "Google Play Services Connected");

        // set the location text when located
        currentLocation = locationClient.getLastLocation();
        locationText.setText(String.valueOf(currentLocation.getLatitude()) + ","
                + String.valueOf(currentLocation.getLongitude()));

        currentTime = currentLocation.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ssZ", Locale.UK);
        timestampText.setText(sdf.format(new Date(currentTime)));

    }

    /**
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Log.d(TAG, "Google Play Services Disconnected.");
    }

    /**
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getSherlockActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
//            showErrorDialog(connectionResult.getErrorCode());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
                && resultCode == getSherlockActivity().RESULT_OK) {
            // the "data" will be null
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            setThumbnail.execute(filePath);
//            fishImage.setImageBitmap(imageBitmap);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // connect the location client
        locationClient.connect();



    }

    @Override
    public void onStop() {
        // disconnect the location client
        locationClient.disconnect();

        super.onStop();

    }

    AsyncTask<String, Void, Bitmap> setThumbnail = new AsyncTask<String, Void, Bitmap>() {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = decodeSampledBitmapFromFile(params[0], fishImage.getWidth(), fishImage.getHeight());
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            fishImage.setImageBitmap(bm);
        }
    };

    /**
     *
     */
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

    /**
     * save the entered content to db
     */
    private void saveToDb() {
        FishingDataOpenHelper db = new FishingDataOpenHelper(getSherlockActivity());

        Fish fish = new Fish();

//        fish.setImgPath();
        fish.setFishName(fishNameText.getText().toString());
        fish.setTimeStamp(currentTime);
        fish.setLatitude((float) currentLocation.getLatitude());
        fish.setLongitude((float) currentLocation.getLongitude());

        if (fishWeightText.getText().toString().equals("") != true) {
            fish.setFishWeight(Float.parseFloat(fishWeightText.getText().toString()));
        }
        if (fishLengthText.getText().toString().equals("") != true) {
        fish.setFishLength(Float.parseFloat(fishLengthText.getText().toString()));
        }

        fish.setBait(fishBaitText.getText().toString());

        if (filePath != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);

            fish.setImgByteArray(baos.toByteArray());
        }
        fish.setNote(noteText.getText().toString());

        db.addFish(fish);
    }

    /**
     * will switch to main record list
     */
    private void switchToMain() {
        getSherlockActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MainFragment(), "MainFragment")
                .commit();
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        if (fileUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
            // start the image capture Intent
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {
            Log.e(TAG, "File saving path is null.");
        }
    }

    /**
     * Check the External Storage state
     * @return true if the external storage is available and readable;
     * 			false if the external storage state is either unavailable or not readable
     */
    private boolean checkStorageState() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        return (mExternalStorageAvailable && mExternalStorageWriteable);
    }

    private File getStorageDirectory() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
//			return getExternalFilesDir(null);
//		} else {
//			return new File(Environment.getExternalStorageDirectory(),
//					"/Android/data/" + this.getPackageName() + "/files/");
//		}
        // This will create a public folder under the sdcard root
        File f = new File(Environment.getExternalStorageDirectory(), "/FishingHelper/");
        if (f.exists() == false) {
            if (f.mkdirs() == true) {
                return f;
            } else {
                return null;
            }
        } else {
            return f;
        }

    }

    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){
        File mediaFile = null;
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (checkStorageState() == true) {
            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (type == MEDIA_TYPE_IMAGE){
                mediaFile = new File(getStorageDirectory(), "Fish_"+ timeStamp + ".jpg");
            } else {
                return null;
            }
        }
        try {
            filePath = mediaFile.getCanonicalPath();
            Log.d(TAG, "Photo saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaFile;
    }

//    private String getMeasurementUnit() {
//
//        if (((MainActivity)getSherlockActivity()).isMetricUnit() == true) {
//
//        }
//
//    }

    public static int calculateInSampleSize (BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile (String pathName, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // decode with required size
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(pathName, options);
    }
}
