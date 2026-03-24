package com.company.IntelligentPlatform.production.service;

import java.util.List;

import com.company.IntelligentPlatform.production.model.BillOfMaterialItem;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class BillOfMaterialItemIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "";

	public static final String TABLE_NAME = BillOfMaterialItem.NODENAME;

	public static final int INDEX_LENGTH = 3;

	@Override
	public String getMainTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getIdPrefix() {
		return ID_PREFIX;
	}

	@Override
	public int getIdPrefixLength() {
		return INDEX_LENGTH;
	}

	@Override
	public boolean isTimeStampNeed() {
		return false;
	}

	@Override
	public String genDefaultIdOnline(List<ServiceEntityNode> seNodeList, String client) {		
		try {
			String serialNumberId = super.getSerialNumberIdOnline(TABLE_NAME, seNodeList, client);
			if(serialNumberId == null || serialNumberId.equals(ServiceEntityStringHelper.EMPTYSTRING)){
				return super.genDefIdNoTimeStampOnline(client, ID_PREFIX, null, seNodeList,
						INDEX_LENGTH);
			}else{
				return serialNumberId;
			}
		} catch (SearchConfigureException e) {
			return null;
		} catch (ServiceEntityInstallationException e) {
			return null;
		} catch (ServiceEntityConfigureException e) {
			return null;
		}
	}

}
