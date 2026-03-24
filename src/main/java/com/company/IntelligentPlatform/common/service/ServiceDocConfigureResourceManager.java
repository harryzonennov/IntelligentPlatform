package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardValueComparatorProxy;
import com.company.IntelligentPlatform.common.service.BSearchFieldConfig;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceDocConfigurePara;

/**
 * Core Logic manager to process configure resource data consume and output
 * 
 * @author Zhang,Hang
 *
 */

@Service
public class ServiceDocConfigureResourceManager {

	@Autowired
	protected ServiceDocConfigureManager serviceDocConfigureManager;

	@Autowired
	protected ServiceDocConfigParaFactory serviceDocConfigParaFactory;

	@Autowired
	protected SimpleDataProviderFactory simpleDataProviderFactory;
	
	
	public void enterProcess(SEUIComModel consumerModel, SEUIComModel inputModel,
			String docConfigureUUID, String client)
			throws ServiceEntityConfigureException,
			ServiceDocConfigureException, ServiceSimpleDataProviderException,
			IOException, ServiceEntityInstallationException {
		ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
				.getEntityNodeByKey(docConfigureUUID,
						IServiceEntityNodeFieldConstant.UUID,
						ServiceDocConfigure.NODENAME, client, null);
		if (serviceDocConfigure == null) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOTFOUND_CONFIG,
					docConfigureUUID);
		}
		/**
		 * [Step2] Get para proxy class by document type, Get all para list
		 */
		ServiceDocConfigParaProxy serviceDocConfigParaProxy = serviceDocConfigParaFactory
				.getDocConfigProxy(serviceDocConfigure.getResourceID());
		List<ServiceEntityNode> serviceDocConfigureParaList = serviceDocConfigureManager
				.getEntityNodeListByKey(serviceDocConfigure.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ServiceDocConfigurePara.NODENAME, null);
		SEUIComModel searchModel = serviceDocConfigParaProxy
				.initInputSearchModel();
		setInputModelValue(inputModel, searchModel, serviceDocConfigureParaList);
	
		/**
		 * [Step4] Reversely set the consumer model value.
		 */
		// TODO, implement logic to pass input model to proxy and get the consumer model
		
	}

	/**
	 * Main entrance to process consumer model
	 * 
	 * @param consumerModel
	 * @param docConfigureUUID
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceDocConfigureException
	 * @throws ServiceEntityInstallationException
	 * @throws IOException
	 * @throws ServiceSimpleDataProviderException
	 */
	public void enterProcessForDocMaterial(SEUIComModel consumerModel,
			String docConfigureUUID, LogonInfo logonInfo, String materialSKUUUID)
			throws ServiceEntityConfigureException,
			ServiceDocConfigureException, ServiceSimpleDataProviderException,
			IOException, ServiceEntityInstallationException {
		ServiceDocConfigure serviceDocConfigure = (ServiceDocConfigure) serviceDocConfigureManager
				.getEntityNodeByKey(docConfigureUUID,
						IServiceEntityNodeFieldConstant.UUID,
						ServiceDocConfigure.NODENAME, logonInfo.getClient(), null);
		if (serviceDocConfigure == null) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_NOTFOUND_CONFIG,
					docConfigureUUID);
		}
		/*
		 * [Step2] Get para proxy class by document type, Get all para list
		 */
		ServiceDocConfigParaProxy serviceDocConfigParaProxy = serviceDocConfigParaFactory
				.getDocConfigProxy(serviceDocConfigure.getResourceID());
		ServiceDocMaterialTemplateParaProxy serviceDocMaterialTemplateParaProxy = (ServiceDocMaterialTemplateParaProxy) serviceDocConfigParaProxy;
		SEUIComModel searchModel = serviceDocMaterialTemplateParaProxy
				.initInputSearchModel();
		List<ServiceEntityNode> serviceDocConfigureParaList = serviceDocConfigureManager
				.getEntityNodeListByKey(serviceDocConfigure.getUuid(),
						IServiceEntityNodeFieldConstant.ROOTNODEUUID,
						ServiceDocConfigurePara.NODENAME, null);
		/*
		 * [Step3] Search the raw document list by init search model, consumer
		 * model (with input paras), and configure para list.
		 */
		List<ServiceEntityNode> rawDocList = getDocByLogicAndUnion(
				consumerModel, searchModel, logonInfo, serviceDocConfigureParaList,
				serviceDocMaterialTemplateParaProxy);
		ServiceDocConfigOutputParaModel serviceDocConfigOutputParaModel = serviceDocMaterialTemplateParaProxy
				.generateOutputParaModel(consumerModel, rawDocList,
						serviceDocConfigureParaList, materialSKUUUID);
		/*
		 * [Step4] Reversely set the consumer model value.
		 */
		setConsumerModelByOutput(serviceDocConfigOutputParaModel,
				consumerModel, serviceDocConfigureParaList);
	}

	public void setConsumerModelByOutput(
			ServiceDocConfigOutputParaModel serviceDocConfigOutputParaModel,
			SEUIComModel consumerModel,
			List<ServiceEntityNode> serviceDocConfigureParaList)
			throws ServiceDocConfigureException {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(consumerModel.getClass());
		List<Field> resourceFieldList = ServiceEntityFieldsHelper
				.getFieldsList(serviceDocConfigOutputParaModel.getClass());
		for (ServiceEntityNode seNode : serviceDocConfigureParaList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getSwitchFlag() != StandardSwitchProxy.SWITCH_ON) {
				// Skip the Switch-off para
				continue;
			}
			if (serviceDocConfigurePara.getParaDirection() == ServiceDocConfigParaUnion.DIRECT_INPUT) {
				// Skip the input para
				continue;
			}
			if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.OUTPUT_VALUEMODE_MANUALOUT) {
				// currently not implemented logic to process manual output
				continue;
			}
			if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.OUTPUT_VALUEMODE_PASSVALUE) {
				String consumerFieldName = serviceDocConfigurePara
						.getConsumerFieldName();
				String resourceFieldName = serviceDocConfigurePara
						.getResourceFieldName();
				try {
					Object outputValue = ServiceEntityFieldsHelper
							.getReflectiveObjectValue(
									serviceDocConfigOutputParaModel,
									resourceFieldList, resourceFieldName);
					setConsumerModelValue(consumerModel, fieldList,
							consumerFieldName, outputValue);
				} catch (IllegalArgumentException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage());
				} catch (IllegalAccessException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage());
				} catch (NoSuchFieldException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Logic to set value from input parameter setting to input model
	 * @param inputModel:any type of input model, but should contains the neccessary fields
	 * @param serviceDocConfigureParaList
	 * @throws ServiceDocConfigureException 
	 */
	public void setInputModelValue(Object inputModel, SEUIComModel searchModel, List<ServiceEntityNode> serviceDocConfigureParaList) throws ServiceDocConfigureException{
		// TODO Check this logic again in the future
		List<Field> inputFieldList = ServiceEntityFieldsHelper
				.getFieldsList(inputModel.getClass());
		List<Field> searchFieldList = ServiceEntityFieldsHelper
				.getFieldsList(searchModel.getClass());
		for (ServiceEntityNode seNode : serviceDocConfigureParaList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getSwitchFlag() != StandardSwitchProxy.SWITCH_ON) {
				// Skip the Switch-off para
				continue;
			}
			if (serviceDocConfigurePara.getParaDirection() == ServiceDocConfigParaUnion.DIRECT_OUTPUT) {
				// Skip the output para
				continue;
			}
			if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE) {
				// In case need to pass value from consumerModel
				try {
					// Get the dynamic value from consumer model
					Object inputValue = ServiceEntityFieldsHelper
							.getReflectiveObjectValue(inputModel, inputFieldList,
									serviceDocConfigurePara
											.getConsumerFieldName());
					setSearchModelValue(searchModel, searchFieldList,
							serviceDocConfigurePara.getResourceFieldName(),
							inputValue);
				} catch (IllegalArgumentException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage());
				} catch (IllegalAccessException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage());
				} catch (NoSuchFieldException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage()
									+ ":"
									+ serviceDocConfigurePara
											.getResourceFieldName());
				}
			}
			
		}
		
	}

	/**
	 * Core Logic to get document list by doc configure para list belongs to one
	 * group
	 * 
	 * @param consumerModel
	 * @param searchModel
	 * @param serviceDocConfigureParaList
	 * @param
	 *            : instance of para proxy class, retrieved by document type
	 * @return
	 * @throws ServiceDocConfigureException
	 */
	public List<ServiceEntityNode> getDocByLogicAndUnion(
			Object consumerModel,
			SEUIComModel searchModel, LogonInfo logonInfo,
			List<ServiceEntityNode> serviceDocConfigureParaList,
			ServiceDocMaterialTemplateParaProxy serviceDocMaterialTemplateParaProxy)
			throws ServiceDocConfigureException {
		if (ServiceCollectionsHelper.checkNullList(serviceDocConfigureParaList)) {
			return null;
		}
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(consumerModel.getClass());
		List<Field> searchFieldList = ServiceEntityFieldsHelper
				.getFieldsList(searchModel.getClass());
		for (ServiceEntityNode seNode : serviceDocConfigureParaList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			if (serviceDocConfigurePara.getSwitchFlag() != StandardSwitchProxy.SWITCH_ON) {
				// Skip the Switch-off para
				continue;
			}
			if (serviceDocConfigurePara.getParaDirection() == ServiceDocConfigParaUnion.DIRECT_OUTPUT) {
				// Skip the output para
				continue;
			}
			if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_PASSVALUE) {
				// In case need to pass value from consumerModel
				try {
					// Get the dynamic value from consumer model
					Object inputValue = ServiceEntityFieldsHelper
							.getReflectiveObjectValue(consumerModel, fieldList,
									serviceDocConfigurePara
											.getConsumerFieldName());
					setSearchModelValue(searchModel, searchFieldList,
							serviceDocConfigurePara.getResourceFieldName(),
							inputValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage());
				} catch (NoSuchFieldException e) {
					throw new ServiceDocConfigureException(
							ServiceDocConfigureException.PARA_SYSTEM_WRONG,
							e.getMessage()
									+ ":"
									+ serviceDocConfigurePara
											.getResourceFieldName());
				}
			}
			if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_SETVALUE) {
				setSearchModelValue(searchModel,
						serviceDocMaterialTemplateParaProxy, searchFieldList,
						serviceDocConfigurePara, logonInfo);
			}
			if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_COMDATASOURCE) {
				setSearchModelValue(searchModel,
						serviceDocMaterialTemplateParaProxy, searchFieldList,
						serviceDocConfigurePara, logonInfo);
			}
		}
		// After set the search model value, then invoke the hook method in
		// proxy instance and get the document list
		return serviceDocMaterialTemplateParaProxy.searchDocList(searchModel,
				serviceDocConfigureParaList.get(0).getClient());
	}
	
	

	/**
	 * set one specific field of search model value
	 * 
	 * @param searchModel
	 * @param searchFieldList
	 * @throws ServiceDocConfigureException
	 */
	public void setSearchModelValue(SEUIComModel searchModel,
			List<Field> searchFieldList, String fieldName, Object inputValue)
			throws ServiceDocConfigureException {
		try {

			for (Field searchField : searchFieldList) {
				BSearchFieldConfig searchFieldConfig = searchField
						.getAnnotation(BSearchFieldConfig.class);
				if (searchFieldConfig == null) {
					continue;
				}
				searchField.setAccessible(true);
				if (fieldName.equals(searchFieldConfig.fieldName())) {
					searchField.set(searchModel, inputValue);
				}
			}
		} catch (IllegalAccessException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * set one specific field of consumer model value
	 * 
	 * @param consumerMode
	 * @param fieldList
	 * @param fieldName
	 * @throws ServiceDocConfigureException
	 */
	public void setConsumerModelValue(SEUIComModel consumerMode,
			List<Field> fieldList, String fieldName, Object inputValue)
			throws ServiceDocConfigureException {
		try {
			for (Field field : fieldList) {
				field.setAccessible(true);
				if (fieldName.equals(field.getName())) {
					field.set(consumerMode, inputValue);
				}
			}
		} catch (IllegalAccessException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * set one specific field of search model value
	 * 
	 * @param searchModel
	 * @param searchFieldList
	 * @param serviceDocConfigurePara
	 * @throws ServiceDocConfigureException
	 */
	public void setSearchModelValue(SEUIComModel searchModel,
			ServiceDocConfigParaProxy serviceDocConfigParaProxy,
			List<Field> searchFieldList,
			ServiceDocConfigurePara serviceDocConfigurePara, LogonInfo logonInfo)
			throws ServiceDocConfigureException {
		try {
			ServiceDocConfigParaUnion serviceDocConfigParaUnion = serviceDocConfigureManager
					.filterOutParaUnionOnline(
							serviceDocConfigParaProxy.getDefaultDocInputPara(),
							serviceDocConfigurePara.getResourceFieldName());
			for (Field searchField : searchFieldList) {
				BSearchFieldConfig searchFieldConfig = searchField
						.getAnnotation(BSearchFieldConfig.class);
				if (searchFieldConfig == null) {
					continue;
				}
				searchField.setAccessible(true);

				if (serviceDocConfigurePara.getResourceFieldName().equals(
						searchFieldConfig.fieldName())) {
					// In case field name matches
					Object fixValue = null;
					if (serviceDocConfigurePara.getConsumerValueMode() == ServiceDocConfigurePara.INPUT_VALUEMODE_COMDATASOURCE) {
						fixValue = getDataProviderResultValueUnion(
								serviceDocConfigurePara, logonInfo, false);
					} else {
						fixValue = getFixValueUnion(serviceDocConfigParaUnion,
								serviceDocConfigurePara, false);
					}
					if (serviceDocConfigurePara.getFixValueOperator() == StandardValueComparatorProxy.OPERATOR_BETWEEN
							|| serviceDocConfigurePara.getFixValueOperator() == StandardValueComparatorProxy.OPERATOR_NOT_BETWEEN) {
						Object fixValueHigh = getDataProviderResultValueUnion(
								serviceDocConfigurePara, logonInfo,true);
						if (searchFieldConfig.fieldType() == BSearchFieldConfig.FIELDTYPE_LOW) {
							searchField.set(searchModel, fixValue);
							continue;
						}
						if (searchFieldConfig.fieldType() == BSearchFieldConfig.FIELDTYPE_HIGH) {
							searchField.set(searchModel, fixValueHigh);
							continue;
						}
					}
					searchField.set(searchModel, fixValue);
				}
			}
		} catch (IllegalAccessException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		} catch (ServiceSimpleDataProviderException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getErrorMessage());
		} catch (IOException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_SYSTEM_WRONG,
					e.getMessage());
		}
	}

	/**
	 * Logic to get the suitable result by checking each field type
	 * 
	 * @param serviceDocConfigParaUnion
	 * @param serviceDocConfigurePara
	 * @return
	 */
	public Object getFixValueUnion(
			ServiceDocConfigParaUnion serviceDocConfigParaUnion,
			ServiceDocConfigurePara serviceDocConfigurePara,
			boolean highValueFlag) {
		String typeSimName = serviceDocConfigParaUnion.getFieldTypeClass()
				.getSimpleName();
		if (typeSimName.equals(String.class.getSimpleName())) {
			if (highValueFlag) {
				return serviceDocConfigurePara.getFixValueHigh();
			} else {
				return serviceDocConfigurePara.getFixValue();
			}
		}
		if (typeSimName.equals(Integer.class.getSimpleName())
				|| typeSimName.equals(int.class.getSimpleName())) {
			if (highValueFlag) {
				return serviceDocConfigurePara.getFixValueIntHigh();
			} else {
				return serviceDocConfigurePara.getFixValueInt();
			}
		}
		if (typeSimName.equals(Double.class.getSimpleName())
				|| typeSimName.equals(double.class.getSimpleName())) {
			if (highValueFlag) {
				return serviceDocConfigurePara.getFixValueDoubleHigh();
			} else {
				return serviceDocConfigurePara.getFixValueDouble();
			}
		}
		if (typeSimName.equals(Date.class.getSimpleName())) {
			if (highValueFlag) {
				return serviceDocConfigurePara.getFixValueDateHigh();
			} else {
				return serviceDocConfigurePara.getFixValueDate();
			}
		}
		return null;
	}

	/**
	 * Logic to get the suitable result by checking each field type
	 *
	 * @param serviceDocConfigurePara
	 * @return
	 * @throws ServiceSimpleDataProviderException
	 */
	public Object getDataProviderResultValueUnion(
			ServiceDocConfigurePara serviceDocConfigurePara, LogonInfo logonInfo,
			boolean highValueFlag) throws ServiceSimpleDataProviderException {
		ServiceSimpleDataProviderTemplate dataProviderTemplate = simpleDataProviderFactory
				.getSimpleDataProvider(serviceDocConfigurePara
						.getDataSourceProviderID());
		if (dataProviderTemplate == null) {
			throw new ServiceSimpleDataProviderException(
					ServiceSimpleDataProviderException.PARA_NOFOUND_DATAPROVIDER_BYID,
					serviceDocConfigurePara.getDataSourceProviderID());
		}
		SimpleDataOffsetUnion simpleDataOffsetUnion = new SimpleDataOffsetUnion();
		if (highValueFlag) {
			simpleDataOffsetUnion.setOffsetDirection(serviceDocConfigurePara
					.getDataOffsetDirectionHigh());
			simpleDataOffsetUnion.setOffsetUnit(serviceDocConfigurePara
					.getDataOffsetUnitHigh());
			simpleDataOffsetUnion.setOffsetValue(serviceDocConfigurePara
					.getDataOffsetValueHigh());
		} else {
			simpleDataOffsetUnion.setOffsetDirection(serviceDocConfigurePara
					.getDataOffsetDirection());
			simpleDataOffsetUnion.setOffsetUnit(serviceDocConfigurePara
					.getDataOffsetUnit());
			simpleDataOffsetUnion.setOffsetValue(serviceDocConfigurePara
					.getDataOffsetValue());
		}
		return dataProviderTemplate.getResultData(simpleDataOffsetUnion, logonInfo);
	}

	public List<List<ServiceEntityNode>> filterConfigureInputParaList(
			List<ServiceEntityNode> rawConfigureParaList) {
		if (ServiceCollectionsHelper.checkNullList(rawConfigureParaList)) {
			return null;
		}
		List<List<ServiceEntityNode>> resultArrayList = new ArrayList<List<ServiceEntityNode>>();
		List<ServiceEntityNode> logicAndList = new ArrayList<>();
		for (ServiceEntityNode seNode : rawConfigureParaList) {
			ServiceDocConfigurePara serviceDocConfigurePara = (ServiceDocConfigurePara) seNode;
			// Check the switch
			if (serviceDocConfigurePara.getSwitchFlag() != StandardSwitchProxy.SWITCH_ON) {
				continue;
			}
			if (serviceDocConfigurePara.getLogicOperator() == StandardLogicOperatorProxy.OPERATOR_OR) {
				// In case the logic operator is [OR]
				List<ServiceEntityNode> tmpLogicOrList = new ArrayList<>();
				tmpLogicOrList.add(serviceDocConfigurePara);
				resultArrayList.add(tmpLogicOrList);
			} else {
				// In case the logic operator is [AND]
				logicAndList.add(serviceDocConfigurePara);
			}
			if (!ServiceCollectionsHelper.checkNullList(logicAndList)) {
				resultArrayList.add(logicAndList);
			}
		}
		return resultArrayList;
	}

}
