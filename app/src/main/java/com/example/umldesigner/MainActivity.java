package com.example.umldesigner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.umldesigner.uml_activity.MainActivityListener;
import com.example.umldesigner.uml_activity.logic.UmlObjectFactory;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.example.umldesigner.uml_activity.views.UmlBackground;
import com.example.umldesigner.uml_activity.UmlListeners;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ReceiverInterface{
    private ViewGroup container;
    public UmlObjectFactory umlObjectFactory;
    public UmlListeners umlListeners;
    public static float dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dp = getResources().getDisplayMetrics().density;
        float spacing = UmlSingleton.spacing;
        container = findViewById(R.id.container);
        
        addGrid();
        listeners();
        
        umlObjectFactory = new UmlObjectFactory(container, umlListeners);

        ArrayList<Object> umlAdapterFieldArrayList = new ArrayList<>(Arrays.asList(
                new UmlAdapterFieldData("ProductId", "int"), new UmlAdapterFieldData("ProductName", "varchar(100)")));
    
        container.addView((View) umlObjectFactory.create("umltable", "stonks", new float[]{3 * spacing, 3 * spacing}, umlAdapterFieldArrayList));
        container.addView((View) umlObjectFactory.create("arrow",
                new float[]{3.5f * spacing, 13f * spacing, 9.5f * spacing, 18f * spacing}));
    }

    //didn't want the main activity to start another activity so I went with this
    private void addGrid(){
       UmlSingleton.getInstance();
        
        UmlBackground umlBackground = new UmlBackground(this);
        umlBackground.setMinimumWidth(50000);
        umlBackground.setMinimumHeight(50000);
        container.addView(umlBackground);

        umlListeners = new UmlListeners(umlBackground);

        ImageButton gridColliders = new ImageButton(this);
        gridColliders.setMinimumWidth(50000);
        gridColliders.setMinimumHeight(50000);
        gridColliders.setBackgroundColor(Color.parseColor("#00000000"));
        gridColliders.setTag("gridColliders");
        gridColliders.setOnDragListener(umlListeners);
        gridColliders.setPadding(150, 150, 0, 0);
        container.addView(gridColliders);
    }
    
    private void listeners(){
        View fab = findViewById(R.id.createTableFab);
        
        fab.setOnClickListener(new MainActivityListener());
    }
    
    @Override
    public boolean receiveData(Object sentData) {
        return false;
    }
}