package com.example.aarogyabandhu;

public class Symptoms {
    public float heartRate;
    public float respiratoryRate;
    public float nausea;
    public float headAche;
    public float diarrhea;
    public float soreThroat;
    public float fever;
    public float muscleAche;
    public float lossOfSmellOrTaste;
    public float cough;
    public float shortnessOfBreath;
    public float feelingTired;

    Symptoms() {
        heartRate = 0;
        respiratoryRate = 0;
        nausea = 0;
        headAche = 0;
        diarrhea = 0;
        soreThroat = 0;
        fever = 0;
        muscleAche = 0;
        lossOfSmellOrTaste = 0;
        cough = 0;
        shortnessOfBreath = 0;
        feelingTired = 0;
    }

    Symptoms (float heartRate, float respiratoryRate, float nausea, float headAche, float diarrhea, float soreThroat, float fever, float muscleAche, float lossOfSmellOrTaste, float cough, float shortnessOfBreath, float feelingTired) {
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.nausea = nausea;
        this.headAche = headAche;
        this.diarrhea = diarrhea;
        this.soreThroat = soreThroat;
        this.fever = fever;
        this.muscleAche = muscleAche;
        this.lossOfSmellOrTaste = lossOfSmellOrTaste;
        this.cough = cough;
        this.shortnessOfBreath = shortnessOfBreath;
        this.feelingTired = feelingTired;
    }

}
