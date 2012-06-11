package com.patdugan.usmcprofitness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {
	
	// list of constants for referencing the db's internal values
	public static final int DATABASE_VERSION = 3;
	public static final String DATABASE_NAME = "pft_test_records.db";
	
	public static final String TABLE_NAME = "pft_test_records";	
	public static final String TIMETRACKER_COLUMN_ID = "_id";
	public static final String TIMETRACKER_COLUMN_RUNTIME = "runTime";
	public static final String TIMETRACKER_COLUMN_SITUPS = "situpCount";
	public static final String TIMETRACKER_COLUMN_PULLUPS = "pullupCount";
	public static final String TIMETRACKER_COLUMN_USERNAME = "userName";
	public static final String TIMETRACKER_COLUMN_SCORE = "testScore";
	public static final String TIMETRACKER_COLUMN_CLASS = "userClass";
	public static final String TIMETRACKER_COLUMN_TESTDATE = "testDate";
	
	public static final String USER_TABLE_NAME = "user_profile";
	public static final String PROFILE_COLUMN_ID = "_id";
	public static final String PROFILE_COLUMN_USERNAME = "userName";
	public static final String PROFILE_COLUMN_GENDER = "gender";
	public static final String PROFILE_COLUMN_AGE = "age";
		
	// stored local variables for the OpenHelper and the database it opens
	public DatabaseOpenHelper openHelper; 
	public SQLiteDatabase database;

	public DatabaseHelper(Context context) {
		openHelper = new DatabaseOpenHelper(context);
		database = openHelper.getWritableDatabase();
	}
	
	// this is what actually declares the database so that other activites can read and write to it
	private class DatabaseOpenHelper extends SQLiteOpenHelper {
		DatabaseOpenHelper(Context context) {
			// pass name (pft_test_records.db) and version number (1) as super
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		// when the db is created. this method is called by onUpgrade when the version number is changed
		public void onCreate(SQLiteDatabase database) {
			// execSQL actually creates the schema of the db we defined in the method above
			database.execSQL(
				"CREATE TABLE " + TABLE_NAME + "( "
					+ TIMETRACKER_COLUMN_ID + " INTEGER PRIMARY KEY, "
					+ TIMETRACKER_COLUMN_RUNTIME + " TEXT, "
					+ TIMETRACKER_COLUMN_SITUPS + " INTEGER, "
					+ TIMETRACKER_COLUMN_PULLUPS + " INTEGER, "
					+ TIMETRACKER_COLUMN_USERNAME + " TEXT, "
					+ TIMETRACKER_COLUMN_SCORE + " INTEGER, "
					+ TIMETRACKER_COLUMN_CLASS + " TEXT, "
					+ TIMETRACKER_COLUMN_TESTDATE + " TEXT )"
			);
			
			database.execSQL(
				"CREATE TABLE " + USER_TABLE_NAME + "( "
					+ PROFILE_COLUMN_ID + " INTEGER PRIMARY KEY, "
					+ PROFILE_COLUMN_USERNAME + " TEXT, "
					+ PROFILE_COLUMN_GENDER + " TEXT, "
					+ PROFILE_COLUMN_AGE + " TEXT )"
			);
		}
		
		// when the version number is changed, this method is called
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			// drops table if exists, and then calls onCreate which implements our new schema
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			database.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME); 
			onCreate(database);
		}
	}
	
	// method called by onActivityResult which actually saves user entered data into the db
	public void saveTimeRecord(String runTime, Integer sitUps, Integer pullUps, String userName, Integer score, String userClass, String testDate) { 
		ContentValues contentValues = new ContentValues();
		contentValues.put(TIMETRACKER_COLUMN_RUNTIME, runTime); 
		contentValues.put(TIMETRACKER_COLUMN_SITUPS, sitUps);
		contentValues.put(TIMETRACKER_COLUMN_PULLUPS, pullUps); 
		contentValues.put(TIMETRACKER_COLUMN_USERNAME, userName);
		contentValues.put(TIMETRACKER_COLUMN_SCORE, score);
		contentValues.put(TIMETRACKER_COLUMN_CLASS, userClass);
		contentValues.put(TIMETRACKER_COLUMN_TESTDATE, testDate); 
		database.insert(TABLE_NAME, null, contentValues);
	}
	
	// method that saves the users profile information
	public void saveUserProfile(String userName, String userGender, String userAge) { 
		
		// Clears the legacy profile information first
		database.execSQL("DELETE FROM user_profile;");
		
		// Writes the new profile information
		ContentValues contentValues = new ContentValues();
		contentValues.put(PROFILE_COLUMN_USERNAME, userName); 
		contentValues.put(PROFILE_COLUMN_GENDER, userGender);
		contentValues.put(PROFILE_COLUMN_AGE, userAge); 
		database.insert(USER_TABLE_NAME, null, contentValues);
	}
	
	// null because there are no selection args since we are just selecting everything
	public Cursor getUserProfileInfo() { 
		return database.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
	}
	
	// null because there are no selection args since we are just selecting everything
	public Cursor getAllTimeRecords() { 
		return database.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + TIMETRACKER_COLUMN_ID + " DESC", null);
	}
	
	// null because there are no selection args since we are just selecting everything
	public Cursor getSingleTimeRecord(String list_view_row_id) { 
		return database.rawQuery("SELECT * FROM pft_test_records WHERE _id = " + list_view_row_id + ";", null);
	}
	
	public void deleteSingleRecord(String list_view_row_id) { 
		database.execSQL("DELETE FROM pft_test_records WHERE _id = " + list_view_row_id + ";");
	}
}