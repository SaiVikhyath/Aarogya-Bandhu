package com.example.aarogyabandhu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.Manifest;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CAMERA_PERMISSION = 1;
    public static Button measureHeartRate;
    public Button measureRespiratoryRate;
    public Button uploadSigns;
    public static Button symptomsButton;
    private TextView displayHeartRate;
    private TextView displayRespiratoryRate;
    private TextView heartRateTime;
    public static MeasureHeartRate heartRate;
    private String respiratoryRateValue = "";
    private String heartRateValue = "";
    private MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();
    public static Symptoms symptoms = new Symptoms();
    public static Database sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        sqLiteDatabase = new Database(this);
        sqLiteDatabase.getWritableDatabase();
        sqLiteDatabase.insertRecords(symptoms);

        displayHeartRate = (TextView) findViewById(R.id.displayHeartRate);
        displayRespiratoryRate = (TextView) findViewById(R.id.displayRespiratoryRate);

        heartRateTime = (TextView) findViewById(R.id.heartRateTime);

        measureHeartRate = (Button) findViewById(R.id.meaureHeartRate);
        measureHeartRate.setOnClickListener((v) -> {

            heartRate = new MeasureHeartRate(MainActivity.this, displayHeartRate, heartRateTime);

            TextureView textureView = findViewById(R.id.textureView);
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();

            if (surfaceTexture != null) {
                Surface surface = new Surface(surfaceTexture);
                if (!MainActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    Toast.makeText(MainActivity.this, "Flash not turned on. Please turn the flash on", Toast.LENGTH_LONG).show();
                }
                VideoInSurface videoInSurface = new VideoInSurface(MainActivity.this);
                videoInSurface.startVideo(surface);
                heartRate.measureHeartRate(textureView, videoInSurface);
            }
        });
        registerReceiver(broadcastReceiver, new IntentFilter(heartRateValue));


        measureRespiratoryRate = (Button) findViewById(R.id.measureRespiratoryRate);
        measureRespiratoryRate.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, MeasureRespiratoryRate.class);
            startService(intent);
        });
        registerReceiver(broadcastReceiver, new IntentFilter(respiratoryRateValue));


        symptomsButton = (Button) findViewById(R.id.symptoms);
        symptomsButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, UploadSymptoms.class);
            startActivity(intent);
        });


        uploadSigns = (Button) findViewById(R.id.uploadSigns);
        uploadSigns.setOnClickListener((v) -> {
            boolean updated = sqLiteDatabase.updateRecords(symptoms);
            if (!updated) {
                Toast.makeText(this, "Updated heart rate and respiratory rate to the database", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Unable to update heart rate and respiratory rate to the database", Toast.LENGTH_LONG).show();
            }
        });

    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            if(intent.getAction().equals(respiratoryRateValue)){
                float reading = intent.getFloatExtra("value",0);
                TextView respiratoryResult = (TextView) findViewById(R.id.displayRespiratoryRate);
                respiratoryResult.setText("Respiratory Rate:\n" + decimalFormat.format(reading));
                symptoms.respiratoryRate = reading;
            }
        }
    }


}

