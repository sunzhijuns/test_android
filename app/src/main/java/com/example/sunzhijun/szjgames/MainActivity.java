package com.example.sunzhijun.szjgames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mylayout);
        final RabbitView rabbitView = new RabbitView(MainActivity.this);
        rabbitView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                rabbitView.bitmapX = motionEvent.getX();
                rabbitView.bitmapY = motionEvent.getY();
                rabbitView.invalidate();
                return true;
            }
        });
        frameLayout.addView(rabbitView);

    }
}
