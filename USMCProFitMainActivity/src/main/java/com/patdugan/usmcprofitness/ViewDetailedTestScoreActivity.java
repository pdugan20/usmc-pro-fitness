package com.patdugan.usmcprofitness;

// import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.actionbarsherlock.view.MenuItem;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

// import android.widget.Button;
// import android.widget.TextView;
//import com.actionbarsherlock.app.SherlockActivity;

public class ViewDetailedTestScoreActivity extends org.holoeverywhere.app.Activity {
	public String runTime;
	public String crunchCount;
	public String pullUpCount;
	public String pftScore;
	public String pftClass;
	public String testDate;
	public Integer dbId;
	public String shareTextBody;
	public ScoreViewAdapter scoreViewAdapter;
	private DatabaseHelper databaseHelper;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_item_detailed);
        
        // Adds the up arrow to the application-icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        databaseHelper = new DatabaseHelper(this);
        
        Bundle extras = getIntent().getExtras();        
        if(extras == null) {
        	// do nothing and skip to next line
        } else {
        	runTime = extras.getString("runTime");
        	crunchCount = extras.getString("crunchCount");
        	pullUpCount = extras.getString("pullUpCount");
        	pftScore = extras.getString("pftScore");
        	pftClass = extras.getString("pftClass");
        	testDate = extras.getString("testDate");
        	dbId = extras.getInt("dbId");
        }
        
        TextView runTimeTextView = (TextView)findViewById(R.id.run_time);
        runTimeTextView.setText(runTime); 
        TextView crunchCountTextView = (TextView)findViewById(R.id.situp_count);
        crunchCountTextView.setText(crunchCount);
        TextView pullUpCountTextView = (TextView)findViewById(R.id.pullup_count);
        pullUpCountTextView.setText(pullUpCount);
        TextView pftScoreTextView = (TextView)findViewById(R.id.single_test_score);
        pftScoreTextView.setText(pftScore);
        TextView pftClassTextView = (TextView)findViewById(R.id.user_class);
        pftClassTextView.setText(pftClass);
        TextView testDateTextView = (TextView)findViewById(R.id.test_date);
		testDateTextView.setText(testDate);

		Button ShareButton = (Button) findViewById(R.id.share_button);
		ShareButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		
        		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                shareTextBody = "I earned a PFT score of " + pftScore + " on " + testDate + ".\n" +
                "-Posted via USMC ProFitness for Android";
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareTextBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                
        	}
        });
		
		Button DeleteButton = (Button) findViewById(R.id.delete_button);
		DeleteButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		
        		AlertDialog.Builder adb = new AlertDialog.Builder(ViewDetailedTestScoreActivity.this);
				
				adb.setTitle("Delete Score?");
    	        adb.setMessage("Are you sure you want to delete this score?");
    	        adb.setNegativeButton("Cancel", null); 
    	        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {  	
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						databaseHelper.deleteSingleRecord(String.valueOf(dbId));
						Intent refreshActivity = new Intent(ViewDetailedTestScoreActivity.this, ViewTestScoresActivity.class);
						ViewDetailedTestScoreActivity.this.finish();
		        	    startActivity(refreshActivity);
		        	    
					}
				});
    	        adb.show();
        		
        	}
		});
		
	};
	
	 // Uses Action-Bar Home button to clear activity-stack and return home
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(this, USMCProFitMainActivity.class);
                parentActivityIntent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
	
