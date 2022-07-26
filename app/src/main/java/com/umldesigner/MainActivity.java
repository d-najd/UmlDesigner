package com.umldesigner;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.activities.uml_activity.recyclers.data.UmlAdapterFieldDataDataImpl;
import com.umldesigner.activities.uml_activity.views.SBackground;
import com.umldesigner.infrastructure.uml.logic.SObjectFactory;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ReceiverInterface{
    private ViewGroup container;
    private SObjectFactory sObjectFactory;
    public static float dp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dp = getResources().getDisplayMetrics().density;
        float spacing = SSettingsSingleton.getInstance().getSpacing();
        container = findViewById(R.id.container);
        
        listeners();
        
        sObjectFactory = new SObjectFactory(container, new CreateUmlGrid(container).getListeners());
    
        SSettingsSingleton.getInstance();
        
        ArrayList<Object> umlAdapterFieldArrayList = new ArrayList<>(Arrays.asList(
                new UmlAdapterFieldDataDataImpl("ProductId", "int"), new UmlAdapterFieldDataDataImpl("ProductName", "varchar(100)")));
    
        container.addView((View) sObjectFactory.create("umltable", "stonks", new float[]{3 * spacing, 3 * spacing}, umlAdapterFieldArrayList));
        container.addView((View) sObjectFactory.create("arrow",
                new float[]{3.5f * spacing, 13f * spacing, 9.5f * spacing, 18f * spacing}));
    }
    
    /**
     * @apiNote unable to store this anywhere as a static reference because it is part of view and
     * it will cause static memory leak
     */
    public SObjectFactory getUmlObjectFactory() {
        return sObjectFactory;
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
    SListeners sListeners;
    
    public CreateUmlGrid(ViewGroup container){
        SBackground sBackground = new SBackground(container.getContext());
        sBackground.setMinimumWidth(50000);
        sBackground.setMinimumHeight(50000);
        container.addView(sBackground);
    
        sListeners = new SListeners(sBackground);
    
        ImageButton gridColliders = new ImageButton(container.getContext());
        gridColliders.setMinimumWidth(50000);
        gridColliders.setMinimumHeight(50000);
        gridColliders.setBackgroundColor(Color.parseColor("#00000000"));
        gridColliders.setTag("gridColliders");
        gridColliders.setOnDragListener(sListeners);
        gridColliders.setPadding(150, 150, 0, 0);
        container.addView(gridColliders);
    }
    
    public SListeners getListeners(){
        return sListeners;
    }
}