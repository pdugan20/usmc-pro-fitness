package com.patdugan.usmcprofitness;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ViewTestScoresActivity extends Activity {
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
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_scores);
        databaseHelper = new DatabaseHelper(this);
        
        final ListView listView = (ListView) findViewById(R.id.times_list);
        scoreViewAdapter = new ScoreViewAdapter(
        	this, databaseHelper.getAllTimeRecords());
        listView.setAdapter(scoreViewAdapter);
        
        // Sets listener for short-click which prompts to share list-item
        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				// Get score info from a single test record
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
		        
		        Intent i = new Intent(ViewTestScoresActivity.this, ViewDetailedTestScoreActivity.class);
        		i.putExtra("runTime", runTime);
        		i.putExtra("crunchCount", crunchCount);
        		i.putExtra("pullUpCount", pullUpCount);
        		i.putExtra("pftScore", pftScore);
        		i.putExtra("pftClass", pftClass);
        		i.putExtra("testDate", testDate);
        		i.putExtra("dbId", dbId);
        	    startActivity(i);
			}
        });
        
        // Sets listener for long-click which prompts to delete list item
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
				Vibrator deleteVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				deleteVibe.vibrate(300);
				
				AlertDialog.Builder adb = new AlertDialog.Builder(ViewTestScoresActivity.this);
				final long list_view_row_id = id;
				
				adb.setTitle("Delete Score?");
    	        adb.setMessage("Are you sure you want to delete this score?");
    	        adb.setNegativeButton("Cancel", null);
    	        
    	        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {  	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						databaseHelper.deleteSingleRecord(String.valueOf(list_view_row_id));
						// This is a hacky way to refresh the ListView
						Intent refreshActivity = new Intent(ViewTestScoresActivity.this, ViewTestScoresActivity.class);
						ViewTestScoresActivity.this.finish();
		        	    startActivity(refreshActivity);
					}
				});
    	        adb.show();
				return false;
			}
		});
        
        Bundle extras = getIntent().getExtras();        
        if(extras == null) {
        	// do nothing and skip to next line
        } else {
        	String test_save_flag = extras.getString("test_save_true_flag");
        	if(test_save_flag.equals("true")) {
                Context context = getApplicationContext();
                CharSequence text = "PFT Score Saved";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
        	}
        }   
    }
    
    @Override
	public void onBackPressed() {
		Intent intent = new Intent(this, USMCProFitMainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
	
