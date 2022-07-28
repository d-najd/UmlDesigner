package com.umldesigner.infrastructure.uml.data.STable;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

import java.util.ArrayList;

import lombok.Getter;

/**
 * data field used exclusively for android
 */
public class STableData extends STablePojo implements BaseDataInterface {
    @Getter
    private Integer id;
    
    public STableData(Integer id, Float x, Float y, String title, ArrayList<SItemData> items) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.title = title;
        this.items = items;
    }
    
    public STableData(Integer id, Float x, Float y, String title){
        this.id = id;
        this.x = x;
        this.y = y;
        this.title = title;
    }
}
