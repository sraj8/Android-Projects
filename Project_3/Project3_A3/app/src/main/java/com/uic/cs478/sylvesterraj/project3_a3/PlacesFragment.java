package com.uic.cs478.sylvesterraj.project3_a3;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlacesFragment extends ListFragment{
    public ListSelectionListener mListener = null;
    private static int mSelectedIdx = -1;

    // Callback interface that allows this Fragment to notify the SanFranMainActivity when
    // user clicks on a List Item
    public interface ListSelectionListener {
         void onListSelection(int index);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        if(pos != mSelectedIdx) {

            mSelectedIdx = pos;
            // Indicates the selected item has been checked
            getListView().setItemChecked(pos, true);
        }

        // Inform the SanFranMainActivity that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        try {

            // Set the ListSelectionListener for communicating with the SanFranMainActivity
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//Retaining the state of the PlacesFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.places_fragment, SanFranMainActivity.mPlaces));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // If an item has been selected, set its checked state
        if (mSelectedIdx > -1) {
            getListView().setItemChecked(mSelectedIdx, true);
            mListener.onListSelection(mSelectedIdx);
        }
    }
}
