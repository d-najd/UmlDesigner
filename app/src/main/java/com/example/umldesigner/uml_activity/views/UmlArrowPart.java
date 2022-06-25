package com.example.umldesigner.uml_activity.views;

import android.util.Log;
import android.view.View;

import com.example.umldesigner.Message;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;
import com.example.umldesigner.uml_activity.logic.UmlObject;
import com.example.umldesigner.uml_activity.logic.UmlObjectType;

public class UmlArrowPart extends View implements UmlObject {
    private final Integer Uuid;
    private final UmlObjectType type;
    private final UmlArrowView parent;

    public UmlArrowPart(UmlArrowView parent, UmlObjectType type) {
        super(parent.getContext());
        this.Uuid = UmlSingleton.UuidCounter++;
        this.type = type;
        this.parent = parent;
        this.setId(Uuid);
        this.setTag(type);
        UmlSingleton.allExistingViewTags.put(Uuid, this);
        this.setMinimumWidth((int) parent.colliderSize);
        this.setMinimumHeight((int) parent.colliderSize);
        setOnTouchListener(parent.umlListeners);

        parent.viewGroup.addView(this);

        double angle = UmlArrowView.calculateAngle(parent.xStart, parent.yStart, parent.xEnd, parent.yEnd);

        switch (type){
            case ArrowHead:
                this.setElevation(UmlSingleton.ARROW_HEAD_ELEVATION);
                this.setX(parent.xEnd - parent.colliderSize/2);
                this.setY(parent.yEnd - parent.colliderSize/2);
                this.setRotation((float) (angle + 180));
                break;
            case ArrowBody:
                this.setElevation(UmlSingleton.ARROW_BODY_ELEVATION);
                this.setX(parent.xStart - parent.colliderSize/2);
                this.setY(parent.yStart - parent.colliderSize/2);
                this.setRotation((float) angle + 90);
                this.setPivotY(parent.colliderSize/2);
                this.setPivotX(parent.colliderSize/2);
                this.setMinimumHeight((int) ((Math.sqrt(Math.pow(parent.xStart - parent.xEnd, 2) +
                        Math.pow(parent.yStart - parent.yEnd, 2))) +  parent.colliderSize));
                break;
            case ArrowBack:
                this.setElevation(UmlSingleton.ARROW_BACK_ELEVATION);
                this.setX(parent.xStart - parent.colliderSize/2);
                this.setY(parent.yStart - parent.colliderSize/2);
                this.setRotation((float) (angle + 180));
                break;
            default:
                Message.defErrMessage(parent.getContext());
                Log.wtf("ERROR", "invalid tag entered in " + getClass().getSimpleName() + ": " + type.toString());
        }
    }

    @Override
    public UmlObjectType getType() {
        return type;
    }

    @Override
    public void move(float x, float y) {
        try {
            switch (type) {
                case ArrowHead:
                    parent.movePart(x, y, true);
                    break;
                case ArrowBody:
                    parent.move(x, y);
                    break;
                case ArrowBack:
                    parent.movePart(x, y, false);
                    break;
                default:
                    throw new IllegalAccessException("invalid arrow type entered " + type.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        //Message.message(getContext(), "destroying arrowpart");
        throw new UnsupportedOperationException("need to implement the method..");

        /*
        if (this.arrowsPointed.containsValue(arrowPointed)) {
            this.arrowsPointed.remove(gridFragmentArrowView, gridFragmentArrowView);
            return true;
        }
        return false;
         */
    }

    @Override
    public Integer getUuid() {
        return Uuid;
    }
}