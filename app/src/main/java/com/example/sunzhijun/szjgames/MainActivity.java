package com.example.sunzhijun.szjgames;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView[] img = new ImageView[4];
    private int[] imagePath = new int[]{
            R.drawable.img01,
            R.drawable.img02,
            R.drawable.img03,
            R.drawable.img04
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout)findViewById(R.id.layout);
        for (int i = 0; i<imagePath.length;i++){
            img[i] = new ImageView(this);
            img[i].setImageResource(imagePath[i]);
            img[i].setPadding(0,0,10,0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,200);
            img[i].setLayoutParams(layoutParams);
            layout.addView(img[i]);
        }


    }
}
