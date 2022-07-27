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
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.UmlObject;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

import java.util.ArrayList;

public class STableView extends ConstraintLayout implements UmlObject {
    
    private STableData data;
    private final SSettingsSingleton umlSettingsInstance;
    
    private View v;
    
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
     * @param SItemData data of all of the fields in the recyclerview
     * @implNote the collision is not done here and each field in the recyclerView should have a
     * different collider (not the same as the title) as to be able to set connections with only
     * dragging the field and change its settings
     */
    public STableView(@NonNull Context context, String title, float x, float y,
                      ArrayList<SItemData> SItemData){
        super(context);
        umlSettingsInstance = SSettingsSingleton.getInstance();
        
        //setting up the data
        //TODO make this add the data of the items as well
        setData(new STableData(umlSettingsInstance.getNextId(), x, y, title));
        umlSettingsInstance.allViewTagsPut(data.getId(), this);
        
        //inflating the view
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.card_uml_table, this, true);
    
        //setting the fields inside the table
        TextView titleTextView = v.findViewById(R.id.title);
        this.setId(data.getId());
        this.setX(data.getX());
        this.setY(data.getY());
        this.setElevation(umlSettingsInstance.getTABLE_ELEVATION());
        this.setBackgroundColor(2131034697); //color of R.color.red somehow
        titleTextView.setText(data.getTitle());
    
        //setting up the recyclerview
        RecyclerView umlTableRecyclerView = v.findViewById(R.id.uml_table_recyclerView);
        UmlAdapter adapter = new UmlAdapter(SItemData, v.getContext());
    
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
        
        updateData();
    }
    
    @Override
    public void destroy() {
        throw new UnsupportedOperationException("destroying of UmlTable not implemented");
    }
    
    @Override
    public <T extends BasePojo & BaseDataInterface> void setData(T data) {
        this.data = (STableData)data;
    }
    
    @Override
    public void updateData() {
        //prep
        TextView titleTextView = v.findViewById(R.id.title);
   
        //updating the data
        data.setTitle(titleTextView.getText().toString());
        data.setX(this.getX());
        data.setY(this.getY());
    }
}