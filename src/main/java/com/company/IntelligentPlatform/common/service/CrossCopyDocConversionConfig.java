package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;

import java.util.List;

public class CrossCopyDocConversionConfig {

    protected int sourceDocType;

    protected int targetDocType;

    protected List<CrossCopyPartyConversionConfig> crossCopyPartyConversionConfigList;

    protected int crossAttachmentFlag;

    protected int triggerSourceActionCode = 0;

    protected int activeCustomSwitch = StandardSwitchProxy.SWITCH_ON;

    protected int copyPartyToItemParty = 0;

    /**
     * When binding the prev-next relationship, weather also need set reserving relationship to prev[source] doc item
     */
    protected int reserveOnSrc = StandardSwitchProxy.SWITCH_OFF;

    /**
     * When create new doc item from current main doc item, weather need to bind prev-next relationship to the newly
     * created doc item, usually in [RESERVED] scenario
     */
    protected int targetOnCreate = StandardSwitchProxy.SWITCH_ON;

    /**
     * When prev-next relationship is removed, weather need to remove the reservation relationship, usually in
     * [RESERVED] scenario
     */
    protected int cleanReserveOnSrc = StandardSwitchProxy.SWITCH_ON;

    public CrossCopyDocConversionConfig() {
    }

    public CrossCopyDocConversionConfig(int sourceDocType, int targetDocType,
                                    List<CrossCopyPartyConversionConfig> crossCopyPartyConversionConfigList,
                                        int crossAttachmentFlag, int triggerSourceActionCode, int copyPartyToItemParty) {
        this.sourceDocType = sourceDocType;
        this.targetDocType = targetDocType;
        this.crossCopyPartyConversionConfigList = crossCopyPartyConversionConfigList;
        this.crossAttachmentFlag = crossAttachmentFlag;
        this.triggerSourceActionCode = triggerSourceActionCode;
        this.copyPartyToItemParty = copyPartyToItemParty;
    }

    public CrossCopyDocConversionConfig(int sourceDocType, int targetDocType,
                                        List<CrossCopyPartyConversionConfig> crossCopyPartyConversionConfigList,
                                        int crossAttachmentFlag, int triggerSourceActionCode,
                                        int copyPartyToItemParty, int reserveOnSrc, int targetOnCreate, int cleanReserveOnSrc) {
        this.sourceDocType = sourceDocType;
        this.targetDocType = targetDocType;
        this.crossCopyPartyConversionConfigList = crossCopyPartyConversionConfigList;
        this.crossAttachmentFlag = crossAttachmentFlag;
        this.triggerSourceActionCode = triggerSourceActionCode;
        this.copyPartyToItemParty = copyPartyToItemParty;
        this.reserveOnSrc = reserveOnSrc;
        this.targetOnCreate = targetOnCreate;
        this.cleanReserveOnSrc = cleanReserveOnSrc;
    }

    public int getSourceDocType() {
        return sourceDocType;
    }

    public void setSourceDocType(int sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    public List<CrossCopyPartyConversionConfig> getCrossCopyPartyConversionConfigList() {
        return crossCopyPartyConversionConfigList;
    }

    public void setCrossCopyPartyConversionConfigList(
            List<CrossCopyPartyConversionConfig> crossCopyPartyConversionConfigList) {
        this.crossCopyPartyConversionConfigList = crossCopyPartyConversionConfigList;
    }

    public int getCrossAttachmentFlag() {
        return crossAttachmentFlag;
    }

    public void setCrossAttachmentFlag(int crossAttachmentFlag) {
        this.crossAttachmentFlag = crossAttachmentFlag;
    }

    public int getTriggerSourceActionCode() {
        return triggerSourceActionCode;
    }

    public void setTriggerSourceActionCode(int triggerSourceActionCode) {
        this.triggerSourceActionCode = triggerSourceActionCode;
    }

    public int getCopyPartyToItemParty() {
        return copyPartyToItemParty;
    }

    public void setCopyPartyToItemParty(int copyPartyToItemParty) {
        this.copyPartyToItemParty = copyPartyToItemParty;
    }

    public int getActiveCustomSwitch() {
        return activeCustomSwitch;
    }

    public void setActiveCustomSwitch(int activeCustomSwitch) {
        this.activeCustomSwitch = activeCustomSwitch;
    }

    public int getReserveOnSrc() {
        return reserveOnSrc;
    }

    public void setReserveOnSrc(int reserveOnSrc) {
        this.reserveOnSrc = reserveOnSrc;
    }

    public int getTargetOnCreate() {
        return targetOnCreate;
    }

    public void setTargetOnCreate(int targetOnCreate) {
        this.targetOnCreate = targetOnCreate;
    }

    public int getCleanReserveOnSrc() {
        return cleanReserveOnSrc;
    }

    public void setCleanReserveOnSrc(int cleanReserveOnSrc) {
        this.cleanReserveOnSrc = cleanReserveOnSrc;
    }
}
