package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.DataTableRequestData;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceDocDeletionSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceDocInitConfigureManager;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceDocDeletionSetting;

import jakarta.servlet.http.HttpServletRequest;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Scope("session")
@Controller(value = "serviceBasicUtilityController")
@RequestMapping(value = "/serviceBasicUtility")
public class ServiceBasicUtilityController {

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected AuthorizationManager authorizationManager;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected ServiceBasicPerformHelper serviceBasicPerformHelper;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected LockObjectManager lockObjectManager;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected ServiceDocInitConfigureManager serviceDocInitConfigureManager;

    @Autowired
    protected ServiceDocDeletionSettingManager serviceDocDeletionSettingManager;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected SplitMatItemProxy splitMatItemProxy;

    @Autowired
    protected SerialIdDocumentProxy serialIdDocumentProxy;

    protected List<ServiceBasicPerformHelper.RecordPointTime> recordPointTimeList;

    protected Logger logger = LoggerFactory.getLogger(ServiceBasicUtilityController.class);

    @RequestMapping(value = "/preCheckResourceAccess", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String preCheckResourceAccess(String resourceId, String acId) {
        try {
            preCheckResourceAccessCore(resourceId, acId);
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public void preCheckResourceAccessCore(String resourceId, String acId)
            throws LogonInfoException, ServiceEntityConfigureException,
            AuthorizationException {
        LogonUser logonUser = logonActionController.getLogonUser();
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        Organization homeOrganization = logonActionController
                .getOrganizationByUser(logonUser.getUuid());
        List<ServiceEntityNode> organizationList = logonActionController
                .getOrganizationListByUser(logonUser);
        boolean accessFlag = authorizationManager.checkAuthorization(logonUser,
                AuthorizationObject.AUTH_TYPE_RESOURCE, resourceId, acId, null,
                homeOrganization, organizationList);
        if (!accessFlag) {
            // Raise Authorization exception
            throw new AuthorizationException(
                    AuthorizationException.TYPE_NO_AUTHORIZATION);
        }
    }

    public SearchContext generateSearchContext(String aoId, String acId, String client, DataTableRequestData dataTableRequestData) throws LogonInfoException,
            ServiceEntityConfigureException, SearchConfigureException {
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(this.logonActionController.getLogonInfo());
        if (dataTableRequestData != null) {
            searchContextBuilder.start(dataTableRequestData.getStart());
            searchContextBuilder.length(dataTableRequestData.getLength());
            searchContextBuilder.acId(acId);
            searchContextBuilder.aoId(aoId);
            searchContextBuilder.client(client);
            searchContextBuilder.searchModel(dataTableRequestData.getSearchModel());
        }
        return searchContextBuilder.build();
    }

    public SearchContext generateSearchContext(String aoId, String acId, String client, SEUIComModel searchModel) throws LogonInfoException,
            SearchConfigureException {
        SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(this.logonActionController.getLogonInfo());
        searchContextBuilder.acId(acId);
        searchContextBuilder.aoId(aoId);
        searchContextBuilder.client(client);
        searchContextBuilder.searchModel(searchModel);
        return searchContextBuilder.build();
    }

    /**
     * Filter Raw list as well as default filter data with "LIST" access
     *
     * @param rawList
     * @return
     */
    public List<ServiceEntityNode> sortSEListFilterAccess(List<ServiceEntityNode> rawList, String aoId) throws ServiceEntityConfigureException, LogonInfoException {
        return sortSEListFilterAccess(rawList, aoId, null);
    }

    /**
     * Filter Raw list as well as default filter data with "LIST" access
     *
     * @param rawList
     * @return
     */
    public List<ServiceEntityNode> sortSEListFilterAccess(List<ServiceEntityNode> rawList, String aoId, Comparator comparator) throws ServiceEntityConfigureException, LogonInfoException {
        rawList = filterDataAccessByAuthorization(rawList, aoId,
                ISystemActionCode.ACID_LIST);
        return sortServiceEntityList(rawList, comparator);
    }

    public List<ServiceEntityNode> sortServiceEntityList(List<ServiceEntityNode> rawList) {
        return sortServiceEntityList(rawList, null);
    }

    public List<ServiceEntityNode> sortServiceEntityList(List<ServiceEntityNode> rawList, Comparator comparator) {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        rawList.sort(Objects.requireNonNullElseGet(comparator, ServiceEntityNodeLastUpdateTimeCompare::new));
        return rawList;
    }

    /**
     * Provide default convertion to UI Model list for List view
     *
     * @param <T>
     * @return
     */
    public <T extends SEUIComModel> List<T> convUIModuleList(Class<T> uiModelClass, List<ServiceEntityNode> rawList,
                                                             ServiceEntityManager serviceEntityManager,
                                                             ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        List<T> resultUIModelList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        for (ServiceEntityNode rawNode : rawList) {
            T uiModel = (T) serviceEntityManager
                    .genUIModelFromUIModelExtension(uiModelClass,
                            serviceUIModelExtension
                                    .genUIModelExtensionUnion().get(0),
                            rawNode, logonActionController.getLogonInfo(), null);
            resultUIModelList.add(uiModel);
        }
        return resultUIModelList;
    }

    /**
     * Provide default convertion to UI Model list for List view
     *
     * @param <T>
     * @return
     */
    public <T extends SEUIComModel> List<T> convUIModuleList(Class<T> uiModelClass, List<ServiceEntityNode> rawList,
                                                             ServiceEntityManager serviceEntityManager,
                                                             ServiceUIModelExtension serviceUIModelExtension,
                                                             List<ServiceModuleConvertPara> additionalConvertParaList)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        List<T> resultUIModelList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        for (ServiceEntityNode rawNode : rawList) {
            T uiModel = (T) serviceEntityManager
                    .genUIModelFromUIModelExtension(uiModelClass,
                            serviceUIModelExtension
                                    .genUIModelExtensionUnion().get(0),
                            rawNode, logonActionController.getLogonInfo(), additionalConvertParaList);
            resultUIModelList.add(uiModel);
        }
        return resultUIModelList;
    }

    public static class ExcelDownloadRequest {

        private Class<?> searchModelClass;

        private String request;

        private String aoId;

        private String acId;

        private String preFileName;

        private String fileName;

        private String nodeInstId;

        private Class<? extends SEUIComModel> excelModelType;

        private DocumentContentSpecifier<?, ?, ?> documentContentSpecifier;

        private IGetListSearchExecutor getListSearchExecutor;

        private ServiceExcelHandlerProxy serviceExcelHandlerProxy;

        private List<ServiceModuleConvertPara> additionalConvertParaList;

        private ServiceUIModelExtension serviceUIModelExtension;

        public ExcelDownloadRequest() {
        }

        public ExcelDownloadRequest(Class<?> searchModelClass, String request, String aoId,
                                    String acId, String preFileName,
                                    ServiceExcelHandlerProxy serviceExcelHandlerProxy) {
            this.searchModelClass = searchModelClass;
            this.request = request;
            this.aoId = aoId;
            this.acId = acId;
            this.preFileName = preFileName;
            this.serviceExcelHandlerProxy = serviceExcelHandlerProxy;
        }

        public ExcelDownloadRequest(Class<?> searchModelClass, String request, String aoId,
                                    String acId, String preFileName,
                                    ServiceExcelHandlerProxy serviceExcelHandlerProxy,
                                    IGetListSearchExecutor getListSearchExecutor, List<ServiceModuleConvertPara> additionalConvertParaList,
                                    Class<? extends SEUIComModel> excelModelType,
                                    String nodeInstId,
                                    DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.searchModelClass = searchModelClass;
            this.request = request;
            this.aoId = aoId;
            this.acId = acId;
            this.preFileName = preFileName;
            this.serviceExcelHandlerProxy = serviceExcelHandlerProxy;
            this.getListSearchExecutor = getListSearchExecutor;
            this.additionalConvertParaList = additionalConvertParaList;
            this.excelModelType = excelModelType;
            this.nodeInstId = nodeInstId;
            this.documentContentSpecifier = documentContentSpecifier;
        }

        public ExcelDownloadRequest(Class<?> searchModelClass, String request, String aoId,
                                    String acId, String preFileName,
                                    ServiceExcelHandlerProxy serviceExcelHandlerProxy,
                                    IGetListSearchExecutor getListSearchExecutor, List<ServiceModuleConvertPara> additionalConvertParaList,
                                    ServiceUIModelExtension serviceUIModelExtension) {
            this.searchModelClass = searchModelClass;
            this.request = request;
            this.aoId = aoId;
            this.acId = acId;
            this.preFileName = preFileName;
            this.serviceExcelHandlerProxy = serviceExcelHandlerProxy;
            this.getListSearchExecutor = getListSearchExecutor;
            this.additionalConvertParaList = additionalConvertParaList;
            this.serviceUIModelExtension = serviceUIModelExtension;
        }

        public IGetListSearchExecutor getGetListSearchExecutor() {
            return getListSearchExecutor;
        }

        public void setGetListSearchExecutor(IGetListSearchExecutor getListSearchExecutor) {
            this.getListSearchExecutor = getListSearchExecutor;
        }

        public List<ServiceModuleConvertPara> getAdditionalConvertParaList() {
            return additionalConvertParaList;
        }

        public void setAdditionalConvertParaList(List<ServiceModuleConvertPara> additionalConvertParaList) {
            this.additionalConvertParaList = additionalConvertParaList;
        }

        public ServiceUIModelExtension getServiceUIModelExtension() {
            return serviceUIModelExtension;
        }

        public void setServiceUIModelExtension(ServiceUIModelExtension serviceUIModelExtension) {
            this.serviceUIModelExtension = serviceUIModelExtension;
        }

        public Class<?> getSearchModelClass() {
            return searchModelClass;
        }

        public void setSearchModelClass(Class<?> searchModelClass) {
            this.searchModelClass = searchModelClass;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getAoId() {
            return aoId;
        }

        public void setAoId(String aoId) {
            this.aoId = aoId;
        }

        public String getAcId() {
            return acId;
        }

        public void setAcId(String acId) {
            this.acId = acId;
        }

        public String getPreFileName() {
            return preFileName;
        }

        public void setPreFileName(String preFileName) {
            this.preFileName = preFileName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public ServiceExcelHandlerProxy getServiceExcelHandlerProxy() {
            return serviceExcelHandlerProxy;
        }

        public void setServiceExcelHandlerProxy(ServiceExcelHandlerProxy serviceExcelHandlerProxy) {
            this.serviceExcelHandlerProxy = serviceExcelHandlerProxy;
        }

        public Class<? extends SEUIComModel> getExcelModelType() {
            return excelModelType;
        }

        public void setExcelModelType(Class<? extends SEUIComModel> excelModelType) {
            this.excelModelType = excelModelType;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public DocumentContentSpecifier<?, ?, ?> getDocumentContentSpecifier() {
            return documentContentSpecifier;
        }

        public void setDocumentContentSpecifier(DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.documentContentSpecifier = documentContentSpecifier;
        }
    }

    public void checkLogonUser() throws LogonInfoException {
        LogonUser logonUser = logonActionController.getLogonUser();
        if (logonUser == null) {
            throw new LogonInfoException(
                    LogonInfoException.TYPE_NO_LOGON_USER);
        }
    }

    public ResponseEntity<byte[]> defaultDownloadExcelTemplate(ExcelDownloadRequest excelDownloadRequest) throws ServiceComExecuteException,
            ServiceEntityExceptionContainer {
        try {
            final HttpHeaders headers = new HttpHeaders();
            checkLogonUser();
            preCheckResourceAccessCore(
                    excelDownloadRequest.getAoId(), excelDownloadRequest.getAcId());
            Class<?> searchModelClass = excelDownloadRequest.getSearchModelClass();
            SEUIComModel searchModel = (SEUIComModel) searchModelClass.getDeclaredConstructor().newInstance();
            String request = excelDownloadRequest.getRequest();
            if (!ServiceEntityStringHelper.checkNullString(request)) {
                JSONObject jsonObject = JSONObject.fromObject(request);
                searchModel = (SEUIComModel) JSONObject
                        .toBean(jsonObject, searchModelClass);
            }
            SearchContext searchContext = SearchContextBuilder.genBuilder(this.logonActionController.getLogonInfo()).
                    acId(excelDownloadRequest.getAcId()).aoId(excelDownloadRequest.getAoId()).searchModel(searchModel).build();
            BSearchResponse bSearchResponse = excelDownloadRequest.getListSearchExecutor.execute(searchContext);
            ServiceExcelHandlerProxy serviceExcelHandlerProxy = excelDownloadRequest.getServiceExcelHandlerProxy();
            ServiceUIModelExtension serviceUIModelExtension = excelDownloadRequest.getServiceUIModelExtension();
            if (serviceUIModelExtension == null) {
                serviceUIModelExtension = getUIModelExtensionFromDocSpecifier(excelDownloadRequest.getDocumentContentSpecifier(), excelDownloadRequest.getNodeInstId());
            }
            List<ServiceModuleConvertPara> additionalConvertParaList = excelDownloadRequest.getAdditionalConvertParaList();
            List<?> seUIModelList = serviceExcelHandlerProxy.getExcelModelListWrapper(bSearchResponse.getResultList(),
                    excelDownloadRequest.getExcelModelType(),
                    serviceUIModelExtension, additionalConvertParaList,
                    logonActionController.getLogonInfo());
            byte[] byteArray = serviceExcelHandlerProxy.buildExcelDocument((List<SEUIComModel>) seUIModelList,
                    logonActionController.getSerialLogonInfo());
            String targetFileName = excelDownloadRequest.getFileName();
            if (ServiceEntityStringHelper.checkNullString(targetFileName)) {
                targetFileName = excelDownloadRequest.getPreFileName()
                        + "-"
                        + DefaultDateFormatConstant.DATE_HOUR_FORMAT
                        .format(new Date());
            }
            headers.setContentDispositionFormData("attachment", targetFileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(
                    byteArray, headers, HttpStatus.CREATED);
        } catch (AuthorizationException | LogonInfoException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceEntityExceptionContainer(e);
        } catch (Exception e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }


    public interface ExcelUploadExecutor {

        /**
         * Executor method to search raw service entity list
         *
         * @param serviceExcelReportResponseModel
         * @param baseUUID
         * @return
         * @throws SearchConfigureException
         */
        void execute(ServiceExcelReportResponseModel serviceExcelReportResponseModel,
                     String baseUUID) throws ServiceComExecuteException, ServiceExcelConfigException;

    }

    public static class ExcelUploadRequest {

        private HttpServletRequest request;

        private ServiceExcelHandlerProxy serviceExcelHandlerProxy;

        private String aoId;

        private String acCode;

        private ExcelUploadExecutor excelUploadExecutor;

        public ExcelUploadRequest() {

        }

        public ExcelUploadRequest(HttpServletRequest request, ServiceExcelHandlerProxy serviceExcelHandlerProxy,
                                  String aoId, String acCode, ExcelUploadExecutor excelUploadExecutor) {
            this.request = request;
            this.serviceExcelHandlerProxy = serviceExcelHandlerProxy;
            this.aoId = aoId;
            this.acCode = acCode;
            this.excelUploadExecutor = excelUploadExecutor;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }

        public ServiceExcelHandlerProxy getServiceExcelHandlerProxy() {
            return serviceExcelHandlerProxy;
        }

        public void setServiceExcelHandlerProxy(ServiceExcelHandlerProxy serviceExcelHandlerProxy) {
            this.serviceExcelHandlerProxy = serviceExcelHandlerProxy;
        }

        public String getAoId() {
            return aoId;
        }

        public void setAoId(String aoId) {
            this.aoId = aoId;
        }

        public String getAcCode() {
            return acCode;
        }

        public void setAcCode(String acCode) {
            this.acCode = acCode;
        }

        public ExcelUploadExecutor getExcelUploadExecutor() {
            return excelUploadExecutor;
        }

        public void setExcelUploadExecutor(ExcelUploadExecutor excelUploadExecutor) {
            this.excelUploadExecutor = excelUploadExecutor;
        }
    }

    public String uploadExcelWrapper(
            ExcelUploadRequest excelUploadRequest) {
        try {
            HttpServletRequest request = excelUploadRequest.getRequest();
            request.setCharacterEncoding("UTF-8");
            MultipartHttpServletRequest muti = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> map = muti.getMultiFileMap();
            preCheckResourceAccessCore(
                    excelUploadRequest.getAoId(), excelUploadRequest.getAcCode());
            for (Map.Entry<String, List<MultipartFile>> entry : map.entrySet()) {
                List<MultipartFile> list = entry.getValue();
                for (MultipartFile multipartFile : list) {
                    try {
                        String baseUUID = request
                                .getParameter(SimpleSEJSONRequest.BASEUUID);
                        ServiceExcelHandlerProxy serviceExcelHandlerProxy =
                                excelUploadRequest.getServiceExcelHandlerProxy();
                        ServiceExcelReportResponseModel serviceExcelReportResponseModel = serviceExcelHandlerProxy
                                .readDataFromSheet(multipartFile,
                                        logonActionController.getSerialLogonInfo());
                        // execute upload
                        excelUploadRequest.getExcelUploadExecutor().execute(serviceExcelReportResponseModel,
                                baseUUID);
                    } catch (IllegalStateException e) {
                        throw new ServiceComExecuteException(ServiceComExecuteException.PARA_SYSTEM_ERROR,
                                e.getMessage());
                    }
                }
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException | UnsupportedEncodingException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        } catch (LogonInfoException | AuthorizationException | ServiceExcelConfigException |
                 ServiceComExecuteException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex
                    .getErrorMessage());
        }
    }

    /**
     * Provide default convertion to Service UI Model list for List view
     *
     * @param <T>
     * @return
     */
    public <T extends ServiceUIModule> List<T> convServiceUIModuleList(Class<T> serviceUIModelClass,
                                                                       Class<?> serviceModelClass,
                                                                       List<ServiceEntityNode> rawList,
                                                                       ServiceEntityManager serviceEntityManager,
                                                                       String nodeInstId,
                                                                       DocumentContentSpecifier<?, ?, ?> documentContentSpecifier,
                                                                       LogonInfo logonInfo) throws ServiceEntityConfigureException, DocActionException {
        ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromDocSpecifier(documentContentSpecifier, nodeInstId);
        return convServiceUIModuleList(serviceUIModelClass, serviceModelClass, rawList, serviceEntityManager, serviceUIModelExtension, null, logonInfo);
    }

    /**
     * Provide default convertion to Service UI Model list for List view
     *
     * @param <T>
     * @return
     */
    public <T extends ServiceUIModule> List<T> convServiceUIModuleList(Class<T> serviceUIModelClass,
                                                                       Class<?> serviceModelClass,
                                                                       List<ServiceEntityNode> rawList,
                                                                       ServiceEntityManager serviceEntityManager,
                                                                       ServiceUIModelExtension serviceUIModelExtension,
                                                                       LogonInfo logonInfo) throws ServiceEntityConfigureException {
        return convServiceUIModuleList(serviceUIModelClass, serviceModelClass, rawList, serviceEntityManager, serviceUIModelExtension, null, logonInfo);
    }


    /**
     * Provide default convertion to Service UI Model list for List view
     *
     * @param <T>
     * @return
     */
    public <T extends ServiceUIModule> List<T> convServiceUIModuleList(Class<T> serviceUIModelClass,
                                                                       Class<?> serviceModelClass,
                                                                       List<ServiceEntityNode> rawList,
                                                                       ServiceEntityManager serviceEntityManager,
                                                                       ServiceUIModelExtension serviceUIModelExtension, List<ServiceModuleConvertPara> addConvertParaList,
                                                                       LogonInfo logonInfo) throws ServiceEntityConfigureException {
        List<T> resultServiceUIModelList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        for (ServiceEntityNode rawNode : rawList) {

            try {
                ServiceModule serviceModule = serviceEntityManager
                        .loadServiceModuleWithFlatNodes(serviceModelClass,
                                rawNode, serviceUIModelExtension);
                T targetServiceUIModel =
                        (T) serviceEntityManager
                                .genServiceUIModuleWithFlatNode(
                                        serviceUIModelClass,
                                        serviceModelClass,
                                        serviceModule,
                                        serviceUIModelExtension, addConvertParaList, logonInfo);
                ServiceUIModuleProxy.fillInDummyValueForFlatNode(targetServiceUIModel, null,
                        serviceUIModelExtension.genUIModelExtensionUnion().get(0));
                resultServiceUIModelList.add(targetServiceUIModel);
            } catch (ServiceModuleProxyException | ServiceUIModuleProxyException e) {
                e.printStackTrace();
                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
            }
        }
        return resultServiceUIModelList;
    }

    private JSONObject parseRequestToJsonObject(String request) {
        JSONObject jsonObject;
        if (ServiceEntityStringHelper.checkNullString(request)) {
            jsonObject = JSONObject.fromObject("{}");
        } else {
            jsonObject = JSONObject.fromObject(request);
        }
        return jsonObject;
    }

    public String searchModuleWrapper(String targetAOId, String acId, String request, Class<?> searchModelClass,
                                      ISearchDataMethod
                                              searchDataMethod, IConvertSearchDataMethod convertDataList) {
        JSONObject jsonObject = parseRequestToJsonObject(request);
        SEUIComModel searchModelInstance = (SEUIComModel) JSONObject
                .toBean(jsonObject, searchModelClass);
        return searchModuleWrapper(targetAOId, acId, searchModelInstance, searchDataMethod, convertDataList);
    }

    public String searchModuleWrapper(String targetAOId, String acId,
                                      SEUIComModel searchModelInstance, ISearchDataMethod
                                              searchDataMethod, IConvertSearchDataMethod convertDataList) {
        try {
            preCheckResourceAccessCore(
                    targetAOId, acId);
            checkLogonUser();
            SearchContext searchContext = SearchContextBuilder.genBuilder(this.logonActionController.getLogonInfo()).
                    acId(acId).aoId(targetAOId).searchModel(searchModelInstance).build();
            BSearchResponse bSearchResponse = searchDataMethod.apply(searchContext);
            List<ServiceEntityNode> rawList = bSearchResponse.getResultList();
            rawList = sortServiceEntityList(rawList);
            if (convertDataList != null) {
                return ServiceJSONParser
                        .genDefOKJSONArray(convertDataList.apply(rawList));
            } else {
                return ServiceJSONParser
                        .genDefOKJSONArray(rawList);
            }
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String searchModuleTemplate(String targetAOId, String acId, String request, Class<?> searchModelClass,
                                       ISearchDataMethod
                                               searchDataMethod, IConvertSearchDataWrapper convertDataList) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            JsonNode root = mapper.readTree(request);
            SEUIComModel searchModelInstance = (SEUIComModel) mapper.treeToValue(
                    root, searchModelClass);
            return searchModuleTemplate(targetAOId, acId, searchModelInstance, searchDataMethod, convertDataList);
        } catch (IOException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }


    public String searchModuleTemplate(String targetAOId, String acId,
                                       SEUIComModel searchModelInstance, ISearchDataMethod
                                               searchDataMethod, IConvertSearchDataWrapper convertDataList) {
        try {
            preCheckResourceAccessCore(
                    targetAOId, acId);
            checkLogonUser();
            SearchContext searchContext = SearchContextBuilder.genBuilder(this.logonActionController.getLogonInfo()).
                    acId(acId).aoId(targetAOId).searchModel(searchModelInstance).build();
            BSearchResponse bSearchResponse = searchDataMethod.apply(searchContext);
            List<ServiceEntityNode> rawList = bSearchResponse.getResultList();
            rawList = sortServiceEntityList(rawList);
            if (convertDataList != null) {
                searchContext.setRawSEList(rawList);
                return ServiceJSONParser
                        .genDefOKJSONArray(convertDataList.apply(searchContext));
            } else {
                return ServiceJSONParser
                        .genDefOKJSONArray(rawList);
            }
        } catch (SearchConfigureException | ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | NodeNotFoundException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String loadLeanModuleListWrapper(String targetAOId, String acId, String request, Class<?> searchModelClass,
                                            ISearchDataMethod
                                                    searchDataMethod) {
        JSONObject jsonObject = parseRequestToJsonObject(request);
        SEUIComModel searchModelInstance = (SEUIComModel) JSONObject
                .toBean(jsonObject, searchModelClass);
        return searchModuleWrapper(targetAOId, acId, searchModelInstance, searchDataMethod, null);
    }

    public String searchTableServiceWrapper(String targetAOId, String acId, String request,
                                            SEListController seListController, Class<?> searchModelClass, ISearchDataMethod searchDataMethod,
                                            IConvertSearchDataMethod convertDataList) {
        try {
            preCheckResourceAccessCore(
                    targetAOId, acId);
            return seListController
                    .searchDataTableCore(
                            request,
                            logonActionController.getClient(),
                            new ServiceEntityNodeLastUpdateTimeCompare(),
                            searchModelClass,
                            dataTableRequestData -> {
                                try {
                                    SearchContext searchContext =
                                            generateSearchContext(targetAOId,
                                                    acId, logonActionController.getClient(), dataTableRequestData);
                                    return searchDataMethod.apply(searchContext);
                                } catch (ServiceEntityConfigureException | NodeNotFoundException e) {
                                    throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
                                } catch (AuthorizationException e) {
                                    throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR,
                                            e.getErrorMessage());
                                }
                            }, convertDataList);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException | IOException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    public ServiceEntityNode loadDataByCheckAccess(String uuid,
                                                   ServiceUIModelRequest serviceUIModelRequest, String acId,
                                                   boolean lockFlag,
                                                   List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList)
            throws ServiceEntityConfigureException, AuthorizationException, LogonInfoException {
        return loadDataByCheckAccess(uuid, serviceUIModelRequest.getServiceEntityManager(),
                serviceUIModelRequest.getNodeName(), serviceUIModelRequest.getResourceId(), acId, lockFlag,
                authorizationACUnionList);
    }

    public ServiceEntityNode loadDataByCheckAccess(String uuid,
                                                   ServiceEntityManager serviceEntityManager, String nodeName,
                                                   String targetAOId, String acId,
                                                   boolean lockFlag,
                                                   List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList)
            throws ServiceEntityConfigureException, AuthorizationException, LogonInfoException {
        return loadDataByCheckAccess(uuid, IServiceEntityNodeFieldConstant.UUID, serviceEntityManager, nodeName,
                targetAOId, acId, lockFlag, authorizationACUnionList);
    }

    public ServiceEntityNode loadDataByCheckAccess(Object keyValue, String keyName,
                                                   ServiceEntityManager serviceEntityManager, String nodeName,
                                                   String targetAOId, String acId,
                                                   boolean lockFlag,
                                                   List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList)
            throws ServiceEntityConfigureException, AuthorizationException, LogonInfoException {
        preCheckResourceAccessCore(
                targetAOId, acId);
        ServiceEntityNode serviceEntityNode = serviceEntityManager
                .getEntityNodeByKey(keyValue, keyName,
                        nodeName, logonActionController.getClient(), null);
        if (!ServiceEntityStringHelper.checkNullString(targetAOId) && !ServiceEntityStringHelper.checkNullString(acId)) {
            boolean checkAuthor = checkTargetDataAccess(logonActionController.getLogonUser(),
                    serviceEntityNode, targetAOId, acId,
                    authorizationACUnionList);
            if (!checkAuthor) {
                throw new AuthorizationException(
                        AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
        }
        if (lockFlag) {
            lockObjectManager.lockServiceEntityList(ServiceCollectionsHelper.asList(serviceEntityNode),
                    logonActionController.getLogonUser(),
                    logonActionController.getOrganizationByUser(logonActionController.getResUserUUID()));
        }
        return serviceEntityNode;
    }

    public ServiceUIModule refreshLoadServiceUIModel(ServiceUIModelRequest serviceUIModelRequest,
                                                     String uuid, String acId)
            throws ServiceEntityConfigureException, LogonInfoException, AuthorizationException,
            ServiceUIModuleProxyException, ServiceModuleProxyException, DocActionException {
        return refreshLoadServiceUIModel(serviceUIModelRequest, uuid, null, acId);
    }

    public <T extends ServiceUIModule> T refreshLoadServiceUIModel(ServiceUIModelRequest serviceUIModelRequest,
                                                                   String uuid, IServiceUIModuleExecutor<T> iServiceUIModuleExecutor, String acId)
            throws ServiceEntityConfigureException, LogonInfoException, AuthorizationException,
            ServiceUIModuleProxyException, ServiceModuleProxyException, DocActionException {
        ServiceEntityNode serviceEntityNode =
                serviceUIModelRequest.getServiceEntityManager().getEntityNodeByUUID(uuid,
                        serviceUIModelRequest.getNodeName(), logonActionController.getClient());
        return refreshLoadServiceUIModel(serviceUIModelRequest, serviceEntityNode, iServiceUIModuleExecutor, acId);
    }

    public ServiceUIModule refreshLoadServiceUIModel(ServiceUIModelRequest serviceUIModelRequest,
                                                     ServiceEntityNode serviceEntityNode, String acId)
            throws ServiceEntityConfigureException, LogonInfoException, AuthorizationException,
            ServiceUIModuleProxyException, ServiceModuleProxyException, DocActionException {
        return refreshLoadServiceUIModel(serviceUIModelRequest, serviceEntityNode, null, acId);
    }

    public <T extends ServiceUIModule> T refreshLoadServiceUIModel(ServiceUIModelRequest serviceUIModelRequest,
                                                                   ServiceEntityNode serviceEntityNode, IServiceUIModuleExecutor<T> iServiceUIModuleExecutor, String acId)
            throws ServiceEntityConfigureException, LogonInfoException, AuthorizationException,
            ServiceUIModuleProxyException, ServiceModuleProxyException, DocActionException {
        LogonInfo logonInfo = logonActionController.getLogonInfo();
        if (!ServiceEntityStringHelper.checkNullString(acId)) {
            boolean checkAuthor = checkTargetDataAccess(logonInfo.getLogonUser(),
                    serviceEntityNode, acId,
                    logonInfo.getAuthorizationActionCodeMap());
            if (!checkAuthor) {
                throw new AuthorizationException(
                        AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
        }
        ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(serviceUIModelRequest);
        ServiceModule serviceModel = serviceUIModelRequest.getServiceEntityManager()
                .loadServiceModule(serviceUIModelRequest.getServiceModuleType(),
                        serviceEntityNode, serviceUIModelExtension);
        T serviceUIModel = (T) serviceUIModelRequest.getServiceEntityManager()
                .genServiceUIModuleFromServiceModel(
                        serviceUIModelRequest.getServiceUIModuleType(),
                        serviceUIModelRequest.getServiceModuleType(),
                        serviceModel,
                        serviceUIModelExtension,
                        serviceUIModelRequest.getServiceModuleConvertParaList(), logonInfo);
        if (iServiceUIModuleExecutor != null) {
            serviceUIModel = iServiceUIModuleExecutor.execute(serviceUIModel, serviceModel);
        }
        return serviceUIModel;
    }

    public String newModuleService(ServiceUIModelRequest serviceUIModelRequest,
                                   ICreateServiceModule createServiceModule, String acId) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), acId);
            ServiceModule serviceModule = createServiceModule.execute();
            ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(serviceUIModelRequest);
            ServiceUIModule serviceUIModule = serviceUIModelRequest.getServiceEntityManager().genServiceUIModuleFromServiceModel(
                    serviceUIModelRequest.getServiceUIModuleType(), serviceUIModelRequest.getServiceModuleType(),
                    serviceModule, serviceUIModelExtension,
                    logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONObject(serviceUIModule);
        } catch (LogonInfoException | AuthorizationException | ServiceModuleProxyException |
                 ServiceUIModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public ServiceUIModule parseToServiceUIModel(String request, Class<?> serviceUIModelType, ServiceUIModelExtension serviceUIModelExtension) throws ServiceEntityConfigureException, DocActionException {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = generateParseModelClassMap(serviceUIModelType);
        List<String> blockSaveSubNodeNameList = serviceUIModelExtension.getBlockSaveSubNodeNameList();
        ServiceCollectionsHelper.traverseListInterrupt(blockSaveSubNodeNameList, blockSaveNode -> {
            jsonObject.remove(blockSaveNode);
            return true;
        });
        return (ServiceUIModule) JSONObject
                .toBean(jsonObject, serviceUIModelType,
                        classMap);
    }

    public <R extends ServiceModule> R parseToServiceModel(String request, Class<?> serviceUIModelType, Class<R> serviceModuleType,
                                                           ServiceEntityManager serviceEntityManager, ServiceUIModelExtension serviceUIModelExtension)
            throws ServiceUIModuleProxyException, ServiceModuleProxyException, ServiceEntityConfigureException, DocActionException {
        ServiceUIModule serviceUIModule = parseToServiceUIModel(request, serviceUIModelType, serviceUIModelExtension);
        return (R) serviceEntityManager
                .genServiceModuleFromServiceUIModel(
                        serviceModuleType,
                        serviceUIModelType,
                        serviceUIModule,
                        serviceUIModelExtension);
    }

    private Map<String, Class> generateParseModelClassMap(Class<?> serviceUIModelType) {
        List<Field> listFieldList = ServiceUIModuleProxy.getListTypeFields(serviceUIModelType);
        Map<String, Class> classMap = new HashMap<>();
        for (Field listField : listFieldList) {
            ;
            classMap.put(listField.getName(), ServiceEntityFieldsHelper
                    .getListSubType(listField));
        }
        return classMap;
    }

    public <R extends ServiceModule> String executeDocActionFramework(@RequestBody String request,
                                                                      DocActionNodeProxy.IActionExecutor<R> iActionExecutor, DocUIModelWithActionRequest docUIModelWithActionRequest) {
        try {
            DocumentContentSpecifier documentContentSpecifier = docUIModelWithActionRequest.getDocActionExecutionProxy().getDocumentContentSpecifier();
            ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(docUIModelWithActionRequest);
            return defaultActionServiceWrapper(request, docUIModelWithActionRequest.getResourceId(),
                    docUIModelWithActionRequest.getDocActionExecutionProxy(), documentContentSpecifier.getDocNodeInstId(),
                    documentContentSpecifier.getDocumentManager(),
                    new DocActionNodeProxy.IActionCodeServiceExecutor<R, ServiceUIModule>() {
                        @Override
                        public R parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException, ServiceUIModuleProxyException {
                            try {
                                return parseToServiceModel(request, docUIModelWithActionRequest.getServiceUIModuleType(),
                                        (Class<R>) docUIModelWithActionRequest.getServiceModuleType(),
                                        docUIModelWithActionRequest.getServiceEntityManager(), serviceUIModelExtension);
                            } catch (DocActionException e) {
                                //TODO move this DocActionException to method header.
                                throw new ServiceUIModuleProxyException(ServiceUIModuleProxyException.PARA_SYSTEM_WRONG, e.getErrorMessage());
                            }
                        }

                        @Override
                        public boolean preExecute(ServiceModule serviceModule, int actionCode) {
                            return true;
                        }

                        @Override
                        public void executeService(R serviceModule, int actionCode,
                                                   LogonInfo logonInfo) throws DocActionException,
                                ServiceEntityConfigureException, ServiceModuleProxyException {
                            if (iActionExecutor != null) {
                                iActionExecutor.executeService(serviceModule, actionCode, logonInfo);
                            }
                        }

                        @Override
                        public ServiceUIModule refreshServiceUIModel(ServiceModule serviceModule
                                , String acId, LogonInfo logonInfo) throws ServiceEntityConfigureException,
                                LogonInfoException, ServiceModuleProxyException, ServiceUIModuleProxyException, AuthorizationException, DocActionException {
                            try {
                                ServiceEntityNode serviceEntityNode = ServiceModuleProxy.getCoreServiceEntityNode(serviceModule, docUIModelWithActionRequest.getNodeInstId());
                                return refreshLoadServiceUIModel(docUIModelWithActionRequest, serviceEntityNode, acId);
                            } catch (IllegalAccessException e) {
                                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                            }
                        }

                        @Override
                        public void postHandle(ServiceUIModule serviceUIModule,
                                               int actionCode,
                                               SerialLogonInfo logonInfo) throws DocActionException {

                        }

                    }, serviceUIModelExtension);
        } catch (ServiceEntityConfigureException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }


    //TODO to merge this this deleteModule method
    public String deleteModuleTemplate(DeleteServiceEntityRequest deleteServiceEntityRequest, ServiceUIModelRequest serviceUIModelRequest) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), deleteServiceEntityRequest.getAcId());
            ServiceEntityNode serviceEntityNode = deleteServiceEntityRequest.getServiceEntityNode();
            if (serviceEntityNode == null) {
                serviceEntityNode = serviceUIModelRequest.getServiceEntityManager().getEntityNodeByUUID(deleteServiceEntityRequest.getUuid(),
                        serviceUIModelRequest.getNodeName(), logonActionController.getClient());
            }
            // Calculate deletion strategy
            // Find the relative doc deletion setting
            IDeleteStrategy deleteStrategy = deleteServiceEntityRequest.getDeleteStrategy();
            int deletionStrategy = 0;
            if (deleteStrategy != null) {
                deletionStrategy = deleteStrategy.execute(serviceEntityNode);
            } else {
                ServiceDocDeletionSetting serviceDocDeletionSetting = serviceDocDeletionSettingManager.getServiceDocDeletionSetting(
                        serviceUIModelRequest.getServiceEntityName(), serviceUIModelRequest.getNodeName(), serviceUIModelRequest.getNodeInstId(),
                        logonActionController.getLogonInfo());
                deletionStrategy = calculateRunTimeDeletionStrategy(deleteServiceEntityRequest.getStatus(),
                        deleteServiceEntityRequest.getDocumentContentSpecifier(), serviceDocDeletionSetting);
            }
            if (deletionStrategy == ServiceDocDeletionSetting.DELETESTG_ADM_DELETE) {
                serviceUIModelRequest.getServiceEntityManager().admDeleteModule(
                        serviceEntityNode.getUuid(), IServiceEntityNodeFieldConstant.UUID, serviceEntityNode.getNodeName(),
                        logonActionController.getSerialLogonInfo());
            }
            if (deletionStrategy == ServiceDocDeletionSetting.DELETESTG_BUS_DELETE) {
                serviceUIModelRequest.getServiceEntityManager().archiveModule(
                        serviceEntityNode.getUuid(), IServiceEntityNodeFieldConstant.UUID, serviceEntityNode.getNodeName(),
                        logonActionController.getSerialLogonInfo());
            }
            if (deletionStrategy == ServiceDocDeletionSetting.DELETESTG_FORBD_DELETE) {
                return ServiceJSONParser.genSimpleOKResponse();
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException | LogonInfoException | AuthorizationException | DocActionException |
                 ServiceModuleProxyException | ServiceEntityInstallationException | SearchConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    /**
     * In case the deletion strategy is configured deletion, then use this method to calculate the run time deletionStrategy
     *
     * @param documentContentSpecifier
     * @return
     */
    private int calculateRunTimeDeletionStrategy(int status, DocumentContentSpecifier<?, ?, ?> documentContentSpecifier,
                                                 ServiceDocDeletionSetting serviceDocDeletionSetting) throws DocActionException {
        int rawStrategy = serviceDocDeletionSetting.getDeletionStrategy();
        if (rawStrategy == ServiceDocDeletionSetting.DELETESTG_ADM_DELETE ||
                rawStrategy == ServiceDocDeletionSetting.DELETESTG_BUS_DELETE || rawStrategy == ServiceDocDeletionSetting.DELETESTG_FORBD_DELETE) {
            return rawStrategy;
        }
        List<Integer> conAdmDeleteStatus = ServiceDocDeletionSettingManager.parseToAdmDeleteStatus(serviceDocDeletionSetting.getAdmDeleteStatus());
        List<Integer> admDeleteStatus = ServiceCollectionsHelper.checkNullList(conAdmDeleteStatus) ?
                documentContentSpecifier.getAdmDeleteStatus() : conAdmDeleteStatus;
        if (admDeleteStatus.contains(status)) {
            return ServiceDocDeletionSetting.DELETESTG_ADM_DELETE;
        }
        return ServiceDocDeletionSetting.DELETESTG_BUS_DELETE;
    }

    /**
     * Check if the service entity creation is for a document item node
     *
     * @return
     */
    private boolean checkItemNodeCreation(InitServiceEntityRequest initServiceEntityRequest, String nodeName) {
        DocumentContentSpecifier documentContentSpecifier = initServiceEntityRequest.getDocumentContentSpecifier();
        if (documentContentSpecifier == null) {
            return false;
        }
        String itemNodeName = documentContentSpecifier.getMatItemNodeInstId();
        return nodeName.equalsIgnoreCase(itemNodeName);
    }

    /**
     * Create a service module instance with an initial configuration template for the service document,
     * and returns the corresponding converted Service UI Model instance.
     *
     * @param serviceUIModelRequest    the request for generating the service UI model
     * @param initServiceEntityRequest the request for initializing the service entity
     * @param acId                     the action code ID for authorization check
     * @return the newly generated Service UI Model instance
     */
    public String newModuleServiceDefTemplate(ServiceUIModelRequest serviceUIModelRequest,
                                              InitServiceEntityRequest initServiceEntityRequest, String acId) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), acId);
            ServiceEntityManager serviceEntityManager = serviceUIModelRequest.getServiceEntityManager();
            Class<?> serviceModuleClass = serviceUIModelRequest.getServiceModuleType();
            ServiceEntityNode serviceEntityNode;
            String parentNodeName = initServiceEntityRequest.getParentNodeName();
            String parentNodeUUID = initServiceEntityRequest.getParentNodeUUID();
            String serviceEntityName = initServiceEntityRequest.getServiceEntityName();
            String nodeName = initServiceEntityRequest.getNodeName();
            String nodeInstId = initServiceEntityRequest.getNodeInstId() != null ?
                    initServiceEntityRequest.getNodeInstId() :
                    ServiceEntityStringHelper.getDefModelId(serviceUIModelRequest.getServiceEntityName(),
                            serviceUIModelRequest.getNodeName());
            // Step 1: Standard process or Custom process to initialize core entity instance
            if (initServiceEntityRequest.getCreateServiceEntityNode() != null) {
                // Custom process to initialize core entity instance
                serviceEntityNode = initServiceEntityRequest.getCreateServiceEntityNode().execute(parentNodeUUID, parentNodeName, logonActionController.getClient());
            } else {
                // Standard process to initialize core entity instance
                if (parentNodeName != null && parentNodeUUID != null) {
                    ServiceEntityNode parentNode = serviceEntityManager.getEntityNodeByUUID(parentNodeUUID,
                            parentNodeName, logonActionController.getClient());
                    boolean itemNodeCreation = checkItemNodeCreation(initServiceEntityRequest, nodeName);
                    // In case the creation is for document item
                    if (itemNodeCreation) {
                        DocumentContentSpecifier documentContentSpecifier = initServiceEntityRequest.getDocumentContentSpecifier();
                        serviceEntityNode = documentContentSpecifier.createItem(parentNode);
                    } else {
                        serviceEntityNode = serviceEntityManager.newEntityNode(parentNode, serviceUIModelRequest.getNodeName());
                    }
                } else {
                    serviceEntityNode = serviceEntityManager.newRootEntityNode(logonActionController.getClient());
                }
            }
            List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta> coreInitConfigureMetaList = new ArrayList<>();
            Map<String, List<ServiceDocInitConfigureManager.ServiceDocInitConfigureMeta>> subNodeInitConfigureMetaMap = new HashMap<>();
            // Step 2: Get module initialization configuration from service document initial configuration
            if (serviceEntityName != null && nodeName != null) {
                ServiceDocInitConfigureManager.ServiceDocInitConfigureMetaParseResult serviceDocInitConfigureMetaParseResult =
                        serviceDocInitConfigureManager.getServiceDocInitConfigureMetaResult(serviceEntityName, nodeName,
                                nodeInstId,
                                initServiceEntityRequest.getDocumentContentSpecifier(),
                                logonActionController.getLogonInfo());
                if (serviceDocInitConfigureMetaParseResult != null) {
                    coreInitConfigureMetaList = serviceDocInitConfigureMetaParseResult.getCoreNodeMetaList();
                    subNodeInitConfigureMetaMap = serviceDocInitConfigureMetaParseResult.getSubNodeMetaMap();
                }
            }
            try {
                // Step 3: Initialize the module with the service document initial configuration.
                if (!ServiceCollectionsHelper.checkNullList(coreInitConfigureMetaList)) {
                    serviceDocInitConfigureManager.initServiceEntityWithMeta(coreInitConfigureMetaList, serviceEntityNode,
                            initServiceEntityRequest.getInputRequest(), logonActionController.getLogonInfo());
                }
                // In case need to execute custom process for service entity node
                if (initServiceEntityRequest.getProcessServiceEntityNode() != null) {
                    serviceEntityNode = initServiceEntityRequest.getProcessServiceEntityNode().execute(serviceEntityNode);
                }
                ServiceModule serviceModule = (ServiceModule) serviceModuleClass.getDeclaredConstructor().newInstance();
                ServiceModuleProxy.setCoreEntityNodeValue(serviceModule, serviceEntityNode,
                        nodeInstId);
                ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(serviceUIModelRequest);
                // Step 4: Initialize with the service document initialization configuration meta map
                if (subNodeInitConfigureMetaMap != null && subNodeInitConfigureMetaMap.size() > 0) {
                    serviceDocInitConfigureManager.initServiceModuleWithMeta(serviceModule, subNodeInitConfigureMetaMap,
                            nodeInstId, serviceEntityManager,serviceUIModelExtension,
                            initServiceEntityRequest.getInputRequest(), logonActionController.getLogonInfo());
                }
                // In case need to execute custom post process for service model
                if (initServiceEntityRequest.getProcessServiceModule() != null) {
                    serviceModule = initServiceEntityRequest.getProcessServiceModule().execute(serviceModule);
                }
                ServiceUIModule serviceUIModule = serviceUIModelRequest.getServiceEntityManager().genServiceUIModuleFromServiceModel(
                        serviceUIModelRequest.getServiceUIModuleType(), serviceUIModelRequest.getServiceModuleType(),
                        serviceModule, serviceUIModelExtension,
                        logonActionController.getLogonInfo());
                return ServiceJSONParser.genDefOKJSONObject(serviceUIModule);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchFieldException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        } catch (LogonInfoException | AuthorizationException | ServiceModuleProxyException |
                 ServiceUIModuleProxyException | DocActionException | ServiceEntityConfigureException |
                 ServiceEntityInstallationException | SearchConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    public String newDocMatItemServiceDefTemplate(ServiceUIModelRequest serviceUIModelRequest,
                                                  InitDocMatItemRequest initDocMatItemRequest, String acId) {
        IProcessServiceModule configProcessServiceModule = initDocMatItemRequest.getProcessServiceModule();
        initDocMatItemRequest.setProcessServiceModule(serviceModule -> {
            serviceModule = defCreateDocMatItemServiceModel(serviceModule.getClass(),
                    initDocMatItemRequest.getParentServiceModuleClass(),
                    initDocMatItemRequest.getDocumentContentSpecifier(),
                    initDocMatItemRequest.getParentNodeUUID());
            if (configProcessServiceModule != null) {
                return configProcessServiceModule.execute(serviceModule);
            } else {
                return serviceModule;
            }
        });
        return newModuleServiceDefTemplate(serviceUIModelRequest,
                initDocMatItemRequest, acId);
    }

    public <R extends ServiceModule> ServiceModule defCreateDocMatItemServiceModel(Class<?> serviceModuleClass,
                                                                                   Class<R> parentServiceModuleClass,
                                                                                   DocumentContentSpecifier<?, ?,
                                                                                           ?> documentContentSpecifier, String baseUUID)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, DocActionException {
        DocumentContentSpecifier<R, ?,
                ?> localDocumentContentSpecifier = (DocumentContentSpecifier<R, ?, ?>) documentContentSpecifier;
        R parentServiceModel =
                localDocumentContentSpecifier.loadServiceModule(baseUUID, logonActionController.getClient());
        return localDocumentContentSpecifier.createItemServiceModule(parentServiceModel);
    }

    public String deleteModule(String uuid, String acId, ServiceUIModelRequest serviceUIModelRequest) {
        return deleteModule(uuid, acId, serviceUIModelRequest, null, null);
    }

    public String deleteModule(String uuid, String acId, ServiceUIModelRequest serviceUIModelRequest,
                               IServieEntityExecutor preDeleteExecutor, IServieEntityExecutor deleteExecutor) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), acId);
            ServiceEntityNode serviceEntityNode =
                    serviceUIModelRequest.getServiceEntityManager().getEntityNodeByUUID(uuid,
                            serviceUIModelRequest.getNodeName(), logonActionController.getClient());
            if (preDeleteExecutor != null) {
                preDeleteExecutor.execute(serviceEntityNode);
            }
            if (deleteExecutor != null) {
                deleteExecutor.execute(serviceEntityNode);
            } else {
                serviceUIModelRequest.getServiceEntityManager().deleteSENode(
                        serviceEntityNode, null, null);
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException | LogonInfoException | AuthorizationException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    public String deleteDocMatItem(String uuid, String acId, ServiceUIModelRequest serviceUIModelRequest) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), acId);
            ServiceEntityNode serviceEntityNode =
                    serviceUIModelRequest.getServiceEntityManager().getEntityNodeByUUID(uuid,
                            serviceUIModelRequest.getNodeName(), logonActionController.getClient());
            docFlowProxy.deleteDocMatItem((DocMatItemNode) serviceEntityNode, serviceUIModelRequest.getServiceEntityManager(),
                    logonActionController.getSerialLogonInfo());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException | LogonInfoException | AuthorizationException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    public interface IVoidExecutor {

        void execute() throws DocActionException, ServiceEntityConfigureException;

    }

    public interface IGetObjectExecutor<T> {

        T execute() throws DocActionException, ServiceEntityConfigureException, AuthorizationException, LogonInfoException;

    }

    public interface IServieEntityExecutor {

        void execute(ServiceEntityNode serviceEntityNode) throws DocActionException, ServiceEntityConfigureException;

    }

    public interface IGetServiceModuleExecutor<T extends ServiceModule> {

        T execute(T serviceModule) throws DocActionException, ServiceEntityConfigureException;

    }

    public interface IServiceUIModuleExecutor<T extends ServiceUIModule> {

        T execute(T serviceUIModule, ServiceModule serviceModule) throws DocActionException, ServiceEntityConfigureException;

    }

    public interface IGetMapExecutor<T> {

        Map<T, String> execute(String lanCode) throws ServiceEntityInstallationException, ServiceEntityConfigureException;

    }

    public interface IGetListExecutor<T> {

        List<T> execute() throws DocActionException, ServiceEntityConfigureException, ServiceEntityInstallationException;

    }

    public interface IGetListSearchExecutor {

        BSearchResponse execute(SearchContext searchContext) throws ServiceEntityInstallationException, ServiceEntityConfigureException, AuthorizationException, SearchConfigureException, LogonInfoException;
    }

    public <T> String getMapMeta(IGetMapExecutor<T> getMapExecutor) {
        return getMapMeta(getMapExecutor, null, null);
    }

    public <T> String getMapMeta(IGetMapExecutor<T> getMapExecutor, String resourceId, String acId) {
        try {
            checkResourceAccessWrapper(resourceId, acId);
            Map<T, String> metaMap = getMapExecutor.execute(logonActionController.getLanguageCode());
            return ServiceEntityManager.getSelectMap(metaMap, false);
        } catch (ServiceEntityInstallationException | LogonInfoException | ServiceEntityConfigureException |
                 AuthorizationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public void checkResourceAccessWrapper(String resourceId,
                                           String acId) throws LogonInfoException, AuthorizationException{
        if (!ServiceEntityStringHelper.checkNullString(resourceId) && ServiceEntityStringHelper.checkNullString(acId)) {
            preCheckResourceAccess(resourceId, acId);
        } else {
            checkLogonUser();
        }
    }

    public <T> String getObjectMeta(IGetObjectExecutor<T> getObjectExecutor) {
        return getObjectMeta(getObjectExecutor, null, null);
    }

    public <T> String getObjectMeta(IGetObjectExecutor<T> getObjectExecutor, String resourceId,
                                    String acId) {
        try {
            checkResourceAccessWrapper(resourceId, acId);
            Object response = getObjectExecutor.execute();
            return ServiceJSONParser.genDefOKJSONObject(response);
        } catch (LogonInfoException | DocActionException | ServiceEntityConfigureException | AuthorizationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    public String updateSerialIdToRegProduct(
            String request, DocUIModelWithActionRequest docUIModelWithActionRequest, String resourceId,
            String acId) {
        return voidExecuteWrapper(() -> {
            try {
                SerialIdInputBatchModel serialIdInputBatchModel = SerialIdDocumentProxy.parseToSerialIdInputBatchModel(request);
                ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(docUIModelWithActionRequest);
                ServiceEntityNode serviceEntityNode =
                        docUIModelWithActionRequest.getServiceEntityManager().getEntityNodeByUUID(serialIdInputBatchModel.getBaseUUID(),
                                docUIModelWithActionRequest.getNodeName(), logonActionController.getClient());
                ServiceModule serviceModel = docUIModelWithActionRequest.getServiceEntityManager()
                        .loadServiceModule(docUIModelWithActionRequest.getServiceModuleType(),
                                serviceEntityNode,serviceUIModelExtension);
                List<SerialIdInputModel> serialIdInputModelList = serialIdInputBatchModel.getSerialIdInputModelList();
                if (ServiceCollectionsHelper.checkNullList(serialIdInputModelList)) {
                    return;
                }
                DocumentContentSpecifier documentContentSpecifier = docUIModelWithActionRequest.getDocActionExecutionProxy().getDocumentContentSpecifier();
                List<ServiceEntityNode> docMatNodeItemList = documentContentSpecifier.getDocMatItemList(serviceModel);
                if (ServiceCollectionsHelper.checkNullList(docMatNodeItemList)) {
                    return;
                }
                Map<String, List<SerialIdDocumentProxy.DocMatItemMaterialUnion>> docItemUnionListMap =
                        serialIdDocumentProxy.buildDocItemUnionListMap(docMatNodeItemList, false);
                for (SerialIdInputModel serialIdInputModel : serialIdInputModelList) {
                    serialIdDocumentProxy.updateSerialIdToRegProduct(serialIdInputModel.getSerialIdList(), docMatNodeItemList,
                            docItemUnionListMap,
                            serialIdInputModel.getRefTemplateMaterialSKUUUID(), logonActionController.getResUserUUID(), logonActionController.getResOrgUUID());
                }
            } catch (MaterialException | ServiceModuleProxyException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, resourceId, acId);
    }

    public @ResponseBody String initSerialInputBatchModel(String uuid, DocUIModelWithActionRequest docUIModelWithActionRequest, String resourceId,
                                                          String acId) {
        return getObjectMeta(() -> {
            try {
                ServiceEntityNode serviceEntityNode = loadDataByCheckAccess(uuid, docUIModelWithActionRequest.getServiceEntityManager(),
                        docUIModelWithActionRequest.getNodeName(),
                        docUIModelWithActionRequest.getResourceId(),
                        acId, false,
                        logonActionController.getLogonInfo().getAuthorizationACUnionList());
                ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(docUIModelWithActionRequest);
                ServiceModule serviceModel = docUIModelWithActionRequest.getServiceEntityManager()
                        .loadServiceModule(docUIModelWithActionRequest.getServiceModuleType(),
                                serviceEntityNode, serviceUIModelExtension);
                DocumentContentSpecifier documentContentSpecifier = docUIModelWithActionRequest.getDocActionExecutionProxy().getDocumentContentSpecifier();
                List<ServiceEntityNode> docMatNodeItemList = documentContentSpecifier.getDocMatItemList(serviceModel);
                if (ServiceCollectionsHelper.checkNullList(docMatNodeItemList)) {
                    return null;
                }
                return serialIdDocumentProxy.initSerialInputBatchModel(uuid
                        , logonActionController.getClient(), docMatNodeItemList);
            } catch (AuthorizationException | ServiceModuleProxyException | LogonInfoException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }, resourceId, acId);
    }

    public static <T> T parseRequestWrapper(String request, Class<T> classType) {
        return parseRequestWrapper(request, classType, new HashMap<>());
    }

    @SuppressWarnings("rawtypes")
    public static <T> T parseRequestWrapper(String request, Class<T> classType, Map<String, Class> classMap) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        T result = (T) JSONObject
                .toBean(jsonObject, classType,
                        classMap);
        return result;
    }

    public String voidExecuteWrapper(IVoidExecutor voidExecutor, String resourceId,
                                     String acId) {
        try {
            checkResourceAccessWrapper(resourceId, acId);
            voidExecutor.execute();
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException | DocActionException | ServiceEntityConfigureException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        }
    }

    public <T> String getListMeta(IGetListExecutor<T> getListExecutor) {
        return getListMeta(getListExecutor, null, null);
    }

    public <T> String getListMeta(IGetListExecutor<T> getListExecutor, String resourceId,
                                  String acId) {
        try {
            if (!ServiceEntityStringHelper.checkNullString(resourceId) && ServiceEntityStringHelper.checkNullString(acId)) {
                preCheckResourceAccess(resourceId, acId);
            } else {
                checkLogonUser();
            }
            List<T> response = getListExecutor.execute();
            if (ServiceCollectionsHelper.checkNullList(response)) {
                return ServiceJSONParser.genSimpleOKResponse();
            }
            return ServiceJSONParser.genDefOKJSONArray(response);
        } catch (LogonInfoException | DocActionException | ServiceEntityConfigureException |
                 ServiceEntityInstallationException e) {
            return e.generateSimpleErrorJSON();
        }
    }

    public String getListMeta(IGetListExecutor<ServiceEntityNode> getListExecutor, String resourceId,
                              String acId, IConvertSearchDataMethod convertDataList) {
        try {
            if (!ServiceEntityStringHelper.checkNullString(resourceId) && ServiceEntityStringHelper.checkNullString(acId)) {
                preCheckResourceAccess(resourceId, acId);
            } else {
                checkLogonUser();
            }
            List<ServiceEntityNode> response = getListExecutor.execute();
            if (ServiceCollectionsHelper.checkNullList(response)) {
                return ServiceJSONParser.genSimpleOKResponse();
            }
            if (convertDataList != null) {
                return ServiceJSONParser
                        .genDefOKJSONArray(convertDataList.apply(response));
            } else {
                return ServiceJSONParser.genDefOKJSONArray(response);
            }
        } catch (LogonInfoException | DocActionException | ServiceEntityConfigureException |
                 ServiceModuleProxyException | ServiceEntityInstallationException e) {
            return e.generateSimpleErrorJSON();
        }
    }

    public String getListMeta(String targetAOId, String acId, String request, Class<?> searchModelClass,
                              ISearchDataMethod
                                      searchDataMethod, IConvertSearchDataMethod convertDataList) {
        JSONObject jsonObject = parseRequestToJsonObject(request);
        SEUIComModel searchModelInstance = (SEUIComModel) JSONObject
                .toBean(jsonObject, searchModelClass);
        return searchModuleWrapper(targetAOId, acId, searchModelInstance, searchDataMethod, convertDataList);
    }

    /**
     * pre-check if the edit object list could be locked, whether the EX-lock
     * exist or not.
     */
    public String preLock(String uuid, String acId, ServiceUIModelRequest serviceUIModelRequest) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), acId);
            ServiceEntityNode serviceEntityNode =
                    serviceUIModelRequest.getServiceEntityManager().getEntityNodeByUUID(uuid,
                            serviceUIModelRequest.getNodeName(), logonActionController.getClient());
            String baseUUID = serviceEntityNode.getUuid();
            List<ServiceEntityNode> lockSEList = new ArrayList<>();
            lockSEList.add(serviceEntityNode);
            List<ServiceEntityNode> lockResult = lockObjectManager
                    .preLockServiceEntityList(lockSEList, logonActionController.getResUserUUID());
            return lockObjectManager.genJSONLockCheckResult(lockResult,
                    serviceEntityNode.getName(), serviceEntityNode.getId(), baseUUID);
        } catch (ServiceEntityConfigureException e) {
            return lockObjectManager.genJSONLockCheckOtherIssue(e.getMessage());
        } catch (LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (AuthorizationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public String saveModuleService(ServiceUIModelRequest serviceUIModelRequest, ServiceUIModule serviceUIModule,
                                    IGetServiceModuleExecutor preSaveExecutor,
                                    IGetServiceModuleExecutor postSaveExecutor, IServiceUIModuleExecutor serviceUIModuleExecutor, String uuid,
                                    String acId) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), acId);
            LogonInfo logonInfo = logonActionController.getLogonInfo();
            ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(serviceUIModelRequest);
            ServiceModule serviceModel =
                    serviceUIModelRequest.getServiceEntityManager().genServiceModuleFromServiceUIModel(
                            serviceUIModelRequest.getServiceModuleType(), serviceUIModelRequest.getServiceUIModuleType(),
                            serviceUIModule, serviceUIModelExtension);
            if (preSaveExecutor != null) {
                serviceModel = preSaveExecutor.execute(serviceModel);
            }
            String nodeInstId =
                    ServiceEntityStringHelper.checkNullString(serviceUIModelRequest.getNodeInstId()) ?
                            ServiceEntityStringHelper.getDefModelId(serviceUIModelRequest.getServiceEntityName(),
                                    serviceUIModelRequest.getNodeName()) : serviceUIModelRequest.getNodeInstId();
            serviceUIModelRequest.getServiceEntityManager().updateServiceModuleWithDelete(serviceUIModelRequest.getServiceModuleType(),
                    serviceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID(),
                    nodeInstId, serviceUIModelExtension);
            if (postSaveExecutor != null) {
                postSaveExecutor.execute(serviceModel);
            }
            // Refresh service model
            serviceUIModule =
                    refreshLoadServiceUIModel(serviceUIModelRequest, uuid, serviceUIModuleExecutor,
                            acId);
            return ServiceJSONParser.genDefOKJSONObject(serviceUIModule);
        } catch (LogonInfoException | AuthorizationException | ServiceModuleProxyException |
                 ServiceUIModuleProxyException | DocActionException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String saveModuleService(String request, ServiceUIModelRequest serviceUIModelRequest, String acId) {
        try {
            ServiceUIModelExtension serviceUIModelExtension = getUIModelExtensionFromRequest(serviceUIModelRequest);
            ServiceUIModule serviceUIModule = parseToServiceUIModel(request, serviceUIModelRequest.getServiceUIModuleType(), serviceUIModelExtension);
            SEUIComModel seuiComModel = ServiceUIModuleProxy.getCoreUIModel(serviceUIModule, serviceUIModelExtension.genUIModelExtensionUnion().get(0));
            return saveModuleService(serviceUIModelRequest,
                    serviceUIModule, null, null, null, seuiComModel.getUuid(), acId);
        } catch (ServiceUIModuleProxyException | ServiceEntityConfigureException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (IllegalAccessException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public ServiceUIModelExtension getUIModelExtensionFromRequest(ServiceUIModelRequest serviceUIModelRequest) throws ServiceEntityConfigureException, DocActionException {
        if (serviceUIModelRequest.getServiceUIModelExtension() != null) {
            return serviceUIModelRequest.getServiceUIModelExtension();
        }
        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier = serviceUIModelRequest.getDocumentContentSpecifier();
        if (documentContentSpecifier == null) {
            return null;
        }
        String nodeInstId = Optional.ofNullable(serviceUIModelRequest.getNodeInstId()).orElseGet(serviceUIModelRequest::getNodeName);
        return getUIModelExtensionFromDocSpecifier(documentContentSpecifier, nodeInstId);
    }

    public ServiceUIModelExtension getUIModelExtensionFromDocSpecifier(DocumentContentSpecifier<?, ?, ?> documentContentSpecifier, String nodeInstId) throws ServiceEntityConfigureException, DocActionException {
        DocUIModelExtensionBuilder docUIModelExtensionBuilder = documentContentSpecifier.getDocUIModelExtensionBuilder();
        if (docUIModelExtensionBuilder == null) {
            return null;
        }
        DocumentContentSpecifier.DocMetadata docMetadata = documentContentSpecifier.getDocMetadata();

        if (documentContentSpecifier.getDocNodeName().equals(nodeInstId) || documentContentSpecifier.getDocNodeInstId().equals(nodeInstId)){
            // In case this is for doc root node
            Class<? extends ServiceEntityNode> docModelClass = docMetadata.getDocModelClass();
            if (DocumentContent.class.isAssignableFrom(docModelClass)) {
                return docUIModelExtensionBuilder.buildDoc();
            } else {
                return docUIModelExtensionBuilder.buildRootNode();
            }
        }
        if (docMetadata.getItemModelClass() != null && documentContentSpecifier.getMatItemNodeInstId().equals(nodeInstId)){
            // In case this is for doc material item node
            Class<? extends ServiceEntityNode> itemModelClass = docMetadata.getItemModelClass();
            if (DocMatItemNode.class.isAssignableFrom(itemModelClass)) {
                return docUIModelExtensionBuilder.buildDocItem();
            } else {
                return docUIModelExtensionBuilder.buildItem();
            }
        }
        return docUIModelExtensionBuilder.buildSubNode(nodeInstId);
    }

    public String saveModuleService(ServiceUIModelRequest serviceUIModelRequest, ServiceUIModule serviceUIModule,
                                    String uuid, String acId) {
        return saveModuleService(serviceUIModelRequest, serviceUIModule, null, null, null, uuid, acId);
    }

    public String getDocActionNodeList(String uuid, String actionCode, DocUIModelWithActionRequest docUIModelWithActionRequest) {
        try {
            List<DocActionNodeUIModel> docActionNodeUIModelList = docActionNodeProxy.getSubActionCodeUIModelList(uuid
                    , docUIModelWithActionRequest.getServiceEntityName(), docUIModelWithActionRequest.getActionNodeName(), actionCode,
                    docUIModelWithActionRequest.getServiceEntityManager(), null,
                    logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONArray(docActionNodeUIModelList);
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    public String loadModule(String uuid, String acId, ServiceUIModelRequest serviceUIModelRequest) {
        try {
            ServiceEntityNode serviceEntityNode = loadDataByCheckAccess(uuid, serviceUIModelRequest.getServiceEntityManager(),
                    serviceUIModelRequest.getNodeName(),
                    serviceUIModelRequest.getResourceId(),
                    acId, false,
                    logonActionController.getLogonInfo().getAuthorizationACUnionList());
            return ServiceJSONParser.genDefOKJSONObject(serviceEntityNode);
        } catch (AuthorizationException | LogonInfoException ex) {
            return ex.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            logger.error(e.getErrorMessage());
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

    public String loadModuleViewService(String uuid, String acId, ServiceUIModelRequest serviceUIModelRequest) {
        return loadModuleViewService(uuid, IServiceEntityNodeFieldConstant.UUID, acId, null, serviceUIModelRequest);
    }

    public <T extends ServiceUIModule> String loadModuleViewService(String uuid, String acId, IServiceUIModuleExecutor<T> postServiceUIModel,
                                                                    ServiceUIModelRequest serviceUIModelRequest) {
        return loadModuleViewService(uuid, IServiceEntityNodeFieldConstant.UUID, acId, postServiceUIModel, serviceUIModelRequest);
    }

    public String loadModuleViewService(Object keyValue, String keyName, String acId,
                                        ServiceUIModelRequest serviceUIModelRequest) {
        return loadModuleViewService(keyValue, keyName, acId, null, serviceUIModelRequest);
    }

    public <T extends ServiceUIModule> String loadModuleViewService(Object keyValue, String keyName, String acId,
                                                                    IServiceUIModuleExecutor<T> postServiceUIModel, ServiceUIModelRequest serviceUIModelRequest) {
        try {
            ServiceEntityNode serviceEntityNode = loadDataByCheckAccess(keyValue,
                    keyName, serviceUIModelRequest.getServiceEntityManager(),
                    serviceUIModelRequest.getNodeName(),
                    serviceUIModelRequest.getResourceId(),
                    acId, false,
                    logonActionController.getLogonInfo().getAuthorizationACUnionList());
            ServiceUIModule serviceUIModule = refreshLoadServiceUIModel(
                    serviceUIModelRequest,
                    serviceEntityNode, postServiceUIModel, acId);
            return ServiceJSONParser
                    .genDefOKJSONObject(serviceUIModule);
        } catch (AuthorizationException | LogonInfoException ex) {
            return ex.generateSimpleErrorJSON();
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ServiceEntityConfigureException |
                 DocActionException e) {
            logger.error(e.getErrorMessage());
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public String loadModuleEditService(String uuid, String acId,
                                        boolean lockFlag, ServiceUIModelRequest serviceUIModelRequest) {
        return loadModuleEditService(uuid, acId, lockFlag, null, serviceUIModelRequest);
    }

    public <T extends ServiceUIModule> String loadModuleEditService(String uuid, String acId,
                                                                    boolean lockFlag, IServiceUIModuleExecutor<T> postServiceUIModel, ServiceUIModelRequest serviceUIModelRequest) {
        try {
            ServiceEntityNode serviceEntityNode = loadDataByCheckAccess(uuid, serviceUIModelRequest.getServiceEntityManager(),
                    serviceUIModelRequest.getNodeName(),
                    serviceUIModelRequest.getResourceId(),
                    acId, lockFlag,
                    logonActionController.getLogonInfo().getAuthorizationACUnionList());
            ServiceUIModule serviceUIModule = refreshLoadServiceUIModel(
                    serviceUIModelRequest,
                    serviceEntityNode, postServiceUIModel, acId);
            return ServiceJSONParser
                    .genDefOKJSONObject(serviceUIModule);
        } catch (AuthorizationException | LogonInfoException ex) {
            return ex.generateSimpleErrorJSON();
        } catch (ServiceModuleProxyException | ServiceUIModuleProxyException | ServiceEntityConfigureException |
                 DocActionException e) {
            logger.error(e.getErrorMessage());
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public String getDocFlowList(ServiceUIModelRequest serviceUIModelRequest, String uuid, String acId) {
        try {
            preCheckResourceAccessCore(serviceUIModelRequest.getResourceId(), acId);
            ServiceEntityNode serviceEntityNode =
                    serviceUIModelRequest.getServiceEntityManager().getEntityNodeByUUID(uuid,
                            serviceUIModelRequest.getNodeName(), logonActionController.getClient());
            List<ServiceDocumentExtendUIModel> docFlowList =
                    serviceDocumentComProxy.getDocFlowList(serviceEntityNode, serviceUIModelRequest.getServiceEntityManager(),
                            acId, logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONArray(docFlowList);
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public boolean checkTargetDataAccess(LogonUser logonUser,
                                         ServiceEntityNode targetNode,
                                         String acId,
                                         Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap)
            throws LogonInfoException, ServiceEntityConfigureException {
        Organization homeOrganization = logonActionController
                .getOrganizationByUser(logonUser.getUuid());
        List<ServiceEntityNode> organizationList = logonActionController
                .getOrganizationListByUser(logonUser);
        boolean accessFlag = authorizationManager
                .checkDataAccessBySystemAuthorization(logonUser, targetNode,
                        acId, homeOrganization, organizationList, authorizationActionCodeMap);
        return accessFlag;
    }

    public boolean checkTargetDataAccess(LogonUser logonUser,
                                         ServiceEntityNode targetNode,
                                         String targetAOId, String acId,
                                         List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList)
            throws LogonInfoException, ServiceEntityConfigureException {
        Organization homeOrganization = logonActionController
                .getOrganizationByUser(logonUser.getUuid());
        List<ServiceEntityNode> organizationList = logonActionController
                .getOrganizationListByUser(logonUser);
        return authorizationManager
                .checkDataAccessByComAuthorization(logonUser, targetNode, targetAOId, acId, homeOrganization,
                        organizationList, authorizationACUnionList);
    }

    public boolean checkTargetDataAccess(LogonUser logonUser,
                                         ServiceEntityNode targetNode,
                                         String acId,
                                         Organization homeOrganization,
                                         List<ServiceEntityNode> organizationList,
                                         Map<AuthorizationObject, List<ActionCode>> authorizationActionCodeMap)
            throws LogonInfoException, ServiceEntityConfigureException {
        boolean accessFlag = authorizationManager
                .checkDataAccessBySystemAuthorization(logonUser, targetNode,
                        acId, homeOrganization, organizationList, authorizationActionCodeMap);
        return accessFlag;
    }

    public List<ServiceEntityNode> filterDataAccessBySystemAuthor(
            List<ServiceEntityNode> rawList, String acId)
            throws LogonInfoException, ServiceEntityConfigureException {
        if (rawList == null || rawList.size() == 0) {
            return new ArrayList<>();
        }
        LogonUser logonUser = logonActionController.getLogonUser();
        List<ServiceEntityNode> organizationList = logonActionController
                .getOrganizationListByUser(logonUser);
        Organization homeOrganization = logonActionController
                .getOrganizationByUser(logonUser.getUuid());
        return authorizationManager.filterDataAccessBySystemAuthor(rawList, acId, logonUser, homeOrganization, organizationList);
    }


    public List<ServiceEntityNode> filterDataAccessByAuthorization(
            List<ServiceEntityNode> rawList, String aoId, String acId)
            throws LogonInfoException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return new ArrayList<>();
        }
        LogonUser logonUser = logonActionController.getLogonUser();
        List<ServiceEntityNode> organizationList = logonActionController
                .getOrganizationListByUser(logonUser);
        Organization homeOrganization = logonActionController
                .getOrganizationByUser(logonUser.getUuid());
        List<AuthorizationManager.AuthorizationACUnion> authorizationACUnionList = logonActionController.getLogonInfo().getAuthorizationACUnionList();
        return authorizationManager.filterDataAccessByAuthorization(rawList, aoId,
                acId, logonUser, homeOrganization, organizationList, authorizationACUnionList);
    }


    public ResponseEntity<byte[]> loadAttachment(String uuid, String resourceId,
                                                 DocAttachmentProxy.DocAttachmentProcessPara docAttachmentProcessPara) {
        try {
            preCheckResourceAccessCore(
                    resourceId, ISystemActionCode.ACID_VIEW);
            docAttachmentProcessPara.setLogonUserUUID(logonActionController.getResUserUUID());
            return docAttachmentProxy.loadAttachment(docAttachmentProcessPara,
                    uuid);
        } catch (ServiceEntityConfigureException e) {
            throw new ServiceAttachmentException(ServiceAttachmentException.PARA_SYSTEM_WRONG, e.getMessage());
        } catch (LogonInfoException | AuthorizationException e) {
            throw new ServiceAttachmentException(ServiceAttachmentException.PARA_SYSTEM_WRONG, e.getErrorMessage());
        }
    }

    public String deleteAttachment(
            SimpleSEJSONRequest request, String resourceId, DocAttachmentProxy.DocAttachmentProcessPara docAttachmentProcessPara) {
        try {
            preCheckResourceAccessCore(
                    resourceId, ISystemActionCode.ACID_EDIT);
            docAttachmentProcessPara.setLogonUserUUID(logonActionController.getResUserUUID());
            return docAttachmentProxy.deleteAttachmentCore(docAttachmentProcessPara, request.getUuid());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        }
    }

    /**
     * Upload the attachment content information.
     */
    public String uploadAttachment(HttpServletRequest request, String resourceId, DocAttachmentProxy.DocAttachmentProcessPara docAttachmentProcessPara) {
        try {
            preCheckResourceAccessCore(
                    resourceId, ISystemActionCode.ACID_EDIT);
            docAttachmentProcessPara.setLogonUserUUID(logonActionController.getResUserUUID());
            docAttachmentProcessPara.setOrganizationUUID(logonActionController.getResOrgUUID());
            docAttachmentProcessPara.setRequest(request);
            return docAttachmentProxy.uploadDefAttachment(docAttachmentProcessPara);
        } catch (ServiceEntityConfigureException ex) {
            return ServiceJSONParser.generateSimpleErrorJSON(ex.getMessage());
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        }
    }

    /**
     * Upload the attachment text information.
     */
    public String uploadAttachmentText(
            FileAttachmentTextRequest request, String resourceId, DocAttachmentProxy.DocAttachmentProcessPara docAttachmentProcessPara) {
        try {
            preCheckResourceAccessCore(
                    resourceId, ISystemActionCode.ACID_EDIT);
            docAttachmentProcessPara.setLogonUserUUID(logonActionController.getResUserUUID());
            docAttachmentProcessPara.setOrganizationUUID(logonActionController.getResOrgUUID());
            return docAttachmentProxy.uploadAttachmentText(docAttachmentProcessPara, request);
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        }
    }

    public String getPageHeaderModelList(@RequestBody SimpleSEJSONRequest request, String resourceId,
                                         DocPageHeaderModelProxy.GenModelList genModelList) {
        try {
            if (!ServiceEntityStringHelper.checkNullString(resourceId)) {
                preCheckResourceAccessCore(resourceId, ISystemActionCode.ACID_VIEW);
            }
            checkLogonUser();
            List<PageHeaderModel> pageHeaderModelList = genModelList.execute(request, logonActionController.getClient());
            return ServiceJSONParser.genDefOKJSONArray(pageHeaderModelList);
        } catch (LogonInfoException | AuthorizationException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String defaultActionServiceWrapper(String request, String aoId, String acId,
                                              int documentType, int actionCode,
                                              String nodeInstId,
                                              ServiceEntityManager serviceEntityManager,
                                              DocActionNodeProxy.IActionCodeServiceWrapper actionCodeServiceWrapper,
                                              ServiceUIModelExtension serviceUIModelExtension) {
        try {
            ServiceModule serviceModule = actionCodeServiceWrapper.parseToServiceModule(request);
            ServiceEntityNode serviceEntityNode = ServiceModuleProxy.getCoreEntityNodeValue(serviceModule,
                    serviceUIModelExtension.genUIModelExtensionUnion().get(0));
            boolean accessFlag = checkTargetDataAccess(logonActionController.getLogonUser(), serviceEntityNode, aoId,
                    acId,
                    logonActionController.getLogonInfo().getAuthorizationACUnionList());
            if (!accessFlag) {
                throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
            }
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            Organization organization = logonActionController
                    .getOrganizationByUser(logonUser.getUuid());
            if (organization != null) {
                organizationUUID = organization.getUuid();
            }
            /*
             * [Step3]
             */
            serviceEntityManager.updateServiceModuleWithDelete(
                    serviceModule.getClass(),
                    serviceModule, logonUser.getUuid(), organizationUUID, nodeInstId,
                    serviceUIModelExtension);
            /*
             * [Step3] checking if current running task exist
             */
            boolean checkResult = actionCodeServiceWrapper.preExecute(serviceModule, actionCode);
            if (!checkResult) {
                return ServiceJSONParser.genSimpleOKResponse();
            }
            /*
             * [Step4]
             */
            actionCodeServiceWrapper.executeService(serviceModule, logonActionController.getLogonInfo());
            ServiceUIModule serviceUIModule = actionCodeServiceWrapper.refreshServiceUIModel(serviceModule,
                    acId, logonActionController.getLogonInfo());
            actionCodeServiceWrapper.postHandle(serviceUIModule, actionCode,
                    logonActionController.getSerialLogonInfo());
            return ServiceJSONParser
                    .genDefOKJSONObject(serviceUIModule);
        } catch (ServiceUIModuleProxyException | ServiceModuleProxyException | DocActionException |
                 ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (IllegalAccessException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    /**
     * Wrapper method to update service module as well as check authorization
     *
     * @param serviceModule
     * @param serviceEntityNode
     * @param serviceEntityManager
     * @param aoId
     * @param acId
     * @param nodeInstId
     * @param serviceUIModelExtension
     * @throws AuthorizationException
     * @throws LogonInfoException
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    private void updateServiceModuleWrapper(ServiceModule serviceModule, ServiceEntityNode serviceEntityNode,
                                            ServiceEntityManager serviceEntityManager, String aoId, String acId,
                                            String nodeInstId, ServiceUIModelExtension serviceUIModelExtension)
            throws AuthorizationException, LogonInfoException, ServiceEntityConfigureException,
            ServiceModuleProxyException {
        boolean accessFlag = checkTargetDataAccess(logonActionController.getLogonUser(), serviceEntityNode, aoId,
                acId,
                logonActionController.getLogonInfo().getAuthorizationACUnionList());
        if (!accessFlag) {
            throw new AuthorizationException(AuthorizationException.TYPE_NO_AUTHORIZATION);
        }
        checkLogonUser();
        /*
         * [Step2] Execute update process
         */
        serviceEntityManager.updateServiceModuleWithDelete(
                serviceModule.getClass(),
                serviceModule, logonActionController.getResUserUUID(), logonActionController.getResOrgUUID(), nodeInstId,
                serviceUIModelExtension);
    }

    public String getDocActionConfigureListCore(DocActionExecutionProxy docActionExecutionProxy) {
        try {
            checkLogonUser();
            List<DocActionConfigure> docActionConfigureList =
                    docActionExecutionProxy.getDocActionConfigureList(logonActionController.getResUserUUID());
            return ServiceJSONParser
                    .genDefOKJSONArray(docActionConfigureList);
        } catch (LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    private int getActionCodeCore(ServiceModule serviceModule) {
        int actionCode = 0;
        if (serviceModule.getServiceJSONRequest() != null && serviceModule.getServiceJSONRequest().getTargetActionCode() > 0) {
            actionCode = serviceModule.getServiceJSONRequest().getTargetActionCode();
        }
        return actionCode;
    }

    public interface ICreateServiceModule {

        ServiceModule execute()
                throws DocActionException, ServiceModuleProxyException, ServiceEntityConfigureException;

    }

    public interface ICreateServiceEntityNode<T extends ServiceEntityNode> {

        T execute(String baseUUID, String parentNodeName, String client)
                throws DocActionException, ServiceModuleProxyException, ServiceEntityConfigureException;

    }

    public interface IProcessServiceEntityNode<T extends ServiceEntityNode> {

        T execute(T serviceEntityNode)
                throws DocActionException, ServiceEntityConfigureException;

    }

    public interface IProcessServiceModule {

        ServiceModule execute(ServiceModule serviceModule)
                throws DocActionException, ServiceModuleProxyException, ServiceEntityConfigureException;

    }

    public interface IDeleteStrategy {

        int execute(ServiceEntityNode serviceEntityNode)
                throws DocActionException, ServiceEntityConfigureException;

    }

    public static class DeleteServiceEntityRequest {

        protected String uuid;

        protected String acId;

        protected ServiceEntityNode serviceEntityNode;

        protected DocumentContentSpecifier<?, ?, ?> documentContentSpecifier;

        protected int status;

        protected IDeleteStrategy deleteStrategy;

        public DeleteServiceEntityRequest() {
        }

        public DeleteServiceEntityRequest(String uuid, String acId, ServiceEntityNode serviceEntityNode, int status,
                                          IDeleteStrategy deleteStrategy) {
            this.uuid = uuid;
            this.acId = acId;
            this.serviceEntityNode = serviceEntityNode;
            this.status = status;
            this.deleteStrategy = deleteStrategy;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public ServiceEntityNode getServiceEntityNode() {
            return serviceEntityNode;
        }

        public void setServiceEntityNode(ServiceEntityNode serviceEntityNode) {
            this.serviceEntityNode = serviceEntityNode;
        }

        public IDeleteStrategy getDeleteStrategy() {
            return deleteStrategy;
        }

        public void setDeleteStrategy(IDeleteStrategy deleteStrategy) {
            this.deleteStrategy = deleteStrategy;
        }

        public String getAcId() {
            return acId;
        }

        public void setAcId(String acId) {
            this.acId = acId;
        }

        public DocumentContentSpecifier<?, ?, ?> getDocumentContentSpecifier() {
            return documentContentSpecifier;
        }

        public void setDocumentContentSpecifier(DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.documentContentSpecifier = documentContentSpecifier;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class InitServiceEntityRequestBuilder {

        protected String parentNodeName;

        protected String parentNodeUUID;

        protected String nodeInstId;

        protected String initConfigureId;

        protected String serviceEntityName;

        protected String nodeName;

        protected Object inputRequest;

        IProcessServiceEntityNode processServiceEntityNode;

        IProcessServiceModule processServiceModule;

        protected DocumentContentSpecifier<?, ?, ?> documentContentSpecifier;

        public static InitServiceEntityRequestBuilder getBuilder() {
            return new InitServiceEntityRequestBuilder();
        }

        public static InitServiceEntityRequestBuilder getBuilder(String serviceEntityName, String nodeName) {
            InitServiceEntityRequestBuilder initServiceEntityRequestBuilder = new InitServiceEntityRequestBuilder();
            initServiceEntityRequestBuilder.serviceEntityName(serviceEntityName);
            initServiceEntityRequestBuilder.nodeName(nodeName);
            return new InitServiceEntityRequestBuilder();
        }

        public static InitServiceEntityRequestBuilder getBuilder(String serviceEntityName, String nodeName, String parentNodeName, String parentNodeUUID) {
            InitServiceEntityRequestBuilder initServiceEntityRequestBuilder = new InitServiceEntityRequestBuilder();
            initServiceEntityRequestBuilder.serviceEntityName(serviceEntityName);
            initServiceEntityRequestBuilder.nodeName(nodeName);
            initServiceEntityRequestBuilder.parentNodeName(parentNodeName);
            initServiceEntityRequestBuilder.parentNodeUUID(parentNodeUUID);
            return new InitServiceEntityRequestBuilder();
        }

        public InitServiceEntityRequestBuilder parentNodeName(String parentNodeName) {
            this.parentNodeName = parentNodeName;
            return this;
        }

        public InitServiceEntityRequestBuilder parentNodeUUID(String parentNodeUUID) {
            this.parentNodeUUID = parentNodeUUID;
            return this;
        }

        public InitServiceEntityRequestBuilder nodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
            return this;
        }

        public InitServiceEntityRequestBuilder initConfigureId(String initConfigureId) {
            this.initConfigureId = initConfigureId;
            return this;
        }

        public InitServiceEntityRequestBuilder serviceEntityName(String serviceEntityName) {
            this.serviceEntityName = serviceEntityName;
            return this;
        }

        public InitServiceEntityRequestBuilder nodeName(String nodeName) {
            this.nodeName = nodeName;
            return this;
        }

        public InitServiceEntityRequestBuilder inputRequest(Object inputRequest) {
            this.inputRequest = inputRequest;
            return this;
        }

        public InitServiceEntityRequestBuilder processServiceEntityNode(IProcessServiceEntityNode processServiceEntityNode) {
            this.processServiceEntityNode = processServiceEntityNode;
            return this;
        }

        public InitServiceEntityRequestBuilder processServiceModule(IProcessServiceModule processServiceModule) {
            this.processServiceModule = processServiceModule;
            return this;
        }

        public String getParentNodeName() {
            return parentNodeName;
        }

        public void setParentNodeName(String parentNodeName) {
            this.parentNodeName = parentNodeName;
        }

        public String getParentNodeUUID() {
            return parentNodeUUID;
        }

        public void setParentNodeUUID(String parentNodeUUID) {
            this.parentNodeUUID = parentNodeUUID;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public String getInitConfigureId() {
            return initConfigureId;
        }

        public void setInitConfigureId(String initConfigureId) {
            this.initConfigureId = initConfigureId;
        }

        public String getServiceEntityName() {
            return serviceEntityName;
        }

        public void setServiceEntityName(String serviceEntityName) {
            this.serviceEntityName = serviceEntityName;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public Object getInputRequest() {
            return inputRequest;
        }

        public void setInputRequest(Object inputRequest) {
            this.inputRequest = inputRequest;
        }

        public IProcessServiceEntityNode getProcessServiceEntityNode() {
            return processServiceEntityNode;
        }

        public void setProcessServiceEntityNode(IProcessServiceEntityNode processServiceEntityNode) {
            this.processServiceEntityNode = processServiceEntityNode;
        }

        public IProcessServiceModule getProcessServiceModule() {
            return processServiceModule;
        }

        public void setProcessServiceModule(IProcessServiceModule processServiceModule) {
            this.processServiceModule = processServiceModule;
        }

        public DocumentContentSpecifier<?, ?, ?> getDocumentContentSpecifier() {
            return documentContentSpecifier;
        }

        public void setDocumentContentSpecifier(DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.documentContentSpecifier = documentContentSpecifier;
        }

        public InitServiceEntityRequest build() {
            InitServiceEntityRequest initServiceEntityRequest = new InitServiceEntityRequest();
            initServiceEntityRequest.setServiceEntityName(this.getServiceEntityName());
            initServiceEntityRequest.setNodeName(this.getNodeName());
            initServiceEntityRequest.setNodeInstId(this.getNodeInstId());
            initServiceEntityRequest.setParentNodeName(this.getParentNodeName());
            initServiceEntityRequest.setParentNodeName(this.getParentNodeUUID());
            initServiceEntityRequest.setInitConfigureId(this.getInitConfigureId());
            initServiceEntityRequest.setInputRequest(this.getInputRequest());
            initServiceEntityRequest.setProcessServiceEntityNode(this.getProcessServiceEntityNode());
            initServiceEntityRequest.setProcessServiceModule(this.getProcessServiceModule());
            initServiceEntityRequest.setDocumentContentSpecifier(this.getDocumentContentSpecifier());
            return initServiceEntityRequest;
        }
    }


    public static class InitServiceEntityRequest {

        protected String parentNodeName;

        protected String parentNodeUUID;

        protected String nodeInstId;

        protected String initConfigureId;

        protected String serviceEntityName;

        protected String nodeName;

        protected Object inputRequest;

        IProcessServiceEntityNode processServiceEntityNode;

        ICreateServiceEntityNode createServiceEntityNode;

        IProcessServiceModule processServiceModule;

        protected DocumentContentSpecifier<?, ?, ?> documentContentSpecifier;

        public InitServiceEntityRequest() {
        }


        public InitServiceEntityRequest(String serviceEntityName, String nodeName,
                                        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.documentContentSpecifier = documentContentSpecifier;
        }

        public InitServiceEntityRequest(String serviceEntityName, String nodeName, String parentNodeName,
                                        String parentNodeUUID,
                                        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.parentNodeName = parentNodeName;
            this.parentNodeUUID = parentNodeUUID;
            this.documentContentSpecifier = documentContentSpecifier;
        }

        public InitServiceEntityRequest(String serviceEntityName, String nodeName, String parentNodeName,
                                        String parentNodeUUID,
                                        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier, ICreateServiceEntityNode<?> createServiceEntityNode) {
            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.parentNodeName = parentNodeName;
            this.parentNodeUUID = parentNodeUUID;
            this.documentContentSpecifier = documentContentSpecifier;
            this.createServiceEntityNode = createServiceEntityNode;
        }

        public InitServiceEntityRequest(String serviceEntityName, String nodeName, String nodeInstId,
                                        String initConfigureId,
                                        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier, Object inputRequest) {
            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.nodeInstId = nodeInstId;
            this.initConfigureId = initConfigureId;
            this.documentContentSpecifier = documentContentSpecifier;
            this.inputRequest = inputRequest;
        }

        public InitServiceEntityRequest(String serviceEntityName, String nodeName, String nodeInstId,
                                        String initConfigureId,
                                        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier, Object inputRequest,
                                        IProcessServiceEntityNode processServiceEntityNode) {
            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.nodeInstId = nodeInstId;
            this.initConfigureId = initConfigureId;
            this.documentContentSpecifier = documentContentSpecifier;
            this.inputRequest = inputRequest;
            this.processServiceEntityNode = processServiceEntityNode;
        }


        public InitServiceEntityRequest(String serviceEntityName, String nodeName, String nodeInstId, String parentNodeName,
                                        String parentNodeUUID,
                                        String initConfigureId,
                                        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier, Object inputRequest,
                                        IProcessServiceEntityNode processServiceEntityNode) {
            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.nodeInstId = nodeInstId;
            this.parentNodeName = parentNodeName;
            this.parentNodeUUID = parentNodeUUID;
            this.initConfigureId = initConfigureId;
            this.documentContentSpecifier = documentContentSpecifier;
            this.inputRequest = inputRequest;
            this.processServiceEntityNode = processServiceEntityNode;
        }

        public InitServiceEntityRequest(
                String serviceEntityName, String nodeName, String nodeInstId, String parentNodeName,
                String parentNodeUUID, String initConfigureId,
                DocumentContentSpecifier<?, ?, ?> documentContentSpecifier, Object inputRequest,
                IProcessServiceEntityNode processServiceEntityNode,
                IProcessServiceModule processServiceModule) {
            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.nodeInstId = nodeInstId;
            this.parentNodeName = parentNodeName;
            this.parentNodeUUID = parentNodeUUID;
            this.initConfigureId = initConfigureId;
            this.documentContentSpecifier = documentContentSpecifier;
            this.inputRequest = inputRequest;
            this.processServiceEntityNode = processServiceEntityNode;
            this.processServiceModule = processServiceModule;
        }

        public String getServiceEntityName() {
            return serviceEntityName;
        }

        public void setServiceEntityName(String serviceEntityName) {
            this.serviceEntityName = serviceEntityName;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public IProcessServiceModule getProcessServiceModule() {
            return processServiceModule;
        }

        public void setProcessServiceModule(IProcessServiceModule processServiceModule) {
            this.processServiceModule = processServiceModule;
        }

        public String getParentNodeName() {
            return parentNodeName;
        }

        public void setParentNodeName(String parentNodeName) {
            this.parentNodeName = parentNodeName;
        }

        public String getParentNodeUUID() {
            return parentNodeUUID;
        }

        public void setParentNodeUUID(String parentNodeUUID) {
            this.parentNodeUUID = parentNodeUUID;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public String getInitConfigureId() {
            return initConfigureId;
        }

        public void setInitConfigureId(String initConfigureId) {
            this.initConfigureId = initConfigureId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public DocumentContentSpecifier<?, ?, ?> getDocumentContentSpecifier() {
            return documentContentSpecifier;
        }

        public void setDocumentContentSpecifier(DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.documentContentSpecifier = documentContentSpecifier;
        }

        public Object getInputRequest() {
            return inputRequest;
        }

        public void setInputRequest(Object inputRequest) {
            this.inputRequest = inputRequest;
        }

        public IProcessServiceEntityNode getProcessServiceEntityNode() {
            return processServiceEntityNode;
        }

        public void setProcessServiceEntityNode(IProcessServiceEntityNode processServiceEntityNode) {
            this.processServiceEntityNode = processServiceEntityNode;
        }

        public ICreateServiceEntityNode getCreateServiceEntityNode() {
            return createServiceEntityNode;
        }

        public void setCreateServiceEntityNode(ICreateServiceEntityNode createServiceEntityNode) {
            this.createServiceEntityNode = createServiceEntityNode;
        }
    }


    public static class InitDocMatItemRequest extends InitServiceEntityRequest {


        /**
         * Only used for doc mat item
         */
        protected Class<? extends ServiceModule> parentServiceModuleClass;

        public InitDocMatItemRequest(String serviceEntityName, String nodeName, String nodeInstId, String parentNodeName,
                                     String parentNodeUUID,
                                     String initConfigureId,
                                     Class<? extends ServiceModule> parentServiceModuleClass,
                                     DocumentContentSpecifier<?, ?, ?> documentContentSpecifier, Object inputRequest,
                                     IProcessServiceEntityNode processServiceEntityNode) {

            this.serviceEntityName = serviceEntityName;
            this.nodeName = nodeName;
            this.nodeInstId = nodeInstId;
            this.parentNodeName = parentNodeName;
            this.parentNodeUUID = parentNodeUUID;
            this.initConfigureId = initConfigureId;
            this.parentServiceModuleClass = parentServiceModuleClass;
            this.documentContentSpecifier = documentContentSpecifier;
            this.inputRequest = inputRequest;
            this.processServiceEntityNode = processServiceEntityNode;
        }

        public Class<? extends ServiceModule> getParentServiceModuleClass() {
            return parentServiceModuleClass;
        }

        public void setParentServiceModuleClass(Class<? extends ServiceModule> parentServiceModuleClass) {
            this.parentServiceModuleClass = parentServiceModuleClass;
        }

    }

    public static class ServiceUIModelRequest {

        Class<?> serviceUIModuleType;

        Class<?> serviceModuleType;

        String resourceId;

        String nodeName;

        String serviceEntityName;

        String nodeInstId;

        ServiceUIModelExtension serviceUIModelExtension;

        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier;

        ServiceEntityManager serviceEntityManager;

        List<ServiceModuleConvertPara> serviceModuleConvertParaList;

        public ServiceUIModelRequest(Class<?> serviceUIModuleType, Class<?> serviceModuleType, String resourceId,
                                     String nodeName, String serviceEntityName, ServiceUIModelExtension serviceUIModelExtension,
                                     ServiceEntityManager serviceEntityManager) {
            this.serviceUIModuleType = serviceUIModuleType;
            this.serviceModuleType = serviceModuleType;
            this.resourceId = resourceId;
            this.nodeName = nodeName;
            this.serviceEntityName = serviceEntityName;
            this.serviceUIModelExtension = serviceUIModelExtension;
            this.serviceEntityManager = serviceEntityManager;
        }

        public ServiceUIModelRequest(Class<?> serviceUIModuleType, Class<?> serviceModuleType, String resourceId,
                                     String nodeName, String serviceEntityName, DocumentContentSpecifier<?, ?, ?> documentContentSpecifier,
                                     ServiceEntityManager serviceEntityManager) {
            this.serviceUIModuleType = serviceUIModuleType;
            this.serviceModuleType = serviceModuleType;
            this.resourceId = resourceId;
            this.nodeName = nodeName;
            this.serviceEntityName = serviceEntityName;
            this.documentContentSpecifier = documentContentSpecifier;
            this.serviceEntityManager = serviceEntityManager;
        }

        public ServiceUIModelRequest(Class<?> serviceUIModuleType, Class<?> serviceModuleType, String resourceId, String nodeName, String serviceEntityName, ServiceUIModelExtension serviceUIModelExtension, ServiceEntityManager serviceEntityManager, List<ServiceModuleConvertPara> serviceModuleConvertParaList) {
            this.serviceUIModuleType = serviceUIModuleType;
            this.serviceModuleType = serviceModuleType;
            this.resourceId = resourceId;
            this.nodeName = nodeName;
            this.serviceEntityName = serviceEntityName;
            this.serviceUIModelExtension = serviceUIModelExtension;
            this.serviceEntityManager = serviceEntityManager;
            this.serviceModuleConvertParaList = serviceModuleConvertParaList;
        }

        public ServiceUIModelRequest(Class<?> serviceUIModuleType, Class<?> serviceModuleType, String resourceId,
                                     String nodeName, String serviceEntityName,
                                     String nodeInstId, ServiceUIModelExtension serviceUIModelExtension,
                                     ServiceEntityManager serviceEntityManager) {
            this.serviceUIModuleType = serviceUIModuleType;
            this.serviceModuleType = serviceModuleType;
            this.resourceId = resourceId;
            this.nodeName = nodeName;
            this.serviceEntityName = serviceEntityName;
            this.nodeInstId = nodeInstId;
            this.serviceUIModelExtension = serviceUIModelExtension;
            this.serviceEntityManager = serviceEntityManager;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getServiceEntityName() {
            return serviceEntityName;
        }

        public void setServiceEntityName(String serviceEntityName) {
            this.serviceEntityName = serviceEntityName;
        }

        public Class<?> getServiceUIModuleType() {
            return serviceUIModuleType;
        }

        public void setServiceUIModuleType(Class<?> serviceUIModuleType) {
            this.serviceUIModuleType = serviceUIModuleType;
        }

        public Class<?> getServiceModuleType() {
            return serviceModuleType;
        }

        public void setServiceModuleType(Class<?> serviceModuleType) {
            this.serviceModuleType = serviceModuleType;
        }

        public ServiceEntityManager getServiceEntityManager() {
            return serviceEntityManager;
        }

        public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
            this.serviceEntityManager = serviceEntityManager;
        }

        public ServiceUIModelExtension getServiceUIModelExtension() {
            return serviceUIModelExtension;
        }

        public void setServiceUIModelExtension(ServiceUIModelExtension serviceUIModelExtension) {
            this.serviceUIModelExtension = serviceUIModelExtension;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public List<ServiceModuleConvertPara> getServiceModuleConvertParaList() {
            return serviceModuleConvertParaList;
        }

        public void setServiceModuleConvertParaList(List<ServiceModuleConvertPara> serviceModuleConvertParaList) {
            this.serviceModuleConvertParaList = serviceModuleConvertParaList;
        }

        public DocumentContentSpecifier<?, ?, ?> getDocumentContentSpecifier() {
            return documentContentSpecifier;
        }

        public void setDocumentContentSpecifier(DocumentContentSpecifier<?, ?, ?> documentContentSpecifier) {
            this.documentContentSpecifier = documentContentSpecifier;
        }
    }

    public static class DocUIModelWithActionRequest extends ServiceUIModelRequest {

        private String actionNodeName;

        private DocActionExecutionProxy<?, ?, ?> docActionExecutionProxy;

        public DocUIModelWithActionRequest(Class<?> serviceUIModuleType, Class<?> serviceModuleType, String resourceId,
                                           String nodeName,
                                           String serviceEntityName, ServiceUIModelExtension serviceUIModelExtension,
                                           ServiceEntityManager serviceEntityManager, String actionNodeName,
                                           DocActionExecutionProxy<?, ?, ?> docActionExecutionProxy) {
            super(serviceUIModuleType, serviceModuleType, resourceId, nodeName, serviceEntityName,
                    serviceUIModelExtension,
                    serviceEntityManager);
            this.setNodeInstId(ServiceEntityStringHelper.getDefModelId(serviceEntityName, nodeName));
            this.actionNodeName = actionNodeName;
            this.docActionExecutionProxy = docActionExecutionProxy;
        }

        public DocUIModelWithActionRequest(Class<?> serviceUIModuleType, Class<?> serviceModuleType, String resourceId,
                                           String nodeName,
                                           String serviceEntityName,
                                           ServiceEntityManager serviceEntityManager, String actionNodeName,
                                           DocActionExecutionProxy<?, ?, ?> docActionExecutionProxy) {
            super(serviceUIModuleType, serviceModuleType, resourceId, nodeName, serviceEntityName,
                    docActionExecutionProxy.getDocumentContentSpecifier(),
                    serviceEntityManager);
            this.setNodeInstId(ServiceEntityStringHelper.getDefModelId(serviceEntityName, nodeName));
            this.actionNodeName = actionNodeName;
            this.docActionExecutionProxy = docActionExecutionProxy;
        }

        public String getActionNodeName() {
            return actionNodeName;
        }

        public void setActionNodeName(String actionNodeName) {
            this.actionNodeName = actionNodeName;
        }

        public DocActionExecutionProxy<?, ?, ?> getDocActionExecutionProxy() {
            return docActionExecutionProxy;
        }

        public void setDocActionExecutionProxy(DocActionExecutionProxy<?, ?, ?> docActionExecutionProxy) {
            this.docActionExecutionProxy = docActionExecutionProxy;
        }
    }

    public static class ExecuteActionRequest {

        private String request;

        private String aoId;

        private String acId;

        private String nodeInstId;

        private ServiceEntityManager serviceEntityManager;

        private ServiceUIModelExtension serviceUIModelExtension;

        public ExecuteActionRequest(String request, String aoId, String acId, String nodeInstId,
                                    ServiceEntityManager serviceEntityManager,
                                    ServiceUIModelExtension serviceUIModelExtension) {
            this.request = request;
            this.aoId = aoId;
            this.acId = acId;
            this.nodeInstId = nodeInstId;
            this.serviceEntityManager = serviceEntityManager;
            this.serviceUIModelExtension = serviceUIModelExtension;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getAoId() {
            return aoId;
        }

        public void setAoId(String aoId) {
            this.aoId = aoId;
        }

        public String getAcId() {
            return acId;
        }

        public void setAcId(String acId) {
            this.acId = acId;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

        public ServiceEntityManager getServiceEntityManager() {
            return serviceEntityManager;
        }

        public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
            this.serviceEntityManager = serviceEntityManager;
        }

        public ServiceUIModelExtension getServiceUIModelExtension() {
            return serviceUIModelExtension;
        }

        public void setServiceUIModelExtension(ServiceUIModelExtension serviceUIModelExtension) {
            this.serviceUIModelExtension = serviceUIModelExtension;
        }
    }

    public interface ICheckMessageExecutor<R extends ServiceModule, EX extends ServiceEntityException> {

        R parseToServiceModule(String request) throws ServiceModuleProxyException, ServiceEntityConfigureException,
                ServiceUIModuleProxyException;

        /**
         * Core Logic method to get message list by service module
         *
         * @param serviceModule
         * @return
         * @throws DocActionException
         * @throws EX
         * @throws ServiceModuleProxyException
         * @throws ServiceEntityConfigureException
         */
        List<SimpleSEMessageResponse> getMessage(R serviceModule) throws DocActionException, EX,
                ServiceModuleProxyException, ServiceEntityConfigureException;

        /**
         * When error message happens, should throw exception.
         *
         * @param errorMessageList
         * @throws EX
         */
        void throwExceptionCallback(List<SimpleSEMessageResponse> errorMessageList) throws EX;

    }


    /**
     * Framework method to do some pre-checking items jobs before setting status, will raise exception when [Error]
     * Type message get and return message list when [Warning]
     *
     * @param executeActionRequest
     * @param getMessageExecutor
     * @param <R>
     * @param <EX>
     * @return
     */
    public <R extends ServiceModule, EX extends ServiceEntityException> String defPreCheckSetStatus(ExecuteActionRequest executeActionRequest,
                                                                                                    ICheckMessageExecutor<R, EX> getMessageExecutor) throws ServiceEntityException {
        try {
            preCheckResourceAccessCore(
                    executeActionRequest.getAoId(), executeActionRequest.getAcId());
            String request = executeActionRequest.getRequest();
            R serviceModule = getMessageExecutor.parseToServiceModule(request);
            ServiceEntityManager serviceEntityManager = executeActionRequest.getServiceEntityManager();
            ServiceUIModelExtension serviceUIModelExtension = executeActionRequest.getServiceUIModelExtension();
            ServiceEntityNode serviceEntityNode = ServiceModuleProxy.getCoreEntityNodeValue(serviceModule);
            /*
             * [Step1] Parse request string to service model and update service module firstly
             */
            updateServiceModuleWrapper(serviceModule, serviceEntityNode, serviceEntityManager, executeActionRequest.getAoId(),
                    ISystemActionCode.ACID_EDIT,
                    executeActionRequest.getNodeInstId(), serviceUIModelExtension);
            List<SimpleSEMessageResponse> rawMessageList = null;
            rawMessageList = getMessageExecutor.getMessage(serviceModule);
            /*
             * [Step2] Try to get error type message firstly, and handle error type message firstly
             */
            List<SimpleSEMessageResponse> errorMessageList = ServiceMessageResponseHelper
                    .filerSEMessageResponseByLevel(
                            SimpleSEMessageResponse.MESSAGELEVEL_ERROR,
                            rawMessageList);
            if (!ServiceCollectionsHelper.checkNullList(errorMessageList)) {
                // Raise exception when error message happens
                getMessageExecutor.throwExceptionCallback(errorMessageList);
            }
            /*
             * [Step3] Try to get warning type message, and return message list
             */
            List<SimpleSEMessageResponse> warnMessageList = ServiceMessageResponseHelper
                    .filerSEMessageResponseByLevel(
                            SimpleSEMessageResponse.MESSAGELEVEL_WARN,
                            rawMessageList);
            if (!ServiceCollectionsHelper.checkNullList(warnMessageList)) {
                return ServiceJSONParser.genDefOKJSONArray(warnMessageList);
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (LogonInfoException | AuthorizationException | ServiceModuleProxyException |
                 ServiceUIModuleProxyException | DocActionException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String defaultActionServiceWrapper(String request, String aoId, DocActionExecutionProxy docActionExecutionProxy,
                                              String nodeInstId,
                                              ServiceEntityManager serviceEntityManager,
                                              DocActionNodeProxy.IActionCodeServiceExecutor actionCodeServiceExecutor,
                                              ServiceUIModelExtension serviceUIModelExtension) {
        try {
            ServiceModule serviceModule = actionCodeServiceExecutor.parseToServiceModule(request);
            ServiceEntityNode serviceEntityNode = ServiceModuleProxy.getCoreEntityNodeValue(serviceModule);
            int actionCode = getActionCodeCore(serviceModule);
            if (actionCode == 0) {
                // in case only update
                updateServiceModuleWrapper(serviceModule, serviceEntityNode, serviceEntityManager, aoId,
                        ISystemActionCode.ACID_EDIT,
                        nodeInstId, serviceUIModelExtension);
                ServiceUIModule serviceUIModule = actionCodeServiceExecutor.refreshServiceUIModel(serviceModule,
                        ISystemActionCode.ACID_EDIT, logonActionController.getLogonInfo());
                return ServiceJSONParser
                        .genDefOKJSONObject(serviceUIModule);
            }
            DocActionConfigure docActionConfigure = docActionExecutionProxy.getDocActionConfigureByCode(actionCode,
                    serviceEntityNode.getClient());
            if (docActionConfigure == null) {
                throw new DocActionException(DocActionException.PARA_MISS_CONFIG, actionCode);
            }
            updateServiceModuleWrapper(serviceModule, serviceEntityNode, serviceEntityManager, aoId,
                    docActionConfigure.getAuthorActionCode(),
                    nodeInstId, serviceUIModelExtension);
            /*
             * [Step3] checking if current running task exist
             */
            boolean checkResult = actionCodeServiceExecutor.preExecute(serviceModule, actionCode);
            if (!checkResult) {
                return ServiceJSONParser.genSimpleOKResponse();
            }
            /*
             * [Step4] execution action service
             */
            actionCodeServiceExecutor.executeService(serviceModule, actionCode, logonActionController.getLogonInfo());
            ServiceUIModule serviceUIModule = actionCodeServiceExecutor.refreshServiceUIModel(serviceModule,
                    docActionConfigure.getAuthorActionCode(), logonActionController.getLogonInfo());
            actionCodeServiceExecutor.postHandle(serviceUIModule, actionCode,
                    logonActionController.getSerialLogonInfo());
            return ServiceJSONParser
                    .genDefOKJSONObject(serviceUIModule);
        } catch (ServiceUIModuleProxyException | ServiceModuleProxyException | DocActionException e) {
            logger.error(e.getErrorMessage());
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public interface IGenNextDocBatchRequest {

        List<ServiceEntityNode> getMatItemListCallback(DocumentMatItemBatchGenRequest genRequest) throws SearchConfigureException, ServiceEntityConfigureException;

        ServiceEntityNode getDocCallback(DocumentMatItemBatchGenRequest genRequest) throws SearchConfigureException, ServiceEntityConfigureException;

    }

    public List<ServiceEntityNode> getDefMatItemListWrapper(DocumentMatItemBatchGenRequest genRequest,
                                                            int documentType) throws DocActionException {
        return docFlowProxy.getDefDocItemNodeList(documentType, genRequest.getUuidList(),
                this.logonActionController.getSerialLogonInfo().getClient());
    }


    /**
     * Identifies and retrieves the appropriate list of target document roots that can potentially contain the newly batch-created target document material item list.
     * This list is presented to the end-user for selection.
     *
     * @param request        JSON data sent from the UI, containing information about the batch creation request.
     * @param targetDocType  The type of the target (next) document.
     * @param GenRequestType The class type of `DocumentMatItemBatchGenRequest`, used for parsing the `request`
     *                       into a specific Java model.
     * @param aoId           The authorization object ID used to validate the user's permissions.
     * @return A JSON string representing the list of possible target document roots, or an error message
     * if an exception occurs during processing.
     */
    public String loadProperTargetDocListBatchGen(String request, int targetDocType, Class<? extends DocumentMatItemBatchGenRequest> GenRequestType,
                                                  String aoId) {
        try {
            preCheckResourceAccessCore(
                    aoId, ISystemActionCode.ACID_EDIT);
            DocumentMatItemBatchGenRequest genRequest =
                    ServiceBasicRequestHelper.parseToDocumentMatItemBatchGenRequest(request,
                            targetDocType, GenRequestType);
            DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy =
                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(genRequest.getSourceDocType());
            DocumentContentSpecifier<?, ?, ?> sourceDocSpecifier = sourceDocActionProxy.getDocumentContentSpecifier();
            List<ServiceEntityNode> rawSourceDocMatItemList = sourceDocSpecifier.getDocumentManager()
                    .getEntityNodeListByKey(genRequest.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                            sourceDocSpecifier.getMatItemNodeInstId(), null);
            DocActionExecutionProxy<?, ?, ?> targetDocActionProxy =
                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(genRequest.getTargetDocType());
            List<ServiceEntityNode> rawTargetDocList =
                    targetDocActionProxy.getExistTargetDocForCreationBatch(genRequest,
                            targetDocActionProxy.getDocumentContentSpecifier(),
                            rawSourceDocMatItemList, DocMatItemNode::getNextDocMatItemUUID, logonActionController.getLogonInfo());
            return ServiceJSONParser
                    .genDefOKJSONArray(rawTargetDocList);
        } catch (AuthorizationException | LogonInfoException | SearchConfigureException | DocActionException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String genDefNextDocBatchWrapper(
            String request, int documentType, String aoId,
            Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest) {
        try {
            CreateDocInput createDocInput = genDefNextDocBatchCore(request, documentType, aoId, GenRequestType,
                    genNextDocBatchRequest);
            DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy = createDocInput.getSourceDocActionProxy();
            sourceDocActionProxy.crossCreateDocumentBatch(createDocInput.getServiceModel(),
                    createDocInput.getSelectedMatItemList(), createDocInput.getGenRequest(), logonActionController.getLogonInfo());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    /**
     * Identifies and retrieves the appropriate list of target document roots that can potentially contain the newly batch-created target document material item list.
     * This list is presented to the end-user for selection.
     *
     * @param request        JSON data sent from the UI, containing information about the batch creation request.
     * @param sourceDocType  The type of the source document.
     * @param GenRequestType The class type of `DocumentMatItemBatchGenRequest`, used for parsing the `request`
     *                       into a specific Java model.
     * @param aoId           The authorization object ID used to validate the user's permissions.
     * @return A JSON string representing the list of possible target document roots, or an error message
     * if an exception occurs during processing.
     */
    public String loadProperTargetDocListBatchGenFromPrevDoc(String request, int sourceDocType, Class<? extends DocumentMatItemBatchGenRequest> GenRequestType,
                                                             String aoId) {
        try {
            preCheckResourceAccessCore(
                    aoId, ISystemActionCode.ACID_EDIT);
            DocumentMatItemBatchGenRequest genRequest =
                    ServiceBasicRequestHelper.parseToDocumentMatItemBatchGenRequest(request, sourceDocType,
                            GenRequestType);
            DocActionExecutionProxy<?, ?, ?> prevProfDocActionProxy =
                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(sourceDocType);
            DocumentContentSpecifier<?, ?, ?> prevProfDocSpecifier = prevProfDocActionProxy.getDocumentContentSpecifier();
            List<ServiceEntityNode> prevDocMatItemList = prevProfDocSpecifier.getDocumentManager()
                    .getEntityNodeListByKey(genRequest.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                            prevProfDocSpecifier.getMatItemNodeInstId(), null);

            DocActionExecutionProxy<?, ?, ?> targetDocActionProxy =
                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(genRequest.getTargetDocType());
            List<ServiceEntityNode> rawTargetDocList =
                    targetDocActionProxy.getExistTargetDocForCreationBatch(genRequest,
                            targetDocActionProxy.getDocumentContentSpecifier(),
                            prevDocMatItemList, DocMatItemNode::getNextProfDocMatItemUUID, logonActionController.getLogonInfo());
            return ServiceJSONParser
                    .genDefOKJSONArray(rawTargetDocList);
        } catch (AuthorizationException | LogonInfoException | SearchConfigureException | DocActionException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    /**
     * Identifies and retrieves the appropriate list of target document roots that can potentially contain the newly batch-created target document material item list.
     * This list is presented to the end-user for selection.
     *
     * @param request        JSON data sent from the UI, containing information about the batch creation request.
     * @param targetDocType  The type of the target (next) document.
     * @param GenRequestType The class type of `DocumentMatItemBatchGenRequest`, used for parsing the `request`
     *                       into a specific Java model.
     * @param aoId           The authorization object ID used to validate the user's permissions.
     * @return A JSON string representing the list of possible target document roots, or an error message
     * if an exception occurs during processing.
     */
    public String loadProperTargetDocListBatchGenReserved(String request, int targetDocType, Class<? extends DocumentMatItemBatchGenRequest> GenRequestType,
                                                          String aoId) {
        try {
            preCheckResourceAccessCore(
                    aoId, ISystemActionCode.ACID_EDIT);
            DocumentMatItemBatchGenRequest genRequest =
                    ServiceBasicRequestHelper.parseToDocumentMatItemBatchGenRequest(request, targetDocType,
                            GenRequestType);
            DocActionExecutionProxy<?, ?, ?> reservedDocActionProxy =
                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(genRequest.getSourceDocType());
            DocumentContentSpecifier<?, ?, ?> reservedDocSpecifier = reservedDocActionProxy.getDocumentContentSpecifier();
            List<ServiceEntityNode> reservedDocMatItemList = reservedDocSpecifier.getDocumentManager()
                    .getEntityNodeListByKey(genRequest.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                            reservedDocSpecifier.getMatItemNodeInstId(), null);
            DocActionExecutionProxy<?, ?, ?> targetDocActionProxy =
                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(genRequest.getTargetDocType());
            List<ServiceEntityNode> rawTargetDocList =
                    targetDocActionProxy.getExistTargetDocForCreationBatch(genRequest,
                            targetDocActionProxy.getDocumentContentSpecifier(),
                            reservedDocMatItemList, DocMatItemNode::getPrevDocMatItemUUID, logonActionController.getLogonInfo());
            return ServiceJSONParser
                    .genDefOKJSONArray(rawTargetDocList);
        } catch (AuthorizationException | LogonInfoException | SearchConfigureException | DocActionException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String genDefNextDocBatchReserved(
            String request, int documentType, String aoId,
            Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest) {
        try {
            CreateDocInput createDocInput = genDefNextDocBatchCore(request, documentType, aoId, GenRequestType,
                    genNextDocBatchRequest);
            DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy = createDocInput.getSourceDocActionProxy();
            sourceDocActionProxy.crossCreateBatchDocReserved(createDocInput.getServiceModel(),
                    createDocInput.getSelectedMatItemList(), createDocInput.getGenRequest(), logonActionController.getLogonInfo());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public String genDefNextDocBatchFromPrevProf(
            String request, int documentType, String aoId,
            Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest) {
        try {
            CreateDocInput createDocInput = genDefNextDocBatchCore(request, documentType, aoId, GenRequestType,
                    genNextDocBatchRequest);
            DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy = createDocInput.getSourceDocActionProxy();
            sourceDocActionProxy.crossCreateBatchDocFromPrevProf(createDocInput.getServiceModel(),
                    createDocInput.getSelectedMatItemList(), createDocInput.getGenRequest(), logonActionController.getLogonInfo());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public String genDefNextDocBatchToPrevProf(
            String request, int documentType, String aoId,
            Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest) {
        try {
            CreateDocInput createDocInput = genDefNextDocBatchCore(request, documentType, aoId, GenRequestType,
                    genNextDocBatchRequest);
            DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy = createDocInput.getSourceDocActionProxy();
            sourceDocActionProxy.crossCreateBatchDocToPrevProf(createDocInput.getServiceModel(),
                    createDocInput.getSelectedMatItemList(), createDocInput.getGenRequest(), logonActionController.getLogonInfo());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    private CreateDocInput genDefNextDocBatchCore(String request, int documentType, String aoId,
                                                  Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest)
            throws AuthorizationException, ServiceEntityConfigureException, LogonInfoException,
            SearchConfigureException, DocActionException, ServiceModuleProxyException {
        DocumentMatItemBatchGenRequest genRequest =
                ServiceBasicRequestHelper.parseToDocumentMatItemBatchGenRequest(request, documentType,
                        GenRequestType);
        preCheckResourceAccessCore(
                aoId, ISystemActionCode.ACID_EDIT);
        DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(genRequest.getSourceDocType());
        DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
                sourceDocActionProxy.getDocumentContentSpecifier();
        ServiceEntityManager sourceEntityManager = documentContentSpecifier.getDocumentManager();
        List<ServiceEntityNode> sourceSelectedMatItemList;
        if (genNextDocBatchRequest != null) {
            sourceSelectedMatItemList =
                    genNextDocBatchRequest.getMatItemListCallback(genRequest);
        } else {
            sourceSelectedMatItemList = getDefMatItemListWrapper(genRequest, genRequest.getSourceDocType());
        }
        if (!ServiceEntityStringHelper.checkNullString(genRequest
                .getBaseUUID())) {
            ServiceEntityNode sourceEntity;
            if (genNextDocBatchRequest != null) {
                sourceEntity = genNextDocBatchRequest.getDocCallback(genRequest);
            } else {
                sourceEntity = sourceEntityManager.getEntityNodeByUUID(genRequest
                        .getBaseUUID(), documentContentSpecifier.getDocNodeName(), logonActionController.getClient());
            }
            ServiceModule sourceServiceModel = documentContentSpecifier.loadServiceModule(sourceEntity.getUuid(),
                    sourceEntity.getClient());
            return new CreateDocInput(sourceServiceModel, sourceSelectedMatItemList, genRequest,
                    sourceDocActionProxy);
        } else {
            // In case don't specify source root document
            return new CreateDocInput(null, sourceSelectedMatItemList, genRequest,
                    sourceDocActionProxy);
        }
    }

    public String batchExecItemHomeAction(
            String request, int documentType, String aoId,
            Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest) {
        try {
            CreateDocInput createDocInput = genDefNextDocBatchCore(request, documentType, aoId, GenRequestType,
                    genNextDocBatchRequest);
            DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy = createDocInput.getSourceDocActionProxy();
            int actionCode = createDocInput.getGenRequest().getActionCode();
            sourceDocActionProxy.batchExecItemHomeAction(createDocInput.getServiceModel(), createDocInput.getSelectedMatItemList(),
                    actionCode, logonActionController.getSerialLogonInfo());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public interface IGetSecondaryActionCode {

        int execute(int docActionCode, ServiceModule serviceModule) throws DocActionException, ServiceEntityConfigureException;

    }

    public String batchExecItemExclusiveHomeAction(
            String request, String aoId, DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy, IGetSecondaryActionCode getSecondaryActionCode,
            Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest) {
        try {
            CreateDocInput createDocInput = genDefNextDocBatchCore(request, 0, aoId, GenRequestType,
                    genNextDocBatchRequest);
            int actionCode = createDocInput.getGenRequest().getActionCode();
            if (getSecondaryActionCode == null) {
                throw new DocActionException(DocActionException.SYSTEM_ERROR);
            }
            int secondaryActionCode = getSecondaryActionCode.execute(actionCode, createDocInput.getServiceModel());
            if (secondaryActionCode == 0) {
                throw new DocActionException(DocActionException.SYSTEM_ERROR);
            }
            sourceDocActionProxy.execItemExclusiveHomeAction(createDocInput.getServiceModel(), createDocInput.getSelectedMatItemList(),
                    actionCode, secondaryActionCode, logonActionController.getSerialLogonInfo());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | ServiceModuleProxyException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public static class CreateDocInput {

        private ServiceModule serviceModel;

        private List<ServiceEntityNode> selectedMatItemList;

        private DocumentMatItemBatchGenRequest genRequest;

        private DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy;

        public CreateDocInput(ServiceModule serviceModel, List<ServiceEntityNode> selectedMatItemList,
                              DocumentMatItemBatchGenRequest genRequest, DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy) {
            this.serviceModel = serviceModel;
            this.selectedMatItemList = selectedMatItemList;
            this.genRequest = genRequest;
            this.sourceDocActionProxy = sourceDocActionProxy;
        }

        public ServiceModule getServiceModel() {
            return serviceModel;
        }

        public void setServiceModel(ServiceModule serviceModel) {
            this.serviceModel = serviceModel;
        }

        public List<ServiceEntityNode> getSelectedMatItemList() {
            return selectedMatItemList;
        }

        public void setSelectedMatItemList(List<ServiceEntityNode> selectedMatItemList) {
            this.selectedMatItemList = selectedMatItemList;
        }

        public DocumentMatItemBatchGenRequest getGenRequest() {
            return genRequest;
        }

        public void setGenRequest(DocumentMatItemBatchGenRequest genRequest) {
            this.genRequest = genRequest;
        }

        public DocActionExecutionProxy<?, ?, ?> getSourceDocActionProxy() {
            return sourceDocActionProxy;
        }

        public void setSourceDocActionProxy(DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy) {
            this.sourceDocActionProxy = sourceDocActionProxy;
        }
    }

    public String mergeDocBatchWrapper(
            String request, int documentType, String aoId,
            Class<? extends DocumentMatItemBatchGenRequest> GenRequestType, IGenNextDocBatchRequest genNextDocBatchRequest) {
        DocumentMatItemBatchGenRequest genRequest =
                ServiceBasicRequestHelper.parseToDocumentMatItemBatchGenRequest(request, documentType,
                        GenRequestType);
        try {
            preCheckResourceAccessCore(
                    aoId, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_NO_LOGON_USER);
            }
            DocActionExecutionProxy sourceDocActionProxy =
                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(documentType);
            DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
                    sourceDocActionProxy.getDocumentContentSpecifier();
            ServiceEntityManager sourceEntityManager = documentContentSpecifier.getDocumentManager();
            List<ServiceEntityNode> sourceSelectedMatItemList;
            if (genNextDocBatchRequest != null) {
                sourceSelectedMatItemList =
                        genNextDocBatchRequest.getMatItemListCallback(genRequest);
            } else {
                sourceSelectedMatItemList = getDefMatItemListWrapper(genRequest, documentType);
            }
            ServiceEntityNode sourceEntity;
            if (genNextDocBatchRequest != null) {
                sourceEntity = genNextDocBatchRequest.getDocCallback(genRequest);
            } else {
                sourceEntity = sourceEntityManager.getEntityNodeByUUID(genRequest
                        .getBaseUUID(), documentContentSpecifier.getDocNodeName(), logonUser.getClient());
            }
            sourceDocActionProxy.mergeDocBatch(sourceEntity, documentType, sourceSelectedMatItemList,
                    logonUser.getUuid(), logonActionController.getLogonInfo().getResOrgUUID());
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (SearchConfigureException | DocActionException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e
                    .getErrorMessage());
        }
    }

    public List<ServiceBasicPerformHelper.RecordPointTime> initRecordPointTime(String name) {
        this.recordPointTimeList = new ArrayList<>();
        this.recordPointTimeList = serviceBasicPerformHelper.recordPointTime(name, this.recordPointTimeList);
        return this.recordPointTimeList;
    }

    public List<ServiceBasicPerformHelper.RecordPointTime> recordPointTime(String name) {
        this.recordPointTimeList = serviceBasicPerformHelper.recordPointTime(name, this.recordPointTimeList);
        return this.recordPointTimeList;
    }

    public void printRecordTimes() {
        serviceBasicPerformHelper.printRecordTimes(this.recordPointTimeList);
    }

}
