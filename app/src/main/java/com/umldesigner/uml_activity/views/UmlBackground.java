package com.umldesigner.uml_activity.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.umldesigner.uml_activity.logic.UmlSingleton;


/**
 * basically the grid on the background
 */

public class UmlBackground extends View {
    private final Paint paint = new Paint();
    private final float dp;

    public UmlBackground(Context context) {
        super(context);
        paint.setColor(Color.parseColor("#353535"));

        dp = context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //float yOff = topbar.getHeight();
        float yOff = 0;
        for (int x = 0; x < 1000; x++){
            for (int y = 0; y < 1000; y++){
                float xVal = x * UmlSingleton.spacing;
                float yVal = y * UmlSingleton.spacing + yOff;

                canvas.drawCircle(xVal, yVal, 2.0f, paint);
            }
        }
    }
}