package com.umldesigner.uml_activity.logic;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.umldesigner.uml_activity.UmlListeners;
import com.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.umldesigner.uml_activity.views.UmlArrowPart;
import com.umldesigner.uml_activity.views.UmlArrowView;
import com.umldesigner.uml_activity.views.UmlTableView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

public class UmlObjectFactory {
    private final UmlListeners umlListeners;
    private final ViewGroup container;
   
    public UmlListeners getUmlListeners(){
        return umlListeners;
    }
    
    public UmlObjectFactory(ViewGroup container, UmlListeners umlListeners){
        this.umlListeners = umlListeners;
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
    private UmlArrowView createUmlArrow(float[] positions, View[] views) {
        if(views != null && views.length > 2)
            throw new IllegalStateException("creating arrows requires at least 1 and no more than 3 views in");
        if(positions.length != 4)
            throw new IllegalStateException("creating arrows requires 4 positions, xStart, xEnd, yStart, yEnd in");
        if(umlListeners == null)
            throw new IllegalStateException("creating arrows requires umlListeners");
        View view1 = views != null && views.length > 0 ? views[0] : null;
        View view2 = view1 != null && views.length > 1 ? views[1] : null;
        return new UmlArrowView(container, positions[0], positions[1],
                positions[2], positions[3], (UmlArrowPart) view1, (UmlArrowPart) view2,
                umlListeners);
    }
    
    @NonNull
    private UmlTableView createUmlTable(String title, float[] positions, ArrayList<Object> objects) {
        if (title == null)
            throw new IllegalStateException("creating UmlTables requires them to have a name");
        if (positions.length != 2)
            throw new IllegalStateException("creating UmlTables requires them to have x and y position, nothing more nothing less");
                
                /* turns the arraylist of Objects into a arraylist of UmlAdapterFieldData, basically
                    just casts it, and if the objects are null creates new arraylist
                 */
        ArrayList<UmlAdapterFieldData> tableData = objects != null ?
                (ArrayList<UmlAdapterFieldData>) objects.parallelStream()
                .map(e -> (UmlAdapterFieldData) e).collect(Collectors.toList()) : new ArrayList<>();
        UmlTableView umlTableView = new UmlTableView(container.getContext(), title, positions[0], positions[1],
                (ArrayList<UmlAdapterFieldData>) tableData);
        umlTableView.setOnTouchListener(umlListeners);
        return umlTableView;
    }
}