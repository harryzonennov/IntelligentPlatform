package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.SystemCodeValueUnionRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;

import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.SystemCodeValueCollection;
import com.company.IntelligentPlatform.common.model.SystemCodeValueUnion;
import com.company.IntelligentPlatform.common.model.SystemCodeValueUnionConfigureProxy;

import java.util.List;

/**
 * Logic Manager CLASS FOR Service Entity [SystemCodeValueUnion]
 *
 * @author
 * @date Tue Nov 20 08:56:19 CST 2018
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class SystemCodeValueUnionManager extends ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected SystemCodeValueUnionRepository systemCodeValueUnionDAO;
	@Autowired
	SystemCodeValueUnionConfigureProxy systemCodeValueUnionConfigureProxy;

	@Autowired
	protected SystemCodeValueCollectionManager systemCodeValueCollectionManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), SystemCodeValueCollection.NODENAME,
						request.getUuid(), SystemCodeValueUnion.NODENAME, systemCodeValueCollectionManager);
		docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<SystemCodeValueCollection>) systemCodeValueCollection -> {
            // How to get the base page header model list
            return
                    docPageHeaderModelProxy.getDocPageHeaderModelList(systemCodeValueCollection,  null);
        });
		docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<SystemCodeValueUnion>) (systemCodeValueUnion, pageHeaderModel) -> {
            // How to render current page header
            pageHeaderModel.setHeaderName(systemCodeValueUnion.getId());
            return pageHeaderModel;
        });
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public SystemCodeValueUnionManager() {
		super.seConfigureProxy = new SystemCodeValueUnionConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, systemCodeValueUnionDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(systemCodeValueUnionConfigureProxy);
	}
}
