package com.company.IntelligentPlatform.common.service;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IDocItemNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocFlowContextProxy {

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    public static List<DocFlowContextUnit> parseStrToContextUnitCore(String rawValue) {
        if (ServiceEntityStringHelper.checkNullString(rawValue)) {
            return new ArrayList<>();
        }
        JSONArray jsonArray = JSONArray.fromObject(rawValue);
        return JSONArray.toList(jsonArray, new DocFlowContextUnit(), new JsonConfig());
    }

    public static String parseContextUnitToStrCore(List<DocFlowContextUnit> docFlowContextUnitList) {
        if (ServiceCollectionsHelper.checkNullList(docFlowContextUnitList)) {
            return null;
        }
        JSONArray jsonArray = JSONArray.fromObject(docFlowContextUnitList);
        return jsonArray.toString();
    }

    public static void updateDocContextUUID(DocMatItemNode docMatItemNode, String uuid, int documentType,
                                            int docFlowDirection)
            throws DocActionException {
        DocFlowContextUnit docFlowContextUnit = new DocFlowContextUnit(uuid, documentType);
        FieldNameUnit fieldNameUnit = getFieldNameUnitByFlowDirection(docFlowDirection);
        updateDocContextUUIDCore(docMatItemNode, fieldNameUnit, docFlowContextUnit);
    }

    public static void clearDocContextUUID(DocMatItemNode docMatItemNode, String refUUID,
                                            int docFlowDirection)
            throws DocActionException {
        FieldNameUnit fieldNameUnit = getFieldNameUnitByFlowDirection(docFlowDirection);
        clearDocContextUUIDCore(docMatItemNode, fieldNameUnit, refUUID);
    }

    /**
     * Clean/Remove the relative document information from current home document item instance.
     *
     * @param homeUUID: Current home document item UUID.
     * @param relativeUUID: The relative UUID value which need to be removed
     * @param homeDocType: Current home document type
     * @param docFlowDirection: Relative document direction or the relationship to current home document
     * @param serialLogonInfo: logon instance
     * @return
     * @throws DocActionException
     */
    public boolean clearDocItemNodeUnit(String homeUUID, String relativeUUID, int homeDocType, int docFlowDirection,
                                        SerialLogonInfo serialLogonInfo) throws DocActionException {
        if (ServiceEntityStringHelper.checkNullString(homeUUID) || homeDocType == 0) {
            return false;
        }
        try {
            DocumentContentSpecifier homeContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(homeDocType);
            DocMatItemNode homeMatItemNode =
                    (DocMatItemNode) homeContentSpecifier.getDocMatItem(homeUUID, serialLogonInfo.getClient());
            clearDocItemNodeUnit(homeMatItemNode, relativeUUID, docFlowDirection, serialLogonInfo);
            return true;
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * Clear all the relationship for current doc mat item instance, it is preparation before delete this instance
     * @param docMatItemNode: the doc mat item instance, which need to be clean relationship
     * @param serialLogonInfo: logon instance
     * @param docFlowDirection: doc flow direction
     * @throws DocActionException
     */
    public void clearDocMatItemRelationship(DocMatItemNode docMatItemNode,
                                            SerialLogonInfo serialLogonInfo, int docFlowDirection) throws DocActionException {
        // Delete previous relationship from main prev mat item uuid
        clearDocItemNodeUnit(docMatItemNode.getPrevDocMatItemUUID(), docMatItemNode.getUuid(), docMatItemNode.getPrevDocType(),
                docFlowDirection, serialLogonInfo);
        // Delete previous relationship from prev mat item uuid array
        // TODO: check the risk of dead-lock when this batch deletion
        List<DocFlowContextUnit> prevDocContextList = DocFlowContextProxy.parseStrToContextUnitCore(docMatItemNode.getPrevDocMatItemArrayUUID());
        if (!ServiceCollectionsHelper.checkNullList(prevDocContextList)) {
            ServiceCollectionsHelper.traverseListInterrupt(prevDocContextList, item -> {
                clearDocItemNodeUnit(item.getRefUUID(), docMatItemNode.getUuid(), item.getDocumentType(),
                        docFlowDirection, serialLogonInfo);
                return true;
            });
        }
    }



    /**
     * Clean/Remove the relative document information from current home document item instance.
     *
     * @param homeMatItemNode: Current home document item instance.
     * @param relativeUUID: The relative UUID value which need to be removed
     * @param docFlowDirection: Relative document direction or the relationship to current home document
     * @param serialLogonInfo: logon instance
     * @return
     * @throws DocActionException
     */
    public boolean clearDocItemNodeUnit(DocMatItemNode homeMatItemNode, String relativeUUID, int docFlowDirection,
                                        SerialLogonInfo serialLogonInfo) throws DocActionException {
        String homeUUID = homeMatItemNode.getUuid();
        int homeDocType = homeMatItemNode.getHomeDocumentType();
        try {
            ServiceConcurrentProxy.writeLock(homeUUID, serialLogonInfo.getRefUserUUID());
            DocumentContentSpecifier homeContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(homeDocType);
            DocFlowContextProxy.clearDocContextUUID(homeMatItemNode, relativeUUID, docFlowDirection);
            homeContentSpecifier.getDocumentManager()
                    .updateSENode(homeMatItemNode, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        } catch (DocActionException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } finally {
            ServiceConcurrentProxy.unLockWrite(homeUUID);
        }
        return true;
    }

    public boolean updateDocItemNodeUnit(String homeUUID, String relativeUUID, int homeDocType, int relativeDocType,
                                         int docFlowDirection, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        if (ServiceEntityStringHelper.checkNullString(homeUUID) || homeDocType == 0) {
            return false;
        }
        try {
            DocumentContentSpecifier documentContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(homeDocType);
            DocMatItemNode docMatItemNode =
                    (DocMatItemNode) documentContentSpecifier.getDocMatItem(homeUUID, serialLogonInfo.getClient());
            updateDocItemNodeUnit(docMatItemNode, relativeUUID, relativeDocType, docFlowDirection, serialLogonInfo);
            return true;
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    public boolean updateDocItemNodeUnit(DocMatItemNode homeMatItemNode, String relativeUUID, int relativeDocType,
                                         int docFlowDirection, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        String homeUUID = homeMatItemNode.getUuid();
        int homeDocType = homeMatItemNode.getHomeDocumentType();
        try {
            ServiceConcurrentProxy.writeLock(homeUUID, serialLogonInfo.getRefUserUUID());
            DocumentContentSpecifier documentContentSpecifier =
                    docActionExecutionProxyFactory.getSpecifierByDocType(homeDocType);
            DocFlowContextProxy.clearDocContextUUID(homeMatItemNode, relativeUUID, docFlowDirection);
            DocFlowContextProxy.updateDocContextUUID(homeMatItemNode, relativeUUID, relativeDocType, docFlowDirection);
            documentContentSpecifier.getDocumentManager()
                    .updateSENode(homeMatItemNode, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
        } catch (DocActionException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } finally {
            ServiceConcurrentProxy.unLockWrite(homeUUID);
        }
        return true;
    }

    public static List<DocFlowContextUnit> getDocContextUUIDValue(DocMatItemNode docMatItemNode,int docFlowDirection)
            throws DocActionException {
        try{
            FieldNameUnit fieldNameUnit = getFieldNameUnitByFlowDirection(docFlowDirection);
            if (fieldNameUnit == null) {
                throw new DocActionException(DocActionException.PARA_MISS_CONFIG, docFlowDirection);
            }
            String rawStrValue = parseToStr(docMatItemNode, fieldNameUnit.getDocMatItemUUIDArrayFieldName());
            return parseStrToContextUnitCore(rawStrValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    public static String getDocContextUUIDCoreValue(DocMatItemNode docMatItemNode,int docFlowDirection)
            throws DocActionException {
        try{
            FieldNameUnit fieldNameUnit = getFieldNameUnitByFlowDirection(docFlowDirection);
            if (fieldNameUnit == null) {
                throw new DocActionException(DocActionException.PARA_MISS_CONFIG, docFlowDirection);
            }
            return parseToStr(docMatItemNode, fieldNameUnit.getDocMatItemUUIDFieldName());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    public static int getDocContextDocType(DocMatItemNode docMatItemNode,int docFlowDirection)
            throws DocActionException {
        try{
            FieldNameUnit fieldNameUnit = getFieldNameUnitByFlowDirection(docFlowDirection);
            if (fieldNameUnit == null) {
                throw new DocActionException(DocActionException.PARA_MISS_CONFIG, docFlowDirection);
            }
            String fieldValueObj = parseToStr(docMatItemNode, fieldNameUnit.getDocumentTypeFieldName());
            if (fieldValueObj == null) {
                return 0;
            }
            return Integer.parseInt(fieldValueObj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    private static void updateDocContextUUIDCore(DocMatItemNode docMatItemNode,
                                                 FieldNameUnit fieldNameUnit,
                                                 DocFlowContextUnit docFlowContextUnit)
            throws DocActionException {
        if (fieldNameUnit == null) {
            // should raise configure exception
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG);
        }
        try {
            Field docMatItemUUIDField =
                    ServiceEntityFieldsHelper.getServiceField(docMatItemNode, fieldNameUnit.getDocMatItemUUIDFieldName());
            docMatItemUUIDField.setAccessible(true);
            Field documentTypeField = ServiceEntityFieldsHelper.getServiceField(docMatItemNode, fieldNameUnit.getDocumentTypeFieldName());
            documentTypeField.setAccessible(true);
            /*
             * Update main item UUID field
             */
            String docMatItemUUID = parseToStr(docMatItemNode, fieldNameUnit.getDocMatItemUUIDFieldName());
            if (ServiceEntityStringHelper.checkNullString(docMatItemUUID)) {
                docMatItemUUIDField.set(docMatItemNode, docFlowContextUnit.getRefUUID());
                documentTypeField.set(docMatItemNode, docFlowContextUnit.getDocumentType());
            }
            /*
             * Update item UUID Array field
             */
            Field docMatItemUUIDArrayField = ServiceEntityFieldsHelper.getServiceField(docMatItemNode,
                    fieldNameUnit.getDocMatItemUUIDArrayFieldName());
            docMatItemUUIDArrayField.setAccessible(true);
            String targetUUIDArray = parseToStr(docMatItemNode, fieldNameUnit.getDocMatItemUUIDArrayFieldName());
            List<DocFlowContextUnit> docFlowContextUnitList = parseStrToContextUnitCore(targetUUIDArray);
            DocFlowContextUnit existedDocContextUnit = ServiceCollectionsHelper.filterOnline(docFlowContextUnitList,
                    tmpFlowContextUnit -> docFlowContextUnit.getRefUUID().equals(tmpFlowContextUnit.getRefUUID()));
            if (existedDocContextUnit == null) {
                docFlowContextUnitList.add(docFlowContextUnit);
            }
            targetUUIDArray = parseContextUnitToStrCore(docFlowContextUnitList);
            docMatItemUUIDArrayField.set(docMatItemNode, targetUUIDArray);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    public static String parseToStr(DocMatItemNode docMatItemNode, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Object fieldValueObj = getFieldValue(docMatItemNode, fieldName);
        if (fieldValueObj == null) {
            return null;
        }
        return fieldValueObj.toString();
    }

    public static Object getFieldValue(DocMatItemNode docMatItemNode, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field docItemField =
                ServiceEntityFieldsHelper.getServiceField(docMatItemNode, fieldName);
        docItemField.setAccessible(true);
        return docItemField.get(docMatItemNode);
    }

    private static void clearDocContextUUIDCore(DocMatItemNode docMatItemNode,
                                                 FieldNameUnit fieldNameUnit,
                                                 String refUUID)
            throws DocActionException {
        if (fieldNameUnit == null) {
            // should raise configure exception
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG);
        }
        try {
            Field docMatItemUUIDField =
                    ServiceEntityFieldsHelper.getServiceField(docMatItemNode, fieldNameUnit.getDocMatItemUUIDFieldName());
            docMatItemUUIDField.setAccessible(true);
            Field documentTypeField = ServiceEntityFieldsHelper.getServiceField(docMatItemNode, fieldNameUnit.getDocumentTypeFieldName());
            documentTypeField.setAccessible(true);
            /*
             * Clean main item UUID field to Empty and document type field to 0 if condition matches
             */
            String docMatItemUUID = parseToStr(docMatItemNode, fieldNameUnit.getDocMatItemUUIDFieldName());
            if (!ServiceEntityStringHelper.checkNullString(docMatItemUUID) && docMatItemUUID.equals(refUUID) ) {
                docMatItemUUIDField.set(docMatItemNode, ServiceEntityStringHelper.EMPTYSTRING);
                documentTypeField.set(docMatItemNode, 0);
            }
            /*
             * Update item UUID Array field: remove the refUUID from item UUID Array field
             */
            Field docMatItemUUIDArrayField = ServiceEntityFieldsHelper.getServiceField(docMatItemNode,
                    fieldNameUnit.getDocMatItemUUIDArrayFieldName());
            docMatItemUUIDArrayField.setAccessible(true);
            String targetUUIDArray = parseToStr(docMatItemNode, fieldNameUnit.getDocMatItemUUIDArrayFieldName());
            List<DocFlowContextUnit> docFlowContextUnitList = parseStrToContextUnitCore(targetUUIDArray);
            DocFlowContextUnit existedDocContextUnit = ServiceCollectionsHelper.filterOnline(docFlowContextUnitList,
                    docFlowContextUnit -> refUUID.equals(docFlowContextUnit.getRefUUID()));
            if (existedDocContextUnit != null) {
                docFlowContextUnitList.remove(existedDocContextUnit);
            }
            targetUUIDArray = parseContextUnitToStrCore(docFlowContextUnitList);
            docMatItemUUIDArrayField.set(docMatItemNode, targetUUIDArray);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Get && Filter ContextUnit from Multiple UUID field, by checking matching the target uuid
     * @param docMatItemNode
     * @param uuid
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static DocFlowContextUnit getContextUnit(DocMatItemNode docMatItemNode,
                                                    int docFlowDirection, String uuid)
            throws DocActionException {
        FieldNameUnit fieldNameUnit = getFieldNameUnitByFlowDirection(docFlowDirection);
        return getContextUnit(docMatItemNode, fieldNameUnit.getDocMatItemUUIDArrayFieldName(), uuid);
    }

    /**
     * Get && Filter ContextUnit from Multiple UUID field, by checking matching the target uuid
     * @param docMatItemNode
     * @param docMatItemUUIDArrayFieldName
     * @param uuid
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static DocFlowContextUnit getContextUnit(DocMatItemNode docMatItemNode,
                                                     String docMatItemUUIDArrayFieldName, String uuid)
            throws DocActionException {
        try {
            Field docMatItemUUIDArrayField =
                    ServiceEntityFieldsHelper.getServiceField(docMatItemNode, docMatItemUUIDArrayFieldName);
            docMatItemUUIDArrayField.setAccessible(true);
            String targetUUIDArray = parseToStr(docMatItemNode, docMatItemUUIDArrayFieldName);
            List<DocFlowContextUnit> docFlowContextUnitList = parseStrToContextUnitCore(targetUUIDArray);
            return ServiceCollectionsHelper.filterOnline(docFlowContextUnitList,
                    tempFlowContextUnit -> tempFlowContextUnit.getRefUUID().equals(uuid));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Constant method to get Field Name unit by different flow category
     * @param docFlowDirection
     * @return
     */
    public static FieldNameUnit getFieldNameUnitByFlowDirection(int docFlowDirection) {
        if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV) {
            return new FieldNameUnit(IDocItemNodeFieldConstant.prevDocMatItemUUID,
                    IDocItemNodeFieldConstant.prevDocType, IDocItemNodeFieldConstant.prevDocMatItemArrayUUID);
        }
        if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT) {
            return new FieldNameUnit(IDocItemNodeFieldConstant.nextDocMatItemUUID,
                    IDocItemNodeFieldConstant.nextDocType, IDocItemNodeFieldConstant.nextDocMatItemArrayUUID);
        }
        if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF) {
            return new FieldNameUnit(IDocItemNodeFieldConstant.prevProfDocMatItemUUID,
                    IDocItemNodeFieldConstant.prevProfDocType, IDocItemNodeFieldConstant.prevProfDocMatItemArrayUUID);
        }
        if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF) {
            return new FieldNameUnit(IDocItemNodeFieldConstant.nextProfDocMatItemUUID,
                    IDocItemNodeFieldConstant.nextProfDocType, IDocItemNodeFieldConstant.nextDocMatItemArrayUUID);
        }
        if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY) {
            return new FieldNameUnit(IDocItemNodeFieldConstant.reservedMatItemUUID,
                    IDocItemNodeFieldConstant.reservedDocType, IDocItemNodeFieldConstant.reservedDocMatItemArrayUUID);
        }
        if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET) {
            return new FieldNameUnit(IDocItemNodeFieldConstant.reserveTargetMatItemUUID,
                    IDocItemNodeFieldConstant.reserveTargetDocType, IDocItemNodeFieldConstant.reserveTargetDocMatItemArrayUUID);
        }
        return null;
    }

    public static class FieldNameUnit{

        private String docMatItemUUIDFieldName;

        private String documentTypeFieldName;

        private String docMatItemUUIDArrayFieldName;

        public FieldNameUnit() {
        }

        public FieldNameUnit(String docMatItemUUIDFieldName, String documentTypeFieldName,
                         String docMatItemUUIDArrayFieldName) {
            this.docMatItemUUIDFieldName = docMatItemUUIDFieldName;
            this.documentTypeFieldName = documentTypeFieldName;
            this.docMatItemUUIDArrayFieldName = docMatItemUUIDArrayFieldName;
        }

        public String getDocMatItemUUIDFieldName() {
            return docMatItemUUIDFieldName;
        }

        public void setDocMatItemUUIDFieldName(String docMatItemUUIDFieldName) {
            this.docMatItemUUIDFieldName = docMatItemUUIDFieldName;
        }

        public String getDocumentTypeFieldName() {
            return documentTypeFieldName;
        }

        public void setDocumentTypeFieldName(String documentTypeFieldName) {
            this.documentTypeFieldName = documentTypeFieldName;
        }

        public String getDocMatItemUUIDArrayFieldName() {
            return docMatItemUUIDArrayFieldName;
        }

        public void setDocMatItemUUIDArrayFieldName(String docMatItemUUIDArrayFieldName) {
            this.docMatItemUUIDArrayFieldName = docMatItemUUIDArrayFieldName;
        }
    }

}
