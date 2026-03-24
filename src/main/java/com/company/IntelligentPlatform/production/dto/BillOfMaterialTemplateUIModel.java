package com.company.IntelligentPlatform.production.dto;

import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

public class BillOfMaterialTemplateUIModel extends BillOfMaterialOrderUIModel {

    @ISEDropDownResourceMapping(resouceMapping = "BillOfMaterialTemplate_status", valueFieldName = "statusValue")
    protected int status;

    @ISEDropDownResourceMapping(resouceMapping = "BillOfMaterialTemplate_leadTimeCalMode", valueFieldName = "")
    protected int leadTimeCalMode;

    @ISEDropDownResourceMapping(resouceMapping = "BillOfMaterialItem_viewType", valueFieldName = "")
    protected int itemViewType;

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getLeadTimeCalMode() {
        return leadTimeCalMode;
    }

    @Override
    public void setLeadTimeCalMode(int leadTimeCalMode) {
        this.leadTimeCalMode = leadTimeCalMode;
    }

    @Override
    public int getItemViewType() {
        return itemViewType;
    }

    @Override
    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }
}
