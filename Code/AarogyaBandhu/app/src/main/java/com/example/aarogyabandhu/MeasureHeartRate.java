package com.example.aarogyabandhu;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.view.TextureView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.concurrent.CopyOnWriteArrayList;

public class MeasureHeartRate {

    Context context;
    private TextView heartRate;
    private TextView heartRateTime;
    private CountDownTimer countDown;
    private final int timePeriod = 50000;
    private final int timeInterval = 50;
    private final int initialWaitTime = 5000;
    private int timerValue;
    private int count = 0;
    private int detectedDips = 0;
    private final CopyOnWriteArrayList<Long> valleys = new CopyOnWriteArrayList<>();
    private ComputePixels computePixels;

    public MeasureHeartRate(Context context, TextView displayHeartRate, TextView heartRateTime) {
        this.context = context;
        this.heartRate = displayHeartRate;
        this.heartRateTime = heartRateTime;
    }

    private boolean detectDip() {
        final int windowSize = 15;
        CopyOnWriteArrayList<Pixels<Integer>> subList = computePixels.getFinalStandardDeviations(windowSize);
        if (subList.size() < windowSize) {
            return false;
        } else {
            Integer reference = subList.get((int) Math.ceil(windowSize / 2f)).reading;
            for (Pixels<Integer> measurement : subList) {
                if (measurement.reading < reference) return false;
            }
            return (!subList.get((int) Math.ceil(windowSize / 2f)).reading.equals(subList.get((int) Math.ceil(windowSize / 2f) - 1).reading));
        }
    }

    void measureHeartRate(TextureView textureView, VideoInSurface videoInSurface) {
        computePixels = new ComputePixels();
        detectedDips = 0;
        timerValue = (int) (timePeriod - initialWaitTime) / 1000;
        count = 0;
        countDown = new CountDownTimer(timePeriod, timeInterval) {
            @Override
            public void onTick(long l) {
                count++;
                if (initialWaitTime > count * timeInterval) {
                    if ((count * timeInterval) % 1000 == 0) {
                        heartRateTime.setText("Place your finger on the flash. Recording starts in  " + String.valueOf((int) ((initialWaitTime - count * timeInterval) / 1000)) + " secs");
                    }
                    return;
                }
                if ((count * timeInterval) % 1000 == 0) {
                    heartRateTime.setText("Remaining : " + String.valueOf(timerValue--) + " secs");
                }
                Thread thread = new Thread(() -> {
                    Bitmap bitMap = textureView.getBitmap();
                    int bitMapWidth = textureView.getWidth();
                    int bitMapHeight = textureView.getHeight();
                    int numberOfPixels = bitMapWidth * bitMapHeight;
                    int reading = 0;
                    int[] pixels = new int[numberOfPixels];
                    bitMap.getPixels(pixels, 0, bitMapWidth, 0, 0, bitMapWidth, bitMapHeight);
                    for (int i = 0; i < numberOfPixels; i++) {
                        reading += (pixels[i] >> 16) & 0xff;
                    }
                    computePixels.add(reading);
                    if (detectDip()) {
                        detectedDips = detectedDips + 1;
                        valleys.add(computePixels.getLastTimestamp().getTime());
                    }
                });
                thread.start();
            }

            @Override
            public void onFinish() {
                DecimalFormat decimalFormat = new DecimalFormat("#.###");
                CopyOnWriteArrayList<Pixels<Float>> standardDeviations = computePixels.getStandardDeviations();
                    if (valleys.size() == 0) {
                        System.out.println("Place the finger properly");
                        Toast.makeText(context, "Finger not placed properly. Unable to measure the heart rate", Toast.LENGTH_LONG).show();
                        return;
                    }
                    float pulse = 60f * (detectedDips - 1) / (Math.max(1, (valleys.get(valleys.size() - 1) - valleys.get(0)) / 1000f));
                    MainActivity.symptoms.heartRate = pulse;
                    videoInSurface.stopCamera();
                    MainActivity.heartRate.stopMeasuring();
                    heartRate.setText("Heart Rate:\n"+Float.toString(Float.parseFloat(decimalFormat.format(pulse))));
                    heartRateTime.setText("");
                }
            };
            countDown.start();
        }

        void stopMeasuring() {
            if (countDown != null) {
                countDown.cancel();
            }
        }
}


