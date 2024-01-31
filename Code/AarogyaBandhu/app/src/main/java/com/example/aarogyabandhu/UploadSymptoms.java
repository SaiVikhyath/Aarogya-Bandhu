package com.example.aarogyabandhu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class UploadSymptoms extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] symptomItems = {"nausea", "headAche", "diarrhea", "soreThroat", "fever", "muscleAche", "lossOfSmellOrTaste", "cough", "shortnessOfBreath", "feelingTired"};
    private final HashMap<String, Float> symptomsRatings = new HashMap<>();
    private Spinner symptomsDropDown;
    private RatingBar ratings;
    private Button showDatabase;
    private int selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_symptoms);

        symptomsDropDown = (Spinner) findViewById(R.id.symptomsDropDown);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, symptomItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        symptomsDropDown.setAdapter(adapter);

        symptomsDropDown.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ratings = (RatingBar) findViewById(R.id.ratings);
        ratings.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                Log.i("SELECTED ITEM INDEX", String.valueOf(selectedItem));
                String symptom = (String) symptomsDropDown.getItemAtPosition(selectedItem);
//                Log.i("SYMPTOM UPLOAD", symptom + String.valueOf(v));
                symptomsRatings.put(symptom, v);
            }
        });

        showDatabase = (Button) findViewById(R.id.showDatabase);
        showDatabase.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ShowDatabase.class);
            startActivity(intent);
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedItem = i;
//        Log.i("SELECTED ITEM INDEX", String.valueOf(selectedItem));
        String selection = (String) adapterView.getItemAtPosition(i);
        Float rating = symptomsRatings.get(selection);
        if (rating != null) {
            ratings.setRating(rating);
        } else {
            ratings.setRating(0);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void uploadToDB(View view) {
//        for (int i = 0; i < symptomsRatings.size(); i++) {
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("nausea", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("headAche", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("diarrhea", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("soreThroat", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("fever", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("muscleAche", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("lossOfSmellOrTaste", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("shortnessOfBreath", (float) 0)));
//            Log.i("SYMPTOM RATING", String.valueOf(symptomsRatings.getOrDefault("feelingTired", (float) 0)));
//        }
        Symptoms symptoms = MainActivity.symptoms;
        symptoms.nausea = symptomsRatings.getOrDefault("nausea", (float) 0);
        symptoms.headAche = symptomsRatings.getOrDefault("headAche", (float) 0);
        symptoms.diarrhea = symptomsRatings.getOrDefault("diarrhea", (float) 0);
        symptoms.soreThroat = symptomsRatings.getOrDefault("soreThroat", (float) 0);
        symptoms.fever = symptomsRatings.getOrDefault("fever", (float) 0);
        symptoms.muscleAche = symptomsRatings.getOrDefault("muscleAche", (float) 0);
        symptoms.lossOfSmellOrTaste = symptomsRatings.getOrDefault("lossOfSmellOrTaste", (float) 0);
        symptoms.cough = symptomsRatings.getOrDefault("cough", (float) 0);
        symptoms.shortnessOfBreath = symptomsRatings.getOrDefault("shortnessOfBreath", (float) 0);
        symptoms.feelingTired = symptomsRatings.getOrDefault("feelingTired", (float) 0);
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.nausea));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.headAche));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.diarrhea));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.soreThroat));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.fever));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.muscleAche));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.lossOfSmellOrTaste));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.shortnessOfBreath));
//        Log.i("SYMPTOM CLASS VALUE", String.valueOf(symptoms.feelingTired));
        boolean updated = MainActivity.sqLiteDatabase.updateRecords(symptoms);
//        Log.i("DATABASE UPDATED FLAG", String.valueOf(updated));
        if (!updated) {
            Toast.makeText(this, "Symptoms updated to database successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Unable to upload symptoms to database", Toast.LENGTH_LONG).show();
        }
    }

}