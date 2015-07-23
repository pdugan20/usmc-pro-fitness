package com.patdugan.usmcprofitness;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ListFragment;
import org.holoeverywhere.widget.ListView;

/** This is a listfragment class */
public class PftFragment extends ListFragment {
	private DatabaseHelper databaseHelper;
	public String runTime;
	public String crunchCount;
	public String pullUpCount;
	public String pftScore;
	public String pftClass;
	public String testDate;
	public String shareTextBody;
	public Integer dbId;
	public ScoreViewAdapter scoreViewAdapter;
 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	// Queries the database for PFT records
        databaseHelper = new DatabaseHelper(getActivity().getBaseContext());
        // Creates the ScoreViewAdapter
        scoreViewAdapter = new ScoreViewAdapter(getActivity().getBaseContext(), databaseHelper.getAllTimeRecords());
        
        // Displays message if the adapter returns no results
        if (scoreViewAdapter.getCount() == 0) {
        	String pft_message[] = new String[]{"No PFT tests have been scored."};
        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.cft_empty, pft_message);
            setListAdapter(adapter);
        } else {
        	// Sets ScoreViewAdapter to the ListView
            setListAdapter(scoreViewAdapter);
        }
 
        return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		 final long list_view_row_id = id;
	        Cursor SingleTestRecordCursor = databaseHelper.getSingleTimeRecord(String.valueOf(list_view_row_id));
	        if (SingleTestRecordCursor != null ) {
	            if (SingleTestRecordCursor.moveToFirst()) {
	                do {
	                    runTime = SingleTestRecordCursor.getString(SingleTestRecordCursor.getColumnIndex("runTime"));
	                    crunchCount = SingleTestRecordCursor.getString(SingleTestRecordCursor.getColumnIndex("situpCount"));
	                    pullUpCount = SingleTestRecordCursor.getString(SingleTestRecordCursor.getColumnIndex("pullupCount"));
	                    pftScore = SingleTestRecordCursor.getString(SingleTestRecordCursor.getColumnIndex("testScore"));
	                    pftClass = SingleTestRecordCursor.getString(SingleTestRecordCursor.getColumnIndex("userClass"));
	                    testDate = SingleTestRecordCursor.getString(SingleTestRecordCursor.getColumnIndex("testDate"));
	                    dbId = SingleTestRecordCursor.getInt(SingleTestRecordCursor.getColumnIndex("_id"));
	                } while (SingleTestRecordCursor.moveToNext());
	                	Log.d("runtime", runTime);
	                	Log.d("crunchcount", crunchCount);
	                	Log.d("pullupcount", pullUpCount);
	                	Log.d("pftscore", pftScore);
	                	Log.d("pftclass", pftClass);
	                	Log.d("testdate", testDate);
	                	Log.d("dbId", dbId.toString());
	            }
	        }
	    
	    SingleTestRecordCursor.close();
	        
	    Intent i = new Intent(getActivity(), ViewDetailedTestScoreActivity.class);
 		i.putExtra("runTime", runTime);
 		i.putExtra("crunchCount", crunchCount);
 		i.putExtra("pullUpCount", pullUpCount);
 		i.putExtra("pftScore", pftScore);
 		i.putExtra("pftClass", pftClass);
 		i.putExtra("testDate", testDate);
 		i.putExtra("dbId", dbId);
 	    startActivity(i);
	}
};