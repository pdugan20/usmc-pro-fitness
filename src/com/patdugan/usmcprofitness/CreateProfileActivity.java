package com.patdugan.usmcprofitness;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

// import android.widget.Button;
// import android.widget.EditText;
// import android.widget.RadioButton;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;

// import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class CreateProfileActivity extends org.holoeverywhere.app.Activity implements OnCheckedChangeListener {
	
	public DatabaseHelper databaseHelper;
	public String userName = "DefaultUser";
	public String userGender = "Male";
	public String userAge = "Group1";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile);
        
        // Adds the up arrow to the application-icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Assign ids for ageGroup RadioButtons
		RadioButton ageGroup1 = (RadioButton) findViewById(R.id.age_first);
    	RadioButton ageGroup2 = (RadioButton) findViewById(R.id.age_second);
    	RadioButton ageGroup3 = (RadioButton) findViewById(R.id.age_third);
    	RadioButton ageGroup4 = (RadioButton) findViewById(R.id.age_forth);
    	// Assign ids for gender RadioButtons
    	RadioButton maleRadioButton = (RadioButton) findViewById(R.id.male_radio);
		RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.female_radio);
        
        // Enable the navigation arrow for application icon
        
        // Initalizes the db-helper connection we imported above
        databaseHelper = new DatabaseHelper(this);
        
        // Get profile information if user has already entered it before
        Cursor UserProfileCursor = databaseHelper.getUserProfileInfo();
        if (UserProfileCursor != null ) {
            if (UserProfileCursor.moveToFirst()) {
                do {
                    userName = UserProfileCursor.getString(UserProfileCursor.getColumnIndex("userName"));
                    EditText userNameEditText = (EditText) findViewById(R.id.username_edit_text);
                    userNameEditText.setText(userName);
                    
                    userGender = UserProfileCursor.getString(UserProfileCursor.getColumnIndex("gender"));
                    if (userGender.equals("Male")) {
                    	maleRadioButton.setChecked(true);
                    } else if (userGender.equals("Female")) {
                    	femaleRadioButton.setChecked(true);
                    }
                    
                    userAge = UserProfileCursor.getString(UserProfileCursor.getColumnIndex("age"));
                    if (userAge.equals("Group1")) {
                    	ageGroup1.setChecked(true);
                    } else if (userAge.equals("Group2")) {
                    	ageGroup2.setChecked(true);
                    } else if (userAge.equals("Group3")) {
                    	ageGroup3.setChecked(true);
                    } else if (userAge.equals("Group4")) {
                    	ageGroup4.setChecked(true);
                    }
                    
                } while (UserProfileCursor.moveToNext());
                	Log.d("gender", userGender);
                	Log.d("age", userAge);
            }
        }
        UserProfileCursor.close();
        
        // Define both radio groups so be used by respective listeners
        RadioGroup GenderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group); 
        RadioGroup AgeRadioGroup = (RadioGroup) findViewById(R.id.age_radio_group);    
        
        // Create CheckChangeListeners for both RadioGroups
        GenderRadioGroup.setOnCheckedChangeListener(this);
        AgeRadioGroup.setOnCheckedChangeListener(this);
        
        // Assign ids for Save and Cancel buttons       
        Button SaveProfileButton = (Button) findViewById(R.id.SaveProfileButton);
        Button CancelProfileButton = (Button) findViewById(R.id.CancelProfileButton);
        
        // Assign onClick Listener for Save button which writes to db and correctly sets username flag
        SaveProfileButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {		
        		EditText userNameEditText = (EditText) findViewById(R.id.username_edit_text);
        		if (userNameEditText.getText().toString() == null) {
                	userName = "DefaultUser";
                } else {
                	userName = userNameEditText.getText().toString();
                }	
        		databaseHelper.saveUserProfile(userName, userGender, userAge);
        		Intent i = new Intent(CreateProfileActivity.this, USMCProFitMainActivity.class);
        		i.putExtra("user_profile_saved", "true");
        		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	    startActivity(i);
        	}
        });
        // Assign onClick Listener for CancelButton which closes activity stack
        CancelProfileButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		finish(); 
        	}
        });
    }

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// Assign ids for ageGroup RadioButtons
		RadioButton ageGroup1 = (RadioButton) findViewById(R.id.age_first);
    	RadioButton ageGroup2 = (RadioButton) findViewById(R.id.age_second);
    	RadioButton ageGroup3 = (RadioButton) findViewById(R.id.age_third);
    	RadioButton ageGroup4 = (RadioButton) findViewById(R.id.age_forth);
    	// Assign ids for gender RadioButtons
    	RadioButton maleRadioButton = (RadioButton) findViewById(R.id.male_radio);
		RadioButton femaleRadioButton = (RadioButton) findViewById(R.id.female_radio);
    	
		// Assigns the user a gender based on their radio button selection
        if(maleRadioButton.isChecked()) {
        	userGender = "Male";
        } else if (femaleRadioButton.isChecked()) {
        	userGender = "Female";
        }      
        // Assigns the user an age-group based on their radio button selection
        if(ageGroup1.isChecked()) {
        	userAge = "Group1";
        } else if (ageGroup2.isChecked()) {
        	userAge = "Group2";
        } else if (ageGroup3.isChecked()) {
        	userAge = "Group3";
        } else if (ageGroup4.isChecked()) {
        	userAge = "Group4";
        }
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