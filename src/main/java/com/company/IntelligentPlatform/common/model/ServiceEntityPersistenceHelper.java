package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.List;

// TODO-LEGACY: import platform.foundation.Administration.Model.ClientInfo;
// TODO-LEGACY: import platform.foundation.Administration.Model.InstallAdminUnion;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceEntityPersistenceHelper {
	
	/**
	 * Static method:get all system cross client table:
	 * Should list and add all cross client table here.
	 * @return
	 */
	public static List<String> getCrossClientTableList(){
		 List<String> tableList = new ArrayList<>();
		 tableList.add(ServiceEntityRegisterEntity.SENAME);
		 tableList.add(City.SENAME);
		 tableList.add(Province.SENAME);
		 tableList.add(Country.SENAME);
		 tableList.add(ActionCode.SENAME);
		 tableList.add(AuthorizationGroup.SENAME);
		 tableList.add(AuthorizationObject.SENAME);
		 // InstallAdminUnion and ClientInfo not yet migrated - skip
		 tableList.add(SystemAuthorizationObject.SENAME);
		 return tableList;
	}
	
	public static boolean checkTableCrossClient(String tableName){
		List<String> tableList = getCrossClientTableList();
        return tableList.contains(tableName);
	}

}
