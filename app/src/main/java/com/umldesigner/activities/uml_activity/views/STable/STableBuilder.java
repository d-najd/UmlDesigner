package com.umldesigner.activities.uml_activity.views.STable;

import android.content.Context;

import androidx.annotation.NonNull;

import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class STableBuilder {
    private final Context context;
    private final float x;
    private final float y;
    private final String title;
    private final SListeners listeners;
    private ArrayList<SItemData> items;
    
    /**
     * builder used for creating schema tables
     * @param context context for the table
     * @param title title for the table
     * @param x position for the table, NOTE this is in positions of the grid (circles) not absolute
     * @param y position for the table, NOTE this is in positions of the grid (circles) not absolute
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
    }
    
    public STableBuilder addItems(ArrayList<SItemData> items) {
        this.items = items;
        return this;
    }
    
    public STableView build(){
        STableView tableView = new STableView(this);
        tableView.setOnTouchListener(listeners);
        return tableView;
    }
}
