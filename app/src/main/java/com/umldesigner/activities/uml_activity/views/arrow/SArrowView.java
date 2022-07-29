package com.umldesigner.activities.uml_activity.views.arrow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

//https://blogs.sitepointstatic.com/examples/tech/svg-curves/cubic-curve.html

public class SArrowView extends View implements SObject {
    private final int color = Color.argb(255, 150, 150, 150);
    private Paint paint;
    
    private float firstX;
    private float firstY;
    
    private float secondX;
    private float secondY;
    
    private SSettingsSingleton settingsInstance;
    
    public SArrowView(Context context) {
        super(context);
        setupPaint();
        
        settingsInstance = SSettingsSingleton.getInstance();
        
        this.setX(1 * settingsInstance.getSpacing());
        this.setY(1 * settingsInstance.getSpacing());
       
        firstX = 0;
        firstY = 0;
        secondX = 20 * settingsInstance.getSpacing();
        secondY = 20 * settingsInstance.getSpacing();
    
        this.setMinimumWidth((int) secondX);
        this.setMinimumHeight((int) secondY);
    }
    
    @Override
    public void destroy() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public <T extends BasePojo & BaseDataInterface> void setData(T data) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void updateData() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void move(float x, float y) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       
       // drawArrow(canvas);
       // drawLine(canvas);
        drawCurve(canvas);
    }
    
    /**
     * test
     * @param canvas
     */
    public void drawArrow(Canvas canvas){
        canvas.drawLine(0, 0, 3000, 3000, paint);
    }
    
    /**
     * draws the line which sits between the 2 given tables
     * <pre>
     *  ______
     * |      |---|
     * |      |   |   <------ this line
     *  ------    |    ______
     *            |___|      |
     *                |      |
     *                 ------
     *  </pre>
     * @param canvas canvas that we draw on
     */
    public void drawLine(Canvas canvas){
        float center = secondX/2;
        
        canvas.drawLine(center, this.getY() + settingsInstance.getSpacing(),
               center,  getHeight() - settingsInstance.getSpacing(), paint);
        
    }
    
    /**
     *
     * @param canvas canvas that we draw on
     */
    public void drawCurve(Canvas canvas){
        
        float center = secondX/2;
        
        float firstX = center;
        float firstY = getY() + settingsInstance.getSpacing();
        
        Path linePath = new Path();
        linePath.moveTo(center, getY() + settingsInstance.getSpacing()); //starting point
       // linePath.lineTo(center,getY() + settingsInstance.getSpacing());
      
        float effect = .2f; //how much we want the line to be curved
        float ease = 0.35f; //works backwards, 0 is full ease 1 is no ease
       
        //applying the "effect" to the beziers (how much the line is curved)
        float bezierX = center - (settingsInstance.getSpacing() * effect);
        float bezierY = getY() + (settingsInstance.getSpacing() * effect);
        
        //applying ease to the first bezier (how far from the origin they are, closer is smoother line)
        float firstBezierX = firstX - Math.abs(bezierX - firstX) * ease;
        float firstBezierY = firstY - Math.abs(bezierY - firstY) * ease;
        
        float secondBezierX;
        
        linePath.cubicTo(
                bezierX, bezierY,
                bezierX, bezierY,
                center - settingsInstance.getSpacing(), getY());
        
       // paint.setColor(R.color.red);
        canvas.drawPath(linePath, paint);
        
        paint.setColor(Color.GREEN);
    
        canvas.drawLine(firstBezierX, firstBezierY, firstBezierX, firstBezierY, paint);
    }
    
    private Paint setupPaint(){
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        
        return paint;
    }
}