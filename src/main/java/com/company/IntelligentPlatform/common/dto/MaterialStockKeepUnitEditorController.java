package com.company.IntelligentPlatform.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "materialStockKeepUnitEditorController")
@RequestMapping(value = "/materialStockKeepUnit")
public class MaterialStockKeepUnitEditorController extends SEEditorController {

    public static final String AOID_RESOURCE = MaterialEditorController.AOID_RESOURCE;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected MaterialSKUActionExecutionProxy materialSKUActionExecutionProxy;

    @Autowired
    protected MaterialStockKeepUnitServiceUIModelExtension materialStockKeepUnitServiceUIModelExtension;

    protected Logger logger = LoggerFactory.getLogger(MaterialStockKeepUnitEditorController.class);

    public ServiceBasicUtilityController.DocUIModelWithActionRequest getDocUIModelRequest() {
        return new ServiceBasicUtilityController.DocUIModelWithActionRequest(
                MaterialStockKeepUnitServiceUIModel.class,
                MaterialStockKeepUnitServiceModel.class, AOID_RESOURCE,
                MaterialStockKeepUnit.NODENAME, MaterialStockKeepUnit.SENAME, materialStockKeepUnitServiceUIModelExtension,
                materialStockKeepUnitManager, MaterialSKUActionLog.NODENAME, materialSKUActionExecutionProxy
        );
    }

    @RequestMapping(value = "/getDocActionNodeList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionNodeList(String uuid, String actionCode) {
        return serviceBasicUtilityController.getDocActionNodeList(uuid, actionCode, getDocUIModelRequest());
    }

    @RequestMapping(value = "/getTraceLevel", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getTraceLevel() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> materialStockKeepUnitManager.initTraceLevelMap(lanCode));
    }

    @RequestMapping(value = "/getTraceMode", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getTraceMode() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> materialStockKeepUnitManager.initTraceModeMap(lanCode));
    }

    @RequestMapping(value = "/getTraceStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getTraceStatus() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> materialStockKeepUnitManager.initTraceStatusMap(lanCode));
    }

    @RequestMapping(value = "/getStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getStatus() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> materialStockKeepUnitManager.initStatusMap(lanCode));
    }

    @RequestMapping(value = "/getQualityInspectFlagMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getQualityInspectFlagMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> materialStockKeepUnitManager.initMaterialQualityInspectFlagMap(lanCode));
    }

    @RequestMapping(value = "/saveModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String saveModuleService(@RequestBody String request) {
        return serviceBasicUtilityController.saveModuleService(request, getDocUIModelRequest(), ISystemActionCode.ACID_EDIT);
    }

    protected MaterialRequestModel parseToMaterialRequestModel(@RequestBody String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
        return (MaterialRequestModel) JSONObject.toBean(jsonObject, MaterialRequestModel.class, classMap);
    }

    @RequestMapping(value = "/calculatePriceService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String calculatePriceService(@RequestBody String request) {
        MaterialRequestModel materialRequestModel = parseToMaterialRequestModel(request);
        return serviceBasicUtilityController.getObjectMeta(() -> {
            try {
                MaterialStockKeepUnit materialStockKeepUnit =
                        materialStockKeepUnitManager.getRefTemplateMaterialSKU(materialRequestModel.getRefMaterialSKUUUID(),
                                logonActionController.getClient());
                if (materialStockKeepUnit == null) {
                    // In case material not selected yet
                    materialRequestModel.setItemPrice(0);
                    return ServiceJSONParser.genDefOKJSONObject(materialRequestModel);
                }
                StorageCoreUnit storageCoreUnit =
                        new StorageCoreUnit(materialStockKeepUnit.getUuid(), materialRequestModel.getRefUnitUUID(),
                                materialRequestModel.getAmount());
                double itemPrice =
                        materialStockKeepUnitManager.calculatePrice(storageCoreUnit, materialStockKeepUnit, materialRequestModel.getUnitPrice());
                materialRequestModel.setItemPrice(itemPrice);
                return materialRequestModel;
            } catch (ServiceEntityConfigureException | MaterialException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }


    @RequestMapping(value = "/checkDuplicateID", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkDuplicateID(@RequestBody SimpleSEJSONRequest simpleRequest) {
        LogonUser logonUser = logonActionController.getLogonUser();
        simpleRequest.setClient(logonUser.getClient());
        return super.checkDuplicateIDCore(simpleRequest, materialStockKeepUnitManager);
    }

    @RequestMapping(value = "/newModuleService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String newModuleService(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.newModuleService(getDocUIModelRequest(), () -> {
            try {
                Material material = (Material) materialManager.getEntityNodeByKey(request.getBaseUUID(),
                        IServiceEntityNodeFieldConstant.UUID, Material.NODENAME, null);
                if (material == null) {
                    throw new MaterialException(MaterialException.PARA_NON_FOUND_MATERIAL, request.getBaseUUID());
                }
                List<ServiceEntityNode> rawMaterialUnitReferenceList =
                        materialManager.getEntityNodeListByKey(material.getUuid(),
                                IServiceEntityNodeFieldConstant.PARENTNODEUUID, MaterialUnitReference.NODENAME,
                                material.getClient(), null);
                List<ServiceEntityNode> rawMaterialAttachmentList =
                        materialManager.getEntityNodeListByKey(material.getUuid(),
                                IServiceEntityNodeFieldConstant.PARENTNODEUUID, MaterialAttachment.NODENAME,
                                material.getClient(), null);
                //TODO read configuration
                boolean copyMaterialId = true;
                MaterialStockKeepUnitServiceModel materialStockKeepUnitServiceModel =
                        materialStockKeepUnitManager.createNewSKUOnlineFromMaterial(material, rawMaterialUnitReferenceList,
                                copyMaterialId, rawMaterialAttachmentList);
                return materialStockKeepUnitServiceModel;
            } catch (MaterialException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, ISystemActionCode.ACID_EDIT);
    }


    /**
     * Get Material SKU Unit List by base UUID
     *
     * @param simpleRequest
     * @return
     */
    @RequestMapping(value = "/getMaterialSKUUnitList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getMaterialSKUUnitList(@RequestBody SimpleSEJSONRequest simpleRequest) {
        return serviceBasicUtilityController.getListMeta(() -> materialStockKeepUnitManager.getEntityNodeListByKey(simpleRequest.getBaseUUID(),
                IServiceEntityNodeFieldConstant.UUID,
                MaterialSKUUnitReference.NODENAME, null));
    }

    /**
     * Calculate different amount by compare 2 material amount
     *
     * @param storageUnitCompare
     * @return
     */
    @RequestMapping(value = "/calculateDiffAmount", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String calculateDiffAmount(@RequestBody StorageUnitCompare storageUnitCompare) {
        return serviceBasicUtilityController.getObjectMeta(() -> {
            try {
                return  materialStockKeepUnitManager.mergeStorageUnitCore(storageUnitCompare.getStorageCoreUnit1(),
                        storageUnitCompare.getStorageCoreUnit2(), StorageCoreUnit.OPERATOR_MINUS,
                        logonActionController.getClient());
            } catch (ServiceEntityConfigureException | MaterialException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    /**
     * Get Material SKU Unit List by base UUID
     *
     * @param storageUnitCompare
     * @return
     */
    @RequestMapping(value = "/compareSKURequestsAmount", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String compareSKURequestsAmount(@RequestBody StorageUnitCompare storageUnitCompare) {
        return serviceBasicUtilityController.getObjectMeta(() -> {
            try {
                return materialStockKeepUnitManager.compareSKURequestsAmount(storageUnitCompare.getStorageCoreUnit1(),
                        storageUnitCompare.getStorageCoreUnit2(), logonActionController.getClient());
            } catch (ServiceEntityConfigureException | MaterialException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        });
    }

    @RequestMapping(value = "/getAllMaterialSKUUnitList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getAllMaterialSKUUnitList(@RequestBody SimpleSEJSONRequest simpleRequest) {
        return serviceBasicUtilityController.getMapMeta(lanCode -> {
            MaterialStockKeepUnit materialStockKeepUnit =
                    (MaterialStockKeepUnit) materialStockKeepUnitManager.getEntityNodeByUUID(simpleRequest.getBaseUUID(),
                             MaterialStockKeepUnit.NODENAME,
                            logonActionController.getClient());
            if (materialStockKeepUnit != null) {
                List<ServiceEntityNode> rawUnitList = materialStockKeepUnitManager.getEntityNodeListByKey(
                        MaterialStockKeepUnitManager.getRefTemplateUUID(materialStockKeepUnit),
                        IServiceEntityNodeFieldConstant.ROOTNODEUUID, MaterialSKUUnitReference.NODENAME, null);
                return materialStockKeepUnitManager.getAllUnitMapFromSKU(materialStockKeepUnit, rawUnitList);
            } else {
                // In case the material SKU is not selected yet, return empty map
                return new HashMap<>();
            }
        });
    }

    private DocAttachmentProxy.DocAttachmentProcessPara genDocAttachmentProcessPara() {
        return new DocAttachmentProxy.DocAttachmentProcessPara(materialStockKeepUnitManager,
                MaterialSKUAttachment.NODENAME, MaterialStockKeepUnit.NODENAME, null, null, null);
    }

    /**
     * load the attachment content to consumer.
     */
    @RequestMapping(value = "/loadAttachment")
    public ResponseEntity<byte[]> loadAttachment(String uuid) {
        return serviceBasicUtilityController.loadAttachment(uuid, AOID_RESOURCE, genDocAttachmentProcessPara());
    }

    /**
     * Delete attachment
     */
    @RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteAttachment(@RequestBody SimpleSEJSONRequest request) {
        return serviceBasicUtilityController.deleteAttachment(request, AOID_RESOURCE, genDocAttachmentProcessPara());
    }

    /**
     * Upload the attachment content information.
     */
    @RequestMapping(value = "/uploadAttachment", consumes = "multipart/form-data", method = RequestMethod.POST)
    public @ResponseBody
    String uploadAttachment(HttpServletRequest request) {
        return serviceBasicUtilityController.uploadAttachment(request, AOID_RESOURCE, genDocAttachmentProcessPara());
    }

    /**
     * Upload the attachment text information.
     */
    @RequestMapping(value = "/uploadAttachmentText", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String uploadAttachmentText(@RequestBody FileAttachmentTextRequest request) {
        return serviceBasicUtilityController.uploadAttachmentText(request, AOID_RESOURCE,
                genDocAttachmentProcessPara());
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLockService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLockService(@RequestBody SimpleSEJSONRequest request) {
        return preLock(request.getUuid());
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    @RequestMapping(value = "/preLock", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preLock(String uuid) {
        return serviceBasicUtilityController.preLock(uuid, ISystemActionCode.ACID_EDIT, getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModule", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModule(String uuid) {
        return serviceBasicUtilityController.loadModule(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleViewService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleViewService(String uuid) {
        return serviceBasicUtilityController.loadModuleViewService(uuid, ISystemActionCode.ACID_VIEW,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/loadModuleEditService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleEditService(String uuid) {
        return serviceBasicUtilityController.loadModuleEditService(uuid, ISystemActionCode.ACID_EDIT, true,
                getDocUIModelRequest());
    }

    @RequestMapping(value = "/exitEditor", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String exitEditor(@RequestBody SimpleSEJSONRequest serviceExitLockJSONModule) {
        return exitEditorCore(serviceExitLockJSONModule);
    }

    @RequestMapping(value = "/executeDocAction", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String executeDocAction(@RequestBody String request) {
        return serviceBasicUtilityController.executeDocActionFramework(request,
                (DocActionNodeProxy.IActionExecutor<MaterialStockKeepUnitServiceModel>) (materialStockKeepUnitServiceModel, actionCode, logonInfo) -> {
                    try {
                        materialSKUActionExecutionProxy.executeActionCore(materialStockKeepUnitServiceModel,
                                actionCode, logonActionController.getSerialLogonInfo());
                    } catch (DocActionException e) {
                        throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                    }
                }, getDocUIModelRequest());
    }

    @RequestMapping(value = "/getActionCodeMap", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getActionCodeMap() {
        return serviceBasicUtilityController.getMapMeta(
                lanCode -> materialSKUActionExecutionProxy.getActionCodeMap(lanCode));
    }

    @RequestMapping(value = "/getDocActionConfigureList", produces = "text/html;charset=UTF-8")
    public @ResponseBody String getDocActionConfigureList() {
        return serviceBasicUtilityController.getDocActionConfigureListCore(this.materialSKUActionExecutionProxy);
    }

}
