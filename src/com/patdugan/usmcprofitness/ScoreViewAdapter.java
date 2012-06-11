package com.patdugan.usmcprofitness;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ScoreViewAdapter extends CursorAdapter {
	public ScoreViewAdapter(Context context, Cursor cursor) {
		// this use to be where our pre-defined times were entered
		super(context, cursor);
	}

	public void bindView(View view, Context context, Cursor cursor) {
		TextView runTimeTextView = (TextView) view.findViewById(R.id.run_time);
		runTimeTextView.setText(cursor.getString(cursor.getColumnIndex("runTime")));
		
		TextView pullUpTextView = (TextView) view.findViewById(R.id.pullup_count);
		pullUpTextView.setText(cursor.getString(cursor.getColumnIndex("pullupCount")));
		
		TextView sitUpTextView = (TextView) view.findViewById(R.id.situp_count);
		sitUpTextView.setText(cursor.getString(cursor.getColumnIndex("situpCount")));
		
		TextView userClassTextView = (TextView) view.findViewById(R.id.user_class);
		userClassTextView.setText(cursor.getString(cursor.getColumnIndex("userClass")));
		
		TextView testScoreTextView = (TextView) view.findViewById(R.id.single_test_score);
		testScoreTextView.setText(cursor.getString(cursor.getColumnIndex("testScore")));
		
		TextView testDateTextView = (TextView) view.findViewById(R.id.test_date);
		testDateTextView.setText(cursor.getString(cursor.getColumnIndex("testDate")));
		
	}
	
	// Layout inflater is retreived and the layout is inflated then returned
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.score_list_item, parent, false);
		return view;
	}
}
