package com.trigger.shock.triggershock;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout trigger;

    private boolean isTriggerPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trigger = (ConstraintLayout) findViewById(R.id.trigger);

        trigger.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isTriggerPressed = true;

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (isTriggerPressed) {
                                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                        VibrationEffect effect = null;
                                        effect = VibrationEffect.createOneShot(40, 255);
                                        vibrator.vibrate(effect);
                                    } else
                                        trigger.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                                    try {
                                        Thread.sleep(175);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();

                        break;
                    case MotionEvent.ACTION_UP:
                        isTriggerPressed = false;
                }

                return true;
            }
        });
    }
}
