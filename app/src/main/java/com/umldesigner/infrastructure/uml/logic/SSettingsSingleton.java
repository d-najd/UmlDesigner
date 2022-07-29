package com.umldesigner.infrastructure.uml.logic;

import com.umldesigner.MainActivity;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.SObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * a singleton which holds the schema settings and schema related stuff
 */
@Getter
public class SSettingsSingleton {
    private static SSettingsSingleton instance;

    public final float TABLE_ELEVATION = 0.5f;
    public final float ARROW_HEAD_ELEVATION = 0.12f;
    public final float ARROW_BACK_ELEVATION = 0.11f;
    public final float ARROW_BODY_ELEVATION = 0.10f;
    private final float spacing;
    /**
     * uuid counter used EXCLUSIVELY for the android app, (the server has a separate infrastructure)
     */
    @Getter(AccessLevel.NONE)
    private Integer appIdCounter;
    //the tags of all existing views/constraint layouts
    private final HashMap<Integer, SObject> allUmlObjects;
    //holds the data inside the uml tables
    private final ArrayList<STableData> umlTablesData;
    
    public Integer getNextId() {
        return appIdCounter++;
    }
    
    private SSettingsSingleton() {
        allUmlObjects = new HashMap<>();
        umlTablesData = new ArrayList<>();
        appIdCounter = 1;
        
        spacing = 19f * MainActivity.dp;
        
    }
    
    public static SSettingsSingleton getInstance() {
        if (instance == null){
            instance = new SSettingsSingleton();
        }
        return instance;
    }
    
    /**
     * puts a view to the ViewTags with given id
     */
    public void allViewTagsPut(Integer id, SObject umlObject){
        allUmlObjects.put(id, umlObject);
    }
    
    /**
     * moves uml view to given position using grid spaces
     * @param id of the view
     * @param x position in grid spaces, not absolute
     * @param y position in grid spaces, not absolute
     * @see #moveViewAbsolute(int, float, float)
     */
    public void moveView(int id, float x, float y){
        x = x * getSpacing();
        y = y * getSpacing();
        
        Objects.requireNonNull(allUmlObjects.get(id)).move(x, y);
    }
    
    /**
     * moves uml view to a given absolute position
     * @param id of the view
     * @param x absolute position
     * @param y absolute position
     * @see #moveView(int, float, float)
     */
    public void moveViewAbsolute(int id, float x, float y){
        Objects.requireNonNull(allUmlObjects.get(id)).move(x, y);
    }
}
