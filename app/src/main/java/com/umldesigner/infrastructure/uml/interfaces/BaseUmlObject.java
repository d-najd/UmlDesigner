package com.umldesigner.infrastructure.uml.interfaces;

import com.umldesigner.infrastructure.uml.logic.SObjectType;

/**
 * Uml Object that has only the most basic of functionality, this is android specific and should
 * not be added to the shared directory
 */
public interface BaseUmlObject {
    float getX();
    float getY();
    int getWidth();
    int getHeight();
    SObjectType getType();
    void destroy();
}
