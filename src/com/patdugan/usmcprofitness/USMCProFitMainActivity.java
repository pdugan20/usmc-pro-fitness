package com.patdugan.usmcprofitness;

// import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

// import android.widget.Button;
// import android.widget.TextView;
// import android.widget.Toast;

import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.holoeverywhere.app.AlertDialog;

public class USMCProFitMainActivity extends org.holoeverywhere.app.Activity {
	
	public DatabaseHelper databaseHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        databaseHelper = new DatabaseHelper(this);
        
        Button AddScoreButton = (Button) findViewById(R.id.AddFitnessScoreButton);
        AddScoreButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        			
                Cursor UserProfileCursor = databaseHelper.getUserProfileInfo();
        		if (UserProfileCursor != null ) {
                	if (UserProfileCursor.moveToFirst()) {
                		// pass user profile information to the next activity
                		Intent AddNewScore = new Intent(USMCProFitMainActivity.this, AddTestScoreMainActivity.class);
                	    startActivity(AddNewScore);
                	} else {
                		AlertDialog.Builder adb = new AlertDialog.Builder(USMCProFitMainActivity.this);
        				
        				adb.setTitle("Please Create A Profile");
            	        adb.setMessage("You must choose a gender and age before entering a test score.");
            	        adb.setNegativeButton("Cancel", null);
            	        
            	        adb.setPositiveButton("Create Profile", new AlertDialog.OnClickListener() {  	
        					@Override
        					public void onClick(DialogInterface dialog, int which) {
        						Intent ManageProfile = new Intent(USMCProFitMainActivity.this, CreateProfileActivity.class);
        		        	    startActivity(ManageProfile);
        					}
        				});
            	        adb.show();
                	}
                }
                UserProfileCursor.close();
            } 
        });
        
        Button ManageProfileButton = (Button) findViewById(R.id.ManageProfileButton);
        ManageProfileButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent ManageProfile = new Intent(USMCProFitMainActivity.this, CreateProfileActivity.class);
        	    startActivity(ManageProfile);
            } 
        });
        
        Button ViewScoreButton = (Button) findViewById(R.id.ViewPreviousScoreButton);
        ViewScoreButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent ViewScore = new Intent(USMCProFitMainActivity.this, ViewTestScoresActivity.class);
        	    startActivity(ViewScore);
            } 
        });
        
        Bundle extras = getIntent().getExtras();
        TextView fitnessTestTypeTextView = (TextView)findViewById(R.id.fitness_type_text_view);
        if(extras == null) {
        	@SuppressWarnings("unused")
			String FitnessTypeString = null;
        	
        } else {
        	
        	String FitnessTypeString = extras.getString("fitness_test_type");
        	fitnessTestTypeTextView.setText(FitnessTypeString);
        	
        	String user_profile_saved = extras.getString("user_profile_saved");
        	if(user_profile_saved.equals("true")) {
                Context context = getApplicationContext();
                CharSequence text = "User Profile Saved";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
        	}
        }     
    }  
}