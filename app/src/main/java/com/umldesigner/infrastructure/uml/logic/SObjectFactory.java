package com.umldesigner.infrastructure.uml.logic;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.activities.uml_activity.recyclers.data.UmlAdapterFieldDataDataImpl;
import com.umldesigner.activities.uml_activity.views.SArrowPart;
import com.umldesigner.activities.uml_activity.views.SArrowView;
import com.umldesigner.activities.uml_activity.views.STableView;
import com.umldesigner.infrastructure.uml.interfaces.UmlObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * rework this so it uses builder and uses enum instead of String type
 */
public class SObjectFactory {
    private final SListeners sListeners;
    private final ViewGroup container;
   
    public SListeners getUmlListeners(){
        return sListeners;
    }
    
    public SObjectFactory(ViewGroup container, SListeners sListeners){
        this.sListeners = sListeners;
        this.container = container;
    }
    
    public UmlObject create(String type, float[] positions){
        return create(type, null, positions, null, null);
    }
    
    public UmlObject create(String type, String title, float[] positions){
        return create(type, title, positions, null, null);
    }
    
    public UmlObject create(String type, String title, float[] positions, ArrayList<Object> objects){
        return create(type, title, positions, null,  objects);
    }
    
    public UmlObject create(String type, float[] positions, View[] views){
        return create(type, null, positions, views, null);
    }
    
    @SuppressLint("ClickableViewAccessibility")
    public UmlObject create(String type, String title, float[] positions, View[] views, ArrayList<Object> objects){
        switch (type.toLowerCase(Locale.ROOT)){
            case "arrow":
                return createUmlArrow(positions, views);
            case "umltable":
                return createUmlTable(title, positions, objects);
            default:
                    throw new IllegalStateException("invalid type: " + type);
        }
    }
    
    @NonNull
    private SArrowView createUmlArrow(float[] positions, View[] views) {
        if(views != null && views.length > 2)
            throw new IllegalStateException("creating arrows requires at least 1 and no more than 3 views in");
        if(positions.length != 4)
            throw new IllegalStateException("creating arrows requires 4 positions, xStart, xEnd, yStart, yEnd in");
        if(sListeners == null)
            throw new IllegalStateException("creating arrows requires umlListeners");
        View view1 = views != null && views.length > 0 ? views[0] : null;
        View view2 = view1 != null && views.length > 1 ? views[1] : null;
        return new SArrowView(container, positions[0], positions[1],
                positions[2], positions[3], (SArrowPart) view1, (SArrowPart) view2,
                sListeners);
    }
    
    @NonNull
    private STableView createUmlTable(String title, float[] positions, ArrayList<Object> objects) {
        if (title == null)
            throw new IllegalStateException("creating UmlTables requires them to have a name");
        if (positions.length != 2)
            throw new IllegalStateException("creating UmlTables requires them to have x and y position, nothing more nothing less");
                
                /* turns the arraylist of Objects into a arraylist of UmlAdapterFieldData, basically
                    just casts it, and if the objects are null creates new arraylist
                 */
        ArrayList<UmlAdapterFieldDataDataImpl> tableData = objects != null ?
                (ArrayList<UmlAdapterFieldDataDataImpl>) objects.parallelStream()
                .map(e -> (UmlAdapterFieldDataDataImpl) e).collect(Collectors.toList()) : new ArrayList<>();
        STableView sTableView = new STableView(container.getContext(), title, positions[0], positions[1],
                (ArrayList<UmlAdapterFieldDataDataImpl>) tableData);
        sTableView.setOnTouchListener(sListeners);
        return sTableView;
    }
}
