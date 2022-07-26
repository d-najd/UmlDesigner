package com.umldesigner.uml_activity.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.R;
import com.umldesigner.uml_activity.logic.UmlObject;
import com.umldesigner.uml_activity.logic.UmlObjectType;
import com.umldesigner.uml_activity.logic.UmlSingleton;
import com.umldesigner.uml_activity.recycler.UmlAdapter;
import com.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.umldesigner.uml_activity.recycler.data.UmlAdapterTableData;

import java.util.ArrayList;

public class UmlTableView extends ConstraintLayout implements UmlObject {
    private UmlAdapterTableData data;
    private final UmlObjectType type;
    private final Integer Uuid;
    
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
    public UmlTableView(@NonNull Context context, String title, float x, float y,
                        ArrayList<UmlAdapterFieldData> fieldData){
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_uml_table, this, true);
        
        type = UmlObjectType.Arrow;
        Uuid = UmlSingleton.UuidCounter++;
        this.setTag(UmlObjectType.Arrow.toString());
        this.setId(Uuid);
        this.setX(x);
        this.setY(y);
        this.setElevation(UmlSingleton.TABLE_ELEVATION);
        this.setBackgroundColor(2131034697); //color of R.color.red somehow
        UmlSingleton.allExistingViewTags.put(Uuid, this);
        
        TextView titleTextView = v.findViewById(R.id.title);
        titleTextView.setText(title);
    
        RecyclerView umlTableRecyclerView = v.findViewById(R.id.uml_table_recyclerView);
        UmlAdapter adapter = new UmlAdapter(fieldData, v.getContext());
    
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        umlTableRecyclerView.setLayoutManager(layoutManager);
        umlTableRecyclerView.setAdapter(adapter);
    }
    
    @Override
    public UmlObjectType getType() {
        return type;
    }
    
    @Override
    public Integer getUuid() {
        return Uuid;
    }
    
    @Override
    public void move(float x, float y) {
        float newX = Math.round((x - this.getWidth() / 2f) / (UmlSingleton.spacing)) * UmlSingleton.spacing;
        float newY = Math.round((y - this.getHeight() / 2f) / (UmlSingleton.spacing)) * UmlSingleton.spacing;
    
        this.setX(newX);
        this.setY(newY);
    }
    
    @Override
    public void destroy() {
        throw new UnsupportedOperationException("destroying of UmlTable not implemented");
    }
}
