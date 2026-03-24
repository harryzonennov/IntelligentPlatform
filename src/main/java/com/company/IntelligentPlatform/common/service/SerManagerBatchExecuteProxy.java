package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Proxy for batch execute for multiple manager
 * 
 * @author Zhang,Hang
 *
 */
@Service
public class SerManagerBatchExecuteProxy {

	public static final int CATEGORY_MAN_SYSCONFIG = 1;

	public static final int CATEGORY_MAN_BUSMASTER = 2;

	public static final int CATEGORY_MAN_TRANSAC = 3;
	
	public static final String METHOD_admDeleteEntityByKey = "admDeleteEntityByKey";
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	/**
	 * Constant method: Name list for [Transaction] Category Manager
	 * 
	 * @param categoryManager
	 * @return
	 */
	public static List<String> getTransactionSerManagerName() {
		List<String> result = new ArrayList<String>();

		// Sales area
		result.add(ServiceEntityManagerFactoryInContext.bidInvitationOrderManager);
		result.add(ServiceEntityManagerFactoryInContext.salesContractManager);

		// SCM area
		result.add(ServiceEntityManagerFactoryInContext.purchaseContractManager);
		result.add(ServiceEntityManagerFactoryInContext.purchaseSettleManager);
		result.add(ServiceEntityManagerFactoryInContext.inboundDeliveryManager);
		result.add(ServiceEntityManagerFactoryInContext.outboundDeliveryManager);
		result.add(ServiceEntityManagerFactoryInContext.inquiryManager);
		result.add(ServiceEntityManagerFactoryInContext.inventoryCheckOrderManager);
		result.add(ServiceEntityManagerFactoryInContext.inventoryTransferOrderManager);
		result.add(ServiceEntityManagerFactoryInContext.qualityInspectOrderManager);

		// Finance area
		result.add(ServiceEntityManagerFactoryInContext.finAccountManager);

		// Production area
//		result.add(ServiceEntityManagerFactoryInContext.productionOrderManager);
//		result.add(ServiceEntityManagerFactoryInContext.productionPlanManager);
//		result.add(ServiceEntityManagerFactoryInContext.prodPickingOrderManager);

		return result;
	}

	/**
	 * Constant method: Name list for [Business Master Data] Category Manager
	 * 
	 * @param categoryManager
	 * @return
	 */
	public static List<String> getBusinessMasterSerManagerName() {
		List<String> result = new ArrayList<String>();
		// Sales area
		result.add(ServiceEntityManagerFactoryInContext.salesAreaManager);
		// SCM area
		// result.add(ServiceEntityManagerFactoryInContext.warehouseManager);
		// Finance area
		// result.add(ServiceEntityManagerFactoryInContext.finAccountTitleManager);
		// Production area
		// result.add(ServiceEntityManagerFactoryInContext.billOfMaterialOrderManager);
		// result.add(ServiceEntityManagerFactoryInContext.prodWorkCenterManager);
		result.add(ServiceEntityManagerFactoryInContext.productionResourceUnionManager);
		// Platform area
		result.add(ServiceEntityManagerFactoryInContext.corporateCustomerManager);
		// result.add(ServiceEntityManagerFactoryInContext.corporateSupplierManager);
		result.add(ServiceEntityManagerFactoryInContext.individualCustomerManager);
		// result.add(ServiceEntityManagerFactoryInContext.employeeManager);
		// result.add(ServiceEntityManagerFactoryInContext.organizationManager);

		result.add(ServiceEntityManagerFactoryInContext.registeredProductManager);
//		result.add(ServiceEntityManagerFactoryInContext.materialStockKeepUnitManager);
//		result.add(ServiceEntityManagerFactoryInContext.materialManager);
//		result.add(ServiceEntityManagerFactoryInContext.materialTypeManager);
		return result;
	}

	/**
	 * Constant method: Name list for [System Configure] Category Manager
	 * 
	 * @param categoryManager
	 * @return
	 */
	public static List<String> getSystemConfigureManagerName() {
		List<String> result = new ArrayList<String>();
		// Production area
		result.add(ServiceEntityManagerFactoryInContext.billOfMaterialOrderManager);
		result.add(ServiceEntityManagerFactoryInContext.prodWorkCenterManager);
		result.add(ServiceEntityManagerFactoryInContext.productionResourceUnionManager);
		// Platform area
		result.add(ServiceEntityManagerFactoryInContext.logonUserManager);
		result.add(ServiceEntityManagerFactoryInContext.corporateSupplierManager);
		result.add(ServiceEntityManagerFactoryInContext.individualCustomerManager);
		result.add(ServiceEntityManagerFactoryInContext.employeeManager);
		result.add(ServiceEntityManagerFactoryInContext.organizationManager);
		return result;
	}

	public List<String> getSerManagerNameList(int categoryManager) {
		if (categoryManager == CATEGORY_MAN_TRANSAC) {
			return getTransactionSerManagerName();
		}
		if (categoryManager == CATEGORY_MAN_BUSMASTER) {
			return getBusinessMasterSerManagerName();
		}
		if (categoryManager == CATEGORY_MAN_SYSCONFIG) {
			return getSystemConfigureManagerName();
		}
		return null;
	}

	private List<ServiceEntityManager> getExcuteableSerManagerList(List<String> managerNameList) {
		if(ServiceCollectionsHelper.checkNullList(managerNameList)){
			return null;
		}
		List<ServiceEntityManager> result = new ArrayList<>();
		for(String managerName:managerNameList){
			ServiceEntityManager seManagerInstance = serviceEntityManagerFactoryInContext.getManagerByManagerName(managerName);
			if(seManagerInstance == null){
				continue;
			}
			result.add(seManagerInstance);			
		}
		return result;
	}

	public void executeBatch(int category, String methodName, String client, String logonUserUUID, String organizationUUID){
		List<String> managerNameList = getSerManagerNameList(category);
		List<ServiceEntityManager> seManagerList = getExcuteableSerManagerList(managerNameList);
		executeBatchCore(seManagerList, methodName, client);
	}
	
	private void executeBatchCore(List<ServiceEntityManager> seManagerList, String methodName, String client){
		if(ServiceCollectionsHelper.checkNullList(seManagerList)){
			return;
		}
		if(METHOD_admDeleteEntityByKey.equals(methodName)){
			for(ServiceEntityManager seManagerInstance:seManagerList){
				System.out.println("admDeleteEntityByKey:" + seManagerInstance.getClass().getSimpleName());
				try {
					seManagerInstance.admDeleteEntityByKey(client, IServiceEntityNodeFieldConstant.CLIENT, ServiceEntityNode.NODENAME_ROOT);
				} catch (ServiceEntityConfigureException e) {
					// continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, seManagerInstance.getClass().getSimpleName()));
				}
			}
		}
	}

}
