package com.example.sunzhijun.szjgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by sunzhijun on 2017/12/1.
 */

public class RabbitView extends View {
    public float bitmapX;
    public float bitmapY;

    public RabbitView(Context context) {
        super(context);
        bitmapX = 750;
        bitmapY = 500;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.rabbit);
        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);
        if (bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
