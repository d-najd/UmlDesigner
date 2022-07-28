package com.umldesigner;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.activities.uml_activity.views.SBackground;
import com.umldesigner.activities.uml_activity.views.STable.STableView;
import com.umldesigner.activities.uml_activity.views.STable.STableBuilder;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.SObjectFactory;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ReceiverInterface{
    private ViewGroup container;
    private SObjectFactory sObjectFactory;
    public static float dp;
    public static SListeners listeners;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dp = getResources().getDisplayMetrics().density;
        float spacing = SSettingsSingleton.getInstance().getSpacing();
        container = findViewById(R.id.container);
        
        listeners = listeners();
        
        //Depricated
        sObjectFactory = new SObjectFactory(container, listeners);
        
        ArrayList<SItemData> umlAdapterFieldArrayList = new ArrayList<>(Arrays.asList(
                new SItemData("ProductId", "int"), new SItemData("ProductName", "varchar(100)")));
        
        STableView sTableView = new STableBuilder(this, listeners, "title", 1f, 1f)
                .addItems(umlAdapterFieldArrayList)
                .build();
        
        container.addView((View) sTableView);
        //depricated, replace with builder in future
        //container.addView((View) sObjectFactory.create("arrow",
        //        new float[]{3.5f * spacing, 13f * spacing, 9.5f * spacing, 18f * spacing}));
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
    
    /**
     * creates a instance of SListeners which are listeners for the background and are used for
     * setting collision for the Schema Items
     */
    private SListeners listeners(){
        View fab = findViewById(R.id.createTableFab);
        fab.setOnClickListener(new MainActivityListener(this));
     
        return new CreateUmlGrid(container).getListeners();
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