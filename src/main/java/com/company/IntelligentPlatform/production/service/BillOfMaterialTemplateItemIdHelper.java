package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.BillOfMaterialTemplateItem;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.SearchConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

import java.util.List;

@Service
public class BillOfMaterialTemplateItemIdHelper extends ServiceDefaultIdGenerateHelper {

	public static final String ID_PREFIX = "";

	public static final String TABLE_NAME = BillOfMaterialTemplateItem.NODENAME;

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

}
