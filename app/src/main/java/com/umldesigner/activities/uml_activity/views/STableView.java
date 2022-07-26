package com.umldesigner.activities.uml_activity.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.recyclers.UmlAdapter;
import com.umldesigner.activities.uml_activity.recyclers.data.UmlAdapterFieldDataDataImpl;
import com.umldesigner.activities.uml_activity.recyclers.data.UmlAdapterTableData;
import com.umldesigner.infrastructure.uml.interfaces.UmlObject;
import com.umldesigner.infrastructure.uml.logic.SObjectType;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.ArrayList;

import lombok.Getter;

public class STableView extends ConstraintLayout implements UmlObject {
    private UmlAdapterTableData data;
    
    /**
     * TODO switch to this kind of data
     */
    //private STablePojo data;
    private final SSettingsSingleton umlSettingsInstance;
    @Getter
    private final SObjectType type;
    private final Integer id;
    
    /**
     * creates UmlTableView at given position and with given listeners if given
     * <pre>
     *      _______________
     *     |    title     |
     *     |--------------|
     *     | field1       |
     *     | field2       |
     *     | field...     |
     *      _______________
     * </pre>
     *
     * @param title title of the table
     * @param x absolute position on the grid (not taking into account the circles)
     * @param y absolute position on the grid (not taking into account the circles)
     * @param fieldData data of all of the fields in the recyclerview
     * @implNote the collision is not done here and each field in the recyclerView should have a
     * different collider (not the same as the title) as to be able to set connections with only
     * dragging the field and change its settings
     */
    public STableView(@NonNull Context context, String title, float x, float y,
                      ArrayList<UmlAdapterFieldDataDataImpl> fieldData){
        super(context);
        umlSettingsInstance = SSettingsSingleton.getInstance();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_uml_table, this, true);
        
        type = SObjectType.Arrow;
        id = umlSettingsInstance.getNextId();
        this.setTag(SObjectType.Arrow.toString());
        this.setId(id);
        this.setX(x);
        this.setY(y);
        this.setElevation(umlSettingsInstance.getTABLE_ELEVATION());
        this.setBackgroundColor(2131034697); //color of R.color.red somehow
        umlSettingsInstance.allViewTagsPut(this.getId(), this);
        
        TextView titleTextView = v.findViewById(R.id.title);
        titleTextView.setText(title);
    
        RecyclerView umlTableRecyclerView = v.findViewById(R.id.uml_table_recyclerView);
        UmlAdapter adapter = new UmlAdapter(fieldData, v.getContext());
    
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        umlTableRecyclerView.setLayoutManager(layoutManager);
        umlTableRecyclerView.setAdapter(adapter);
    }
    
    @Override
    public void move(float x, float y) {
        float newX = Math.round((x - this.getWidth() / 2f) / (umlSettingsInstance.getSpacing())) * umlSettingsInstance.getSpacing();
        float newY = Math.round((y - this.getHeight() / 2f) / (umlSettingsInstance.getSpacing())) * umlSettingsInstance.getSpacing();
    
        this.setX(newX);
        this.setY(newY);
    }
    
    @Override
    public void destroy() {
        throw new UnsupportedOperationException("destroying of UmlTable not implemented");
    }
}
