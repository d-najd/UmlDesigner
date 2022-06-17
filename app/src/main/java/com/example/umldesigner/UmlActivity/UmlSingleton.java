package com.example.umldesigner.UmlActivity;

import com.example.umldesigner.MainActivity;
import com.example.umldesigner.UmlActivity.Views.UmlObject;

import java.util.HashMap;
import java.util.UUID;

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

    public static float spacing =                 6f * MainActivity.dp;

    public static final float BOARD_LAYER =      .500f;
    public static final float ARROW_HEAD_LAYER = .012f;
    public static final float ARROW_BACK_LAYER = .011f;
    public static final float ARROW_BODY_LAYER = .010f;

    private UmlSingleton() {}

    public UmlSingleton getInstance() {
        if (firstInstance == null){
            firstInstance = new UmlSingleton();
            UuidCounter = 0;
            allExistingViewTags = new HashMap<>();
        }

        return firstInstance;
    }
}
