package com.umldesigner.activities.uml_activity.views;

import android.util.Log;
import android.view.View;

import com.umldesigner.Message;
import com.umldesigner.infrastructure.uml.interfaces.UmlObject;
import com.umldesigner.infrastructure.uml.logic.SObjectType;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

public class SArrowPart extends View implements UmlObject {
    private final Integer id;
    private final SObjectType type;
    private final SArrowView parent;
    private final SSettingsSingleton umlSettingsInstance;
    
    public SArrowPart(SArrowView parent, SObjectType type) {
        super(parent.getContext());
        umlSettingsInstance = SSettingsSingleton.getInstance();
        this.id = umlSettingsInstance.getNextId();
        this.type = type;
        this.parent = parent;
        this.setId(id);
        this.setTag(type);
        umlSettingsInstance.allViewTagsPut(id, this);
        this.setMinimumWidth((int) parent.colliderSize);
        this.setMinimumHeight((int) parent.colliderSize);
        setOnTouchListener(parent.sListeners);

        parent.viewGroup.addView(this);

        double angle = SArrowView.calculateAngle(parent.xStart, parent.yStart, parent.xEnd, parent.yEnd);

        switch (type){
            case ArrowHead:
                this.setElevation(umlSettingsInstance.getARROW_HEAD_ELEVATION());
                this.setX(parent.xEnd - parent.colliderSize/2);
                this.setY(parent.yEnd - parent.colliderSize/2);
                this.setRotation((float) (angle + 180));
                break;
            case ArrowBody:
                this.setElevation(umlSettingsInstance.getARROW_BODY_ELEVATION());
                this.setX(parent.xStart - parent.colliderSize/2);
                this.setY(parent.yStart - parent.colliderSize/2);
                this.setRotation((float) angle + 90);
                this.setPivotY(parent.colliderSize/2);
                this.setPivotX(parent.colliderSize/2);
                this.setMinimumHeight((int) ((Math.sqrt(Math.pow(parent.xStart - parent.xEnd, 2) +
                        Math.pow(parent.yStart - parent.yEnd, 2))) +  parent.colliderSize));
                break;
            case ArrowBack:
                this.setElevation(umlSettingsInstance.getARROW_BACK_ELEVATION());
                this.setX(parent.xStart - parent.colliderSize/2);
                this.setY(parent.yStart - parent.colliderSize/2);
                this.setRotation((float) (angle + 180));
                break;
            default:
                Message.defErrMessage(parent.getContext());
                Log.wtf("ERROR", "invalid tag entered in " + getClass().getSimpleName() + ": " + type);
        }
    }

    @Override
    public SObjectType getType() {
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
                    throw new IllegalAccessException("invalid arrow type entered " + type);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("need to implement the method..");
    }
}