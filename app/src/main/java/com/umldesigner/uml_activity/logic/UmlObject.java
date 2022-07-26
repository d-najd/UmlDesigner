package com.umldesigner.uml_activity.logic;

public interface UmlObject {
    float getX();
    float getY();
    int getWidth();
    int getHeight();
    UmlObjectType getType();
    Integer getUuid();
    void move(float x, float y);
    void destroy();
}