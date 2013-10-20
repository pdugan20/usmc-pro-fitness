package com.patdugan.usmcprofitness;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import org.holoeverywhere.ArrayAdapter;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ListFragment;

/** This is a listfragment class */
public class CftFragment extends ListFragment {
 
    String cft_message[] = new String[]{
        "No CFT tests have been scored."
    };
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.cft_empty, cft_message);
        setListAdapter(adapter);
 
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}