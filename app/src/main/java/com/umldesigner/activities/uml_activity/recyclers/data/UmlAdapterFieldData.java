package com.umldesigner.activities.uml_activity.recyclers.data;

import lombok.Getter;
import lombok.Setter;


/**
 * field of a {@link UmlAdapterTableData}
 */

@Getter
@Setter
public abstract class UmlAdapterFieldData {
    protected Integer id;
    protected String value;
    protected String type;
    //protected boolean is Primary Key
}