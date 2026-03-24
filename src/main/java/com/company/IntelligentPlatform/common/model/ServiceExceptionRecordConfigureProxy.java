package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ServiceExceptionRecord]
 * 
 * @author
 * @date Mon Jul 22 11:03:55 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class ServiceExceptionRecordConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of ServiceExceptionRecord [ROOT] node
		ServiceEntityConfigureMap serviceExceptionRecordConfigureMap = new ServiceEntityConfigureMap();
		serviceExceptionRecordConfigureMap.setParentNodeName(" ");
		serviceExceptionRecordConfigureMap.setNodeName("ROOT");
		serviceExceptionRecordConfigureMap
				.setNodeType(ServiceExceptionRecord.class);
		serviceExceptionRecordConfigureMap
				.setTableName("ServiceExceptionRecord");
		serviceExceptionRecordConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serviceExceptionRecordConfigureMap.addNodeFieldMap("callStack",
				java.lang.String.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("source",
				java.lang.String.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("processorUUID",
				java.lang.String.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("reporterUUID",
				java.lang.String.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("processorName",
				java.lang.String.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("reporterName",
				java.lang.String.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("category",
				int.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("priority",
				int.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("sourceType",
				int.class);
		serviceExceptionRecordConfigureMap.addNodeFieldMap("status", int.class);
		seConfigureMapList.add(serviceExceptionRecordConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
