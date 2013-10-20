package com.patdugan.usmcprofitness;

// import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import org.holoeverywhere.widget.Toast;

// import android.widget.ListView;
// import android.widget.Toast;
// import com.actionbarsherlock.app.SherlockActivity;

public class ViewTestScoresActivity extends org.holoeverywhere.app.ListActivity {
	public String runTime;
	public String crunchCount;
	public String pullUpCount;
	public String pftScore;
	public String pftClass;
	public String testDate;
	public String shareTextBody;
	public Integer dbId;
	public ScoreViewAdapter scoreViewAdapter;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_scores);
        
        // Adds the up arrow to the application-icon
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        
        // Tell the ActionBar we want to use tabs
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        // Initiating both tabs and sets their text
        ActionBar.Tab pftTab = bar.newTab().setText("PFT Scores");
        ActionBar.Tab cftTab = bar.newTab().setText("CFT Scores");

        // Set the Tab listener so we can listen for clicks
        pftTab.setTabListener(new ScoreViewTabListener<PftFragment>(this, "pft", PftFragment.class));
        cftTab.setTabListener(new ScoreViewTabListener<CftFragment>(this, "cft", CftFragment.class));
        
        // Add the two tabs to the action-bar
        bar.addTab(pftTab);
        bar.addTab(cftTab);
        
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
	
