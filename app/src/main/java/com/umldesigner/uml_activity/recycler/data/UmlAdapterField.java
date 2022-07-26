package com.umldesigner.uml_activity.recycler.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * @implNote the position is stored in a hashmap inside the table
 */
public abstract class UmlAdapterField {
    protected Integer id = -1;
    protected String value;
    protected String type;
    //protected boolean is Primary Key
}