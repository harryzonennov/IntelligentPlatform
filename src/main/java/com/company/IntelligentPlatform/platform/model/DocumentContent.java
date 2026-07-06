package com.company.IntelligentPlatform.platform.model;

import jakarta.persistence.*;

/**
 * Intermediate superclass for all document root node entities.
 * Migrated from: ThorsteinPlatform - DocumentContent.hbm.xml
 * Table: DocumentContent (schema: common) — only if used as a standalone entity.
 *
 * Hierarchy:
 *   ServiceEntityNode
 *       └── DocumentContent          (adds document-specific fields)
 *               ├── SalesContract    (schema: sales)
 *               ├── PurchaseContract (schema: logistics)
 *               └── ProductionOrder  (schema: production)
 *
 * DocumentContent adds the common document root node fields:
 *   status, priorityCode, documentCategoryType,
 *   and the document chain links (prev/next doc UUID references).
 */
@MappedSuperclass
public abstract class DocumentContent extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.DocumentContent;

	// Document status constants
	//
	// NOTE: Numeric values must match the legacy ThorsteinPlatform DocumentContent
	// constants exactly. The framework stores `actionCode` and `targetStatus` in the
	// same integer namespace inside DocActionConfigure, and several DOC_ACTION_*
	// constants in SystemDefDocActionCodeProxy are *defined as* these STATUS_*
	// values (e.g. DOC_ACTION_SUBMIT = STATUS_SUBMITTED). Any redefinition that
	// puts a STATUS_* in the same numeric range as the hard-coded action codes
	// (DOC_ACTION_APPROVE=2, COUNTAPPROVE=3, DELIVERY_DONE=5, PROCESS_DONE=6)
	// causes getDocActionConfigureByCode() to return the wrong configure entry —
	// observed as documents and items being silently reset to STATUS_INITIAL after
	// cross-document creation calls. Do NOT renumber to small contiguous ints.
	public static final int STATUS_INITIAL = 1;

	public static final int STATUS_APPROVED = 2;

	public static final int STATUS_DELIVERYDONE = 4;

	public static final int STATUS_PROCESSDONE = 5;

	public static final int STATUS_SUBMITTED = 299;

	public static final int STATUS_INPROCESS = 310;

	public static final int STATUS_ACTIVE = 305;

	public static final int STATUS_REVOKE_SUBMIT = 690;

	public static final int STATUS_REJECT_APPROVAL = 790;

	public static final int STATUS_BLOCKED = 910;

	public static final int STATUS_ARCHIVED = 980;

	public static final int STATUS_CANCELED = 990;

	public static final int STATUS_DELETED = 991;

	// Priority constants (legacy values)
	public static final int PRIORITY_LOW = 1;

	public static final int PRIORITY_MIDDLE = 2;

	public static final int PRIORITY_HIGH = 3;

	public static final int PRIORITY_VERYHIGH = 4;

	// Document category type constants (legacy values)
	public static final int DOCUMENTCATTYPE_ORDER = 1;

	public static final int DOCUMENTCATTYPE_REQUEST = 2;

	public static final double DEF_TAXRATE = 17;

	@Column(name = "status")
	protected int status;

	@Column(name = "priorityCode")
	protected int priorityCode;

	@Column(name = "documentCategoryType")
	protected int documentCategoryType;

	// Document chain — previous document links
	@Column(name = "prevProfDocType")
	protected int prevProfDocType;

	@Column(name = "prevProfDocUUID")
	protected String prevProfDocUUID;

	@Column(name = "prevDocType")
	protected int prevDocType;

	@Column(name = "prevDocUUID")
	protected String prevDocUUID;

	// Document chain — next document links
	@Column(name = "nextProfDocType")
	protected int nextProfDocType;

	@Column(name = "nextProfDocUUID")
	protected String nextProfDocUUID;

	@Column(name = "nextDocType")
	protected int nextDocType;

	@Column(name = "nextDocUUID")
	protected String nextDocUUID;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(int priorityCode) {
		this.priorityCode = priorityCode;
	}

	public int getDocumentCategoryType() {
		return documentCategoryType;
	}

	public void setDocumentCategoryType(int documentCategoryType) {
		this.documentCategoryType = documentCategoryType;
	}

	public int getPrevProfDocType() {
		return prevProfDocType;
	}

	public void setPrevProfDocType(int prevProfDocType) {
		this.prevProfDocType = prevProfDocType;
	}

	public String getPrevProfDocUUID() {
		return prevProfDocUUID;
	}

	public void setPrevProfDocUUID(String prevProfDocUUID) {
		this.prevProfDocUUID = prevProfDocUUID;
	}

	public int getPrevDocType() {
		return prevDocType;
	}

	public void setPrevDocType(int prevDocType) {
		this.prevDocType = prevDocType;
	}

	public String getPrevDocUUID() {
		return prevDocUUID;
	}

	public void setPrevDocUUID(String prevDocUUID) {
		this.prevDocUUID = prevDocUUID;
	}

	public int getNextProfDocType() {
		return nextProfDocType;
	}

	public void setNextProfDocType(int nextProfDocType) {
		this.nextProfDocType = nextProfDocType;
	}

	public String getNextProfDocUUID() {
		return nextProfDocUUID;
	}

	public void setNextProfDocUUID(String nextProfDocUUID) {
		this.nextProfDocUUID = nextProfDocUUID;
	}

	public int getNextDocType() {
		return nextDocType;
	}

	public void setNextDocType(int nextDocType) {
		this.nextDocType = nextDocType;
	}

	public String getNextDocUUID() {
		return nextDocUUID;
	}

	public void setNextDocUUID(String nextDocUUID) {
		this.nextDocUUID = nextDocUUID;
	}

}
