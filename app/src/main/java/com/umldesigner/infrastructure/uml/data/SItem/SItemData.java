package com.umldesigner.infrastructure.uml.data.SItem;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import lombok.Getter;
import lombok.Setter;

/**
 * data field used exclusively for android
 */
public class SItemData extends SItemPojo implements BaseDataInterface {
    @Getter
    @Setter
    private Integer id;
    
    public SItemData(String value, String type) {
        this.value = value;
        this.type = type;
    }
    
    public SItemData(String value, String type, Boolean isPrimaryKey){
        this.value = value;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
    }
}
