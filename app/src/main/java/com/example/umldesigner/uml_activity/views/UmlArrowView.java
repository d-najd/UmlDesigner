package com.example.umldesigner.uml_activity.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.umldesigner.MainActivity;
import com.example.umldesigner.R;
import com.example.umldesigner.uml_activity.UmlListeners;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;
import com.example.umldesigner.uml_activity.logic.UmlObject;
import com.example.umldesigner.uml_activity.logic.UmlObjectType;

public class UmlArrowView extends View implements UmlObject {
    //http://blogs.sitepointstatic.com/examples/tech/canvas-curves/bezier-curve.html

    private final int color = Color.argb(255, 150, 150, 150);
    private final float dp;
    public final float colliderSize;

    //these 6 should only be used when creating the view, since the arrow position can change with moving it n stuff but these values don't update
    public final float xStart;
    public final float yStart;
    public final float xEnd;
    public final float yEnd;
    //the smaller of the *start and *end values (Math.min(*start, *end) + edgesPadding * dp)
    private final float xMinM;
    private final float yMinM;

    //the view that the head/back is following
    private UmlArrowPart headFollowView;
    private UmlArrowPart backFollowView;
    private UmlArrowPart headCollider = null;
    private UmlArrowPart backCollider = null;
    private UmlArrowPart bodyCollider = null;
    public final UmlListeners umlListeners;
    public final ViewGroup viewGroup;

    private final UmlObjectType type;
    private final Integer Uuid;

    /**
     * sets a arrow which is following view/layout's
     * <pre>
     *    __        __
     *   |  | ---> |  |
     *   |__|      |__|
     * </pre>
     * @param xStart starting x position of the arrow
     * @param yStart starting y position of the arrow
     * @param xEnd ending x position of the arrow, the arrow head is located here
     * @param yEnd ending y position of the arrow, the arrow head is located here
     * @param backFollowView the constraint layout that the back of the arrow is following, if not null xStart and yStart will be overridden
     * @param headFollowView the constraint layout that the arrow head is following, if not null xEnd and yEnd will be overridden
     * @see #UmlArrowView(ViewGroup, float, float, float, float, UmlListeners)  
     */
    public UmlArrowView(ViewGroup viewGroup,
                        float xStart, float yStart, float xEnd, float yEnd,
                        UmlArrowPart backFollowView,
                        UmlArrowPart headFollowView,
                        UmlListeners umlListeners){
        super(viewGroup.getContext());
        type = UmlObjectType.Arrow;
        Uuid = UmlSingleton.UuidCounter++;
        dp = MainActivity.dp;
        colliderSize = 36 * dp;

        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;

        //edges padding, so that the arrow displays fully and for easier grabbing
        float edgesPadding = 24 * dp;
        xMinM = Math.min(xStart, xEnd) - edgesPadding;
        yMinM = Math.min(yStart, yEnd) - edgesPadding;
        this.setX(xMinM);
        this.setY(yMinM);

        //gets multiplied by 2 to compensate for subtracting in xMin
        this.setMinimumWidth((int) (Math.max(xStart, xEnd) - Math.min(xStart, xEnd) + edgesPadding *2));
        this.setMinimumHeight((int) (Math.max(yStart, yEnd) - Math.min(yStart, yEnd) + edgesPadding *2));

        this.backFollowView = backFollowView;
        this.headFollowView = headFollowView;
        this.umlListeners = umlListeners;
        this.viewGroup = viewGroup;
        this.setTag(UmlObjectType.Arrow.toString());
        this.setId(Uuid);
        UmlSingleton.allExistingViewTags.put(Uuid, this);
    }

    /**
     * sets an arrow which doesn't follow any other view view
     * <pre>
     *           
     *    ---> 
     *         
     * </pre>
     * @param xStart starting x position of the arrow
     * @param yStart starting y position of the arrow
     * @param xEnd ending x position of the arrow, the arrow head is located here
     * @param yEnd ending y position of the arrow, the arrow head is located here
     * @see #UmlArrowView(ViewGroup, float, float, float, float, UmlArrowPart, UmlArrowPart, UmlListeners) 
     */
    public UmlArrowView (ViewGroup viewGroup,
                         float xStart, float yStart, float xEnd, float yEnd,
                         UmlListeners umlListeners){
        this(viewGroup, xStart, yStart, xEnd, yEnd, null, null, umlListeners);
    } 

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLine(canvas);
        drawArrow(canvas);
        setCollision();
    }


    @Override
    public UmlObjectType getType() {
        return type;
    }


    /**
     * moves the view and its colliders to a given position
     */
    @Override
    public void move(float x, float y) {
        //new position of the arrow (the main part that is holding all the others)
        float newX = Math.round((x - this.getWidth() / 2f) / (UmlSingleton.spacing * dp)) * UmlSingleton.spacing * dp;
        float newY = Math.round((y - this.getHeight() / 2f) / (UmlSingleton.spacing * dp)) * UmlSingleton.spacing * dp;

        //difference between the starting positions and new positions, for ex arrow starts at 100
        //and new is 150, difference is 50, the difference is used because the different arrow parts
        //have different positions than the main arrow part that is holding them all
        float difX = newX - this.getX();
        float difY = newY - this.getY();

        this.setX(newX);
        this.setY(newY);
        headCollider.setX(headCollider.getX() + difX);
        headCollider.setY(headCollider.getY() + difY);
        bodyCollider.setX(bodyCollider.getX() + difX);
        bodyCollider.setY(bodyCollider.getY() + difY);
        backCollider.setX(backCollider.getX() + difX);
        backCollider.setY(backCollider.getY() + difY);

        //when we move the arrow fully it stops following any view that it is currently following
        headFollowView = null;
        backFollowView = null;
    }

    @Override
    public void destroy() {
        //Message.message(getContext(), "destroying arrow fully");
        throw new UnsupportedOperationException("need to implement the method..");
    }

    /**
     * moves the head or back to a given position, for example
     *
     * <pre>
     *     |          /
     *     |   to    /
     *    \_/      \_/
     * </pre>
     *
     * and also sets a view to follow if its over one
     *
     * @param newXPos final x position of the arrow head/back
     * @param newYPos final y position of the arrow head/back
     * @param head if true the head gets moved, if false the back gets moved
     * @apiNote <p>moving the head also affects the position and rotation of the view and its colliders</p>
     * @implNote <h2>moving the head also removes and redraws the view and its colliders</h2>
     * @return the view created from the new positions
     */
    public View movePart(float newXPos, float newYPos, boolean head){
        //newXPos = spacing * (8 + 1.5f) * dp;
        //newYPos = spacing * (15) * dp;
        float startX = backCollider.getX() + colliderSize/2;
        float startY = backCollider.getY() + colliderSize/2;
        float endX = headCollider.getX() + colliderSize/2;
        float endY = headCollider.getY() + colliderSize/2;

        ((ViewGroup) headCollider.getParent()).removeView(headCollider);
        ((ViewGroup) bodyCollider.getParent()).removeView(bodyCollider);
        ((ViewGroup) backCollider.getParent()).removeView(backCollider);
        ((ViewGroup) this.getParent()).removeView(this);

        //checkIfOverView(newXPos, newYPos, head);

        UmlArrowView newView;
        if (head) {
            newView = new UmlArrowView(viewGroup,
                    startX, startY,
                    newXPos, newYPos, backFollowView, headFollowView, umlListeners);
        } else {
            newView = new UmlArrowView(viewGroup,
                    newXPos, newYPos,
                    endX, endY, backFollowView, headFollowView, umlListeners);
        }

        viewGroup.addView(newView);
        return newView;
    }

    @Override
    public Integer getUuid() {
        return Uuid;
    }

    /**
     * checks whether the head/back of the arrow on a given position is over some view
     * and makes it to be followed if it does
     * @param head if true checks for head if false for the back
     * @return the layout that the arrow will get constrained to
     */
/*    private GridFragmentCustomConstraintLayout checkIfOverView(float xPos, float yPos, boolean head){
        for (GridFragmentCustomConstraintLayout curView : GridFragmentSettings.allExistingViewTags) {
            if (    //basically checking if the arrow is in bounds of the current view
                    (xPos >= curView.getX() && xPos <= curView.getX() + curView.getWidth()) &&
                            (yPos >= curView.getY() && yPos <= curView.getY() + curView.getHeight())){
                if (head) {
                    curView.addPointedArrow(this, headCollider);
                    headFollowView = curView;
                }
                else {
                    curView.addPointedArrow(this, backCollider);
                    backFollowView = curView;
                }
                return curView;
            }
        }
        if (head && headFollowView != null){
            headFollowView.removePointedArrow(this, headCollider);
        } else if (!head && backFollowView != null){
            backFollowView.removePointedArrow(this, backCollider);
        }
        return null;
    }

 */




    /**
     * sets collision, one collider on the arrow head, one on back and 1 for body
     *
     * <pre>
     *     1   2   3
     *     _ _____ __
     *    | |     |  |
     *     <----------
     *
     *   1 is head, 2 is body, 3 is back
     * </pre>
     */

    private void setCollision(){
        headCollider = new UmlArrowPart(this, UmlObjectType.ArrowHead);
        bodyCollider = new UmlArrowPart(this, UmlObjectType.ArrowBody);
        backCollider = new UmlArrowPart(this, UmlObjectType.ArrowBack);

        boolean debug = false;
        if (debug){
            headCollider.setBackgroundColor(getResources().getColor(R.color.green));
            backCollider.setBackgroundColor(getResources().getColor(R.color.red));
            bodyCollider.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }

    private void drawLine(Canvas canvas){
        Paint linePaint = linePaint();

        /*
        curved line code

        Path linePath = new Path();
        linePath.moveTo(xs, ys); //starting point
        linePath.cubicTo(xb, yb, xb, yb, xe, ye); //first 2 are the curves last one is ending pos
        canvas.drawPath(linePath, linePaint);
         */

        canvas.drawLine(xStart - xMinM, yStart - yMinM, xEnd - xMinM, yEnd - yMinM, linePaint);
    }

    private void drawArrow(Canvas canvas){
        Paint arrowPaint = arrowPaint();

        float[][] arrowPaths = getArrowPaths();
        Path arrowPath = new Path(); {
            arrowPath.moveTo(arrowPaths[0][0] - xMinM, arrowPaths[0][1] - yMinM);
            arrowPath.lineTo(arrowPaths[1][0] - xMinM, arrowPaths[1][1] - yMinM);
            arrowPath.lineTo(arrowPaths[2][0] - xMinM, arrowPaths[2][1] - yMinM);
            arrowPath.lineTo(arrowPaths[0][0] - xMinM, arrowPaths[0][1] - yMinM);
        }

        canvas.drawPath(arrowPath, arrowPaint);
    }

    /**
     * @apiNote they are rotated along the arrow line so that the arrow follows its rotation
     * @return gets the arrow head point locations
     */
    private float[][] getArrowPaths(){
        float xScaling = .45f, yScaling = .6f;
        float spacing = UmlSingleton.spacing;

        float[][] arrowPaths = {
                {-1 * spacing * dp * xScaling + xEnd , .5f * spacing * dp * yScaling + yEnd}, //left
                {0 * spacing * dp * xScaling + xEnd , -.5f * spacing * dp * yScaling + yEnd}, //top
                {1 * spacing * dp * xScaling + xEnd, .5f * spacing * dp * yScaling + yEnd} //right
        };

        double angle = calculateAngle(xStart, yStart, xEnd, yEnd);

        for (int i = 0; i < arrowPaths.length; i++){
            Point rotatedPoint = rotate_point(xEnd, yEnd, (angle - 90) * Math.PI/180, new Point(arrowPaths[i][0], arrowPaths[i][1]));
            arrowPaths[i][0] = rotatedPoint.x;
            arrowPaths[i][1] = rotatedPoint.y;
        }

        return arrowPaths;
    }

    /**
     * rotates a point along a a given angle and a point of rotation
     *
     * <pre>
     *     ___________________________
     *     |
     *     |      (pointEnd)
     *     |          |
     *     |          |  angle (90 in this case)
     *     |          |
     *     |        cx,cy-------(pointStart)
     *     |
     * </pre>
     * @param cx the x position of where we want the starting point to be rotated around
     * @param cy the y position of where we want the starting point to be rotated around
     * @param angle the number of degrees we want to rotate
     * @param p the starting point
     * @return rotated {@link UmlArrowView.Point} object
     * @see #calculateAngle(float, float, float, float)
     */
    public static Point rotate_point(double cx, double cy, double angle, Point p) {
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        // translate point back to origin:
        p.x -= cx;
        p.y -= cy;
        // rotate point
        double xNew = p.x * c - p.y * s;
        double yNew = p.x * s + p.y * c;
        // translate point back:
        p.x = (float) (xNew + cx);
        p.y = (float) (yNew + cy);
        return p;
    }

    /**
     * Work out the angle from the x horizontal winding anti-clockwise
     * in screen space.
     *
     * <pre>
     * x,y -------------
     *     |  1,1
     *     |    \
     *     |     \
     *     |     2,2
     * </pre>
     * @return - a double from 0 to 360
     * @see #rotate_point(double, double, double, Point)
     */

    public static double calculateAngle(float xStart, float yStart, float xEnd, float yEnd){
        double deltaX = xStart - xEnd;
        double deltaY = yStart - yEnd;

        double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return (result < 0) ? (360d + result) : result;
    }

    private Paint linePaint(){
        Paint linePaint = new Paint();
        linePaint.setColor(color);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(15);

        return linePaint;
    }

    private Paint arrowPaint(){
        Paint arrowPaint = new Paint();
        arrowPaint.setColor(color);
        arrowPaint.setAntiAlias(true);
        arrowPaint.setStrokeCap(Paint.Cap.ROUND);
        arrowPaint.setStrokeJoin(Paint.Join.ROUND);
        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //the smoothness of the edges, the bigger the smoother they are and the bigger the arrow is
        arrowPaint.setStrokeWidth(3);

        return arrowPaint;
    }

    /**
     * nothing else wants to work, I will go crazy if I spend another hour trying to figure out why it doesn't work
     */
    static class Point
    {
        public float x;
        public float y;

        public Point(float x, float y){
            this.x = x;
            this.y = y;
        }
    }
}