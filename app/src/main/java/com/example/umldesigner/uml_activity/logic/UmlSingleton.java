package com.example.umldesigner.uml_activity.logic;

import com.example.umldesigner.MainActivity;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterTableData;

import java.util.ArrayList;
import java.util.HashMap;

public class UmlSingleton {
    private static UmlSingleton firstInstance = null;
    /*
        unique id for each UmlView, this should be client sided as to not reveal any unnecessary info
        NOTE I am not using UUID because I am using the id of the view to find
        which view has been pressed and that's a 32bit number and UUID uses 128bit number, plus this
        is only client sided so it doesn't matter
     */
    public static Integer UuidCounter = null;
    //the tags of all existing views/constraint layouts
    public static HashMap<Integer, UmlObject> allExistingViewTags = null;
    //holds the data inside the uml tables
    public static ArrayList<UmlAdapterTableData> umlTablesData = null;
    
    public static float spacing =                 19f * MainActivity.dp;

    public static final float TABLE_ELEVATION =  .500f;
    public static final float ARROW_HEAD_ELEVATION = .012f;
    public static final float ARROW_BACK_ELEVATION = .011f;
    public static final float ARROW_BODY_ELEVATION = .010f;

    private UmlSingleton() {}

    public static UmlSingleton getInstance() {
        if (firstInstance == null){
            firstInstance = new UmlSingleton();
            UuidCounter = 0;
            allExistingViewTags = new HashMap<>();
            umlTablesData = new ArrayList<>();
        }

        return firstInstance;
    }
}
