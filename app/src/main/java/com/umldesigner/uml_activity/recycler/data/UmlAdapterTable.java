package com.umldesigner.uml_activity.recycler.data;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UmlAdapterTable {
    protected String title;
    protected Integer id = -1;
    protected Integer x;
    protected Integer y;
    /**
     * holds reference to the singleton, the positions in the list will be used to get the position
     * of each element
     */
    protected ArrayList<UmlAdapterFieldData> valuePos = null;
}
