package com.yeyaxi.android.fishinghelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardGridView;

/**
 * Created by yaxi on 16/03/2014.
 */
public class MainFragment extends SherlockFragment {

    ArrayList<Card> cardList = new ArrayList<Card>();
    private Button addRecordButton;
    private int count = 0;
    private TextView countText;

//    @Override
//    public void onCreate() {
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        addRecordButton = (Button) view.findViewById(R.id.button_new_record);

        View absView = getSherlockActivity().getSupportActionBar().getCustomView();
        countText = (TextView) absView.findViewById(R.id.textView_counter);

        Card card = new Card(getSherlockActivity());
        CardHeader header = new CardHeader(getSherlockActivity());

        header.setTitle("Title");
//        card.setBackgroundResourceId();

        card.addCardHeader(header);
        cardList.add(card);

        CardGridArrayAdapter mCardArrayAdapter = new CardGridArrayAdapter(getSherlockActivity(),cardList);

        CardGridView gridView = (CardGridView) view.findViewById(R.id.myGrid);
        if (gridView!=null){
            gridView.setAdapter(mCardArrayAdapter);
        }

        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the counter
                countText.setVisibility(View.INVISIBLE);
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
        if (countText.getVisibility() != View.VISIBLE) {
            countText.setVisibility(View.VISIBLE);
        }
    }

}
