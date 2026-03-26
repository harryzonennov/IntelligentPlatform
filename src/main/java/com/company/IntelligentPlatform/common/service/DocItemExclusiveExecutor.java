package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionExecutionProxy;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Execute the action for one selected doc item, while triggering other action for the rest of other items for
 * keeping only one doc item with one specific status
 */
public class DocItemExclusiveExecutor<R extends ServiceModule, T extends ServiceEntityNode, Item extends ServiceEntityNode> {

	public void batchExecItemInList(DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
									DocumentContentSpecifier<R, T, Item> documentContentSpecifier,
									DocActionExecutorCase.DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
									SerialLogonInfo serialLogonInfo) throws DocActionException {
		// Step 1 execute the actionCode for one selected item
		List<ServiceEntityNode> allDocMatItemList =
				documentContentSpecifier.getDocMatItemList(docItemBatchExecutionRequest.getServiceModule());
		List<ServiceEntityNode> selectedItemList = docItemBatchExecutionRequest.getSelectedDocMatItemList();
		int docActionCode = docItemBatchExecutionRequest.getDocActionCode();
		// Execute the selected Item list
		ServiceCollectionsHelper.traverseListInterrupt(selectedItemList, item -> {
			// In case need to update item status
			Item docMatItem = (Item) item;
			docActionExecutionProxy.checkUpdateItemStatus(docMatItem, docActionCode, serialLogonInfo,
					false, null);
			return true;
		});
		// Step 2 TODO Get relative actionCode for other items
		int nextDocActionCode = 0;
		ServiceCollectionsHelper.traverseListInterrupt(allDocMatItemList, otherItem -> {
			// In case need to update item status
			ServiceEntityNode existedItem =
					ServiceCollectionsHelper.filterSENodeOnline(otherItem.getUuid(), selectedItemList);
			if (existedItem != null) {
				return true;
			}
			Item otherDocMatItem = (Item) otherItem;
			docActionExecutionProxy.checkUpdateItemStatus(otherDocMatItem, nextDocActionCode,
					serialLogonInfo, false, null);
			return true;
		});

	}

	//TODO

	/**
	 * How to design the duplicate check backwards.
	 * 1.Design for the sub node (section) flag: exclusiveByKeyField: 'refUUID' or 'refMaterialSKUUUID'.
	 * 2.When to trigger check backend: in the save method by editor ?
	 * 3. How to pass the request from UI Client to backend ?
	 * 4. Implementation logic a: Get all the existed item list from the current node.
	 * 5. Implementation logic a: Check uuid if found duplicate, then return before save.
	 *
	 */

	/**
	 * 1. Find Better Item Callback interface.
	 * 2. Finish all the logic.
	 * 3. Should be placed a condition check
	 * 3. implement this API for Employee Logon User case lean Execution .
	 * 4. implement this API for Customer Page case: maybe .
	 * 5. implement this API for BOM case ?
	 *
	 */

	//TODO find proper callback method
	public void leanExecuteItemList(List<ServiceEntityNode> selectedItemList, List<ServiceEntityNode> allDocMatItemList,
								   DocActionExecutionProxy.DocItemActionExecution<Item> selectedItemActionCallback,
								   DocActionExecutionProxy.DocItemActionExecution<Item> otherItemActionCallback) {
		// Get the others items by filter from seleted item

	}

	public List<ServiceEntityNode> getSelectedListWrapper(ServiceEntityManager serviceEntityManager, String itemNodeName, List<String> uuidList, String client)
			throws DocActionException {
		try {
			return serviceEntityManager
					.getEntityNodeListByMultipleKey(uuidList, IServiceEntityNodeFieldConstant.UUID, itemNodeName,
							client, null);
		} catch (ServiceEntityConfigureException e) {
			throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
		}
	}

}
