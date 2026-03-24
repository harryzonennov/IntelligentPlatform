package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocActionExecutionProxy;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocItemProxy {

    /**
     * Checks for duplicates of the given reference item among existing items that might point to the same target reference.
     * If duplicates are found, this method returns the list; otherwise it returns null.
     *
     * @param currentEntityNode   the current reference item that needs to be checked
     * @param keyStructureList    additional search constraints to narrow the scope of existing items; may be null or empty
     * @param referKeyFieldName   reference key field name, holds the reference key value used for comparison
     * @param serviceEntityManager service entity manager instance
     * @return a list of duplicate items, or null if no duplicates are found
     */
    public List<ServiceEntityNode> getDuplicateItemList(ServiceEntityNode currentEntityNode, List<ServiceBasicKeyStructure> keyStructureList,
                                           String referKeyFieldName, ServiceEntityManager serviceEntityManager) throws DocActionException {
        try {
            Object currentReferKeyValue = ServiceEntityFieldsHelper.getServiceFieldValue(currentEntityNode, referKeyFieldName);
            List<ServiceBasicKeyStructure> keyStructureListWithReferKey = ServiceCollectionsHelper.asList(new ServiceBasicKeyStructure(currentReferKeyValue, referKeyFieldName));
            if (!ServiceCollectionsHelper.checkNullList(keyStructureList)){
                keyStructureListWithReferKey.addAll(keyStructureList);
            }
            List<ServiceEntityNode> existedReferItemList = serviceEntityManager.getEntityNodeListByKeyList(keyStructureListWithReferKey, currentEntityNode.getNodeName(), currentEntityNode.getClient(), null);
            if (ServiceCollectionsHelper.checkNullList(existedReferItemList)){
                return null;
            }
            List<ServiceEntityNode> duplicateItemList = new ArrayList<>();
            ServiceCollectionsHelper.traverseListInterrupt(existedReferItemList, tmpServiceEntityNode -> {
                if (!tmpServiceEntityNode.getUuid().equals(currentEntityNode.getUuid())) {
                    duplicateItemList.add(tmpServiceEntityNode);
                }
                return true;
            });
            return duplicateItemList;
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    public static String pluckDuplicateIdNames(List<ServiceEntityNode> duplicateItemList, String idFieldName) throws NoSuchFieldException, IllegalAccessException, ServiceEntityConfigureException {
        List<String> errorIdList = ServiceCollectionsHelper.pluckList(duplicateItemList, idFieldName);
        return String.join(", ", errorIdList);
    }

    /**
     * Executes one exclusive action for the selected item list, while executing another action for the rest of non-selected items.
     * A typical use case is to set the selected item status to {@code active}, while setting all non-selected items to {@code archived}.
     *
     * @param selectedItemList     items that have been selected by the user
     * @param allItemList          all available items from which the "other" items will be computed
     * @param selectedItemActionCallback action to execute for each item in selectedItemList
     * @param otherItemActionCallback    action to execute for each item not in selectedItemList (currently not used in this implementation)
     * @param serialLogonInfo      login/session information used by the action callbacks
     * @throws DocActionException   if an error occurs during processing
     */
    public void exclusiveExeSelectItemList(List<ServiceEntityNode> selectedItemList, List<ServiceEntityNode> allItemList,
                                      DocActionExecutionProxy.DocActionExecution<ServiceEntityNode> selectedItemActionCallback,
                                      DocActionExecutionProxy.DocActionExecution<ServiceEntityNode> otherItemActionCallback, SerialLogonInfo serialLogonInfo) throws DocActionException {
        // Step 1: Execute one action for selected item list.
        if (selectedItemActionCallback != null) {
            ServiceCollectionsHelper.traverseListInterrupt(selectedItemList, item -> {
                // In case need to update item status
                selectedItemActionCallback.execute(item, serialLogonInfo);
                return true;
            });
        }
        // Step 2: Execute other action for the rest item list from all the provided item list
        if (otherItemActionCallback != null) {
            List<ServiceEntityNode> restItemList = ServiceCollectionsHelper.getDiffItemList(selectedItemList, allItemList);
            ServiceCollectionsHelper.traverseListInterrupt(restItemList, otherItem -> {
                // In case need to update item status
                otherItemActionCallback.execute(otherItem, serialLogonInfo);
                return true;
            });
        }
    }
}
