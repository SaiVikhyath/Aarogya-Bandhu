package com.example.aarogyabandhu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Symptoms.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Symptoms";
    private static final String COLUMN_ID = "ID";
    private static final String HEART_RATE = "heartRate";
    private static final String RESPIRATORY_RATE = "respiratoryRate";
    private static final String NAUSEA = "nausea";
    private static final String HEADACHE = "headAche";
    private static final String DIARRHEA = "diarrhea";
    private static final String SORE_THROAT = "soreThroat";
    private static final String FEVER = "fever";
    private static final String MUSCLE_ACHE = "muscleAche";
    private static final String LOSS_OF_SMELL_OR_TASTE = "lossOfSmellOrTaste";
    private static final String COUGH = "cough";
    private static final String SHORTNESS_OF_BREATH = "shortnessOfBreath";
    private static final String FEELING_TIRED = "feelingTired";

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HEART_RATE + " FLOAT DEFAULT 0, " +
            RESPIRATORY_RATE + " FLOAT DEFAULT 0, " +
            NAUSEA + " FLOAT DEFAULT 0, " +
            HEADACHE + " FLOAT DEFAULT 0, " +
            DIARRHEA + " FLOAT DEFAULT 0, " +
            SORE_THROAT + " FLOAT DEFAULT 0, " +
            FEVER + " FLOAT DEFAULT 0, " +
            MUSCLE_ACHE + " FLOAT DEFAULT 0, " +
            LOSS_OF_SMELL_OR_TASTE + " FLOAT DEFAULT 0, " +
            COUGH + " FLOAT DEFAULT 0, " +
            SHORTNESS_OF_BREATH + " FLOAT DEFAULT 0, " +
            FEELING_TIRED + " FLOAT DEFAULT 0" + ")";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
            Log.i("DATABASE", "CREATED SUCCESSFULLY");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("ERROR", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertRecords(Symptoms symptoms) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HEART_RATE, symptoms.heartRate);
        values.put(RESPIRATORY_RATE, symptoms.respiratoryRate);
        values.put(NAUSEA, symptoms.nausea);
        values.put(HEADACHE, symptoms.headAche);
        values.put(DIARRHEA, symptoms.diarrhea);
        values.put(SORE_THROAT, symptoms.soreThroat);
        values.put(FEVER, symptoms.fever);
        values.put(MUSCLE_ACHE, symptoms.muscleAche);
        values.put(LOSS_OF_SMELL_OR_TASTE, symptoms.lossOfSmellOrTaste);
        values.put(COUGH, symptoms.cough);
        values.put(SHORTNESS_OF_BREATH, symptoms.shortnessOfBreath);
        values.put(FEELING_TIRED, symptoms.feelingTired);
        long rowID = sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return rowID == -1;
    }

    public boolean updateRecords(Symptoms symptoms) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HEART_RATE, symptoms.heartRate);
        values.put(RESPIRATORY_RATE, symptoms.respiratoryRate);
        values.put(NAUSEA, symptoms.nausea);
        values.put(HEADACHE, symptoms.headAche);
        values.put(DIARRHEA, symptoms.diarrhea);
        values.put(SORE_THROAT, symptoms.soreThroat);
        values.put(FEVER, symptoms.fever);
        values.put(MUSCLE_ACHE, symptoms.muscleAche);
        values.put(LOSS_OF_SMELL_OR_TASTE, symptoms.lossOfSmellOrTaste);
        values.put(COUGH, symptoms.cough);
        values.put(SHORTNESS_OF_BREATH, symptoms.shortnessOfBreath);
        values.put(FEELING_TIRED, symptoms.feelingTired);
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(1)};
        int numberOfRecordsUpdated = sqLiteDatabase.update(TABLE_NAME, values, whereClause, whereArgs);
        sqLiteDatabase.close();
        return numberOfRecordsUpdated == -1;
    }

    public List<Symptoms> retrieveRecords() {
        List<Symptoms> symptoms = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {HEART_RATE, RESPIRATORY_RATE, NAUSEA, HEADACHE, DIARRHEA, SORE_THROAT, FEVER, MUSCLE_ACHE, LOSS_OF_SMELL_OR_TASTE, COUGH, SHORTNESS_OF_BREATH, FEELING_TIRED};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                float heart_rate = cursor.getFloat(cursor.getColumnIndex(HEART_RATE));
                float respiratory_rate = cursor.getFloat(cursor.getColumnIndex(RESPIRATORY_RATE));
                float nausea = cursor.getFloat(cursor.getColumnIndex(NAUSEA));
                float headache = cursor.getFloat(cursor.getColumnIndex(HEADACHE));
                float diarrhea = cursor.getFloat(cursor.getColumnIndex(DIARRHEA));
                float sore_throat = cursor.getFloat(cursor.getColumnIndex(SORE_THROAT));
                float fever = cursor.getFloat(cursor.getColumnIndex(FEVER));
                float muscle_ache = cursor.getFloat(cursor.getColumnIndex(MUSCLE_ACHE));
                float loss_of_smell_or_taste = cursor.getFloat(cursor.getColumnIndex(LOSS_OF_SMELL_OR_TASTE));
                float cough = cursor.getFloat(cursor.getColumnIndex(COUGH));
                float shortness_of_breath = cursor.getFloat(cursor.getColumnIndex(SHORTNESS_OF_BREATH));
                float feeling_tired = cursor.getFloat(cursor.getColumnIndex(FEELING_TIRED));

                Symptoms gettingSymptoms = new Symptoms(heart_rate, respiratory_rate, nausea, headache, diarrhea, sore_throat, fever, muscle_ache, loss_of_smell_or_taste, cough, shortness_of_breath, feeling_tired);
                symptoms.add(gettingSymptoms);
                break;
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return symptoms;
    }

}
