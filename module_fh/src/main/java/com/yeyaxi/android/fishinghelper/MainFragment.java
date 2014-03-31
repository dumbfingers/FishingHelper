package com.yeyaxi.android.fishinghelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaxi on 16/03/2014.
 */
public class MainFragment extends SherlockFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
//    ArrayList<Card> cardList = new ArrayList<Card>();
    private Button addRecordButton;
    private int count = 0;
    private TextView countText;
    private ListView listView;

    private ActionMode mMode;
    private boolean isLongPressed = false;

    private FishingDataOpenHelper db;

    private static CardStyleAdapter adapter;

//    @Override
//    public void onCreate() {
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        countText = (TextView) view.findViewById(R.id.textView_counter);

        View absView = getSherlockActivity().getSupportActionBar().getCustomView();
        addRecordButton = (Button) absView.findViewById(R.id.button_new_record);

        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the counter
//                countText.setVisibility(View.INVISIBLE);
                // swap the listview/card view with the new record fragment
                getSherlockActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new AddRecordFragment(), "AddRecordFragment")
                        .commit();
                addRecordButton.setVisibility(View.GONE);
            }
        });


        return view;
    }


    public void onStart() {
        super.onStart();


    }

    public void onResume() {
        super.onResume();

        if (addRecordButton.getVisibility() != View.VISIBLE) {
            addRecordButton.setVisibility(View.VISIBLE);
        }

        db = new FishingDataOpenHelper(getSherlockActivity());

//        if (countText.getVisibility() != View.VISIBLE) {
//            db = new FishingDataOpenHelper(getSherlockActivity());
//            countText.setVisibility(View.VISIBLE);
//            countText.setText(db.getFishCount());
//        } else {
//            db = new FishingDataOpenHelper(getSherlockActivity());
//            countText.setText(String.valueOf(db.getFishCount()));
//        }

        List<Fish> fishArrayList = new ArrayList<Fish>();
        fishArrayList = db.getAllFish();

        adapter = new CardStyleAdapter(
                getSherlockActivity(), R.layout.cell_listview, fishArrayList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(longClickListener);
    }

    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            isLongPressed = true;
            mMode = getSherlockActivity().startActionMode(mActionModeCallback);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            // clear the previous selection
            adapter.removeSelection();
            selectItems(position);
            return true;
        }
    };

    private void selectItems(int position) {
        adapter.toggleSelection(position);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (isLongPressed == true) {
                selectItems(position);
            } else {
                //TODO launch detail view
            }
        }
    };



    // set the contextual action bar
    public ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Choose items");
            menu.add("Delete")
                    .setIcon(R.drawable.ic_action_discard)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            //TODO call database to delete the selected items

            Log.d(TAG, "Test delete function: " + adapter.getSelectedItems());
            popAlertDialog();
            mode.finish();
            return true;
        }


        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isLongPressed = false;
            adapter.removeSelection();

        }
    };

    private void popAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
        builder.setTitle("Delete?");
        builder.setMessage("Selected Item(s) will be deleted");
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                deleteItemsFromDbTask.execute(adapter.getSelectedItems());
                for (int i = 0; i < adapter.getSelectedItems().size(); i++) {
                    db.deleteFish(i + 1);
                }
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

//    AsyncTask<SparseBooleanArray, Void, Void> deleteItemsFromDbTask = new AsyncTask<SparseBooleanArray, Void, Void>() {
//        @Override
//        protected Void doInBackground(SparseBooleanArray... params) {
//            for (int i = 0; i < params[0].size(); i++) {
//                db.deleteFish(i+1);
//            }
//            return null;
//        }
//    };

}
