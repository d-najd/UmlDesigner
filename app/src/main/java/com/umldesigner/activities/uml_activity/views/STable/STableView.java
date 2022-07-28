package com.umldesigner.activities.uml_activity.views.STable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.recyclers.SAdapter;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.SObject;
import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.pojos.BasePojo;

import java.util.ArrayList;


public class STableView extends ConstraintLayout implements SObject {
    
    private final SSettingsSingleton umlSettingsInstance;
    private STableData data;
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
     * TODO there is a bug if there are no items the table is not movable
     *
     * @param           builder which holds all of the data
     * @implNote        builder is used because we want to make sure STableView is made from the
     *                  builder
     */
    public STableView(STableBuilder builder){
        super(builder.getContext());
        umlSettingsInstance = SSettingsSingleton.getInstance();
    
        //setting up the data
        setData(new STableData(umlSettingsInstance.getNextId(), builder.getX(), builder.getY(), builder.getTitle(), builder.getItems()));
        umlSettingsInstance.allViewTagsPut(data.getId(), this);
    
        //inflating the view
        LayoutInflater inflater = LayoutInflater.from(builder.getContext());
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
    
            LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
            umlTableRecyclerView.setLayoutManager(layoutManager);
        
        if (builder.getItems() != null){
    
            SAdapter adapter = new SAdapter(builder.getItems(), v.getContext());
            umlTableRecyclerView.setAdapter(adapter);
        }
        
        updateData(); //making sure nothing breaks in the future
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
        this.data = (STableData) data;
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
    
    /**
     * sets the title of the Table View but does not guarantee updating the data, use {@link #updateData()} for that
     *
     * @param title the title that we are setting the view to
     * @see #updateData()
     */
    public void setTitle(String title) {
        TextView titleTextView = v.findViewById(R.id.title);
        
        titleTextView.setText(title);
    }
    
    /**
     * sets the itemData to given ArrayList but does not guarantee updating the data, use {@link #updateData()} for that
     *
     * @param itemDataArrayList the given itemArrayList
     * @see #updateData()
     */
    public void setSItemData(ArrayList<SItemData> itemDataArrayList) {
        data.setItems(itemDataArrayList);
        
        RecyclerView umlTableRecyclerView = v.findViewById(R.id.uml_table_recyclerView);
        SAdapter adapter = new SAdapter(itemDataArrayList, v.getContext());
        
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        umlTableRecyclerView.setLayoutManager(layoutManager);
        umlTableRecyclerView.setAdapter(adapter);
    }
}