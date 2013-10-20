package com.patdugan.usmcprofitness;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.actionbarsherlock.view.MenuItem;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// import android.widget.Button;
// import android.widget.SeekBar;
// import android.widget.TextView;
// import com.actionbarsherlock.app.SherlockActivity;

public class AddTestScorePFTActivity extends org.holoeverywhere.app.Activity implements SeekBar.OnSeekBarChangeListener, OnClickListener {	
	
	SeekBar pullUpSeekBar;
    SeekBar crunchesSeekBar;    
    TextView pullUpSeekBarText;
    TextView crunchesSeekBarText;
    
    protected Button runTimeAddMinutes;
    protected Button runTimeDecreaseMinutes;
    protected TextView runTimeMinutesLabel;
    
    protected Button runTimeAddSeconds;
    protected Button runTimeDecreaseSeconds;
    protected TextView runTimeSecondsLabel;
    
    protected int runTimeMinutes = 18;
    protected int runTimeSeconds = 30;
    
    protected String pftRunTimeCombined;
    protected Integer pullUpInt;
    protected Integer crunchesInt;
    protected String testCompletionDate;
    
    private DatabaseHelper databaseHelper;
    public PFTCalculatorLogic pftCalculator;
    public static final int TIME_ENTRY_REQUEST_CODE = 1;
    
    protected int calculatedScore;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_score_pft);
        
        // Adds the up arrow to the application-icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseHelper = new DatabaseHelper(this);
        
        Bundle extras = getIntent().getExtras();
        String FitnessTypeString = extras.getString("fitness_test_type");
        if (FitnessTypeString == "PFT: Physical Fitness Test") {
        	FitnessTypeString = "PFT";
        }
        
        // pullUpSeekbar specific variables
        pullUpSeekBar = (SeekBar)findViewById(R.id.pull_up_seek_bar);
        pullUpSeekBar.setOnSeekBarChangeListener(this);
        pullUpSeekBarText = (TextView)findViewById(R.id.pullup_number_text_view);
        
        // crunchesSeekbar specific variables
        crunchesSeekBar = (SeekBar)findViewById(R.id.crunches_seek_bar);
        crunchesSeekBar.setOnSeekBarChangeListener(this);
        crunchesSeekBarText = (TextView)findViewById(R.id.crunches_number_text_view);
        
        Button NextActivityButton = (Button) findViewById(R.id.NextButton);
        NextActivityButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		
        		// Generic variables which are loaded by default
            	String runTime = pftRunTimeCombined;
            	Integer sitUps = crunchesInt;
            	Integer pullUps = pullUpInt;
            	Integer runMinutes = runTimeMinutes;
            	Integer runSeconds = runTimeSeconds;
            	String userName = "DefaultUser";
            	// String userClass = "1st";
            	String userClass = "Failure";
            	
            	if (sitUps == null) {
            		sitUps = 50;
            	}
            	if (pullUps == null) {
            		pullUps = 10;
            	}
            	
            	String testDate = testCompletionDate;
            	
            	// Does all of the actual score calculation
                pftCalculator = new PFTCalculatorLogic();
                Integer finalScore = pftCalculator.GetPftInputs(runMinutes, runSeconds, pullUps, sitUps);
                
                // Gets the users profile information for class detection
                Cursor UserProfileCursor = databaseHelper.getUserProfileInfo();
                if (UserProfileCursor != null ) {
                    if (UserProfileCursor.moveToFirst()) {
                        do {
                            userName = UserProfileCursor.getString(UserProfileCursor.getColumnIndex("userName"));
                            
                            String userAge = UserProfileCursor.getString(UserProfileCursor.getColumnIndex("age"));
                            if (userAge.equals("Group1")) {
                            	if (finalScore >= 225) {
                            		userClass = "1st";
                            	} else if (finalScore >= 175) {
                            		userClass = "2nd";
                            	} else if (finalScore >= 135) {
                            		userClass = "3rd";
                            	}
                            } else if (userAge.equals("Group2")) {
                            	if (finalScore >= 200) {
                            		userClass = "1st";
                            	} else if (finalScore >= 150) {
                            		userClass = "2nd";
                            	} else if (finalScore >= 110) {
                            		userClass = "3rd";
                            	}
                            } else if (userAge.equals("Group3")) {
                            	if (finalScore >= 175) {
                            		userClass = "1st";
                            	} else if (finalScore >= 125) {
                            		userClass = "2nd";
                            	} else if (finalScore >= 88) {
                            		userClass = "3rd";
                            	}
                            } else if (userAge.equals("Group4")) {
                            	if (finalScore >= 150) {
                            		userClass = "1st";
                            	} else if (finalScore >= 100) {
                            		userClass = "2nd";
                            	} else if (finalScore >= 65) {
                            		userClass = "3rd";
                            	}
                            }
                            
                        } while (UserProfileCursor.moveToNext());
                    }
                }
                UserProfileCursor.close();
                // Saves calculated score
        		databaseHelper.saveTimeRecord(runTime, sitUps, pullUps, userName, finalScore, userClass, testDate);
        		
        		Intent i = new Intent(AddTestScorePFTActivity.this, ViewTestScoresActivity.class);
        		i.putExtra("test_save_true_flag", "true");
        		
        		AddTestScorePFTActivity.this.finish();
        	    startActivity(i);
        	    
        	}
        });
        
        Button BackActivityButton = (Button) findViewById(R.id.BackButton);
        BackActivityButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		finish(); 
        	}
        });
        
        runTimeAddMinutes = (Button) findViewById(R.id.minutes_up);
        runTimeDecreaseMinutes = (Button) findViewById(R.id.minutes_down);
        runTimeMinutesLabel = (TextView) findViewById(R.id.run_time_minutes);
        
        runTimeAddMinutes.setOnClickListener(this);
        runTimeDecreaseMinutes.setOnClickListener(this);   
        setRunTimeMinutes(runTimeMinutes);
        
        runTimeAddSeconds = (Button) findViewById(R.id.seconds_up);
        runTimeDecreaseSeconds = (Button) findViewById(R.id.seconds_down);
        runTimeSecondsLabel = (TextView) findViewById(R.id.run_time_seconds);
        
        runTimeAddSeconds.setOnClickListener(this);
        runTimeDecreaseSeconds.setOnClickListener(this); 
        setRunTimeSeconds(runTimeSeconds);
        
        Calendar c = Calendar.getInstance(); 
        SimpleDateFormat df_simple = new SimpleDateFormat("dd-MM-yyyy");  
        testCompletionDate = df_simple.format(c.getTime());
        
    }
    
    @Override
	public void onClick(View v) {
    	if(v == runTimeAddMinutes)
    		setRunTimeMinutes(runTimeMinutes + 1);
    	else if(v == runTimeDecreaseMinutes)
    		setRunTimeMinutes(runTimeMinutes -1);
    	
    	if(v == runTimeAddSeconds)
    		setRunTimeSeconds(runTimeSeconds + 1);
    	else if(v == runTimeDecreaseSeconds)
    		setRunTimeSeconds(runTimeSeconds -1);
    }
    
    public void setRunTimeMinutes(int minutes) {
    	runTimeMinutes = minutes;
    	if(runTimeMinutes < 1)
    		runTimeMinutes = 1;
    	runTimeMinutesLabel.setText(String.valueOf(runTimeMinutes) + " min");
    	pftRunTimeCombined = Integer.toString(runTimeMinutes) + ":" + Integer.toString(runTimeSeconds);
    } 
    
    public void setRunTimeSeconds(int seconds) {
    	runTimeSeconds = seconds;
    	if(runTimeSeconds < 1)
    		runTimeSeconds = 1;
    	if(runTimeSeconds > 59)
    		runTimeSeconds = 59;
    	runTimeSecondsLabel.setText(String.valueOf(runTimeSeconds) + " sec");
    	pftRunTimeCombined = Integer.toString(runTimeMinutes) + ":" + Integer.toString(runTimeSeconds);
    }  

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.pull_up_seek_bar:
			pullUpSeekBarText.setText(progress + "");
			break;
		case R.id.crunches_seek_bar:
			crunchesSeekBarText.setText(progress + "");
			break;
		}
		pullUpInt = Integer.parseInt(pullUpSeekBarText.getText().toString());
		crunchesInt = Integer.parseInt(crunchesSeekBarText.getText().toString());
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub	
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