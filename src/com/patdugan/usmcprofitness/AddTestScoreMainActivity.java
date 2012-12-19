package com.patdugan.usmcprofitness;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// import android.widget.AdapterView;
// import android.widget.AdapterView.OnItemSelectedListener;
// import android.widget.ArrayAdapter;
// import android.widget.Button;
// import android.widget.Spinner;
// import android.widget.TextView;

import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.ArrayAdapter;

// import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class AddTestScoreMainActivity extends org.holoeverywhere.app.Activity {	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_score);
        
        // Adds the up arrow to the application-icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        final Spinner spinner = (Spinner) findViewById(R.id.test_selection_spinner);
        Button NextActivityButton = (Button) findViewById(R.id.NextButton);
        Button BackActivityButton = (Button) findViewById(R.id.BackButton);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
                R.array.fitness_test_array,
                // replaced default with holoeverywhere library
                // android.R.layout.simple_spinner_item
                // R.layout.spinner_item
                org.holoeverywhere.R.layout.simple_spinner_item
        		);
        
        adapter.setDropDownViewResource(
        		// replaced default with holoeverywhere library
        		// android.R.layout.simple_spinner_dropdown_item
        		org.holoeverywhere.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);         
        spinner.setOnItemSelectedListener(new FitnessTestTypeOnItemSelectedListener());
        
        NextActivityButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent i = new Intent(AddTestScoreMainActivity.this, AddTestScorePFTActivity.class);
                i.putExtra("fitness_test_type", spinner.getSelectedItem().toString());
        	    startActivity(i);  
        	}
        });
           
        BackActivityButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		finish(); 
        	}
        });
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
    
    public class FitnessTestTypeOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
			TextView fitnessDescriptionTextView = (TextView)findViewById(R.id.fitness_description_text);
			TextView fitnessTestTypeBackground = (TextView)findViewById(R.id.fitness_type_background);
			
			if (parent.getSelectedItem().equals(parent.getItemAtPosition(0))){
        		fitnessDescriptionTextView.setText(R.string.pft_test_description);
        		fitnessTestTypeBackground.setText(R.string.pft_background);
        		
            } else if (parent.getSelectedItem().equals(parent.getItemAtPosition(1))){
            	fitnessDescriptionTextView.setText(R.string.cft_test_description);
            	fitnessTestTypeBackground.setText(R.string.cft_background);
            }	
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub			
		}
    }
}