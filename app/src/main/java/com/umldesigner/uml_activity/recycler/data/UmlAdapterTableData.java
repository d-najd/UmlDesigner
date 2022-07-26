package com.umldesigner.uml_activity.recycler.data;

import java.util.ArrayList;
import java.util.HashMap;

public class UmlAdapterTableData extends UmlAdapterTable{
    public UmlAdapterTableData(String title, Integer x, Integer y) {
        this.title = title;
        this.x = x;
        this.y = y;
    }
    
    public UmlAdapterTableData(String title, Integer x, Integer y, ArrayList<UmlAdapterFieldData> valuePos) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.valuePos = valuePos;
    }
}
