package com.umldesigner.infrastructure.uml.logic;

import com.umldesigner.MainActivity;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.UmlObject;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * a singleton which holds the schema settings
 */
@Getter
public class SSettingsSingleton {
    private static SSettingsSingleton instance;
    private final float TABLE_ELEVATION;
    private final float ARROW_HEAD_ELEVATION;
    private final float ARROW_BACK_ELEVATION;
    private final float ARROW_BODY_ELEVATION;
    private final float spacing;
    /**
     * uuid counter used EXCLUSIVELY for the android app, (the server has a separate infrastructure)
     */
    @Getter(AccessLevel.NONE)
    private Integer appIdCounter;
    //the tags of all existing views/constraint layouts
    private final HashMap<Integer, UmlObject> allUmlObjects;
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
        
        TABLE_ELEVATION = .500f;
        ARROW_HEAD_ELEVATION = 0.12f;
        ARROW_BACK_ELEVATION = 0.11f;
        ARROW_BODY_ELEVATION = 0.10f;
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
    public void allViewTagsPut(Integer appId, UmlObject umlObject){
        allUmlObjects.put(appId, umlObject);
    }
}
