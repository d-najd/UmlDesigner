package com.example.umldesigner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.umldesigner.uml_activity.logic.UmlObjectFactory;
import com.example.umldesigner.uml_activity.recycler.UmlAdapter;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterField;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterTable;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterTableData;
import com.example.umldesigner.uml_activity.views.UmlArrowView;
import com.example.umldesigner.uml_activity.views.UmlBackground;
import com.example.umldesigner.uml_activity.UmlListeners;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;
import com.example.umldesigner.uml_activity.views.UmlTableView;

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
        float spacedDp = spacing * dp;
        container = findViewById(R.id.container);
        
        addGrid();
        
        umlObjectFactory = new UmlObjectFactory(container, umlListeners);


        container.addView((View) umlObjectFactory.create("arrow",
                new float[]{3.5f * spacedDp, 10f * spacedDp, 9.5f * spacedDp, 15f * spacedDp}));
    
        RecyclerView umlTableRecyclerView = container.findViewById(R.id.uml_table_recyclerView);
        ConstraintLayout umlTableLayout = container.findViewById(R.id.includeTest);
    
        container.addView((View) umlObjectFactory.create("umltable", "stonks", new float[]{200, 200}));
        
        umlTableLayout.setElevation(UmlSingleton.TABLE_ELEVATION);
        
        ArrayList<UmlAdapterFieldData> umlAdapterFieldArrayList = new ArrayList<>(Arrays.asList(
                new UmlAdapterFieldData("ProductId", "int"), new UmlAdapterFieldData("ProductName", "varchar(100)")));
    
        ArrayList<UmlAdapterTableData> umlAdapterTableDataArrayList = new ArrayList<>();
        umlAdapterTableDataArrayList.add(new UmlAdapterTableData("Product", 0, 0, umlAdapterFieldArrayList));
        
        UmlAdapter adapter = new UmlAdapter(umlAdapterFieldArrayList, this);
    
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        umlTableRecyclerView.setLayoutManager(layoutManager);
        umlTableRecyclerView.setAdapter(adapter);
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

    @Override
    public boolean receiveData(Object sentData) {
        return false;
    }
}