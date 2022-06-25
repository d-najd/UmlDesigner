package com.example.umldesigner.uml_activity.logic;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.umldesigner.uml_activity.UmlListeners;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.example.umldesigner.uml_activity.views.UmlArrowPart;
import com.example.umldesigner.uml_activity.views.UmlArrowView;
import com.example.umldesigner.uml_activity.views.UmlTableView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.zip.Inflater;

public class UmlObjectFactory {
    private UmlListeners umlListeners;
    private ViewGroup container;
    
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
            case "umltable":
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
            default:
                    throw new IllegalStateException("invalid type: " + type);
        }
    }
}
