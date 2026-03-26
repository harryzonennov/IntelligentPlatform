package com.company.IntelligentPlatform.common.service;

import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.service.HostCompanySearchProxy;
import com.company.IntelligentPlatform.common.dto.HostCompanyUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.HostCompanyRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.HostCompanyConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [HostCompany]
 *
 * @author
 * @date Thu Nov 21 14:30:21 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class HostCompanyManager extends ServiceEntityManager {

    public static final String METHOD_ConvHostCompanyToUI = "convHostCompanyToUI";

    public static final String METHOD_ConvUIToHostCompany = "convUIToHostCompany";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected HostCompanyRepository hostCompanyDAO;
    @Autowired
    HostCompanyConfigureProxy hostCompanyConfigureProxy;

    @Autowired
    HostCompanySearchProxy hostCompanySearchProxy;

    public HostCompanyManager() {
        super.seConfigureProxy = new HostCompanyConfigureProxy();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, hostCompanyDAO));
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(hostCompanyConfigureProxy);
    }

    public HostCompany getCurHostCompany(String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> rawList = this.getEntityNodeListByKey(null,
                null, HostCompany.NODENAME, client, null);
        if (ServiceCollectionsHelper.checkNullList(rawList)) {
            return null;
        }
        return (HostCompany) rawList.get(0);
    }

    public void convHostCompanyToUI(HostCompany hostCompany,
                                    HostCompanyUIModel hostCompanyUIModel) {
        if (hostCompany != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(hostCompany, hostCompanyUIModel);
            hostCompanyUIModel.setAddress(hostCompany.getAddress());
            hostCompanyUIModel.setTelephone(hostCompany
                    .getTelephone());
            hostCompanyUIModel.setFax(hostCompany.getFax());
            hostCompanyUIModel.setWebPage(hostCompany.getWebPage());
            hostCompanyUIModel.setEmail(hostCompany.getEmail());
            hostCompanyUIModel.setFullName(hostCompany.getFullName());
            hostCompanyUIModel.setComReportTitle(hostCompany
                    .getComReportTitle());
            hostCompanyUIModel.setTags(hostCompany
                    .getTags());
            hostCompanyUIModel.setTaxNumber(hostCompany.getTaxNumber());
            hostCompanyUIModel.setBankAccount(hostCompany.getBankAccount());
            hostCompanyUIModel.setDepositBank(hostCompany.getDepositBank());
        }
    }

    public void convUIToHostCompany(HostCompanyUIModel hostCompanyUIModel,
                                    HostCompany rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(hostCompanyUIModel, rawEntity);
        rawEntity.setNote(hostCompanyUIModel.getNote());
        rawEntity.setAddress(hostCompanyUIModel.getAddress());
        rawEntity.setTelephone(hostCompanyUIModel.getTelephone());
        rawEntity.setFax(hostCompanyUIModel.getFax());
        rawEntity.setWebPage(hostCompanyUIModel.getWebPage());
        rawEntity.setEmail(hostCompanyUIModel.getEmail());
        rawEntity.setFullName(hostCompanyUIModel.getFullName());
        rawEntity.setComReportTitle(hostCompanyUIModel.getComReportTitle());
        rawEntity.setTags(hostCompanyUIModel.getTags());
        rawEntity.setTaxNumber(hostCompanyUIModel.getTaxNumber());
        rawEntity.setBankAccount(hostCompanyUIModel.getBankAccount());
        rawEntity.setDepositBank(hostCompanyUIModel.getDepositBank());
    }

    @Override
    public ServiceSearchProxy getSearchProxy() {
        return hostCompanySearchProxy;
    }
}
