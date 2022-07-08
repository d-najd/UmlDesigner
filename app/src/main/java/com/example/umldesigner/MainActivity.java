package com.example.umldesigner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.umldesigner.uml_activity.logic.UmlObjectFactory;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.example.umldesigner.uml_activity.views.UmlBackground;
import com.example.umldesigner.uml_activity.UmlListeners;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ReceiverInterface{
    private ViewGroup container;
    private UmlObjectFactory umlObjectFactory;
    public static float dp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dp = getResources().getDisplayMetrics().density;
        float spacing = UmlSingleton.spacing;
        container = findViewById(R.id.container);
        
        listeners();
        
        umlObjectFactory = new UmlObjectFactory(container, new CreateUmlGrid(container).getListeners());
    
        UmlSingleton.getInstance();
        
        ArrayList<Object> umlAdapterFieldArrayList = new ArrayList<>(Arrays.asList(
                new UmlAdapterFieldData("ProductId", "int"), new UmlAdapterFieldData("ProductName", "varchar(100)")));
    
        container.addView((View) umlObjectFactory.create("umltable", "stonks", new float[]{3 * spacing, 3 * spacing}, umlAdapterFieldArrayList));
        container.addView((View) umlObjectFactory.create("arrow",
                new float[]{3.5f * spacing, 13f * spacing, 9.5f * spacing, 18f * spacing}));
    }
    
    /**
     * @apiNote unable to store this anywhere as a static reference because it is part of view and
     * it will cause static memory leak
     */
    public UmlObjectFactory getUmlObjectFactory() {
        return umlObjectFactory;
    }
    
    public ViewGroup getContainer(){
        return container;
    }
    
    private void listeners(){
        View fab = findViewById(R.id.createTableFab);
        
        fab.setOnClickListener(new MainActivityListener(this));
    }
    
    @Override
    public boolean receiveData(Object sentData) {
        return false;
    }
}

class CreateUmlGrid {
    UmlListeners umlListeners;
    
    public CreateUmlGrid(ViewGroup container){
        UmlBackground umlBackground = new UmlBackground(container.getContext());
        umlBackground.setMinimumWidth(50000);
        umlBackground.setMinimumHeight(50000);
        container.addView(umlBackground);
    
        umlListeners = new UmlListeners(umlBackground);
    
        ImageButton gridColliders = new ImageButton(container.getContext());
        gridColliders.setMinimumWidth(50000);
        gridColliders.setMinimumHeight(50000);
        gridColliders.setBackgroundColor(Color.parseColor("#00000000"));
        gridColliders.setTag("gridColliders");
        gridColliders.setOnDragListener(umlListeners);
        gridColliders.setPadding(150, 150, 0, 0);
        container.addView(gridColliders);
    }
    
    public UmlListeners getListeners(){
        return umlListeners;
    }
}