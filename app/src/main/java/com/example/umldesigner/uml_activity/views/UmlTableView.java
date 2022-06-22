package com.example.umldesigner.uml_activity.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.umldesigner.R;
import com.example.umldesigner.uml_activity.logic.UmlObject;
import com.example.umldesigner.uml_activity.logic.UmlObjectType;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;
import com.example.umldesigner.uml_activity.recycler.UmlAdapter;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterFieldData;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterTableData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UmlTableView extends ConstraintLayout implements UmlObject {
    private UmlAdapterTableData data;
    private UmlObjectType type;
    private Integer Uuid;
    
    public UmlTableView(@NonNull Context context, String title, float x, float y, ArrayList<UmlAdapterFieldData> fieldData){
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
        this.setX(x);
        this.setY(y);
    }
    
    @Override
    public void destroy() {
        throw new UnsupportedOperationException("destroying of UmlTable not implemented");
    }
}
