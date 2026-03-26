package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SearchFieldConfig;
import com.company.IntelligentPlatform.common.model.SearchProxyConfig;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.*;

@Service
public class ServiceProxyConfigInitRegService {

    @Autowired
    protected ServiceSearchProxyRepository serviceSearchProxyRepository;

    @Autowired
    protected SearchProxyConfigManager searchProxyConfigManager;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    protected Logger logger = LoggerFactory.getLogger(ServiceProxyConfigInitRegService.class);

    public void execute(LogonInfo logonInfo) throws SearchProxyConfigureException {
        List<ServiceSearchProxy> allSearchProxyList = serviceSearchProxyRepository.getAllSearchProxyList();
        try {
            Map<String, String> serviceProxyConfigMap = serviceSearchProxyRepository.loadServiceProxyMap(logonInfo.getLanguageCode());
            Map<String, Map<String, String>> allSearchModelNameMap = serviceSearchProxyRepository.loadServiceModelMap(ServiceLanHelper.getDefLanList());
            List<String> activeServiceProxyList = activeServiceProxyList();
            for (ServiceSearchProxy serviceSearchProxy : allSearchProxyList) {
                /*
                 * [Step1] Filtering logic if neccessary
                 */
                String identifier =
                        ServiceEntityStringHelper.headerToLowerCase(serviceSearchProxy.getClass().getSimpleName());
                if (!ServiceCollectionsHelper.checkNullList(activeServiceProxyList)) {
                    // In case need to active filter
                    List<String> tempList =
                            ServiceCollectionsHelper.filterListOnline(activeServiceProxyList, activeServiceProxy -> {
                                return (activeServiceProxy.equals(identifier));
                            }, true);
                    if (ServiceCollectionsHelper.checkNullList(tempList)) {
                        continue;
                    }
                }
                try {
                    /*
                     * [Step2] New Search proxy configure
                     */
                    SearchProxyConfig searchProxyConfig =
                            (SearchProxyConfig) searchProxyConfigManager.newRootEntityNode(logonInfo.getClient());
                    String searchProxyName = serviceProxyConfigMap.getOrDefault(identifier, identifier);
                    searchProxyConfig.setSearchProxyName(searchProxyName);
                    // Initialize model name json
                    List<SearchProxyConfigManager.SearchModelNameMap> searchModelNameMapList =
                            searchProxyConfigManager.initSearchModelNameMap(searchProxyConfig, allSearchModelNameMap);
                    String modelNameJson = searchProxyConfigManager.parseArray(searchModelNameMapList);
                    searchProxyConfig.setModelNameJson(modelNameJson);

                    /*
                     * [Step3] Initialize search field list
                     */
                    List<ServiceEntityNode> searchFieldConfigList = new ArrayList<>();
                    List<Class<?>> allSearchModelList = searchProxyConfigManager.getRawSearchModelList(searchProxyConfig);
                    List<SearchProxyConfigManager.SearchFieldUnion> searchFieldUnionList = searchProxyConfigManager
                            .getRawSearchFieldList(searchProxyConfig, null);
                    if(!ServiceCollectionsHelper.checkNullList(searchFieldUnionList)){
                        for(SearchProxyConfigManager.SearchFieldUnion searchFieldUnion: searchFieldUnionList){
                            SearchFieldConfig searchFieldConfig =
                                    searchProxyConfigManager.newSearchFieldConfig(searchProxyConfig,
                                            searchFieldUnion.getSearchField());
                            searchFieldConfigList.add(searchFieldConfig);
                        }
                    }
                    SearchProxyConfigServiceModel searchProxyConfigServiceModel = new SearchProxyConfigServiceModel();
                    searchProxyConfigServiceModel.setSearchProxyConfig(searchProxyConfig);
                    searchProxyConfigServiceModel.setSearchFieldConfigList(searchFieldConfigList);
                    /*
                     * [Step4] Initialize search field list
                     */
                    searchProxyConfigManager.updateServiceModuleWithDelete(SearchProxyConfigServiceModel.class,
                            searchProxyConfigServiceModel, logonInfo.getRefUserUUID(), logonInfo.getResOrgUUID());
                } catch (ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, identifier));
                } catch (ServiceModuleProxyException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, identifier));
                }
            }
        } catch (ServiceEntityInstallationException e) {
            logger.error("Failed to initialize service proxy config registration", e);
        }
    }

    /**
     * Filter logic to return only part of service proxy
     *
     * @return
     */
    private List<String> activeServiceProxyList() {
        List<String> resultList = new ArrayList<>();
        resultList.add("salesContractSearchProxy");
        resultList.add("salesForcastSearchProxy");
        resultList.add("salesReturnOrderSearchProxy");
        resultList.add("qualityInspectOrderSearchProxy");
        resultList.add("purchaseContractSearchProxy");
        return resultList;
    }
}
