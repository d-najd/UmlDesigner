package com.example.umldesigner.uml_activity.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.umldesigner.R;
import com.example.umldesigner.uml_activity.logic.UmlObject;
import com.example.umldesigner.uml_activity.logic.UmlObjectType;
import com.example.umldesigner.uml_activity.logic.UmlSingleton;
import com.example.umldesigner.uml_activity.recycler.data.UmlAdapterTableData;

public class UmlTableView extends ConstraintLayout implements UmlObject {
    private UmlAdapterTableData data;
    private UmlObjectType type;
    private Integer Uuid;
    
    public UmlTableView(@NonNull Context context, String title, float x, float y) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_uml_table, this, true);
        
        type = UmlObjectType.Arrow;
        Uuid = UmlSingleton.UuidCounter++;
        this.setTag(UmlObjectType.Arrow.toString());
        this.setId(Uuid);
        this.setX(x);
        this.setY(y);
        UmlSingleton.allExistingViewTags.put(Uuid, this);
        
        //TODO set the title n shit
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
