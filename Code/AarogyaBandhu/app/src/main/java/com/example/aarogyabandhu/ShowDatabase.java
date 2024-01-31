package com.example.aarogyabandhu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowDatabase extends AppCompatActivity {
    List<Symptoms> symptoms = new ArrayList<>();
    private TextView viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_database);

        symptoms = MainActivity.sqLiteDatabase.retrieveRecords();

        viewData = (TextView) findViewById(R.id.viewData);
        viewData.setText("HeartRate | Respiratory Rate | Nausea \nHeadache | Diarrhea | Sore Throat \nFever | Muscle Ache | Loss of Smell or Taste \nCough | Shortness of Breath | Feeling Tired");
        for(int i = 0; i < symptoms.size(); i++) {
            viewData.append("\n\n" + String.valueOf(symptoms.get(i).heartRate) + " | "
            + String.valueOf(symptoms.get(i).respiratoryRate) + " | "
            + String.valueOf(symptoms.get(i).nausea) + " \n"
            + String.valueOf(symptoms.get(i).headAche) + " | "
            + String.valueOf(symptoms.get(i).diarrhea) + " | "
            + String.valueOf(symptoms.get(i).soreThroat) + " \n"
            + String.valueOf(symptoms.get(i).fever) + " | "
            + String.valueOf(symptoms.get(i).muscleAche) + " | "
            + String.valueOf(symptoms.get(i).lossOfSmellOrTaste) + " \n"
            + String.valueOf(symptoms.get(i).cough) + " | "
            + String.valueOf(symptoms.get(i).shortnessOfBreath) + " | "
            + String.valueOf(symptoms.get(i).feelingTired));
        }

    }
}