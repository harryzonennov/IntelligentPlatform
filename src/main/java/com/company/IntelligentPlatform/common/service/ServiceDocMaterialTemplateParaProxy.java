package com.company.IntelligentPlatform.common.service;

import java.util.List;

import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

public abstract class ServiceDocMaterialTemplateParaProxy extends ServiceDocConfigParaProxy{
	
	/**
	 * Get the doc material ref node list by input raw doc/service entity list
	 * and filter material SKU UUID
	 * 
	 * @param docList
	 * @param materialSKUUUID
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	public abstract List<ServiceEntityNode> getDocMaterialSKUItemList(
			List<ServiceEntityNode> docList, String materialSKUUUID)
			throws ServiceDocConfigureException;

	/**
	 * Helper method to return material amount by import raw doc/service entity list & filter
	 * material SKU UUID
	 * 
	 * @param consumerModel
	 * @param docList
	 * @param inputParaUnionList
	 * @param materialSKUUUID
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	public abstract StorageCoreUnit getMaterialSKUAmount(
			SEUIComModel consumerModel, List<ServiceEntityNode> docList,
			List<ServiceEntityNode> inputParaUnionList, String materialSKUUUID)
			throws ServiceDocConfigureException;

	/**
	 * Hook Method to implement search basic doc list by search model, will be
	 * invoked by service doc configure framework.
	 * 
	 * @return basic doc list/service entity list by search model
	 * @throws ServiceDocConfigureException
	 */
	public abstract List<ServiceEntityNode> searchDocList(
			SEUIComModel searchModel, String client)
			throws ServiceDocConfigureException;

	
	/**
	 * Hook method to generate output para model, will be invoked by service doc configure framework.
	 * pay attention, this method is suitable for list-material type output
	 * 
	 * @param consumerModel
	 * @param docList
	 * @param inputParaUnionList
	 * @param materialSKUUUID
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	public abstract ServiceDocConfigOutputParaModel generateOutputParaModel(
			SEUIComModel consumerModel, List<ServiceEntityNode> docList,
			List<ServiceEntityNode> inputParaUnionList, String materialSKUUUID)
			throws ServiceDocConfigureException;


}
