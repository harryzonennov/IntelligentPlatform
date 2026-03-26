package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.service.CrossDocConvertRequest;

import java.util.List;

/**
 * JSON Request body for Document material item list batch generation.
 */
public class DocumentMatItemBatchGenRequest implements Cloneable {

    public static class InvolvePartyGenRequest{

        private int partyRole;

        private String refPartyUUID;

        private String refPartyId;

        private String refPartyName;

        public InvolvePartyGenRequest() {
        }

        public InvolvePartyGenRequest(int partyRole, String refPartyUUID, String refPartyId, String refPartyName) {
            this.partyRole = partyRole;
            this.refPartyUUID = refPartyUUID;
            this.refPartyId = refPartyId;
            this.refPartyName = refPartyName;
        }

        public int getPartyRole() {
            return partyRole;
        }

        public void setPartyRole(int partyRole) {
            this.partyRole = partyRole;
        }

        public String getRefPartyUUID() {
            return refPartyUUID;
        }

        public void setRefPartyUUID(String refPartyUUID) {
            this.refPartyUUID = refPartyUUID;
        }

        public String getRefPartyId() {
            return refPartyId;
        }

        public void setRefPartyId(String refPartyId) {
            this.refPartyId = refPartyId;
        }

        public String getRefPartyName() {
            return refPartyName;
        }

        public void setRefPartyName(String refPartyName) {
            this.refPartyName = refPartyName;
        }
    }

    protected String baseUUID;

    protected String targetUUID;

    protected List<String> uuidList;

    protected List<InvolvePartyGenRequest> involvePartyGenRequestList;

    protected int targetDocType;

    protected int sourceDocType;

    protected int prevProfDocType;

    protected int actionCode;

    protected int secondaryActionCode;

    protected CrossDocConvertRequest.InputOption inputOption;

    public String getBaseUUID() {
        return baseUUID;
    }

    public void setBaseUUID(String baseUUID) {
        this.baseUUID = baseUUID;
    }

    public String getTargetUUID() {
		return targetUUID;
	}

	public void setTargetUUID(String targetUUID) {
		this.targetUUID = targetUUID;
	}

	public List<String> getUuidList() {
        return uuidList;
    }

    public void setUuidList(List<String> uuidList) {
        this.uuidList = uuidList;
    }

    public int getTargetDocType() {
        return targetDocType;
    }

    public void setTargetDocType(int targetDocType) {
        this.targetDocType = targetDocType;
    }

    public int getSourceDocType() {
        return sourceDocType;
    }

    public void setSourceDocType(int sourceDocType) {
        this.sourceDocType = sourceDocType;
    }

    public List<InvolvePartyGenRequest> getInvolvePartyGenRequestList() {
        return involvePartyGenRequestList;
    }

    public void setInvolvePartyGenRequestList(List<InvolvePartyGenRequest> involvePartyGenRequestList) {
        this.involvePartyGenRequestList = involvePartyGenRequestList;
    }

    public int getPrevProfDocType() {
        return prevProfDocType;
    }

    public void setPrevProfDocType(int prevProfDocType) {
        this.prevProfDocType = prevProfDocType;
    }

    public CrossDocConvertRequest.InputOption getInputOption() {
        return inputOption;
    }

    public void setInputOption(CrossDocConvertRequest.InputOption inputOption) {
        this.inputOption = inputOption;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }

    public int getSecondaryActionCode() {
        return secondaryActionCode;
    }

    public void setSecondaryActionCode(int secondaryActionCode) {
        this.secondaryActionCode = secondaryActionCode;
    }

    @Override
    public DocumentMatItemBatchGenRequest clone() {
        try {
            DocumentMatItemBatchGenRequest clone = (DocumentMatItemBatchGenRequest) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
