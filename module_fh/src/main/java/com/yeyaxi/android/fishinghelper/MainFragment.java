package com.yeyaxi.android.fishinghelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaxi on 16/03/2014.
 */
public class MainFragment extends SherlockFragment {

//    ArrayList<Card> cardList = new ArrayList<Card>();
    private Button addRecordButton;
    private int count = 0;
    private TextView countText;
    private ListView listView;

    private FishingDataOpenHelper db;

//    @Override
//    public void onCreate() {
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        addRecordButton = (Button) view.findViewById(R.id.button_new_record);
        listView = (ListView) view.findViewById(R.id.listView);
        countText = (TextView) view.findViewById(R.id.textView_counter);

//        View absView = getSherlockActivity().getSupportActionBar().getCustomView();
//        Card card = new Card(getSherlockActivity());
//        CardHeader header = new CardHeader(getSherlockActivity());

//        header.setTitle("Title");
//        card.setBackgroundResourceId();

//        card.addCardHeader(header);
//        cardList.add(card);

//        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getSherlockActivity(),cardList);
//
//        CardGridView gridView = (CardGridView) view.findViewById(android.R.id.myGrid);
//        if (gridView!=null){
//            gridView.setAdapter(mCardArrayAdapter);
//        }

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
            }
        });


        return view;
    }


    public void onStart() {
        super.onStart();


    }

    public void onResume() {
        super.onResume();

        db = new FishingDataOpenHelper(getSherlockActivity());

//        if (countText.getVisibility() != View.VISIBLE) {
//            db = new FishingDataOpenHelper(getSherlockActivity());
//            countText.setVisibility(View.VISIBLE);
//            countText.setText(db.getFishCount());
//        } else {
//            db = new FishingDataOpenHelper(getSherlockActivity());
//            countText.setText(db.getFishCount());
//        }

        List<Fish> fishArrayList = new ArrayList<Fish>();
        fishArrayList = db.getAllFish();

        CardStyleAdapter adapter = new CardStyleAdapter(
                getSherlockActivity(), R.layout.cell_listview, fishArrayList);

        listView.setAdapter(adapter);
    }

}
