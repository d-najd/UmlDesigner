package com.umldesigner.activities.uml_activity.views.table;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class STableBuilder {
    private final Context context;
    private final float x;
    private final float y;
    private final String title;
    private final SListeners listeners;
    private ArrayList<SItemData> items;
    @Getter(AccessLevel.NONE)
    private final STableView sTableView;
    
    /**
     * builder used for creating schema tables
     * @param context context for the table
     * @param title title for the table
     * @param x position in grid pieces?
     * @param y position in grid pieces?
     */
    public STableBuilder(
            @NonNull Context context,
            @NonNull SListeners listeners,
            @NonNull String title,
            float x, float y){
        this.context = context;
        this.listeners = listeners;
        this.title = title;
        this.x = x * SSettingsSingleton.getInstance().getSpacing();
        this.y = y * SSettingsSingleton.getInstance().getSpacing();
        
        sTableView = new STableView(this);
    
        //ViewGroup.LayoutParams a = sTableView.getLayoutParams();
        //a.width = 200;
        //sTableView.setLayoutParams(a);
        
        sTableView.setVisibility(View.GONE);
    }
    
    /**
     * adds all the items and sets the table for those items
     * @param items list of items
     * @return the builder
     */
    public STableBuilder addItems(ArrayList<SItemData> items) {
        for (SItemData item : items){
            item.setTable(sTableView.getData());
        }
        
        this.items = items;
        return this;
    }
    
    public STableView build(){
        sTableView.setSItemData(items);
        sTableView.updateData();
        
        sTableView.setOnTouchListener(listeners);
        sTableView.setVisibility(View.VISIBLE);
    
        return sTableView;
    }
}
