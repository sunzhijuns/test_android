package com.example.sunzhijun.szjgames;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setBackground(this.getDrawable(R.drawable.background));
        setContentView(frameLayout);

        TextView textView = new TextView(this);
        textView.setText("在代码中控制ui");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        textView.setTextColor(Color.rgb(1,1,1));
        frameLayout.addView(textView);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;

        textView.setLayoutParams(params);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("3-2","11");
                new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                        .setMessage("游戏有风险，进入需谨慎，进入吗？")
                        .setPositiveButton("进入",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Log.i("3-2","进入游戏");
                                    }
                                })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i("3-2","退出游戏");
                            }
                        }).show();
            }
        });



    }
}
